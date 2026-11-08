"""Create and upgrade disposable worlds using the public 1.11.2 beta4.58 release."""
import argparse
import json
import subprocess
import time
from pathlib import Path

from run_packaged_suite import fingerprint
from run_runtime_checks import FORGE, ROOT, dependency, digest, prepare, write_json


PUBLIC_JARS = {
    "modern-metals-244357-2534044": "8F5011542650A03A7B25E4BCB410D676A5FB6AD705B91552E494A08A6C43087A",
    "base-metals-240967-2533178": "ABDF704023100AD6BB70351CE72C31A07943AD5BCE8308155564194A25089CCC",
    "mmd-orespawn-245586-2533159": "05CC48E59C7B20ED9B7F29AE014F5E2E5B2F16F289EF7D6F274F017B5E2A6F77",
}
BLOCKS = [
    ("0 100 0", "aluminum_ore"),
    ("1 100 0", "chromium_ore"),
    ("2 100 0", "tungsten_block"),
    ("7 100 7", "aluminumbrass"),
    ("15 100 7", "titanium"),
]
STACKS = [
    ("aluminum_ingot", 7, 0),
    ("titanium_pickaxe", 1, 17),
    ("plutonium_pickaxe", 1, 3),
    ("aluminum_door", 2, 0),
    ("tungsten_fishing_rod", 1, 4),
    ("titanium_horse_armor", 1, 0),
]


def chest():
    items = [f'{{Slot:{slot}b,id:"modernmetals:{item}",Count:{count}b,Damage:{damage}s}}'
             for slot, (item, count, damage) in enumerate(STACKS)]
    items.append('{Slot:6b,id:"forge:bucketfilled",Count:1b,Damage:0s,tag:{FluidName:"aluminumbrass",Amount:1000}}')
    return "{Items:[" + ",".join(items) + "]}"


def place():
    containers = ["fill 5 99 5 9 102 9 minecraft:stone 0 hollow", "fill 13 99 5 17 102 9 minecraft:stone 0 hollow"]
    blocks = [f"setblock {pos} modernmetals:{block}" for pos, block in BLOCKS]
    return containers + blocks + [f"setblock 0 100 2 minecraft:chest 0 replace {chest()}"]


def verify():
    blocks = [f"testforblock {pos} modernmetals:{block}" for pos, block in BLOCKS]
    return blocks + [f"testforblock 0 100 2 minecraft:chest -1 {chest()}"]


def run(java, directory, phase, setup, checks):
    console = directory / (phase + "-console.log")
    with console.open("wb") as log, subprocess.Popen(
        [str(java), "-Xmx2G", "-jar", FORGE, "nogui"],
        cwd=directory, stdin=subprocess.PIPE, stdout=log, stderr=subprocess.STDOUT
    ) as process:
        try:
            deadline = time.monotonic() + 100
            while 'For help, type "help"' not in (output := console.read_text(errors="replace")):
                if "Run the command /fml confirm" in output:
                    raise AssertionError(f"Forge requested permission to remove old entries: {console}")
                if process.poll() is not None or time.monotonic() >= deadline:
                    raise AssertionError(f"Server did not reach the world: {console}")
                time.sleep(0.25)
            commands = setup + checks + ["save-all", "stop"]
            process.communicate(("\n".join(commands) + "\n").encode("ascii"), timeout=60)
            assert process.returncode == 0, console
        finally:
            if process.poll() is None:
                process.kill()
                process.wait()
    assert console.read_text(errors="replace").count("Successfully found the block") == len(checks), console
    print(f"{directory.name} {phase} passed {len(checks)} block and chest checks", flush=True)


def check(args):
    jars = []
    for name, expected in PUBLIC_JARS.items():
        jar = args.public_jars / (name + ".jar")
        assert digest(jar).upper() == expected, f"Public jar checksum mismatch: {jar}"
        jars.append(jar)
    source = args.output / "beta4.58-source"
    prepare(source, args.template, jars)
    run(args.java, source, "capture", place(), verify())
    world = source / "world"
    before = fingerprint(world)
    specs = json.loads((ROOT / "gradle/dependencies.json").read_text())
    jars = [dependency(spec, args.mirror) for spec in specs if spec.get("runtime")]
    target = args.output / "beta4.58-upgraded"
    prepare(target, args.template, jars + [args.candidate], world)
    try:
        for phase in ("first", "reload"):
            run(args.java, target, phase, [], verify())
    finally:
        assert fingerprint(world) == before, "Original fixture was modified"
    write_json(target / "receipt.json", {"passed": True, "candidate_sha256": digest(args.candidate),
                                        "source_world_unchanged": True, "public_jars": PUBLIC_JARS})


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    for name in ("java", "template", "output", "public-jars", "candidate"):
        parser.add_argument("--" + name, required=True, type=lambda path: Path(path).resolve())
    parser.add_argument("--mirror", type=lambda path: Path(path).resolve())
    args = parser.parse_args()
    check(args)


if __name__ == "__main__":
    main()

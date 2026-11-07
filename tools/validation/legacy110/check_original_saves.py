"""Create and upgrade disposable worlds using the two public 1.10.2 releases."""
import argparse
import json
import subprocess
import time
from pathlib import Path

from run_packaged_suite import fingerprint
from run_runtime_checks import FORGE, ROOT, dependency, digest, prepare, write_json


PUBLIC_JARS = {
    "cyano": {
        "modern-metals-244357-2305634": "F8FC0FD92CB2A3C76DD7ABE39926AD44A8A52A08F9BE76CD677AEBD35D492490",
        "base-metals-240967-2323100": "F919CEF794C73F02758BBD967346DBA0B53F6383153C7E29BD79F211CDA8C319",
        "mmd-orespawn-245586-2323091": "91345E4B825AA06E05F470066F467AABA0C7A2804A8DBCA4D3CD1905ED53607C",
    },
    "mmd": {
        "modern-metals-244357-2533187": "B721359D37269D632DDA9ED1B7CEC6392EC295FB9C1111A47720B240622848D9",
        "base-metals-240967-2533174": "51F04770C316E62D9ED3EF713144F02FA2EC899645813627091365E12F61C3EF",
        "mmdlib-261744-2400405": "286149F4E8132535135C27285A750916D35AB3ECDDB28AE4475FC604D1FDF178",
        "mmd-orespawn-245586-2533153": "D964148C20175E16CCEFAB02AE43A181A50DC08A1D636A77E59CFE8C8EA8D41A",
    },
}
# Enclose the molten blocks so the fixtures retain source states after the server ticks.
CONTAINERS = ["fill 5 99 5 9 102 9 minecraft:stone hollow", "fill 13 99 5 17 102 9 minecraft:stone hollow"]
CYANO_ORES = ["aluminum_ore", "chromium_ore", "tungsten_block",
              "galvanizedsteel_ore", "nichrome_ore", "stainlesssteel_ore"]


def positions(family, original):
    if family == "mmd":
        # The public beta4.19 jar only registers fluid blocks/items in this stack.
        return [("7 100 7", "aluminumbrass"), ("15 100 7", "titanium")]
    brass = "alubrass" if original else "aluminumbrass"
    return [(f"{x} 100 0", block) for x, block in enumerate(CYANO_ORES)] + [("7 100 7", brass)]


def chest(family, original):
    if family == "mmd":
        items = [("aluminumbrass", 1, 0), ("titanium", 1, 0)]
    else:
        items = [
            ("aluminum_ingot", 7, 0), ("titanium_pickaxe", 1, 17), ("plutonium_pickaxe", 1, 3),
            ("aluminum_door_item" if original else "aluminum_door", 2, 0),
            ("tungsten_fishingrod" if original else "tungsten_fishing_rod", 1, 4),
            ("titanium_horsearmor" if original else "titanium_horse_armor", 1, 0),
            ("nichrome_ore", 2, 0), ("alubrass" if original else "aluminumbrass", 1, 0),
        ]
    nbt = [f'{{Slot:{slot}b,id:"modernmetals:{item}",Count:{count}b,Damage:{damage}s}}'
           for slot, (item, count, damage) in enumerate(items)]
    if family == "cyano":
        nbt.append('{Slot:8b,id:"forge:bucketFilled",Count:1b,Damage:0s,tag:{FluidName:"alubrass",Amount:1000}}')
    return "{Items:[" + ",".join(nbt) + "]}"


def place(family):
    blocks = [f"setblock {pos} modernmetals:{block}" for pos, block in positions(family, True)]
    return CONTAINERS + blocks + [f"setblock 0 100 2 minecraft:chest 0 replace {chest(family, True)}"]


def verify(family, original):
    blocks = [f"testforblock {pos} modernmetals:{block}" for pos, block in positions(family, original)]
    return blocks + [f"testforblock 0 100 2 minecraft:chest -1 {chest(family, original)}"]


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


def check(args, family):
    jars = []
    for name, expected in PUBLIC_JARS[family].items():
        jar = args.public_jars / (name + ".jar")
        assert digest(jar).upper() == expected, f"Public jar checksum mismatch: {jar}"
        jars.append(jar)
    source = args.output / (family + "-source")
    prepare(source, args.template, jars)
    run(args.java, source, "capture", place(family), verify(family, True))
    world = source / "world"
    before = fingerprint(world)
    specs = json.loads((ROOT / "gradle/dependencies.json").read_text())
    jars = [dependency(spec) for spec in specs if spec.get("runtime")]
    target = args.output / (family + "-upgraded")
    prepare(target, args.template, jars + [args.candidate], world)
    try:
        for phase in ("first", "reload"):
            run(args.java, target, phase, [], verify(family, False))
    finally:
        assert fingerprint(world) == before, "Original fixture was modified"
    write_json(target / "receipt.json", {"passed": True, "candidate_sha256": digest(args.candidate),
                                        "source_world_unchanged": True, "public_jars": PUBLIC_JARS[family]})


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    for name in ("java", "template", "output", "public-jars", "candidate"):
        parser.add_argument("--" + name, required=True, type=lambda path: Path(path).resolve())
    parser.add_argument("--family", choices=tuple(PUBLIC_JARS))
    args = parser.parse_args()
    for family in (args.family,) if args.family else PUBLIC_JARS:
        check(args, family)


if __name__ == "__main__":
    main()

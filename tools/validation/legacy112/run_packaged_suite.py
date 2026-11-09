"""Run the packaged mod on disposable Minecraft 1.12.2 servers."""

import argparse
import hashlib
import json
from pathlib import Path
import shutil
import subprocess
from zipfile import ZipFile

from jsonschema.validators import validator_for
from referencing import Registry, Resource


def dependency(spec, mirror):
    group, name, version = spec["coordinate"].split(":")
    jar = mirror / group.replace(".", "/") / name / version / f"{name}-{version}.jar"
    assert digest(jar).upper() == spec["sha256"], jar
    return jar


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def write_config(directory, mod, mode, boron_disabled=False):
    text = f"general {{\n    S:contentMode={mode}_fantasy\n}}\n"
    if boron_disabled:
        text += "metals {\n    B:EnableBoron=false\n}\n"
    (directory / f"config/{mod}.cfg").write_text(text, encoding="utf-8")


def run(java, directory, phase, flags):
    report = directory / "modernmetals-review.json"
    assert not report.exists(), report
    console = directory / f"{phase}-console.log"
    with console.open("w", encoding="utf-8") as log:
        process = subprocess.run(
            [str(java), "-Xmx1200M", *flags, "-jar",
             "forge-1.12.2-14.23.5.2859-universal.jar", "nogui"],
            cwd=directory, stdin=subprocess.DEVNULL, stdout=log, stderr=subprocess.STDOUT,
            timeout=150, check=False,
        )
    assert process.returncode == 0 and report.is_file(), console
    report.rename(directory / f"{phase}-review.json")
    return json.loads((directory / f"{phase}-review.json").read_text(encoding="utf-8"))


def prepare(template, target, installed):
    shutil.copytree(template / "libraries", target / "libraries")
    for filename in ("forge-1.12.2-14.23.5.2859-universal.jar", "minecraft_server.1.12.2.jar"):
        shutil.copy2(template / filename, target)
    (target / "eula.txt").write_text("eula=true\n", encoding="utf-8")
    (target / "server.properties").write_text(
        "online-mode=false\nserver-ip=127.0.0.1\nserver-port=0\nmax-tick-time=-1\n"
        "level-seed=6713525\nview-distance=3\n", encoding="utf-8",
    )
    for folder in ("config", "mods"):
        (target / folder).mkdir()
    for jar in installed:
        shutil.copy2(jar, target / "mods")


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    for arg in ("java", "template", "output", "mirror"):
        parser.add_argument(f"--{arg}", type=Path, required=True)
    args = parser.parse_args()
    args.output.mkdir(parents=True)
    specs = json.loads(Path("gradle/dependencies.json").read_text(encoding="utf-8"))
    jars = {spec["id"]: dependency(spec, args.mirror) for spec in specs}
    mod = Path("build/libs/ModernMetals-2.5.2.112021.jar")
    probe = Path("build/test-mods/modernmetals-probe-packaged.jar")
    with ZipFile(jars["orespawn"]) as archive:
        schemas = [json.loads(archive.read(name)) for name in archive.namelist()
                   if name.startswith("META-INF/orespawn/docs/schemas/") and name.endswith(".json")]
    registry = Registry().with_resources((schema["$id"], Resource.from_contents(schema)) for schema in schemas)
    schema = next(schema for schema in schemas if schema["$id"].endswith("/orespawn-provider.schema.json"))
    validator = validator_for(schema)(schema, registry=registry)

    fresh_provider = None
    for name, local_low, inherited, boron_disabled, integrations in [
        ("standalone-high", False, False, False, ()),
        ("standalone-low", True, False, True, ()),
        ("inherit-base-low", False, True, False, ()),
        ("tinkers-high", False, False, False, ("mantle", "tconstruct")),
        ("tinkers-low", True, False, False, ("mantle", "tconstruct")),
        ("titanium-opt-in", False, False, False, ()),
        ("os3-import", False, False, False, ()),
    ]:
        target = args.output / name
        installed = [jars["mmdlib"], jars["orespawn"], mod, probe]
        installed.extend(jars[integration] for integration in integrations)
        if inherited:
            installed.append(jars["basemetals"])
        prepare(args.template, target, installed)
        if inherited:
            write_config(target, "BaseMetals", "low")
        write_config(target, "ModernMetals", "low" if local_low else "high", boron_disabled)

        rule_id = "modernmetals:legacy/titanium_ore"
        if name == "titanium-opt-in":
            fresh_provider["ores"][rule_id]["enabled"] = True
            override = target / "config/modernmetals-orespawn.json"
            override.write_text(json.dumps(fresh_provider), encoding="utf-8")
            source_path = override
        elif name == "os3-import":
            legacy = target / "config/orespawn3/modernmetals.json"
            legacy.parent.mkdir()
            original = Path("tools/validation/legacy112/fixtures/orespawn3-modernmetals.json")
            settings = json.loads(original.read_text(encoding="utf-8"))
            settings["spawns"]["aluminum_ore"]["parameters"].update(
                size=5, variation=0, frequency=2.75, minHeight=10, maxHeight=50)
            settings["spawns"]["thorium_ore"]["enabled"] = False
            legacy.write_text(json.dumps(settings), encoding="utf-8")
            source_path = legacy
        else:
            source_path = None
        original = digest(source_path) if source_path else None

        flags = [f"-Dreview.low={str(local_low or inherited).lower()}",
                 f"-Dreview.inherited={str(inherited).lower()}",
                 f"-Dreview.boronDisabled={str(boron_disabled).lower()}",
                 f"-Dreview.titanium={str(name == 'titanium-opt-in').lower()}"]
        first = run(args.java, target, "first", flags)
        provider = first["provider"]
        if name == "standalone-high":
            fresh_provider = provider
        validator.validate(provider)
        rules = provider["ores"]
        assert len(rules) == (16 if boron_disabled else 17), rules.keys()
        assert not rules[rule_id]["enabled"]
        assert all(not rule["retrogen"] for rule in rules.values())
        profile = first["profile"]
        assert not profile.get("terrain_dimensions")
        assert profile["ores"].get(rule_id, {}).get("enabled", False) == (name == "titanium-opt-in")
        selector = "orespawn:all_except_nether_end"
        if name == "titanium-opt-in":
            assert (profile["ores"][rule_id]["dimension_selectors"][selector]
                    == profile["ores"]["modernmetals:legacy/rutile_ore"]["dimension_selectors"][selector])
        if name == "os3-import":
            aluminum = profile["ores"]["modernmetals:legacy/aluminum_ore"]["dimension_selectors"][selector]
            assert (aluminum["frequency"], aluminum["min_y"], aluminum["max_y"]) == (2.75, 10, 49), aluminum
            assert not profile["ores"]["modernmetals:legacy/thorium_ore"]["enabled"]
        group = profile["ore_material_groups"]["orespawn:aluminum"]
        assert {"oreAluminum", "oreAluminium"} <= set(group["ore_dictionary_entries"])
        if name == "os3-import":
            # The 1.12 importer keeps the ambiguous legacy Bauxite alias for review.
            assert any(key.startswith("orespawn:review/") and "oreBauxite" in item["ore_dictionary_entries"]
                       for key, item in profile["ore_material_groups"].items())
        else:
            assert "oreBauxite" in group["ore_dictionary_entries"]
        assert run(args.java, target, "reload", flags) == first, name
        if name == "os3-import":
            remote_region = target / "world/region/r.6.-6.mca"
            assert not remote_region.exists(), remote_region
            later = run(args.java, target, "later", [*flags, "-Dreview.later=true"])
            assert remote_region.is_file() and later["profile"] == profile
            print("OS3 profile preserved while generating later chunks", flush=True)
        if source_path:
            assert digest(source_path) == original, source_path
        print(f"{name} passed fresh world and reload", flush=True)
    print(f"Packaged game SHA-256 {digest(mod)}")


if __name__ == "__main__":
    main()

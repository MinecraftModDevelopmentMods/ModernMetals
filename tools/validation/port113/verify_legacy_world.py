"""Compare Modern Metals data in a source Forge save and its upgraded copy."""
import argparse
from collections import Counter
from io import BytesIO
import gzip
import json
from pathlib import Path
import zlib

import nbtlib
import numpy as np

PREFIX = "modernmetals:"
FACING = ("down", "up", "north", "south", "west", "east")
HORIZONTAL = ("south", "west", "north", "east")
DOOR = ("east", "south", "west", "north")
TRAPDOOR = ("north", "south", "west", "east")
FLUIDS = {
    "aluminum", "aluminumbrass", "beryllium", "boron", "cadmium", "chromium", "galvanizedsteel",
    "iridium", "magnesium", "manganese", "nichrome", "osmium", "plutonium", "rutile",
    "stainlesssteel", "tantalum", "thorium", "titanium", "tungsten", "uranium", "zirconium",
}


def canonical(name):
    name = name.lower().removeprefix(PREFIX)
    if name == "alubrass":
        name = "aluminumbrass"
    for old, new in (("_door_item", "_door"), ("_horsearmor", "_horse_armor"), ("_fishingrod", "_fishing_rod")):
        if name.endswith(old):
            name = name.removesuffix(old) + new
            break
    return PREFIX + name


def region_chunks(path):
    with path.open("rb") as stream:
        offsets = stream.read(4096)
        for offset in range(0, 4096, 4):
            sector = int.from_bytes(offsets[offset:offset + 3], "big")
            if not sector:
                continue
            stream.seek(4096 * sector)
            length = int.from_bytes(stream.read(4), "big")
            scheme = stream.read(1)[0]
            payload = stream.read(length - 1)
            if scheme == 1:
                payload = gzip.decompress(payload)
            elif scheme == 2:
                payload = zlib.decompress(payload)
            else:
                raise ValueError(f"Unknown chunk compression {scheme} in {path}")
            yield nbtlib.File.parse(BytesIO(payload))


def region_records(world):
    paths = list((world / "region").glob("*.mca"))
    for dimension in world.glob("DIM*"):
        paths.extend((dimension / "region").glob("*.mca"))
    for path in sorted(paths):
        dimension = str(path.parent.parent.relative_to(world))
        for root in region_chunks(path):
            level = root["Level"]
            yield (dimension, int(level["xPos"]), int(level["zPos"])), level


def nibble_array(nbt):
    if nbt is None:
        return np.zeros(4096, dtype=np.uint16)
    raw = np.asarray(nbt, dtype=np.uint8)
    result = np.empty(4096, dtype=np.uint16)
    result[::2] = raw & 15
    result[1::2] = raw >> 4
    return result


def expected_state(name, meta):
    path = name.removeprefix(PREFIX)
    state = {}
    if path.startswith("double_"):
        name = PREFIX + path.removeprefix("double_")
        state["type"] = "double"
    elif path.endswith("_slab"):
        state["type"] = "top" if meta & 8 else "bottom"
    elif path.endswith("_plate") and not path.endswith("_pressure_plate"):
        state["facing"] = FACING[(meta & 7) % 6]
    elif path.endswith("_stairs"):
        state = {"facing": FACING[5 - (meta & 3)], "half": "top" if meta & 4 else "bottom"}
    elif path.endswith("_door"):
        if meta & 8:
            state = {"half": "upper", "hinge": "right" if meta & 1 else "left", "powered": str(bool(meta & 2)).lower()}
        else:
            state = {"half": "lower", "facing": DOOR[meta & 3], "open": str(bool(meta & 4)).lower()}
    elif path.endswith("_trapdoor"):
        state = {"facing": TRAPDOOR[meta & 3], "half": "top" if meta & 8 else "bottom", "open": str(bool(meta & 4)).lower()}
    elif path.endswith("_button"):
        attach = ("down", "east", "west", "south", "north", "up", "up", "up")[meta & 7]
        state = {"face": {"down": "ceiling", "up": "floor"}.get(attach, "wall"), "powered": str(bool(meta & 8)).lower()}
        if attach not in {"up", "down"}:
            state["facing"] = attach
    elif path.endswith("_lever"):
        orientation = meta & 7
        attach, face = (
            ("ceiling", "east"), ("wall", "east"), ("wall", "west"), ("wall", "south"),
            ("wall", "north"), ("floor", "north"), ("floor", "east"), ("ceiling", "north"),
        )[orientation]
        state = {"face": attach, "facing": face, "powered": str(bool(meta & 8)).lower()}
    elif path.endswith("_pressure_plate"):
        state["powered"] = str(meta > 0).lower()
    elif path in FLUIDS:
        state["level"] = str(meta)
    return name, state


def saved_stacks(value, old_items):
    if isinstance(value, nbtlib.Compound):
        item_id = value.get("id", "")
        if "Count" in value:
            if isinstance(item_id, nbtlib.Numeric):
                item_id = old_items.get(int(item_id), "")
            name = str(item_id).lower()
            tag = value.get("tag", {})
            if name == "forge:bucketfilled":
                fluid = canonical(str(tag.get("FluidName", "")))
                if fluid.removeprefix(PREFIX) in FLUIDS:
                    name = fluid + "_bucket"
            if name.startswith(PREFIX):
                properties = {key: entry.snbt() for key, entry in tag.items()
                              if key != "Damage" and (str(item_id).lower() != "forge:bucketfilled"
                                                        or key not in {"FluidName", "Amount"})}
                yield (canonical(name), int(value["Count"]), int(tag.get("Damage", value.get("Damage", 0))),
                       int(value.get("Slot", -1)), json.dumps(properties, sort_keys=True))
        for child in value.values():
            yield from saved_stacks(child, old_items)
    elif isinstance(value, nbtlib.List):
        for child in value:
            yield from saved_stacks(child, old_items)


def loose_stacks(world, item_ids):
    files = [(world / "level.dat", "Data.Player")]
    files.extend((path, str(path.relative_to(world))) for path in (world / "playerdata").glob("*.dat"))
    for path, location in files:
        data = nbtlib.load(path)
        if location == "Data.Player":
            data = data["Data"].get("Player", nbtlib.Compound())
        for stack in saved_stacks(data, item_ids):
            yield location, stack


def legacy_states(level, block_ids):
    ids = list(block_ids)
    for section in level.get("Sections", []):
        if "Blocks" not in section:
            continue
        numeric = np.asarray(section["Blocks"], dtype=np.uint8).astype(np.uint16)
        numeric |= nibble_array(section.get("Add")) << 8
        matches = np.flatnonzero(np.isin(numeric, ids))
        if not len(matches):
            continue
        meta = nibble_array(section.get("Data"))
        section_y = int(section["Y"])
        for index in matches:
            name = canonical(block_ids[numeric[index]])
            yield (section_y, int(index)), expected_state(name, int(meta[index]))


def join_legacy_door_expectations(states):
    for (section, index), (name, properties) in states.items():
        if not name.endswith("_door") or properties.get("half") != "lower":
            continue
        index_above = index + 256
        other_name, other = states.get((section + index_above // 4096, index_above % 4096), ("", {}))
        if other_name == name and other.get("half") == "upper":
            properties.update({key: other[key] for key in ("hinge", "powered")})
            other.update({key: properties[key] for key in ("facing", "open")})


def actual_states(level, locations):
    sections = {int(section["Y"]): section for section in level.get("Sections", [])}
    for section_y, index in locations:
        section = sections.get(section_y, {})
        palette = section.get("Palette", [])
        block_states = section.get("BlockStates", [])
        if not palette or not len(block_states):
            yield "", {}
            continue
        bits = max(4, (len(palette) - 1).bit_length())
        long_index, offset = divmod(index * bits, 64)
        combined = (int(block_states[long_index]) & ((1 << 64) - 1)) >> offset
        if offset + bits > 64:
            combined |= int(block_states[long_index + 1]) << (64 - offset)
        entry = palette[combined & ((1 << bits) - 1)]
        yield str(entry["Name"]), dict(entry.get("Properties", {}))


def compare(source, upgraded, before_registry, include_properties=True):
    block_ids = {number: name for name, number in before_registry["blocks"].items()}
    item_ids = {number: name for name, number in before_registry["items"].items()}
    # Store only the chunks with Modern Metals data, then stream the upgraded regions.
    expected = {}
    state_counts = Counter()
    for chunk, level in region_records(source):
        blocks = dict(legacy_states(level, block_ids))
        join_legacy_door_expectations(blocks)
        stacks = Counter(saved_stacks(level, item_ids))
        if blocks or stacks:
            expected[chunk] = blocks, stacks
        state_counts.update(name for name, _ in blocks.values())
    errors = Counter()
    block_count = 0
    stack_count = 0
    sample = []
    for chunk, level in region_records(upgraded):
        if chunk not in expected:
            continue
        blocks, stacks = expected.pop(chunk)
        for (location, (name, properties)), (current_name, current_properties) in zip(
                blocks.items(), actual_states(level, blocks)):
            block_count += 1
            if current_name != name:
                key = ("block", name, current_name)
                errors[key] += 1
                if len(sample) < 12:
                    sample.append((chunk, location, key))
            elif include_properties:
                for key, value in properties.items():
                    actual = current_properties.get(key)
                    if actual != value:
                        errors["property", name, key, value, str(actual)] += 1
        stack_count += sum(stacks.values())
        for item, count in (stacks - Counter(saved_stacks(level, {}))).items():
            errors["chunk stack", *item] += count
    for location in expected:
        errors["unconverted chunk", *location] += 1
    expected_loose = Counter(loose_stacks(source, item_ids))
    stack_count += sum(expected_loose.values())
    for (location, item), count in (expected_loose - Counter(loose_stacks(upgraded, {}))).items():
        errors["player stack", location, *item] += count
    return {"blocks": block_count, "stacks": stack_count, "block_types": dict(state_counts),
            "errors": [(key, value) for key, value in errors.most_common()], "samples": sample}


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("source", type=Path)
    parser.add_argument("upgraded", type=Path)
    parser.add_argument("--identity-only", action="store_true")
    args = parser.parse_args()
    registry = nbtlib.load(args.source / "level.dat")["FML"]["Registries"]
    rows = {}
    for kind in ("blocks", "items"):
        rows[kind] = {str(entry["K"]): int(entry["V"]) for entry in registry[f"minecraft:{kind}"]["ids"]
                      if str(entry["K"]).lower().startswith(PREFIX)}
    result = compare(args.source, args.upgraded, rows, not args.identity_only)
    print(json.dumps(result, indent=2))
    if result["errors"]:
        raise SystemExit(1)


if __name__ == "__main__":
    main()

"""Translate the released 1.12.2 models and recipes to Forge 25 resource formats."""
import argparse
import json
import re
from pathlib import Path
from zipfile import ZipFile

MOD = "modernmetals"
ASSETS = f"assets/{MOD}/"
MATERIAL_FORMS = {
    "ingot": ("ingots", "ingot"), "nugget": ("nuggets", "nugget"),
    "dust": ("dusts", "powder"), "powder": ("dusts", "powder"),
    "dustTiny": ("tiny_dusts", "smallpowder"), "block": ("storage_blocks", "block"),
    "rod": ("rods", "rod"), "gear": ("gears", "gear"),
    "blend": ("blends", "blend"), "smallblend": ("small_blends", "smallblend"),
}
BASE_DUSTS = {"copper", "nickel", "steel", "zinc"}
MODEL_RENAMES = {
    "block/trapdoor_bottom": "block/template_orientable_trapdoor_bottom",
    "block/trapdoor_top": "block/template_orientable_trapdoor_top",
    "block/trapdoor_open": "block/template_orientable_trapdoor_open",
    "block/half_slab": "block/slab",
    "block/upper_slab": "block/slab_top",
    "block/wall_post": "block/template_wall_post",
    "block/wall_side": "block/template_wall_side",
    "blocks/lever": "minecraft:block/lever"
}
ALLOY_ORES = {"galvanizedsteel_ore", "nichrome_ore", "stainlesssteel_ore"}


def json_file(path, data):
    if path.exists() and json.loads(path.read_text(encoding="utf-8-sig")) == data:
        return
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(data, ensure_ascii=False, indent=2) + "\n", encoding="utf-8", newline="\n")


def model_data(value):
    if isinstance(value, dict):
        return {key: model_data(entry) for key, entry in value.items()}
    if isinstance(value, list):
        return [model_data(entry) for entry in value]
    if isinstance(value, str):
        value = value.replace("minecraft:blocks/", "minecraft:block/").replace("minecraft:items/", "minecraft:item/")
        if value.startswith("basemetals:block/"):
            value = value.replace("basemetals:", "modernmetals:", 1)
        value = MODEL_RENAMES.get(value, value)
        if value == "builtin/generated":
            return "item/generated"
    return value


def tag(directory, registry, path, values):
    json_file(directory / f"data/forge/tags/{registry}/{path}.json", {"replace": False, "values": sorted(values)})


def ingredient(entry, tags):
    if isinstance(entry, list):
        return [ingredient(value, tags) for value in entry]
    if "ore" in entry:
        ore = entry["ore"]
        if ore == "stickWood":
            return {"item": "minecraft:stick"}
        if ore == "plankWood":
            return {"tag": "minecraft:planks"}
        category, material = re.fullmatch(r"([a-z]+(?:Tiny)?)([A-Z][A-Za-z]+)", ore).groups()
        material = material.lower()
        kind, form = MATERIAL_FORMS[category]
        path = f"{kind}/{material}"
        if material not in BASE_DUSTS:
            tags.setdefault(path, set()).add(f"{MOD}:{material}_{form}")
        return {"tag": f"forge:{path}"}
    if entry.get("item") == "minecraft:wool" and entry.get("data") == 32767:
        colours = ("white", "orange", "magenta", "light_blue", "yellow", "lime", "pink",
                   "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black")
        return [{"item": f"minecraft:{colour}_wool"} for colour in colours]
    data = entry.get("data", 0)
    if data:
        raise ValueError(f"Unmapped item metadata in recipe ingredient {entry}")
    return {"item": entry["item"]}


def translate_recipe(raw, tags):
    output = raw["result"]
    if output.get("data", 0):
        raise ValueError(f"Unmapped recipe output metadata {output}")
    result = {"item": output["item"]}
    if "count" in output:
        result["count"] = output["count"]
    recipe = {"result": result}
    if "key" in raw:
        recipe["type"] = "minecraft:crafting_shaped"
        recipe["pattern"] = raw["pattern"]
        recipe["key"] = {key: ingredient(value, tags) for key, value in raw["key"].items()}
    else:
        recipe["type"] = "minecraft:crafting_shapeless"
        recipe["ingredients"] = [ingredient(value, tags) for value in raw["ingredients"]]
    serialized = {"type": "modernmetals:content_crafting", "recipe": recipe}
    required = sorted({match.group(1) for match in re.finditer(
        r'"tag": "(forge:dusts/[^"]+)"', json.dumps(recipe))
        if match.group(1).split("/")[-1] in BASE_DUSTS})
    if required:
        serialized["conditions"] = [{"type": "modernmetals:non_empty_tag", "tag": name} for name in required]
    return serialized


def modern_model(source, material):
    return (source.replace("basemetals:", "modernmetals:").replace("copper", material))


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("release", type=Path, help="Published Modern Metals 2.5.2.112021 game jar")
    parser.add_argument("base_metals", type=Path, help="Base Metals Forge 25 checkout")
    args = parser.parse_args()
    project = Path(__file__).resolve().parents[3]
    resources = project / "src/main/resources"
    generated = project / "src/generated/resources"
    own = resources / ASSETS
    data = generated / "data/modernmetals"
    registry = json.loads((resources / "data/modernmetals/migration/registry_1_12.json").read_text(encoding="utf-8"))
    blocks = set(registry["blocks"]) | ALLOY_ORES
    blocks = {block for block in blocks if not block.startswith("double_")}
    items = set(registry["items"]) | ALLOY_ORES
    materials = sorted(name for name in blocks if "_" not in name)
    items.update(f"{name}_scythe" for name in materials)

    tags = {}
    with ZipFile(args.release) as release:
        for name in release.namelist():
            if not name.startswith(ASSETS):
                continue
            relative = name.removeprefix(ASSETS)
            if relative.startswith("models/") and relative.endswith(".json"):
                json_file(own / relative, model_data(json.loads(release.read(name))))
            elif relative.startswith("textures/") and not name.endswith("/") and not (own / relative).exists():
                output = own / relative
                output.parent.mkdir(parents=True, exist_ok=True)
                output.write_bytes(release.read(name))
            elif relative.startswith("lang/") and relative.endswith(".lang"):
                locale = Path(relative).stem.lower()
                translations = {}
                for line in release.read(name).decode("utf-8-sig").splitlines():
                    key, sep, value = line.partition("=")
                    if not sep or key.startswith("#"):
                        continue
                    key = key.strip().removesuffix(".name")
                    translations[key] = value
                    if key.startswith("tile."):
                        translations["block." + key[5:]] = value
                json_file(own / "lang" / f"{locale}.json", translations)
            elif relative.startswith("recipes/") and relative.endswith(".json") and not Path(relative).name.startswith("_"):
                raw = json.loads(release.read(name))
                if raw["result"]["item"].removeprefix("modernmetals:") in items:
                    json_file(data / relative, translate_recipe(raw, tags))

    for folder in ("main", "generated"):
        source = args.base_metals / f"src/{folder}/resources/assets/basemetals"
        for path in (source / "blockstates").glob("copper*.json"):
            for material in materials:
                name = path.stem.replace("copper", material)
                if name in blocks and not name.endswith("_ore"):
                    blockstate = json.loads(modern_model(path.read_text(encoding="utf-8"), material))
                    json_file(own / "blockstates" / f"{name}.json", blockstate)
    # Convert names used by the state files that had different names in the 1.12 pack.
    for state_path in (own / "blockstates").glob("*.json"):
        if state_path.stem not in blocks:
            continue
        if state_path.stem.endswith("_ore"):
            name = state_path.stem
            json_file(state_path, {"variants": {"": {"model": f"{MOD}:block/{name}"}}})
            if name in ALLOY_ORES:
                json_file(own / "models/block" / f"{name}.json", {
                    "parent": f"{MOD}:block/{name.removesuffix('_ore')}_block"
                })
                json_file(own / "models/item" / f"{name}.json", {"parent": f"{MOD}:block/{name}"})
        state = state_path.read_text(encoding="utf-8")
        for model in set(re.findall(r'"model": "modernmetals:block/([^"]+)"', state)):
            target = own / "models/block" / f"{model}.json"
            if target.exists():
                continue
            material = next((name for name in materials if model.startswith(name + "_")), None)
            if material is None:
                continue
            # The 1.13 slab uses a single state container for bottom, top and double.
            model_aliases = {
                material + "_slab": "half_slab_" + material,
                material + "_slab_top": "upper_slab_" + material,
                material + "_lever_off": material + "_lever",
            }
            if model in model_aliases and (own / "models/block" / (model_aliases[model] + ".json")).exists():
                json_file(target, {"parent": f"{MOD}:block/{model_aliases[model]}"})
                continue
            copper_name = "copper" + model[len(material):]
            for folder in ("main", "generated"):
                donor = args.base_metals / f"src/{folder}/resources/assets/basemetals/models/block/{copper_name}.json"
                if donor.exists():
                    value = json.loads(modern_model(donor.read_text(encoding="utf-8"), material))
                    if "textures" in value:
                        value["textures"] = {k: v.replace("modernmetals:block/", "modernmetals:blocks/")
                                             for k, v in value["textures"].items()}
                    json_file(target, value)
                    break

    for material in materials:
        for form in ("block", "plate", "bars", "door", "trapdoor", "button", "slab", "lever",
                     "pressure_plate", "stairs", "wall", "ore"):
            name = f"{material}_{form}"
            if name not in blocks:
                continue
            if form == "ore":
                tags.setdefault(f"ores/{material}", set()).add(f"{MOD}:{name}")
                tag(generated, "blocks", f"ores/{material}", {f"{MOD}:{name}"})
        for item in (f"{material}_{form}" for form in ("powder", "ingot", "ore", "blend")):
            if item not in items or item.endswith("_ingot") or item in ALLOY_ORES:
                continue
            json_file(data / f"recipes/smelting/{item}.json", {
                "type": "minecraft:smelting", "ingredient": {"item": f"{MOD}:{item}"},
                "result": f"{MOD}:{material}_ingot", "experience": 0.45, "cookingtime": 200,
            })
        for form, output in (("ore", "powder"), ("ingot", "powder"), ("nugget", "smallpowder")):
            name = f"{material}_{form}"
            if name not in items or name in ALLOY_ORES:
                continue
            json_file(data / f"recipes/crushing/{name}.json", {
                "type": "modernmetals:crushing", "ingredient": {"item": f"{MOD}:{name}"},
                "result": {"item": f"{MOD}:{material}_{output}", "count": 2 if form == "ore" else 1},
            })

    tags["ores/chrome"] = tags["ores/chromium"]
    for kind in ("ingots", "nuggets", "dusts", "tiny_dusts", "storage_blocks", "rods", "gears"):
        if f"{kind}/chromium" in tags:
            tags[f"{kind}/chrome"] = tags[f"{kind}/chromium"]
    tag(generated, "blocks", "ores/chrome", {"modernmetals:chromium_ore"})
    for path, values in tags.items():
        unknown = values.difference({f"{MOD}:{item}" for item in items})
        if unknown:
            raise ValueError(f"Unknown Modern Metals tag entries for {path}: {unknown}")
        tag(generated, "items", path, values)
    for material in sorted(BASE_DUSTS):
        tag(generated, "items", f"dusts/{material}", set())


if __name__ == "__main__":
    main()

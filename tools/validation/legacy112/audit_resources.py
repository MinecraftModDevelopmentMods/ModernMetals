"""Parse the JSON resources shipped to Minecraft."""
import json
import sys
from pathlib import Path

root = Path(sys.argv[1])
files = sorted(set(root.rglob("*.json")) | set(root.rglob("*.mcmeta")) | {root / "mcmod.info"})
for file in files:
    try:
        json.loads(file.read_text(encoding="utf-8-sig"))
    except (OSError, ValueError) as exc:
        raise SystemExit(f"{file}: {exc}") from exc

assets = root / "assets/modernmetals"
assert json.loads((root / "pack.mcmeta").read_text(encoding="utf-8"))["pack"]["pack_format"] == 3
for file in sorted((assets / "models").rglob("*.json")):
    model = json.loads(file.read_text(encoding="utf-8"))
    parent = model.get("parent", "")
    assert not parent.startswith("basemetals:"), f"{file}: requires the optional Base Metals mod"
    if parent.startswith("modernmetals:"):
        model_path = parent.split(":", 1)[1]
        assert (assets / "models" / (model_path + ".json")).is_file(), (file, parent)

for locale in ("de_de", "en_us", "ru_ru", "zh_cn"):
    lang = (assets / "lang" / (locale + ".lang")).read_text(encoding="utf-8-sig")
    keys = {line.split("=", 1)[0] for line in lang.splitlines() if "=" in line and not line.startswith("#")}
    assert {"config.modernmetals.title", "config.modernmetals.inherited_mode",
            "config.modernmetals.content_mode", "config.modernmetals.content_mode.high_fantasy",
            "config.modernmetals.content_mode.low_fantasy"} <= keys, locale
print(f"Verified {len(files)} packaged JSON resources")

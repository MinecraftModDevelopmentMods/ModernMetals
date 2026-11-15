"""Recolour the checked-in Base Metals/vanilla pixel masks; requires Pillow.

Run explicitly after a palette change. Normal builds consume the saved PNGs.
The user selected deterministic recolouring rather than new generated artwork.
"""
from pathlib import Path
import json
from PIL import Image

ROOT = Path(__file__).resolve().parents[1]
ASSETS = ROOT / 'src/main/resources/assets/modernmetals/textures'


def recolour(template, colour):
    image = Image.open(template).convert('RGBA')
    assert image.size == (16, 16)
    rgb = ((colour >> 16) & 255, (colour >> 8) & 255, colour & 255)
    pixels = []
    for red, green, blue, alpha in image.getdata():
        light = (0.2126 * red + 0.7152 * green + 0.0722 * blue) / 255
        shade = 0.24 + 1.12 * light
        highlight = max(0, light - 0.68) * 0.45
        pixels.append(tuple(min(255, round(channel * shade + 255 * highlight)) for channel in rgb) + (alpha,))
    image.putdata(pixels)
    return image


if __name__ == '__main__':
    for material in sorted((ROOT / 'src/main/resources/mmdlib/material').glob('*.json')):
        data = json.loads(material.read_text())
        if 'modernmetals:raw' not in data['items'].split():
            continue
        for kind, folder, suffix in [('item', 'item', '_raw'), ('block', 'block', '_raw_block')]:
            image = recolour(ROOT / ('tools/textures/raw_' + kind + '_template.png'), data['color'])
            target = ASSETS / folder / (material.stem + suffix + '.png')
            target.parent.mkdir(parents=True, exist_ok=True)
            image.save(target, optimize=False, compress_level=9)
        print(material.stem)

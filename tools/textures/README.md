# Raw texture masks

The raw items reuse Base Metals' existing pixel shapes. Both masks are recoloured
deterministically in code.

`raw_item_template.png` is the tin raw-item shape from the Base Metals 1.21.1
NeoForge checkout. `raw_block_template.png` is Minecraft 1.21.1's raw iron block
texture, extracted from the vanilla client assets. They retain their original
16 by 16 pixel layouts and alpha. The material colours come from this mod's
MMDLib definitions.

Run `python tools/generate_raw_textures.py` with Pillow installed to regenerate
the 17 item and 17 block PNGs. These are saved product resources; the ordinary
build does not regenerate textures or require Pillow. Existing ore overlays
are unchanged by this script.

# Ore overlays in Forge 1.10.2

Ore models use Minecraft's stone texture underneath KiriCattus's transparent
ore art. A resource pack that replaces stone also changes the stone part of
the ore. Thorium still uses its original full texture.

Forge 1.10 predates Minecraft's data generator. Run `gradlew generateOreModels`
to generate the models without starting the game. The normal build runs it
automatically, and Eclipse uses the processed resources. The inputs are the
existing ore blockstates and PNGs in
`src/main/resources/assets/modernmetals/textures/blocks/ore_overlays/`.
Generated models go to `build/generated/ore-models`; nothing is written to
`src`. Existing blockstate and item model IDs still point to the same block
model names.

The generated models use the two cubes from Base Metals' generator. The outer
cube sits 0.05 model units above the host faces so they do not fight over the
same depth. On Forge 1.10 both cubes render in the cutout layer with mipmaps,
so the transparent pixels leave the host visible. The ore blocks still inherit
their mining and drop behaviour from Base Metals.

The 20 overlay files come from [KiriCattus's art commit](https://github.com/MinecraftModDevelopmentMods/ModernMetals/commit/6c892309ab9c2cd19371a8a65ad59b250a647216).
Nineteen are used by registered ores, including the three alloy ores restored
for worlds saved by 0.11.0. The aluminum brass overlay is available as an asset.
OreSpawn still uses the same 17 rules, with titanium disabled by default.

`verifyProjectResources` checks the generated resources and texture references.
The packaged client probe checks the placed and inventory models, face offsets
and render layer. Add `--ore-host-pack` on its reload run to test a replacement
stone texture and capture the ore display. See the
[validation guide](../tools/validation/legacy110/README.md).

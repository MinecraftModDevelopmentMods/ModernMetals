# Minecraft 1.15.2 port

The baseline is released Modern Metals 3.0.0.114041, commit a14f7d16.
Forge 31.2.57, MCP snapshot 20200514-1.15.1/config 20200515.085601 and Java 8
match the sibling ports. Gradle uses Java 17 and Mavenizer Java 25.

The port keeps all material and registry identities, ore provider rules, alloy
formulas, recipe IDs and discovery. No new flattening is needed after 1.13.
Pickaxes still drop ore blocks; crackhammers produce powders. Titanium remains
obtainable through rutile and magnesium, with direct ore generation off by default.

Client setup assigns RenderTypeLookup layers for overlay ores, bars, doors,
trapdoors and molten fluids. Historical solid ores stay solid. Arrow and bolt
renderers register by EntityType and use vanilla's batched renderer. Common
classes contain no client rendering-layer overrides. Resource pack format is 5.
The existing world-data hook is queued after parallel loading; its 1.15 coremod
descriptors and copied-world behaviour are checked separately from compilation.

OreSpawn 4.1.7.115021 and optional Base Metals 3.0.3.115021 are published pins.
The published Base Metals build includes the ore overlay, bars and cutout fixes. The sibling profiles use the local 1.15.2 Base Gems and Base Minerals jars.

Both Fantasy modes, coal ownership, cross-mod alloys, shared crushing, mining,
config factories, baked bars/ore models and world reopen are qualification targets.
All original worlds stay outside disposable test directories. Manual visual and
ordinary gameplay acceptance remain separate from the automated checks.

References: [Forge porting notes](https://docs.minecraftforge.net/en/1.15.x/legacy/porting/),
[the rendering primer](https://docs.neoforged.net/primer/docs/1.15/),
[Minecraft 1.15.2 release notes](https://www.minecraft.net/en-us/article/minecraft-java-edition-1-15-2)
and the exact mapped Forge 31.2.57 classes.

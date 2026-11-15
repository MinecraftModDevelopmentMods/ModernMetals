# Modern Metals for Minecraft 1.21.1

Modern Metals adds industrial metals and alloys, including aluminum, chromium,
osmium and titanium. It adds their ores and molten forms, equipment, building
blocks and Crack Hammer processing.

For Modern Metals 4.0.0.121012, install NeoForge 21.1.265 or later,
[MMDLib 2.0.2][mmdlib] or later and [OreSpawn 4.1.1][orespawn]. Modern Metals also
works alongside [Base Metals][basemetals], which supplies the copper, nickel,
steel and zinc dusts used in some alloy blends.

The Modern Metals settings are in `config/modernmetals-common.toml` and on the
settings screen in the Mods list or OreSpawn's mod directory. Low Fantasy removes recipes for implausible
equipment and structures. Existing items remain usable. When Base Metals is
installed, Modern Metals uses Base Metals' Fantasy Level. Restart Minecraft
after changing the setting.

Titanium is made from rutile and magnesium by default. The optional titanium
ore rule can be enabled in OreSpawn.

Mining an ore with a suitable pickaxe drops raw material. Fortune increases the
raw drops; Silk Touch keeps the ore block. A Crack Hammer crushes an ore into
two dust, without a Fortune bonus. Raw material crushes into one dust, or can
be smelted or blasted directly. Nine raw pieces make a storage block.

Equipment and components can be recycled in a furnace. Recycling is limited
to the metal used to make them, including the four rods, four gears and three
plates produced by their recipes. A matching plate repairs armor or a shield
in a crafting grid and preserves its enchantments and other saved data.

The recipe books reveal crafting, furnace and blast-furnace recipes when you
collect their ingredients. Dust already in your inventory is checked when you
join or reload data. Earned recipes return when you switch back to an eligible
Fantasy Level. Galvanized and stainless steel blends also accept whole steel
blend. Chromium dust and the Chrome tag work in the same alloy recipes.

Modern Metals supplies coal-dust processing when used alone. Base Metals owns
those recipes when both mods are installed. Coal dust must be compressed into
a coal block to recover usable coal; the dust itself is not furnace fuel.

Flattened 1.13.2 saves retain their material IDs, old molten buckets and fluids,
legacy alloy ores, wall connections, arrows and bolts. Always test upgrades on
a backup. This port does not directly convert numeric 1.10–1.12 registries;
upgrade those saves through the released 1.13.2 version first. OreSpawn's saved
rule IDs are retained and retrogen remains off.

See [the testing guide](docs/MANUAL_TESTING.md) for the standalone and Base
Metals Eclipse profiles and the remaining manual checks.

[mmdlib]: https://www.curseforge.com/minecraft/mc-mods/mmdlib
[orespawn]: https://www.curseforge.com/minecraft/mc-mods/mmd-orespawn
[basemetals]: https://www.curseforge.com/minecraft/mc-mods/base-metals

## Building

Building needs Java 21 and Node.js 22 or later. Run `./gradlew build`; the mod, sources and Javadocs
are written to `build/libs`. `./gradlew runClient` and `./gradlew runServer`
start a development game. `runClient` uses `run/standalone/`, while
`runClientWithBase` uses `run/with-base-metals/` and the checksum-verified
published Base Metals 4.0.1 jar. These profiles have separate settings and saves.

`generateProcessingData` builds the processing supplements, independent mining
tags, translations and recipe advancements. The raw textures are checked in;
the optional Pillow script in `tools/` reproduces them from the documented pixel
masks. Normal builds do not need Python or Pillow.

Each material is defined in `src/main/resources/mmdlib/material/`. MMDLib reads
these at startup and registers the material's items, blocks and molten fluid.
The models, recipes, loot tables and tags for those items and blocks are in
`src/main/resources`.

`test` loads Minecraft's real recipe codecs and tags. `runGameTest` and
`runGameTestLow` exercise world behavior; add `-PwithBaseMetals=true` to test
with published Base Metals, or `-PwithContributors=true` to `test` for optional
third-party ingredient tags. Test helpers are excluded from the product jars
and ordinary client/server profiles.

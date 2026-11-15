# Modern Metals for Minecraft 1.21.1

Modern Metals adds industrial metals and alloys, including aluminum, chromium,
osmium and titanium. It adds their ores and molten forms, equipment, building
blocks and Crack Hammer processing.

For Modern Metals 4.0.0.121012, install NeoForge 21.1.265 or later,
[MMDLib 2.0.2][mmdlib] or later and [OreSpawn 4][orespawn]. Modern Metals also
works alongside [Base Metals][basemetals], which supplies the copper, nickel,
steel and zinc dusts used in some alloy blends.

The Modern Metals settings are in `config/modernmetals-common.toml` and on the
settings screen in the Mods list. Low Fantasy removes recipes for implausible
equipment and structures. Existing items remain usable. When Base Metals is
installed, Modern Metals uses Base Metals' Fantasy Level. Restart Minecraft
after changing the setting.

Titanium is made from rutile and magnesium by default. The optional titanium
ore rule can be enabled in OreSpawn.

[mmdlib]: https://www.curseforge.com/minecraft/mc-mods/mmdlib
[orespawn]: https://www.curseforge.com/minecraft/mc-mods/mmd-orespawn
[basemetals]: https://www.curseforge.com/minecraft/mc-mods/base-metals

## Building

Building needs Java 21. Run `./gradlew build`; the mod, sources and Javadocs
are written to `build/libs`. `./gradlew runClient` and `./gradlew runServer`
start a development game in `run/`.

Each material is defined in `src/main/resources/mmdlib/material/`. MMDLib reads
these at startup and registers the material's items, blocks and molten fluid.
The models, recipes, loot tables and tags for those items and blocks are in
`src/main/resources`.

To publish a release, push a tag such as `4.0.0.121012`. The tag is used as the
mod version.

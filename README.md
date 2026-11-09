# Modern Metals for Minecraft 1.11.2

Modern Metals adds industrial metals and alloys to Base Metals, with ores,
building blocks, tools, armour and molten metals. This branch is for Minecraft
**1.11.2**, version **2.5.2.111021**.

## Installation

Use Java **8** and Forge **13.20.1.2588 or newer for Minecraft 1.11.2**.
Put these three jars in `mods/` on both the client and server.

- Modern Metals `2.5.2.111021`.
- [Base Metals 2.5.1.111021](https://www.curseforge.com/minecraft/mc-mods/base-metals/files/9526386), supported range `[2.5.1.111021,2.6.0)`.
- [MMD OreSpawn 4.1.6.111021](https://www.curseforge.com/minecraft/mc-mods/mmd-orespawn/files/9524249), supported range `[4.1.6.111021,5.0.0)`.

Remove older copies first. Use the normal mod jars rather than the sources,
Javadocs or development jars. This version uses Base Metals' shared library, so
you don't need a separate MMDLib or Mineralogy jar.

## Materials

The 21 configurable materials are aluminum, aluminum brass, beryllium, boron,
cadmium, chromium, galvanized steel, iridium, magnesium, manganese, nichrome,
osmium, plutonium, rutile, stainless steel, tantalum, thorium, titanium, tungsten,
uranium and zirconium. Chromium/Chrome, Tungsten/Wolfram and Aluminum/Aluminium
Ore Dictionary names are kept for compatibility.

Boron, beryllium and thorium have ores, molten forms, buckets and processing
recipes. Titanium is still made from **rutile and magnesium**, using
titanium blend and smelting, or molten alloying and casting. Titanium ore generation
is **off by default**. If enabled, it uses the same placement settings as rutile.

## Configuration

Open **Mods -> Modern Metals -> Config**, or use the Modern Metals cog under
**Create World -> OreSpawn -> Mods**. Both save to `config/ModernMetals.cfg`.
Done saves your changes. Escape cancels them. Restart the game or server to apply
them. Keep materials enabled in worlds that use their blocks and items.

**Fantasy Level** defaults to High Fantasy, which keeps the usual recipes. Low
Fantasy restricts unrealistic equipment and building recipes across the materials,
including trades and Tinkers parts. In 1.11 this is a Modern Metals setting and is
independent of Base Metals. See [the fantasy settings](docs/CONTENT_MODE.md) for
the full list of restrictions. A multiplayer server and its clients need the
same Fantasy Level. Use **Villager Trades** to turn off new Modern Metals offers
in either mode.

The config screen is available in English, German, Simplified Chinese and
Russian. Regional English strings are included for Australia, Canada, the UK,
New Zealand and Pirate Speak.

Ore generation is handled by OreSpawn 4. Use its editor to change ore settings.
Existing chunks are left alone and retrogen stays off. See [ore generation and
migration](docs/ORE_GENERATION.md) for defaults, ore groups and older config files.
Ores use KiriCattus's overlays over the stone texture, so stone resource packs
also change the background of the ore. Thorium keeps its original texture.

Modern Metals ores work in the Base Metals Crack Hammer and in the rock
crushers that use the same Base Metals registry. They use the Base Metals crusher
settings, including the powder yield and any ores you disable there.

## Updating an older 1.11.2 world

Keep a backup before opening the world with the new jars. See the [migration
notes](docs/ORE_GENERATION.md) before moving an existing configuration. Modern
Metals resolves the old door, fishing rod, horse armor and molten aluminum brass IDs from 0.11.0.
Placed galvanized steel, nichrome and stainless steel ores still load. Those
three ores have no default OreSpawn rules. Keep the materials enabled when first
opening a world that contains them.

## Development

The build uses ForgeGradle 7 and still produces Java 8 jars. See [building and
Eclipse](docs/BUILDING.md), [integration checks and known limits](tools/validation/legacy111/README.md)
and [release preparation](docs/RELEASING.md).

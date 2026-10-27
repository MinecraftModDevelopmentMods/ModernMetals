# Modern Metals for Minecraft 1.10.2

Modern Metals adds industrial metals and alloys to Base Metals, with ores,
building blocks, tools, armour and molten metals. This branch is for Minecraft
**1.10.2**, version **2.5.1.110021**.

## Installation

Use Java **8** and Forge **12.18.3.2511 or newer for Minecraft 1.10.2**.
Put these three jars in `mods/` on both the client and server:

- Modern Metals `2.5.1.110021`.
- [Base Metals 2.5.1.110021](https://www.curseforge.com/minecraft/mc-mods/base-metals/files/9280029), supported range `[2.5.1.110021,2.6.0)`.
- [MMD OreSpawn 4.1.2.110021](https://www.curseforge.com/minecraft/mc-mods/mmd-orespawn/files/9276430), supported range `[4.1.2.110021,5.0.0)`.

Remove older copies first. Use the normal mod jars rather than the sources,
Javadocs or development jars. This version uses Base Metals' shared library, so
you don't need a separate MMDLib or Mineralogy jar.

## Materials

The 21 configurable materials are aluminum, aluminum brass, beryllium, boron,
cadmium, chromium, galvanized steel, iridium, magnesium, manganese, nichrome,
osmium, plutonium, rutile, stainless steel, tantalum, thorium, titanium, tungsten,
uranium and zirconium. Chromium/Chrome, Tungsten/Wolfram and Aluminum/Aluminium
Ore Dictionary names are kept for compatibility.

Boron, beryllium and thorium now have their missing materials, molten forms and
processing recipes. Titanium is still made from **rutile and magnesium**, using
titanium blend and smelting, or molten alloying and casting. Titanium ore generation
is **off by default**. If enabled, it uses the same placement settings as rutile.

## Configuration

Open **Mods -> Modern Metals -> Config**, or choose Modern Metals from OreSpawn's
**Mods** list. Both open the same screen and save to `config/ModernMetals.cfg`.
Done saves your changes; Escape cancels them. Restart the game or server to apply
them. Keep materials enabled in worlds that use their blocks and items.

**Fantasy Level** defaults to High Fantasy, which keeps the usual recipes. Low
Fantasy restricts unrealistic equipment and building recipes across the materials,
including trades and Tinkers parts. In 1.10 this is a Modern Metals setting and is
independent of Base Metals. See [the fantasy settings](docs/CONTENT_MODE.md) for
the full list of restrictions.

Ore generation is handled by OreSpawn 4. Use its editor to change ore settings.
Existing chunks are left alone and retrogen stays off. See [ore generation and
migration](docs/ORE_GENERATION.md) for defaults, ore groups and older config files.

## Development

The build uses ForgeGradle 7 and still produces Java 8 jars. See [building and
Eclipse](docs/BUILDING.md), [integration checks and known limits](tools/validation/legacy110/README.md)
and [release preparation](docs/RELEASING.md).

The 1.11 and 1.12 branches will be updated separately.

# Modern Metals for Minecraft 1.12.2

Modern Metals adds industrial metals and alloys, including aluminum, chromium,
osmium and titanium. It adds their ores and molten forms, equipment, building
blocks and Crack Hammer processing.

Install Forge 14.23.5.2859, [MMDLib 1.0.0-rc2.36][mmdlib] and
[OreSpawn 4.1.3.112021][orespawn]. Modern Metals also works alongside
[Base Metals 2.6.1.112021][basemetals]. Base Metals supplies additional
ingredients for alloys that use its steel, zinc, brass or nickel.

The Modern Metals settings are available from Forge's Mods list and OreSpawn's
Mods list. Fantasy Level comes from a compatible Base Metals installation.
When running Modern Metals by itself, set Fantasy Level in
`config/ModernMetals.cfg` or in the settings screen. Low Fantasy restricts
implausible gear and building recipes. Existing items remain usable. Restart
Minecraft after changing the setting.

Titanium is made from rutile and magnesium by default. The optional titanium
ore rule can be enabled in OreSpawn. Existing chunks keep their ores. OreSpawn
uses its native profile for new chunks and can import supported legacy files.
Back up a world and its configs before upgrading from OreSpawn 3.

[mmdlib]: https://www.curseforge.com/minecraft/mc-mods/mmdlib/files/2712947
[orespawn]: https://maven.moddev.zone/releases/zone/moddev/mc/orespawn/OreSpawn/4.1.3.112021/
[basemetals]: https://maven.moddev.zone/releases/zone/moddev/mc/basemetals/BaseMetals/2.6.1.112021/

## Building

The Gradle wrapper runs on Java 17, ForgeGradle's Mavenizer uses Java 25, and
the game and compiler use Java 8. Run `./gradlew check build
verifyProjectResources writeReleaseChecksums prepareEclipse`. To stage a
local mirror first, run `python gradle/stage-legacy-dependencies.py <directory>`
and pass `-PlegacyDependencyVerificationRepository=<directory>` to Gradle.
The runtime jar, sources and Javadocs are written to `build/libs`.

CI builds from the pinned jars, audits the package and checks disposable Forge
servers. The release workflow takes an explicitly confirmed version, commit
and game jar checksum. The update feed should be advanced after publication.

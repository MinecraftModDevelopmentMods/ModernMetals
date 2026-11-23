# Minecraft 1.14.4 port

The source is the released 1.13.2 Modern Metals tree, f05f7208. Base Metals'
1.14.4 port is an API reference; its older hardness-derived mining tiers
were deliberately not copied over the later Modern Metals material audit.

## Target decisions

- Forge 28.2.26, MCP snapshot 20190719-1.14.3 / config 20190829.143755. Java 8
  remains the compilation and game target. Gradle 9.6.1 and ForgeGradle 7 use
  the same toolchain arrangement as Base Metals.
- Block loot moved to data in 1.14. Generated tables cover every solid block
  and historical alloy ore. Doors drop only their lower half, double slabs
  drop two slabs, and fluids have empty tables. Ore blocks remain pickaxe
  drops; raw ore is a later Minecraft feature.
- Blast furnaces use their own native recipe type. Native metal processing
  gets matching inputs and outputs at half the cooking time. Recycling
  retains the ordinary furnace and its multi-output serializer.
- Recipes use `CraftingInventory`, native `IRecipeType` and registered Forge
  serializers. Shared crushing recipes deserialize through Base Metals'
  serializer when installed, retaining their contribution IDs.
- Forge's typed conditions replace the old reflective tag condition. Empty
  optional tags keep cross-mod alloys absent safely. The five formulas,
  Chrome aliases, direct steel blends and coal ownership are preserved.
- Forge fluid attributes replace the Forge 25 renderer hook. Still and
  flowing textures are animated; buckets retain their material colours.
  Fluid construction and crafting-condition registration remain sequential.
- Smith trades now use the armorer, toolsmith and weaponsmith professions,
  with restocking, experience and Fantasy policy checks.
- Configuration uses the native 1.14 Mods-list factory and OreSpawn child
  factory. Standalone Fantasy Level remains local; Base Metals supplies
  the effective setting when present. Modes stay latched until restart.
- Saved IDs, named states and old item aliases are retained. Legacy numeric
  migration hooks run before vanilla data fixing; completed old chunks use
  1.14's `full` status. Existing terrain guards use primitive chunk-key sets.
- OreSpawn owns generation. The ore-only schema-5 provider keeps its 17 rule
  IDs, material groups, heights and dimension selector. Titanium stays off
  and retrogen stays off. No terrain or fallback generator was added.

References: [Forge's 1.14 porting notes](https://docs.minecraftforge.net/en/1.14.x/legacy/porting1214/),
[Minecraft 1.14.4 notes](https://feedback.minecraft.net/hc/en-us/articles/360030780172-Minecraft-Java-Edition-1-14-4),
the exact mapped Forge/Minecraft sources, and
[OreSpawn 4.1.7.114041](https://github.com/MinecraftModDevelopmentMods/OreSpawn/releases/tag/4.1.7.114041).

## Verification

The build runs resource-generation, startup-order, processing and explicit
mining-tier checks. `runRuntimeProbeServer` and `runProbeClient` are explicit
automated tests, using separate source sets excluded from production jars.
Use `-PwithBaseMetals=true` to test the published Base Metals dependency.
World-upgrade runs accept only disposable copies in a separate probe directory.

Manual acceptance and publication are separate steps. The local verification
receipt records the exact artifacts,
passed and failed attempts, and the copied-world source fingerprints.


The local candidate passed a clean build, all archive and Eclipse checks,
and an exact rebuild comparison of the player, source and Javadoc jars.
The native server checks cover every registered block drop, component yields,
alloy processing, plate repairs, beryllium discovery, smith offers, fluids,
survival hammer mining and all 21 ranged-weapon families. Both Fantasy modes
were checked standalone and alongside the published Base Metals dependency.

Nine new chunks in each vanilla dimension contain the expected Modern Metals
ores only in the Overworld. Enabling titanium adds its ore without changing
rutile's rule. The normal provider keeps titanium and retrogen disabled.

Client checks cover 967 item models, overlay layers and transparency, bucket
colours, configuration factories, saving and reopening an integrated world.
The test waits for the first integrated-server thread to finish before
reopening its save and rejects unexpected error logs.

Representative copied-world checks preserved 302,255 Modern Metals blocks
from 1.10, 199,176 from 1.11, and 289,362 from a 1.12 metadata fixture, together
with their saved stacks. A released 1.13 world and additional fixtures cover
stable block IDs, fluids, historical alloy ores, buckets, and a named,
enchanted, worn hammer. Packaged reloads passed and the original fixture
fingerprints remained unchanged. These checks do not replace backups or
manual acceptance of a particular modpack.

`runPackagedProbeServer` tests the actual player jar; supply
`-PpackagedForgeServerRuntime=<official Forge 28.2.26 server directory>`.
Use `-PprobeDirectory=<disposable copy>` and `-PprobeWorldUpgrade=true` for
upgrade tests. Copies outside `build` or `run/verification` must contain a
`.modernmetals-disposable-test` marker. Test receipts and failed attempts are
kept locally, outside published artifacts.

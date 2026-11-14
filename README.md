# Modern Metals for Minecraft 1.13.2

Modern Metals adds 21 metals and alloys, including boron, beryllium, thorium
and titanium. Crush and smelt ores, blend alloys, or make tools, armour and
building blocks.

## Installation

Use Java 8 and Forge **25.0.223** for Minecraft **1.13.2**. Install Modern
Metals and [OreSpawn 4.1.7.113021](https://www.curseforge.com/minecraft/mc-mods/mmd-orespawn/files/9611746)
on both the client and server. [Base Metals 3.0.3.113021](https://github.com/MinecraftModDevelopmentMods/BaseMetals/releases/tag/3.0.3.113021)
is optional for new worlds. Its powders supply the ingredients for several
alloys, and its crusher recipes work alongside Modern Metals recipes.

Use Base Metals 3.0.3.113021 or a later compatible 1.13.2 build when installing
the two together. That release includes the shared processing and mining fixes.

OreSpawn places the ores. Titanium ore is off by default. You can still make
titanium from rutile and magnesium. Existing chunks keep their ores.

Open **Mods → Modern Metals → Config**, or choose Modern Metals from the
OreSpawn settings screen. High Fantasy allows the full set of recipes. Low
Fantasy restricts equipment and decorative recipes by material. Modern
Metals uses Base Metals' setting when Base Metals is installed. Restart the
game or server after changing Fantasy Level, and use the same mode on both
ends of a multiplayer connection.

## Processing and recipe discovery

Pick up a material to discover its crafting recipes. Powders, small powders
and blends reveal their processing recipes, including entries in the furnace
book. Existing inventories are checked when joining a world and after a data
pack reload.

Nine coal dust make a vanilla coal block, which you can craft back into nine
coal. Crackhammers also crush coal and coal blocks. Base Metals supplies those
shared recipes when installed.

Galvanized steel and stainless steel accept steel blend directly in place of
steel dust. One whole blend replaces one dust, keeping the usual output.

Small powders and small blends smelt into nuggets. Metal blocks crush into
nine powders. You can recycle equipment and components in a furnace, and
repair armour and shields with a matching plate. Named and enchanted equipment
keeps its saved data when repaired.

See [processing and alloy recipes](docs/PROCESSING.md) for the five blend
formulas and the forms retained for optional integrations.

## Crushing recipes for add-ons

Crackhammers need the same mining level as a pickaxe. Galvanized steel,
stainless steel and titanium have iron-level mining and can mine Base Gems'
jasper. Ores and storage blocks in the shared Forge block tags
use the normal hammer mining speed when the hammer is strong enough.

[The material audit](docs/MINING_LEVELS.md) lists the reviewed tool, solid-metal
and ore requirements for every metal. Tool capability accounts for material
type rather than treating one hardness score as a complete tool specification.
New smith offers also follow the reviewed tiers through the existing pricing formula.

On 1.13.2, a mod or data pack can add a recipe under its own
`data/<namespace>/recipes/` directory with type `modernmetals:crushing`.
Use a Minecraft `ingredient` and a `result` object with the output `item`
and `count`, just as with `basemetals:crushing`. If Modern Metals is optional,
use a `forge:mod_loaded` condition for `modernmetals`.

Read the recipes from the world's recipe manager. With Modern Metals alone
they use `CrushingRecipe.TYPE`. With Base Metals installed they keep their
recipe IDs and use the Base Metals type, so Base Metals tools and crushers
can find them too.

## Upgrading a world

Keep the original world intact and open a separate copy in 1.13.2. Keep the
original Forge registry data in `level.dat`. Minecraft 1.13.2 uses block
states and named item IDs, so the migration needs the old world’s saved
numeric IDs to find the right blocks. Install compatible 1.13.2 versions of
the other mods that supplied blocks or items to the world.
[World migration](docs/MIGRATION.md)

## Building

Gradle uses Java 17, Mavenizer uses Java 25, and the mod compiles and runs
on Java 8. Run `gradlew build genEclipseRuns eclipse` from this checkout.
The build checks the OreSpawn jar against the published SHA-256. To stage a
local copy, run `bash gradle/stage-orespawn-release.sh <directory>` and pass
`-PdependencyMirror=<directory>` to Gradle.

In Eclipse, use **Gradle → Refresh Gradle Project** to rebuild the
development output and regenerate the launches. Run `runClient.launch`
from the project.

To test add-ons from Eclipse, stage their 1.13.2 `*-deobf.jar` files from
`build/libs-dev` in `run/compatibility-jars` as `basemetals-dev.jar`,
`basegems-dev.jar` and `baseminerals-dev.jar`. Run
`gradlew prepareCompatibilityLaunches` or refresh the Gradle project once
the jars are staged. In **Run → Run Configurations → Java Application**,
choose a **Modern Metals - …** launch. Each combination has its own saves,
config and mods in `run/compatibility`.

For a local OreSpawn fix, stage its published-format jar as
`run/compatibility-jars/orespawn-development.jar` and put its SHA-256 in
`orespawn-development.sha256` alongside it. Enable `-PuseDevelopmentOreSpawn=true`
when generating the client, server and combination launches. The development engine must
keep the pinned version and public API. Compilation and release dependency
checks use the published jar. Normal launches use the published runtime even
if the old local files are still present.

The [changelog](CHANGELOG.txt) and [migration checks](docs/MIGRATION.md)
cover the differences from 1.12.2. The port uses artwork and code from
Modern Metals and the Forge 25 Base Metals port. See [artwork credits](LICENSE-ART.txt).

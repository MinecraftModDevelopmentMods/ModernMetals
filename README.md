# Modern Metals for Minecraft 1.15.2

Modern Metals adds 21 metals and alloys, including boron, beryllium, thorium
and titanium. Crush and smelt ores, blend alloys, or make tools, armour and
building blocks.

## Installation

Use Java 8 and Forge **31.2.57** for Minecraft **1.15.2**. Install Modern
Metals and [OreSpawn 4.1.7.115021](https://github.com/MinecraftModDevelopmentMods/OreSpawn/releases/tag/4.1.7.115021)
on both the client and server. [Base Metals 3.0.3.115021](https://github.com/MinecraftModDevelopmentMods/BaseMetals/releases/tag/3.0.3.115021)
is optional. Its copper, steel, zinc and nickel powders supply ingredients
for several alloys. Modern Metals contributes its crushing recipes to Base
Metals when both are installed.

OreSpawn places the ores. Titanium ore is off by default; rutile and magnesium
still make titanium. Retrogen is off. This version follows vanilla 1.15
mining: pickaxes drop ore blocks, and crackhammers produce powders.

Open **Mods → Modern Metals → Config**, or choose Modern Metals from the
OreSpawn settings screen. High Fantasy allows the full set of recipes. Low
Fantasy restricts equipment and decorative recipes by material. Modern
Metals uses Base Metals' setting when Base Metals is installed. Restart after
changing Fantasy Level and use the same mode on the client and server.

## Processing and discovery

Pick up a material to discover its crafting recipes. Powders, small powders
and blends also reveal their furnace and blast-furnace recipes. Existing
inventories are checked on login and after a data pack reload.

Nine coal dust make a vanilla coal block, which crafts back into nine coal.
Crackhammers also crush coal and coal blocks. Base Metals owns these shared
recipes when installed.

Galvanized steel and stainless steel accept steel blend directly in place
of steel dust. One whole blend replaces one dust, keeping the usual output.
The five alloy formulas and their yields are unchanged.

Component recipes give four rods, four gears or three plates. Recycling
follows those crafting quantities, rounding down where a fraction cannot be
returned. Small powders and small blends smelt into nuggets; metal blocks
crush into nine powders. Armour and shields can be repaired with a matching
plate while keeping names, enchantments and other saved data.

See [processing and alloy recipes](docs/PROCESSING.md) and the
[material mining audit](docs/MINING_LEVELS.md). Galvanized steel, stainless
steel and titanium keep iron-level mining. Molten metal burns on contact.

## Crushing recipes for add-ons

Add a recipe under `data/<namespace>/recipes/` with type
`modernmetals:crushing`, a Minecraft `ingredient`, and a `result` object
containing `item` and `count`. If Modern Metals is optional, use a
`forge:mod_loaded` condition.

Read loaded recipes from the world's recipe manager. Standalone recipes use
`CrushingRecipe.TYPE`. With Base Metals installed, they keep their IDs and
use `basemetals:crushing`, so its tools and machines can find them too.

## Existing worlds

Open a complete copy of the world and keep the original outside the test
instance. Install compatible versions of every mod that supplied saved
blocks or items. Do not remove an old world's Forge registry snapshot.
See [world migration](docs/MIGRATION.md) for the legacy numeric conversion
and the [1.15 port notes](docs/PORTING_115.md) for the port's checks.

## Building and Eclipse

Gradle uses Java 17, Mavenizer uses Java 25, and compilation and Minecraft use
Java 8. Node.js runs the data generators and resource checks.

Run `gradlew build verify115ReleaseArtifacts genEclipseRuns eclipse`.
The build verifies the published OreSpawn jar's SHA-256. Eclipse's compatibility
profiles also verify the sibling jars before staging their mapped versions.

Import this inner project into its own Eclipse workspace, then use
**Gradle → Refresh Gradle Project**. With the local sibling pins set up below,
Run Configurations lists eight clients under **Modern Metals - …**:

- Standalone
- Base Metals
- Base Gems
- Base Minerals
- Base Metals + Base Gems
- Base Metals + Base Minerals
- Base Gems + Base Minerals
- All Add-ons

Each includes Modern Metals and the published OreSpawn dependency. The label
lists the additional mods. Each combination has its own saves and config.
Standalone keeps the existing `run` folder; Base Metals keeps
`run/with-basemetals`. The other six use separate folders under
`run/compatibility`. The older `runClient.launch` and
`runClientWithBaseMetals.launch` names still work.

Automated probe launches use disposable saves and exit when finished. They
are separate from the manual profiles and are excluded from the player,
source and Javadoc jars.

See the
[changelog](CHANGELOG.txt) and [artwork credits](LICENSE-ART.txt).

## Testing a local Base Metals candidate

Build the sibling Base Metals 1.14 checkout, then run
`gradlew pinLocalBaseMetals` here, followed by `gradlew eclipse`. The ignored
`local-basemetals.json` records the exact player and mapped jar hashes.
`runClientWithBaseMetals.launch` then uses that candidate; the standalone
profile still runs without Base Metals. A Gradle refresh keeps the local pin.

After rebuilding Base Metals, run the pin task again. A stale hash stops the
profile refresh. Use `-PuseLocalBaseMetals=false` to test the published jar.
Release builds use published dependencies.

For Base Gems and Base Minerals, build their sibling 1.15 checkouts first, then
run `gradlew pinLocalSiblingMods` here. Run `gradlew eclipse` as a separate
command afterwards. The ignored `local-sibling-mods.json` records both jars'
paths and hashes for each mod. Regenerate these pins after rebuilding either
sibling; a stale hash stops the refresh. Without local sibling pins, a clean
checkout generates the original standalone and Base Metals profiles.

Profile refreshes check the exact sibling selection and preserve saves,
config and unrelated mods. Replaced sibling jars are retained under
`.gradle/retired-compatibility-jars`. `gradlew verifyCompatibilityLaunches`
checks the generated profiles and staged hashes.

Bars now use vanilla connection geometry for all materials. The ore overlays
keep the 0.05 pixel separation used by the later fixes.

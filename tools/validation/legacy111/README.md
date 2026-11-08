# Modern Metals 1.11 qualification

Tests and probes are isolated from release jars. All game runs use disposable
directories and worlds. The packaged suite fingerprints its source fixture world
and confirms that imports/reloads do not rewrite it.

## Automated coverage

- **JUnit.** Checks default-enabled materials, historical ore conversion, missing
  blocks, optional titanium, empty geology declarations, namespace linkage and
  the Low Fantasy matrix for all 21 materials and product families.
- **Resources.** Parse every JSON/mcmeta/mod-info file, validate provider schema 5
  from the pinned production OreSpawn jar, check material plate assets/GUI labels
  and registered material model references/parents, and parse all workflows with
  full action pins. Historical unregistered silicon item placeholders are retained
  and parsed.
- **Installed server.** Checks materials, items, storage, fluids, buckets and
  five alloy chains. Covers aliases, statistics, named stacks, equipment and NBT.
  Exercises ore and material switches, optional titanium, dimension boundaries,
  legacy imports, saved overrides, later chunks and Low Fantasy with Tinkers.
- **Optional mods.** Tinkers 2.7.1.34, its matching Mantle, IC2
  2.7.81-ex111 and Mekanism 9.3.5. Tests cover molten
  forms, alloys, casting, actual tool construction/repair and processing recipes.
- **Dependency rejection.** missing/old Base Metals and OreSpawn fail with explicit
  version requirements before `world/level.dat` exists. Historical fixture
  downloads are checksum verified.
- **Client.** Checks the Forge configuration factory and the OreSpawn Mods entry, the High/Low Fantasy selector, translated labels,
  scrolling, Escape/Done, saved restart-required switches, unchanged live registries, baked models,
  creative tabs, achievement labels and integrated-world fresh/reload checks.

`packagedForgeRuntimeTest` runs 18 world scenarios plus five dependency rejection
cases. See [BUILDING](../../../docs/BUILDING.md) for setup. The server template
contains Forge 1.11.2-13.20.1.2588, vanilla server and libraries, without mods,
configuration or worlds. Do not point a test at an active installation.

## Client commands

`runtimeProbeJar` builds the development probe. `packagedRuntimeProbeJar` builds
the installed probe. They must never be distributed as part of the mod.

```text
python run_eclipse_profile.py <runClient.launch> <java8> <disposable directory> first --probe <development probe> --production-output <disposable merged production output>
python run_eclipse_profile.py <runClient.launch> <java8> <same directory> reload --probe <same probe> --production-output <same output>
```

Merge `build/classes/java/main` and `build/resources/main` into that disposable
output first. Keep the editor's `bin/main` unchanged during qualification.

On Windows, `run_packaged_client.py` launches the installed jar against the
unmodified obfuscated Minecraft client and Forge universal jar. Supply `--java`,
`--cache`, `--installer`, `--template`, `--output`, `--mirror` and `--phase first|reload`.
It verifies the Forge installer and Minecraft client hashes, stages verified
Windows natives in the disposable game directory, and isolates the universal jar from the server jar named
in its manifest. Every ordinary installed mod is copied into the disposable
directory with the packaged game classpath. Inspect the generated
client reports and screenshots separately from manual acceptance.
The matching Mojang asset index and every referenced object are checksum verified
in a disposable asset directory. The script copies matching cached objects and
downloads the rest from Mojang. A different branch's shared Gradle asset index is not
overwritten or assumed to match Minecraft 1.11.2.

For the overlay check, run `--phase first` with vanilla textures, then run
`--phase reload --ore-host-pack` against the same disposable world. The probe
checks the baked block and inventory faces and records an ore display screenshot
in each run. Copy the first screenshot before reloading to compare both.
Use `--language de_DE`, `zh_CN` or `ru_RU` to check the actual localized
settings and selector values.

## Multiplayer and original saves

`check_multiplayer_mode.py` uses the same client inputs and a loopback-only
dedicated server. Supply `--server-jar` and `--client-jar` to test the installed
archives. The four default scenarios cover each pair of High and Low Fantasy.
The server log verifies that a rejected client never enters the world.

`check_original_saves.py --java <java8> --template <clean Forge template>
--public-jars <download folder> --candidate <installed jar>
--output <new disposable directory>` verifies the SHA-256 of the original
public jars, creates a 1.11.2 beta4.58 fixture, and checks a separate
upgraded copy on first load and reload. Pass `--mirror` for the staged dependencies. It checks placed blocks, filled
buckets and item stacks with non-default count or damage. It stops on any Forge
prompt to remove missing blocks or items. The source worlds are fingerprinted.

## Limits and separately owned defects

Base Metals 2.5.1.111021 and OreSpawn 4.1.5.111021 are staged locally.
The suite tests OS1 and OS3 import and checks that existing native overrides
and saved world profiles survive migration.

Modern Metals 1.11 owns its `General.contentMode` in `ModernMetals.cfg` and the
Forge configuration screen. `check_content_modes.py --java <java8> --template <clean
Forge template> --output <new disposable directory>` checks defaults, invalid
values, conflicting Base Metals settings, saved-world mode changes and Low Fantasy
with Tinkers, including fresh/reload behavior. It preserves its source fixture.
Core-mod inheritance is reserved for 1.12. See [CONTENT_MODE](../../../docs/CONTENT_MODE.md).

The 1.10 regression matrix supplies the corresponding checks for 1.11.
The optional runtime tests cover Tinkers, Mantle, Mekanism and IC2. Other
integrations have source and API coverage only. The local issue matrix
records these limits and reports lacking exact reproduction evidence. Radiation,
new alloys/abilities and Construct's Armory are outside this modernization.

Hosted CI/CodeQL, manual graphical acceptance, real-world migration, multiplayer
pack qualification and publication remain separate steps. No performance budget
is claimed from successful bounded generation alone.

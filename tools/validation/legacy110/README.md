# Modern Metals 1.10 qualification

Tests and probes are isolated from release jars. All game runs use disposable
directories and worlds. The packaged suite fingerprints its source fixture world
and confirms that imports/reloads do not rewrite it.

## Automated coverage

- JUnit: default-enabled restored materials, historical ore conversion, missing
  blocks, optional titanium, empty geology declarations, namespace linkage and
  the Low Fantasy matrix for all 21 materials and product families.
- Resources: parse every JSON/mcmeta/mod-info file, validate provider schema 5
  from the pinned production OreSpawn jar, check material plate assets/GUI labels
  and registered material model references/parents, and parse all workflows with
  full action pins. Historical unregistered silicon item placeholders are retained
  and parsed; their absent parents do not define new Modern Metals content.
- Installed server: all enabled materials, items, storage, fluids and buckets;
  five alloy chains; aliases and distinct statistics; fresh/reloaded named stacks,
  NBT and equipment; native rules, disabled rules and material switches; titanium
  generation separately enabled; Nether/End exclusion and an ordinary custom
  dimension; OS1/OS3 imports, native override/saved-profile precedence and later
  chunks; Low Fantasy with and without Tinkers.
- Optional runtime combination: published Tinkers 2.6.5 with Mantle 1.1.5, IC2
  2.6.254-ex110 and Mekanism 9.2.4 with MCMultiPart file 2381408. Tests cover molten
  forms, alloys, casting, actual tool construction/repair and processing recipes.
- Dependency rejection: missing/old Base Metals and OreSpawn fail with explicit
  version requirements before `world/level.dat` exists. Historical fixture
  downloads are checksum verified.
- Client: both config factories, the High/Low Fantasy selector, translated labels,
  scrolling, Escape/Done, saved restart-required switches, unchanged live registries, baked models,
  creative tabs, achievement labels and integrated-world fresh/reload checks.

`packagedForgeRuntimeTest` runs 16 world scenarios plus four dependency rejection
cases. See [BUILDING](../../../docs/BUILDING.md) for setup. The server template
contains Forge 1.10.2-12.18.3.2511, vanilla server and libraries, without mods,
configuration or worlds. Do not point a test at an active installation.

## Client commands

`runtimeProbeJar` builds the development probe; `packagedRuntimeProbeJar` builds
the installed probe. They must never be distributed as part of the mod.

```text
python run_eclipse_profile.py <runClient.launch> <java8> <disposable directory> first --probe <development probe> --production-output <disposable merged production output>
python run_eclipse_profile.py <runClient.launch> <java8> <same directory> reload --probe <same probe> --production-output <same output>
```

Merge `build/classes/java/main` and `build/resources/main` into that disposable
output first. Keep the editor's `bin/main` unchanged during qualification.

On Windows, `run_packaged_client.py` launches the installed jar against the
unmodified obfuscated Minecraft client and Forge universal jar. Supply `--java`,
`--cache`, `--installer`, `--template`, `--output` and `--phase first|reload`.
It verifies the Forge installer and Minecraft client hashes, uses existing verified
libraries/natives, and isolates the universal jar from the server jar named
in its manifest. Every ordinary installed mod is copied into the disposable
directory; no development output enters its classpath. Inspect the generated
client reports and screenshots separately from manual acceptance.
The matching Mojang asset index and every referenced object are checksum verified
in a disposable asset directory. Cached matching objects are copied; missing ones
are downloaded from Mojang. A different branch's shared Gradle asset index is not
overwritten or assumed to match Minecraft 1.10.2.

## Limits and separately owned defects

Public Base Metals 2.5.1.110021 catches errors in its own optional IC2/Mekanism
plugins: IC2 recipe registration runs before its input factory is ready, and the
shared Mekanism helper references the absent `sulphuricAcid` gas. Modern Metals
registers its own processing later and uses Mekanism's direct recipe API. Passing
Modern Metals probes does not mean those Base Metals errors are fixed. No local
Base Metals or OreSpawn source/binary is substituted for the published dependency.

Modern Metals 1.10 owns its `General.contentMode` in `ModernMetals.cfg` and both
configuration screens. `check_content_modes.py --java <java8> --template <clean
Forge template> --output <new disposable directory>` checks defaults, invalid
values, conflicting Base Metals settings, saved-world mode changes and Low Fantasy
with Tinkers, including fresh/reload behavior. It preserves its source fixture.
Core-mod inheritance is reserved for 1.12. See [CONTENT_MODE](../../../docs/CONTENT_MODE.md).

Historical reports specific to 1.11/1.12 receive equivalent 1.10 checks where
possible. Foundry, TechReborn, Ender IO, Thermal Expansion, Building Bricks and
VeinMiner are not all represented in the tested runtime combination; source/API
checks do not establish complete pack compatibility. The local issue matrix
records these limits and reports lacking exact reproduction evidence. Radiation,
new alloys/abilities and Construct's Armory are outside this modernization.

Hosted CI/CodeQL, manual graphical acceptance, real-world migration, multiplayer
pack qualification and publication remain separate steps. No performance budget
is claimed from successful bounded generation alone.

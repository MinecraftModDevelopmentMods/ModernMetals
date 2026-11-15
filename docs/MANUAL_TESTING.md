# Testing the 1.21.1 NeoForge candidate

This candidate has been checked in Eclipse and is ready for review. It has not
been published.

Use Java 21, the Gradle wrapper and Node.js 22 or later. In this workspace,
Gradle and Eclipse use `D:/BaseMetals/.gradle-verify-cache`.

Run `gradlew build eclipse createLaunchScripts` before importing the inner
`ModernMetals` project into Eclipse as an existing Gradle project. Refresh the
Gradle project after changes. The two launch configurations are generated in
`.eclipse/configurations/` and appear under Gradle Project in Eclipse's Run
Configurations. They launch through Gradle so generated resources are current.
The generated command scripts are also available under `build/moddev/`.
The two manual client profiles are:

| Profile | Gradle task | Settings and saves |
| --- | --- | --- |
| Modern Metals alone | `runClient` | `run/standalone/` |
| Modern Metals with published Base Metals 4.0.1 | `runClientWithBase` | `run/with-base-metals/` |

Both use published MMDLib 2.0.2 and OreSpawn 4.1.1. The Base Metals test jar is
downloaded from its GitHub release and checked against its SHA-256. Test probes
are excluded from these two profiles and from the distributable jars.

## Gameplay checks

1. Start a fresh world in each profile. Check raw item/block textures, ore
   overlays, creative tabs and translated names. Thorium retains its existing
   full-block ore texture; the other native ores use overlays.
2. Mine ores with an appropriate pickaxe, Fortune and Silk Touch. Check that
   raw pieces pack/unpack nine to one, smelt/blast to one ingot and crush to one
   dust. A Crack Hammer should crush a whole ore into two dust, without Fortune.
3. Pick up beryllium dust and check the furnace book. Try dust/ingot conversions,
   the crafting book and the blast-furnace book. Rejoin with dust already held.
   Run `/reload` and check the same recipes remain available.
4. Test titanium from rutile and magnesium with titanium generation off.
   With Base Metals, test aluminum brass, galvanized steel, nichrome and
   stainless steel. Use steel dust and whole steel blend separately; Chrome-tagged
   contributors should also work.
5. Craft rods, gears and plates: outputs should be four, four and three.
   Recycle equipment twice into the same furnace output slot and check full
   quantities accumulate. Repair named/enchanted armor and a shield with the
   matching plate and check their saved data.
6. In standalone, crush coal and a coal block. Compress nine dust into a coal
   block, then unpack to nine coal. With Base Metals, check the shared coal
   recipes have one deterministic owner. Coal dust itself should not burn.
7. Check the Mods-list config screen and the Modern Metals entry in OreSpawn.
   Both must return to the correct parent screen. Test molten-metal contact
   with burning enabled and disabled, including Fire Resistance.
8. Restart in Low Fantasy, using Base Metals' setting in the combined profile.
   Plutonium tools should be unavailable while existing items remain usable.
   Restart in High Fantasy and check previously earned recipes return.
9. Save/reopen a disposable copy of a 1.13.2 world. Check buckets, molten fluids,
   placed walls, inventories and equipment. Visit later-generated chunks.
   Do not open an original world for an upgrade test. Direct numeric 1.10–1.12
   conversion is outside this port; use the released 1.13.2 migration first.

## Automated checks

`build` runs deterministic resource checks and native recipe/registry tests.
`runGameTest` tests loot, hammer yields, mining levels, furnace accumulation,
held-dust discovery, old wall palettes/projectiles and Fantasy discovery.
`runGameTestLow` starts a separate Low Fantasy test world. Run both again with
`-PwithBaseMetals=true` for published Base Metals. Its cached Fantasy Level
requires a restart, so the Low profile deliberately starts with Base Metals
Low and Modern Metals High to verify inheritance.

`test -PwithContributors=true` supplies optional tag-only alloy contributors.
`runClientSmoke` and `runClientSmokeWithBase` check baked models, names, overlay
UVs and both configuration factories. `runServerSmoke` is a disposable dedicated-world probe and stops the
server after checking generation and saving. These probe profiles are for
automation; use the two ordinary client profiles for manual testing.

Automated checks cover the stated code paths. Visual appearance, recipe-book
usability and general gameplay still need manual acceptance.

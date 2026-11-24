# Manual testing on Minecraft 1.15.2

Open the outer ModernMetals 115 folder as the Eclipse workspace and import or
refresh the inner ModernMetals Gradle project. Gradle uses Java 17; the client
uses Java 8. The shared Gradle cache is D:/BaseMetals/.gradle-verify-cache.

Choose a Modern Metals launch: Standalone; Base Metals; Base Gems; Base Minerals;
each pair; or All Add-ons. These eight profiles use separate game directories.
Do not mix 1.14 jars into their mods folders. Local pins must be refreshed after
rebuilding a sibling, followed by a separate Eclipse generation.

- Compare isolated, straight, corner, T and crossing bars, including waterlogging.
- Place open and closed trapdoors directly on a floor and doors against walls.
  The terrain must remain visible through their holes from either side.
- Inspect ore faces and inventory blocks at shallow angles: no flicker, atlas bleed
  or opaque overlay backgrounds. Include Base Metals, gems and minerals.
- Crush coal and ores with both metal mods, then without Base Metals. Confirm one
  coal result and the expected powders, repair costs, tool speed and durability.
- Use beryllium powder to reveal crafting, furnace and blast-furnace recipes.
  Test all five alloys, including direct steel-blend ingredients with Base Metals.
- Open the mod-list and OreSpawn child config screens. Test both Fantasy modes
  with matching client/server settings; Modern Metals inherits Base Metals' mode.
- Check buckets, molten-fluid rendering and contact damage, arrows and bolts,
  villagers and creative tabs. Titanium should work with its ore rule disabled.
- Save, close and reopen the test world. Test upgrades on complete copies only.
  Keep every required mod and the old registry snapshot with an upgraded save.

Use the released game jars when checking the public stack.

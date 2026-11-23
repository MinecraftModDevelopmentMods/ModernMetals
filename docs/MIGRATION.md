# Upgrading a Modern Metals world to Minecraft 1.14.4

Released 1.13.2 saves already have named block states and use vanilla data fixing.
The legacy numeric conversion below is retained for older saves.

Make a complete copy of the world. Keep the source outside the test instance.
Install Forge 28.2.26, Modern Metals 3.0.0.114041 and OreSpawn 4.1.7.114041.
Install the Forge 28 Base Metals version when upgrading a world that used
Base Metals or placed MMDLib's vanilla-extension blocks. Other add-ons need
their own compatible versions and migration support.

The port uses the source world's Forge registry snapshot to resolve numeric
block IDs. Those numbers can differ between two saves made with the same
version. The mod retains the Modern Metals namespace and translates historical
registry names, including older door, fishing rod, horse armour and aluminum
brass IDs. Double slabs now use the `type=double` state of the material's
ordinary slab. Stair, door, trapdoor, button, lever, plate and liquid metadata
is restored as named properties.

Modern Metals reads the old registry before Minecraft converts the world.
On save, it carries the registry forward for chunks you have yet to visit. It converts legacy universal buckets
into named metal buckets and preserves item counts, slots, tool wear and
extra NBT. If it has to rewrite a loose player file or `level.dat`, it first
writes a sibling `*.modernmetals-legacy-backup`. The original copy is still
needed to go back to the old Minecraft version.

Normal loading upgrades chunks as you visit them. On a dedicated server you
can use `--forceUpgrade` to convert all of the copied world's existing chunks
before checking it. Save, close and reload the copy to check that the
converted data survives another start.

## Configuration

The port keeps all known registry entries available for old saves. It reads
the former `contentMode` setting from `ModernMetals.cfg` as the default for
the new `modernmetals-common.toml`. Base Metals supplies Fantasy Level when
installed. The old per-material registration switches and the 1.12.2
integration adapters have not been carried over. Ore selection, placement
and retrogen are managed through OreSpawn.

## Comparing worlds

Install the Python dependency `nbtlib==2.0.4` and run
`python tools/validation/port113/verify_legacy_world.py <source> <copy>`.
The tool reads both saves and compares each Modern Metals block at its
original coordinates, checks named states derived from metadata, and compares
saved stack counts, slots, durability and extra tags. It uses the source
world's actual numeric registry and never writes to either save.

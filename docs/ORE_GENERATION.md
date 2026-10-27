# Native OreSpawn 4 generation

Modern Metals enqueues a schema-5 provider through the public OreSpawn API after
blocks register. It supplies ore declarations only. No OS3 plugin, legacy IMC
generator or second fallback generator is packaged. It supplies no terrain,
rock/strata, biome or fluid-deposit policy and requires no Mineralogy.

Rules use `modernmetals:legacy/<material>_ore`, canonical `orespawn:<material>`
materials and the `orespawn:standard` placement channel. Disabled/missing blocks
are omitted safely. Empty legacy dimension lists mean ordinary dimensions except
the vanilla Nether and End, rather than Overworld only.

## Defaults

The legacy exclusive maximum becomes inclusive. Quantity conversion is
`size - variation` through `size + variation - 1`, clamped to 1..64 as documented
by OreSpawn; minimum clamps are reported at startup.

| Ore | Attempts/chunk | Quantity | Inclusive Y |
| --- | ---: | --- | --- |
| Boron | 8 | 1..15 | 0..31 |
| Thorium | 8 | 4..11 | 0..31 |
| Beryllium | 20 | 2..7 | 0..127 |
| Aluminum | 8 | 2..17 | 0..95 |
| Cadmium | 8 | 1..11 | 0..95 |
| Chromium | 3 | 1..5 | 0..31 |
| Iridium | 5 | 2..9 | 0..63 |
| Magnesium | 8 | 2..9 | 0..95 |
| Manganese | 6 | 2..9 | 0..63 |
| Osmium | 8 | 6..13 | 0..95 |
| Plutonium | 4 | 1..5 | 0..31 |
| Rutile | 6 | 2..9 | 0..63 |
| Tantalum | 6 | 2..9 | 0..63 |
| Tungsten | 6 | 1..7 | 0..31 |
| Uranium | 6 | 1..5 | 0..31 |
| Zirconium | 6 | 4..11 | 0..63 |
| Titanium (disabled) | 6 | 2..9 | 0..63 |

Titanium is independent and disabled by default. Its placement equals rutile's.
Enable it in the world's OreSpawn editor for future chunks of an existing world.
For future-world defaults, use `config/modernmetals-orespawn.json`. `test` exports
the full native declaration to `build/reports/native-provider.json` for pack authors.
There is no packaged JSON competing with the typed declaration.

## Migration and saved choices

OreSpawn imports supported OS1 `config/orespawn/modernmetals.json` and OS3
`config/orespawn3/modernmetals.json` into native overrides, preserving enabled
flags, dimensions, frequencies and quantities and reporting normalization/clamps.
Leave originals until imports are reviewed. Matching rules replace defaults;
they do not add a second pass. Native overrides and saved world profiles take
precedence over older files.

Each world keeps `serverconfig/orespawn-worldgen.json`. Global edits do not replace
saved choices. New provider entries may merge into existing profiles while existing
entries retain their settings. Retrogen stays off: generated chunks and saved
stacks remain intact; enabled ores can appear in later chunks.

## Groups and overlaps

Base Metals' eleven generated materials do not overlap these defaults. The reviewed
Base Minerals/Electric Advantage sources add no matching material. Nether Metals
and End Metals have corresponding variants in distinct dimension domains. Custom
configs can create real same-domain conflicts that need pack-specific review.

Canonical IDs group aluminum aliases (Aluminium/Bauxite), chromium/Chrome and
tungsten/Wolfram by registered output. OreSpawn exposes candidates by material,
domain and channel. Keep independent rules until a same-domain conflict has been
reviewed; a standard channel can share one placement owner with selected output
weights. No unrelated native generator is claimed as controlled. Existing source
choices and the engine's curated sulfur/lithium policies remain untouched.

External IC2/Mekanism candidates in OreSpawn's catalog may be unloaded. A catalog
entry alone does not prove control over their native generation. Qualify the exact
installed pack before changing its source policies.

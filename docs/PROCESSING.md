# Processing and alloy recipes on 1.13.2

Crackhammers turn an ore into two powders, an ingot into one powder, a nugget
into one small powder, and a metal storage block into nine powders. Nine small
powders make one powder. A powder splits back into nine small powders.

Ores and powders smelt into ingots. Small powders smelt into nuggets. Blends
and small blends follow the same ingot and nugget sizes.

## Blends

| Blend | Ingredients | Output |
| --- | --- | --- |
| Aluminum brass | Three aluminum dust and one copper dust | Four blends |
| Galvanized steel | One steel dust or steel blend, and one zinc dust | Two blends |
| Nichrome | One nickel dust and one chromium dust | Two blends |
| Stainless steel | One steel dust or steel blend, and one chromium dust | Two blends |
| Titanium | One rutile dust and one magnesium dust | One blend |

These are the modernized 1.12 formulas. Base Metals can supply copper, steel,
zinc and nickel. Other mods can supply them through the matching Forge dust
tags. Recipes requiring those ingredients become available when the tags
contain items. Chromium and Chrome ingredients both work for the two
chromium alloys.

Whole steel blends can go straight into galvanized steel or stainless steel.
Dust and blend inputs share the same recipe. Base Metals also accepts whole
iron blends in steel and invar, and whole gold blends in electrum. Each whole
blend replaces one matching dust; small blends keep their existing processing.

Titanium's rutile and magnesium route works with titanium ore generation off.
OreSpawn controls placement through its provider rules.

## Coal and shared recipes

Nine coal dust make a vanilla coal block. Craft that block into nine coal.
Crackhammers turn coal into one dust and coal blocks into nine dust.
Coal dust uses this processing route to recover ordinary furnace fuel.

Modern Metals supplies these recipes when running on its own. With Base
Metals installed, its shared recipes accept both mods' coal dust. Modern
Metals keeps its recipe IDs when contributing crushing recipes to Base
Metals' recipe type.

## Equipment and materials

Equipment and components can be recycled in a furnace. Outputs are bounded
by their actual crafting cost, including rods and gears used in a recipe.
Buttons recycle into two nuggets. Levers and ranged items retain their
earlier recycling losses. Armour and shields can be repaired with a matching plate. Repairs
preserve names, enchantments and other saved item data.

Every material has powder, small powder, ingot, nugget, storage, component,
equipment and bucket forms. The ordinary ore-processing materials are
aluminum, beryllium, boron, cadmium, chromium, iridium, magnesium, manganese,
osmium, plutonium, rutile, tantalum, thorium, tungsten, uranium and zirconium.
Titanium has its optional ore as well as its blend.

Aluminum brass, galvanized steel, nichrome and stainless steel use blends.
The old galvanized steel, nichrome and stainless steel ore blocks are
retained for saved worlds.

The catalogue also retains 138 processing forms from optional integrations,
including crushed and purified ores, crystals, shards, clumps, dirty powders,
casings and dense plates. Their mod-specific processing belongs to those
integrations. The 21 old fluid block items remain readable in saved
inventories. Filled buckets collect and place the corresponding molten
fluids.

## Recipe discovery

Picking up an ingot reveals its material's allowed crafting recipes.
Picking up a recipe ingredient also reveals the recipes using it. This
includes furnace recipes for powders, small powders, blends and recycling.

Existing inventories are checked on login and after a data pack reload.
Earned advancement progress is kept when changing Fantasy Level. High
Fantasy enables the full crafting catalogue. Low Fantasy applies the
material restrictions from the config, including the inherited Base Metals
setting when it is installed.

Crushing and plate repairs use their normal gameplay entry points. The
recipe books display crafting and furnace recipes.

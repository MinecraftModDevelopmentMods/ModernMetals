# Modern Metals fantasy setting

In Minecraft 1.11.2, Modern Metals owns its fantasy setting. Open **Mods → Modern
Metals → Config → Fantasy Level** and choose **High Fantasy** or **Low Fantasy**.
The same selector is available through OreSpawn's child Mods list. Save with Done
and restart the game or server to apply the recipes. Forge checks for matching
settings before joining a multiplayer world and explains how to fix a mismatch.
Use the same build on both sides so the check is available.

The setting is stored in **`config/ModernMetals.cfg`**:

```text
general {
    S:contentMode=low_fantasy
    B:enableVillagerTrades=true
}
```

High Fantasy is the default. Missing or invalid values use High Fantasy. Invalid
file values are normalized at startup. Changes remain pending until a full restart
and do not rewrite live registries or recipes.

**Villager Trades** defaults to on. Turn it off to stop new Modern Metals
blacksmith offers in either fantasy mode. The change applies after restart.
Offers already stored in a villager's inventory and offers from Base Metals
keep their existing behavior.

The 1.11 version does not read or change Base Metals' fantasy setting or recipes.
If an earlier local candidate used `BaseMetals.cfg`, move the desired value to
`ModernMetals.cfg`. Core-mod inheritance is reserved for the 1.12 modernization.

## Low Fantasy

| Form | Allowed Modern Metals materials |
| --- | --- |
| Tools, swords, ammunition, Tinkers tool parts | Galvanized steel, stainless steel, titanium |
| Armor, shields, horse armor | Aluminum, aluminum brass, galvanized steel, stainless steel, titanium |
| Bows, crossbows, fishing rods, Tinkers bow parts | None |
| Gears | Aluminum, aluminum brass, galvanized steel, stainless steel, titanium |
| Anvils | Galvanized steel, stainless steel |
| Doors, bars, stairs, slabs, fences, walls, mechanisms and other structural products | Aluminum, aluminum brass, galvanized steel, nichrome, stainless steel, tantalum, titanium, tungsten, zirconium |
| Ores, ingots, powders, fluids, buckets, plates and storage blocks | Unchanged |

Plutonium pickaxes remain registered so old stacks load, while their crafting
recipes and new equipment trade offers are unavailable. Titanium retains its
rutile-and-magnesium route. Molten Tinkers processing remains available for
materials that cannot supply parts. Restricted handles and ranged parts cannot
bypass the policy. Dynamic shared-library repair/upgrade recipes check their
resulting Modern Metals item too.

The policy covers every material and product family.
it is not a chemistry or radiation simulation. For example, elemental boron,
rutile and radioactive materials stay useful as processing inputs but cannot be
turned into structural doors, stairs or gears in Low Fantasy.

High Fantasy retains historical recipes, trades and integration behavior. Neither
mode unregisters IDs, rewrites saved inventories or enables retrogen.

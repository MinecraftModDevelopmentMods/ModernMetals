package modernmetals.init;

import cyano.basemetals.init.Items;
import cyano.basemetals.items.ItemMetalCrackHammer;
import cyano.basemetals.items.ItemMetalIngot;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.util.VillagerTradeHelper;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.passive.EntityVillager.ListEnchantedItemForEmeralds;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

import java.util.*;

/**
 * 
 * @author Jasmine Iwanek
 *
 */
public class VillagerTrades extends cyano.basemetals.init.VillagerTrades {
	private static boolean initDone = false;

	/**
	 * 
	 */
	public static void init() {
		if(initDone)
			return;

		modernmetals.init.Materials.init();
		modernmetals.init.Items.init();

		// Minecraft stores trades in a 4D array:
		// [Profession ID][Sub-profession ID][villager level - 1][trades]

		final int size = modernmetals.init.Materials.getAllMetals().size();
		final Map<MetalMaterial, List<Item>> allArmors = new HashMap<>(size);
		final Map<MetalMaterial, Item> allHammers = new HashMap<>(size);
		final Map<MetalMaterial, Item> allSwords = new HashMap<>(size);
		final Map<MetalMaterial, Item> allHoes = new HashMap<>(size);
		final Map<MetalMaterial, Item> allAxes = new HashMap<>(size);
		final Map<MetalMaterial, Item> allPickAxes = new HashMap<>(size);
		final Map<MetalMaterial, Item> allShovels = new HashMap<>(size);
		final Map<MetalMaterial, Item> allIngots = new HashMap<>(size);

		modernmetals.init.Items.getItemsByMetal().entrySet().stream()
				.forEach((Map.Entry<MetalMaterial,List<Item>> e)->{
					final MetalMaterial m = e.getKey();
					if(m == null) return;
					for(Item i : e.getValue()) {
						if(i instanceof ItemArmor) { allArmors.computeIfAbsent(m, (MetalMaterial g)->new ArrayList<>()).add(i); continue; }
						if(i instanceof ItemMetalCrackHammer) { allHammers.put(m, i); continue; }
						if(i instanceof ItemSword) { allSwords.put(m, i); continue; }
						if(i instanceof ItemHoe) { allHoes.put(m, i); continue; }
						if(i instanceof ItemAxe) { allAxes.put(m, i); continue; }
						if(i instanceof ItemPickaxe) { allPickAxes.put(m, i); continue; }
						if(i instanceof ItemSpade) { allShovels.put(m, i); continue; }
						if(i instanceof ItemMetalIngot) { allIngots.put(m, i); continue; }
					}
				}
		);

		Map<Integer, List<ITradeList>> tradesTable = new HashMap<>(); // integer is used as byte data: (unused) (profession) (career) (level)

		for(MetalMaterial m : modernmetals.init.Materials.getAllMetals()) {
			float value = m.hardness + m.strength + m.magicAffinity + m.getToolHarvestLevel();
//			if(m.isRare) continue;
			// for reference, iron has a value of 21.5, gold would be 14, copper is 14, and diamond is 30
			int emeraldPurch = emeraldPurchaseValue(value);
			int emeraldSale = emeraldSaleValue(value);
			int tradeLevel = tradeLevel(value);

			if(emeraldPurch > 64 || emeraldSale > 64)
				continue; // too expensive

			int armorsmith = (3 << 16) | (1 << 8) | (tradeLevel);
			int weaponsmith = (3 << 16) | (2 << 8) | (tradeLevel);
			int toolsmith = (3 << 16) | (3 << 8) | (tradeLevel);

			if(allIngots.containsKey(m)) {
				ITradeList[] ingotTrades = makeTradePalette(
						makePurchasePalette(emeraldPurch, 12, allIngots.get(m)),
						makeSalePalette(emeraldSale, 12, allIngots.get(m))
					);
				tradesTable.computeIfAbsent(armorsmith, (Integer key)->new ArrayList<>())
						.addAll(Arrays.asList(ingotTrades));
				tradesTable.computeIfAbsent(weaponsmith, (Integer key)->new ArrayList<>())
						.addAll(Arrays.asList(ingotTrades));
				tradesTable.computeIfAbsent(toolsmith, (Integer key)->new ArrayList<>())
						.addAll(Arrays.asList(ingotTrades));
			}
			if(allHammers.containsKey(m) && allPickAxes.containsKey(m)
					 && allAxes.containsKey(m) && allShovels.containsKey(m)
					 && allHoes.containsKey(m)) {
				tradesTable.computeIfAbsent(toolsmith,(Integer key)->new ArrayList<>())
						.addAll(Arrays.asList(
						makeTradePalette(
								makePurchasePalette(emeraldPurch, 1,
										allPickAxes.get(m),
										allAxes.get(m),
										allShovels.get(m),
										allHoes.get(m)))
						));
				tradesTable.computeIfAbsent((3 << 16) | (3 << 8) | (tradeLevel+1),(Integer key)->new ArrayList<>())
						.addAll(Arrays.asList(makeTradePalette(
								makePurchasePalette(emeraldPurch, 1,
										allHammers.get(m)))
						));
			}
			if(allSwords.containsKey(m)) {
				tradesTable.computeIfAbsent(weaponsmith,(Integer key)->new ArrayList<>())
						.addAll(Arrays.asList(
						makeTradePalette(
						makePurchasePalette(emeraldPurch + (int)(m.getBaseAttackDamage() / 2) - 1, 1, allSwords.get(m)))
						));
			}
			if(allArmors.containsKey(m)) {
				tradesTable.computeIfAbsent(armorsmith,(Integer key)->new ArrayList<>())
						.addAll(Arrays.asList(
						makeTradePalette(
						makePurchasePalette(emeraldPurch + (int)(m.hardness / 2), 1, allArmors.get(m).toArray(new Item[0]))
						)));
			}

			if(m.magicAffinity > 5) {
				if(allHammers.containsKey(m))
					tradesTable.computeIfAbsent((3 << 16) | (3 << 8) | (tradeLevel+2),(Integer key)->new ArrayList<>())
						.addAll(Arrays.asList(
						new ListEnchantedItemForEmeralds(allHammers.get(m), new PriceInfo(emeraldPurch+7, emeraldPurch+12))));
				if(allPickAxes.containsKey(m))
					tradesTable.computeIfAbsent((3 << 16) | (3 << 8) | (tradeLevel+1),(Integer key)->new ArrayList<>())
						.addAll(Arrays.asList(
						new ListEnchantedItemForEmeralds(allPickAxes.get(m), new PriceInfo(emeraldPurch+7, emeraldPurch+12))));
				if(allArmors.containsKey(m)) {
					for(int i = 0; i < allArmors.get(m).size(); i++)
						tradesTable.computeIfAbsent((3 << 16) | (1 << 8) | (tradeLevel+1),(Integer key)->new ArrayList<>())
								.addAll(Arrays.asList(
						new ListEnchantedItemForEmeralds(allArmors.get(m).get(i), new PriceInfo(emeraldPurch + 7 + (int)(m.hardness / 2), emeraldPurch + 12 + (int)(m.hardness / 2)))));
				}
				if(allSwords.containsKey(m))
					tradesTable.computeIfAbsent((3 << 16) | (2 << 8) | (tradeLevel + 1),(Integer key)->new ArrayList<>())
						.addAll(Arrays.asList(
						new ListEnchantedItemForEmeralds(allSwords.get(m), new PriceInfo(emeraldPurch + 7 + (int)(m.getBaseAttackDamage() / 2) - 1, emeraldPurch + 12 + (int)(m.getBaseAttackDamage() / 2) - 1))));
			}
		}
		tradesTable.computeIfAbsent((3 << 16) | (1 << 8) | (1),(Integer key)->new ArrayList<>())
				.addAll(Arrays.asList(
				makePurchasePalette(1, 10, Items.carbon_powder)));
		tradesTable.computeIfAbsent((3 << 16) | (2 << 8) | (1),(Integer key)->new ArrayList<>())
				.addAll(Arrays.asList(
				makePurchasePalette(1, 10, Items.carbon_powder)));
		tradesTable.computeIfAbsent((3 << 16) | (3 << 8) | (1),(Integer key)->new ArrayList<>())
				.addAll(Arrays.asList(
				makePurchasePalette(1, 10, Items.carbon_powder)));

		for(Integer k : tradesTable.keySet()) {
			List<ITradeList> trades = tradesTable.get(k);
			int profession = (k >> 16) & 0xFF;
			int career = (k >> 8) & 0xFF;
			int level = k & 0xFF;

			try {
				VillagerTradeHelper.insertTrades(profession, career, level, new MultiTradeGenerator(
						TRADES_PER_LEVEL,
						trades
				));
			} catch (NoSuchFieldException | IllegalAccessException ex) {
				FMLLog.log(Level.ERROR, ex, "Java Reflection Exception");
			}
		}

		initDone = true;
	}

	private static int emeraldPurchaseValue(float value) {
		return Math.max(1, (int)(value * 0.2F));
	}

	private static int emeraldSaleValue(float value) {
		return Math.max(1, emeraldPurchaseValue(value) / 3);
	}

	private static int tradeLevel(float value) {
		return Math.max(1, Math.min(4, (int)(value * 0.1F)));
	}

	private static int fluctuation(int baseValue) {
		if(baseValue <= 1)
			return 0;
		return Math.max(2, baseValue / 4);
	}

	private static ITradeList[] makePurchasePalette(int emeraldPrice, int stackSize, Item... items) {
		ITradeList[] trades = new ITradeList[items.length];
		for(int i = 0; i < items.length; i++) {
			Item item = items[i];
			trades[i] = new SimpleTrade(
					new ItemStack(net.minecraft.init.Items.EMERALD, emeraldPrice, 0), fluctuation(emeraldPrice),
					(ItemStack)null, 0,
					new ItemStack(item, stackSize, 0), 0);
		}
		return trades;
	}

	private static ITradeList[] makeSalePalette(int emeraldValue, int stackSize, Item... items) {
		ITradeList[] trades = new ITradeList[items.length];
		for(int i = 0; i < items.length; i++) {
			Item item = items[i];
			trades[i] = new SimpleTrade(
					new ItemStack(item, stackSize, 0), fluctuation(stackSize),
					(ItemStack)null, 0,
					new ItemStack(net.minecraft.init.Items.EMERALD, emeraldValue, 0), 0);
		}
		return trades;
	}

	private static ITradeList[] makeTradePalette(ITradeList[]... list) {
		if(list.length == 1)
			return list[0];
		int totalsize = 0;
		for(ITradeList[] e : list) {
			totalsize += e.length;
		}
		ITradeList[] concat = new ITradeList[totalsize];
		int index = 0;
		int element = 0;
		while(index < totalsize) {
			System.arraycopy(list[element], 0, concat, index, list[element].length);
			index += list[element].length;
			element++;
		}
		return concat;
	}
}

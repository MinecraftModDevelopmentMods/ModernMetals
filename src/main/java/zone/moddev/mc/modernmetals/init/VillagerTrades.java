package zone.moddev.mc.modernmetals.init;

/**
 *
 * @author Jasmine Iwanek
 *
 */
public class VillagerTrades extends com.mcmoddev.lib.init.VillagerTrades {

	private static boolean initDone = false;

	private VillagerTrades() {
		throw new IllegalAccessError("Not a instantiable class");
	}

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		Materials.init();
		Items.init();

		if(zone.moddev.mc.modernmetals.content.ContentPolicy.active().lowFantasy()) registerRestrictedTrades();
		else registerCommonTrades();

		initDone = true;
	}

	/** Preserve common trade prices while omitting disallowed equipment offers. */
	private static void registerRestrictedTrades() {
		final zone.moddev.mc.modernmetals.content.ContentPolicy policy=zone.moddev.mc.modernmetals.content.ContentPolicy.active();
		for(final com.mcmoddev.lib.material.MMDMaterial material:Materials.getMaterialsByMod("modernmetals")) {
			if(material.isRare()) continue;
			final float hardness=material.getStat(com.mcmoddev.lib.data.MaterialStats.HARDNESS);
			final float value=hardness+material.getStat(com.mcmoddev.lib.data.MaterialStats.STRENGTH)
				+material.getStat(com.mcmoddev.lib.data.MaterialStats.MAGICAFFINITY)+material.getToolHarvestLevel();
			final int purchase=emeraldPurchaseValue(value),sale=emeraldSaleValue(value),level=tradeLevel(value);
			if(purchase>64 || sale>64) continue;
			for(final com.mcmoddev.lib.data.Names form:new com.mcmoddev.lib.data.Names[]{
				com.mcmoddev.lib.data.Names.HELMET,com.mcmoddev.lib.data.Names.CHESTPLATE,com.mcmoddev.lib.data.Names.LEGGINGS,
				com.mcmoddev.lib.data.Names.BOOTS,com.mcmoddev.lib.data.Names.CRACKHAMMER,com.mcmoddev.lib.data.Names.SWORD,
				com.mcmoddev.lib.data.Names.AXE,com.mcmoddev.lib.data.Names.HOE,com.mcmoddev.lib.data.Names.SHOVEL,com.mcmoddev.lib.data.Names.PICKAXE}) {
				if(!material.hasItem(form) || !policy.allows(material.getItemStack(form))) continue;
				final boolean armor=form==com.mcmoddev.lib.data.Names.HELMET || form==com.mcmoddev.lib.data.Names.CHESTPLATE
					|| form==com.mcmoddev.lib.data.Names.LEGGINGS || form==com.mcmoddev.lib.data.Names.BOOTS;
				final boolean weapon=form==com.mcmoddev.lib.data.Names.SWORD;
				final int cost=armor?purchase+(int)(hardness/2):weapon?purchase+(int)(material.getBaseAttackDamage()/2)-1:purchase;
				zone.moddev.mc.basemetals.util.VillagerTradeHelper.insertTrades(SMITH_RL,
					armor?ARMOR_SMITH_ID:weapon?WEAPON_SMITH_ID:TOOL_SMITH_ID,level,
					makeTradePalette(makePurchasePalette(cost,material.getItemStack(form))));
			}
			if(material.hasItem(com.mcmoddev.lib.data.Names.INGOT)) {
				final net.minecraft.item.ItemStack ingots=material.getItemStack(com.mcmoddev.lib.data.Names.INGOT,12);
				for(final int career:new int[]{ARMOR_SMITH_ID,WEAPON_SMITH_ID,TOOL_SMITH_ID})
					zone.moddev.mc.basemetals.util.VillagerTradeHelper.insertTrades(SMITH_RL,career,level,
						makeTradePalette(makePurchasePalette(purchase,ingots),makeSalePalette(sale,ingots)));
			}
		}
	}
}

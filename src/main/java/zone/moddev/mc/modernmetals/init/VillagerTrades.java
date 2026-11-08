package zone.moddev.mc.modernmetals.init;

import com.mcmoddev.lib.data.MaterialStats;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.data.SharedStrings;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.util.VillagerTradeHelper;
import net.minecraft.item.ItemStack;
import zone.moddev.mc.modernmetals.ModernMetals;
import zone.moddev.mc.modernmetals.content.ContentPolicy;

/**
 *
 * @author Jasmine Iwanek
 *
 */
public class VillagerTrades extends com.mcmoddev.lib.init.VillagerTrades {
	private VillagerTrades() {
		throw new IllegalAccessError(SharedStrings.NOT_INSTANTIABLE);
	}

	/**
	 *
	 */
	public static void init() {
        if (!ContentPolicy.active().lowFantasy()) {
            registerCommonTrades();
            return;
        }
        // Keep smiths trading metal in Low Fantasy, without offering implausible equipment.
        for (final MMDMaterial material : Materials.getMaterialsByMod(ModernMetals.MODID)) {
            if (material.isEmpty() || material.isRare() || !material.hasItem(Names.INGOT)) continue;
            final float value = material.getStat(MaterialStats.HARDNESS)
                    + material.getStat(MaterialStats.STRENGTH)
                    + material.getStat(MaterialStats.MAGICAFFINITY) + material.getToolHarvestLevel();
            final int price = emeraldPurchaseValue(value);
            if (price > 64 || emeraldSaleValue(value) > 64) continue;
            final ItemStack ingots = material.getItemStack(Names.INGOT, 12);
            for (final int career : new int[] { ARMOR_SMITH_ID, WEAPON_SMITH_ID, TOOL_SMITH_ID }) {
                VillagerTradeHelper.insertTrades(SMITH_RL, career, tradeLevel(value),
                        makePurchasePalette(price, ingots));
            }
        }
	}
}

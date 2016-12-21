package modernmetals.integration.plugins;

import cyano.basemetals.integration.BaseMetalsPlugin;
import cyano.basemetals.integration.IIntegration;

import modernmetals.init.Blocks;
import modernmetals.init.Items;
import modernmetals.util.Config.Options;
import net.minecraft.item.ItemStack;

@BaseMetalsPlugin(Mekanism.PLUGIN_MODID)
public class Mekanism extends cyano.basemetals.integration.plugins.Mekanism implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !cyano.basemetals.util.Config.Options.ENABLE_MEKANISM) {
			return;
		}

		if (Options.ENABLE_ALUMINUM) {
			addCrusherRecipe(new ItemStack(Items.aluminum_clump), new ItemStack(Items.aluminum_powder_dirty)); // Verified
			addCrusherRecipe(new ItemStack(Items.aluminum_ingot), new ItemStack(Items.aluminum_powder)); // Verified

			addEnrichmentChamberRecipe(new ItemStack(Blocks.aluminum_ore), new ItemStack(Items.aluminum_powder, 2)); // Verified
			addEnrichmentChamberRecipe(new ItemStack(Items.aluminum_powder_dirty), new ItemStack(Items.aluminum_powder)); // Verified

			addPurificationChamberRecipe(new ItemStack(Blocks.aluminum_ore), new ItemStack(Items.aluminum_clump, 3)); // Verified
			addPurificationChamberRecipe(new ItemStack(Items.aluminum_shard), new ItemStack(Items.aluminum_clump)); // Verified

			addChemicalInjectionChamberRecipe(new ItemStack(Blocks.aluminum_ore), new ItemStack(Items.aluminum_shard, 4)); // Verified
			addChemicalInjectionChamberRecipe(new ItemStack(Items.aluminum_crystal), new ItemStack(Items.aluminum_shard)); // Verified
		}

		initDone = true;
	}
}

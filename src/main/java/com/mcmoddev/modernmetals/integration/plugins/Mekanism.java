package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.init.Materials;
import com.mcmoddev.modernmetals.util.Config.Options;

import cyano.basemetals.integration.BaseMetalsPlugin;
import cyano.basemetals.integration.IIntegration;
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
			addCrusherRecipe(new ItemStack(Materials.aluminum.clump), new ItemStack(Materials.aluminum.powder_dirty)); // Verified
			addCrusherRecipe(new ItemStack(Materials.aluminum.ingot), new ItemStack(Materials.aluminum.powder)); // Verified

			addEnrichmentChamberRecipe(new ItemStack(Materials.aluminum.ore), new ItemStack(Materials.aluminum.powder, 2)); // Verified
			addEnrichmentChamberRecipe(new ItemStack(Materials.aluminum.powder_dirty), new ItemStack(Materials.aluminum.powder)); // Verified

			addPurificationChamberRecipe(new ItemStack(Materials.aluminum.ore), new ItemStack(Materials.aluminum.clump, 3)); // Verified
			addPurificationChamberRecipe(new ItemStack(Materials.aluminum.shard), new ItemStack(Materials.aluminum.clump)); // Verified

			addChemicalInjectionChamberRecipe(new ItemStack(Materials.aluminum.ore), new ItemStack(Materials.aluminum.shard, 4)); // Verified
			addChemicalInjectionChamberRecipe(new ItemStack(Materials.aluminum.crystal), new ItemStack(Materials.aluminum.shard)); // Verified
		}

		initDone = true;
	}
}

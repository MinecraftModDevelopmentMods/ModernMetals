package com.mcmoddev.modernmetals.integration.plugins;

import java.util.HashMap;
import java.util.Map;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import cofh.api.util.ThermalExpansionHelper;
import net.minecraft.item.ItemStack;

@MMDPlugin(addonId = ModernMetals.MODID, pluginId = ThermalExpansion.PLUGIN_MODID)
public class ThermalExpansion extends com.mcmoddev.lib.integration.plugins.ThermalExpansionBase implements IIntegration {

	private static boolean initDone = false;

	public void init() {
		if (initDone || !Options.isModEnabled(ThermalExpansion.PLUGIN_MODID)) {
			return;
		}

		final String[] baseNames = new String[] {
			MaterialNames.ALUMINUM_BRASS,
			MaterialNames.BERYLLIUM,
			MaterialNames.BORON,
			MaterialNames.CADMIUM,
			MaterialNames.CHROMIUM,
			MaterialNames.GALVANIZED_STEEL,
			MaterialNames.MAGNESIUM,
			MaterialNames.MANGANESE,
			MaterialNames.NICHROME,
			MaterialNames.OSMIUM,
			MaterialNames.PLUTONIUM,
			MaterialNames.RUTILE,
			MaterialNames.STAINLESS_STEEL,
			MaterialNames.TANTALUM,
			MaterialNames.THORIUM,
			MaterialNames.TITANIUM,
			MaterialNames.TUNGSTEN,
			MaterialNames.URANIUM,
			MaterialNames.ZIRCONIUM
		};

		for (final String materialName : baseNames) {
			boolean enabled = Materials.hasMaterial(materialName);
			if (Materials.hasMaterial(materialName)) {
				addFurnace(enabled, materialName);
				addCrucible(enabled, materialName);
				addPlatePress(enabled, materialName);
				addPressStorage(enabled, materialName);
			}
		}

		if (Materials.hasMaterial(MaterialNames.PLUTONIUM)) {
			ThermalExpansionHelper.addMagmaticFuel(Materials.getMaterialByName(MaterialNames.PLUTONIUM).getFluid().getName(), 1000000);
		}
		
		if (Materials.hasMaterial(MaterialNames.URANIUM)) {
			ThermalExpansionHelper.addMagmaticFuel(Materials.getMaterialByName(MaterialNames.URANIUM).getFluid().getName(), 750000);
		}

		if ((Materials.hasMaterial(com.mcmoddev.basemetals.data.MaterialNames.STEEL))) {
			final MMDMaterial steel = Materials.getMaterialByName(com.mcmoddev.basemetals.data.MaterialNames.STEEL);
			if ((Materials.hasMaterial(MaterialNames.CHROMIUM) && (Materials.hasMaterial(MaterialNames.STAINLESS_STEEL)))) {
				ThermalExpansionHelper.addSmelterRecipe(4000, new ItemStack(steel.getItem(Names.INGOT), 1), new ItemStack(Materials.getMaterialByName(MaterialNames.CHROMIUM).getItem(Names.INGOT), 1), new ItemStack(Materials.getMaterialByName(MaterialNames.STAINLESS_STEEL).getItem(Names.INGOT), 2));
			}

			if ((Materials.hasMaterial(com.mcmoddev.basemetals.data.MaterialNames.ZINC) && (Materials.hasMaterial(MaterialNames.GALVANIZED_STEEL)))) {
				ThermalExpansionHelper.addSmelterRecipe(4000, new ItemStack(steel.getItem(Names.INGOT), 1), new ItemStack(Materials.getMaterialByName(com.mcmoddev.basemetals.data.MaterialNames.ZINC).getItem(Names.INGOT), 1), new ItemStack(Materials.getMaterialByName(MaterialNames.GALVANIZED_STEEL).getItem(Names.INGOT), 2));
			}
		}

		if ((Materials.hasMaterial(MaterialNames.RUTILE) && Materials.hasMaterial(MaterialNames.MAGNESIUM) && (Materials.hasMaterial(MaterialNames.TITANIUM)))) {
			ThermalExpansionHelper.addSmelterRecipe(4000, new ItemStack(Materials.getMaterialByName(MaterialNames.RUTILE).getItem(Names.INGOT), 1), new ItemStack(Materials.getMaterialByName(MaterialNames.MAGNESIUM).getItem(Names.INGOT), 1), new ItemStack( Materials.getMaterialByName(MaterialNames.TITANIUM).getItem(Names.INGOT), 2));
		}

		initDone = true;
	}
}

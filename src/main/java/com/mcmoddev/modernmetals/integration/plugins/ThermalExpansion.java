package com.mcmoddev.modernmetals.integration.plugins;

import java.util.HashMap;
import java.util.Map;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.modernmetals.init.Materials;
import com.mcmoddev.modernmetals.util.Config.Options;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import cofh.api.util.ThermalExpansionHelper;
import net.minecraft.item.ItemStack;

@MMDPlugin( addonId = ModernMetals.MODID, pluginId = ThermalExpansion.PLUGIN_MODID )
public class ThermalExpansion extends com.mcmoddev.lib.integration.plugins.ThermalExpansionBase implements IIntegration {

	private static boolean initDone = false;

	public void init() {
		if (initDone || !com.mcmoddev.basemetals.util.Config.Options.modEnabled(ThermalExpansion.PLUGIN_MODID)) {
			return;
		}

		Map<String, Boolean> materials = new HashMap<>();
		
		materials.put(MaterialNames.ALUMINUM_BRASS, Options.materialEnabled(MaterialNames.ALUMINUM_BRASS));
		materials.put(MaterialNames.CADMIUM, Options.materialEnabled(MaterialNames.CADMIUM));
		materials.put(MaterialNames.CHROMIUM, Options.materialEnabled(MaterialNames.CHROMIUM));
		materials.put(MaterialNames.GALVANIZED_STEEL, Options.materialEnabled(MaterialNames.GALVANIZED_STEEL));
		materials.put(MaterialNames.MAGNESIUM, Options.materialEnabled(MaterialNames.MAGNESIUM));
		materials.put(MaterialNames.MANGANESE, Options.materialEnabled(MaterialNames.MANGANESE));
		materials.put(MaterialNames.NICHROME, Options.materialEnabled(MaterialNames.NICHROME));
		materials.put(MaterialNames.OSMIUM, Options.materialEnabled(MaterialNames.OSMIUM));
		materials.put(MaterialNames.PLUTONIUM, Options.materialEnabled(MaterialNames.PLUTONIUM));
		materials.put(MaterialNames.RUTILE, Options.materialEnabled(MaterialNames.RUTILE));
		materials.put(MaterialNames.STAINLESS_STEEL, Options.materialEnabled(MaterialNames.STAINLESS_STEEL));
		materials.put(MaterialNames.TANTALUM, Options.materialEnabled(MaterialNames.TANTALUM));
		materials.put(MaterialNames.TITANIUM, Options.materialEnabled(MaterialNames.TITANIUM));
		materials.put(MaterialNames.TUNGSTEN, Options.materialEnabled(MaterialNames.TUNGSTEN));
		materials.put(MaterialNames.URANIUM, Options.materialEnabled(MaterialNames.URANIUM));
		materials.put(MaterialNames.ZIRCONIUM, Options.materialEnabled(MaterialNames.ZIRCONIUM));

		for (Map.Entry<String, Boolean> e : materials.entrySet()) {
			addFurnace(e.getValue(), e.getKey());
			addCrucible(e.getValue(), e.getKey());
			addPlatePress(e.getValue(), e.getKey());
			addPressStorage(e.getValue(), e.getKey());
		}
		
		if (Options.materialEnabled(MaterialNames.PLUTONIUM)) {
			ThermalExpansionHelper.addMagmaticFuel(Materials.getMaterialByName(MaterialNames.PLUTONIUM).getFluid().getName(), 1000000);
		}
		
		if (Options.materialEnabled(MaterialNames.URANIUM)) {
			ThermalExpansionHelper.addMagmaticFuel(Materials.getMaterialByName(MaterialNames.URANIUM).getFluid().getName(), 750000);
		}
		
		if (Options.materialEnabled(MaterialNames.CHROMIUM) && Options.materialEnabled(MaterialNames.STAINLESS_STEEL)) {
			ThermalExpansionHelper.addSmelterRecipe(4000, new ItemStack(Materials.getMaterialByName(com.mcmoddev.basemetals.data.MaterialNames.STEEL).getItem(Names.INGOT), 1), new ItemStack(Materials.getMaterialByName(MaterialNames.CHROMIUM).getItem(Names.INGOT), 1), new ItemStack(Materials.getMaterialByName(MaterialNames.STAINLESS_STEEL).getItem(Names.INGOT), 2));
		}
		
		if (Options.materialEnabled(MaterialNames.GALVANIZED_STEEL)) {
			ThermalExpansionHelper.addSmelterRecipe(4000, new ItemStack(Materials.getMaterialByName(com.mcmoddev.basemetals.data.MaterialNames.STEEL).getItem(Names.INGOT), 1), new ItemStack(Materials.getMaterialByName(com.mcmoddev.basemetals.data.MaterialNames.ZINC).getItem(Names.INGOT), 1), new ItemStack(Materials.getMaterialByName(MaterialNames.GALVANIZED_STEEL).getItem(Names.INGOT), 2));
		}
		
		if (Options.materialEnabled(MaterialNames.RUTILE) && Options.materialEnabled(MaterialNames.MAGNESIUM) && Options.materialEnabled(MaterialNames.TITANIUM)) {
			ThermalExpansionHelper.addSmelterRecipe(4000, new ItemStack(Materials.getMaterialByName(MaterialNames.RUTILE).getItem(Names.INGOT), 1), new ItemStack(Materials.getMaterialByName(MaterialNames.MAGNESIUM).getItem(Names.INGOT), 1), new ItemStack( Materials.getMaterialByName(MaterialNames.TITANIUM).getItem(Names.INGOT), 2));
		}

		initDone = true;
	}
}

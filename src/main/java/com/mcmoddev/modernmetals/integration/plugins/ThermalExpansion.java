package com.mcmoddev.modernmetals.integration.plugins;

import java.util.Arrays;
import java.util.List;

import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;

import cofh.api.util.ThermalExpansionHelper;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@MMDPlugin(addonId = ModernMetals.MODID, pluginId = ThermalExpansion.PLUGIN_MODID)
public class ThermalExpansion extends com.mcmoddev.lib.integration.plugins.ThermalExpansionBase
		implements IIntegration {
	public void init() {
		if (!Options.isModEnabled(PLUGIN_MODID)) {
			return;
		}
	}

	@SubscribeEvent
	public void regShit(RegistryEvent.Register<IRecipe> event) {

		final List<String> materials = Arrays.asList(MaterialNames.ALUMINUM_BRASS, MaterialNames.BERYLLIUM,
				MaterialNames.BORON, MaterialNames.CADMIUM, MaterialNames.CHROMIUM, MaterialNames.GALVANIZED_STEEL,
				MaterialNames.MAGNESIUM, MaterialNames.MANGANESE, MaterialNames.NICHROME, MaterialNames.OSMIUM,
				MaterialNames.PLUTONIUM, MaterialNames.RUTILE, MaterialNames.STAINLESS_STEEL, MaterialNames.TANTALUM,
				MaterialNames.THORIUM, MaterialNames.TITANIUM, MaterialNames.TUNGSTEN, MaterialNames.URANIUM,
				MaterialNames.ZIRCONIUM);

		materials.stream().filter(Materials::hasMaterial)
				.filter(materialName -> !Materials.getMaterialByName(materialName).isEmpty())
				.forEach(materialName -> {
					addFurnace(materialName);
					addCrucible(materialName);
					addPlatePress(materialName);
					addPressStorage(materialName);
				});

		maybeAddMagmaticFuels( MaterialNames.PLUTONIUM, MaterialNames.URANIUM );

		if ( (hasMaterials(com.mcmoddev.basemetals.data.MaterialNames.STEEL, MaterialNames.CHROMIUM, MaterialNames.STAINLESS_STEEL))
				&& (materialsHaveItems(Arrays.asList(com.mcmoddev.basemetals.data.MaterialNames.STEEL, MaterialNames.CHROMIUM, MaterialNames.STAINLESS_STEEL), Names.INGOT.toString()))) {
			final MMDMaterial steel = Materials.getMaterialByName(com.mcmoddev.basemetals.data.MaterialNames.STEEL);
			final MMDMaterial chromium = Materials.getMaterialByName(MaterialNames.CHROMIUM);
			final MMDMaterial stainlessSteel = Materials.getMaterialByName(MaterialNames.STAINLESS_STEEL);
			ThermalExpansionHelper.addSmelterRecipe(4000, steel.getItemStack(Names.INGOT, 1),
					chromium.getItemStack(Names.INGOT, 1), stainlessSteel.getItemStack(Names.INGOT, 2));
		}

		if ( (hasMaterials(com.mcmoddev.basemetals.data.MaterialNames.STEEL, com.mcmoddev.basemetals.data.MaterialNames.ZINC, MaterialNames.GALVANIZED_STEEL))
				&& (materialsHaveItems(Arrays.asList(com.mcmoddev.basemetals.data.MaterialNames.STEEL, com.mcmoddev.basemetals.data.MaterialNames.ZINC, MaterialNames.GALVANIZED_STEEL), Names.INGOT.toString()))) {
			final MMDMaterial steel = Materials.getMaterialByName(com.mcmoddev.basemetals.data.MaterialNames.STEEL);
			final MMDMaterial galvanizedSteel = Materials.getMaterialByName(MaterialNames.GALVANIZED_STEEL);
			final MMDMaterial zinc = Materials
					.getMaterialByName(com.mcmoddev.basemetals.data.MaterialNames.ZINC);
			ThermalExpansionHelper.addSmelterRecipe(4000, steel.getItemStack(Names.INGOT, 1),
					zinc.getItemStack(Names.INGOT, 1), galvanizedSteel.getItemStack(Names.INGOT, 2));
		}

		if ((hasMaterials(MaterialNames.RUTILE, MaterialNames.MAGNESIUM, MaterialNames.TITANIUM)
				&& (materialsHaveItems(Arrays.asList(MaterialNames.RUTILE, MaterialNames.MAGNESIUM, MaterialNames.TITANIUM), Names.INGOT.toString())))) {
			final MMDMaterial magnesium = Materials.getMaterialByName(MaterialNames.MAGNESIUM);
			final MMDMaterial rutile = Materials.getMaterialByName(MaterialNames.RUTILE);
			final MMDMaterial titanium = Materials.getMaterialByName(MaterialNames.TITANIUM);
			ThermalExpansionHelper.addSmelterRecipe(4000, rutile.getItemStack(Names.INGOT, 1),
					magnesium.getItemStack(Names.INGOT, 1), titanium.getItemStack(Names.INGOT, 2));
		}
	}

	private void maybeAddMagmaticFuels(String...materials) {
		Arrays.asList(materials).stream().filter(Materials::hasMaterial)
		.map(Materials::getMaterialByName)
		.forEach(mat -> {
			int fuelRF = getFuelRF(mat.getName());
			if( fuelRF > 0 ) {
				String fluidName = mat.getFluid().getName();
				ThermalExpansionHelper.addMagmaticFuel( fluidName, fuelRF );
			}
		});
	}

	private int getFuelRF(String name) {
		if (name.equals(MaterialNames.URANIUM)) return 750000;
		if (name.equals(MaterialNames.PLUTONIUM)) return 1000000;
		return 0;
	}
	
	private static boolean materialsHaveItems(List<String> materialNames, String...items) {
		for (String item : items) {
			for (String materialName : materialNames) {
				if (!Materials.getMaterialByName(materialName).hasItem(item)) return false;
			}
		}
		return true;
	}
	
	private static boolean hasMaterials(String...materials) {
		for (String materialName : materials) {
			if (!Materials.hasMaterial(materialName)) return false;
		}
		return true;
	}
}

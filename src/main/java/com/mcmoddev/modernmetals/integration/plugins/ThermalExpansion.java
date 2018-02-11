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

	private static boolean initDone = false;

	public void init() {
		if (initDone || !Options.isModEnabled(ThermalExpansion.PLUGIN_MODID)) {
			return;
		}

		initDone = true;
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

		if (Materials.hasMaterial(MaterialNames.PLUTONIUM)) {
			ThermalExpansionHelper.addMagmaticFuel(
					Materials.getMaterialByName(MaterialNames.PLUTONIUM).getFluid().getName(), 1000000);
		}

		if (Materials.hasMaterial(MaterialNames.URANIUM)) {
			ThermalExpansionHelper
					.addMagmaticFuel(Materials.getMaterialByName(MaterialNames.URANIUM).getFluid().getName(), 750000);
		}

		if ((Materials.hasMaterial(com.mcmoddev.basemetals.data.MaterialNames.STEEL))) {
			final MMDMaterial steel = Materials.getMaterialByName(com.mcmoddev.basemetals.data.MaterialNames.STEEL);
			if (steel.hasItem(Names.INGOT)) {
				if ((Materials.hasMaterial(MaterialNames.CHROMIUM)
						&& (Materials.hasMaterial(MaterialNames.STAINLESS_STEEL)))) {
					final MMDMaterial chromium = Materials.getMaterialByName(MaterialNames.CHROMIUM);
					final MMDMaterial stainlessSteel = Materials.getMaterialByName(MaterialNames.STAINLESS_STEEL);
					if ((chromium.hasItem(Names.INGOT)) && (stainlessSteel.hasItem(Names.INGOT))) {
						ThermalExpansionHelper.addSmelterRecipe(4000, steel.getItemStack(Names.INGOT, 1),
								chromium.getItemStack(Names.INGOT, 1), stainlessSteel.getItemStack(Names.INGOT, 2));
					}
				}

				if ((Materials.hasMaterial(com.mcmoddev.basemetals.data.MaterialNames.ZINC)
						&& (Materials.hasMaterial(MaterialNames.GALVANIZED_STEEL)))) {
					final MMDMaterial galvanizedSteel = Materials.getMaterialByName(MaterialNames.GALVANIZED_STEEL);
					final MMDMaterial zinc = Materials
							.getMaterialByName(com.mcmoddev.basemetals.data.MaterialNames.ZINC);
					if ((zinc.hasItem(Names.INGOT)) && (galvanizedSteel.hasItem(Names.INGOT))) {
						ThermalExpansionHelper.addSmelterRecipe(4000, steel.getItemStack(Names.INGOT, 1),
								zinc.getItemStack(Names.INGOT, 1), galvanizedSteel.getItemStack(Names.INGOT, 2));
					}
				}
			}
		}

		if ((Materials.hasMaterial(MaterialNames.RUTILE) && Materials.hasMaterial(MaterialNames.MAGNESIUM)
				&& (Materials.hasMaterial(MaterialNames.TITANIUM)))) {
			final MMDMaterial magnesium = Materials.getMaterialByName(MaterialNames.MAGNESIUM);
			final MMDMaterial rutile = Materials.getMaterialByName(MaterialNames.RUTILE);
			final MMDMaterial titanium = Materials.getMaterialByName(MaterialNames.TITANIUM);
			if ((rutile.hasItem(Names.INGOT)) && (magnesium.hasItem(Names.INGOT)) && (titanium.hasItem(Names.INGOT))) {
				ThermalExpansionHelper.addSmelterRecipe(4000, rutile.getItemStack(Names.INGOT, 1),
						magnesium.getItemStack(Names.INGOT, 1), titanium.getItemStack(Names.INGOT, 2));
			}
		}
	}
}

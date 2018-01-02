package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.integration.IIntegration;

@MMDPlugin(addonId = ModernMetals.MODID, pluginId = IC2.PLUGIN_MODID)
public class IC2 extends com.mcmoddev.lib.integration.plugins.IC2Base implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !Options.isModEnabled(IC2.PLUGIN_MODID)) {
			return;
		}

		initDone = true;
	}

	@SubscribeEvent
	public void multiplyOres(RegistryEvent.Register<IRecipe> event) {
		final String[] baseNames = new String[] {
			MaterialNames.ALUMINUM,
			MaterialNames.BERYLLIUM,
			MaterialNames.BORON,
			MaterialNames.CADMIUM,
			MaterialNames.CHROMIUM,
			MaterialNames.IRIDIUM,
			MaterialNames.MAGNESIUM,
			MaterialNames.MANGANESE,
			MaterialNames.PLUTONIUM,
			MaterialNames.RUTILE,
			MaterialNames.TANTALUM,
			MaterialNames.THORIUM,
			MaterialNames.TITANIUM,
			MaterialNames.TUNGSTEN,
			MaterialNames.URANIUM,
			MaterialNames.ZIRCONIUM
		};

		for (final String materialName : baseNames) {
			if (Materials.hasMaterial(materialName)) {
	            registerVanillaRecipes(materialName);
	            addMaceratorRecipes(materialName);
	            addOreWashingPlantRecipes(materialName);
	            addThermalCentrifugeRecipes(materialName);
	            addMetalFormerRecipes(materialName);
				addCompressorRecipes(materialName);
			}
		}
	}
}

package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.integration.IIntegration;

@MMDPlugin(addonId = ModernMetals.MODID, pluginId = Mekanism.PLUGIN_MODID)
public class Mekanism extends com.mcmoddev.lib.integration.plugins.MekanismBase implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !Options.isModEnabled(Mekanism.PLUGIN_MODID)) {
			return;
		}

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

		for (int i = 0; i < baseNames.length; i++) {
			final String materialName = baseNames[i];
			if (Options.isMaterialEnabled(materialName)) {
				addGassesForMaterial(materialName);
			}
		}

		MinecraftForge.EVENT_BUS.register(this);
		
		initDone = true;
	}
	
	@SubscribeEvent
	public void multiplyOres(RegistryEvent.Register<IRecipe> ev ) {
	final String[] baseNames = new String[] {
			MaterialNames.ALUMINUM,
			MaterialNames.CADMIUM,
			MaterialNames.CHROMIUM,
			MaterialNames.IRIDIUM,
			MaterialNames.MAGNESIUM,
			MaterialNames.MANGANESE,
			MaterialNames.PLUTONIUM,
			MaterialNames.RUTILE,
			MaterialNames.TANTALUM,
			MaterialNames.TITANIUM,
			MaterialNames.TUNGSTEN,
			MaterialNames.URANIUM,
			MaterialNames.ZIRCONIUM
		};

		for (int i = 0; i < baseNames.length; i++) {
			final String materialName = baseNames[i];
			if (Options.isMaterialEnabled(materialName)) {
				addOreMultiplicationRecipes(materialName);
			}
		}
	}
}

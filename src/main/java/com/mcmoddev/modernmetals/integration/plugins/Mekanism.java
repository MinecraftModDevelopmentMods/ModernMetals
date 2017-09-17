package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;
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

		initDone = true;
	}
}

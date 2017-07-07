package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.modernmetals.util.Config.Options;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.integration.IIntegration;

/**
 * VeinMiner Integration Plugin
 *
 * @author Jasmine Iwanek
 *
 */
@MMDPlugin(addonId = ModernMetals.MODID, pluginId = VeinMiner.PLUGIN_MODID)
public class VeinMiner extends com.mcmoddev.lib.integration.plugins.VeinMinerBase implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !com.mcmoddev.basemetals.util.Config.Options.modEnabled(VeinMiner.PLUGIN_MODID)) {
			return;
		}

		final String[] baseNames = new String[] {
			MaterialNames.ALUMINUM,
			MaterialNames.ALUMINUM_BRASS,
			MaterialNames.CADMIUM,
			MaterialNames.CHROMIUM,
			MaterialNames.GALVANIZED_STEEL,
			MaterialNames.IRIDIUM,
			MaterialNames.MAGNESIUM,
			MaterialNames.MANGANESE,
			MaterialNames.NICHROME,
			MaterialNames.OSMIUM,
			MaterialNames.PLUTONIUM,
			MaterialNames.RUTILE,
			MaterialNames.STAINLESS_STEEL,
			MaterialNames.TANTALUM,
			MaterialNames.TITANIUM,
			MaterialNames.TUNGSTEN,
			MaterialNames.URANIUM,
			MaterialNames.ZIRCONIUM
		};

		for (int i = 0; i < baseNames.length; i++) {
			final String materialName = baseNames[i];
			if (Options.materialEnabled(materialName)) {
				addToolsForMaterial(materialName);
			}
		}

		initDone = true;
	}
}

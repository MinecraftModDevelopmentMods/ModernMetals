package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.integration.IIntegration;

/**
 *
 * @author Jasmine Iwanek
 *
 */
@MMDPlugin(addonId = ModernMetals.MODID, pluginId = EnderIO.PLUGIN_MODID)
public class EnderIO extends com.mcmoddev.lib.integration.plugins.EnderIOBase implements IIntegration {

	private static boolean initDone = false;

	/**
	 *
	 */
	@Override
	public void init() {
		if (initDone || !com.mcmoddev.lib.util.ConfigBase.Options.isModEnabled(EnderIO.PLUGIN_MODID)) {
			return;
		}

		if (Options.isMaterialEnabled(MaterialNames.ALUMINUM)) {
			addSagMillRecipe(MaterialNames.ALUMINUM, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.ALUMINUM_BRASS)) {
			addSagMillRecipe(MaterialNames.ALUMINUM_BRASS, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.CADMIUM)) {
			addSagMillRecipe(MaterialNames.CADMIUM, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.CHROMIUM)) {
			addSagMillRecipe(MaterialNames.CHROMIUM, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.GALVANIZED_STEEL)) {
			addSagMillRecipe(MaterialNames.GALVANIZED_STEEL, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.IRIDIUM)) {
			addSagMillRecipe(MaterialNames.IRIDIUM, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.MAGNESIUM)) {
			addSagMillRecipe(MaterialNames.MAGNESIUM, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.MANGANESE)) {
			addSagMillRecipe(MaterialNames.MANGANESE, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.NICHROME)) {
			addSagMillRecipe(MaterialNames.NICHROME, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.OSMIUM)) {
			addSagMillRecipe(MaterialNames.OSMIUM, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.PLUTONIUM)) {
			addSagMillRecipe(MaterialNames.PLUTONIUM, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.RUTILE)) {
			addSagMillRecipe(MaterialNames.RUTILE, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.STAINLESS_STEEL)) {
			addSagMillRecipe(MaterialNames.STAINLESS_STEEL, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.TANTALUM)) {
			addSagMillRecipe(MaterialNames.TANTALUM, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.TITANIUM)) {
			addSagMillRecipe(MaterialNames.TITANIUM, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.TUNGSTEN)) {
			addSagMillRecipe(MaterialNames.TUNGSTEN, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.URANIUM)) {
			addSagMillRecipe(MaterialNames.URANIUM, null, 3600);
		}

		if (Options.isMaterialEnabled(MaterialNames.ZIRCONIUM)) {
			addSagMillRecipe(MaterialNames.ZIRCONIUM, null, 3600);
		}

		initDone = true;
	}
}

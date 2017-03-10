package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.util.Config.Options;
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
		if (initDone || !com.mcmoddev.basemetals.util.Config.Options.enableEnderIO) {
			return;
		}

		if (Options.enableAluminum) {
			addSagMillRecipe("aluminum", null, 3600);
		}

		if (Options.enableAluminumBrass) {
			addSagMillRecipe("aluminumbrass", null, 3600);
		}

		if (Options.enableCadmium) {
			addSagMillRecipe("cadmium", null, 3600);
		}

		if (Options.enableChromium) {
			addSagMillRecipe("chromium", null, 3600);
		}

		if (Options.enableGalvanizedSteel) {
			addSagMillRecipe("galvanizedsteel", null, 3600);
		}

		if (Options.enableIridium) {
			addSagMillRecipe("iridium", null, 3600);
		}

		if (Options.enableMagnesium) {
			addSagMillRecipe("magnesium", null, 3600);
		}

		if (Options.enableManganese) {
			addSagMillRecipe("manganese", null, 3600);
		}

		if (Options.enableNichrome) {
			addSagMillRecipe("nichrome", null, 3600);
		}

		if (Options.enableOsmium) {
			addSagMillRecipe("osmium", null, 3600);
		}

		if (Options.enablePlutonium) {
			addSagMillRecipe("plutonium", null, 3600);
		}

		if (Options.enableRutile) {
			addSagMillRecipe("rutile", null, 3600);
		}

		if (Options.enableStainlessSteel) {
			addSagMillRecipe("stainlesssteel", null, 3600);
		}

		if (Options.enableTantalum) {
			addSagMillRecipe("tantalum", null, 3600);
		}

		if (Options.enableTitanium) {
			addSagMillRecipe("titanium", null, 3600);
		}

		if (Options.enableTungsten) {
			addSagMillRecipe("tungsten", null, 3600);
		}

		if (Options.enableUranium) {
			addSagMillRecipe("uranium", null, 3600);
		}

		if (Options.enableZirconium) {
			addSagMillRecipe("zirconium", null, 3600);
		}

		initDone = true;
	}
}

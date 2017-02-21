package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.ModernMetals;
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
public class VeinMiner extends com.mcmoddev.lib.integration.plugins.VeinMiner implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !com.mcmoddev.basemetals.util.Config.Options.enableVeinminer) {
			return;
		}

		if (Options.enableAluminum) {
			addToolsForMaterial("aluminum");
		}

		if (Options.enableAluminumBrass) {
			addToolsForMaterial("aluminumbrass");
		}

		if (Options.enableCadmium) {
			addToolsForMaterial("cadmium");
		}

		if (Options.enableChromium) {
			addToolsForMaterial("chromium");
		}

		if (Options.enableGalvanizedSteel) {
			addToolsForMaterial("galvanizedsteel");
		}

		if (Options.enableIridium) {
			addToolsForMaterial("iridium");
		}

		if (Options.enableMagnesium) {
			addToolsForMaterial("magnesium");
		}

		if (Options.enableManganese) {
			addToolsForMaterial("manganese");
		}

		if (Options.enableNichrome) {
			addToolsForMaterial("nichrome");
		}

		if (Options.enableOsmium) {
			addToolsForMaterial("osmium");
		}

		if (Options.enablePlutonium) {
			addToolsForMaterial("plutonium");
		}

		if (Options.enableRutile) {
			addToolsForMaterial("rutile");
		}

		if (Options.enableStainlessSteel) {
			addToolsForMaterial("stainlesssteel");
		}

		if (Options.enableTantalum) {
			addToolsForMaterial("tantalum");
		}

		if (Options.enableTitanium) {
			addToolsForMaterial("titanium");
		}

		if (Options.enableTungsten) {
			addToolsForMaterial("tungsten");
		}

		if (Options.enableUranium) {
			addToolsForMaterial("uranium");
		}

		if (Options.enableZirconium) {
			addToolsForMaterial("zirconium");
		}

		initDone = true;
	}
}

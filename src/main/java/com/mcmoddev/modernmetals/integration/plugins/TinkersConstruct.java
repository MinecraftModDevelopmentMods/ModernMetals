package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.util.Config.Options;

import cyano.basemetals.integration.BaseMetalsPlugin;
import cyano.basemetals.integration.IIntegration;

/**
 *
 * @author Jasmine Iwanek
 *
 */
@BaseMetalsPlugin(TinkersConstruct.PLUGIN_MODID)
public class TinkersConstruct extends cyano.basemetals.integration.plugins.TinkersConstruct implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !cyano.basemetals.util.Config.Options.ENABLE_TINKERS_CONSTRUCT) {
			return;
		}

		if (Options.ENABLE_ALUMINUM) {
			registerMaterial("aluminum", false, true);
		}
		if (Options.ENABLE_ALUMINUMBRASS) {
			registerMaterial("aluminumbrass", false, true);
		}
		if (Options.ENABLE_CADMIUM) {
			registerMaterial("cadmium", false, true);
		}
		if (Options.ENABLE_CHROMIUM) {
			registerMaterial("chromium", false, true);
		}
		if (Options.ENABLE_GALVANIZEDSTEEL) {
			registerMaterial("galvanizedsteel", false, true);
		}
		if (Options.ENABLE_IRIDIUM) {
			registerMaterial("iridium", false, true);
		}
		if (Options.ENABLE_MAGNESIUM) {
			registerMaterial("magnesium", false, true);
		}
		if (Options.ENABLE_MANGANESE) {
			registerMaterial("manganese", false, true);
		}
		if (Options.ENABLE_NICHROME) {
			registerMaterial("nichrome", false, true);
		}
		if (Options.ENABLE_OSMIUM) {
			registerMaterial("osmium", false, true);
		}
		if (Options.ENABLE_PLUTONIUM) {
			registerMaterial("plutonium", false, true);
		}
		if (Options.ENABLE_RUTILE) {
			registerMaterial("rutile", false, true);
		}
		if (Options.ENABLE_STAINLESSSTEEL) {
			registerMaterial("stainlesssteel", false, true);
		}
		if (Options.ENABLE_TANTALUM) {
			registerMaterial("tantalum", false, true);
		}
		if (Options.ENABLE_TITANIUM) {
			registerMaterial("titanium", false, true);
		}
		if (Options.ENABLE_TUNGSTEN) {
			registerMaterial("tungsten", false, true);
		}
		if (Options.ENABLE_URANIUM) {
			registerMaterial("uranium", false, true);
		}
		if (Options.ENABLE_ZIRCONIUM) {
			registerMaterial("zirconium", false, true);
		}

		// registerAlloy("aluminumbrass", 2, new String[] { "aluminum", "brass" }, new int[] { 1, 1 }); // TCon already has Aluminum Brass alloy
		registerAlloy("galvanizedsteel", 2, new String[] { "steel", "zinc" }, new int[] { 1, 1 });
		registerAlloy("nichrome", 2, new String[] { "nickel", "chrome" }, new int[] { 1, 1 });
		registerAlloy("stainlesssteel", 2, new String[] { "steel", "chrome" }, new int[] { 1, 1 });
		registerAlloy("titanium", 2, new String[] { "rutile", "magnesium" }, new int[] { 1, 1 });

		initDone = true;
	}
}

package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.util.Config.Options;
import com.mcmoddev.modernmetals.init.Materials;
import com.mcmoddev.modernmetals.integration.ModernMetalsPlugin;
import com.mcmoddev.lib.integration.IIntegration;

/**
 *
 * @author Jasmine Iwanek
 *
 */
@ModernMetalsPlugin(TinkersConstruct.PLUGIN_MODID)
public class TinkersConstruct extends com.mcmoddev.lib.integration.plugins.TinkersConstruct implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !com.mcmoddev.basemetals.util.Config.Options.enableTinkersConstruct) {
			return;
		}

		if (Options.enableAluminum) {
			registerMaterial(Materials.aluminum.getName(), false, true);
		}

		if (Options.enableAluminumBrass) {
			registerMaterial(Materials.aluminumBrass.getName(), false, true);
			// registerAlloy(Materials.aluminumbrass.getName(), 2, new String[] { "aluminum", "brass" }, new int[] { 1, 1 }); // TCon already has Aluminum Brass alloy
		}

		if (Options.enableCadmium) {
			registerMaterial(Materials.cadmium.getName(), false, true);
		}

		if (Options.enableChromium) {
			registerMaterial(Materials.chromium.getName(), false, true);
		}

		if (Options.enableGalvanizedSteel) {
			registerMaterial(Materials.galvanizedSteel.getName(), false, true);
			registerAlloy(Materials.galvanizedSteel.getName(), 2, new String[] { "steel", "zinc" }, new int[] { 1, 1 });
		}

		if (Options.enableIridium) {
			registerMaterial(Materials.iridium.getName(), false, true);
		}

		if (Options.enableMagnesium) {
			registerMaterial(Materials.magnesium.getName(), false, true);
		}

		if (Options.enableManganese) {
			registerMaterial(Materials.manganese.getName(), false, true);
		}

		if (Options.enableNichrome) {
			registerMaterial(Materials.nichrome.getName(), false, true);
			registerAlloy(Materials.nichrome.getName(), 2, new String[] { "nickel", "chromium" }, new int[] { 1, 1 });
		}

		if (Options.enableOsmium) {
			registerMaterial(Materials.osmium.getName(), false, true);
		}

		if (Options.enablePlutonium) {
			registerMaterial(Materials.plutonium.getName(), false, true);
		}

		if (Options.enableRutile) {
			registerMaterial(Materials.rutile.getName(), false, true);
		}

		if (Options.enableStainlessSteel) {
			registerMaterial(Materials.stainlessSteel.getName(), false, true);
			registerAlloy(Materials.stainlessSteel.getName(), 2, new String[] { "steel", "chromium" }, new int[] { 1, 1 });
		}

		if (Options.enableTantalum) {
			registerMaterial(Materials.tantalum.getName(), false, true);
		}

		if (Options.enableTitanium) {
			registerMaterial(Materials.titanium.getName(), false, true);
			registerAlloy(Materials.titanium.getName(), 2, new String[] { "rutile", "magnesium" }, new int[] { 1, 1 });
		}

		if (Options.enableTungsten) {
			registerMaterial(Materials.tungsten.getName(), false, true);
		}

		if (Options.enableUranium) {
			registerMaterial(Materials.uranium.getName(), false, true);
		}

		if (Options.enableZirconium) {
			registerMaterial(Materials.zirconium.getName(), false, true);
		}

		initDone = true;
	}
}

package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.init.Materials;
import com.mcmoddev.modernmetals.util.Config.Options;

import com.mcmoddev.modernmetals.integration.ModernMetalsPlugin;
import com.mcmoddev.lib.integration.IIntegration;

@ModernMetalsPlugin(Mekanism.PLUGIN_MODID)
public class Mekanism extends com.mcmoddev.lib.integration.plugins.Mekanism implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !com.mcmoddev.basemetals.util.Config.Options.enableMekanism) {
			return;
		}

		if (Options.enableAluminum) {
			addOreMultiplicationRecipes(Materials.aluminum);
		}

		if (Options.enableCadmium) {
			addOreMultiplicationRecipes(Materials.cadmium);
		}

		if (Options.enableChromium) {
			addOreMultiplicationRecipes(Materials.chromium);
		}

		if (Options.enableIridium) {
			addOreMultiplicationRecipes(Materials.iridium);
		}

		if (Options.enableMagnesium) {
			addOreMultiplicationRecipes(Materials.magnesium);
		}

		if (Options.enableManganese) {
			addOreMultiplicationRecipes(Materials.manganese);
		}

		if (Options.enablePlutonium) {
			addOreMultiplicationRecipes(Materials.plutonium);
		}

		if (Options.enableRutile) {
			addOreMultiplicationRecipes(Materials.rutile);
		}

		if (Options.enableTantalum) {
			addOreMultiplicationRecipes(Materials.tantalum);
		}

		if (Options.enableTitanium) {
			addOreMultiplicationRecipes(Materials.titanium);
		}

		if (Options.enableTungsten) {
			addOreMultiplicationRecipes(Materials.tungsten);
		}

		if (Options.enableUranium) {
			addOreMultiplicationRecipes(Materials.uranium);
		}

		if (Options.enableZirconium) {
			addOreMultiplicationRecipes(Materials.zirconium);
		}

		initDone = true;
	}
}

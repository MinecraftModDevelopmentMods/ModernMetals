package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.modernmetals.init.Materials;
import com.mcmoddev.modernmetals.util.Config.Options;

/**
 *
 * @author Jasmine Iwanek
 *
 */
public class Recipes extends com.mcmoddev.lib.init.Recipes {

	private static boolean initDone = false;

	private Recipes() {
		throw new IllegalAccessError("Not a instantiable class");
	}

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		Materials.init();
		Blocks.init();
		Items.init();

		initModSpecificRecipes();

		initDone = true;
	}

	private static void initModSpecificRecipes() {
		if (Options.materialEnabled(MaterialNames.ALUMINUM)) {
			addAdditionalOredicts(Materials.getMaterialByName(MaterialNames.ALUMINUM), "Aluminium");
			addAdditionalOredicts(Materials.getMaterialByName(MaterialNames.ALUMINUM), "Bauxite");
		}

		if (Options.materialEnabled(MaterialNames.ALUMINUM_BRASS)) {
			addSimpleAlloyRecipe(Materials.getMaterialByName(MaterialNames.ALUMINUM_BRASS), 2, "Aluminum", "Brass");
			addAdditionalOredicts(Materials.getMaterialByName(MaterialNames.ALUMINUM_BRASS), "AluminumBrass");
			addAdditionalOredicts(Materials.getMaterialByName(MaterialNames.ALUMINUM_BRASS), "Aluminiumbrass");
			addAdditionalOredicts(Materials.getMaterialByName(MaterialNames.ALUMINUM_BRASS), "AluminiumBrass");
			addAdditionalOredicts(Materials.getMaterialByName(MaterialNames.ALUMINUM_BRASS), "Alubrass");
			addAdditionalOredicts(Materials.getMaterialByName(MaterialNames.ALUMINUM_BRASS), "AluBrass");
		}

		if (Options.materialEnabled(MaterialNames.CHROMIUM)) {
			addAdditionalOredicts(Materials.getMaterialByName(MaterialNames.CHROMIUM), "Chrome");
		}

		if (Options.materialEnabled(MaterialNames.GALVANIZED_STEEL)) {
			addSimpleAlloyRecipe(Materials.getMaterialByName(MaterialNames.GALVANIZED_STEEL), 2, "Steel", "Zinc");
			addAdditionalOredicts(Materials.getMaterialByName(MaterialNames.GALVANIZED_STEEL), "GalvinizedSteel");
		}

		if (Options.materialEnabled(MaterialNames.NICHROME)) {
			addSimpleAlloyRecipe(Materials.getMaterialByName(MaterialNames.NICHROME), 2, "Nickel", "Chromium");
		}

		if (Options.materialEnabled(MaterialNames.STAINLESS_STEEL)) {
			addSimpleAlloyRecipe(Materials.getMaterialByName(MaterialNames.STAINLESS_STEEL), 2, "Steel", "Chromium");
			addAdditionalOredicts(Materials.getMaterialByName(MaterialNames.STAINLESS_STEEL), "StainlessSteel");
		}

		if (Options.materialEnabled(MaterialNames.TITANIUM)) {
			addSimpleAlloyRecipe(Materials.getMaterialByName(MaterialNames.TITANIUM), 2, "Rutile", "Magnesium");
		}

		if (Options.materialEnabled(MaterialNames.TUNGSTEN)) {
			addAdditionalOredicts(Materials.getMaterialByName(MaterialNames.TUNGSTEN), "Wolfram");
		}
	}
}

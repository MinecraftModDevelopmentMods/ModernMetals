package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.data.SharedStrings;
import com.mcmoddev.lib.material.MMDMaterial;

/**
 *
 * @author Jasmine Iwanek
 *
 */
public class Recipes extends com.mcmoddev.lib.init.Recipes {

	private static boolean initDone = false;

	private Recipes() {
		throw new IllegalAccessError(SharedStrings.NOT_INSTANTIABLE);
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
		if (Materials.hasMaterial(MaterialNames.ALUMINUM)) {
			final MMDMaterial aluminum = Materials.getMaterialByName(MaterialNames.ALUMINUM);
			addAdditionalOredicts(aluminum, "Aluminium");
			addAdditionalOredicts(aluminum, "Bauxite");
		}

		if (Materials.hasMaterial(MaterialNames.ALUMINUM_BRASS)) {
			final MMDMaterial aluminumBrass = Materials.getMaterialByName(MaterialNames.ALUMINUM_BRASS);
			addSimpleAlloyRecipe(aluminumBrass, 2, MaterialNames.ALUMINUM, com.mcmoddev.basemetals.data.MaterialNames.BRASS);
			addAdditionalOredicts(aluminumBrass, "AluminumBrass");
			addAdditionalOredicts(aluminumBrass, "Aluminiumbrass");
			addAdditionalOredicts(aluminumBrass, "AluminiumBrass");
			addAdditionalOredicts(aluminumBrass, "Alubrass");
			addAdditionalOredicts(aluminumBrass, "AluBrass");
		}

		if (Materials.hasMaterial(MaterialNames.CHROMIUM)) {
			addAdditionalOredicts(MaterialNames.CHROMIUM, "Chrome");
		}

		if (Materials.hasMaterial(MaterialNames.GALVANIZED_STEEL)) {
			final MMDMaterial galvanizedSteel = Materials.getMaterialByName(MaterialNames.GALVANIZED_STEEL);
			addSimpleAlloyRecipe(galvanizedSteel, 2, com.mcmoddev.basemetals.data.MaterialNames.STEEL, com.mcmoddev.basemetals.data.MaterialNames.ZINC);
			addAdditionalOredicts(galvanizedSteel, "GalvinizedSteel");
		}

		if (Materials.hasMaterial(MaterialNames.NICHROME)) {
			addSimpleAlloyRecipe(MaterialNames.NICHROME, 2, com.mcmoddev.basemetals.data.MaterialNames.NICKEL, MaterialNames.CHROMIUM);
		}

		if (Materials.hasMaterial(MaterialNames.STAINLESS_STEEL)) {
			final MMDMaterial stainlessSteel = Materials.getMaterialByName(MaterialNames.STAINLESS_STEEL);
			addSimpleAlloyRecipe(stainlessSteel, 2, com.mcmoddev.basemetals.data.MaterialNames.STEEL, MaterialNames.CHROMIUM);
			addAdditionalOredicts(stainlessSteel, "StainlessSteel");
		}

		if (Materials.hasMaterial(MaterialNames.TITANIUM)) {
			addSimpleAlloyRecipe(MaterialNames.TITANIUM, 2, MaterialNames.RUTILE, MaterialNames.MAGNESIUM);
		}

		if (Materials.hasMaterial(MaterialNames.TUNGSTEN)) {
			addAdditionalOredicts(MaterialNames.TUNGSTEN, "Wolfram");
		}
	}
}

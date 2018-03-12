package com.mcmoddev.modernmetals.init;

import com.mcmoddev.lib.data.SharedStrings;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.modernmetals.data.MaterialNames;

/**
 *
 * @author Jasmine Iwanek
 *
 */
public class Recipes extends com.mcmoddev.lib.init.Recipes {

	private Recipes() {
		throw new IllegalAccessError(SharedStrings.NOT_INSTANTIABLE);
	}

	/**
	 *
	 */
	public static void init() {
		initModSpecificRecipes();
	}

	private static void initModSpecificRecipes() {
		if (Materials.hasMaterial(MaterialNames.ALUMINUM)) {
			final MMDMaterial aluminum = Materials.getMaterialByName(MaterialNames.ALUMINUM);

			addAdditionalOredicts(aluminum, "Aluminium");
			addAdditionalOredicts(aluminum, "Bauxite");
		}

		if (Materials.hasMaterial(MaterialNames.ALUMINUM_BRASS)) {
			final MMDMaterial aluminumBrass = Materials.getMaterialByName(MaterialNames.ALUMINUM_BRASS);

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

			addAdditionalOredicts(galvanizedSteel, "GalvinizedSteel");
		}

		if (Materials.hasMaterial(MaterialNames.NICHROME)) {
			final MMDMaterial nichrome = Materials.getMaterialByName(MaterialNames.NICHROME);

			addAdditionalOredicts(nichrome, "Nichrome");
		}

		if (Materials.hasMaterial(MaterialNames.STAINLESS_STEEL)) {
			final MMDMaterial stainlessSteel = Materials.getMaterialByName(MaterialNames.STAINLESS_STEEL);

			addAdditionalOredicts(stainlessSteel, "StainlessSteel");
		}

		if (Materials.hasMaterial(MaterialNames.TUNGSTEN)) {
			addAdditionalOredicts(MaterialNames.TUNGSTEN, "Wolfram");
		}
	}
}

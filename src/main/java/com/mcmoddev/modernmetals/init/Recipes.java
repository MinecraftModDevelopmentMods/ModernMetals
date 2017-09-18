package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.modernmetals.init.Materials;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.util.ConfigBase.Options;

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
		if (Options.isMaterialEnabled(MaterialNames.ALUMINUM)) {
			final MMDMaterial material = Materials.getMaterialByName(MaterialNames.ALUMINUM);
			addAdditionalOredicts(material, "Aluminium");
			addAdditionalOredicts(material, "Bauxite");
		}

		if (Options.isMaterialEnabled(MaterialNames.ALUMINUM_BRASS)) {
			final MMDMaterial material = Materials.getMaterialByName(MaterialNames.ALUMINUM_BRASS);
			addAdditionalOredicts(material, "AluminumBrass");
			addAdditionalOredicts(material, "Aluminiumbrass");
			addAdditionalOredicts(material, "AluminiumBrass");
			addAdditionalOredicts(material, "Alubrass");
			addAdditionalOredicts(material, "AluBrass");
		}

		if (Options.isMaterialEnabled(MaterialNames.CHROMIUM)) {
		}

		if (Options.isMaterialEnabled(MaterialNames.GALVANIZED_STEEL)) {
			final MMDMaterial material = Materials.getMaterialByName(MaterialNames.GALVANIZED_STEEL);
			addAdditionalOredicts(material, "GalvinizedSteel");
		}

		if (Options.isMaterialEnabled(MaterialNames.NICHROME)) {
			final MMDMaterial material = Materials.getMaterialByName(MaterialNames.NICHROME);
			addAdditionalOredicts(material, "Nichrome");
		}

		if (Options.isMaterialEnabled(MaterialNames.STAINLESS_STEEL)) {
			final MMDMaterial material = Materials.getMaterialByName(MaterialNames.STAINLESS_STEEL);
			addAdditionalOredicts(material, "StainlessSteel");
		}

		if (Options.isMaterialEnabled(MaterialNames.TUNGSTEN)) {
			addAdditionalOredicts(Materials.getMaterialByName(MaterialNames.TUNGSTEN), "Wolfram");
		}
	}
}

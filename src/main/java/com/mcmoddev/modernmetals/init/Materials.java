package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.lib.util.ConfigBase.Options;

import com.mcmoddev.lib.material.MMDMaterial.MaterialType;

/**
 * This class initializes all of the materials in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Materials extends com.mcmoddev.lib.init.Materials {

	private static boolean initDone = false;

	protected Materials() {
		throw new IllegalAccessError("Not a instantiable class");
	}

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		if (Options.isMaterialEnabled(MaterialNames.ALUMINUM)) {
			createMaterial(MaterialNames.ALUMINUM, MaterialType.METAL, 2.5, 3.75, 4.5, 0xFFC5C8C1);
		}

		if (Options.isMaterialEnabled(MaterialNames.ALUMINUM_BRASS)) {
			createAlloyMaterial(MaterialNames.ALUMINUM_BRASS, MaterialType.METAL, 1, 7.5, 4.5, 0xFFEBAA56);
		}

		if (Options.isMaterialEnabled(MaterialNames.CADMIUM)) {
			createRareMaterial(MaterialNames.CADMIUM, MaterialType.METAL, 2, 1, 4.5, 0xFFC9D4DA);
		}

		if (Options.isMaterialEnabled(MaterialNames.CHROMIUM)) {
			createMaterial(MaterialNames.CHROMIUM, MaterialType.METAL, 9, 3, 4.5, 0xFFCDCDCF);
		}

		if (Options.isMaterialEnabled(MaterialNames.GALVANIZED_STEEL)) {
			createAlloyMaterial(MaterialNames.GALVANIZED_STEEL, MaterialType.METAL, 5.5, 15.25, 4.5, 0xFF9BA6A2);
		}

		if (Options.isMaterialEnabled(MaterialNames.IRIDIUM)) {
			createRareMaterial(MaterialNames.IRIDIUM, MaterialType.METAL, 6.5, 3, 4.5, 0xFFF8EDCC);
		}

		if (Options.isMaterialEnabled(MaterialNames.MAGNESIUM)) {
			createMaterial(MaterialNames.MAGNESIUM, MaterialType.METAL, 2.5, 3.5, 4.5, 0xFF7F7F77);
		}

		if (Options.isMaterialEnabled(MaterialNames.MANGANESE)) {
			createMaterial(MaterialNames.MANGANESE, MaterialType.METAL, 5, 2.75, 4.5, 0xFFF5CFDA);
		}

		if (Options.isMaterialEnabled(MaterialNames.NICHROME)) {
			createAlloyMaterial(MaterialNames.NICHROME, MaterialType.METAL, 6, 15.5, 4.5, 0xFFDEA054);
		}

		if (Options.isMaterialEnabled(MaterialNames.OSMIUM)) {
			createMaterial(MaterialNames.OSMIUM, MaterialType.METAL, 7, 2.75, 4.5, 0xFF7C8E99);
		}

		if (Options.isMaterialEnabled(MaterialNames.PLUTONIUM)) {
			createRareMaterial(MaterialNames.PLUTONIUM, MaterialType.METAL, 4, 7.5, 4.5, 0xFFB333EA);
		}

		if (Options.isMaterialEnabled(MaterialNames.RUTILE)) {
			createMaterial(MaterialNames.RUTILE, MaterialType.METAL, 6, 0.25, 4.5, 0xFFBF928B);
		}

		if (Options.isMaterialEnabled(MaterialNames.STAINLESS_STEEL)) {
			createAlloyMaterial(MaterialNames.STAINLESS_STEEL, MaterialType.METAL, 6, 16, 4.5, 0xFFC5BFC1);
		}

		if (Options.isMaterialEnabled(MaterialNames.TANTALUM)) {
			createMaterial(MaterialNames.TANTALUM, MaterialType.METAL, 6.5, 17.5, 4.5, 0xFFC4BEC2);
		}

		if (Options.isMaterialEnabled(MaterialNames.TITANIUM)) {
			createSpecialMaterial(MaterialNames.TITANIUM, MaterialType.METAL, 6.5, 16.25, 4.5, 0xFF73787E);
		}

		if (Options.isMaterialEnabled(MaterialNames.TUNGSTEN)) {
			createMaterial(MaterialNames.TUNGSTEN, MaterialType.METAL, 7.5, 12.5, 4.5, 0xFF969696);
		}

		if (Options.isMaterialEnabled(MaterialNames.URANIUM)) {
			createRareMaterial(MaterialNames.URANIUM, MaterialType.METAL, 6, 16.25, 4.5, 0xFFA7B345);
		}

		if (Options.isMaterialEnabled(MaterialNames.ZIRCONIUM)) {
			createMaterial(MaterialNames.ZIRCONIUM, MaterialType.METAL, 5, 3.5, 4.5, 0xFF929793);
		}

		initDone = true;
	}
}

package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.util.Config.Options;

import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.material.MMDMaterial.MaterialType;

/**
 * This class initializes all of the materials in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Materials extends com.mcmoddev.lib.init.Materials {

	/** Aluminum */
	public static MMDMaterial aluminum;
	/** Aluminum-Brass*/
	public static MMDMaterial aluminumBrass;
	/** Cadmium */
	public static MMDMaterial cadmium;
	/** Chromium */
	public static MMDMaterial chromium;
	/** Galvanized Steel */
	public static MMDMaterial galvanizedSteel;
	/** Iridium */
	public static MMDMaterial iridium;
	/** Magnesium */
	public static MMDMaterial magnesium;
	/** Manganese */
	public static MMDMaterial manganese;
	/** Nichrome */
	public static MMDMaterial nichrome;
	/** Osmium */
	public static MMDMaterial osmium;
	/** Plutonium */
	public static MMDMaterial plutonium;
	/** Rutile */
	public static MMDMaterial rutile;
	/** Stainless Steel */
	public static MMDMaterial stainlessSteel;
	/** Tantalum */
	public static MMDMaterial tantalum;
	/** Titanium */
	public static MMDMaterial titanium;
	/** Tungsten */
	public static MMDMaterial tungsten;
	/** Uranium */
	public static MMDMaterial uranium;
	/** Zirconium */
	public static MMDMaterial zirconium;

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

		if (Options.enableAluminum) {
			aluminum = createMaterial("aluminum", MaterialType.METAL, 2.5, 3.75, 4.5, 0xFFC5C8C1);
		}

		if (Options.enableAluminumBrass) {
			aluminumBrass = createAlloyMaterial("aluminumbrass", MaterialType.METAL, 1, 7.5, 4.5, 0xFFEBAA56);
		}

		if (Options.enableCadmium) {
			cadmium = createRareMaterial("cadmium", MaterialType.METAL, 2, 1, 4.5, 0xFFC9D4DA);
		}

		if (Options.enableChromium) {
			chromium = createMaterial("chromium", MaterialType.METAL, 9, 3, 4.5, 0xFFCDCDCF);
		}

		if (Options.enableGalvanizedSteel) {
			galvanizedSteel = createAlloyMaterial("galvanizedsteel", MaterialType.METAL, 5.5, 15.25, 4.5, 0xFF9BA6A2);
		}

		if (Options.enableIridium) {
			iridium = createRareMaterial("iridium", MaterialType.METAL, 6.5, 3, 4.5, 0xFFF8EDCC);
		}

		if (Options.enableMagnesium) {
			magnesium = createMaterial("magnesium", MaterialType.METAL, 2.5, 3.5, 4.5, 0xFF7F7F77);
		}

		if (Options.enableManganese) {
			manganese = createMaterial("manganese", MaterialType.METAL, 5, 2.75, 4.5, 0xFFF5CFDA);
		}

		if (Options.enableNichrome) {
			nichrome = createAlloyMaterial("nichrome", MaterialType.METAL, 6, 15.5, 4.5, 0xFFDEA054);
		}

		if (Options.enableOsmium) {
			osmium = createMaterial("osmium", MaterialType.METAL, 7, 2.75, 4.5, 0xFF7C8E99);
		}

		if (Options.enablePlutonium) {
			plutonium = createRareMaterial("plutonium", MaterialType.METAL, 4, 7.5, 4.5, 0xFFB333EA);
		}

		if (Options.enableRutile) {
			rutile = createMaterial("rutile", MaterialType.METAL, 6, 0.25, 4.5, 0xFFBF928B);
		}

		if (Options.enableStainlessSteel) {
			stainlessSteel = createAlloyMaterial("stainlesssteel", MaterialType.METAL, 6, 16, 4.5, 0xFFC5BFC1);
		}

		if (Options.enableTantalum) {
			tantalum = createMaterial("tantalum", MaterialType.METAL, 6.5, 17.5, 4.5, 0xFFC4BEC2);
		}

		if (Options.enableTitanium) {
			titanium = createSpecialMaterial("titanium", MaterialType.METAL, 6.5, 16.25, 4.5, 0xFF73787E);
		}

		if (Options.enableTungsten) {
			tungsten = createMaterial("tungsten", MaterialType.METAL, 7.5, 12.5, 4.5, 0xFF969696);
		}

		if (Options.enableUranium) {
			uranium = createRareMaterial("uranium", MaterialType.METAL, 6, 16.25, 4.5, 0xFFA7B345);
		}

		if (Options.enableZirconium) {
			zirconium = createMaterial("zirconium", MaterialType.METAL, 5, 3.5, 4.5, 0xFF929793);
		}

		initDone = true;
	}
}

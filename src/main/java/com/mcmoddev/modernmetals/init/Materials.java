package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.util.Config.Options;

import com.mcmoddev.lib.material.MetalMaterial;

/**
 * This class initializes all of the materials in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Materials extends com.mcmoddev.lib.init.Materials {

	/** Aluminum */
	public static MetalMaterial aluminum;
	/** Aluminum-Brass*/
	public static MetalMaterial aluminumBrass;
	/** Cadmium */
	public static MetalMaterial cadmium;
	/** Chromium */
	public static MetalMaterial chromium;
	/** Galvanized Steel */
	public static MetalMaterial galvanizedSteel;
	/** Iridium */
	public static MetalMaterial iridium;
	/** Magnesium */
	public static MetalMaterial magnesium;
	/** Manganese */
	public static MetalMaterial manganese;
	/** Nichrome */
	public static MetalMaterial nichrome;
	/** Osmium */
	public static MetalMaterial osmium;
	/** Plutonium */
	public static MetalMaterial plutonium;
	/** Rutile */
	public static MetalMaterial rutile;
	/** Stainless Steel */
	public static MetalMaterial stainlessSteel;
	/** Tantalum */
	public static MetalMaterial tantalum;
	/** Titanium */
	public static MetalMaterial titanium;
	/** Tungsten */
	public static MetalMaterial tungsten;
	/** Uranium */
	public static MetalMaterial uranium;
	/** Zirconium */
	public static MetalMaterial zirconium;

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
			aluminum = createMaterial("aluminum", 2.5, 3.75, 4.5, 0xFFC5C8C1);
			aluminum.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enableAluminumBrass) {
			aluminumBrass = createAlloyMaterial("aluminumbrass", 1, 7.5, 4.5, 0xFFEBAA56);
			aluminumBrass.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enableCadmium) {
			cadmium = createRareMaterial("cadmium", 2, 1, 4.5, 0xFFC9D4DA);
			cadmium.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enableChromium) {
			chromium = createMaterial("chromium", 9, 3, 4.5, 0xFFCDCDCF);
			chromium.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enableGalvanizedSteel) {
			galvanizedSteel = createAlloyMaterial("galvanizedsteel", 5.5, 15.25, 4.5, 0xFF9BA6A2);
			galvanizedSteel.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enableIridium) {
			iridium = createRareMaterial("iridium", 6.5, 3, 4.5, 0xFFF8EDCC);
			iridium.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enableMagnesium) {
			magnesium = createMaterial("magnesium", 2.5, 3.5, 4.5, 0xFF7F7F77);
			magnesium.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enableManganese) {
			manganese = createMaterial("manganese", 5, 2.75, 4.5, 0xFFF5CFDA);
			manganese.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enableNichrome) {
			nichrome = createAlloyMaterial("nichrome", 6, 15.5, 4.5, 0xFFDEA054);
			nichrome.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enableOsmium) {
			osmium = createMaterial("osmium", 7, 2.75, 4.5, 0xFF7C8E99);
			osmium.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enablePlutonium) {
			plutonium = createRareMaterial("plutonium", 4, 7.5, 4.5, 0xFFB333EA);
			plutonium.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enableRutile) {
			rutile = createMaterial("rutile", 6, 0.25, 4.5, 0xFFBF928B);
			rutile.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enableStainlessSteel) {
			stainlessSteel = createAlloyMaterial("stainlesssteel", 6, 16, 4.5, 0xFFC5BFC1);
			stainlessSteel.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enableTantalum) {
			tantalum = createMaterial("tantalum", 6.5, 17.5, 4.5, 0xFFC4BEC2);
			tantalum.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enableTitanium) {
			titanium = createSpecialMaterial("titanium", 6.5, 16.25, 4.5, 0xFF73787E);
			titanium.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enableTungsten) {
			tungsten = createMaterial("tungsten", 7.5, 12.5, 4.5, 0xFF969696);
			tungsten.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enableUranium) {
			uranium = createRareMaterial("uranium", 6, 16.25, 4.5, 0xFFA7B345);
			uranium.materialType = MetalMaterial.MaterialType.METAL;
		}

		if (Options.enableZirconium) {
			zirconium = createMaterial("zirconium", 5, 3.5, 4.5, 0xFF929793);
			zirconium.materialType = MetalMaterial.MaterialType.METAL;
		}

		initDone = true;
	}
}

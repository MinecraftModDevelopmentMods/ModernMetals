package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.util.Config.Options;

/**
 * This class initializes all blocks in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Blocks extends com.mcmoddev.lib.init.Blocks {

	private static boolean initDone = false;

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		Materials.init();
		ItemGroups.init();

		if (Options.enableAluminum) {
			createBlocksFull(Materials.aluminum);
		}

		if (Options.enableAluminumBrass) {
			createBlocksFull(Materials.aluminumBrass);
		}

		if (Options.enableCadmium) {
			createBlocksFull(Materials.cadmium);
		}

		if (Options.enableChromium) {
			createBlocksFull(Materials.chromium);
		}

		if (Options.enableGalvanizedSteel) {
			createBlocksFull(Materials.galvanizedSteel);
		}

		if (Options.enableIridium) {
			createBlocksFull(Materials.iridium);
		}

		if (Options.enableMagnesium) {
			createBlocksFull(Materials.magnesium);
		}

		if (Options.enableManganese) {
			createBlocksFull(Materials.manganese);
		}

		if (Options.enableNichrome) {
			createBlocksFull(Materials.nichrome);
		}

		if (Options.enableOsmium) {
			createBlocksFull(Materials.osmium);
		}

		if (Options.enablePlutonium) {
			createBlocksFull(Materials.plutonium);
		}

		if (Options.enableRutile) {
			createBlocksFull(Materials.rutile);
		}

		if (Options.enableStainlessSteel) {
			createBlocksFull(Materials.stainlessSteel);
		}

		if (Options.enableTantalum) {
			createBlocksFull(Materials.tantalum);
		}

		if (Options.enableTitanium) {
			createBlocksFull(Materials.titanium);
		}

		if (Options.enableTungsten) {
			createBlocksFull(Materials.tungsten);
		}

		if (Options.enableUranium) {
			createBlocksFull(Materials.uranium);
		}

		if (Options.enableZirconium) {
			createBlocksFull(Materials.zirconium);
		}

		initDone = true;
	}
}

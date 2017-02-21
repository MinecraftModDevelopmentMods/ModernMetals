package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.util.Config.Options;

/**
 * This class initializes all items in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Items extends com.mcmoddev.lib.init.Items {

	private static boolean initDone = false;

	protected Items() {
		throw new IllegalAccessError("Not a instantiable class");
	}

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		Blocks.init();

		if (Options.enableAluminum) {
			createItemsFull(Materials.aluminum);
			createItemsModSupport(Materials.aluminum);
		}

		if (Options.enableAluminumBrass) {
			createItemsFull(Materials.aluminumBrass);
		}

		if (Options.enableCadmium) {
			createItemsFull(Materials.cadmium);
			createItemsModSupport(Materials.cadmium);
		}

		if (Options.enableChromium) {
			createItemsFull(Materials.chromium);
			createItemsModSupport(Materials.chromium);
		}

		if (Options.enableGalvanizedSteel) {
			createItemsFull(Materials.galvanizedSteel);
		}

		if (Options.enableIridium) {
			createItemsFull(Materials.iridium);
			createItemsModSupport(Materials.iridium);
		}

		if (Options.enableMagnesium) {
			createItemsFull(Materials.magnesium);
			createItemsModSupport(Materials.magnesium);
		}

		if (Options.enableManganese) {
			createItemsFull(Materials.manganese);
			createItemsModSupport(Materials.manganese);
		}

		if (Options.enableNichrome) {
			createItemsFull(Materials.nichrome);
		}

		if (Options.enableOsmium) {
			createItemsFull(Materials.osmium);
			createItemsModIC2(Materials.osmium);
		}

		if (Options.enablePlutonium) {
			createItemsFull(Materials.plutonium);
			createItemsModSupport(Materials.plutonium);
		}

		if (Options.enableRutile) {
			createItemsFull(Materials.rutile);
			createItemsModSupport(Materials.rutile);
		}

		if (Options.enableStainlessSteel) {
			createItemsFull(Materials.stainlessSteel);
		}

		if (Options.enableTantalum) {
			createItemsFull(Materials.tantalum);
			createItemsModSupport(Materials.tantalum);
		}

		if (Options.enableTitanium) {
			createItemsFull(Materials.titanium);
			createItemsModSupport(Materials.titanium);
		}

		if (Options.enableTungsten) {
			createItemsFull(Materials.tungsten);
			createItemsModSupport(Materials.tungsten);
		}

		if (Options.enableUranium) {
			createItemsFull(Materials.uranium);
			createItemsModMekanism(Materials.uranium);
		}

		if (Options.enableZirconium) {
			createItemsFull(Materials.zirconium);
			createItemsModSupport(Materials.zirconium);
		}

		// addToMetList() // May not be needed, check

		initDone = true;
	}
}

package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.util.Config.Options;

/**
 * This class initializes all fluids in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Fluids extends com.mcmoddev.lib.init.Fluids {

	private static boolean initDone = false;

	private Fluids() {
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
			addFluid(Materials.aluminum, 2000, 10000, 330, 10);
			addFluidBlock(Materials.aluminum);
		}

		if (Options.enableAluminumBrass) {
			addFluid(Materials.aluminumBrass, 2000, 10000, 500, 10);
			addFluidBlock(Materials.aluminumBrass);
		}

		if (Options.enableCadmium) {
			addFluid(Materials.cadmium, 2000, 10000, 300, 10);
			addFluidBlock(Materials.cadmium);
		}

		if (Options.enableChromium) {
			addFluid(Materials.chromium, 2000, 10000, 769, 10);
			addFluidBlock(Materials.chromium);
		}

		if (Options.enableGalvanizedSteel) {
			addFluid(Materials.galvanizedSteel, 2000, 10000, 769, 10);
			addFluidBlock(Materials.galvanizedSteel);
		}

		if (Options.enableIridium) {
			addFluid(Materials.iridium, 2000, 10000, 769, 10);
			addFluidBlock(Materials.iridium);
		}

		if (Options.enableMagnesium) {
			addFluid(Materials.magnesium, 2000, 10000, 769, 10);
			addFluidBlock(Materials.magnesium);
		}

		if (Options.enableManganese) {
			addFluid(Materials.manganese, 2000, 10000, 769, 10);
			addFluidBlock(Materials.manganese);
		}

		if (Options.enableNichrome) {
			addFluid(Materials.nichrome, 2000, 10000, 769, 10);
			addFluidBlock(Materials.nichrome);
		}

		if (Options.enableOsmium) {
			addFluid(Materials.osmium, 2000, 10000, 769, 10);
			addFluidBlock(Materials.osmium);
		}

		if (Options.enablePlutonium) {
			addFluid(Materials.plutonium, 2000, 10000, 769, 10);
			addFluidBlock(Materials.plutonium);
		}

		if (Options.enableRutile) {
			addFluid(Materials.rutile, 2000, 10000, 769, 10);
			addFluidBlock(Materials.rutile);
		}

		if (Options.enableStainlessSteel) {
			addFluid(Materials.stainlessSteel, 2000, 10000, 769, 10);
			addFluidBlock(Materials.stainlessSteel);
		}

		if (Options.enableTantalum) {
			addFluid(Materials.tantalum, 2000, 10000, 769, 10);
			addFluidBlock(Materials.tantalum);
		}

		if (Options.enableTitanium) {
			addFluid(Materials.titanium, 2000, 10000, 769, 10);
			addFluidBlock(Materials.titanium);
		}

		if (Options.enableTungsten) {
			addFluid(Materials.tungsten, 2000, 10000, 769, 10);
			addFluidBlock(Materials.tungsten);
		}

		if (Options.enableUranium) {
			addFluid(Materials.uranium, 2000, 10000, 769, 10);
			addFluidBlock(Materials.uranium);
		}

		if (Options.enableZirconium) {
			addFluid(Materials.zirconium, 2000, 10000, 769, 10);
			addFluidBlock(Materials.zirconium);
		}

		initDone = true;
	}
}

package com.mcmoddev.modernmetals.init;

import com.mcmoddev.basemetals.material.MetalMaterial;
import com.mcmoddev.modernmetals.util.Config.Options;

/**
 * This class initializes all fluids in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Fluids extends com.mcmoddev.lib.init.Fluids {

	private static boolean initDone = false;

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		MetalMaterial material;

		// fluids and fluid blocks
		if (Options.ENABLE_ALUMINUM) {
			material = Materials.aluminum;

			addFluid(material, 2000, 10000, 330, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_ALUMINUMBRASS) {
			material = Materials.aluminumbrass;

			addFluid(material, 2000, 10000, 500, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_CADMIUM) {
			material = Materials.cadmium;

			addFluid(material, 2000, 10000, 300, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_CHROMIUM) {
			material = Materials.chromium;

			addFluid(material, 2000, 10000, 769, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_GALVANIZEDSTEEL) {
			material = Materials.galvanizedsteel;

			addFluid(material, 2000, 10000, 769, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_IRIDIUM) {
			material = Materials.iridium;

			addFluid(material, 2000, 10000, 769, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_MAGNESIUM) {
			material = Materials.magnesium;

			addFluid(material, 2000, 10000, 769, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_MANGANESE) {
			material = Materials.manganese;

			addFluid(material, 2000, 10000, 769, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_NICHROME) {
			material = Materials.nichrome;

			addFluid(material, 2000, 10000, 769, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_OSMIUM) {
			material = Materials.osmium;

			addFluid(material, 2000, 10000, 769, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_PLUTONIUM) {
			material = Materials.plutonium;

			addFluid(material, 2000, 10000, 769, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_RUTILE) {
			material = Materials.rutile;

			addFluid(material, 2000, 10000, 769, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_STAINLESSSTEEL) {
			material = Materials.stainlesssteel;

			addFluid(material, 2000, 10000, 769, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_TANTALUM) {
			material = Materials.tantalum;

			addFluid(material, 2000, 10000, 769, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_TITANIUM) {
			material = Materials.titanium;

			addFluid(material, 2000, 10000, 769, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_TUNGSTEN) {
			material = Materials.tungsten;

			addFluid(material, 2000, 10000, 769, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_URANIUM) {
			material = Materials.uranium;

			addFluid(material, 2000, 10000, 769, 10);
			addFluidBlock(material);
		}
		if (Options.ENABLE_ZIRCONIUM) {
			material = Materials.zirconium;

			addFluid(material, 2000, 10000, 769, 10);
			addFluidBlock(material);
		}

		initDone = true;
	}
}

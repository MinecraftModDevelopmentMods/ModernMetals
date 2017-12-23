package com.mcmoddev.modernmetals.init;

import java.util.Arrays;
import java.util.List;

import com.mcmoddev.basemetals.init.Materials;
import com.mcmoddev.modernmetals.data.MaterialNames;

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

		if (Materials.hasMaterial(MaterialNames.ALUMINUM)) {
			addFluid(MaterialNames.ALUMINUM, 2000, 10000, 330, 10);
			addFluidBlock(MaterialNames.ALUMINUM);
		}

		if (Materials.hasMaterial(MaterialNames.ALUMINUM_BRASS)) {
			addFluid(MaterialNames.ALUMINUM_BRASS, 2000, 10000, 500, 10);
			addFluidBlock(MaterialNames.ALUMINUM_BRASS);
		}

		if (Materials.hasMaterial(MaterialNames.CADMIUM)) {
			addFluid(MaterialNames.CADMIUM, 2000, 10000, 300, 10);
			addFluidBlock(MaterialNames.CADMIUM);
		}

		List<String> standardMaterialsMap = Arrays.asList(MaterialNames.CHROMIUM,
				MaterialNames.GALVANIZED_STEEL,
				MaterialNames.IRIDIUM,
				MaterialNames.MAGNESIUM,
				MaterialNames.MANGANESE,
				MaterialNames.NICHROME,
				MaterialNames.OSMIUM,
				MaterialNames.PLUTONIUM,
				MaterialNames.RUTILE,
				MaterialNames.STAINLESS_STEEL,
				MaterialNames.TANTALUM,
				MaterialNames.TITANIUM,
				MaterialNames.TUNGSTEN,
				MaterialNames.URANIUM,
				MaterialNames.ZIRCONIUM
			);

		standardMaterialsMap.stream()
			.filter(Materials::hasMaterial)
			.forEach(name -> {
				addFluid(name, 2000, 10000, 769, 10);
				addFluidBlock(name);
			});

		initDone = true;
	}
}

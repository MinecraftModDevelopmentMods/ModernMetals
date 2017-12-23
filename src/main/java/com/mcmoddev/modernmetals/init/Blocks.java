package com.mcmoddev.modernmetals.init;

import java.util.Arrays;

import javax.annotation.Nonnull;

import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.modernmetals.data.MaterialNames;

import net.minecraft.block.Block;

/**
 * This class initializes all blocks in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Blocks extends com.mcmoddev.lib.init.Blocks {

	private static boolean initDone = false;

	protected Blocks() {
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
		ItemGroups.init();

		String[] simpleFullBlocks = new String[] { MaterialNames.ALUMINUM,
				MaterialNames.ALUMINUM_BRASS,
				MaterialNames.CADMIUM,
				MaterialNames.CHROMIUM,
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
			};

		Arrays.stream(simpleFullBlocks)
		.filter(Materials::hasMaterial)
		.forEach(name -> {
			final MMDMaterial material = Materials.getMaterialByName(name);

			create(Names.BLOCK, material);
			create(Names.PLATE, material);
			create(Names.ORE, material);
			create(Names.BARS, material);
			create(Names.DOOR, material);
			create(Names.TRAPDOOR, material);

			create(Names.BUTTON, material);
			create(Names.SLAB, material);
			create(Names.DOUBLE_SLAB, material);
			create(Names.LEVER, material);
			create(Names.PRESSURE_PLATE, material);
			create(Names.STAIRS, material);
			create(Names.WALL, material);
		});

		initDone = true;
	}

	protected static Block create(@Nonnull final Names name, @Nonnull final MMDMaterial material) {
		return create(name, material, ItemGroups.myTabs.blocksTab);
	}
}

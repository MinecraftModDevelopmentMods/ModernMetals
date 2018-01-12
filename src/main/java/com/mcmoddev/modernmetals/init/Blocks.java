package com.mcmoddev.modernmetals.init;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.data.SharedStrings;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * This class initializes all blocks in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Blocks extends com.mcmoddev.lib.init.Blocks {

	private static boolean initDone = false;

	protected Blocks() {
		throw new IllegalAccessError(SharedStrings.NOT_INSTANTIABLE);
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

		final List<String> materials = Arrays.asList(MaterialNames.ALUMINUM, MaterialNames.ALUMINUM_BRASS,
				MaterialNames.BERYLLIUM, MaterialNames.BORON, MaterialNames.CADMIUM, MaterialNames.CHROMIUM,
				MaterialNames.GALVANIZED_STEEL, MaterialNames.IRIDIUM, MaterialNames.MAGNESIUM, MaterialNames.MANGANESE,
				MaterialNames.NICHROME, MaterialNames.OSMIUM, MaterialNames.PLUTONIUM, MaterialNames.RUTILE,
				MaterialNames.STAINLESS_STEEL, MaterialNames.TANTALUM, MaterialNames.THORIUM, MaterialNames.TITANIUM,
				MaterialNames.TUNGSTEN, MaterialNames.URANIUM, MaterialNames.ZIRCONIUM);

		materials.stream().filter(Materials::hasMaterial)
				.filter(materialName -> !Materials.getMaterialByName(materialName).equals(Materials.emptyMaterial)).forEach(materialName -> {
					final MMDMaterial material = Materials.getMaterialByName(materialName);

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

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		for (MMDMaterial material : Materials.getMaterialsByMod(ModernMetals.MODID)) {
			for (Block block : material.getBlocks()) {
				if (block.getRegistryName().getResourceDomain().equals(ModernMetals.MODID)) {
					event.getRegistry().register(block);
				}
			}
		}
	}

	protected static Block create(@Nonnull final Names name, @Nonnull final MMDMaterial material) {
		return create(name, material, ItemGroups.myTabs.blocksTab);
	}
}

package com.mcmoddev.modernmetals.init;

import java.util.Arrays;
import java.util.List;

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

	protected Blocks() {
		throw new IllegalAccessError(SharedStrings.NOT_INSTANTIABLE);
	}

	/**
	 *
	 */
	public static void init() {
		final List<String> materials = Arrays.asList(MaterialNames.ALUMINUM, MaterialNames.ALUMINUM_BRASS,
				MaterialNames.BERYLLIUM, MaterialNames.BORON, MaterialNames.CADMIUM, MaterialNames.CHROMIUM,
				MaterialNames.GALVANIZED_STEEL, MaterialNames.IRIDIUM, MaterialNames.MAGNESIUM, MaterialNames.MANGANESE,
				MaterialNames.NICHROME, MaterialNames.OSMIUM, MaterialNames.PLUTONIUM, MaterialNames.RUTILE,
				MaterialNames.STAINLESS_STEEL, MaterialNames.TANTALUM, MaterialNames.THORIUM, MaterialNames.TITANIUM,
				MaterialNames.TUNGSTEN, MaterialNames.URANIUM, MaterialNames.ZIRCONIUM);

		materials.stream().filter(Materials::hasMaterial)
				.filter(materialName -> !Materials.getMaterialByName(materialName).isEmpty())
				.forEach(materialName -> {
					final MMDMaterial material = Materials.getMaterialByName(materialName);
					Arrays.asList(Names.BLOCK, Names.PLATE, Names.ORE, Names.BARS, Names.DOOR, Names.TRAPDOOR,
							Names.BUTTON, Names.SLAB, Names.DOUBLE_SLAB, Names.LEVER, Names.PRESSURE_PLATE,
							Names.STAIRS, Names.WALL).stream()
					.forEach(name -> create(name, material));
				});
	}

	private static boolean filterFunc(final Block block) {
		return block.getRegistryName().getNamespace().equals(ModernMetals.MODID);
	}

	/**
	 * 
	 * @param event The Event.
	 */
	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		Materials.getMaterialsByMod(ModernMetals.MODID).stream()
		.forEach(mat ->
			mat.getBlocks().stream()
			.filter(Blocks::filterFunc)
			.forEach(event.getRegistry()::register)
		);
	}
}

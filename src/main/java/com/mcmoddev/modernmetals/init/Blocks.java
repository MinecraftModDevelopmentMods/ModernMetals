package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.lib.util.Oredicts;

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

		if (Options.isMaterialEnabled(MaterialNames.ALUMINUM)) {
			createBlocksFull(MaterialNames.ALUMINUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.ALUMINUM_BRASS)) {
			createBlocksFullOreless(MaterialNames.ALUMINUM_BRASS, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.BORON)) {
			createBlocksFull(MaterialNames.BORON, ItemGroups.myTabs);
		}
		
		if (Options.isMaterialEnabled(MaterialNames.CADMIUM)) {
			createBlocksFull(MaterialNames.CADMIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.CHROMIUM)) {
			createBlocksFull(MaterialNames.CHROMIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.GALVANIZED_STEEL)) {
			createBlocksFullOreless(MaterialNames.GALVANIZED_STEEL, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.IRIDIUM)) {
			createBlocksFull(MaterialNames.IRIDIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.MAGNESIUM)) {
			createBlocksFull(MaterialNames.MAGNESIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.MANGANESE)) {
			createBlocksFull(MaterialNames.MANGANESE, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.NICHROME)) {
			createBlocksFullOreless(MaterialNames.NICHROME, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.OSMIUM)) {
			createBlocksFull(MaterialNames.OSMIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.PLUTONIUM)) {
			createBlocksFull(MaterialNames.PLUTONIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.RUTILE)) {
			createBlocksFull(MaterialNames.RUTILE, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.STAINLESS_STEEL)) {
			createBlocksFullOreless(MaterialNames.STAINLESS_STEEL, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.TANTALUM)) {
			createBlocksFull(MaterialNames.TANTALUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.TITANIUM)) {
			createBlocksFull(MaterialNames.TITANIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.TUNGSTEN)) {
			createBlocksFull(MaterialNames.TUNGSTEN, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.URANIUM)) {
			createBlocksFull(MaterialNames.URANIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.ZIRCONIUM)) {
			createBlocksFull(MaterialNames.ZIRCONIUM, ItemGroups.myTabs);
		}

		initDone = true;
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		for( MMDMaterial mat : Materials.getMaterialsByMod(ModernMetals.MODID) ) {
			for( Block block : mat.getBlocks() ) {
				if( block.getRegistryName().getResourceDomain().equals(ModernMetals.MODID) ) {
					event.getRegistry().register(block);
				}
			}
		}
	}
}

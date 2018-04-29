package com.mcmoddev.modernmetals.util;

import com.mcmoddev.modernmetals.init.Blocks;
import com.mcmoddev.modernmetals.init.Fluids;
import com.mcmoddev.modernmetals.init.Items;
import com.mcmoddev.modernmetals.init.Materials;
import com.mcmoddev.lib.events.MMDLibRegisterBlocks;
import com.mcmoddev.lib.events.MMDLibRegisterFluids;
import com.mcmoddev.lib.events.MMDLibRegisterItems;
import com.mcmoddev.lib.events.MMDLibRegisterMaterials;

import com.mcmoddev.modernmetals.ModernMetals;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class EventHandler {
	@SubscribeEvent
	public static void mmdlibRegisterMaterials(MMDLibRegisterMaterials ev) {
		com.mcmoddev.modernmetals.ModernMetals.logger.fatal("mmdlibRegisterMaterials - ModernMetals");
		ev.setActive(ModernMetals.MODID);
		Materials.init();
	}
	
	@SubscribeEvent
	public static void mmdlibRegisterBlocks(MMDLibRegisterBlocks ev) {
		com.mcmoddev.modernmetals.ModernMetals.logger.fatal("mmdlibRegisterBlocks - ModernMetals");
		ev.setActive(ModernMetals.MODID);
		Blocks.init();
	}

	@SubscribeEvent
	public static void mmdlibRegisterItems(MMDLibRegisterItems ev) {
		com.mcmoddev.modernmetals.ModernMetals.logger.fatal("mmdlibRegisterItems - ModernMetals");
		ev.setActive(ModernMetals.MODID);
		Items.init();
	}
	
	@SubscribeEvent
	public static void mmdlibRegisterFluids(MMDLibRegisterFluids ev) {
		com.mcmoddev.modernmetals.ModernMetals.logger.fatal("mmdlibRegisterFluids - ModernMetals");
		ev.setActive(ModernMetals.MODID);
		Fluids.init();
	}

	@SubscribeEvent
	public static void onRemapBlock(RegistryEvent.MissingMappings<Block> event) {
		for (final RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getAllMappings()) {
			if (mapping.key.getResourceDomain().equals(ModernMetals.MODID)) {
				// dummy
			}
		}
	}

	@SubscribeEvent
	public static void onRemapItem(RegistryEvent.MissingMappings<Item> event) {
		for (final RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getAllMappings()) {
			if (mapping.key.getResourceDomain().equals(ModernMetals.MODID)) {
				// dummy
			}
		}
	}

}

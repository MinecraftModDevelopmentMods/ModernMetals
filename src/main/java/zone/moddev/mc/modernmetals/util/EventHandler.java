package zone.moddev.mc.modernmetals.util;

import zone.moddev.mc.modernmetals.init.Blocks;
import zone.moddev.mc.modernmetals.init.Fluids;
import zone.moddev.mc.modernmetals.init.Items;
import zone.moddev.mc.modernmetals.init.Materials;
import com.mcmoddev.lib.events.MMDLibRegisterBlocks;
import com.mcmoddev.lib.events.MMDLibRegisterFluids;
import com.mcmoddev.lib.events.MMDLibRegisterItems;
import com.mcmoddev.lib.events.MMDLibRegisterMaterials;

import zone.moddev.mc.modernmetals.ModernMetals;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid=ModernMetals.MODID)
public class EventHandler {

	@SubscribeEvent
	public static void mmdlibRegisterMaterials(final MMDLibRegisterMaterials event) {
		Materials.init();
	}

	@SubscribeEvent
	public static void mmdlibRegisterBlocks(final MMDLibRegisterBlocks event) {
		Blocks.init();
	}

	@SubscribeEvent
	public static void mmdlibRegisterItems(final MMDLibRegisterItems event) {
		Items.init();
	}

	@SubscribeEvent
	public static void mmdlibRegisterFluids(final MMDLibRegisterFluids event) {
		Fluids.init();
	}

	/**
	 *
	 * @param event The Event.
	 */
	@SubscribeEvent
	public static void onRemapBlock(final RegistryEvent.MissingMappings<Block> event) {
		for (final RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getAllMappings()) {
			if (mapping.key.getNamespace().equals(ModernMetals.MODID)) {
				// dummy
			}
		}
	}

	/**
	 *
	 * @param event The Event.
	 */
	@SubscribeEvent
	public static void onRemapItem(final RegistryEvent.MissingMappings<Item> event) {
		for (final RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getAllMappings()) {
			if (mapping.key.getNamespace().equals(ModernMetals.MODID)) {
				// dummy
			}
		}
	}

}

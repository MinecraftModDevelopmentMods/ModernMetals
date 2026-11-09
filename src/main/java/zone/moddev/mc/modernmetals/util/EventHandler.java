package zone.moddev.mc.modernmetals.util;

import zone.moddev.mc.modernmetals.init.Blocks;
import zone.moddev.mc.modernmetals.init.Fluids;
import zone.moddev.mc.modernmetals.init.Items;
import zone.moddev.mc.modernmetals.init.Materials;
import com.google.common.collect.ImmutableSet;
import com.mcmoddev.lib.events.MMDLibRegisterBlocks;
import com.mcmoddev.lib.events.MMDLibRegisterFluids;
import com.mcmoddev.lib.events.MMDLibRegisterItems;
import com.mcmoddev.lib.events.MMDLibRegisterMaterials;
import com.mcmoddev.lib.registry.recipe.ICrusherRecipe;
import java.util.Set;

import zone.moddev.mc.modernmetals.ModernMetals;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid=ModernMetals.MODID)
public class EventHandler {
	private static final Set<String> LEGACY_EMPTY_CRUSHER_RECIPES = ImmutableSet.of(
			"blocklapis_to_air", "orelapis_to_air", "blockredstone_to_air",
			"oreredstone_to_air", "quartz_block_to_air", "orequartz_to_air", "stone_slab_to_air");

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

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onRemapLegacyCrusherRecipes(final RegistryEvent.MissingMappings<ICrusherRecipe> event) {
		for (final RegistryEvent.MissingMappings.Mapping<ICrusherRecipe> mapping : event.getAllMappings()) {
			// Older builds registered these empty outputs under either namespace.
			final String namespace = mapping.key.getNamespace();
			if (mapping.getAction() == RegistryEvent.MissingMappings.Action.DEFAULT
					&& ("mmdlib".equals(namespace) || ModernMetals.MODID.equals(namespace))
					&& LEGACY_EMPTY_CRUSHER_RECIPES.contains(mapping.key.getPath())) {
				mapping.ignore();
			}
		}
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

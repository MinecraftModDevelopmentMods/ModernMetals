package zone.moddev.mc.modernmetals.init;

import java.util.Arrays;
import java.util.List;

import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.data.SharedStrings;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.util.Oredicts;
import zone.moddev.mc.modernmetals.ModernMetals;
import zone.moddev.mc.modernmetals.block.OverlayOreBlock;
import zone.moddev.mc.modernmetals.data.MaterialNames;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * This class initializes all blocks in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
@EventBusSubscriber(modid=ModernMetals.MODID)
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
					create(Names.BLOCK, material);
					create(Names.PLATE, material);
					createOre(material);
					Arrays.asList(Names.BARS, Names.DOOR, Names.TRAPDOOR,
							Names.BUTTON, Names.SLAB, Names.DOUBLE_SLAB, Names.LEVER, Names.PRESSURE_PLATE,
							Names.STAIRS, Names.WALL).stream()
					.forEach(name -> create(name, material));
				});
	}

	private static void createOre(final MMDMaterial material) {
		if (MaterialNames.THORIUM.equals(material.getName())) {
			create(Names.ORE, material);
		} else if (material.hasOre() && !material.hasBlock(Names.ORE) && isNameEnabled(Names.ORE)) {
			final Block ore = addBlock(new OverlayOreBlock(material), Names.ORE, material,
					ItemGroups.getTab(SharedStrings.TAB_BLOCKS));
			material.addNewBlock(Names.ORE, ore);
			final String prefix = getOredictFromName(Names.ORE);
			if (prefix != null) {
				Oredicts.registerOre(prefix + material.getCapitalizedName(), ore);
			}
		}
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
		ModernMetals.logger.fatal("RegistryEvent.Register<Block>");
		Materials.getMaterialsByMod(ModernMetals.MODID).stream()
		.forEach(mat ->
			mat.getBlocks().stream()
			.filter(Blocks::filterFunc)
			.forEach(event.getRegistry()::register)
		);
	}
}

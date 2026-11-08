package zone.moddev.mc.modernmetals.init;

import java.util.Arrays;

import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.data.SharedStrings;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.util.Oredicts;
import zone.moddev.mc.modernmetals.block.OverlayOreBlock;
import zone.moddev.mc.modernmetals.data.MaterialNames;

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

		String[] materials = new String[] { MaterialNames.ALUMINUM,
				MaterialNames.ALUMINUM_BRASS,
				MaterialNames.BERYLLIUM,
				MaterialNames.BORON,
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
				MaterialNames.THORIUM,
				MaterialNames.TITANIUM,
				MaterialNames.TUNGSTEN,
				MaterialNames.URANIUM,
				MaterialNames.ZIRCONIUM
			};

		Arrays.stream(materials)
				.filter(Materials::hasMaterial)
				.forEach(name -> {
					final MMDMaterial material = Materials.getMaterialByName(name);

					create(Names.BLOCK, material);
					create(Names.PLATE, material);
					createOre(material);
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

	private static void createOre(MMDMaterial material) {
		if (MaterialNames.THORIUM.equals(material.getName())) {
			// Thorium still has its original opaque texture.
			create(Names.ORE, material);
		} else if ((material.hasOre() || hasLegacyOre(material.getName()))
				&& !material.hasBlock(Names.ORE) && isNameEnabled(Names.ORE)) {
			final Block ore = addBlock(new OverlayOreBlock(material), Names.ORE, material,
					ItemGroups.getTab(SharedStrings.TAB_BLOCKS));
			material.addNewBlock(Names.ORE.toString(), ore);
			Oredicts.registerOre("ore" + material.getCapitalizedName(), ore);
		}
	}

	private static boolean hasLegacyOre(final String name) {
		// 0.11.0 saved these alloy ores. OreSpawn has no placement rules for them.
		return MaterialNames.GALVANIZED_STEEL.equals(name) || MaterialNames.NICHROME.equals(name)
				|| MaterialNames.STAINLESS_STEEL.equals(name);
	}
}

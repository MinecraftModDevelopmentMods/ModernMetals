package com.mcmoddev.modernmetals.init;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.data.SharedStrings;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.modernmetals.data.MaterialNames;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * This class initializes all items in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Items extends com.mcmoddev.lib.init.Items {

	private static boolean initDone = false;

	protected Items() {
		throw new IllegalAccessError(SharedStrings.NOT_INSTANTIABLE);
	}

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		Blocks.init();

		List<String> matsModSupport = Arrays.asList(
				MaterialNames.ALUMINUM,
				MaterialNames.BERYLLIUM,
				MaterialNames.BORON,
				MaterialNames.CADMIUM,
				MaterialNames.CHROMIUM,
				MaterialNames.IRIDIUM,
				MaterialNames.MAGNESIUM,
				MaterialNames.MANGANESE,
				MaterialNames.PLUTONIUM,
				MaterialNames.RUTILE,
				MaterialNames.TANTALUM,
				MaterialNames.THORIUM,
				MaterialNames.TITANIUM,
				MaterialNames.TUNGSTEN,
				MaterialNames.ZIRCONIUM
				);

		List<String> myModMats = Arrays.asList(
				MaterialNames.ALUMINUM,
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
				);

		myModMats.stream()
				.filter(Materials::hasMaterial)
				.filter(name -> !Materials.getMaterialByName(name).isEmpty())
				.forEach(name -> {
					final MMDMaterial material = Materials.getMaterialByName(name);

					create(Names.BLEND, material);
					create(Names.INGOT, material);
					create(Names.NUGGET, material);
					create(Names.POWDER, material);
					create(Names.SMALLBLEND, material);
					create(Names.SMALLPOWDER, material);

					create(Names.ARROW, material);
					create(Names.AXE, material);
					create(Names.BOLT, material);
					create(Names.BOOTS, material);
					create(Names.BOW, material);
					create(Names.CHESTPLATE, material);
					create(Names.CRACKHAMMER, material);
					create(Names.CROSSBOW, material);
					create(Names.DOOR, material);
					create(Names.FISHING_ROD, material);
					create(Names.HELMET, material);
					create(Names.HOE, material);
					create(Names.HORSE_ARMOR, material);
					create(Names.LEGGINGS, material);
					create(Names.PICKAXE, material);
					create(Names.SHEARS, material);
					create(Names.SHIELD, material);
					create(Names.SHOVEL, material);
					create(Names.SLAB, material);
					create(Names.SWORD, material);
					create(Names.ROD, material);
					create(Names.GEAR, material);
				});

		matsModSupport.stream()
				.filter(Materials::hasMaterial)
				.filter(name -> !Materials.getMaterialByName(name).isEmpty())
				.forEach(name -> {
					final MMDMaterial material = Materials.getMaterialByName(name);

					create(Names.CASING, material);
					create(Names.DENSE_PLATE, material);

					if (material.hasOre()) {
						create(Names.CRUSHED, material);
						create(Names.CRUSHED_PURIFIED, material);

						createMekCrystal(material, ItemGroups.getTab(SharedStrings.TAB_ITEMS));
						create(Names.SHARD, material);
						create(Names.CLUMP, material);
						create(Names.POWDER_DIRTY, material);
						create(Names.CRYSTAL, material);
			}
		});

		if (Materials.hasMaterial(MaterialNames.OSMIUM)) {
			final MMDMaterial osmium = Materials.getMaterialByName(MaterialNames.OSMIUM);

			create(Names.CRUSHED, osmium);
			create(Names.CRUSHED_PURIFIED, osmium);
		}

		if (Materials.hasMaterial(MaterialNames.URANIUM)) {
			final MMDMaterial uranium = Materials.getMaterialByName(MaterialNames.URANIUM);

			create(Names.SHARD, uranium);
			create(Names.CLUMP, uranium);
			create(Names.POWDER_DIRTY, uranium);
			create(Names.CRYSTAL, uranium);
		}

		// addToMetList() // TODO: May not be needed, check

		initDone = true;
	}
}

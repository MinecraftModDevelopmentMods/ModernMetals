package com.mcmoddev.modernmetals.init;

import java.util.Arrays;
import java.util.List;

import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.data.SharedStrings;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.util.Oredicts;
import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * This class initializes all items in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Items extends com.mcmoddev.lib.init.Items {
	protected Items() {
		throw new IllegalAccessError(SharedStrings.NOT_INSTANTIABLE);
	}

	/**
	 *
	 */
	public static void init() {
		Blocks.init();

		final List<String> materials = Arrays.asList(MaterialNames.ALUMINUM, MaterialNames.ALUMINUM_BRASS,
				MaterialNames.BERYLLIUM, MaterialNames.BORON, MaterialNames.CADMIUM, MaterialNames.CHROMIUM,
				MaterialNames.GALVANIZED_STEEL, MaterialNames.IRIDIUM, MaterialNames.MAGNESIUM, MaterialNames.MANGANESE,
				MaterialNames.NICHROME, MaterialNames.OSMIUM, MaterialNames.PLUTONIUM, MaterialNames.RUTILE,
				MaterialNames.STAINLESS_STEEL, MaterialNames.TANTALUM, MaterialNames.THORIUM, MaterialNames.TITANIUM,
				MaterialNames.TUNGSTEN, MaterialNames.URANIUM, MaterialNames.ZIRCONIUM);

		final List<String> materialsIC2Support = Arrays.asList(MaterialNames.ALUMINUM_BRASS, MaterialNames.GALVANIZED_STEEL,
				MaterialNames.NICHROME, MaterialNames.OSMIUM, MaterialNames.STAINLESS_STEEL, MaterialNames.URANIUM);

		final List<String> materialsModSupport = Arrays.asList(MaterialNames.ALUMINUM, MaterialNames.BERYLLIUM,
				MaterialNames.BORON, MaterialNames.CADMIUM, MaterialNames.CHROMIUM, MaterialNames.IRIDIUM,
				MaterialNames.MAGNESIUM, MaterialNames.MANGANESE, MaterialNames.PLUTONIUM, MaterialNames.RUTILE,
				MaterialNames.TANTALUM, MaterialNames.THORIUM, MaterialNames.TITANIUM, MaterialNames.TUNGSTEN,
				MaterialNames.ZIRCONIUM);

		final List<Names> itemsToMake = Arrays.asList(Names.BLEND, Names.INGOT, Names.NUGGET, Names.POWDER,
				Names.SMALLBLEND, Names.SMALLPOWDER, Names.ARROW, Names.AXE, Names.BOLT, Names.BOOTS,
				Names.BOW, Names.CHESTPLATE, Names.CRACKHAMMER, Names.CROSSBOW, Names.DOOR,
				Names.FISHING_ROD, Names.HELMET, Names.HOE, Names.HORSE_ARMOR, Names.LEGGINGS, Names.PICKAXE,
				Names.SHEARS, Names.SHIELD, Names.SHOVEL, Names.SCYTHE, Names.SLAB, Names.SWORD, Names.ROD,
				Names.GEAR);

		materials.stream().filter(Materials::hasMaterial)
				.filter(materialName -> !Materials.getMaterialByName(materialName).isEmpty())
				.forEach(materialName -> {
					final MMDMaterial material = Materials.getMaterialByName(materialName);
					itemsToMake.stream().forEach(name -> create(name, material));
				});

		materialsModSupport.stream().filter(Materials::hasMaterial)
				.filter(materialName -> !Materials.getMaterialByName(materialName).isEmpty())
				.forEach(materialName -> {
					final MMDMaterial material = Materials.getMaterialByName(materialName);

					create(Names.CASING, material);
					create(Names.DENSE_PLATE, material);

					if (material.hasOre()) {
						Arrays.asList(Names.CRUSHED, Names.CRUSHED_PURIFIED, Names.SHARD, Names.CLUMP,
								Names.POWDER_DIRTY, Names.CRYSTAL).stream()
						.forEach(name -> create(name, material));
					}
				});

		materialsIC2Support.stream().filter(Materials::hasMaterial)
				.filter(materialName -> !Materials.getMaterialByName(materialName).isEmpty())
				.forEach(materialName -> {
					final MMDMaterial material = Materials.getMaterialByName(materialName);

					create(Names.CASING, material);
					create(Names.DENSE_PLATE, material);
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
	}

	private static boolean filterFunc(ItemStack item) {
		return item.getItem().getRegistryName().getResourceDomain().equals(ModernMetals.MODID);
	}

	private static Item mapFunc(ItemStack itemStack) {
		return itemStack.getItem();
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		Materials.getMaterialsByMod(ModernMetals.MODID).stream()
		.forEach( mat -> {
			mat.getItems().stream()
			.filter(Items::filterFunc)
			.map(Items::mapFunc)
			.forEach(event.getRegistry()::register);
		});

		Oredicts.registerItemOreDictionaryEntries();
		Oredicts.registerBlockOreDictionaryEntries();
	}
}

package modernmetals.init;

import modernmetals.ModernMetals;
import modernmetals.items.ItemMetalBlend;
import modernmetals.items.ItemMetalIngot;
import cyano.basemetals.blocks.*;
import cyano.basemetals.items.*;
import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockSlab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

/**
 * This class initializes all items in Modern Metals and provides some utility 
 * methods for looking up items. 
 * @author DrCyano
 *
 */
public abstract class Items {

	public static Item aluminum_arrow;
	public static Item aluminum_axe;
	public static Item aluminum_boots;
	public static Item aluminum_bolt;
	public static Item aluminum_bow;
	public static Item aluminum_chestplate;
	public static Item aluminum_crackhammer;
	public static Item aluminum_crossbow;
	public static Item aluminum_door;
	public static Item aluminum_fishing_rod;
	public static Item aluminum_helmet;
	public static Item aluminum_hoe;
	public static Item aluminum_horsearmor;
	public static Item aluminum_ingot;
	public static Item aluminum_leggings;
	public static Item aluminum_nugget;
	public static Item aluminum_pickaxe;
	public static Item aluminum_powder;
	public static Item aluminum_shears;
	public static Item aluminum_shovel;
	public static Item aluminum_smallpowder;
	public static Item aluminum_sword;
	public static Item aluminum_rod;
	public static Item aluminum_gear;

	public static Item aluminum_slab;
//	public static Item aluminum_shield;

	public static Item aluminum_crystal;
	public static Item aluminum_shard;
	public static Item aluminum_clump;
	public static Item aluminum_powder_dirty;

	public static Item aluminum_dense_plate;
	public static Item aluminum_crushed;
	public static Item aluminum_crushed_purified;

	public static Item aluminumbrass_arrow;
	public static Item aluminumbrass_axe;
	public static Item aluminumbrass_blend;
	public static Item aluminumbrass_boots;
	public static Item aluminumbrass_bolt;
	public static Item aluminumbrass_bow;
	public static Item aluminumbrass_chestplate;
	public static Item aluminumbrass_crackhammer;
	public static Item aluminumbrass_crossbow;
	public static Item aluminumbrass_door;
	public static Item aluminumbrass_fishing_rod;
	public static Item aluminumbrass_helmet;
	public static Item aluminumbrass_hoe;
	public static Item aluminumbrass_horsearmor;
	public static Item aluminumbrass_ingot;
	public static Item aluminumbrass_leggings;
	public static Item aluminumbrass_nugget;
	public static Item aluminumbrass_pickaxe;
	public static Item aluminumbrass_powder;
	public static Item aluminumbrass_shears;
	public static Item aluminumbrass_shovel;
	public static Item aluminumbrass_smallblend;
	public static Item aluminumbrass_smallpowder;
	public static Item aluminumbrass_sword;
	public static Item aluminumbrass_rod;
	public static Item aluminumbrass_gear;

	public static Item aluminumbrass_slab;
//	public static Item aluminumbrass_shield;

//	public static Item aluminumbrass_crystal;
//	public static Item aluminumbrass_shard;
//	public static Item aluminumbrass_clump;
//	public static Item aluminumbrass_powder_dirty;

	public static Item aluminumbrass_dense_plate;
//	public static Item aluminumbrass_crushed;
//	public static Item aluminumbrass_crushed_purified;

	public static Item cadmium_arrow;
	public static Item cadmium_axe;
	public static Item cadmium_boots;
	public static Item cadmium_bolt;
	public static Item cadmium_bow;
	public static Item cadmium_chestplate;
	public static Item cadmium_crackhammer;
	public static Item cadmium_crossbow;
	public static Item cadmium_door;
	public static Item cadmium_fishing_rod;
	public static Item cadmium_helmet;
	public static Item cadmium_hoe;
	public static Item cadmium_horsearmor;
	public static Item cadmium_ingot;
	public static Item cadmium_leggings;
	public static Item cadmium_nugget;
	public static Item cadmium_pickaxe;
	public static Item cadmium_powder;
	public static Item cadmium_shears;
	public static Item cadmium_shovel;
	public static Item cadmium_smallpowder;
	public static Item cadmium_sword;
	public static Item cadmium_rod;
	public static Item cadmium_gear;

	public static Item cadmium_slab;
//	public static Item cadmium_shield;

	public static Item cadmium_crystal;
	public static Item cadmium_shard;
	public static Item cadmium_clump;
	public static Item cadmium_powder_dirty;

	public static Item cadmium_dense_plate;
	public static Item cadmium_crushed;
	public static Item cadmium_crushed_purified;

	public static Item chromium_arrow;
	public static Item chromium_axe;
	public static Item chromium_boots;
	public static Item chromium_bolt;
	public static Item chromium_bow;
	public static Item chromium_chestplate;
	public static Item chromium_crackhammer;
	public static Item chromium_crossbow;
	public static Item chromium_door;
	public static Item chromium_fishing_rod;
	public static Item chromium_helmet;
	public static Item chromium_hoe;
	public static Item chromium_horsearmor;
	public static Item chromium_ingot;
	public static Item chromium_leggings;
	public static Item chromium_nugget;
	public static Item chromium_pickaxe;
	public static Item chromium_powder;
	public static Item chromium_shears;
	public static Item chromium_shovel;
	public static Item chromium_smallpowder;
	public static Item chromium_sword;
	public static Item chromium_rod;
	public static Item chromium_gear;

	public static Item chromium_slab;
//	public static Item chromium_shield;

	public static Item chromium_crystal;
	public static Item chromium_shard;
	public static Item chromium_clump;
	public static Item chromium_powder_dirty;

	public static Item chromium_dense_plate;
	public static Item chromium_crushed;
	public static Item chromium_crushed_purified;

	public static Item galvanizedsteel_arrow;
	public static Item galvanizedsteel_axe;
	public static Item galvanizedsteel_blend;
	public static Item galvanizedsteel_boots;
	public static Item galvanizedsteel_bolt;
	public static Item galvanizedsteel_bow;
	public static Item galvanizedsteel_chestplate;
	public static Item galvanizedsteel_crackhammer;
	public static Item galvanizedsteel_crossbow;
	public static Item galvanizedsteel_door;
	public static Item galvanizedsteel_fishing_rod;
	public static Item galvanizedsteel_helmet;
	public static Item galvanizedsteel_hoe;
	public static Item galvanizedsteel_horsearmor;
	public static Item galvanizedsteel_ingot;
	public static Item galvanizedsteel_leggings;
	public static Item galvanizedsteel_nugget;
	public static Item galvanizedsteel_pickaxe;
	public static Item galvanizedsteel_powder;
	public static Item galvanizedsteel_shears;
	public static Item galvanizedsteel_shovel;
	public static Item galvanizedsteel_smallblend;
	public static Item galvanizedsteel_smallpowder;
	public static Item galvanizedsteel_sword;
	public static Item galvanizedsteel_rod;
	public static Item galvanizedsteel_gear;

	public static Item galvanizedsteel_slab;
//	public static Item galvanizedsteel_shield;

//	public static Item galvanizedsteel_crystal;
//	public static Item galvanizedsteel_shard;
//	public static Item galvanizedsteel_clump;
//	public static Item galvanizedsteel_powder_dirty;

	public static Item galvanizedsteel_dense_plate;
//	public static Item galvanizedsteel_crushed;
//	public static Item galvanizedsteel_crushed_purified;

	public static Item iridium_arrow;
	public static Item iridium_axe;
	public static Item iridium_boots;
	public static Item iridium_bolt;
	public static Item iridium_bow;
	public static Item iridium_chestplate;
	public static Item iridium_crackhammer;
	public static Item iridium_crossbow;
	public static Item iridium_door;
	public static Item iridium_fishing_rod;
	public static Item iridium_helmet;
	public static Item iridium_hoe;
	public static Item iridium_horsearmor;
	public static Item iridium_ingot;
	public static Item iridium_leggings;
	public static Item iridium_nugget;
	public static Item iridium_pickaxe;
	public static Item iridium_powder;
	public static Item iridium_shears;
	public static Item iridium_shovel;
	public static Item iridium_smallpowder;
	public static Item iridium_sword;
	public static Item iridium_rod;
	public static Item iridium_gear;

	public static Item iridium_slab;
//	public static Item iridium_shield;

	public static Item iridium_crystal;
	public static Item iridium_shard;
	public static Item iridium_clump;
	public static Item iridium_powder_dirty;

	public static Item iridium_dense_plate;
	public static Item iridium_crushed;
	public static Item iridium_crushed_purified;

	public static Item magnesium_arrow;
	public static Item magnesium_axe;
	public static Item magnesium_boots;
	public static Item magnesium_bolt;
	public static Item magnesium_bow;
	public static Item magnesium_chestplate;
	public static Item magnesium_crackhammer;
	public static Item magnesium_crossbow;
	public static Item magnesium_door;
	public static Item magnesium_fishing_rod;
	public static Item magnesium_helmet;
	public static Item magnesium_hoe;
	public static Item magnesium_horsearmor;
	public static Item magnesium_ingot;
	public static Item magnesium_leggings;
	public static Item magnesium_nugget;
	public static Item magnesium_pickaxe;
	public static Item magnesium_powder;
	public static Item magnesium_shears;
	public static Item magnesium_shovel;
	public static Item magnesium_smallpowder;
	public static Item magnesium_sword;
	public static Item magnesium_rod;
	public static Item magnesium_gear;

	public static Item magnesium_slab;
//	public static Item magnesium_shield;

	public static Item magnesium_crystal;
	public static Item magnesium_shard;
	public static Item magnesium_clump;
	public static Item magnesium_powder_dirty;

	public static Item magnesium_dense_plate;
	public static Item magnesium_crushed;
	public static Item magnesium_crushed_purified;

	public static Item manganese_arrow;
	public static Item manganese_axe;
	public static Item manganese_boots;
	public static Item manganese_bolt;
	public static Item manganese_bow;
	public static Item manganese_chestplate;
	public static Item manganese_crackhammer;
	public static Item manganese_crossbow;
	public static Item manganese_door;
	public static Item manganese_fishing_rod;
	public static Item manganese_helmet;
	public static Item manganese_hoe;
	public static Item manganese_horsearmor;
	public static Item manganese_ingot;
	public static Item manganese_leggings;
	public static Item manganese_nugget;
	public static Item manganese_pickaxe;
	public static Item manganese_powder;
	public static Item manganese_shears;
	public static Item manganese_shovel;
	public static Item manganese_smallpowder;
	public static Item manganese_sword;
	public static Item manganese_rod;
	public static Item manganese_gear;

	public static Item manganese_slab;
//	public static Item manganese_shield;

	public static Item manganese_crystal;
	public static Item manganese_shard;
	public static Item manganese_clump;
	public static Item manganese_powder_dirty;

	public static Item manganese_dense_plate;
	public static Item manganese_crushed;
	public static Item manganese_crushed_purified;

	public static Item nichrome_arrow;
	public static Item nichrome_axe;
	public static Item nichrome_blend;
	public static Item nichrome_boots;
	public static Item nichrome_bolt;
	public static Item nichrome_bow;
	public static Item nichrome_chestplate;
	public static Item nichrome_crackhammer;
	public static Item nichrome_crossbow;
	public static Item nichrome_door;
	public static Item nichrome_fishing_rod;
	public static Item nichrome_helmet;
	public static Item nichrome_hoe;
	public static Item nichrome_horsearmor;
	public static Item nichrome_ingot;
	public static Item nichrome_leggings;
	public static Item nichrome_nugget;
	public static Item nichrome_pickaxe;
	public static Item nichrome_powder;
	public static Item nichrome_shears;
	public static Item nichrome_shovel;
	public static Item nichrome_smallblend;
	public static Item nichrome_smallpowder;
	public static Item nichrome_sword;
	public static Item nichrome_rod;
	public static Item nichrome_gear;

	public static Item nichrome_slab;
//	public static Item nichrome_shield;

//	public static Item nichrome_crystal;
//	public static Item nichrome_shard;
//	public static Item nichrome_clump;
//	public static Item nichrome_powder_dirty;

	public static Item nichrome_dense_plate;
//	public static Item nichrome_crushed;
//	public static Item nichrome_crushed_purified;

	public static Item osmium_arrow;
	public static Item osmium_axe;
	public static Item osmium_boots;
	public static Item osmium_bolt;
	public static Item osmium_bow;
	public static Item osmium_chestplate;
	public static Item osmium_crackhammer;
	public static Item osmium_crossbow;
	public static Item osmium_door;
	public static Item osmium_fishing_rod;
	public static Item osmium_helmet;
	public static Item osmium_hoe;
	public static Item osmium_horsearmor;
	public static Item osmium_ingot;
	public static Item osmium_leggings;
	public static Item osmium_nugget;
	public static Item osmium_pickaxe;
	public static Item osmium_powder;
	public static Item osmium_shears;
	public static Item osmium_shovel;
	public static Item osmium_smallpowder;
	public static Item osmium_sword;
	public static Item osmium_rod;
	public static Item osmium_gear;

	public static Item osmium_slab;
//	public static Item osmium_shield;

//	public static Item osmium_crystal;
//	public static Item osmium_shard;
//	public static Item osmium_clump;
//	public static Item osmium_powder_dirty;

	public static Item osmium_dense_plate;
	public static Item osmium_crushed;
	public static Item osmium_crushed_purified;

	public static Item plutonium_arrow;
	public static Item plutonium_axe;
	public static Item plutonium_boots;
	public static Item plutonium_bolt;
	public static Item plutonium_bow;
	public static Item plutonium_chestplate;
	public static Item plutonium_crackhammer;
	public static Item plutonium_crossbow;
	public static Item plutonium_door;
	public static Item plutonium_fishing_rod;
	public static Item plutonium_helmet;
	public static Item plutonium_hoe;
	public static Item plutonium_horsearmor;
	public static Item plutonium_ingot;
	public static Item plutonium_leggings;
	public static Item plutonium_nugget;
	public static Item plutonium_pickaxe;
	public static Item plutonium_powder;
	public static Item plutonium_shears;
	public static Item plutonium_shovel;
	public static Item plutonium_smallpowder;
	public static Item plutonium_sword;
	public static Item plutonium_rod;
	public static Item plutonium_gear;

	public static Item plutonium_slab;
//	public static Item plutonium_shield;

	public static Item plutonium_crystal;
	public static Item plutonium_shard;
	public static Item plutonium_clump;
	public static Item plutonium_powder_dirty;

	public static Item plutonium_dense_plate;
	public static Item plutonium_crushed;
	public static Item plutonium_crushed_purified;

	public static Item rutile_arrow;
	public static Item rutile_axe;
	public static Item rutile_boots;
	public static Item rutile_bolt;
	public static Item rutile_bow;
	public static Item rutile_chestplate;
	public static Item rutile_crackhammer;
	public static Item rutile_crossbow;
	public static Item rutile_door;
	public static Item rutile_fishing_rod;
	public static Item rutile_helmet;
	public static Item rutile_hoe;
	public static Item rutile_horsearmor;
	public static Item rutile_ingot;
	public static Item rutile_leggings;
	public static Item rutile_nugget;
	public static Item rutile_pickaxe;
	public static Item rutile_powder;
	public static Item rutile_shears;
	public static Item rutile_shovel;
	public static Item rutile_smallpowder;
	public static Item rutile_sword;
	public static Item rutile_rod;
	public static Item rutile_gear;

	public static Item rutile_slab;
//	public static Item rutile_shield;

	public static Item rutile_crystal;
	public static Item rutile_shard;
	public static Item rutile_clump;
	public static Item rutile_powder_dirty;

	public static Item rutile_dense_plate;
	public static Item rutile_crushed;
	public static Item rutile_crushed_purified;

	public static Item stainlesssteel_arrow;
	public static Item stainlesssteel_axe;
	public static Item stainlesssteel_blend;
	public static Item stainlesssteel_boots;
	public static Item stainlesssteel_bolt;
	public static Item stainlesssteel_bow;
	public static Item stainlesssteel_chestplate;
	public static Item stainlesssteel_crackhammer;
	public static Item stainlesssteel_crossbow;
	public static Item stainlesssteel_door;
	public static Item stainlesssteel_fishing_rod;
	public static Item stainlesssteel_helmet;
	public static Item stainlesssteel_hoe;
	public static Item stainlesssteel_horsearmor;
	public static Item stainlesssteel_ingot;
	public static Item stainlesssteel_leggings;
	public static Item stainlesssteel_nugget;
	public static Item stainlesssteel_pickaxe;
	public static Item stainlesssteel_powder;
	public static Item stainlesssteel_shears;
	public static Item stainlesssteel_shovel;
	public static Item stainlesssteel_smallblend;
	public static Item stainlesssteel_smallpowder;
	public static Item stainlesssteel_sword;
	public static Item stainlesssteel_rod;
	public static Item stainlesssteel_gear;

	public static Item stainlesssteel_slab;
//	public static Item stainlesssteel_shield;

//	public static Item stainlesssteel_crystal;
//	public static Item stainlesssteel_shard;
//	public static Item stainlesssteel_clump;
//	public static Item stainlesssteel_powder_dirty;

	public static Item stainlesssteel_dense_plate;
//	public static Item stainlesssteel_crushed;
//	public static Item stainlesssteel_crushed_purified;

	public static Item tantalum_arrow;
	public static Item tantalum_axe;
	public static Item tantalum_boots;
	public static Item tantalum_bolt;
	public static Item tantalum_bow;
	public static Item tantalum_chestplate;
	public static Item tantalum_crackhammer;
	public static Item tantalum_crossbow;
	public static Item tantalum_door;
	public static Item tantalum_fishing_rod;
	public static Item tantalum_helmet;
	public static Item tantalum_hoe;
	public static Item tantalum_horsearmor;
	public static Item tantalum_ingot;
	public static Item tantalum_leggings;
	public static Item tantalum_nugget;
	public static Item tantalum_pickaxe;
	public static Item tantalum_powder;
	public static Item tantalum_shears;
	public static Item tantalum_shovel;
	public static Item tantalum_smallpowder;
	public static Item tantalum_sword;
	public static Item tantalum_rod;
	public static Item tantalum_gear;

	public static Item tantalum_slab;
//	public static Item tantalum_shield;

	public static Item tantalum_crystal;
	public static Item tantalum_shard;
	public static Item tantalum_clump;
	public static Item tantalum_powder_dirty;

	public static Item tantalum_dense_plate;
	public static Item tantalum_crushed;
	public static Item tantalum_crushed_purified;

	public static Item titanium_arrow;
	public static Item titanium_axe;
	public static Item titanium_blend;
	public static Item titanium_boots;
	public static Item titanium_bolt;
	public static Item titanium_bow;
	public static Item titanium_chestplate;
	public static Item titanium_crackhammer;
	public static Item titanium_crossbow;
	public static Item titanium_door;
	public static Item titanium_fishing_rod;
	public static Item titanium_helmet;
	public static Item titanium_hoe;
	public static Item titanium_horsearmor;
	public static Item titanium_ingot;
	public static Item titanium_leggings;
	public static Item titanium_nugget;
	public static Item titanium_pickaxe;
	public static Item titanium_powder;
	public static Item titanium_shears;
	public static Item titanium_shovel;
	public static Item titanium_smallblend;
	public static Item titanium_smallpowder;
	public static Item titanium_sword;
	public static Item titanium_rod;
	public static Item titanium_gear;

	public static Item titanium_slab;
//	public static Item titanium_shield;

	public static Item titanium_crystal;
	public static Item titanium_shard;
	public static Item titanium_clump;
	public static Item titanium_powder_dirty;

	public static Item titanium_dense_plate;
	public static Item titanium_crushed;
	public static Item titanium_crushed_purified;

	public static Item tungsten_arrow;
	public static Item tungsten_axe;
	public static Item tungsten_boots;
	public static Item tungsten_bolt;
	public static Item tungsten_bow;
	public static Item tungsten_chestplate;
	public static Item tungsten_crackhammer;
	public static Item tungsten_crossbow;
	public static Item tungsten_door;
	public static Item tungsten_fishing_rod;
	public static Item tungsten_helmet;
	public static Item tungsten_hoe;
	public static Item tungsten_horsearmor;
	public static Item tungsten_ingot;
	public static Item tungsten_leggings;
	public static Item tungsten_nugget;
	public static Item tungsten_pickaxe;
	public static Item tungsten_powder;
	public static Item tungsten_shears;
	public static Item tungsten_shovel;
	public static Item tungsten_smallpowder;
	public static Item tungsten_sword;
	public static Item tungsten_rod;
	public static Item tungsten_gear;

	public static Item tungsten_slab;
//	public static Item tungsten_shield;

	public static Item tungsten_crystal;
	public static Item tungsten_shard;
	public static Item tungsten_clump;
	public static Item tungsten_powder_dirty;

	public static Item tungsten_dense_plate;
	public static Item tungsten_crushed;
	public static Item tungsten_crushed_purified;

	public static Item uranium_arrow;
	public static Item uranium_axe;
	public static Item uranium_boots;
	public static Item uranium_bolt;
	public static Item uranium_bow;
	public static Item uranium_chestplate;
	public static Item uranium_crackhammer;
	public static Item uranium_crossbow;
	public static Item uranium_door;
	public static Item uranium_fishing_rod;
	public static Item uranium_helmet;
	public static Item uranium_hoe;
	public static Item uranium_horsearmor;
	public static Item uranium_ingot;
	public static Item uranium_leggings;
	public static Item uranium_nugget;
	public static Item uranium_pickaxe;
	public static Item uranium_powder;
	public static Item uranium_shears;
	public static Item uranium_shovel;
	public static Item uranium_smallpowder;
	public static Item uranium_sword;
	public static Item uranium_rod;
	public static Item uranium_gear;

	public static Item uranium_slab;
//	public static Item uranium_shield;

	public static Item uranium_crystal;
	public static Item uranium_shard;
	public static Item uranium_clump;
	public static Item uranium_powder_dirty;

	public static Item uranium_dense_plate;
	public static Item uranium_crushed;
	public static Item uranium_crushed_purified;

	public static Item zirconium_arrow;
	public static Item zirconium_axe;
	public static Item zirconium_boots;
	public static Item zirconium_bolt;
	public static Item zirconium_bow;
	public static Item zirconium_chestplate;
	public static Item zirconium_crackhammer;
	public static Item zirconium_crossbow;
	public static Item zirconium_door;
	public static Item zirconium_fishing_rod;
	public static Item zirconium_helmet;
	public static Item zirconium_hoe;
	public static Item zirconium_horsearmor;
	public static Item zirconium_ingot;
	public static Item zirconium_leggings;
	public static Item zirconium_nugget;
	public static Item zirconium_pickaxe;
	public static Item zirconium_powder;
	public static Item zirconium_shears;
	public static Item zirconium_shovel;
	public static Item zirconium_smallpowder;
	public static Item zirconium_sword;
	public static Item zirconium_rod;
	public static Item zirconium_gear;

	public static Item zirconium_slab;
//	public static Item zirconium_shield;

	public static Item zirconium_crystal;
	public static Item zirconium_shard;
	public static Item zirconium_clump;
	public static Item zirconium_powder_dirty;

	public static Item zirconium_dense_plate;
	public static Item zirconium_crushed;
	public static Item zirconium_crushed_purified;

	private static boolean initDone = false;

	private static Map<Item, String> itemRegistry = new HashMap<>();
	private static Map<String, Item> allItems = new HashMap<>();
	private static Map<MetalMaterial, List<Item>> itemsByMetal = new HashMap<>();

	private static Map<BlockDoor, Item> doorMap = new HashMap<>();

	private static Map<Class<?>, Integer> classSortingValues = new HashMap<>();
	private static Map<MetalMaterial, Integer> materialSortingValues = new HashMap<>();

	/**
	 * Gets an item by its name. The name is the name as it is registered in the
	 * GameRegistry, not its unlocalized name (the unlocalized name is the
	 * registered name plus the prefix "modernmetals.")
	 * @param name The name of the item in question
	 * @return The item matching that name, or null if there isn't one
	 */
	public static Item getItemByName(String name) {
		return allItems.get(name);
	}

	/**
	 * This is the reverse of the getItemByName(...) method, returning the
	 * registered name of an item instance (Modern Metals items only).
	 * @param i The item in question
	 * @return The name of the item, or null if the item is not a Modern Metals
	 * item.
	 */
	public static String getNameOfItem(Item i) {
		return itemRegistry.get(i);
	}

	/**
	 * Gets a map of all items added, sorted by metal
	 * @return An unmodifiable map of added items catagorized by metal material
	 */
	public static Map<MetalMaterial, List<Item>> getItemsByMetal() {
		return Collections.unmodifiableMap(itemsByMetal);
	}

	/**
	 * Gets the inventory item corresponding to a given door block
	 *
	 * @param b The door block
	 * @return The item that the player should use to place that kind of door
	 */
	public static Item getDoorItemForBlock(BlockMetalDoor b) {
		return doorMap.get(b);
	}

	/**
	 *
	 */
	public static void init() {
		if(initDone)
			return;
		
		modernmetals.init.Blocks.init();

		aluminum_arrow = createArrow(Materials.aluminum);
		aluminum_axe = createAxe(Materials.aluminum);
		aluminum_boots = createBoots(Materials.aluminum);
		aluminum_bolt = createBolt(Materials.aluminum);
		aluminum_bow = createBow(Materials.aluminum);
		aluminum_chestplate = createChestplate(Materials.aluminum);
		aluminum_crackhammer = createCrackhammer(Materials.aluminum);
		aluminum_crossbow = createCrossbow(Materials.aluminum);
		aluminum_door = createDoor(Materials.aluminum, Blocks.aluminum_door);
		aluminum_fishing_rod = createFishingRod(Materials.aluminum);
		aluminum_helmet = createHelmet(Materials.aluminum);
		aluminum_hoe = createHoe(Materials.aluminum);
		aluminum_horsearmor = createHorseArmor(Materials.aluminum);
		aluminum_ingot = createIngot(Materials.aluminum);
		aluminum_leggings = createLeggings(Materials.aluminum);
		aluminum_nugget = createNugget(Materials.aluminum);
		aluminum_pickaxe = createPickaxe(Materials.aluminum);
		aluminum_powder = createPowder(Materials.aluminum);
		aluminum_shears = createShears(Materials.aluminum);
		aluminum_shovel = createShovel(Materials.aluminum);
		aluminum_smallpowder = createSmallPowder(Materials.aluminum);
		aluminum_sword = createSword(Materials.aluminum);
		aluminum_rod = createRod(Materials.aluminum);
		aluminum_gear = createGear(Materials.aluminum);

		aluminum_slab = createSlab(Materials.aluminum, Blocks.aluminum_slab, Blocks.aluminum_slab, Blocks.double_aluminum_slab);
//		aluminum_shield = createShield(Materials.aluminum);

		aluminum_crystal = createCrystal(Materials.aluminum);
		aluminum_shard = createShard(Materials.aluminum);
		aluminum_clump = createClump(Materials.aluminum);
		aluminum_powder_dirty = createDirtyPowder(Materials.aluminum);

		aluminum_dense_plate = createDensePlate(Materials.aluminum);
		aluminum_crushed = createCrushed(Materials.aluminum);
		aluminum_crushed_purified = createCrushedPurified(Materials.aluminum);

		aluminumbrass_arrow = createArrow(Materials.aluminumbrass);
		aluminumbrass_axe = createAxe(Materials.aluminumbrass);
		aluminumbrass_blend = createBlend(Materials.aluminumbrass);
		aluminumbrass_boots = createBoots(Materials.aluminumbrass);
		aluminumbrass_bolt = createBolt(Materials.aluminumbrass);
		aluminumbrass_bow = createBow(Materials.aluminumbrass);
		aluminumbrass_chestplate = createChestplate(Materials.aluminumbrass);
		aluminumbrass_crackhammer = createCrackhammer(Materials.aluminumbrass);
		aluminumbrass_crossbow = createCrossbow(Materials.aluminumbrass);
		aluminumbrass_door = createDoor(Materials.aluminumbrass, Blocks.aluminumbrass_door);
		aluminumbrass_fishing_rod = createFishingRod(Materials.aluminumbrass);
		aluminumbrass_helmet = createHelmet(Materials.aluminumbrass);
		aluminumbrass_hoe = createHoe(Materials.aluminumbrass);
		aluminumbrass_horsearmor = createHorseArmor(Materials.aluminumbrass);
		aluminumbrass_ingot = createIngot(Materials.aluminumbrass);
		aluminumbrass_leggings = createLeggings(Materials.aluminumbrass);
		aluminumbrass_nugget = createNugget(Materials.aluminumbrass);
		aluminumbrass_pickaxe = createPickaxe(Materials.aluminumbrass);
		aluminumbrass_powder = createPowder(Materials.aluminumbrass);
		aluminumbrass_shears = createShears(Materials.aluminumbrass);
		aluminumbrass_shovel = createShovel(Materials.aluminumbrass);
		aluminumbrass_smallblend = createSmallBlend(Materials.aluminumbrass);
		aluminumbrass_smallpowder = createSmallPowder(Materials.aluminumbrass);
		aluminumbrass_sword = createSword(Materials.aluminumbrass);
		aluminumbrass_rod = createRod(Materials.aluminumbrass);
		aluminumbrass_gear = createGear(Materials.aluminumbrass);

		aluminumbrass_slab = createSlab(Materials.aluminumbrass, Blocks.aluminumbrass_slab, Blocks.aluminumbrass_slab, Blocks.double_aluminumbrass_slab);
//		aluminumbrass_shield = createShield(Materials.aluminumbrass);

//		aluminumbrass_crystal = createCrystal(Materials.aluminumbrass);
//		aluminumbrass_shard = createShard(Materials.aluminumbrass);
//		aluminumbrass_clump = createClump(Materials.aluminumbrass);
//		aluminumbrass_powder_dirty = createDirtyPowder(Materials.aluminumbrass);

		aluminumbrass_dense_plate = createDensePlate(Materials.aluminumbrass);
//		aluminumbrass_crushed = createCrushed(Materials.aluminumbrass);
//		aluminumbrass_crushed_purified = createCrushedPurified(Materials.aluminumbrass);

		cadmium_arrow = createArrow(Materials.cadmium);
		cadmium_axe = createAxe(Materials.cadmium);
		cadmium_boots = createBoots(Materials.cadmium);
		cadmium_bolt = createBolt(Materials.cadmium);
		cadmium_bow = createBow(Materials.cadmium);
		cadmium_chestplate = createChestplate(Materials.cadmium);
		cadmium_crackhammer = createCrackhammer(Materials.cadmium);
		cadmium_crossbow = createCrossbow(Materials.cadmium);
		cadmium_door = createDoor(Materials.cadmium, Blocks.cadmium_door);
		cadmium_fishing_rod = createFishingRod(Materials.cadmium);
		cadmium_helmet = createHelmet(Materials.cadmium);
		cadmium_hoe = createHoe(Materials.cadmium);
		cadmium_horsearmor = createHorseArmor(Materials.cadmium);
		cadmium_ingot = createIngot(Materials.cadmium);
		cadmium_leggings = createLeggings(Materials.cadmium);
		cadmium_nugget = createNugget(Materials.cadmium);
		cadmium_pickaxe = createPickaxe(Materials.cadmium);
		cadmium_powder = createPowder(Materials.cadmium);
		cadmium_shears = createShears(Materials.cadmium);
		cadmium_shovel = createShovel(Materials.cadmium);
		cadmium_smallpowder = createSmallPowder(Materials.cadmium);
		cadmium_sword = createSword(Materials.cadmium);
		cadmium_rod = createRod(Materials.cadmium);
		cadmium_gear = createGear(Materials.cadmium);

		cadmium_slab = createSlab(Materials.cadmium, Blocks.cadmium_slab, Blocks.cadmium_slab, Blocks.double_cadmium_slab);
//		cadmium_shield = createShield(Materials.cadmium);

		cadmium_crystal = createCrystal(Materials.cadmium);
		cadmium_shard = createShard(Materials.cadmium);
		cadmium_clump = createClump(Materials.cadmium);
		cadmium_powder_dirty = createDirtyPowder(Materials.cadmium);

		cadmium_dense_plate = createDensePlate(Materials.cadmium);
		cadmium_crushed = createCrushed(Materials.cadmium);
		cadmium_crushed_purified = createCrushedPurified(Materials.cadmium);

		chromium_arrow = createArrow(Materials.chromium);
		chromium_axe = createAxe(Materials.chromium);
		chromium_boots = createBoots(Materials.chromium);
		chromium_bolt = createBolt(Materials.chromium);
		chromium_bow = createBow(Materials.chromium);
		chromium_chestplate = createChestplate(Materials.chromium);
		chromium_crackhammer = createCrackhammer(Materials.chromium);
		chromium_crossbow = createCrossbow(Materials.chromium);
		chromium_door = createDoor(Materials.chromium, Blocks.chromium_door);
		chromium_fishing_rod = createFishingRod(Materials.chromium);
		chromium_helmet = createHelmet(Materials.chromium);
		chromium_hoe = createHoe(Materials.chromium);
		chromium_horsearmor = createHorseArmor(Materials.chromium);
		chromium_ingot = createIngot(Materials.chromium);
		chromium_leggings = createLeggings(Materials.chromium);
		chromium_nugget = createNugget(Materials.chromium);
		chromium_pickaxe = createPickaxe(Materials.chromium);
		chromium_powder = createPowder(Materials.chromium);
		chromium_shears = createShears(Materials.chromium);
		chromium_shovel = createShovel(Materials.chromium);
		chromium_smallpowder = createSmallPowder(Materials.chromium);
		chromium_sword = createSword(Materials.chromium);
		chromium_rod = createRod(Materials.chromium);
		chromium_gear = createGear(Materials.chromium);

		chromium_slab = createSlab(Materials.chromium, Blocks.chromium_slab, Blocks.chromium_slab, Blocks.double_chromium_slab);
//		chromium_shield = createShield(Materials.chromium);

		chromium_crystal = createCrystal(Materials.chromium);
		chromium_shard = createShard(Materials.chromium);
		chromium_clump = createClump(Materials.chromium);
		chromium_powder_dirty = createDirtyPowder(Materials.chromium);

		chromium_dense_plate = createDensePlate(Materials.chromium);
		chromium_crushed = createCrushed(Materials.chromium);
		chromium_crushed_purified = createCrushedPurified(Materials.chromium);

		galvanizedsteel_arrow = createArrow(Materials.galvanizedsteel);
		galvanizedsteel_axe = createAxe(Materials.galvanizedsteel);
		galvanizedsteel_blend = createBlend(Materials.galvanizedsteel);
		galvanizedsteel_boots = createBoots(Materials.galvanizedsteel);
		galvanizedsteel_bolt = createBolt(Materials.galvanizedsteel);
		galvanizedsteel_bow = createBow(Materials.galvanizedsteel);
		galvanizedsteel_chestplate = createChestplate(Materials.galvanizedsteel);
		galvanizedsteel_crackhammer = createCrackhammer(Materials.galvanizedsteel);
		galvanizedsteel_crossbow = createCrossbow(Materials.galvanizedsteel);
		galvanizedsteel_door = createDoor(Materials.galvanizedsteel, Blocks.galvanizedsteel_door);
		galvanizedsteel_fishing_rod = createFishingRod(Materials.galvanizedsteel);
		galvanizedsteel_helmet = createHelmet(Materials.galvanizedsteel);
		galvanizedsteel_hoe = createHoe(Materials.galvanizedsteel);
		galvanizedsteel_horsearmor = createHorseArmor(Materials.galvanizedsteel);
		galvanizedsteel_ingot = createIngot(Materials.galvanizedsteel);
		galvanizedsteel_leggings = createLeggings(Materials.galvanizedsteel);
		galvanizedsteel_nugget = createNugget(Materials.galvanizedsteel);
		galvanizedsteel_pickaxe = createPickaxe(Materials.galvanizedsteel);
		galvanizedsteel_powder = createPowder(Materials.galvanizedsteel);
		galvanizedsteel_shears = createShears(Materials.galvanizedsteel);
		galvanizedsteel_shovel = createShovel(Materials.galvanizedsteel);
		galvanizedsteel_smallblend = createSmallBlend(Materials.galvanizedsteel);
		galvanizedsteel_smallpowder = createSmallPowder(Materials.galvanizedsteel);
		galvanizedsteel_sword = createSword(Materials.galvanizedsteel);
		galvanizedsteel_rod = createRod(Materials.galvanizedsteel);
		galvanizedsteel_gear = createGear(Materials.galvanizedsteel);

		galvanizedsteel_slab = createSlab(Materials.galvanizedsteel, Blocks.galvanizedsteel_slab, Blocks.galvanizedsteel_slab, Blocks.double_galvanizedsteel_slab);
//		galvanizedsteel_shield = createShield(Materials.galvanizedsteel);

//		galvanizedsteel_crystal = createCrystal(Materials.galvanizedsteel);
//		galvanizedsteel_shard = createShard(Materials.galvanizedsteel);
//		galvanizedsteel_clump = createClump(Materials.galvanizedsteel);
//		galvanizedsteel_powder_dirty = createDirtyPowder(Materials.galvanizedsteel);

		galvanizedsteel_dense_plate = createDensePlate(Materials.galvanizedsteel);
//		galvanizedsteel_crushed = createCrushed(Materials.galvanizedsteel);
//		galvanizedsteel_crushed_purified = createCrushedPurified(Materials.galvanizedsteel);

		iridium_arrow = createArrow(Materials.iridium);
		iridium_axe = createAxe(Materials.iridium);
		iridium_boots = createBoots(Materials.iridium);
		iridium_bolt = createBolt(Materials.iridium);
		iridium_bow = createBow(Materials.iridium);
		iridium_chestplate = createChestplate(Materials.iridium);
		iridium_crackhammer = createCrackhammer(Materials.iridium);
		iridium_crossbow = createCrossbow(Materials.iridium);
		iridium_door = createDoor(Materials.iridium, Blocks.iridium_door);
		iridium_fishing_rod = createFishingRod(Materials.iridium);
		iridium_helmet = createHelmet(Materials.iridium);
		iridium_hoe = createHoe(Materials.iridium);
		iridium_horsearmor = createHorseArmor(Materials.iridium);
		iridium_ingot = createIngot(Materials.iridium);
		iridium_leggings = createLeggings(Materials.iridium);
		iridium_nugget = createNugget(Materials.iridium);
		iridium_pickaxe = createPickaxe(Materials.iridium);
		iridium_powder = createPowder(Materials.iridium);
		iridium_shears = createShears(Materials.iridium);
		iridium_shovel = createShovel(Materials.iridium);
		iridium_smallpowder = createSmallPowder(Materials.iridium);
		iridium_sword = createSword(Materials.iridium);
		iridium_rod = createRod(Materials.iridium);
		iridium_gear = createGear(Materials.iridium);

		iridium_slab = createSlab(Materials.iridium, Blocks.iridium_slab, Blocks.iridium_slab, Blocks.double_iridium_slab);
//		iridium_shield = createShield(Materials.iridium);

		iridium_crystal = createCrystal(Materials.iridium);
		iridium_shard = createShard(Materials.iridium);
		iridium_clump = createClump(Materials.iridium);
		iridium_powder_dirty = createDirtyPowder(Materials.iridium);

		iridium_dense_plate = createDensePlate(Materials.iridium);
		iridium_crushed = createCrushed(Materials.iridium);
		iridium_crushed_purified = createCrushedPurified(Materials.iridium);

		magnesium_arrow = createArrow(Materials.magnesium);
		magnesium_axe = createAxe(Materials.magnesium);
		magnesium_boots = createBoots(Materials.magnesium);
		magnesium_bolt = createBolt(Materials.magnesium);
		magnesium_bow = createBow(Materials.magnesium);
		magnesium_chestplate = createChestplate(Materials.magnesium);
		magnesium_crackhammer = createCrackhammer(Materials.magnesium);
		magnesium_crossbow = createCrossbow(Materials.magnesium);
		magnesium_door = createDoor(Materials.magnesium, Blocks.magnesium_door);
		magnesium_fishing_rod = createFishingRod(Materials.magnesium);
		magnesium_helmet = createHelmet(Materials.magnesium);
		magnesium_hoe = createHoe(Materials.magnesium);
		magnesium_horsearmor = createHorseArmor(Materials.magnesium);
		magnesium_ingot = createIngot(Materials.magnesium);
		magnesium_leggings = createLeggings(Materials.magnesium);
		magnesium_nugget = createNugget(Materials.magnesium);
		magnesium_pickaxe = createPickaxe(Materials.magnesium);
		magnesium_powder = createPowder(Materials.magnesium);
		magnesium_shears = createShears(Materials.magnesium);
		magnesium_shovel = createShovel(Materials.magnesium);
		magnesium_smallpowder = createSmallPowder(Materials.magnesium);
		magnesium_sword = createSword(Materials.magnesium);
		magnesium_rod = createRod(Materials.magnesium);
		magnesium_gear = createGear(Materials.magnesium);

		magnesium_slab = createSlab(Materials.magnesium, Blocks.magnesium_slab, Blocks.magnesium_slab, Blocks.double_magnesium_slab);
//		magnesium_shield = createShield(Materials.magnesium);

		magnesium_crystal = createCrystal(Materials.magnesium);
		magnesium_shard = createShard(Materials.magnesium);
		magnesium_clump = createClump(Materials.magnesium);
		magnesium_powder_dirty = createDirtyPowder(Materials.magnesium);

		magnesium_dense_plate = createDensePlate(Materials.magnesium);
		magnesium_crushed = createCrushed(Materials.magnesium);
		magnesium_crushed_purified = createCrushedPurified(Materials.magnesium);

		manganese_arrow = createArrow(Materials.manganese);
		manganese_axe = createAxe(Materials.manganese);
		manganese_boots = createBoots(Materials.manganese);
		manganese_bolt = createBolt(Materials.manganese);
		manganese_bow = createBow(Materials.manganese);
		manganese_chestplate = createChestplate(Materials.manganese);
		manganese_crackhammer = createCrackhammer(Materials.manganese);
		manganese_crossbow = createCrossbow(Materials.manganese);
		manganese_door = createDoor(Materials.manganese, Blocks.manganese_door);
		manganese_fishing_rod = createFishingRod(Materials.manganese);
		manganese_helmet = createHelmet(Materials.manganese);
		manganese_hoe = createHoe(Materials.manganese);
		manganese_horsearmor = createHorseArmor(Materials.manganese);
		manganese_ingot = createIngot(Materials.manganese);
		manganese_leggings = createLeggings(Materials.manganese);
		manganese_nugget = createNugget(Materials.manganese);
		manganese_pickaxe = createPickaxe(Materials.manganese);
		manganese_powder = createPowder(Materials.manganese);
		manganese_shears = createShears(Materials.manganese);
		manganese_shovel = createShovel(Materials.manganese);
		manganese_smallpowder = createSmallPowder(Materials.manganese);
		manganese_sword = createSword(Materials.manganese);
		manganese_rod = createRod(Materials.manganese);
		manganese_gear = createGear(Materials.manganese);

		manganese_slab = createSlab(Materials.manganese, Blocks.manganese_slab, Blocks.manganese_slab, Blocks.double_manganese_slab);
//		manganese_shield = createShield(Materials.manganese);

		manganese_crystal = createCrystal(Materials.manganese);
		manganese_shard = createShard(Materials.manganese);
		manganese_clump = createClump(Materials.manganese);
		manganese_powder_dirty = createDirtyPowder(Materials.manganese);

		manganese_dense_plate = createDensePlate(Materials.manganese);
		manganese_crushed = createCrushed(Materials.manganese);
		manganese_crushed_purified = createCrushedPurified(Materials.manganese);

		nichrome_arrow = createArrow(Materials.nichrome);
		nichrome_axe = createAxe(Materials.nichrome);
		nichrome_blend = createBlend(Materials.nichrome);
		nichrome_boots = createBoots(Materials.nichrome);
		nichrome_bolt = createBolt(Materials.nichrome);
		nichrome_bow = createBow(Materials.nichrome);
		nichrome_chestplate = createChestplate(Materials.nichrome);
		nichrome_crackhammer = createCrackhammer(Materials.nichrome);
		nichrome_crossbow = createCrossbow(Materials.nichrome);
		nichrome_door = createDoor(Materials.nichrome, Blocks.nichrome_door);
		nichrome_fishing_rod = createFishingRod(Materials.nichrome);
		nichrome_helmet = createHelmet(Materials.nichrome);
		nichrome_hoe = createHoe(Materials.nichrome);
		nichrome_horsearmor = createHorseArmor(Materials.nichrome);
		nichrome_ingot = createIngot(Materials.nichrome);
		nichrome_leggings = createLeggings(Materials.nichrome);
		nichrome_nugget = createNugget(Materials.nichrome);
		nichrome_pickaxe = createPickaxe(Materials.nichrome);
		nichrome_powder = createPowder(Materials.nichrome);
		nichrome_shears = createShears(Materials.nichrome);
		nichrome_shovel = createShovel(Materials.nichrome);
		nichrome_smallblend = createSmallBlend(Materials.nichrome);
		nichrome_smallpowder = createSmallPowder(Materials.nichrome);
		nichrome_sword = createSword(Materials.nichrome);
		nichrome_rod = createRod(Materials.nichrome);
		nichrome_gear = createGear(Materials.nichrome);

		nichrome_slab = createSlab(Materials.nichrome, Blocks.nichrome_slab, Blocks.nichrome_slab, Blocks.double_nichrome_slab);
//		nichrome_shield = createShield(Materials.nichrome);

//		nichrome_crystal = createCrystal(Materials.nichrome);
//		nichrome_shard = createShard(Materials.nichrome);
//		nichrome_clump = createClump(Materials.nichrome);
//		nichrome_powder_dirty = createDirtyPowder(Materials.nichrome);

		nichrome_dense_plate = createDensePlate(Materials.nichrome);
//		nichrome_crushed = createCrushed(Materials.nichrome);
//		nichrome_crushed_purified = createCrushedPurified(Materials.nichrome);

		osmium_arrow = createArrow(Materials.osmium);
		osmium_axe = createAxe(Materials.osmium);
		osmium_boots = createBoots(Materials.osmium);
		osmium_bolt = createBolt(Materials.osmium);
		osmium_bow = createBow(Materials.osmium);
		osmium_chestplate = createChestplate(Materials.osmium);
		osmium_crackhammer = createCrackhammer(Materials.osmium);
		osmium_crossbow = createCrossbow(Materials.osmium);
		osmium_door = createDoor(Materials.osmium, Blocks.osmium_door);
		osmium_fishing_rod = createFishingRod(Materials.osmium);
		osmium_helmet = createHelmet(Materials.osmium);
		osmium_hoe = createHoe(Materials.osmium);
		osmium_horsearmor = createHorseArmor(Materials.osmium);
		osmium_ingot = createIngot(Materials.osmium);
		osmium_leggings = createLeggings(Materials.osmium);
		osmium_nugget = createNugget(Materials.osmium);
		osmium_pickaxe = createPickaxe(Materials.osmium);
		osmium_powder = createPowder(Materials.osmium);
		osmium_shears = createShears(Materials.osmium);
		osmium_shovel = createShovel(Materials.osmium);
		osmium_smallpowder = createSmallPowder(Materials.osmium);
		osmium_sword = createSword(Materials.osmium);
		osmium_rod = createRod(Materials.osmium);
		osmium_gear = createGear(Materials.osmium);

		osmium_slab = createSlab(Materials.osmium, Blocks.osmium_slab, Blocks.osmium_slab, Blocks.double_osmium_slab);
//		osmium_shield = createShield(Materials.osmium);

//		osmium_crystal = createCrystal(Materials.osmium);
//		osmium_shard = createShard(Materials.osmium);
//		osmium_clump = createClump(Materials.osmium);
//		osmium_powder_dirty = createDirtyPowder(Materials.osmium);

		osmium_dense_plate = createDensePlate(Materials.osmium);
		osmium_crushed = createCrushed(Materials.osmium);
		osmium_crushed_purified = createCrushedPurified(Materials.osmium);

		plutonium_arrow = createArrow(Materials.plutonium);
		plutonium_axe = createAxe(Materials.plutonium);
		plutonium_boots = createBoots(Materials.plutonium);
		plutonium_bolt = createBolt(Materials.plutonium);
		plutonium_bow = createBow(Materials.plutonium);
		plutonium_chestplate = createChestplate(Materials.plutonium);
		plutonium_crackhammer = createCrackhammer(Materials.plutonium);
		plutonium_crossbow = createCrossbow(Materials.plutonium);
		plutonium_door = createDoor(Materials.plutonium, Blocks.plutonium_door);
		plutonium_fishing_rod = createFishingRod(Materials.plutonium);
		plutonium_helmet = createHelmet(Materials.plutonium);
		plutonium_hoe = createHoe(Materials.plutonium);
		plutonium_horsearmor = createHorseArmor(Materials.plutonium);
		plutonium_ingot = createIngot(Materials.plutonium);
		plutonium_leggings = createLeggings(Materials.plutonium);
		plutonium_nugget = createNugget(Materials.plutonium);
		plutonium_pickaxe = createPickaxe(Materials.plutonium);
		plutonium_powder = createPowder(Materials.plutonium);
		plutonium_shears = createShears(Materials.plutonium);
		plutonium_shovel = createShovel(Materials.plutonium);
		plutonium_smallpowder = createSmallPowder(Materials.plutonium);
		plutonium_sword = createSword(Materials.plutonium);
		plutonium_rod = createRod(Materials.plutonium);
		plutonium_gear = createGear(Materials.plutonium);

		plutonium_slab = createSlab(Materials.plutonium, Blocks.plutonium_slab, Blocks.plutonium_slab, Blocks.double_plutonium_slab);
//		plutonium_shield = createShield(Materials.plutonium);

		plutonium_crystal = createCrystal(Materials.plutonium);
		plutonium_shard = createShard(Materials.plutonium);
		plutonium_clump = createClump(Materials.plutonium);
		plutonium_powder_dirty = createDirtyPowder(Materials.plutonium);

		plutonium_dense_plate = createDensePlate(Materials.plutonium);
		plutonium_crushed = createCrushed(Materials.plutonium);
		plutonium_crushed_purified = createCrushedPurified(Materials.plutonium);

		rutile_arrow = createArrow(Materials.rutile);
		rutile_axe = createAxe(Materials.rutile);
		rutile_boots = createBoots(Materials.rutile);
		rutile_bolt = createBolt(Materials.rutile);
		rutile_bow = createBow(Materials.rutile);
		rutile_chestplate = createChestplate(Materials.rutile);
		rutile_crackhammer = createCrackhammer(Materials.rutile);
		rutile_crossbow = createCrossbow(Materials.rutile);
		rutile_door = createDoor(Materials.rutile, Blocks.rutile_door);
		rutile_fishing_rod = createFishingRod(Materials.rutile);
		rutile_helmet = createHelmet(Materials.rutile);
		rutile_hoe = createHoe(Materials.rutile);
		rutile_horsearmor = createHorseArmor(Materials.rutile);
		rutile_ingot = createIngot(Materials.rutile);
		rutile_leggings = createLeggings(Materials.rutile);
		rutile_nugget = createNugget(Materials.rutile);
		rutile_pickaxe = createPickaxe(Materials.rutile);
		rutile_powder = createPowder(Materials.rutile);
		rutile_shears = createShears(Materials.rutile);
		rutile_shovel = createShovel(Materials.rutile);
		rutile_smallpowder = createSmallPowder(Materials.rutile);
		rutile_sword = createSword(Materials.rutile);
		rutile_rod = createRod(Materials.rutile);
		rutile_gear = createGear(Materials.rutile);

		rutile_slab = createSlab(Materials.rutile, Blocks.rutile_slab, Blocks.rutile_slab, Blocks.double_rutile_slab);
//		rutile_shield = createShield(Materials.rutile);

		rutile_crystal = createCrystal(Materials.rutile);
		rutile_shard = createShard(Materials.rutile);
		rutile_clump = createClump(Materials.rutile);
		rutile_powder_dirty = createDirtyPowder(Materials.rutile);

		rutile_dense_plate = createDensePlate(Materials.rutile);
		rutile_crushed = createCrushed(Materials.rutile);
		rutile_crushed_purified = createCrushedPurified(Materials.rutile);

		stainlesssteel_arrow = createArrow(Materials.stainlesssteel);
		stainlesssteel_axe = createAxe(Materials.stainlesssteel);
		stainlesssteel_blend = createBlend(Materials.stainlesssteel);
		stainlesssteel_boots = createBoots(Materials.stainlesssteel);
		stainlesssteel_bolt = createBolt(Materials.stainlesssteel);
		stainlesssteel_bow = createBow(Materials.stainlesssteel);
		stainlesssteel_chestplate = createChestplate(Materials.stainlesssteel);
		stainlesssteel_crackhammer = createCrackhammer(Materials.stainlesssteel);
		stainlesssteel_crossbow = createCrossbow(Materials.stainlesssteel);
		stainlesssteel_door = createDoor(Materials.stainlesssteel, Blocks.stainlesssteel_door);
		stainlesssteel_fishing_rod = createFishingRod(Materials.stainlesssteel);
		stainlesssteel_helmet = createHelmet(Materials.stainlesssteel);
		stainlesssteel_hoe = createHoe(Materials.stainlesssteel);
		stainlesssteel_horsearmor = createHorseArmor(Materials.stainlesssteel);
		stainlesssteel_ingot = createIngot(Materials.stainlesssteel);
		stainlesssteel_leggings = createLeggings(Materials.stainlesssteel);
		stainlesssteel_nugget = createNugget(Materials.stainlesssteel);
		stainlesssteel_pickaxe = createPickaxe(Materials.stainlesssteel);
		stainlesssteel_powder = createPowder(Materials.stainlesssteel);
		stainlesssteel_shears = createShears(Materials.stainlesssteel);
		stainlesssteel_shovel = createShovel(Materials.stainlesssteel);
		stainlesssteel_smallblend = createSmallBlend(Materials.stainlesssteel);
		stainlesssteel_smallpowder = createSmallPowder(Materials.stainlesssteel);
		stainlesssteel_sword = createSword(Materials.stainlesssteel);
		stainlesssteel_rod = createRod(Materials.stainlesssteel);
		stainlesssteel_gear = createGear(Materials.stainlesssteel);

		stainlesssteel_slab = createSlab(Materials.stainlesssteel, Blocks.stainlesssteel_slab, Blocks.stainlesssteel_slab, Blocks.double_stainlesssteel_slab);
//		stainlesssteel_shield = createShield(Materials.stainlesssteel);

//		stainlesssteel_crystal = createCrystal(Materials.stainlesssteel);
//		stainlesssteel_shard = createShard(Materials.stainlesssteel);
//		stainlesssteel_clump = createClump(Materials.stainlesssteel);
//		stainlesssteel_powder_dirty = createDirtyPowder(Materials.stainlesssteel);

		stainlesssteel_dense_plate = createDensePlate(Materials.stainlesssteel);
//		stainlesssteel_crushed = createCrushed(Materials.stainlesssteel);
//		stainlesssteel_crushed_purified = createCrushedPurified(Materials.stainlesssteel);

		tantalum_arrow = createArrow(Materials.tantalum);
		tantalum_axe = createAxe(Materials.tantalum);
		tantalum_boots = createBoots(Materials.tantalum);
		tantalum_bolt = createBolt(Materials.tantalum);
		tantalum_bow = createBow(Materials.tantalum);
		tantalum_chestplate = createChestplate(Materials.tantalum);
		tantalum_crackhammer = createCrackhammer(Materials.tantalum);
		tantalum_crossbow = createCrossbow(Materials.tantalum);
		tantalum_door = createDoor(Materials.tantalum, Blocks.tantalum_door);
		tantalum_fishing_rod = createFishingRod(Materials.tantalum);
		tantalum_helmet = createHelmet(Materials.tantalum);
		tantalum_hoe = createHoe(Materials.tantalum);
		tantalum_horsearmor = createHorseArmor(Materials.tantalum);
		tantalum_ingot = createIngot(Materials.tantalum);
		tantalum_leggings = createLeggings(Materials.tantalum);
		tantalum_nugget = createNugget(Materials.tantalum);
		tantalum_pickaxe = createPickaxe(Materials.tantalum);
		tantalum_powder = createPowder(Materials.tantalum);
		tantalum_shears = createShears(Materials.tantalum);
		tantalum_shovel = createShovel(Materials.tantalum);
		tantalum_smallpowder = createSmallPowder(Materials.tantalum);
		tantalum_sword = createSword(Materials.tantalum);
		tantalum_rod = createRod(Materials.tantalum);
		tantalum_gear = createGear(Materials.tantalum);

		tantalum_slab = createSlab(Materials.tantalum, Blocks.tantalum_slab, Blocks.tantalum_slab, Blocks.double_tantalum_slab);
//		tantalum_shield = createShield(Materials.tantalum);

		tantalum_crystal = createCrystal(Materials.tantalum);
		tantalum_shard = createShard(Materials.tantalum);
		tantalum_clump = createClump(Materials.tantalum);
		tantalum_powder_dirty = createDirtyPowder(Materials.tantalum);

		tantalum_dense_plate = createDensePlate(Materials.tantalum);
		tantalum_crushed = createCrushed(Materials.tantalum);
		tantalum_crushed_purified = createCrushedPurified(Materials.tantalum);

		titanium_arrow = createArrow(Materials.titanium);
		titanium_axe = createAxe(Materials.titanium);
		titanium_blend = createBlend(Materials.titanium);
		titanium_boots = createBoots(Materials.titanium);
		titanium_bolt = createBolt(Materials.titanium);
		titanium_bow = createBow(Materials.titanium);
		titanium_chestplate = createChestplate(Materials.titanium);
		titanium_crackhammer = createCrackhammer(Materials.titanium);
		titanium_crossbow = createCrossbow(Materials.titanium);
		titanium_door = createDoor(Materials.titanium, Blocks.titanium_door);
		titanium_fishing_rod = createFishingRod(Materials.titanium);
		titanium_helmet = createHelmet(Materials.titanium);
		titanium_hoe = createHoe(Materials.titanium);
		titanium_horsearmor = createHorseArmor(Materials.titanium);
		titanium_ingot = createIngot(Materials.titanium);
		titanium_leggings = createLeggings(Materials.titanium);
		titanium_nugget = createNugget(Materials.titanium);
		titanium_pickaxe = createPickaxe(Materials.titanium);
		titanium_powder = createPowder(Materials.titanium);
		titanium_shears = createShears(Materials.titanium);
		titanium_shovel = createShovel(Materials.titanium);
		titanium_smallblend = createSmallBlend(Materials.titanium);
		titanium_smallpowder = createSmallPowder(Materials.titanium);
		titanium_sword = createSword(Materials.titanium);
		titanium_rod = createRod(Materials.titanium);
		titanium_gear = createGear(Materials.titanium);

		titanium_slab = createSlab(Materials.titanium, Blocks.titanium_slab, Blocks.titanium_slab, Blocks.double_titanium_slab);
//		titanium_shield = createShield(Materials.titanium);

		titanium_crystal = createCrystal(Materials.titanium);
		titanium_shard = createShard(Materials.titanium);
		titanium_clump = createClump(Materials.titanium);
		titanium_powder_dirty = createDirtyPowder(Materials.titanium);

		titanium_dense_plate = createDensePlate(Materials.titanium);
		titanium_crushed = createCrushed(Materials.titanium);
		titanium_crushed_purified = createCrushedPurified(Materials.titanium);

		tungsten_arrow = createArrow(Materials.tungsten);
		tungsten_axe = createAxe(Materials.tungsten);
		tungsten_boots = createBoots(Materials.tungsten);
		tungsten_bolt = createBolt(Materials.tungsten);
		tungsten_bow = createBow(Materials.tungsten);
		tungsten_chestplate = createChestplate(Materials.tungsten);
		tungsten_crackhammer = createCrackhammer(Materials.tungsten);
		tungsten_crossbow = createCrossbow(Materials.tungsten);
		tungsten_door = createDoor(Materials.tungsten, Blocks.tungsten_door);
		tungsten_fishing_rod = createFishingRod(Materials.tungsten);
		tungsten_helmet = createHelmet(Materials.tungsten);
		tungsten_hoe = createHoe(Materials.tungsten);
		tungsten_horsearmor = createHorseArmor(Materials.tungsten);
		tungsten_ingot = createIngot(Materials.tungsten);
		tungsten_leggings = createLeggings(Materials.tungsten);
		tungsten_nugget = createNugget(Materials.tungsten);
		tungsten_pickaxe = createPickaxe(Materials.tungsten);
		tungsten_powder = createPowder(Materials.tungsten);
		tungsten_shears = createShears(Materials.tungsten);
		tungsten_shovel = createShovel(Materials.tungsten);
		tungsten_smallpowder = createSmallPowder(Materials.tungsten);
		tungsten_sword = createSword(Materials.tungsten);
		tungsten_rod = createRod(Materials.tungsten);
		tungsten_gear = createGear(Materials.tungsten);

		tungsten_slab = createSlab(Materials.tungsten, Blocks.tungsten_slab, Blocks.tungsten_slab, Blocks.double_tungsten_slab);
//		tungsten_shield = createShield(Materials.tungsten);

		tungsten_crystal = createCrystal(Materials.tungsten);
		tungsten_shard = createShard(Materials.tungsten);
		tungsten_clump = createClump(Materials.tungsten);
		tungsten_powder_dirty = createDirtyPowder(Materials.tungsten);

		tungsten_dense_plate = createDensePlate(Materials.tungsten);
		tungsten_crushed = createCrushed(Materials.tungsten);
		tungsten_crushed_purified = createCrushedPurified(Materials.tungsten);

		uranium_arrow = createArrow(Materials.uranium);
		uranium_axe = createAxe(Materials.uranium);
		uranium_boots = createBoots(Materials.uranium);
		uranium_bolt = createBolt(Materials.uranium);
		uranium_bow = createBow(Materials.uranium);
		uranium_chestplate = createChestplate(Materials.uranium);
		uranium_crackhammer = createCrackhammer(Materials.uranium);
		uranium_crossbow = createCrossbow(Materials.uranium);
		uranium_door = createDoor(Materials.uranium, Blocks.uranium_door);
		uranium_fishing_rod = createFishingRod(Materials.uranium);
		uranium_helmet = createHelmet(Materials.uranium);
		uranium_hoe = createHoe(Materials.uranium);
		uranium_horsearmor = createHorseArmor(Materials.uranium);
		uranium_ingot = createIngot(Materials.uranium);
		uranium_leggings = createLeggings(Materials.uranium);
		uranium_nugget = createNugget(Materials.uranium);
		uranium_pickaxe = createPickaxe(Materials.uranium);
		uranium_powder = createPowder(Materials.uranium);
		uranium_shears = createShears(Materials.uranium);
		uranium_shovel = createShovel(Materials.uranium);
		uranium_smallpowder = createSmallPowder(Materials.uranium);
		uranium_sword = createSword(Materials.uranium);
		uranium_rod = createRod(Materials.uranium);
		uranium_gear = createGear(Materials.uranium);

		uranium_slab = createSlab(Materials.uranium, Blocks.uranium_slab, Blocks.uranium_slab, Blocks.double_uranium_slab);
//		uranium_shield = createShield(Materials.uranium);

		uranium_crystal = createCrystal(Materials.uranium);
		uranium_shard = createShard(Materials.uranium);
		uranium_clump = createClump(Materials.uranium);
		uranium_powder_dirty = createDirtyPowder(Materials.uranium);

		uranium_dense_plate = createDensePlate(Materials.uranium);
		uranium_crushed = createCrushed(Materials.uranium);
		uranium_crushed_purified = createCrushedPurified(Materials.uranium);

		zirconium_arrow = createArrow(Materials.zirconium);
		zirconium_axe = createAxe(Materials.zirconium);
		zirconium_boots = createBoots(Materials.zirconium);
		zirconium_bolt = createBolt(Materials.zirconium);
		zirconium_bow = createBow(Materials.zirconium);
		zirconium_chestplate = createChestplate(Materials.zirconium);
		zirconium_crackhammer = createCrackhammer(Materials.zirconium);
		zirconium_crossbow = createCrossbow(Materials.zirconium);
		zirconium_door = createDoor(Materials.zirconium, Blocks.zirconium_door);
		zirconium_fishing_rod = createFishingRod(Materials.zirconium);
		zirconium_helmet = createHelmet(Materials.zirconium);
		zirconium_hoe = createHoe(Materials.zirconium);
		zirconium_horsearmor = createHorseArmor(Materials.zirconium);
		zirconium_ingot = createIngot(Materials.zirconium);
		zirconium_leggings = createLeggings(Materials.zirconium);
		zirconium_nugget = createNugget(Materials.zirconium);
		zirconium_pickaxe = createPickaxe(Materials.zirconium);
		zirconium_powder = createPowder(Materials.zirconium);
		zirconium_shears = createShears(Materials.zirconium);
		zirconium_shovel = createShovel(Materials.zirconium);
		zirconium_smallpowder = createSmallPowder(Materials.zirconium);
		zirconium_sword = createSword(Materials.zirconium);
		zirconium_rod = createRod(Materials.zirconium);
		zirconium_gear = createGear(Materials.zirconium);

		zirconium_slab = createSlab(Materials.zirconium, Blocks.zirconium_slab, Blocks.zirconium_slab, Blocks.double_zirconium_slab);
//		zirconium_shield = createShield(Materials.zirconium);

		zirconium_crystal = createCrystal(Materials.zirconium);
		zirconium_shard = createShard(Materials.zirconium);
		zirconium_clump = createClump(Materials.zirconium);
		zirconium_powder_dirty = createDirtyPowder(Materials.zirconium);

		zirconium_dense_plate = createDensePlate(Materials.zirconium);
		zirconium_crushed = createCrushed(Materials.zirconium);
		zirconium_crushed_purified = createCrushedPurified(Materials.zirconium);

		// TODO: Make this support multiple oredicts
		for(final Item i : itemRegistry.keySet()) {
			allItems.put(itemRegistry.get(i), i);
			if(i instanceof IOreDictionaryEntry)
				OreDictionary.registerOre(((IOreDictionaryEntry)i).getOreDictionaryName(), i);
		}

		int ss = 0;
		classSortingValues.put(BlockMetalOre.class, ++ss * 10000);
		classSortingValues.put(BlockMetalBlock.class, ++ss * 10000);
		classSortingValues.put(BlockMetalPlate.class, ++ss * 10000);
		classSortingValues.put(BlockMetalBars.class, ++ss * 10000);
		classSortingValues.put(BlockMetalDoor.class, ++ss * 10000);
		classSortingValues.put(BlockMetalTrapDoor.class, ++ss * 10000);
		classSortingValues.put(InteractiveFluidBlock.class, ++ss * 10000);
		classSortingValues.put(ItemMetalIngot.class, ++ss * 10000);
		classSortingValues.put(ItemMetalNugget.class, ++ss * 10000);
		classSortingValues.put(ItemMetalPowder.class, ++ss * 10000);
		classSortingValues.put(ItemMetalBlend.class, classSortingValues.get(ItemMetalPowder.class));
		classSortingValues.put(ItemMetalSmallPowder.class, ++ss * 10000);
		classSortingValues.put(ItemMetalSmallBlend.class, classSortingValues.get(ItemMetalSmallPowder.class));
		classSortingValues.put(ItemMetalCrackHammer.class, ++ss * 10000);
		classSortingValues.put(ItemMetalPickaxe.class, ++ss * 10000);
		classSortingValues.put(ItemMetalShovel.class, ++ss * 10000);
		classSortingValues.put(ItemMetalAxe.class, ++ss * 10000);
		classSortingValues.put(ItemMetalHoe.class, ++ss * 10000);
		classSortingValues.put(ItemMetalSword.class, ++ss * 10000);
		classSortingValues.put(ItemMetalArmor.class, ++ss * 10000);
		classSortingValues.put(GenericMetalItem.class, ++ss * 10000);
		classSortingValues.put(ItemMetalArrow.class, ++ss * 10000);
		classSortingValues.put(ItemMetalBolt.class, ++ss * 10000);
		classSortingValues.put(ItemMetalBow.class, ++ss * 10000);
		classSortingValues.put(ItemMetalCrossbow.class, ++ss * 10000);
		classSortingValues.put(ItemMetalFishingRod.class, ++ss * 10000);
		classSortingValues.put(ItemMetalHorseArmor.class, ++ss * 10000);
		classSortingValues.put(ItemMetalShears.class, ++ss * 10000);
		classSortingValues.put(ItemMetalShield.class, ++ss * 10000);
		classSortingValues.put(ItemMetalDoor.class, classSortingValues.get(BlockMetalDoor.class));

		classSortingValues.put(BlockButtonMetal.class, ++ss * 10000);
		classSortingValues.put(BlockMetalSlab.class, ++ss * 10000);
		classSortingValues.put(BlockDoubleMetalSlab.class, ++ss * 10000); // Probably not needed
		classSortingValues.put(BlockHalfMetalSlab.class, ++ss * 10000); // Probably not needed
		classSortingValues.put(BlockMetalLever.class, ++ss * 10000);
		classSortingValues.put(BlockMetalPressurePlate.class, ++ss * 10000);
		classSortingValues.put(BlockMetalStairs.class, ++ss * 10000);
		classSortingValues.put(BlockMetalWall.class, ++ss * 10000);
		classSortingValues.put(BlockMoltenFluid.class, ++ss * 10000);
		classSortingValues.put(ItemMetalSlab.class, ++ss * 10000);

		List<MetalMaterial> metlist = new ArrayList<>(Materials.getAllMetals().size());
		metlist.addAll(Materials.getAllMetals());
		metlist.sort((MetalMaterial a, MetalMaterial b)-> a.getName().compareToIgnoreCase(b.getName()));
		for(int i = 0; i < metlist.size(); i++)
			materialSortingValues.put(metlist.get(i), i * 100);

		initDone = true;
	}

	private static Item registerItem(Item item, String name, MetalMaterial metal, CreativeTabs tab) {
		final ResourceLocation location = new ResourceLocation(ModernMetals.MODID, name);
		item.setRegistryName(location);
		item.setUnlocalizedName(location.toString());
		GameRegistry.register(item);
		itemRegistry.put(item, name);

		if(tab != null) {
			item.setCreativeTab(tab);
		}

		if(metal != null) {
			itemsByMetal.computeIfAbsent(metal, (MetalMaterial g)->new ArrayList<>());
			itemsByMetal.get(metal).add(item);
		}

		return item;
	}

	private static Item createIngot(MetalMaterial metal) {
//		return registerItem(new modernmetals.items.ItemMetalIngot(metal), metal.getName() + "_ingot", metal, ItemGroups.tab_items);
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_ingot", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("ingot" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createNugget(MetalMaterial metal) {
//		return registerItem(new ItemMetalNugget(metal), metal.getName() + "_nugget", metal, ItemGroups.tab_items);
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_nugget", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("nugget" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createPowder(MetalMaterial metal) {
//		return registerItem(new ItemMetalPowder(metal), metal.getName() + "_powder", metal, ItemGroups.tab_items);
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_powder", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("dust" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createBlend(MetalMaterial metal) {
//		return registerItem(new modernmetals.items.ItemMetalBlend(metal), metal.getName() + "_blend", metal, ItemGroups.tab_items);
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_blend", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("dust" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createRod(MetalMaterial metal) {
//		return registerItem(new modernmetals.items.ItemMetalRod(metal), metal.getName() + "_rod", metal, ItemGroups.tab_items);
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_rod", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("rod" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createGear(MetalMaterial metal) {
//		return registerItem(new modernmetals.items.ItemMetalGear(metal), metal.getName() + "_blend", metal, ItemGroups.tab_items);
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_gear", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("gear" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createAxe(MetalMaterial metal) {
		return registerItem(new ItemMetalAxe(metal), metal.getName() + "_axe", metal, ItemGroups.tab_tools);
	}

	private static Item createCrackhammer(MetalMaterial metal) {
		return registerItem(new ItemMetalCrackHammer(metal), metal.getName() + "_crackhammer", metal, ItemGroups.tab_tools);
	}

	private static Item createHoe(MetalMaterial metal) {
		return registerItem(new ItemMetalHoe(metal), metal.getName() + "_hoe", metal, ItemGroups.tab_tools);
	}

	private static Item createPickaxe(MetalMaterial metal) {
		return registerItem(new ItemMetalPickaxe(metal), metal.getName() + "_pickaxe", metal, ItemGroups.tab_tools);
	}

	private static Item createShovel(MetalMaterial metal) {
		return registerItem(new ItemMetalShovel(metal), metal.getName() + "_shovel", metal, ItemGroups.tab_tools);
	}

	private static Item createSword(MetalMaterial metal) {
		return registerItem(new ItemMetalSword(metal), metal.getName() + "_sword", metal, ItemGroups.tab_tools);
	}

	private static Item createHelmet(MetalMaterial metal) {
		return registerItem(ItemMetalArmor.createHelmet(metal), metal.getName() + "_helmet", metal, ItemGroups.tab_tools);
	}

	private static Item createChestplate(MetalMaterial metal) {
		return registerItem(ItemMetalArmor.createChestplate(metal), metal.getName() + "_chestplate", metal, ItemGroups.tab_tools);
	}

	private static Item createLeggings(MetalMaterial metal) {
		return registerItem(ItemMetalArmor.createLeggings(metal), metal.getName() + "_leggings", metal, ItemGroups.tab_tools);
	}

	private static Item createBoots(MetalMaterial metal) {
		return registerItem(ItemMetalArmor.createBoots(metal), metal.getName() + "_boots", metal, ItemGroups.tab_tools);
	}

	private static Item createHorseArmor(MetalMaterial metal) {
		return registerItem(new ItemMetalHorseArmor(metal), metal.getName() + "_horsearmor", metal, ItemGroups.tab_tools);
	}

	private static Item createArrow(MetalMaterial metal) {
		return registerItem(new ItemMetalArrow(metal), metal.getName() + "_arrow", metal, ItemGroups.tab_tools);
	}

	private static Item createBolt(MetalMaterial metal) {
		return registerItem(new ItemMetalBolt(metal), metal.getName() + "_bolt", metal, ItemGroups.tab_tools);
	}

	private static Item createBow(MetalMaterial metal) {
		return registerItem(new ItemMetalBow(metal), metal.getName() + "_bow", metal, ItemGroups.tab_tools);
	}

	private static Item createCrossbow(MetalMaterial metal) {
		return registerItem(new ItemMetalCrossbow(metal), metal.getName() + "_crossbow", metal, ItemGroups.tab_tools);
	}

	private static Item createShears(MetalMaterial metal) {
		return registerItem(new ItemMetalShears(metal), metal.getName() + "_shears", metal, ItemGroups.tab_tools);
	}

	private static Item createSmallBlend(MetalMaterial metal) {
//		return registerItem(new ItemMetalSmallBlend(metal), metal.getName() + "_smallblend", metal, ItemGroups.tab_items);
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_smallblend", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("dustTiny" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createFishingRod(MetalMaterial metal) {
		return registerItem(new ItemMetalFishingRod(metal), metal.getName() + "_fishing_rod", metal, ItemGroups.tab_tools);
	}

	private static Item createSmallPowder(MetalMaterial metal) {
//		return registerItem(new ItemMetalSmallPowder(metal), metal.getName() + "_smallpowder", metal, ItemGroups.tab_items);
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_smallpowder", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("dustTiny" + metal.getCapitalizedName(), i);
		return i;
	}

	@SuppressWarnings("unused")
	private static Item createShield(MetalMaterial metal) {
		return registerItem(new ItemMetalShield(metal), metal.getName() + "_shield", metal, ItemGroups.tab_items);
	}

	private static Item createCrystal(MetalMaterial metal) {
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_crystal", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("crystal" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createShard(MetalMaterial metal) {
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_shard", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("shard" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createClump(MetalMaterial metal) {
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_clump", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("clump" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createDirtyPowder(MetalMaterial metal) {
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_powder_dirty", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("dustDirty" + metal.getCapitalizedName(), i);
		return i;
	}

	// TODO: Possibly make this a block, 1/2 of the normal plate.
	private static Item createDensePlate(MetalMaterial metal) {
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_dense_plate", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("plateDense" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createCrushed(MetalMaterial metal) {
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_crushed", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("crushed" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createCrushedPurified(MetalMaterial metal) {
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_crushed_purified", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("crushedPurified" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createSlab(MetalMaterial metal, Block block, BlockSlab slab, BlockSlab doubleslab) {
		final ResourceLocation location = new ResourceLocation(ModernMetals.MODID, metal.getName() + "_slab");
		final Item item = new ItemMetalSlab(metal, block, slab, doubleslab);
		registerItem(item, location.getResourcePath(), metal, ItemGroups.tab_blocks);
		//item.setUnlocalizedName(location.toString()); // Hack to set name right
		return item;
	}

	private static Item createDoor(MetalMaterial metal, BlockDoor door) {
		final ResourceLocation location = new ResourceLocation(ModernMetals.MODID, metal.getName() + "_door");
		final Item item = new ItemMetalDoor(door, metal);
		registerItem(item, location.getResourcePath() + "_item", metal, ItemGroups.tab_blocks);
		item.setUnlocalizedName(location.toString()); // Hack to set name right
		doorMap.put(door, item);
		return item;
	}

	/**
	 * 
	 * @param a
	 * @return
	 */
	public static int getSortingValue(ItemStack a) {
		int classVal = 990000;
		int metalVal = 9900;
		if(a.getItem() instanceof ItemBlock && ((ItemBlock)a.getItem()).getBlock() instanceof IMetalObject) {
			classVal = classSortingValues.computeIfAbsent(((ItemBlock)a.getItem()).getBlock().getClass(),
					(Class<?> c)->990000);
			metalVal = materialSortingValues.computeIfAbsent(((IMetalObject)((ItemBlock)a.getItem()).getBlock()).getMetalMaterial(),
					(MetalMaterial m)->9900);
		} else if(a.getItem() instanceof IMetalObject) {
			classVal = classSortingValues.computeIfAbsent(a.getItem().getClass(),
					(Class<?> c)->990000);
			metalVal = materialSortingValues.computeIfAbsent(((IMetalObject)a.getItem()).getMetalMaterial(),
					(MetalMaterial m)->9900);
		}
		return classVal + metalVal + (a.getMetadata() % 100);
	}

	/**
	 * 
	 * @param event
	 */
	@SideOnly(Side.CLIENT)
	public static void registerItemRenders(FMLInitializationEvent event) {
		for(final Item i : itemRegistry.keySet()) {
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
			.register(i, 0, 
				new ModelResourceLocation(new ResourceLocation(ModernMetals.MODID, itemRegistry.get(i)), "inventory"));
		}
	}

	public static Map<Item, String> getItemRegistry() {
		return itemRegistry;
	}
}

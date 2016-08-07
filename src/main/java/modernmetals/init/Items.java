package modernmetals.init;

import java.util.*;

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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

/**
 * This class initializes all items in Modern Metals and provides some utility
 * methods for looking up items.
 *
 * @author DrCyano
 *
 */
public abstract class Items {

	protected static Item aluminum_arrow;
	protected static Item aluminum_axe;
	protected static Item aluminum_boots;
	protected static Item aluminum_bolt;
	protected static Item aluminum_bow;
	protected static Item aluminum_chestplate;
	protected static Item aluminum_crackhammer;
	protected static Item aluminum_crossbow;
	protected static Item aluminum_door;
	protected static Item aluminum_fishing_rod;
	protected static Item aluminum_helmet;
	protected static Item aluminum_hoe;
	protected static Item aluminum_horsearmor;
	protected static Item aluminum_ingot;
	protected static Item aluminum_leggings;
	protected static Item aluminum_nugget;
	protected static Item aluminum_pickaxe;
	protected static Item aluminum_powder;
	protected static Item aluminum_shears;
	protected static Item aluminum_shovel;
	protected static Item aluminum_smallpowder;
	protected static Item aluminum_sword;
	protected static Item aluminum_rod;
	protected static Item aluminum_gear;

	protected static Item aluminum_slab;
	//protected static Item aluminum_shield;

	protected static Item aluminum_crystal;
	protected static Item aluminum_shard;
	protected static Item aluminum_clump;
	protected static Item aluminum_powder_dirty;

	protected static Item aluminum_dense_plate;
	protected static Item aluminum_crushed;
	protected static Item aluminum_crushed_purified;

	protected static Item aluminumbrass_arrow;
	protected static Item aluminumbrass_axe;
	protected static Item aluminumbrass_blend;
	protected static Item aluminumbrass_boots;
	protected static Item aluminumbrass_bolt;
	protected static Item aluminumbrass_bow;
	protected static Item aluminumbrass_chestplate;
	protected static Item aluminumbrass_crackhammer;
	protected static Item aluminumbrass_crossbow;
	protected static Item aluminumbrass_door;
	protected static Item aluminumbrass_fishing_rod;
	protected static Item aluminumbrass_helmet;
	protected static Item aluminumbrass_hoe;
	protected static Item aluminumbrass_horsearmor;
	protected static Item aluminumbrass_ingot;
	protected static Item aluminumbrass_leggings;
	protected static Item aluminumbrass_nugget;
	protected static Item aluminumbrass_pickaxe;
	protected static Item aluminumbrass_powder;
	protected static Item aluminumbrass_shears;
	protected static Item aluminumbrass_shovel;
	protected static Item aluminumbrass_smallblend;
	protected static Item aluminumbrass_smallpowder;
	protected static Item aluminumbrass_sword;
	protected static Item aluminumbrass_rod;
	protected static Item aluminumbrass_gear;

	protected static Item aluminumbrass_slab;
	//protected static Item aluminumbrass_shield;

	//protected static Item aluminumbrass_crystal;
	//protected static Item aluminumbrass_shard;
	//protected static Item aluminumbrass_clump;
	//protected static Item aluminumbrass_powder_dirty;

	protected static Item aluminumbrass_dense_plate;
	//protected static Item aluminumbrass_crushed;
	//protected static Item aluminumbrass_crushed_purified;

	protected static Item cadmium_arrow;
	protected static Item cadmium_axe;
	protected static Item cadmium_boots;
	protected static Item cadmium_bolt;
	protected static Item cadmium_bow;
	protected static Item cadmium_chestplate;
	protected static Item cadmium_crackhammer;
	protected static Item cadmium_crossbow;
	protected static Item cadmium_door;
	protected static Item cadmium_fishing_rod;
	protected static Item cadmium_helmet;
	protected static Item cadmium_hoe;
	protected static Item cadmium_horsearmor;
	protected static Item cadmium_ingot;
	protected static Item cadmium_leggings;
	protected static Item cadmium_nugget;
	protected static Item cadmium_pickaxe;
	protected static Item cadmium_powder;
	protected static Item cadmium_shears;
	protected static Item cadmium_shovel;
	protected static Item cadmium_smallpowder;
	protected static Item cadmium_sword;
	protected static Item cadmium_rod;
	protected static Item cadmium_gear;

	protected static Item cadmium_slab;
	//protected static Item cadmium_shield;

	protected static Item cadmium_crystal;
	protected static Item cadmium_shard;
	protected static Item cadmium_clump;
	protected static Item cadmium_powder_dirty;

	protected static Item cadmium_dense_plate;
	protected static Item cadmium_crushed;
	protected static Item cadmium_crushed_purified;

	protected static Item chromium_arrow;
	protected static Item chromium_axe;
	protected static Item chromium_boots;
	protected static Item chromium_bolt;
	protected static Item chromium_bow;
	protected static Item chromium_chestplate;
	protected static Item chromium_crackhammer;
	protected static Item chromium_crossbow;
	protected static Item chromium_door;
	protected static Item chromium_fishing_rod;
	protected static Item chromium_helmet;
	protected static Item chromium_hoe;
	protected static Item chromium_horsearmor;
	protected static Item chromium_ingot;
	protected static Item chromium_leggings;
	protected static Item chromium_nugget;
	protected static Item chromium_pickaxe;
	protected static Item chromium_powder;
	protected static Item chromium_shears;
	protected static Item chromium_shovel;
	protected static Item chromium_smallpowder;
	protected static Item chromium_sword;
	protected static Item chromium_rod;
	protected static Item chromium_gear;

	protected static Item chromium_slab;
	//protected static Item chromium_shield;

	protected static Item chromium_crystal;
	protected static Item chromium_shard;
	protected static Item chromium_clump;
	protected static Item chromium_powder_dirty;

	protected static Item chromium_dense_plate;
	protected static Item chromium_crushed;
	protected static Item chromium_crushed_purified;

	protected static Item galvanizedsteel_arrow;
	protected static Item galvanizedsteel_axe;
	protected static Item galvanizedsteel_blend;
	protected static Item galvanizedsteel_boots;
	protected static Item galvanizedsteel_bolt;
	protected static Item galvanizedsteel_bow;
	protected static Item galvanizedsteel_chestplate;
	protected static Item galvanizedsteel_crackhammer;
	protected static Item galvanizedsteel_crossbow;
	protected static Item galvanizedsteel_door;
	protected static Item galvanizedsteel_fishing_rod;
	protected static Item galvanizedsteel_helmet;
	protected static Item galvanizedsteel_hoe;
	protected static Item galvanizedsteel_horsearmor;
	protected static Item galvanizedsteel_ingot;
	protected static Item galvanizedsteel_leggings;
	protected static Item galvanizedsteel_nugget;
	protected static Item galvanizedsteel_pickaxe;
	protected static Item galvanizedsteel_powder;
	protected static Item galvanizedsteel_shears;
	protected static Item galvanizedsteel_shovel;
	protected static Item galvanizedsteel_smallblend;
	protected static Item galvanizedsteel_smallpowder;
	protected static Item galvanizedsteel_sword;
	protected static Item galvanizedsteel_rod;
	protected static Item galvanizedsteel_gear;

	protected static Item galvanizedsteel_slab;
	//protected static Item galvanizedsteel_shield;

	//protected static Item galvanizedsteel_crystal;
	//protected static Item galvanizedsteel_shard;
	//protected static Item galvanizedsteel_clump;
	//protected static Item galvanizedsteel_powder_dirty;

	protected static Item galvanizedsteel_dense_plate;
	//protected static Item galvanizedsteel_crushed;
	//protected static Item galvanizedsteel_crushed_purified;

	protected static Item iridium_arrow;
	protected static Item iridium_axe;
	protected static Item iridium_boots;
	protected static Item iridium_bolt;
	protected static Item iridium_bow;
	protected static Item iridium_chestplate;
	protected static Item iridium_crackhammer;
	protected static Item iridium_crossbow;
	protected static Item iridium_door;
	protected static Item iridium_fishing_rod;
	protected static Item iridium_helmet;
	protected static Item iridium_hoe;
	protected static Item iridium_horsearmor;
	protected static Item iridium_ingot;
	protected static Item iridium_leggings;
	protected static Item iridium_nugget;
	protected static Item iridium_pickaxe;
	protected static Item iridium_powder;
	protected static Item iridium_shears;
	protected static Item iridium_shovel;
	protected static Item iridium_smallpowder;
	protected static Item iridium_sword;
	protected static Item iridium_rod;
	protected static Item iridium_gear;

	protected static Item iridium_slab;
	//protected static Item iridium_shield;

	protected static Item iridium_crystal;
	protected static Item iridium_shard;
	protected static Item iridium_clump;
	protected static Item iridium_powder_dirty;

	protected static Item iridium_dense_plate;
	protected static Item iridium_crushed;
	protected static Item iridium_crushed_purified;

	protected static Item magnesium_arrow;
	protected static Item magnesium_axe;
	protected static Item magnesium_boots;
	protected static Item magnesium_bolt;
	protected static Item magnesium_bow;
	protected static Item magnesium_chestplate;
	protected static Item magnesium_crackhammer;
	protected static Item magnesium_crossbow;
	protected static Item magnesium_door;
	protected static Item magnesium_fishing_rod;
	protected static Item magnesium_helmet;
	protected static Item magnesium_hoe;
	protected static Item magnesium_horsearmor;
	protected static Item magnesium_ingot;
	protected static Item magnesium_leggings;
	protected static Item magnesium_nugget;
	protected static Item magnesium_pickaxe;
	protected static Item magnesium_powder;
	protected static Item magnesium_shears;
	protected static Item magnesium_shovel;
	protected static Item magnesium_smallpowder;
	protected static Item magnesium_sword;
	protected static Item magnesium_rod;
	protected static Item magnesium_gear;

	protected static Item magnesium_slab;
	//protected static Item magnesium_shield;

	protected static Item magnesium_crystal;
	protected static Item magnesium_shard;
	protected static Item magnesium_clump;
	protected static Item magnesium_powder_dirty;

	protected static Item magnesium_dense_plate;
	protected static Item magnesium_crushed;
	protected static Item magnesium_crushed_purified;

	protected static Item manganese_arrow;
	protected static Item manganese_axe;
	protected static Item manganese_boots;
	protected static Item manganese_bolt;
	protected static Item manganese_bow;
	protected static Item manganese_chestplate;
	protected static Item manganese_crackhammer;
	protected static Item manganese_crossbow;
	protected static Item manganese_door;
	protected static Item manganese_fishing_rod;
	protected static Item manganese_helmet;
	protected static Item manganese_hoe;
	protected static Item manganese_horsearmor;
	protected static Item manganese_ingot;
	protected static Item manganese_leggings;
	protected static Item manganese_nugget;
	protected static Item manganese_pickaxe;
	protected static Item manganese_powder;
	protected static Item manganese_shears;
	protected static Item manganese_shovel;
	protected static Item manganese_smallpowder;
	protected static Item manganese_sword;
	protected static Item manganese_rod;
	protected static Item manganese_gear;

	protected static Item manganese_slab;
	//protected static Item manganese_shield;

	protected static Item manganese_crystal;
	protected static Item manganese_shard;
	protected static Item manganese_clump;
	protected static Item manganese_powder_dirty;

	protected static Item manganese_dense_plate;
	protected static Item manganese_crushed;
	protected static Item manganese_crushed_purified;

	protected static Item nichrome_arrow;
	protected static Item nichrome_axe;
	protected static Item nichrome_blend;
	protected static Item nichrome_boots;
	protected static Item nichrome_bolt;
	protected static Item nichrome_bow;
	protected static Item nichrome_chestplate;
	protected static Item nichrome_crackhammer;
	protected static Item nichrome_crossbow;
	protected static Item nichrome_door;
	protected static Item nichrome_fishing_rod;
	protected static Item nichrome_helmet;
	protected static Item nichrome_hoe;
	protected static Item nichrome_horsearmor;
	protected static Item nichrome_ingot;
	protected static Item nichrome_leggings;
	protected static Item nichrome_nugget;
	protected static Item nichrome_pickaxe;
	protected static Item nichrome_powder;
	protected static Item nichrome_shears;
	protected static Item nichrome_shovel;
	protected static Item nichrome_smallblend;
	protected static Item nichrome_smallpowder;
	protected static Item nichrome_sword;
	protected static Item nichrome_rod;
	protected static Item nichrome_gear;

	protected static Item nichrome_slab;
	//protected static Item nichrome_shield;

	//protected static Item nichrome_crystal;
	//protected static Item nichrome_shard;
	//protected static Item nichrome_clump;
	//protected static Item nichrome_powder_dirty;

	protected static Item nichrome_dense_plate;
	//protected static Item nichrome_crushed;
	//protected static Item nichrome_crushed_purified;

	protected static Item osmium_arrow;
	protected static Item osmium_axe;
	protected static Item osmium_boots;
	protected static Item osmium_bolt;
	protected static Item osmium_bow;
	protected static Item osmium_chestplate;
	protected static Item osmium_crackhammer;
	protected static Item osmium_crossbow;
	protected static Item osmium_door;
	protected static Item osmium_fishing_rod;
	protected static Item osmium_helmet;
	protected static Item osmium_hoe;
	protected static Item osmium_horsearmor;
	protected static Item osmium_ingot;
	protected static Item osmium_leggings;
	protected static Item osmium_nugget;
	protected static Item osmium_pickaxe;
	protected static Item osmium_powder;
	protected static Item osmium_shears;
	protected static Item osmium_shovel;
	protected static Item osmium_smallpowder;
	protected static Item osmium_sword;
	protected static Item osmium_rod;
	protected static Item osmium_gear;

	protected static Item osmium_slab;
	//protected static Item osmium_shield;

	//protected static Item osmium_crystal;
	//protected static Item osmium_shard;
	//protected static Item osmium_clump;
	//protected static Item osmium_powder_dirty;

	protected static Item osmium_dense_plate;
	protected static Item osmium_crushed;
	protected static Item osmium_crushed_purified;

	protected static Item plutonium_arrow;
	protected static Item plutonium_axe;
	protected static Item plutonium_boots;
	protected static Item plutonium_bolt;
	protected static Item plutonium_bow;
	protected static Item plutonium_chestplate;
	protected static Item plutonium_crackhammer;
	protected static Item plutonium_crossbow;
	protected static Item plutonium_door;
	protected static Item plutonium_fishing_rod;
	protected static Item plutonium_helmet;
	protected static Item plutonium_hoe;
	protected static Item plutonium_horsearmor;
	protected static Item plutonium_ingot;
	protected static Item plutonium_leggings;
	protected static Item plutonium_nugget;
	protected static Item plutonium_pickaxe;
	protected static Item plutonium_powder;
	protected static Item plutonium_shears;
	protected static Item plutonium_shovel;
	protected static Item plutonium_smallpowder;
	protected static Item plutonium_sword;
	protected static Item plutonium_rod;
	protected static Item plutonium_gear;

	protected static Item plutonium_slab;
	//protected static Item plutonium_shield;

	protected static Item plutonium_crystal;
	protected static Item plutonium_shard;
	protected static Item plutonium_clump;
	protected static Item plutonium_powder_dirty;

	protected static Item plutonium_dense_plate;
	protected static Item plutonium_crushed;
	protected static Item plutonium_crushed_purified;

	protected static Item rutile_arrow;
	protected static Item rutile_axe;
	protected static Item rutile_boots;
	protected static Item rutile_bolt;
	protected static Item rutile_bow;
	protected static Item rutile_chestplate;
	protected static Item rutile_crackhammer;
	protected static Item rutile_crossbow;
	protected static Item rutile_door;
	protected static Item rutile_fishing_rod;
	protected static Item rutile_helmet;
	protected static Item rutile_hoe;
	protected static Item rutile_horsearmor;
	protected static Item rutile_ingot;
	protected static Item rutile_leggings;
	protected static Item rutile_nugget;
	protected static Item rutile_pickaxe;
	protected static Item rutile_powder;
	protected static Item rutile_shears;
	protected static Item rutile_shovel;
	protected static Item rutile_smallpowder;
	protected static Item rutile_sword;
	protected static Item rutile_rod;
	protected static Item rutile_gear;

	protected static Item rutile_slab;
	//protected static Item rutile_shield;

	protected static Item rutile_crystal;
	protected static Item rutile_shard;
	protected static Item rutile_clump;
	protected static Item rutile_powder_dirty;

	protected static Item rutile_dense_plate;
	protected static Item rutile_crushed;
	protected static Item rutile_crushed_purified;

	protected static Item stainlesssteel_arrow;
	protected static Item stainlesssteel_axe;
	protected static Item stainlesssteel_blend;
	protected static Item stainlesssteel_boots;
	protected static Item stainlesssteel_bolt;
	protected static Item stainlesssteel_bow;
	protected static Item stainlesssteel_chestplate;
	protected static Item stainlesssteel_crackhammer;
	protected static Item stainlesssteel_crossbow;
	protected static Item stainlesssteel_door;
	protected static Item stainlesssteel_fishing_rod;
	protected static Item stainlesssteel_helmet;
	protected static Item stainlesssteel_hoe;
	protected static Item stainlesssteel_horsearmor;
	protected static Item stainlesssteel_ingot;
	protected static Item stainlesssteel_leggings;
	protected static Item stainlesssteel_nugget;
	protected static Item stainlesssteel_pickaxe;
	protected static Item stainlesssteel_powder;
	protected static Item stainlesssteel_shears;
	protected static Item stainlesssteel_shovel;
	protected static Item stainlesssteel_smallblend;
	protected static Item stainlesssteel_smallpowder;
	protected static Item stainlesssteel_sword;
	protected static Item stainlesssteel_rod;
	protected static Item stainlesssteel_gear;

	protected static Item stainlesssteel_slab;
	//protected static Item stainlesssteel_shield;

	//protected static Item stainlesssteel_crystal;
	//protected static Item stainlesssteel_shard;
	//protected static Item stainlesssteel_clump;
	//protected static Item stainlesssteel_powder_dirty;

	protected static Item stainlesssteel_dense_plate;
	//protected static Item stainlesssteel_crushed;
	//protected static Item stainlesssteel_crushed_purified;

	protected static Item tantalum_arrow;
	protected static Item tantalum_axe;
	protected static Item tantalum_boots;
	protected static Item tantalum_bolt;
	protected static Item tantalum_bow;
	protected static Item tantalum_chestplate;
	protected static Item tantalum_crackhammer;
	protected static Item tantalum_crossbow;
	protected static Item tantalum_door;
	protected static Item tantalum_fishing_rod;
	protected static Item tantalum_helmet;
	protected static Item tantalum_hoe;
	protected static Item tantalum_horsearmor;
	protected static Item tantalum_ingot;
	protected static Item tantalum_leggings;
	protected static Item tantalum_nugget;
	protected static Item tantalum_pickaxe;
	protected static Item tantalum_powder;
	protected static Item tantalum_shears;
	protected static Item tantalum_shovel;
	protected static Item tantalum_smallpowder;
	protected static Item tantalum_sword;
	protected static Item tantalum_rod;
	protected static Item tantalum_gear;

	protected static Item tantalum_slab;
	//protected static Item tantalum_shield;

	protected static Item tantalum_crystal;
	protected static Item tantalum_shard;
	protected static Item tantalum_clump;
	protected static Item tantalum_powder_dirty;

	protected static Item tantalum_dense_plate;
	protected static Item tantalum_crushed;
	protected static Item tantalum_crushed_purified;

	protected static Item titanium_arrow;
	protected static Item titanium_axe;
	protected static Item titanium_blend;
	protected static Item titanium_boots;
	protected static Item titanium_bolt;
	protected static Item titanium_bow;
	protected static Item titanium_chestplate;
	protected static Item titanium_crackhammer;
	protected static Item titanium_crossbow;
	protected static Item titanium_door;
	protected static Item titanium_fishing_rod;
	protected static Item titanium_helmet;
	protected static Item titanium_hoe;
	protected static Item titanium_horsearmor;
	protected static Item titanium_ingot;
	protected static Item titanium_leggings;
	protected static Item titanium_nugget;
	protected static Item titanium_pickaxe;
	protected static Item titanium_powder;
	protected static Item titanium_shears;
	protected static Item titanium_shovel;
	protected static Item titanium_smallblend;
	protected static Item titanium_smallpowder;
	protected static Item titanium_sword;
	protected static Item titanium_rod;
	protected static Item titanium_gear;

	protected static Item titanium_slab;
	//protected static Item titanium_shield;

	protected static Item titanium_crystal;
	protected static Item titanium_shard;
	protected static Item titanium_clump;
	protected static Item titanium_powder_dirty;

	protected static Item titanium_dense_plate;
	protected static Item titanium_crushed;
	protected static Item titanium_crushed_purified;

	protected static Item tungsten_arrow;
	protected static Item tungsten_axe;
	protected static Item tungsten_boots;
	protected static Item tungsten_bolt;
	protected static Item tungsten_bow;
	protected static Item tungsten_chestplate;
	protected static Item tungsten_crackhammer;
	protected static Item tungsten_crossbow;
	protected static Item tungsten_door;
	protected static Item tungsten_fishing_rod;
	protected static Item tungsten_helmet;
	protected static Item tungsten_hoe;
	protected static Item tungsten_horsearmor;
	protected static Item tungsten_ingot;
	protected static Item tungsten_leggings;
	protected static Item tungsten_nugget;
	protected static Item tungsten_pickaxe;
	protected static Item tungsten_powder;
	protected static Item tungsten_shears;
	protected static Item tungsten_shovel;
	protected static Item tungsten_smallpowder;
	protected static Item tungsten_sword;
	protected static Item tungsten_rod;
	protected static Item tungsten_gear;

	protected static Item tungsten_slab;
	//protected static Item tungsten_shield;

	protected static Item tungsten_crystal;
	protected static Item tungsten_shard;
	protected static Item tungsten_clump;
	protected static Item tungsten_powder_dirty;

	protected static Item tungsten_dense_plate;
	protected static Item tungsten_crushed;
	protected static Item tungsten_crushed_purified;

	protected static Item uranium_arrow;
	protected static Item uranium_axe;
	protected static Item uranium_boots;
	protected static Item uranium_bolt;
	protected static Item uranium_bow;
	protected static Item uranium_chestplate;
	protected static Item uranium_crackhammer;
	protected static Item uranium_crossbow;
	protected static Item uranium_door;
	protected static Item uranium_fishing_rod;
	protected static Item uranium_helmet;
	protected static Item uranium_hoe;
	protected static Item uranium_horsearmor;
	protected static Item uranium_ingot;
	protected static Item uranium_leggings;
	protected static Item uranium_nugget;
	protected static Item uranium_pickaxe;
	protected static Item uranium_powder;
	protected static Item uranium_shears;
	protected static Item uranium_shovel;
	protected static Item uranium_smallpowder;
	protected static Item uranium_sword;
	protected static Item uranium_rod;
	protected static Item uranium_gear;

	protected static Item uranium_slab;
	//protected static Item uranium_shield;

	protected static Item uranium_crystal;
	protected static Item uranium_shard;
	protected static Item uranium_clump;
	protected static Item uranium_powder_dirty;

	protected static Item uranium_dense_plate;
	protected static Item uranium_crushed;
	protected static Item uranium_crushed_purified;

	protected static Item zirconium_arrow;
	protected static Item zirconium_axe;
	protected static Item zirconium_boots;
	protected static Item zirconium_bolt;
	protected static Item zirconium_bow;
	protected static Item zirconium_chestplate;
	protected static Item zirconium_crackhammer;
	protected static Item zirconium_crossbow;
	protected static Item zirconium_door;
	protected static Item zirconium_fishing_rod;
	protected static Item zirconium_helmet;
	protected static Item zirconium_hoe;
	protected static Item zirconium_horsearmor;
	protected static Item zirconium_ingot;
	protected static Item zirconium_leggings;
	protected static Item zirconium_nugget;
	protected static Item zirconium_pickaxe;
	protected static Item zirconium_powder;
	protected static Item zirconium_shears;
	protected static Item zirconium_shovel;
	protected static Item zirconium_smallpowder;
	protected static Item zirconium_sword;
	protected static Item zirconium_rod;
	protected static Item zirconium_gear;

	protected static Item zirconium_slab;
	//protected static Item zirconium_shield;

	protected static Item zirconium_crystal;
	protected static Item zirconium_shard;
	protected static Item zirconium_clump;
	protected static Item zirconium_powder_dirty;

	protected static Item zirconium_dense_plate;
	protected static Item zirconium_crushed;
	protected static Item zirconium_crushed_purified;

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
	 *
	 * @param name The name of the item in question
	 * @return The item matching that name, or null if there isn't one
	 */
	public static Item getItemByName(String name) {
		return allItems.get(name);
	}

	/**
	 * This is the reverse of the getItemByName(...) method, returning the
	 * registered name of an item instance (Modern Metals items only).
	 *
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
		aluminum_horsearmor = createHorsearmor(Materials.aluminum);
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
		//aluminum_shield = createShield(Materials.aluminum);

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
		aluminumbrass_horsearmor = createHorsearmor(Materials.aluminumbrass);
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
		//aluminumbrass_shield = createShield(Materials.aluminumbrass);

		//aluminumbrass_crystal = createCrystal(Materials.aluminumbrass);
		//aluminumbrass_shard = createShard(Materials.aluminumbrass);
		//aluminumbrass_clump = createClump(Materials.aluminumbrass);
		//aluminumbrass_powder_dirty = createDirtyPowder(Materials.aluminumbrass);

		aluminumbrass_dense_plate = createDensePlate(Materials.aluminumbrass);
		//aluminumbrass_crushed = createCrushed(Materials.aluminumbrass);
		//aluminumbrass_crushed_purified = createCrushedPurified(Materials.aluminumbrass);

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
		cadmium_horsearmor = createHorsearmor(Materials.cadmium);
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
		//cadmium_shield = createShield(Materials.cadmium);

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
		chromium_horsearmor = createHorsearmor(Materials.chromium);
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
		//chromium_shield = createShield(Materials.chromium);

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
		galvanizedsteel_horsearmor = createHorsearmor(Materials.galvanizedsteel);
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
		//galvanizedsteel_shield = createShield(Materials.galvanizedsteel);

		//galvanizedsteel_crystal = createCrystal(Materials.galvanizedsteel);
		//galvanizedsteel_shard = createShard(Materials.galvanizedsteel);
		//galvanizedsteel_clump = createClump(Materials.galvanizedsteel);
		//galvanizedsteel_powder_dirty = createDirtyPowder(Materials.galvanizedsteel);

		//galvanizedsteel_dense_plate = createDensePlate(Materials.galvanizedsteel);
		//galvanizedsteel_crushed = createCrushed(Materials.galvanizedsteel);
		//galvanizedsteel_crushed_purified = createCrushedPurified(Materials.galvanizedsteel);

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
		iridium_horsearmor = createHorsearmor(Materials.iridium);
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
		//iridium_shield = createShield(Materials.iridium);

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
		magnesium_horsearmor = createHorsearmor(Materials.magnesium);
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
		//magnesium_shield = createShield(Materials.magnesium);

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
		manganese_horsearmor = createHorsearmor(Materials.manganese);
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
		//manganese_shield = createShield(Materials.manganese);

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
		nichrome_horsearmor = createHorsearmor(Materials.nichrome);
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
		//nichrome_shield = createShield(Materials.nichrome);

		//nichrome_crystal = createCrystal(Materials.nichrome);
		//nichrome_shard = createShard(Materials.nichrome);
		//nichrome_clump = createClump(Materials.nichrome);
		//nichrome_powder_dirty = createDirtyPowder(Materials.nichrome);

		nichrome_dense_plate = createDensePlate(Materials.nichrome);
		//nichrome_crushed = createCrushed(Materials.nichrome);
		//nichrome_crushed_purified = createCrushedPurified(Materials.nichrome);

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
		osmium_horsearmor = createHorsearmor(Materials.osmium);
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
		//osmium_shield = createShield(Materials.osmium);

		//osmium_crystal = createCrystal(Materials.osmium);
		//osmium_shard = createShard(Materials.osmium);
		//osmium_clump = createClump(Materials.osmium);
		//osmium_powder_dirty = createDirtyPowder(Materials.osmium);

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
		plutonium_horsearmor = createHorsearmor(Materials.plutonium);
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
		//plutonium_shield = createShield(Materials.plutonium);

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
		rutile_horsearmor = createHorsearmor(Materials.rutile);
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
		//rutile_shield = createShield(Materials.rutile);

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
		stainlesssteel_horsearmor = createHorsearmor(Materials.stainlesssteel);
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
		//stainlesssteel_shield = createShield(Materials.stainlesssteel);

		//stainlesssteel_crystal = createCrystal(Materials.stainlesssteel);
		//stainlesssteel_shard = createShard(Materials.stainlesssteel);
		//stainlesssteel_clump = createClump(Materials.stainlesssteel);
		//stainlesssteel_powder_dirty = createDirtyPowder(Materials.stainlesssteel);

		stainlesssteel_dense_plate = createDensePlate(Materials.stainlesssteel);
		//stainlesssteel_crushed = createCrushed(Materials.stainlesssteel);
		//stainlesssteel_crushed_purified = createCrushedPurified(Materials.stainlesssteel);

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
		tantalum_horsearmor = createHorsearmor(Materials.tantalum);
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
		//tantalum_shield = createShield(Materials.tantalum);

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
		titanium_horsearmor = createHorsearmor(Materials.titanium);
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
		//titanium_shield = createShield(Materials.titanium);

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
		tungsten_horsearmor = createHorsearmor(Materials.tungsten);
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
		//tungsten_shield = createShield(Materials.tungsten);

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
		uranium_horsearmor = createHorsearmor(Materials.uranium);
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
		//uranium_shield = createShield(Materials.uranium);

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
		zirconium_horsearmor = createHorsearmor(Materials.zirconium);
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
		//zirconium_shield = createShield(Materials.zirconium);

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
		//return registerItem(new modernmetals.items.ItemMetalIngot(metal), metal.getName() + "_ingot", metal, ItemGroups.tab_items);
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_ingot", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("ingot" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createNugget(MetalMaterial metal) {
		//return registerItem(new ItemMetalNugget(metal), metal.getName() + "_nugget", metal, ItemGroups.tab_items);
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_nugget", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("nugget" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createPowder(MetalMaterial metal) {
		//return registerItem(new ItemMetalPowder(metal), metal.getName() + "_powder", metal, ItemGroups.tab_items);
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_powder", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("dust" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createBlend(MetalMaterial metal) {
		//return registerItem(new modernmetals.items.ItemMetalBlend(metal), metal.getName() + "_blend", metal, ItemGroups.tab_items);
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_blend", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("dust" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createRod(MetalMaterial metal) {
		//return registerItem(new modernmetals.items.ItemMetalRod(metal), metal.getName() + "_rod", metal, ItemGroups.tab_items);
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_rod", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("rod" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createGear(MetalMaterial metal) {
		//return registerItem(new modernmetals.items.ItemMetalGear(metal), metal.getName() + "_blend", metal, ItemGroups.tab_items);
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

	private static Item createHorsearmor(MetalMaterial metal) {
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
		//return registerItem(new ItemMetalSmallBlend(metal), metal.getName() + "_smallblend", metal, ItemGroups.tab_items);
		final Item i = registerItem(new GenericMetalItem(metal), metal.getName() + "_smallblend", metal, ItemGroups.tab_items);
		OreDictionary.registerOre("dustTiny" + metal.getCapitalizedName(), i);
		return i;
	}

	private static Item createFishingRod(MetalMaterial metal) {
		return registerItem(new ItemMetalFishingRod(metal), metal.getName() + "_fishing_rod", metal, ItemGroups.tab_tools);
	}

	private static Item createSmallPowder(MetalMaterial metal) {
		//return registerItem(new ItemMetalSmallPowder(metal), metal.getName() + "_smallpowder", metal, ItemGroups.tab_items);
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

	public static Map<Item, String> getItemRegistry() {
		return itemRegistry;
	}
}

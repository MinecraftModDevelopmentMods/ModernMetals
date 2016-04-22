package modernmetals.init;

import modernmetals.ModernMetals;
import cyano.basemetals.blocks.*;
import cyano.basemetals.items.*;
//import modernmetals.blocks.*;
import modernmetals.items.*;
import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
//import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
//import modernmetals.init.ItemGroups;
//import modernmetals.init.Blocks;
//import modernmetals.init.Materials;

import java.util.*;

/**
 * This class initializes all items in Modern Metals and provides some utility 
 * methods for looking up items. 
 * @author DrCyano
 *
 */
public abstract class Items {

	private static Map<Item,String> itemRegistry = new HashMap<>();
	private static Map<String,Item> allItems = new HashMap<>();
	private static Map<MetalMaterial,List<Item>> itemsByMetal = new HashMap<>();
	
	private static Map<BlockDoor,Item> doorMap = new HashMap<>();
	
	

	private static Map<Class,Integer> classSortingValues = new HashMap<>();
	private static Map<MetalMaterial,Integer> materialSortingValues = new HashMap<>();
	/**
	 * Gets an item by its name. The name is the name as it is registered in 
	 * the GameRegistry, not its unlocalized name (the unlocalized name is the 
	 * registered name plus the prefix "modernmetals.")
	 * @param name The name of the item in question
	 * @return The item matching that name, or null if there isn't one
	 */
	public static Item getItemByName(String name){
		return allItems.get(name);
	}
	/**
	 * This is the reverse of the getItemByName(...) method, returning the 
	 * registered name of an item instance (Modern Metals items only).
	 * @param i The item in question
	 * @return The name of the item, or null if the item is not a Modern Metals 
	 * item.
	 */
	public static String getNameOfItem(Item i){
		return itemRegistry.get(i);
	}
	/**
	 * Gets a map of all items added, sorted by metal
	 * @return An unmodifiable map of added items catagorized by metal material
	 */
	public static Map<MetalMaterial,List<Item>> getItemsByMetal(){
		return Collections.unmodifiableMap(itemsByMetal);
	}

//	public static UniversalBucket universal_bucket;
	
	public static Item aluminum_axe;
//	public static Item aluminum_blend;
	public static Item aluminum_boots;
	public static Item aluminum_chestplate;
	public static Item aluminum_crackhammer;
	public static Item aluminum_door;
	public static Item aluminum_helmet;
	public static Item aluminum_hoe;
	public static Item aluminum_ingot;
	public static Item aluminum_leggings;
	public static Item aluminum_nugget;
	public static Item aluminum_pickaxe;
	public static Item aluminum_powder;
	public static Item aluminum_shovel;
	public static Item aluminum_sword;
	public static Item aluminum_rod;
	public static Item aluminum_gear;

	public static Item aluminum_arrow;
	public static Item aluminum_bow;
	public static Item aluminum_bolt;
	public static Item aluminum_crossbow;
	public static Item aluminum_shears;
	public static Item aluminum_smallblend;
	public static Item aluminum_smallpowder;

	public static Item aluminumbrass_axe;
	public static Item aluminumbrass_blend;
	public static Item aluminumbrass_boots;
	public static Item aluminumbrass_chestplate;
	public static Item aluminumbrass_crackhammer;
	public static Item aluminumbrass_door;
	public static Item aluminumbrass_helmet;
	public static Item aluminumbrass_hoe;
	public static Item aluminumbrass_ingot;
	public static Item aluminumbrass_leggings;
	public static Item aluminumbrass_nugget;
	public static Item aluminumbrass_pickaxe;
	public static Item aluminumbrass_powder;
	public static Item aluminumbrass_shovel;
	public static Item aluminumbrass_sword;
	public static Item aluminumbrass_rod;
	public static Item aluminumbrass_gear;

	public static Item cadmium_axe;
	public static Item cadmium_blend;
	public static Item cadmium_boots;
	public static Item cadmium_chestplate;
	public static Item cadmium_crackhammer;
	public static Item cadmium_door;
	public static Item cadmium_helmet;
	public static Item cadmium_hoe;
	public static Item cadmium_ingot;
	public static Item cadmium_leggings;
	public static Item cadmium_nugget;
	public static Item cadmium_pickaxe;
	public static Item cadmium_powder;
	public static Item cadmium_shovel;
	public static Item cadmium_sword;
	public static Item cadmium_rod;
	public static Item cadmium_gear;

	public static Item chromium_axe;
	public static Item chromium_blend;
	public static Item chromium_boots;
	public static Item chromium_chestplate;
	public static Item chromium_crackhammer;
	public static Item chromium_door;
	public static Item chromium_helmet;
	public static Item chromium_hoe;
	public static Item chromium_ingot;
	public static Item chromium_leggings;
	public static Item chromium_nugget;
	public static Item chromium_pickaxe;
	public static Item chromium_powder;
	public static Item chromium_shovel;
	public static Item chromium_sword;
	public static Item chromium_rod;
	public static Item chromium_gear;

	public static Item iridium_axe;
	public static Item iridium_blend;
	public static Item iridium_boots;
	public static Item iridium_chestplate;
	public static Item iridium_crackhammer;
	public static Item iridium_door;
	public static Item iridium_helmet;
	public static Item iridium_hoe;
	public static Item iridium_ingot;
	public static Item iridium_leggings;
	public static Item iridium_nugget;
	public static Item iridium_pickaxe;
	public static Item iridium_powder;
	public static Item iridium_shovel;
	public static Item iridium_sword;
	public static Item iridium_rod;
	public static Item iridium_gear;

	public static Item lithium_axe;
	public static Item lithium_blend;
	public static Item lithium_boots;
	public static Item lithium_chestplate;
	public static Item lithium_crackhammer;
	public static Item lithium_door;
	public static Item lithium_helmet;
	public static Item lithium_hoe;
	public static Item lithium_ingot;
	public static Item lithium_leggings;
	public static Item lithium_nugget;
	public static Item lithium_pickaxe;
	public static Item lithium_powder;
	public static Item lithium_shovel;
	public static Item lithium_sword;
	public static Item lithium_rod;
	public static Item lithium_gear;

	public static Item magnesium_axe;
//	public static Item magnesium_blend;
	public static Item magnesium_boots;
	public static Item magnesium_chestplate;
	public static Item magnesium_crackhammer;
	public static Item magnesium_door;
	public static Item magnesium_helmet;
	public static Item magnesium_hoe;
	public static Item magnesium_ingot;
	public static Item magnesium_leggings;
	public static Item magnesium_nugget;
	public static Item magnesium_pickaxe;
	public static Item magnesium_powder;
	public static Item magnesium_shovel;
	public static Item magnesium_sword;
	public static Item magnesium_rod;
	public static Item magnesium_gear;

	public static Item manganese_axe;
	public static Item manganese_blend;
	public static Item manganese_boots;
	public static Item manganese_chestplate;
	public static Item manganese_crackhammer;
	public static Item manganese_door;
	public static Item manganese_helmet;
	public static Item manganese_hoe;
	public static Item manganese_ingot;
	public static Item manganese_leggings;
	public static Item manganese_nugget;
	public static Item manganese_pickaxe;
	public static Item manganese_powder;
	public static Item manganese_shovel;
	public static Item manganese_sword;
	public static Item manganese_rod;
	public static Item manganese_gear;

	public static Item niter_axe;
	public static Item niter_blend;
	public static Item niter_boots;
	public static Item niter_chestplate;
	public static Item niter_crackhammer;
	public static Item niter_door;
	public static Item niter_helmet;
	public static Item niter_hoe;
	public static Item niter_ingot;
	public static Item niter_leggings;
	public static Item niter_nugget;
	public static Item niter_pickaxe;
	public static Item niter_powder;
	public static Item niter_shovel;
	public static Item niter_sword;
	public static Item niter_rod;
	public static Item niter_gear;

	public static Item osmium_axe;
//	public static Item osmium_blend;
	public static Item osmium_boots;
	public static Item osmium_chestplate;
	public static Item osmium_crackhammer;
	public static Item osmium_door;
	public static Item osmium_helmet;
	public static Item osmium_hoe;
	public static Item osmium_ingot;
	public static Item osmium_leggings;
	public static Item osmium_nugget;
	public static Item osmium_pickaxe;
	public static Item osmium_powder;
	public static Item osmium_shovel;
	public static Item osmium_sword;
	public static Item osmium_rod;
	public static Item osmium_gear;

	public static Item phospherous_axe;
	public static Item phospherous_blend;
	public static Item phospherous_boots;
	public static Item phospherous_chestplate;
	public static Item phospherous_crackhammer;
	public static Item phospherous_door;
	public static Item phospherous_helmet;
	public static Item phospherous_hoe;
	public static Item phospherous_ingot;
	public static Item phospherous_leggings;
	public static Item phospherous_nugget;
	public static Item phospherous_pickaxe;
	public static Item phospherous_powder;
	public static Item phospherous_shovel;
	public static Item phospherous_sword;
	public static Item phospherous_rod;
	public static Item phospherous_gear;

	public static Item plutonium_axe;
	public static Item plutonium_blend;
	public static Item plutonium_boots;
	public static Item plutonium_chestplate;
	public static Item plutonium_crackhammer;
	public static Item plutonium_door;
	public static Item plutonium_helmet;
	public static Item plutonium_hoe;
	public static Item plutonium_ingot;
	public static Item plutonium_leggings;
	public static Item plutonium_nugget;
	public static Item plutonium_pickaxe;
	public static Item plutonium_powder;
	public static Item plutonium_shovel;
	public static Item plutonium_sword;
	public static Item plutonium_rod;
	public static Item plutonium_gear;

	public static Item rutile_axe;
//	public static Item rutile_blend;
	public static Item rutile_boots;
	public static Item rutile_chestplate;
	public static Item rutile_crackhammer;
	public static Item rutile_door;
	public static Item rutile_helmet;
	public static Item rutile_hoe;
	public static Item rutile_ingot;
	public static Item rutile_leggings;
	public static Item rutile_nugget;
	public static Item rutile_pickaxe;
	public static Item rutile_powder;
	public static Item rutile_shovel;
	public static Item rutile_sword;
	public static Item rutile_rod;
	public static Item rutile_gear;

	public static Item silicon_axe;
	public static Item silicon_blend;
	public static Item silicon_boots;
	public static Item silicon_chestplate;
	public static Item silicon_crackhammer;
	public static Item silicon_door;
	public static Item silicon_helmet;
	public static Item silicon_hoe;
	public static Item silicon_ingot;
	public static Item silicon_leggings;
	public static Item silicon_nugget;
	public static Item silicon_pickaxe;
	public static Item silicon_powder;
	public static Item silicon_shovel;
	public static Item silicon_sword;
	public static Item silicon_rod;
	public static Item silicon_gear;

	public static Item sulfur_axe;
	public static Item sulfur_blend;
	public static Item sulfur_boots;
	public static Item sulfur_chestplate;
	public static Item sulfur_crackhammer;
	public static Item sulfur_door;
	public static Item sulfur_helmet;
	public static Item sulfur_hoe;
	public static Item sulfur_ingot;
	public static Item sulfur_leggings;
	public static Item sulfur_nugget;
	public static Item sulfur_pickaxe;
	public static Item sulfur_powder;
	public static Item sulfur_shovel;
	public static Item sulfur_sword;
	public static Item sulfur_rod;
	public static Item sulfur_gear;

	public static Item tantalum_axe;
	public static Item tantalum_blend;
	public static Item tantalum_boots;
	public static Item tantalum_chestplate;
	public static Item tantalum_crackhammer;
	public static Item tantalum_door;
	public static Item tantalum_helmet;
	public static Item tantalum_hoe;
	public static Item tantalum_ingot;
	public static Item tantalum_leggings;
	public static Item tantalum_nugget;
	public static Item tantalum_pickaxe;
	public static Item tantalum_powder;
	public static Item tantalum_shovel;
	public static Item tantalum_sword;
	public static Item tantalum_rod;
	public static Item tantalum_gear;

	public static Item titanium_axe;
	public static Item titanium_blend;
	public static Item titanium_boots;
	public static Item titanium_chestplate;
	public static Item titanium_crackhammer;
	public static Item titanium_door;
	public static Item titanium_helmet;
	public static Item titanium_hoe;
	public static Item titanium_ingot;
	public static Item titanium_leggings;
	public static Item titanium_nugget;
	public static Item titanium_pickaxe;
	public static Item titanium_powder;
	public static Item titanium_shovel;
	public static Item titanium_sword;
	public static Item titanium_rod;
	public static Item titanium_gear;

	public static Item tungsten_axe;
//	public static Item tungsten_blend;
	public static Item tungsten_boots;
	public static Item tungsten_chestplate;
	public static Item tungsten_crackhammer;
	public static Item tungsten_door;
	public static Item tungsten_helmet;
	public static Item tungsten_hoe;
	public static Item tungsten_ingot;
	public static Item tungsten_leggings;
	public static Item tungsten_nugget;
	public static Item tungsten_pickaxe;
	public static Item tungsten_powder;
	public static Item tungsten_shovel;
	public static Item tungsten_sword;
	public static Item tungsten_rod;
	public static Item tungsten_gear;

	public static Item uranium_axe;
//	public static Item uranium_blend;
	public static Item uranium_boots;
	public static Item uranium_chestplate;
	public static Item uranium_crackhammer;
	public static Item uranium_door;
	public static Item uranium_helmet;
	public static Item uranium_hoe;
	public static Item uranium_ingot;
	public static Item uranium_leggings;
	public static Item uranium_nugget;
	public static Item uranium_pickaxe;
	public static Item uranium_powder;
	public static Item uranium_shovel;
	public static Item uranium_sword;
	public static Item uranium_rod;
	public static Item uranium_gear;

	public static Item zirconium_axe;
	public static Item zirconium_blend;
	public static Item zirconium_boots;
	public static Item zirconium_chestplate;
	public static Item zirconium_crackhammer;
	public static Item zirconium_door;
	public static Item zirconium_helmet;
	public static Item zirconium_hoe;
	public static Item zirconium_ingot;
	public static Item zirconium_leggings;
	public static Item zirconium_nugget;
	public static Item zirconium_pickaxe;
	public static Item zirconium_powder;
	public static Item zirconium_shovel;
	public static Item zirconium_sword;
	public static Item zirconium_rod;
	public static Item zirconium_gear;

	/**
	 * Gets the inventory item corresponding to a given door block
	 * @param b The door block
	 * @return The item that the player should use to place that kind of door
	 */
	public static Item getDoorItemForBlock(BlockMetalDoor b){
		return doorMap.get(b);
	}

	private static boolean initDone = false;
	public static void init(){
		if(initDone) return;
		
		modernmetals.init.Blocks.init();

		aluminum_axe = create_axe(Materials.aluminum);
//		aluminum_blend = create_blend(Materials.aluminum);
		aluminum_boots = create_boots(Materials.aluminum);
		aluminum_chestplate = create_chestplate(Materials.aluminum);
		aluminum_crackhammer = create_crackhammer(Materials.aluminum);
		aluminum_door = create_door(Materials.aluminum, Blocks.aluminum_door);
		aluminum_helmet = create_helmet(Materials.aluminum);
		aluminum_hoe = create_hoe(Materials.aluminum);
		aluminum_ingot = create_ingot(Materials.aluminum);
		aluminum_leggings = create_leggings(Materials.aluminum);
		aluminum_nugget = create_nugget(Materials.aluminum);
		aluminum_pickaxe = create_pickaxe(Materials.aluminum);
		aluminum_powder = create_powder(Materials.aluminum);
		aluminum_shovel = create_shovel(Materials.aluminum);
		aluminum_sword = create_sword(Materials.aluminum);
		aluminum_rod = create_rod(Materials.aluminum);
		aluminum_gear = create_gear(Materials.aluminum);

		aluminum_arrow = create_arrow(Materials.aluminum);
		aluminum_bow = create_bow(Materials.aluminum);
		aluminum_bolt = create_bolt(Materials.aluminum);
		aluminum_crossbow = create_crossbow(Materials.aluminum);
		aluminum_shears = create_shears(Materials.aluminum);
		aluminum_smallblend = create_smallblend(Materials.aluminum);
		aluminum_smallpowder = create_smallpowder(Materials.aluminum);

		aluminumbrass_axe = create_axe(Materials.aluminumbrass);
		aluminumbrass_blend = create_blend(Materials.aluminumbrass);
		aluminumbrass_boots = create_boots(Materials.aluminumbrass);
		aluminumbrass_chestplate = create_chestplate(Materials.aluminumbrass);
		aluminumbrass_crackhammer = create_crackhammer(Materials.aluminumbrass);
		aluminumbrass_door = create_door(Materials.aluminumbrass, Blocks.aluminumbrass_door);
		aluminumbrass_helmet = create_helmet(Materials.aluminumbrass);
		aluminumbrass_hoe = create_hoe(Materials.aluminumbrass);
		aluminumbrass_ingot = create_ingot(Materials.aluminumbrass);
		aluminumbrass_leggings = create_leggings(Materials.aluminumbrass);
		aluminumbrass_nugget = create_nugget(Materials.aluminumbrass);
		aluminumbrass_pickaxe = create_pickaxe(Materials.aluminumbrass);
		aluminumbrass_powder = create_powder(Materials.aluminumbrass);
		aluminumbrass_shovel = create_shovel(Materials.aluminumbrass);
		aluminumbrass_sword = create_sword(Materials.aluminumbrass);
		aluminumbrass_rod = create_rod(Materials.aluminumbrass);
		aluminumbrass_gear = create_gear(Materials.aluminumbrass);

		cadmium_axe = create_axe(Materials.cadmium);
		cadmium_blend = create_blend(Materials.cadmium);
		cadmium_boots = create_boots(Materials.cadmium);
		cadmium_chestplate = create_chestplate(Materials.cadmium);
		cadmium_crackhammer = create_crackhammer(Materials.cadmium);
		cadmium_door = create_door(Materials.cadmium, Blocks.cadmium_door);
		cadmium_helmet = create_helmet(Materials.cadmium);
		cadmium_hoe = create_hoe(Materials.cadmium);
		cadmium_ingot = create_ingot(Materials.cadmium);
		cadmium_leggings = create_leggings(Materials.cadmium);
		cadmium_nugget = create_nugget(Materials.cadmium);
		cadmium_pickaxe = create_pickaxe(Materials.cadmium);
		cadmium_powder = create_powder(Materials.cadmium);
		cadmium_shovel = create_shovel(Materials.cadmium);
		cadmium_sword = create_sword(Materials.cadmium);
		cadmium_rod = create_rod(Materials.cadmium);
		cadmium_gear = create_gear(Materials.cadmium);

		chromium_axe = create_axe(Materials.chromium);
		chromium_blend = create_blend(Materials.chromium);
		chromium_boots = create_boots(Materials.chromium);
		chromium_chestplate = create_chestplate(Materials.chromium);
		chromium_crackhammer = create_crackhammer(Materials.chromium);
		chromium_door = create_door(Materials.chromium, Blocks.chromium_door);
		chromium_helmet = create_helmet(Materials.chromium);
		chromium_hoe = create_hoe(Materials.chromium);
		chromium_ingot = create_ingot(Materials.chromium);
		chromium_leggings = create_leggings(Materials.chromium);
		chromium_nugget = create_nugget(Materials.chromium);
		chromium_pickaxe = create_pickaxe(Materials.chromium);
		chromium_powder = create_powder(Materials.chromium);
		chromium_shovel = create_shovel(Materials.chromium);
		chromium_sword = create_sword(Materials.chromium);
		chromium_rod = create_rod(Materials.chromium);
		chromium_gear = create_gear(Materials.chromium);

		iridium_axe = create_axe(Materials.iridium);
		iridium_blend = create_blend(Materials.iridium);
		iridium_boots = create_boots(Materials.iridium);
		iridium_chestplate = create_chestplate(Materials.iridium);
		iridium_crackhammer = create_crackhammer(Materials.iridium);
		iridium_door = create_door(Materials.iridium, Blocks.iridium_door);
		iridium_helmet = create_helmet(Materials.iridium);
		iridium_hoe = create_hoe(Materials.iridium);
		iridium_ingot = create_ingot(Materials.iridium);
		iridium_leggings = create_leggings(Materials.iridium);
		iridium_nugget = create_nugget(Materials.iridium);
		iridium_pickaxe = create_pickaxe(Materials.iridium);
		iridium_powder = create_powder(Materials.iridium);
		iridium_shovel = create_shovel(Materials.iridium);
		iridium_sword = create_sword(Materials.iridium);
		iridium_rod = create_rod(Materials.iridium);
		iridium_gear = create_gear(Materials.iridium);

		lithium_axe = create_axe(Materials.lithium);
		lithium_blend = create_blend(Materials.lithium);
		lithium_boots = create_boots(Materials.lithium);
		lithium_chestplate = create_chestplate(Materials.lithium);
		lithium_crackhammer = create_crackhammer(Materials.lithium);
		lithium_door = create_door(Materials.lithium, Blocks.lithium_door);
		lithium_helmet = create_helmet(Materials.lithium);
		lithium_hoe = create_hoe(Materials.lithium);
		lithium_ingot = create_ingot(Materials.lithium);
		lithium_leggings = create_leggings(Materials.lithium);
		lithium_nugget = create_nugget(Materials.lithium);
		lithium_pickaxe = create_pickaxe(Materials.lithium);
		lithium_powder = create_powder(Materials.lithium);
		lithium_shovel = create_shovel(Materials.lithium);
		lithium_sword = create_sword(Materials.lithium);
		lithium_rod = create_rod(Materials.lithium);
		lithium_gear = create_gear(Materials.lithium);

		magnesium_axe = create_axe(Materials.magnesium);
//		magnesium_blend = create_blend(Materials.magnesium);
		magnesium_boots = create_boots(Materials.magnesium);
		magnesium_chestplate = create_chestplate(Materials.magnesium);
		magnesium_crackhammer = create_crackhammer(Materials.magnesium);
		magnesium_door = create_door(Materials.magnesium, Blocks.magnesium_door);
		magnesium_helmet = create_helmet(Materials.magnesium);
		magnesium_hoe = create_hoe(Materials.magnesium);
		magnesium_ingot = create_ingot(Materials.magnesium);
		magnesium_leggings = create_leggings(Materials.magnesium);
		magnesium_nugget = create_nugget(Materials.magnesium);
		magnesium_pickaxe = create_pickaxe(Materials.magnesium);
		magnesium_powder = create_powder(Materials.magnesium);
		magnesium_shovel = create_shovel(Materials.magnesium);
		magnesium_sword = create_sword(Materials.magnesium);
		magnesium_rod = create_rod(Materials.magnesium);
		magnesium_gear = create_gear(Materials.magnesium);

		manganese_axe = create_axe(Materials.manganese);
		manganese_blend = create_blend(Materials.manganese);
		manganese_boots = create_boots(Materials.manganese);
		manganese_chestplate = create_chestplate(Materials.manganese);
		manganese_crackhammer = create_crackhammer(Materials.manganese);
		manganese_door = create_door(Materials.manganese, Blocks.manganese_door);
		manganese_helmet = create_helmet(Materials.manganese);
		manganese_hoe = create_hoe(Materials.manganese);
		manganese_ingot = create_ingot(Materials.manganese);
		manganese_leggings = create_leggings(Materials.manganese);
		manganese_nugget = create_nugget(Materials.manganese);
		manganese_pickaxe = create_pickaxe(Materials.manganese);
		manganese_powder = create_powder(Materials.manganese);
		manganese_shovel = create_shovel(Materials.manganese);
		manganese_sword = create_sword(Materials.manganese);
		manganese_rod = create_rod(Materials.manganese);
		manganese_gear = create_gear(Materials.manganese);

		niter_axe = create_axe(Materials.niter);
		niter_blend = create_blend(Materials.niter);
		niter_boots = create_boots(Materials.niter);
		niter_chestplate = create_chestplate(Materials.niter);
		niter_crackhammer = create_crackhammer(Materials.niter);
		niter_door = create_door(Materials.niter, Blocks.niter_door);
		niter_helmet = create_helmet(Materials.niter);
		niter_hoe = create_hoe(Materials.niter);
		niter_ingot = create_ingot(Materials.niter);
		niter_leggings = create_leggings(Materials.niter);
		niter_nugget = create_nugget(Materials.niter);
		niter_pickaxe = create_pickaxe(Materials.niter);
		niter_powder = create_powder(Materials.niter);
		niter_shovel = create_shovel(Materials.niter);
		niter_sword = create_sword(Materials.niter);
		niter_rod = create_rod(Materials.niter);
		niter_gear = create_gear(Materials.niter);

		osmium_axe = create_axe(Materials.osmium);
//		osmium_blend = create_blend(Materials.osmium);
		osmium_boots = create_boots(Materials.osmium);
		osmium_chestplate = create_chestplate(Materials.osmium);
		osmium_crackhammer = create_crackhammer(Materials.osmium);
		osmium_door = create_door(Materials.osmium, Blocks.osmium_door);
		osmium_helmet = create_helmet(Materials.osmium);
		osmium_hoe = create_hoe(Materials.osmium);
		osmium_ingot = create_ingot(Materials.osmium);
		osmium_leggings = create_leggings(Materials.osmium);
		osmium_nugget = create_nugget(Materials.osmium);
		osmium_pickaxe = create_pickaxe(Materials.osmium);
		osmium_powder = create_powder(Materials.osmium);
		osmium_shovel = create_shovel(Materials.osmium);
		osmium_sword = create_sword(Materials.osmium);
		osmium_rod = create_rod(Materials.osmium);
		osmium_gear = create_gear(Materials.osmium);

		phospherous_axe = create_axe(Materials.phospherous);
		phospherous_blend = create_blend(Materials.phospherous);
		phospherous_boots = create_boots(Materials.phospherous);
		phospherous_chestplate = create_chestplate(Materials.phospherous);
		phospherous_crackhammer = create_crackhammer(Materials.phospherous);
		phospherous_door = create_door(Materials.phospherous, Blocks.phospherous_door);
		phospherous_helmet = create_helmet(Materials.phospherous);
		phospherous_hoe = create_hoe(Materials.phospherous);
		phospherous_ingot = create_ingot(Materials.phospherous);
		phospherous_leggings = create_leggings(Materials.phospherous);
		phospherous_nugget = create_nugget(Materials.phospherous);
		phospherous_pickaxe = create_pickaxe(Materials.phospherous);
		phospherous_powder = create_powder(Materials.phospherous);
		phospherous_shovel = create_shovel(Materials.phospherous);
		phospherous_sword = create_sword(Materials.phospherous);
		phospherous_rod = create_rod(Materials.phospherous);
		phospherous_gear = create_gear(Materials.phospherous);

		plutonium_axe = create_axe(Materials.plutonium);
		plutonium_blend = create_blend(Materials.plutonium);
		plutonium_boots = create_boots(Materials.plutonium);
		plutonium_chestplate = create_chestplate(Materials.plutonium);
		plutonium_crackhammer = create_crackhammer(Materials.plutonium);
		plutonium_door = create_door(Materials.plutonium, Blocks.plutonium_door);
		plutonium_helmet = create_helmet(Materials.plutonium);
		plutonium_hoe = create_hoe(Materials.plutonium);
		plutonium_ingot = create_ingot(Materials.plutonium);
		plutonium_leggings = create_leggings(Materials.plutonium);
		plutonium_nugget = create_nugget(Materials.plutonium);
		plutonium_pickaxe = create_pickaxe(Materials.plutonium);
		plutonium_powder = create_powder(Materials.plutonium);
		plutonium_shovel = create_shovel(Materials.plutonium);
		plutonium_sword = create_sword(Materials.plutonium);
		plutonium_rod = create_rod(Materials.plutonium);
		plutonium_gear = create_gear(Materials.plutonium);

		rutile_axe = create_axe(Materials.rutile);
//		rutile_blend = create_blend(Materials.rutile);
		rutile_boots = create_boots(Materials.rutile);
		rutile_chestplate = create_chestplate(Materials.rutile);
		rutile_crackhammer = create_crackhammer(Materials.rutile);
		rutile_door = create_door(Materials.rutile, Blocks.rutile_door);
		rutile_helmet = create_helmet(Materials.rutile);
		rutile_hoe = create_hoe(Materials.rutile);
		rutile_ingot = create_ingot(Materials.rutile);
		rutile_leggings = create_leggings(Materials.rutile);
		rutile_nugget = create_nugget(Materials.rutile);
		rutile_pickaxe = create_pickaxe(Materials.rutile);
		rutile_powder = create_powder(Materials.rutile);
		rutile_shovel = create_shovel(Materials.rutile);
		rutile_sword = create_sword(Materials.rutile);
		rutile_rod = create_rod(Materials.rutile);
		rutile_gear = create_gear(Materials.rutile);

		silicon_axe = create_axe(Materials.silicon);
		silicon_blend = create_blend(Materials.silicon);
		silicon_boots = create_boots(Materials.silicon);
		silicon_chestplate = create_chestplate(Materials.silicon);
		silicon_crackhammer = create_crackhammer(Materials.silicon);
		silicon_door = create_door(Materials.silicon, Blocks.silicon_door);
		silicon_helmet = create_helmet(Materials.silicon);
		silicon_hoe = create_hoe(Materials.silicon);
		silicon_ingot = create_ingot(Materials.silicon);
		silicon_leggings = create_leggings(Materials.silicon);
		silicon_nugget = create_nugget(Materials.silicon);
		silicon_pickaxe = create_pickaxe(Materials.silicon);
		silicon_powder = create_powder(Materials.silicon);
		silicon_shovel = create_shovel(Materials.silicon);
		silicon_sword = create_sword(Materials.silicon);
		silicon_rod = create_rod(Materials.silicon);
		silicon_gear = create_gear(Materials.silicon);

		sulfur_axe = create_axe(Materials.sulfur);
		sulfur_blend = create_blend(Materials.sulfur);
		sulfur_boots = create_boots(Materials.sulfur);
		sulfur_chestplate = create_chestplate(Materials.sulfur);
		sulfur_crackhammer = create_crackhammer(Materials.sulfur);
		sulfur_door = create_door(Materials.sulfur, Blocks.sulfur_door);
		sulfur_helmet = create_helmet(Materials.sulfur);
		sulfur_hoe = create_hoe(Materials.sulfur);
		sulfur_ingot = create_ingot(Materials.sulfur);
		sulfur_leggings = create_leggings(Materials.sulfur);
		sulfur_nugget = create_nugget(Materials.sulfur);
		sulfur_pickaxe = create_pickaxe(Materials.sulfur);
		sulfur_powder = create_powder(Materials.sulfur);
		sulfur_shovel = create_shovel(Materials.sulfur);
		sulfur_sword = create_sword(Materials.sulfur);
		sulfur_rod = create_rod(Materials.sulfur);
		sulfur_gear = create_gear(Materials.sulfur);

		tantalum_axe = create_axe(Materials.tantalum);
		tantalum_blend = create_blend(Materials.tantalum);
		tantalum_boots = create_boots(Materials.tantalum);
		tantalum_chestplate = create_chestplate(Materials.tantalum);
		tantalum_crackhammer = create_crackhammer(Materials.tantalum);
		tantalum_door = create_door(Materials.tantalum, Blocks.tantalum_door);
		tantalum_helmet = create_helmet(Materials.tantalum);
		tantalum_hoe = create_hoe(Materials.tantalum);
		tantalum_ingot = create_ingot(Materials.tantalum);
		tantalum_leggings = create_leggings(Materials.tantalum);
		tantalum_nugget = create_nugget(Materials.tantalum);
		tantalum_pickaxe = create_pickaxe(Materials.tantalum);
		tantalum_powder = create_powder(Materials.tantalum);
		tantalum_shovel = create_shovel(Materials.tantalum);
		tantalum_sword = create_sword(Materials.tantalum);
		tantalum_rod = create_rod(Materials.tantalum);
		tantalum_gear = create_gear(Materials.tantalum);

		titanium_axe = create_axe(Materials.titanium);
		titanium_blend = create_blend(Materials.titanium);
		titanium_boots = create_boots(Materials.titanium);
		titanium_chestplate = create_chestplate(Materials.titanium);
		titanium_crackhammer = create_crackhammer(Materials.titanium);
		titanium_door = create_door(Materials.titanium, Blocks.titanium_door);
		titanium_helmet = create_helmet(Materials.titanium);
		titanium_hoe = create_hoe(Materials.titanium);
		titanium_ingot = create_ingot(Materials.titanium);
		titanium_leggings = create_leggings(Materials.titanium);
		titanium_nugget = create_nugget(Materials.titanium);
		titanium_pickaxe = create_pickaxe(Materials.titanium);
		titanium_powder = create_powder(Materials.titanium);
		titanium_shovel = create_shovel(Materials.titanium);
		titanium_sword = create_sword(Materials.titanium);
		titanium_rod = create_rod(Materials.titanium);
		titanium_gear = create_gear(Materials.titanium);

		tungsten_axe = create_axe(Materials.tungsten);
//		tungsten_blend = create_blend(Materials.tungsten);
		tungsten_boots = create_boots(Materials.tungsten);
		tungsten_chestplate = create_chestplate(Materials.tungsten);
		tungsten_crackhammer = create_crackhammer(Materials.tungsten);
		tungsten_door = create_door(Materials.tungsten, Blocks.tungsten_door);
		tungsten_helmet = create_helmet(Materials.tungsten);
		tungsten_hoe = create_hoe(Materials.tungsten);
		tungsten_ingot = create_ingot(Materials.tungsten);
		tungsten_leggings = create_leggings(Materials.tungsten);
		tungsten_nugget = create_nugget(Materials.tungsten);
		tungsten_pickaxe = create_pickaxe(Materials.tungsten);
		tungsten_powder = create_powder(Materials.tungsten);
		tungsten_shovel = create_shovel(Materials.tungsten);
		tungsten_sword = create_sword(Materials.tungsten);
		tungsten_rod = create_rod(Materials.tungsten);
		tungsten_gear = create_gear(Materials.tungsten);

		uranium_axe = create_axe(Materials.uranium);
//		uranium_blend = create_blend(Materials.uranium);
		uranium_boots = create_boots(Materials.uranium);
		uranium_chestplate = create_chestplate(Materials.uranium);
		uranium_crackhammer = create_crackhammer(Materials.uranium);
		uranium_door = create_door(Materials.uranium, Blocks.uranium_door);
		uranium_helmet = create_helmet(Materials.uranium);
		uranium_hoe = create_hoe(Materials.uranium);
		uranium_ingot = create_ingot(Materials.uranium);
		uranium_leggings = create_leggings(Materials.uranium);
		uranium_nugget = create_nugget(Materials.uranium);
		uranium_pickaxe = create_pickaxe(Materials.uranium);
		uranium_powder = create_powder(Materials.uranium);
		uranium_shovel = create_shovel(Materials.uranium);
		uranium_sword = create_sword(Materials.uranium);
		uranium_rod = create_rod(Materials.uranium);
		uranium_gear = create_gear(Materials.uranium);

		zirconium_axe = create_axe(Materials.zirconium);
		zirconium_blend = create_blend(Materials.zirconium);
		zirconium_boots = create_boots(Materials.zirconium);
		zirconium_chestplate = create_chestplate(Materials.zirconium);
		zirconium_crackhammer = create_crackhammer(Materials.zirconium);
		zirconium_door = create_door(Materials.zirconium, Blocks.zirconium_door);
		zirconium_helmet = create_helmet(Materials.zirconium);
		zirconium_hoe = create_hoe(Materials.zirconium);
		zirconium_ingot = create_ingot(Materials.zirconium);
		zirconium_leggings = create_leggings(Materials.zirconium);
		zirconium_nugget = create_nugget(Materials.zirconium);
		zirconium_pickaxe = create_pickaxe(Materials.zirconium);
		zirconium_powder = create_powder(Materials.zirconium);
		zirconium_shovel = create_shovel(Materials.zirconium);
		zirconium_sword = create_sword(Materials.zirconium);
		zirconium_rod = create_rod(Materials.zirconium);
		zirconium_gear = create_gear(Materials.zirconium);

		for(Item i : itemRegistry.keySet()){
			allItems.put(itemRegistry.get(i), i);
			if(i instanceof IOreDictionaryEntry){OreDictionary.registerOre(((IOreDictionaryEntry)i).getOreDictionaryName(), i);}
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
		classSortingValues.put(ItemMetalShears.class, ++ss * 10000);
		classSortingValues.put(ItemMetalSmallBlend.class, ++ss * 10000);
		classSortingValues.put(ItemMetalSmallPowder.class, ++ss * 10000);
		classSortingValues.put(ItemMetalDoor.class, classSortingValues.get(BlockMetalDoor.class));

		List<MetalMaterial> metlist = new ArrayList<>(Materials.getAllMetals().size());
		metlist.addAll(Materials.getAllMetals());
		metlist.sort((MetalMaterial a, MetalMaterial b)-> a.getName().compareToIgnoreCase(b.getName()));
		for(int i = 0; i < metlist.size(); i++){
			materialSortingValues.put(metlist.get(i), i*100);
		}
		
		initDone = true;
	}



	private static Item registerItem(Item item, String name, MetalMaterial metal, CreativeTabs tab){
		item.setRegistryName(ModernMetals.MODID, name);
		item.setUnlocalizedName(ModernMetals.MODID+"."+name);
		GameRegistry.register(item); 
		itemRegistry.put(item, name);
		if(tab != null){
			item.setCreativeTab(tab);
		}
		if(metal != null){
			itemsByMetal.computeIfAbsent(metal, (MetalMaterial g)->new ArrayList<>());
			itemsByMetal.get(metal).add(item);
		}
		return item;
	}
	
	
	private static Item create_ingot(MetalMaterial metal){
		return registerItem(new ItemMetalIngot(metal), metal.getName()+"_"+"ingot", metal, ItemGroups.tab_items);
	}
	
	private static Item create_nugget(MetalMaterial metal){
		return registerItem(new ItemMetalNugget(metal), metal.getName()+"_"+"nugget", metal, ItemGroups.tab_items);
	}
	
	private static Item create_powder(MetalMaterial metal){
		return registerItem(new ItemMetalPowder(metal), metal.getName()+"_"+"powder", metal, ItemGroups.tab_items);
	}
	
	private static Item create_blend(MetalMaterial metal){
		return registerItem(new ItemMetalBlend(metal), metal.getName()+"_"+"blend", metal, ItemGroups.tab_items);
	}


	private static Item create_rod(MetalMaterial metal){
		return registerItem(new GenericMetalItem(metal), metal.getName()+"_"+"rod", metal, ItemGroups.tab_items);
	}


	private static Item create_gear(MetalMaterial metal){
		return registerItem(new GenericMetalItem(metal), metal.getName()+"_"+"gear", metal, ItemGroups.tab_items);
	}
	
	private static Item create_axe(MetalMaterial metal){
		return registerItem(new ItemMetalAxe(metal), metal.getName()+"_"+"axe", metal, ItemGroups.tab_tools);
	}

	private static Item create_crackhammer(MetalMaterial metal){
		return registerItem(new ItemMetalCrackHammer(metal), metal.getName()+"_"+"crackhammer", metal, ItemGroups.tab_tools);
	}
	
	private static Item create_hoe(MetalMaterial metal){
		return registerItem(new ItemMetalHoe(metal), metal.getName()+"_"+"hoe", metal, ItemGroups.tab_tools);
	}
	
	private static Item create_pickaxe(MetalMaterial metal){
		return registerItem(new ItemMetalPickaxe(metal), metal.getName()+"_"+"pickaxe", metal, ItemGroups.tab_tools);
	}
	
	private static Item create_shovel(MetalMaterial metal){
		return registerItem(new ItemMetalShovel(metal), metal.getName()+"_"+"shovel", metal, ItemGroups.tab_tools);
	}
	
	private static Item create_sword(MetalMaterial metal){
		return registerItem(new ItemMetalSword(metal), metal.getName()+"_"+"sword", metal, ItemGroups.tab_tools);
	}

	private static Item create_helmet(MetalMaterial metal){
		return registerItem(ItemMetalArmor.createHelmet(metal), metal.getName()+"_"+"helmet", metal, ItemGroups.tab_tools);
	}

	private static Item create_chestplate(MetalMaterial metal){
		return registerItem(ItemMetalArmor.createChestplate(metal), metal.getName()+"_"+"chestplate", metal, ItemGroups.tab_tools);
	}

	private static Item create_leggings(MetalMaterial metal){
		return registerItem(ItemMetalArmor.createLeggings(metal), metal.getName()+"_"+"leggings", metal, ItemGroups.tab_tools);
	}

	private static Item create_boots(MetalMaterial metal){
		return registerItem(ItemMetalArmor.createBoots(metal), metal.getName()+"_"+"boots", metal, ItemGroups.tab_tools);
	}

	private static Item create_arrow(MetalMaterial metal){
		return registerItem(new ItemMetalArrow(metal), metal.getName()+"_"+"arrow", metal, ItemGroups.tab_tools);
	}

	private static Item create_bolt(MetalMaterial metal){
		return registerItem(new ItemMetalBolt(metal), metal.getName()+"_"+"bolt", metal, ItemGroups.tab_tools);
	}

	private static Item create_bow(MetalMaterial metal){
		return registerItem(new ItemMetalBow(metal), metal.getName()+"_"+"bow", metal, ItemGroups.tab_tools);
	}

	private static Item create_crossbow(MetalMaterial metal){
		return registerItem(new ItemMetalCrossbow(metal), metal.getName()+"_"+"crossbow", metal, ItemGroups.tab_tools);
	}

	private static Item create_shears(MetalMaterial metal){
		return registerItem(new ItemMetalShears(metal), metal.getName()+"_"+"shears", metal, ItemGroups.tab_tools);
	}
	
	private static Item create_smallblend(MetalMaterial metal){
		return registerItem(new ItemMetalSmallBlend(metal), metal.getName()+"_"+"smallblend", metal, ItemGroups.tab_items);
	}

	private static Item create_smallpowder(MetalMaterial metal){
		return registerItem(new ItemMetalSmallPowder(metal), metal.getName()+"_"+"smallpowder", metal, ItemGroups.tab_items);
	}

	private static Item create_door(MetalMaterial metal,BlockDoor door){
		Item item = new ItemMetalDoor(door,metal);
		registerItem(item, metal.getName()+"_"+"door"+"_"+"item", metal, ItemGroups.tab_blocks);
		doorMap.put(door, item);
		return item;
	}

	public static int getSortingValue(ItemStack a){
		int classVal = 990000;
		int metalVal = 9900;
		if(a.getItem() instanceof ItemBlock && ((ItemBlock)a.getItem()).getBlock() instanceof IMetalObject){
			classVal = classSortingValues.computeIfAbsent(((ItemBlock)a.getItem()).getBlock().getClass(),
					(Class c)->990000);
			metalVal = materialSortingValues.computeIfAbsent(((IMetalObject)((ItemBlock)a.getItem()).getBlock()).getMetalMaterial(),
					(MetalMaterial m)->9900);
		} else if(a.getItem() instanceof IMetalObject){
			classVal = classSortingValues.computeIfAbsent(a.getItem().getClass(),
					(Class c)->990000);
			metalVal = materialSortingValues.computeIfAbsent(((IMetalObject)a.getItem()).getMetalMaterial(),
					(MetalMaterial m)->9900);
		}
		return classVal + metalVal + (a.getMetadata() % 100);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemRenders(FMLInitializationEvent event){
		for(Item i : itemRegistry.keySet()){
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
			.register(i, 0, 
				new ModelResourceLocation(ModernMetals.MODID+":"+itemRegistry.get(i), "inventory"));
		}

		// colorize universal bucket
/*		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				FluidStack fs = ((UniversalBucket) stack.getItem()).getFluid(stack);
				if(fs == null) return 0x00FFFFFF;
				return tintIndex > 0?fs.getFluid().getColor(fs):0xFFFFFFFF;
			}
		},universal_bucket);
*/
	}
}

package modernmetals.init;

import modernmetals.ModernMetals;
import cyano.basemetals.BaseMetals;
import cyano.basemetals.blocks.*;
import cyano.basemetals.items.*;
import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import modernmetals.init.ItemGroups;
import modernmetals.init.Blocks;
import modernmetals.init.Materials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.*;

/**
 * This class initializes all items in Modern Metals and provides some utility 
 * methods for looking up items. 
 * @author DrCyano
 *
 */
public abstract class Items { // extends cyano.basemetals.init.Items {
	
	private static Map<Item,String> itemRegistry = new HashMap<>();
	private static Map<String,Item> allItems = new HashMap<>();
	private static Map<MetalMaterial,List<Item>> itemsByMetal = new HashMap<>();

	private static Map<BlockDoor,Item> doorMap = new HashMap<>();

	
	
	@SuppressWarnings("rawtypes")
	private static Map<Class,Integer> classSortingValues = new HashMap<>();
	private static Map<MetalMaterial,Integer> materialSortingValues = new HashMap<>();
	/**
	 * Gets an item by its name. The name is the name as it is registered in 
	 * the GameRegistry, not its unlocalized name (the unlocalized name is the 
	 * registered name plus the prefix "basemetals.")
	 * @param name The name of the item in question
	 * @return The item matching that name, or null if there isn't one
	 */
	public static Item getItemByName(String name){
		return allItems.get(name);
	}
	/**
	 * This is the reverse of the getItemByName(...) method, returning the 
	 * registered name of an item instance (Base Metals items only).
	 * @param i The item in question
	 * @return The name of the item, or null if the item is not a Base Metals 
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

	public static UniversalBucket universal_bucket;
	// TODO: metal arrows or crossbow & bolts

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

	public static Item cupronickel_axe;
	public static Item cupronickel_blend;
	public static Item cupronickel_boots;
	public static Item cupronickel_chestplate;
	public static Item cupronickel_crackhammer;
	public static Item cupronickel_door;
	public static Item cupronickel_helmet;
	public static Item cupronickel_hoe;
	public static Item cupronickel_ingot;
	public static Item cupronickel_leggings;
	public static Item cupronickel_nugget;
	public static Item cupronickel_pickaxe;
	public static Item cupronickel_powder;
	public static Item cupronickel_shovel;
	public static Item cupronickel_sword;

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

	public static Item platinum_axe;
//	public static Item platinum_blend;
	public static Item platinum_boots;
	public static Item platinum_chestplate;
	public static Item platinum_crackhammer;
	public static Item platinum_door;
	public static Item platinum_helmet;
	public static Item platinum_hoe;
	public static Item platinum_ingot;
	public static Item platinum_leggings;
	public static Item platinum_nugget;
	public static Item platinum_pickaxe;
	public static Item platinum_powder;
	public static Item platinum_shovel;
	public static Item platinum_sword;

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

		cupronickel_axe = create_axe(Materials.cupronickel);
		cupronickel_blend = create_blend(Materials.cupronickel);
		cupronickel_boots = create_boots(Materials.cupronickel);
		cupronickel_chestplate = create_chestplate(Materials.cupronickel);
		cupronickel_crackhammer = create_crackhammer(Materials.cupronickel);
		cupronickel_door = create_door(Materials.cupronickel, Blocks.cupronickel_door);
		cupronickel_helmet = create_helmet(Materials.cupronickel);
		cupronickel_hoe = create_hoe(Materials.cupronickel);
		cupronickel_ingot = create_ingot(Materials.cupronickel);
		cupronickel_leggings = create_leggings(Materials.cupronickel);
		cupronickel_nugget = create_nugget(Materials.cupronickel);
		cupronickel_pickaxe = create_pickaxe(Materials.cupronickel);
		cupronickel_powder = create_powder(Materials.cupronickel);
		cupronickel_shovel = create_shovel(Materials.cupronickel);
		cupronickel_sword = create_sword(Materials.cupronickel);

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

		platinum_axe = create_axe(Materials.platinum);
//		platinum_blend = create_blend(Materials.platinum);
		platinum_boots = create_boots(Materials.platinum);
		platinum_chestplate = create_chestplate(Materials.platinum);
		platinum_crackhammer = create_crackhammer(Materials.platinum);
		platinum_door = create_door(Materials.platinum, Blocks.platinum_door);
		platinum_helmet = create_helmet(Materials.platinum);
		platinum_hoe = create_hoe(Materials.platinum);
		platinum_ingot = create_ingot(Materials.platinum);
		platinum_leggings = create_leggings(Materials.platinum);
		platinum_nugget = create_nugget(Materials.platinum);
		platinum_pickaxe = create_pickaxe(Materials.platinum);
		platinum_powder = create_powder(Materials.platinum);
		platinum_shovel = create_shovel(Materials.platinum);
		platinum_sword = create_sword(Materials.platinum);

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
		classSortingValues.put(ItemMetalDoor.class, classSortingValues.get(BlockMetalDoor.class));

		List<MetalMaterial> metlist = new ArrayList<>(Materials.getAllMetals().size());
		metlist.addAll(Materials.getAllMetals());
		metlist.sort((MetalMaterial a, MetalMaterial b)-> a.getName().compareToIgnoreCase(b.getName()));
		for(int i = 0; i < metlist.size(); i++){
			materialSortingValues.put(metlist.get(i), i*100);
		}
		
		initDone = true;
	}

	
	
	private static Item registerItem(Item i, String regName, MetalMaterial m){
//		i.setRegistryName(ModernMetals.MODID+":"+regName);
		i.setRegistryName(ModernMetals.MODID, regName);
		GameRegistry.register(i); 
		itemRegistry.put(i, regName);
		if(m != null){
			itemsByMetal.computeIfAbsent(m, (MetalMaterial g)->new ArrayList<>());
			itemsByMetal.get(m).add(i);
		}
		return i;
	}

	private static Item init(Item i, String n, CreativeTabs tab){
		i.setUnlocalizedName(BaseMetals.MODID+"."+n);
		String regName = n;
		registerItem(i, regName, null);
		i.setCreativeTab(tab);
		return i;
	}

	
	private static Item create_ingot(MetalMaterial m){
		String n = "ingot";
		Item i = new ItemMetalIngot(m);
		i.setUnlocalizedName(ModernMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		registerItem(i, regName, m);
		i.setCreativeTab(ItemGroups.tab_items);
		return i;
	}
	
	private static Item create_nugget(MetalMaterial m){
		String n = "nugget";
		Item i = new ItemMetalNugget(m);
		i.setUnlocalizedName(ModernMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		registerItem(i, regName, m);
		i.setCreativeTab(ItemGroups.tab_items);
		return i;
	}
	
	private static Item create_powder(MetalMaterial m){
		String n = "powder";
		Item i = new ItemMetalPowder(m);
		i.setUnlocalizedName(ModernMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		registerItem(i, regName, m);
		i.setCreativeTab(ItemGroups.tab_items);
		return i;
	}
	
	private static Item create_blend(MetalMaterial m){
		String n = "blend";
		Item i = new ItemMetalBlend(m);
		i.setUnlocalizedName(ModernMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		registerItem(i, regName, m);
		i.setCreativeTab(ItemGroups.tab_items);
		return i;
	}
	
	private static Item create_axe(MetalMaterial m){
		String n = "axe";
		Item i = new ItemMetalAxe(m);
		i.setUnlocalizedName(ModernMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		registerItem(i, regName, m);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}

	private static Item create_crackhammer(MetalMaterial m){
		String n = "crackhammer";
		Item i = new ItemMetalCrackHammer(m);
		i.setUnlocalizedName(ModernMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		registerItem(i, regName, m);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}
	
	private static Item create_hoe(MetalMaterial m){
		String n = "hoe";
		Item i = new ItemMetalHoe(m);
		i.setUnlocalizedName(ModernMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		registerItem(i, regName, m);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}
	
	private static Item create_pickaxe(MetalMaterial m){
		String n = "pickaxe";
		Item i = new ItemMetalPickaxe(m);
		i.setUnlocalizedName(ModernMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		registerItem(i, regName, m);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}
	
	private static Item create_shovel(MetalMaterial m){
		String n = "shovel";
		Item i = new ItemMetalShovel(m);
		i.setUnlocalizedName(ModernMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		registerItem(i, regName, m);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}
	
	private static Item create_sword(MetalMaterial m){
		String n = "sword";
		Item i = new ItemMetalSword(m);
		i.setUnlocalizedName(ModernMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		registerItem(i, regName, m);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}

	private static Item create_helmet(MetalMaterial m){
		String n = "helmet";
		Item i = ItemMetalArmor.createHelmet(m);
		i.setUnlocalizedName(ModernMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		registerItem(i, regName, m);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}
	private static Item create_chestplate(MetalMaterial m){
		String n = "chestplate";
		Item i = ItemMetalArmor.createChestplate(m);
		i.setUnlocalizedName(ModernMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		registerItem(i, regName, m);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}
	private static Item create_leggings(MetalMaterial m){
		String n = "leggings";
		Item i = ItemMetalArmor.createLeggings(m);
		i.setUnlocalizedName(ModernMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		registerItem(i, regName, m);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}
	private static Item create_boots(MetalMaterial m){
		String n = "boots";
		Item i = ItemMetalArmor.createBoots(m);
		i.setUnlocalizedName(ModernMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		registerItem(i, regName, m);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}
	private static Item create_door(MetalMaterial m,BlockDoor door){
		String n = "door";
		Item i = new ItemMetalDoor(door,m);
		i.setUnlocalizedName(ModernMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n+"_item";
		registerItem(i, regName, m);
		doorMap.put(door, i);
		i.setCreativeTab(ItemGroups.tab_blocks);
		return i;
	}

	public static int getSortingValue(ItemStack a){
		int classVal = 990000;
		int metalVal = 9900;
		if(a.getItem() instanceof ItemBlock && ((ItemBlock)a.getItem()).getBlock() instanceof IMetalObject){
			classVal = classSortingValues.computeIfAbsent(((ItemBlock)a.getItem()).getBlock().getClass(),
					(@SuppressWarnings("rawtypes") Class c)->990000);
			metalVal = materialSortingValues.computeIfAbsent(((IMetalObject)((ItemBlock)a.getItem()).getBlock()).getMetalMaterial(),
					(MetalMaterial m)->9900);
		} else if(a.getItem() instanceof IMetalObject){
			classVal = classSortingValues.computeIfAbsent(a.getItem().getClass(),
					(@SuppressWarnings("rawtypes") Class c)->990000);
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

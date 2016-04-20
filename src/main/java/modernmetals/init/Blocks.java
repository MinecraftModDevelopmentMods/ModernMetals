package modernmetals.init;

import java.util.HashMap;
import java.util.Map;

import modernmetals.ModernMetals;
import cyano.basemetals.blocks.*;

import modernmetals.init.ItemGroups;
import modernmetals.init.Materials;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

/**
 * This class initializes all blocks in Modern Metals and provides some utility 
 * methods for looking up blocks. 
 * @author DrCyano
 *
 */
public abstract class Blocks {
//	private static Map<Block,String> blockRegistry = new HashMap<>();
	private static final Map<String,Block> allBlocks = new HashMap<>();
//	private static Map<MetalMaterial,List<Block>> blocksByMetal = new HashMap<>();

	/**
	 * Gets an block by its name. The name is the name as it is registered in 
	 * the GameRegistry, not its unlocalized name (the unlocalized name is the 
	 * registered name plus the prefix "modernmetals.")
	 * @param name The name of the block in question
	 * @return The block matching that name, or null if there isn't one
	 */
	public static Block getBlockByName(String name) {
		return allBlocks.get(name);
	}

	public static Block aluminum_bars;
	public static Block aluminum_block;
	public static Block aluminum_plate;
	public static BlockDoor aluminum_door;
	public static Block aluminum_ore;
	public static Block aluminum_trapdoor;

	public static Block aluminumbrass_bars;
	public static Block aluminumbrass_block;
	public static Block aluminumbrass_plate;
	public static BlockDoor aluminumbrass_door;
//	public static Block aluminumbrass_ore;
	public static Block aluminumbrass_trapdoor;

	public static Block cupronickel_bars;
	public static Block cupronickel_block;
	public static Block cupronickel_plate;
	public static BlockDoor cupronickel_door;
//	public static Block cupronickel_ore;
	public static Block cupronickel_trapdoor;
	
	public static Block magnesium_bars;
	public static Block magnesium_block;
	public static Block magnesium_plate;
	public static BlockDoor magnesium_door;
	public static Block magnesium_ore;
	public static Block magnesium_trapdoor;

	public static Block osmium_bars;
	public static Block osmium_block;
	public static Block osmium_plate;
	public static BlockDoor osmium_door;
	public static Block osmium_ore;
	public static Block osmium_trapdoor;

	public static Block platinum_bars;
	public static Block platinum_block;
	public static Block platinum_plate;
	public static BlockDoor platinum_door;
	public static Block platinum_ore;
	public static Block platinum_trapdoor;

	public static Block rutile_bars;
	public static Block rutile_block;
	public static Block rutile_plate;
	public static BlockDoor rutile_door;
	public static Block rutile_ore;
	public static Block rutile_trapdoor;
	
	public static Block titanium_bars;
	public static Block titanium_block;
	public static Block titanium_plate;
	public static BlockDoor titanium_door;
	public static Block titanium_ore;
	public static Block titanium_trapdoor;

	public static Block tungsten_bars;
	public static Block tungsten_block;
	public static Block tungsten_plate;
	public static BlockDoor tungsten_door;
	public static Block tungsten_ore;
	public static Block tungsten_trapdoor;
	
	public static Block uranium_bars;
	public static Block uranium_block;
	public static Block uranium_plate;
	public static BlockDoor uranium_door;
	public static Block uranium_ore;
	public static Block uranium_trapdoor;

	private static boolean initDone = false;
	public static void init(){
		if(initDone)return;

		modernmetals.init.Materials.init();
		modernmetals.init.ItemGroups.init();

		
		aluminum_block = createBlock(Materials.aluminum);
		aluminum_plate = createPlate(Materials.aluminum);
		aluminum_ore = createOre(Materials.aluminum);
		aluminum_bars = createBars(Materials.aluminum);
		aluminum_door = createDoor(Materials.aluminum);
		aluminum_trapdoor = createTrapDoor(Materials.aluminum);

		aluminumbrass_block = createBlock(Materials.aluminumbrass);
		aluminumbrass_plate = createPlate(Materials.aluminumbrass);
//		aluminumbrass_ore = createOre(Materials.aluminumbrass);
		aluminumbrass_bars = createBars(Materials.aluminumbrass);
		aluminumbrass_door = createDoor(Materials.aluminumbrass);
		aluminumbrass_trapdoor = createTrapDoor(Materials.aluminumbrass);

		cupronickel_block = createBlock(Materials.cupronickel);
		cupronickel_plate = createPlate(Materials.cupronickel);
//		cupronickel_ore = createOre(Materials.cupronickel);
		cupronickel_bars = createBars(Materials.cupronickel);
		cupronickel_door = createDoor(Materials.cupronickel);
		cupronickel_trapdoor = createTrapDoor(Materials.cupronickel);

		magnesium_block = createBlock(Materials.magnesium);
		magnesium_plate = createPlate(Materials.magnesium);
		magnesium_ore = createOre(Materials.magnesium);
		magnesium_bars = createBars(Materials.magnesium);
		magnesium_door = createDoor(Materials.magnesium);
		magnesium_trapdoor = createTrapDoor(Materials.magnesium);

		osmium_block = createBlock(Materials.osmium);
		osmium_plate = createPlate(Materials.osmium);
		osmium_ore = createOre(Materials.osmium);
		osmium_bars = createBars(Materials.osmium);
		osmium_door = createDoor(Materials.osmium);
		osmium_trapdoor = createTrapDoor(Materials.osmium);

		platinum_block = createBlock(Materials.platinum);
		platinum_plate = createPlate(Materials.platinum);
		platinum_ore = createOre(Materials.platinum);
		platinum_bars = createBars(Materials.platinum);
		platinum_door = createDoor(Materials.platinum);
		platinum_trapdoor = createTrapDoor(Materials.platinum);

		rutile_block = createBlock(Materials.rutile);
		rutile_plate = createPlate(Materials.rutile);
		rutile_ore = createOre(Materials.rutile);
		rutile_bars = createBars(Materials.rutile);
		rutile_door = createDoor(Materials.rutile);
		rutile_trapdoor = createTrapDoor(Materials.rutile);

		titanium_block = createBlock(Materials.titanium);
		titanium_plate = createPlate(Materials.titanium);
		titanium_ore = createOre(Materials.titanium);
		titanium_bars = createBars(Materials.titanium);
		titanium_door = createDoor(Materials.titanium);
		titanium_trapdoor = createTrapDoor(Materials.titanium);

		tungsten_block = createBlock(Materials.tungsten);
		tungsten_plate = createPlate(Materials.tungsten);
		tungsten_ore = createOre(Materials.tungsten);
		tungsten_bars = createBars(Materials.tungsten);
		tungsten_door = createDoor(Materials.tungsten);
		tungsten_trapdoor = createTrapDoor(Materials.tungsten);

		uranium_block = createBlock(Materials.uranium);
		uranium_plate = createPlate(Materials.uranium);
		uranium_ore = createOre(Materials.uranium);
		uranium_bars = createBars(Materials.uranium);
		uranium_door = createDoor(Materials.uranium);
		uranium_trapdoor = createTrapDoor(Materials.uranium);

		// final block settings
		for(Block b : allBlocks.values()){
			if(b instanceof IOreDictionaryEntry){OreDictionary.registerOre(((IOreDictionaryEntry)b).getOreDictionaryName(), b);}
			if(b instanceof BlockMetalDoor == false) b.setCreativeTab(ItemGroups.tab_blocks);
		}
		
		initDone = true;
	}

	private static Block createPlate(MetalMaterial metal) {
		Block block = new BlockMetalPlate(metal);
//		block.setRegistryName(ModernMetals.MODID+":"+metal.getName()+"_plate");
		block.setRegistryName(ModernMetals.MODID, metal.getName()+"_plate");
//		block.setUnlocalizedName(block.getRegistryName().toString());
		block.setUnlocalizedName(ModernMetals.MODID+"."+metal.getName()+"_plate");
//		GameRegistry.registerBlock(block, metal.getName()+"_plate");
		GameRegistry.register(block); 

		ItemBlock blockItem = new ItemBlock(block);
		blockItem.setRegistryName(ModernMetals.MODID, metal.getName()+"_plate");
		GameRegistry.register(blockItem);

		allBlocks.put(metal.getName()+"_plate", block);
		return block;
	}

	private static Block createBars(MetalMaterial metal){
		Block block = new BlockMetalBars(metal);
//		block.setRegistryName(ModernMetals.MODID+":"+metal.getName()+"_bars");
		block.setRegistryName(ModernMetals.MODID, metal.getName()+"_bars");
		block.setUnlocalizedName(ModernMetals.MODID+"."+metal.getName()+"_bars");
//		GameRegistry.registerBlock(block, metal.getName()+"_bars");
		GameRegistry.register(block); 

		ItemBlock blockItem = new ItemBlock(block);
		blockItem.setRegistryName(ModernMetals.MODID, metal.getName()+"_bars");
		GameRegistry.register(blockItem);

		allBlocks.put(metal.getName()+"_bars", block);
		return block;
	}
	private static Block createBlock(MetalMaterial metal){
		return createBlock(metal,false);
	}
	private static Block createBlock(MetalMaterial metal, boolean glow){
		Block block = new BlockMetalBlock(metal,glow);
//		block.setRegistryName(ModernMetals.MODID+":"+metal.getName()+"_block");
		block.setRegistryName(ModernMetals.MODID, metal.getName()+"_block");
		block.setUnlocalizedName(ModernMetals.MODID+"."+metal.getName()+"_block");
//		GameRegistry.registerBlock(block, metal.getName()+"_block");
		GameRegistry.register(block); 

		ItemBlock blockItem = new ItemBlock(block);
		blockItem.setRegistryName(ModernMetals.MODID, metal.getName()+"_block");
		GameRegistry.register(blockItem);

		allBlocks.put(metal.getName()+"_block", block);
		return block;
	}
	private static Block createOre(MetalMaterial metal){
		Block block = new BlockMetalOre(metal);
//		block.setRegistryName(ModernMetals.MODID+":"+metal.getName()+"_ore");
		block.setRegistryName(ModernMetals.MODID, metal.getName()+"_ore");
		block.setUnlocalizedName(ModernMetals.MODID+"."+metal.getName()+"_ore");
//		GameRegistry.registerBlock(block, metal.getName()+"_ore");
		GameRegistry.register(block); 

		ItemBlock blockItem = new ItemBlock(block);
		blockItem.setRegistryName(ModernMetals.MODID, metal.getName()+"_ore");
		GameRegistry.register(blockItem);

		allBlocks.put(metal.getName()+"_ore", block);
		return block;
	}
	private static BlockDoor createDoor(MetalMaterial metal){
		BlockDoor block = new BlockMetalDoor(metal);
//		block.setRegistryName(ModernMetals.MODID+":"+metal.getName()+"_door");
		block.setRegistryName(ModernMetals.MODID, metal.getName()+"_door");
		block.setUnlocalizedName(ModernMetals.MODID+"."+metal.getName()+"_door");
		GameRegistry.register(block); 

		ItemBlock blockItem = new ItemBlock(block);
		blockItem.setRegistryName(ModernMetals.MODID, metal.getName()+"_door");
		GameRegistry.register(blockItem);
		
		allBlocks.put(metal.getName()+"_door", block);
		return block;
/*
		String n = "door";
		Block block = new BlockMetalDoor(metal);
		String regName = metal.getName()+"_"+n;
		registerBlock(block, regName, metal);
		return block;
*/
	}

	private static Block createTrapDoor(MetalMaterial metal){
		Block block = new BlockMetalTrapDoor(metal);
//		block.setRegistryName(ModernMetals.MODID+":"+metal.getName()+"_trapdoor");
		block.setRegistryName(ModernMetals.MODID, metal.getName()+"_trapdoor");
		block.setUnlocalizedName(ModernMetals.MODID+"."+metal.getName()+"_trapdoor");
//		GameRegistry.registerBlock(block, metal.getName()+"_trapdoor");
		GameRegistry.register(block); 

		ItemBlock blockItem = new ItemBlock(block);
		blockItem.setRegistryName(ModernMetals.MODID, metal.getName()+"_trapdoor");
		GameRegistry.register(blockItem);

		allBlocks.put(metal.getName()+"_trapdoor", block);
		return block;
	}

	
	@SideOnly(Side.CLIENT)
	public static void registerItemRenders(FMLInitializationEvent event){
		for(String name : allBlocks.keySet()){
			if(allBlocks.get(name) instanceof BlockDoor) continue;// do not add door blocks
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
			.register(net.minecraft.item.Item.getItemFromBlock(allBlocks.get(name)), 0, 
				new ModelResourceLocation(ModernMetals.MODID+":"+name, "inventory"));
		}
	}

}

package modernmetals.init;

import modernmetals.ModernMetals;
import cyano.basemetals.blocks.*;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

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
	public static Block aluminumbrass_trapdoor;

	public static Block cadmium_bars;
	public static Block cadmium_block;
	public static Block cadmium_plate;
	public static BlockDoor cadmium_door;
	public static Block cadmium_ore;
	public static Block cadmium_trapdoor;
	
	public static Block chromium_bars;
	public static Block chromium_block;
	public static Block chromium_plate;
	public static BlockDoor chromium_door;
	public static Block chromium_ore;
	public static Block chromium_trapdoor;
		
	public static Block galvanizedsteel_bars;
	public static Block galvanizedsteel_block;
	public static Block galvanizedsteel_plate;
	public static BlockDoor galvanizedsteel_door;
	public static Block galvanizedsteel_ore;
	public static Block galvanizedsteel_trapdoor;

	public static Block iridium_bars;
	public static Block iridium_block;
	public static Block iridium_plate;
	public static BlockDoor iridium_door;
	public static Block iridium_ore;
	public static Block iridium_trapdoor;
	
	public static Block magnesium_bars;
	public static Block magnesium_block;
	public static Block magnesium_plate;
	public static BlockDoor magnesium_door;
	public static Block magnesium_ore;
	public static Block magnesium_trapdoor;
	
	public static Block manganese_bars;
	public static Block manganese_block;
	public static Block manganese_plate;
	public static BlockDoor manganese_door;
	public static Block manganese_ore;
	public static Block manganese_trapdoor;
	
	public static Block nichrome_bars;
	public static Block nichrome_block;
	public static Block nichrome_plate;
	public static BlockDoor nichrome_door;
	public static Block nichrome_ore;
	public static Block nichrome_trapdoor;

	public static Block osmium_bars;
	public static Block osmium_block;
	public static Block osmium_plate;
	public static BlockDoor osmium_door;
	public static Block osmium_ore;
	public static Block osmium_trapdoor;

	public static Block plutonium_bars;
	public static Block plutonium_block;
	public static Block plutonium_plate;
	public static BlockDoor plutonium_door;
	public static Block plutonium_ore;
	public static Block plutonium_trapdoor;
	
	public static Block rutile_bars;
	public static Block rutile_block;
	public static Block rutile_plate;
	public static BlockDoor rutile_door;
	public static Block rutile_ore;
	public static Block rutile_trapdoor;

	public static Block stainlesssteel_bars;
	public static Block stainlesssteel_block;
	public static Block stainlesssteel_plate;
	public static BlockDoor stainlesssteel_door;
	public static Block stainlesssteel_ore;
	public static Block stainlesssteel_trapdoor;

	public static Block tantalum_bars;
	public static Block tantalum_block;
	public static Block tantalum_plate;
	public static BlockDoor tantalum_door;
	public static Block tantalum_ore;
	public static Block tantalum_trapdoor;
	
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
	
	public static Block zirconium_bars;
	public static Block zirconium_block;
	public static Block zirconium_plate;
	public static BlockDoor zirconium_door;
	public static Block zirconium_ore;
	public static Block zirconium_trapdoor;
	
	private static boolean initDone = false;
	public static void init() {
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
		aluminumbrass_bars = createBars(Materials.aluminumbrass);
		aluminumbrass_door = createDoor(Materials.aluminumbrass);
		aluminumbrass_trapdoor = createTrapDoor(Materials.aluminumbrass);

		cadmium_block = createBlock(Materials.cadmium);
		cadmium_plate = createPlate(Materials.cadmium);
		cadmium_ore = createOre(Materials.cadmium);
		cadmium_bars = createBars(Materials.cadmium);
		cadmium_door = createDoor(Materials.cadmium);
		cadmium_trapdoor = createTrapDoor(Materials.cadmium);

		chromium_block = createBlock(Materials.chromium);
		chromium_plate = createPlate(Materials.chromium);
		chromium_ore = createOre(Materials.chromium);
		chromium_bars = createBars(Materials.chromium);
		chromium_door = createDoor(Materials.chromium);
		chromium_trapdoor = createTrapDoor(Materials.chromium);

		galvanizedsteel_block = createBlock(Materials.galvanizedsteel);
		galvanizedsteel_plate = createPlate(Materials.galvanizedsteel);
		galvanizedsteel_ore = createOre(Materials.galvanizedsteel);
		galvanizedsteel_bars = createBars(Materials.galvanizedsteel);
		galvanizedsteel_door = createDoor(Materials.galvanizedsteel);
		galvanizedsteel_trapdoor = createTrapDoor(Materials.galvanizedsteel);

		iridium_block = createBlock(Materials.iridium);
		iridium_plate = createPlate(Materials.iridium);
		iridium_ore = createOre(Materials.iridium);
		iridium_bars = createBars(Materials.iridium);
		iridium_door = createDoor(Materials.iridium);
		iridium_trapdoor = createTrapDoor(Materials.iridium);

		magnesium_block = createBlock(Materials.magnesium);
		magnesium_plate = createPlate(Materials.magnesium);
		magnesium_ore = createOre(Materials.magnesium);
		magnesium_bars = createBars(Materials.magnesium);
		magnesium_door = createDoor(Materials.magnesium);
		magnesium_trapdoor = createTrapDoor(Materials.magnesium);

		manganese_block = createBlock(Materials.manganese);
		manganese_plate = createPlate(Materials.manganese);
		manganese_ore = createOre(Materials.manganese);
		manganese_bars = createBars(Materials.manganese);
		manganese_door = createDoor(Materials.manganese);
		manganese_trapdoor = createTrapDoor(Materials.manganese);

		nichrome_block = createBlock(Materials.nichrome);
		nichrome_plate = createPlate(Materials.nichrome);
		nichrome_ore = createOre(Materials.nichrome);
		nichrome_bars = createBars(Materials.nichrome);
		nichrome_door = createDoor(Materials.nichrome);
		nichrome_trapdoor = createTrapDoor(Materials.nichrome);

		osmium_block = createBlock(Materials.osmium);
		osmium_plate = createPlate(Materials.osmium);
		osmium_ore = createOre(Materials.osmium);
		osmium_bars = createBars(Materials.osmium);
		osmium_door = createDoor(Materials.osmium);
		osmium_trapdoor = createTrapDoor(Materials.osmium);

		plutonium_block = createBlock(Materials.plutonium);
		plutonium_plate = createPlate(Materials.plutonium);
		plutonium_ore = createOre(Materials.plutonium);
		plutonium_bars = createBars(Materials.plutonium);
		plutonium_door = createDoor(Materials.plutonium);
		plutonium_trapdoor = createTrapDoor(Materials.plutonium);

		rutile_block = createBlock(Materials.rutile);
		rutile_plate = createPlate(Materials.rutile);
		rutile_ore = createOre(Materials.rutile);
		rutile_bars = createBars(Materials.rutile);
		rutile_door = createDoor(Materials.rutile);
		rutile_trapdoor = createTrapDoor(Materials.rutile);

		stainlesssteel_block = createBlock(Materials.stainlesssteel);
		stainlesssteel_plate = createPlate(Materials.stainlesssteel);
		stainlesssteel_ore = createOre(Materials.stainlesssteel);
		stainlesssteel_bars = createBars(Materials.stainlesssteel);
		stainlesssteel_door = createDoor(Materials.stainlesssteel);
		stainlesssteel_trapdoor = createTrapDoor(Materials.stainlesssteel);

		tantalum_block = createBlock(Materials.tantalum);
		tantalum_plate = createPlate(Materials.tantalum);
		tantalum_ore = createOre(Materials.tantalum);
		tantalum_bars = createBars(Materials.tantalum);
		tantalum_door = createDoor(Materials.tantalum);
		tantalum_trapdoor = createTrapDoor(Materials.tantalum);

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

		zirconium_block = createBlock(Materials.zirconium);
		zirconium_plate = createPlate(Materials.zirconium);
		zirconium_ore = createOre(Materials.zirconium);
		zirconium_bars = createBars(Materials.zirconium);
		zirconium_door = createDoor(Materials.zirconium);
		zirconium_trapdoor = createTrapDoor(Materials.zirconium);

		// final block settings
		for(Block b : allBlocks.values()) {
			if(b instanceof IOreDictionaryEntry) { OreDictionary.registerOre(((IOreDictionaryEntry)b).getOreDictionaryName(), b); }
			if(!(b instanceof BlockMetalDoor)) b.setCreativeTab(ItemGroups.tab_blocks);
		}
		
		initDone = true;
	}

	private static Block addBlock(Block block, String name) {
		ResourceLocation location = new ResourceLocation(ModernMetals.MODID, name);
		block.setRegistryName(location);
		block.setUnlocalizedName(location.toString());
		GameRegistry.register(block);

		if (!(block instanceof BlockMetalDoor)) {
			ItemBlock itemBlock = new ItemBlock(block);
			itemBlock.setRegistryName(location);
			itemBlock.setUnlocalizedName(location.toString());
			GameRegistry.register(itemBlock);
		}

		allBlocks.put(name, block);
		return block;
	}

	private static Block createPlate(MetalMaterial metal) {
		return addBlock(new BlockMetalPlate(metal), metal.getName()+"_plate");
	}

	private static Block createBars(MetalMaterial metal) {
		return addBlock(new BlockMetalBars(metal), metal.getName()+"_bars");
	}

	private static Block createBlock(MetalMaterial metal) {
		return createBlock(metal, false);
	}

	private static Block createBlock(MetalMaterial metal, boolean glow) {
		return addBlock(new BlockMetalBlock(metal, glow), metal.getName()+"_block");
	}

	private static Block createOre(MetalMaterial metal) {
		return addBlock(new BlockMetalOre(metal), metal.getName()+"_ore");
	}

	private static BlockDoor createDoor(MetalMaterial metal) {
		return (BlockDoor)addBlock(new modernmetals.blocks.BlockMetalDoor(metal), metal.getName()+"_door");
	}

	private static Block createTrapDoor(MetalMaterial metal) {
		return addBlock(new BlockMetalTrapDoor(metal), metal.getName()+"_trapdoor");
	}
	

	@SideOnly(Side.CLIENT)
	public static void registerItemRenders(FMLInitializationEvent event) {
		for(String name : allBlocks.keySet()) {
			if(allBlocks.get(name) instanceof BlockDoor) continue; // do not add door blocks
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
			.register(net.minecraft.item.Item.getItemFromBlock(allBlocks.get(name)), 0, 
				new ModelResourceLocation(new ResourceLocation(ModernMetals.MODID, name), "inventory"));
		}
	}
}

package modernmetals.init;

import modernmetals.ModernMetals;
import cyano.basemetals.blocks.*;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockSlab;
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
 *
 * @author DrCyano
 *
 */
public abstract class Blocks {

	public static Block aluminum_bars;
	public static Block aluminum_block;
	public static Block aluminum_plate;
	public static BlockDoor aluminum_door;
	public static Block aluminum_ore;
	public static Block aluminum_trapdoor;

	public static Block aluminum_button;
	public static BlockSlab aluminum_slab;
	public static BlockSlab double_aluminum_slab;
	public static Block aluminum_lever;
	public static Block aluminum_pressure_plate;
	public static Block aluminum_stairs;
	public static Block aluminum_wall;

	public static Block aluminumbrass_bars;
	public static Block aluminumbrass_block;
	public static Block aluminumbrass_plate;
	public static BlockDoor aluminumbrass_door;
	public static Block aluminumbrass_trapdoor;

	public static Block aluminumbrass_button;
	public static BlockSlab aluminumbrass_slab;
	public static BlockSlab double_aluminumbrass_slab;
	public static Block aluminumbrass_lever;
	public static Block aluminumbrass_pressure_plate;
	public static Block aluminumbrass_stairs;
	public static Block aluminumbrass_wall;

	public static Block cadmium_bars;
	public static Block cadmium_block;
	public static Block cadmium_plate;
	public static BlockDoor cadmium_door;
	public static Block cadmium_ore;
	public static Block cadmium_trapdoor;

	public static Block cadmium_button;
	public static BlockSlab cadmium_slab;
	public static BlockSlab double_cadmium_slab;
	public static Block cadmium_lever;
	public static Block cadmium_pressure_plate;
	public static Block cadmium_stairs;
	public static Block cadmium_wall;

	public static Block chromium_bars;
	public static Block chromium_block;
	public static Block chromium_plate;
	public static BlockDoor chromium_door;
	public static Block chromium_ore;
	public static Block chromium_trapdoor;

	public static Block chromium_button;
	public static BlockSlab chromium_slab;
	public static BlockSlab double_chromium_slab;
	public static Block chromium_lever;
	public static Block chromium_pressure_plate;
	public static Block chromium_stairs;
	public static Block chromium_wall;

	public static Block galvanizedsteel_bars;
	public static Block galvanizedsteel_block;
	public static Block galvanizedsteel_plate;
	public static BlockDoor galvanizedsteel_door;
	public static Block galvanizedsteel_trapdoor;

	public static Block galvanizedsteel_button;
	public static BlockSlab galvanizedsteel_slab;
	public static BlockSlab double_galvanizedsteel_slab;
	public static Block galvanizedsteel_lever;
	public static Block galvanizedsteel_pressure_plate;
	public static Block galvanizedsteel_stairs;
	public static Block galvanizedsteel_wall;

	public static Block iridium_bars;
	public static Block iridium_block;
	public static Block iridium_plate;
	public static BlockDoor iridium_door;
	public static Block iridium_ore;
	public static Block iridium_trapdoor;

	public static Block iridium_button;
	public static BlockSlab iridium_slab;
	public static BlockSlab double_iridium_slab;
	public static Block iridium_lever;
	public static Block iridium_pressure_plate;
	public static Block iridium_stairs;
	public static Block iridium_wall;

	public static Block magnesium_bars;
	public static Block magnesium_block;
	public static Block magnesium_plate;
	public static BlockDoor magnesium_door;
	public static Block magnesium_ore;
	public static Block magnesium_trapdoor;

	public static Block magnesium_button;
	public static BlockSlab magnesium_slab;
	public static BlockSlab double_magnesium_slab;
	public static Block magnesium_lever;
	public static Block magnesium_pressure_plate;
	public static Block magnesium_stairs;
	public static Block magnesium_wall;

	public static Block manganese_bars;
	public static Block manganese_block;
	public static Block manganese_plate;
	public static BlockDoor manganese_door;
	public static Block manganese_ore;
	public static Block manganese_trapdoor;

	public static Block manganese_button;
	public static BlockSlab manganese_slab;
	public static BlockSlab double_manganese_slab;
	public static Block manganese_lever;
	public static Block manganese_pressure_plate;
	public static Block manganese_stairs;
	public static Block manganese_wall;

	public static Block nichrome_bars;
	public static Block nichrome_block;
	public static Block nichrome_plate;
	public static BlockDoor nichrome_door;
	public static Block nichrome_trapdoor;

	public static Block nichrome_button;
	public static BlockSlab nichrome_slab;
	public static BlockSlab double_nichrome_slab;
	public static Block nichrome_lever;
	public static Block nichrome_pressure_plate;
	public static Block nichrome_stairs;
	public static Block nichrome_wall;

	public static Block osmium_bars;
	public static Block osmium_block;
	public static Block osmium_plate;
	public static BlockDoor osmium_door;
	public static Block osmium_ore;
	public static Block osmium_trapdoor;

	public static Block osmium_button;
	public static BlockSlab osmium_slab;
	public static BlockSlab double_osmium_slab;
	public static Block osmium_lever;
	public static Block osmium_pressure_plate;
	public static Block osmium_stairs;
	public static Block osmium_wall;

	public static Block plutonium_bars;
	public static Block plutonium_block;
	public static Block plutonium_plate;
	public static BlockDoor plutonium_door;
	public static Block plutonium_ore;
	public static Block plutonium_trapdoor;

	public static Block plutonium_button;
	public static BlockSlab plutonium_slab;
	public static BlockSlab double_plutonium_slab;
	public static Block plutonium_lever;
	public static Block plutonium_pressure_plate;
	public static Block plutonium_stairs;
	public static Block plutonium_wall;

	public static Block rutile_bars;
	public static Block rutile_block;
	public static Block rutile_plate;
	public static BlockDoor rutile_door;
	public static Block rutile_ore;
	public static Block rutile_trapdoor;

	public static Block rutile_button;
	public static BlockSlab rutile_slab;
	public static BlockSlab double_rutile_slab;
	public static Block rutile_lever;
	public static Block rutile_pressure_plate;
	public static Block rutile_stairs;
	public static Block rutile_wall;

	public static Block stainlesssteel_bars;
	public static Block stainlesssteel_block;
	public static Block stainlesssteel_plate;
	public static BlockDoor stainlesssteel_door;
	public static Block stainlesssteel_trapdoor;

	public static Block stainlesssteel_button;
	public static BlockSlab stainlesssteel_slab;
	public static BlockSlab double_stainlesssteel_slab;
	public static Block stainlesssteel_lever;
	public static Block stainlesssteel_pressure_plate;
	public static Block stainlesssteel_stairs;
	public static Block stainlesssteel_wall;

	public static Block tantalum_bars;
	public static Block tantalum_block;
	public static Block tantalum_plate;
	public static BlockDoor tantalum_door;
	public static Block tantalum_ore;
	public static Block tantalum_trapdoor;

	public static Block tantalum_button;
	public static BlockSlab tantalum_slab;
	public static BlockSlab double_tantalum_slab;
	public static Block tantalum_lever;
	public static Block tantalum_pressure_plate;
	public static Block tantalum_stairs;
	public static Block tantalum_wall;

	public static Block titanium_bars;
	public static Block titanium_block;
	public static Block titanium_plate;
	public static BlockDoor titanium_door;
	public static Block titanium_ore;
	public static Block titanium_trapdoor;

	public static Block titanium_button;
	public static BlockSlab titanium_slab;
	public static BlockSlab double_titanium_slab;
	public static Block titanium_lever;
	public static Block titanium_pressure_plate;
	public static Block titanium_stairs;
	public static Block titanium_wall;

	public static Block tungsten_bars;
	public static Block tungsten_block;
	public static Block tungsten_plate;
	public static BlockDoor tungsten_door;
	public static Block tungsten_ore;
	public static Block tungsten_trapdoor;

	public static Block tungsten_button;
	public static BlockSlab tungsten_slab;
	public static BlockSlab double_tungsten_slab;
	public static Block tungsten_lever;
	public static Block tungsten_pressure_plate;
	public static Block tungsten_stairs;
	public static Block tungsten_wall;

	public static Block uranium_bars;
	public static Block uranium_block;
	public static Block uranium_plate;
	public static BlockDoor uranium_door;
	public static Block uranium_ore;
	public static Block uranium_trapdoor;

	public static Block uranium_button;
	public static BlockSlab uranium_slab;
	public static BlockSlab double_uranium_slab;
	public static Block uranium_lever;
	public static Block uranium_pressure_plate;
	public static Block uranium_stairs;
	public static Block uranium_wall;

	public static Block zirconium_bars;
	public static Block zirconium_block;
	public static Block zirconium_plate;
	public static BlockDoor zirconium_door;
	public static Block zirconium_ore;
	public static Block zirconium_trapdoor;

	public static Block zirconium_button;
	public static BlockSlab zirconium_slab;
	public static BlockSlab double_zirconium_slab;
	public static Block zirconium_lever;
	public static Block zirconium_pressure_plate;
	public static Block zirconium_stairs;
	public static Block zirconium_wall;

	private static boolean initDone = false;

	// private static Map<Block, String> blockRegistry = new HashMap<>();
	private static final Map<String, Block> blockRegistry = new HashMap<>();
	// private static Map<MetalMaterial, List<Block>> blocksByMetal = new HashMap<>();

	/**
	 * Gets an block by its name. The name is the name as it is registered in
	 * the GameRegistry, not its unlocalized name (the unlocalized name is the
	 * registered name plus the prefix "modernmetals.")
	 * @param name The name of the block in question
	 * @return The block matching that name, or null if there isn't one
	 */
	public static Block getBlockByName(String name) {
		return blockRegistry.get(name);
	}

	/**
	 *
	 */
	public static void init() {
		if(initDone)
			return;

		modernmetals.init.Materials.init();
		modernmetals.init.ItemGroups.init();

		aluminum_block = createBlock(Materials.aluminum);
		aluminum_plate = createPlate(Materials.aluminum);
		aluminum_ore = createOre(Materials.aluminum);
		aluminum_bars = createBars(Materials.aluminum);
		aluminum_door = createDoor(Materials.aluminum);
		aluminum_trapdoor = createTrapDoor(Materials.aluminum);

		aluminum_button = createButton(Materials.aluminum);
		aluminum_slab = createSlab(Materials.aluminum);
		double_aluminum_slab = createDoubleSlab(Materials.aluminum);
		aluminum_lever = createLever(Materials.aluminum);
		aluminum_pressure_plate = createPressurePlate(Materials.aluminum);
		aluminum_stairs = createStairs(Materials.aluminum);
		aluminum_wall = createWall(Materials.aluminum);

		aluminumbrass_block = createBlock(Materials.aluminumbrass);
		aluminumbrass_plate = createPlate(Materials.aluminumbrass);
		aluminumbrass_bars = createBars(Materials.aluminumbrass);
		aluminumbrass_door = createDoor(Materials.aluminumbrass);
		aluminumbrass_trapdoor = createTrapDoor(Materials.aluminumbrass);

		aluminumbrass_button = createButton(Materials.aluminumbrass);
		aluminumbrass_slab = createSlab(Materials.aluminumbrass);
		double_aluminumbrass_slab = createDoubleSlab(Materials.aluminumbrass);
		aluminumbrass_lever = createLever(Materials.aluminumbrass);
		aluminumbrass_pressure_plate = createPressurePlate(Materials.aluminumbrass);
		aluminumbrass_stairs = createStairs(Materials.aluminumbrass);
		aluminumbrass_wall = createWall(Materials.aluminumbrass);

		cadmium_block = createBlock(Materials.cadmium);
		cadmium_plate = createPlate(Materials.cadmium);
		cadmium_ore = createOre(Materials.cadmium);
		cadmium_bars = createBars(Materials.cadmium);
		cadmium_door = createDoor(Materials.cadmium);
		cadmium_trapdoor = createTrapDoor(Materials.cadmium);

		cadmium_button = createButton(Materials.cadmium);
		cadmium_slab = createSlab(Materials.cadmium);
		double_cadmium_slab = createDoubleSlab(Materials.cadmium);
		cadmium_lever = createLever(Materials.cadmium);
		cadmium_pressure_plate = createPressurePlate(Materials.cadmium);
		cadmium_stairs = createStairs(Materials.cadmium);
		cadmium_wall = createWall(Materials.cadmium);

		chromium_block = createBlock(Materials.chromium);
		chromium_plate = createPlate(Materials.chromium);
		chromium_ore = createOre(Materials.chromium);
		chromium_bars = createBars(Materials.chromium);
		chromium_door = createDoor(Materials.chromium);
		chromium_trapdoor = createTrapDoor(Materials.chromium);

		chromium_button = createButton(Materials.chromium);
		chromium_slab = createSlab(Materials.chromium);
		double_chromium_slab = createDoubleSlab(Materials.chromium);
		chromium_lever = createLever(Materials.chromium);
		chromium_pressure_plate = createPressurePlate(Materials.chromium);
		chromium_stairs = createStairs(Materials.chromium);
		chromium_wall = createWall(Materials.chromium);

		galvanizedsteel_block = createBlock(Materials.galvanizedsteel);
		galvanizedsteel_plate = createPlate(Materials.galvanizedsteel);
		galvanizedsteel_bars = createBars(Materials.galvanizedsteel);
		galvanizedsteel_door = createDoor(Materials.galvanizedsteel);
		galvanizedsteel_trapdoor = createTrapDoor(Materials.galvanizedsteel);

		galvanizedsteel_button = createButton(Materials.galvanizedsteel);
		galvanizedsteel_slab = createSlab(Materials.galvanizedsteel);
		double_galvanizedsteel_slab = createDoubleSlab(Materials.galvanizedsteel);
		galvanizedsteel_lever = createLever(Materials.galvanizedsteel);
		galvanizedsteel_pressure_plate = createPressurePlate(Materials.galvanizedsteel);
		galvanizedsteel_stairs = createStairs(Materials.galvanizedsteel);
		galvanizedsteel_wall = createWall(Materials.galvanizedsteel);

		iridium_block = createBlock(Materials.iridium);
		iridium_plate = createPlate(Materials.iridium);
		iridium_ore = createOre(Materials.iridium);
		iridium_bars = createBars(Materials.iridium);
		iridium_door = createDoor(Materials.iridium);
		iridium_trapdoor = createTrapDoor(Materials.iridium);

		iridium_button = createButton(Materials.iridium);
		iridium_slab = createSlab(Materials.iridium);
		double_iridium_slab = createDoubleSlab(Materials.iridium);
		iridium_lever = createLever(Materials.iridium);
		iridium_pressure_plate = createPressurePlate(Materials.iridium);
		iridium_stairs = createStairs(Materials.iridium);
		iridium_wall = createWall(Materials.iridium);

		magnesium_block = createBlock(Materials.magnesium);
		magnesium_plate = createPlate(Materials.magnesium);
		magnesium_ore = createOre(Materials.magnesium);
		magnesium_bars = createBars(Materials.magnesium);
		magnesium_door = createDoor(Materials.magnesium);
		magnesium_trapdoor = createTrapDoor(Materials.magnesium);

		magnesium_button = createButton(Materials.magnesium);
		magnesium_slab = createSlab(Materials.magnesium);
		double_magnesium_slab = createDoubleSlab(Materials.magnesium);
		magnesium_lever = createLever(Materials.magnesium);
		magnesium_pressure_plate = createPressurePlate(Materials.magnesium);
		magnesium_stairs = createStairs(Materials.magnesium);
		magnesium_wall = createWall(Materials.magnesium);

		manganese_block = createBlock(Materials.manganese);
		manganese_plate = createPlate(Materials.manganese);
		manganese_ore = createOre(Materials.manganese);
		manganese_bars = createBars(Materials.manganese);
		manganese_door = createDoor(Materials.manganese);
		manganese_trapdoor = createTrapDoor(Materials.manganese);

		manganese_button = createButton(Materials.manganese);
		manganese_slab = createSlab(Materials.manganese);
		double_manganese_slab = createDoubleSlab(Materials.manganese);
		manganese_lever = createLever(Materials.manganese);
		manganese_pressure_plate = createPressurePlate(Materials.manganese);
		manganese_stairs = createStairs(Materials.manganese);
		manganese_wall = createWall(Materials.manganese);

		nichrome_block = createBlock(Materials.nichrome);
		nichrome_plate = createPlate(Materials.nichrome);
		nichrome_bars = createBars(Materials.nichrome);
		nichrome_door = createDoor(Materials.nichrome);
		nichrome_trapdoor = createTrapDoor(Materials.nichrome);

		nichrome_button = createButton(Materials.nichrome);
		nichrome_slab = createSlab(Materials.nichrome);
		double_nichrome_slab = createDoubleSlab(Materials.nichrome);
		nichrome_lever = createLever(Materials.nichrome);
		nichrome_pressure_plate = createPressurePlate(Materials.nichrome);
		nichrome_stairs = createStairs(Materials.nichrome);
		nichrome_wall = createWall(Materials.nichrome);

		osmium_block = createBlock(Materials.osmium);
		osmium_plate = createPlate(Materials.osmium);
		osmium_ore = createOre(Materials.osmium);
		osmium_bars = createBars(Materials.osmium);
		osmium_door = createDoor(Materials.osmium);
		osmium_trapdoor = createTrapDoor(Materials.osmium);

		osmium_button = createButton(Materials.osmium);
		osmium_slab = createSlab(Materials.osmium);
		double_osmium_slab = createDoubleSlab(Materials.osmium);
		osmium_lever = createLever(Materials.osmium);
		osmium_pressure_plate = createPressurePlate(Materials.osmium);
		osmium_stairs = createStairs(Materials.osmium);
		osmium_wall = createWall(Materials.osmium);

		plutonium_block = createBlock(Materials.plutonium);
		plutonium_plate = createPlate(Materials.plutonium);
		plutonium_ore = createOre(Materials.plutonium);
		plutonium_bars = createBars(Materials.plutonium);
		plutonium_door = createDoor(Materials.plutonium);
		plutonium_trapdoor = createTrapDoor(Materials.plutonium);

		plutonium_button = createButton(Materials.plutonium);
		plutonium_slab = createSlab(Materials.plutonium);
		double_plutonium_slab = createDoubleSlab(Materials.plutonium);
		plutonium_lever = createLever(Materials.plutonium);
		plutonium_pressure_plate = createPressurePlate(Materials.plutonium);
		plutonium_stairs = createStairs(Materials.plutonium);
		plutonium_wall = createWall(Materials.plutonium);

		rutile_block = createBlock(Materials.rutile);
		rutile_plate = createPlate(Materials.rutile);
		rutile_ore = createOre(Materials.rutile);
		rutile_bars = createBars(Materials.rutile);
		rutile_door = createDoor(Materials.rutile);
		rutile_trapdoor = createTrapDoor(Materials.rutile);

		rutile_button = createButton(Materials.rutile);
		rutile_slab = createSlab(Materials.rutile);
		double_rutile_slab = createDoubleSlab(Materials.rutile);
		rutile_lever = createLever(Materials.rutile);
		rutile_pressure_plate = createPressurePlate(Materials.rutile);
		rutile_stairs = createStairs(Materials.rutile);
		rutile_wall = createWall(Materials.rutile);

		stainlesssteel_block = createBlock(Materials.stainlesssteel);
		stainlesssteel_plate = createPlate(Materials.stainlesssteel);
		stainlesssteel_bars = createBars(Materials.stainlesssteel);
		stainlesssteel_door = createDoor(Materials.stainlesssteel);
		stainlesssteel_trapdoor = createTrapDoor(Materials.stainlesssteel);

		stainlesssteel_button = createButton(Materials.stainlesssteel);
		stainlesssteel_slab = createSlab(Materials.stainlesssteel);
		double_stainlesssteel_slab = createDoubleSlab(Materials.stainlesssteel);
		stainlesssteel_lever = createLever(Materials.stainlesssteel);
		stainlesssteel_pressure_plate = createPressurePlate(Materials.stainlesssteel);
		stainlesssteel_stairs = createStairs(Materials.stainlesssteel);
		stainlesssteel_wall = createWall(Materials.stainlesssteel);

		tantalum_block = createBlock(Materials.tantalum);
		tantalum_plate = createPlate(Materials.tantalum);
		tantalum_ore = createOre(Materials.tantalum);
		tantalum_bars = createBars(Materials.tantalum);
		tantalum_door = createDoor(Materials.tantalum);
		tantalum_trapdoor = createTrapDoor(Materials.tantalum);

		tantalum_button = createButton(Materials.tantalum);
		tantalum_slab = createSlab(Materials.tantalum);
		double_tantalum_slab = createDoubleSlab(Materials.tantalum);
		tantalum_lever = createLever(Materials.tantalum);
		tantalum_pressure_plate = createPressurePlate(Materials.tantalum);
		tantalum_stairs = createStairs(Materials.tantalum);
		tantalum_wall = createWall(Materials.tantalum);

		titanium_block = createBlock(Materials.titanium);
		titanium_plate = createPlate(Materials.titanium);
		titanium_ore = createOre(Materials.titanium);
		titanium_bars = createBars(Materials.titanium);
		titanium_door = createDoor(Materials.titanium);
		titanium_trapdoor = createTrapDoor(Materials.titanium);

		titanium_button = createButton(Materials.titanium);
		titanium_slab = createSlab(Materials.titanium);
		double_titanium_slab = createDoubleSlab(Materials.titanium);
		titanium_lever = createLever(Materials.titanium);
		titanium_pressure_plate = createPressurePlate(Materials.titanium);
		titanium_stairs = createStairs(Materials.titanium);
		titanium_wall = createWall(Materials.titanium);

		tungsten_block = createBlock(Materials.tungsten);
		tungsten_plate = createPlate(Materials.tungsten);
		tungsten_ore = createOre(Materials.tungsten);
		tungsten_bars = createBars(Materials.tungsten);
		tungsten_door = createDoor(Materials.tungsten);
		tungsten_trapdoor = createTrapDoor(Materials.tungsten);

		tungsten_button = createButton(Materials.tungsten);
		tungsten_slab = createSlab(Materials.tungsten);
		double_tungsten_slab = createDoubleSlab(Materials.tungsten);
		tungsten_lever = createLever(Materials.tungsten);
		tungsten_pressure_plate = createPressurePlate(Materials.tungsten);
		tungsten_stairs = createStairs(Materials.tungsten);
		tungsten_wall = createWall(Materials.tungsten);

		uranium_block = createBlock(Materials.uranium);
		uranium_plate = createPlate(Materials.uranium);
		uranium_ore = createOre(Materials.uranium);
		uranium_bars = createBars(Materials.uranium);
		uranium_door = createDoor(Materials.uranium);
		uranium_trapdoor = createTrapDoor(Materials.uranium);

		uranium_button = createButton(Materials.uranium);
		uranium_slab = createSlab(Materials.uranium);
		double_uranium_slab = createDoubleSlab(Materials.uranium);
		uranium_lever = createLever(Materials.uranium);
		uranium_pressure_plate = createPressurePlate(Materials.uranium);
		uranium_stairs = createStairs(Materials.uranium);
		uranium_wall = createWall(Materials.uranium);

		zirconium_block = createBlock(Materials.zirconium);
		zirconium_plate = createPlate(Materials.zirconium);
		zirconium_ore = createOre(Materials.zirconium);
		zirconium_bars = createBars(Materials.zirconium);
		zirconium_door = createDoor(Materials.zirconium);
		zirconium_trapdoor = createTrapDoor(Materials.zirconium);

		zirconium_button = createButton(Materials.zirconium);
		zirconium_slab = createSlab(Materials.zirconium);
		double_zirconium_slab = createDoubleSlab(Materials.zirconium);
		zirconium_lever = createLever(Materials.zirconium);
		zirconium_pressure_plate = createPressurePlate(Materials.zirconium);
		zirconium_stairs = createStairs(Materials.zirconium);
		zirconium_wall = createWall(Materials.zirconium);

		// TODO: Make this support multiple oredicts
		// final block settings
		for(final Block b : blockRegistry.values()) {
			if(b instanceof IOreDictionaryEntry)
				OreDictionary.registerOre(((IOreDictionaryEntry)b).getOreDictionaryName(), b);
			if(!(b instanceof BlockMetalDoor))
				b.setCreativeTab(ItemGroups.tab_blocks);
		}

		initDone = true;
	}

	private static Block addBlock(Block block, String name) {
		final ResourceLocation location = new ResourceLocation(ModernMetals.MODID, name);
		block.setRegistryName(location);
		block.setUnlocalizedName(location.toString());
		GameRegistry.register(block);

		if (!(block instanceof BlockMetalDoor) && !(block instanceof BlockMetalSlab)) {
			final ItemBlock itemBlock = new ItemBlock(block);
			itemBlock.setRegistryName(location);
			itemBlock.setUnlocalizedName(location.toString());
			GameRegistry.register(itemBlock);
		}

		blockRegistry.put(name, block);
		return block;
	}

	private static Block createPlate(MetalMaterial metal) {
		return addBlock(new BlockMetalPlate(metal), metal.getName() + "_plate");
	}

	private static Block createBars(MetalMaterial metal) {
		return addBlock(new BlockMetalBars(metal), metal.getName() + "_bars");
	}

	private static Block createBlock(MetalMaterial metal) {
		return createBlock(metal, false);
	}

	private static Block createBlock(MetalMaterial metal, boolean glow) {
		return addBlock(new BlockMetalBlock(metal, glow), metal.getName() + "_block");
	}

	private static Block createButton(MetalMaterial metal) {
		return addBlock(new BlockButtonMetal(metal), metal.getName() + "_button");
	}

	private static Block createLever(MetalMaterial metal) {
		return addBlock(new BlockMetalLever(metal), metal.getName() + "_lever");
	}

	private static Block createPressurePlate(MetalMaterial metal) {
		return addBlock(new BlockMetalPressurePlate(metal), metal.getName() + "_pressure_plate");
	}

	private static BlockSlab createSlab(MetalMaterial metal) {
		return (BlockSlab)addBlock(new BlockHalfMetalSlab(metal), metal.getName() + "_slab");
	}

	private static BlockSlab createDoubleSlab(MetalMaterial metal) {
		return (BlockSlab)addBlock(new BlockDoubleMetalSlab(metal), "double_" + metal.getName() + "_slab");
	}

	private static Block createStairs(MetalMaterial metal) {
		return addBlock(new BlockMetalStairs(metal, Blocks.getBlockByName(metal.getName() + "_block")), metal.getName() + "_stairs");
	}

	private static Block createWall(MetalMaterial metal) {
		return addBlock(new BlockMetalWall(metal, Blocks.getBlockByName(metal.getName() + "_block")), metal.getName() + "_wall");
	}

	private static Block createOre(MetalMaterial metal) {
		return addBlock(new BlockMetalOre(metal), metal.getName() + "_ore");
	}

	private static BlockDoor createDoor(MetalMaterial metal) {
		return (BlockDoor)addBlock(new modernmetals.blocks.BlockMetalDoor(metal), metal.getName() + "_door");
	}

	private static Block createTrapDoor(MetalMaterial metal) {
		return addBlock(new BlockMetalTrapDoor(metal), metal.getName() + "_trapdoor");
	}

	/**
	 * 
	 * @param event
	 */
	@SideOnly(Side.CLIENT)
	public static void registerItemRenders(FMLInitializationEvent event) {
		for(final String name : blockRegistry.keySet()) {
			if(blockRegistry.get(name) instanceof BlockDoor)
				continue; // do not add door blocks
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
			.register(net.minecraft.item.Item.getItemFromBlock(blockRegistry.get(name)), 0,
				new ModelResourceLocation(new ResourceLocation(ModernMetals.MODID, name), "inventory"));
		}
	}

	public static Map<String, Block> getBlockRegistry() {
		return blockRegistry;
	}
}

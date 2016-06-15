package modernmetals.init;

import java.util.Arrays;
import java.util.List;


import modernmetals.init.Materials;
import modernmetals.init.Items;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.CrusherRecipeRegistry;
import net.minecraft.block.Block;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Recipes extends cyano.basemetals.init.Recipes {

	private static boolean initDone = false;
	public static void init(){
		if(initDone)return;

		modernmetals.init.Materials.init();
		modernmetals.init.Blocks.init();
		modernmetals.init.Items.init();
		
		initMetalRecipes();
		
		initDone = true;
	}
	private static void initMetalRecipes(){
		List<MetalMaterial> exceptions = Arrays.asList(Materials.vanilla_iron,Materials.vanilla_gold,Materials.vanilla_diamond,Materials.vanilla_stone,Materials.vanilla_wood,Materials.copper,Materials.silver,Materials.tin,Materials.lead,Materials.nickel,Materials.bronze,Materials.brass,Materials.steel,Materials.invar,Materials.electrum,Materials.coldiron,Materials.mithril,Materials.adamantine,Materials.starsteel,Materials.zinc,Materials.aquarium);

		for(MetalMaterial metal : Materials.getAllMetals()){
			if(exceptions.contains(metal))continue;
			String baseName = metal.getName()+"_";
			String oreDictName = metal.getCapitalizedName();
			Item axe = modernmetals.init.Items.getItemByName(baseName+"axe");
			Item blend = modernmetals.init.Items.getItemByName(baseName+"blend");
			Item boots = modernmetals.init.Items.getItemByName(baseName+"boots");
			Item chestplate = modernmetals.init.Items.getItemByName(baseName+"chestplate");
			Item crackhammer = modernmetals.init.Items.getItemByName(baseName+"crackhammer");
			Item door = modernmetals.init.Items.getItemByName(baseName+"door_item");
			Item helmet = modernmetals.init.Items.getItemByName(baseName+"helmet");
			Item hoe = modernmetals.init.Items.getItemByName(baseName+"hoe");
			Item ingot = modernmetals.init.Items.getItemByName(baseName+"ingot");
			Item leggings = modernmetals.init.Items.getItemByName(baseName+"leggings");
			Item nugget = modernmetals.init.Items.getItemByName(baseName+"nugget");
			Item pickaxe = modernmetals.init.Items.getItemByName(baseName+"pickaxe");
			Item powder = modernmetals.init.Items.getItemByName(baseName+"powder");
			Item shovel = modernmetals.init.Items.getItemByName(baseName+"shovel");
			Item sword = modernmetals.init.Items.getItemByName(baseName+"sword");
			Item rod = modernmetals.init.Items.getItemByName(baseName+"rod");
			Item gear = modernmetals.init.Items.getItemByName(baseName+"gear");
			Block bars = modernmetals.init.Blocks.getBlockByName(baseName+"bars");
			Block block = modernmetals.init.Blocks.getBlockByName(baseName+"block");
			Block plate = modernmetals.init.Blocks.getBlockByName(baseName+"plate");
			Block ore = modernmetals.init.Blocks.getBlockByName(baseName+"ore");
			Block trapdoor = modernmetals.init.Blocks.getBlockByName(baseName+"trapdoor");
			
			Item arrow = modernmetals.init.Items.getItemByName(baseName+"arrow");
			Item bow = modernmetals.init.Items.getItemByName(baseName+"bow");
			Item bolt = modernmetals.init.Items.getItemByName(baseName+"bolt");
			Item crossbow = modernmetals.init.Items.getItemByName(baseName+"crossbow");
			Item shears = modernmetals.init.Items.getItemByName(baseName+"shears");
			Item smallblend = modernmetals.init.Items.getItemByName(baseName+"smallblend");
			Item smallpowder = modernmetals.init.Items.getItemByName(baseName+"smallpowder");
			Item fishingrod = modernmetals.init.Items.getItemByName(baseName+"fishingrod");
			Item horsearmor = modernmetals.init.Items.getItemByName(baseName+"horsearmor");

			// NOTE: smelting XP is based on output item, not input item
			// ingot-related recipes 
			if(ore != null && powder != null){
				CrusherRecipeRegistry.addNewCrusherRecipe("ore"+oreDictName,new ItemStack(powder,2));
			}
			if(ore != null && ingot != null){
				GameRegistry.addSmelting(ore, new ItemStack(ingot,1), metal.getOreSmeltXP());
			}
			if(ingot != null && powder != null){
				CrusherRecipeRegistry.addNewCrusherRecipe("ingot"+oreDictName,new ItemStack(powder,1));
				GameRegistry.addSmelting(powder, new ItemStack(ingot,1), metal.getOreSmeltXP());
			}
			if(ingot != null && blend != null){
				GameRegistry.addSmelting(blend, new ItemStack(ingot,1), metal.getOreSmeltXP());
			}
			if(ingot != null && nugget != null){
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(nugget,9), new ItemStack(ingot)));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingot), "xxx","xxx","xxx",'x',"nugget"+oreDictName));
			}
			if(ingot != null && block != null){
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ingot,9), new ItemStack(block)));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block), "xxx","xxx","xxx",'x',"ingot"+oreDictName));
			}
			if(ingot != null && plate != null){
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(plate,3), "xxx",'x',"ingot"+oreDictName));
				GameRegistry.addSmelting(plate, new ItemStack(ingot,1), metal.getOreSmeltXP());
			}
			if(block != null && powder != null){
				CrusherRecipeRegistry.addNewCrusherRecipe("block"+oreDictName, new ItemStack(powder,9));
			}
			if(ingot != null && bars != null){
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bars,16), "xxx","xxx",'x',"ingot"+oreDictName));
				OreDictionary.registerOre("bars", bars);
			}
			if(ingot != null && rod != null){
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rod,4), "x","x",'x',"ingot"+oreDictName));
				OreDictionary.registerOre("stick"+oreDictName,rod);
				OreDictionary.registerOre("rod"+oreDictName,rod);
				OreDictionary.registerOre("rod",rod);
			}
			if(nugget != null && rod != null){
				GameRegistry.addSmelting(rod, new ItemStack(nugget,4), 0);
			}
			if(rod != null && bars != null){
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bars,4), "xxx",'x',"rod"+oreDictName));
			}
			if(rod != null && ingot != null && gear != null){
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(gear,4), " x ","x/x"," x ",'x',"ingot"+oreDictName,'/',"rod"+oreDictName));
				OreDictionary.registerOre("gear"+oreDictName,gear);
				OreDictionary.registerOre("gear",gear);
				if(metal == Materials.steel)OreDictionary.registerOre("sprocket",gear);
			}
			if(ingot != null && door != null){
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(door,3), "xx","xx","xx",'x',"ingot"+oreDictName));
				OreDictionary.registerOre("door", door);
			}
			if(ingot != null && trapdoor != null){
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(trapdoor), "xx","xx",'x',"ingot"+oreDictName));
				OreDictionary.registerOre("trapdoor", trapdoor);
			}

			if(blend != null && smallblend != null){
				// TODO - why the hell doesn't this work?
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(smallblend,9), new ItemStack(blend)));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blend), "xxx","xxx","xxx",'x',new ItemStack(smallblend)));
				GameRegistry.addSmelting(smallblend, new ItemStack(nugget,1), metal.getOreSmeltXP());
			}
			if(powder != null && smallpowder != null){
				// TODO - small powder to powder recipe doesn't use oredict
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(smallpowder,9), new ItemStack(powder)));
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(powder), "xxx","xxx","xxx",'x',new ItemStack(smallpowder)));
				GameRegistry.addSmelting(smallpowder, new ItemStack(nugget,1), metal.getOreSmeltXP());
				CrusherRecipeRegistry.addNewCrusherRecipe("nugget"+oreDictName,new ItemStack(smallpowder,1));
			}
			
			// armor and tools
			if(ingot != null && boots != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(boots), "x x", "x x", 'x', "ingot"+oreDictName));
			if(ingot != null && helmet != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(helmet), "xxx", "x x", 'x', "ingot"+oreDictName));
			if(ingot != null && chestplate != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chestplate), "x x", "xxx", "xxx", 'x', "ingot"+oreDictName));
			if(ingot != null && leggings != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(leggings), "xxx", "x x", "x x", 'x', "ingot"+oreDictName));
			if(ingot != null && axe != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(axe), "xx", "x/", " /", 'x', "ingot"+oreDictName,'/', "stickWood"));
//			if(ingot != null && axe != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(axe), "xx", "/x", "/ ", 'x', "ingot"+oreDictName,'/', "stickWood"));
			if(block != null && crackhammer != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(crackhammer), "x", "/", "/" , 'x', "block"+oreDictName, '/', "stickWood"));
			if(ingot != null && hoe != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hoe), "xx", " /" , " /", 'x', "ingot"+oreDictName, '/', "stickWood"));
//			if(ingot != null && hoe != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hoe), "xx", "/ " , "/ ", 'x', "ingot"+oreDictName, '/', "stickWood"));
			if(ingot != null && pickaxe != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pickaxe), "xxx", " / ", " / ", 'x', "ingot"+oreDictName, '/', "stickWood"));
			if(ingot != null && shovel != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(shovel), "x", "/", "/", 'x', "ingot"+oreDictName, '/', "stickWood"));
			if(ingot != null && sword != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sword), "x", "x" , "/", 'x', "ingot"+oreDictName, '/', "stickWood"));
			if(ingot != null && shears != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(shears), " x", "x " , 'x', "ingot"+oreDictName));
//			if(rod != null && fishingrod != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fishingrod), "  x", " xy", "x y", 'x', "rod"+oreDictName, 'y', Items.STRING));
//			if(ingot != null && horsearmor != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(horsearmor), "  x", "xyx", "xxx", 'x', "ingot"+oreDictName, 'y', Blocks.WOOL));

			// Bows and Crossbows
			if(rod != null && arrow != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(arrow), "x", "y", "z", 'x', "nugget"+oreDictName, 'y', "rod"+oreDictName,'z' ,net.minecraft.init.Items.FEATHER));
			if(rod != null && bow != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bow), " xy", "x y", " xy", 'x', "rod"+oreDictName, 'y', net.minecraft.init.Items.STRING));
//			if(rod != null && gear != null && crossbow != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(crossbow), "zxx", " yx", "x z", 'x', "rod"+oreDictName, 'y', "gear"+oreDictName, 'z', Items.STRING));
//			if(rod != null && bolt != null) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bolt), "x", "x", "y", 'x', "rod"+oreDictName, 'y', Items.FEATHER));
		}
		
		// alloy blends
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(modernmetals.init.Items.aluminumbrass_blend,2), "dustAluminum", "dustBrass"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(modernmetals.init.Items.galvanizedsteel_blend,2), "dustSteel", "dustZinc"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(modernmetals.init.Items.nichrome_blend,2), "dustNickel", "dustChromium"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(modernmetals.init.Items.stainlesssteel_blend,2), "dustSteel", "dustChromium"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(modernmetals.init.Items.titanium_blend,2), "dustRutile", "dustMagnesium"));
		
		// small alloy blends
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(modernmetals.init.Items.aluminumbrass_smallblend,2), "smalldustAluminum", "smalldustBrass"));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(modernmetals.init.Items.galvanizedsteel_smallblend,2), "smalldustSteel", "smalldustZinc"));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(modernmetals.init.Items.nichrome_smallblend,2), "smalldustNickel", "smalldustChromium"));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(modernmetals.init.Items.stainlesssteel_smallblend,2), "smalldustSteel", "smalldustChromium"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(modernmetals.init.Items.titanium_smallblend,2), "smalldustRutile", "smalldustMagnesium"));

		// Alt oreDict Chrome
		OreDictionary.registerOre("oreChrome", Blocks.chromium_ore);
		OreDictionary.registerOre("blockChrome", Blocks.chromium_block);
		OreDictionary.registerOre("plateChrome", Blocks.chromium_plate);
		OreDictionary.registerOre("barsChrome", Blocks.chromium_bars);
//		OreDictionary.registerOre("doorChrome", Blocks.chromium_door);
		OreDictionary.registerOre("trapdoorChrome", Blocks.chromium_trapdoor);
		OreDictionary.registerOre("ingotChrome", Items.chromium_ingot);
		OreDictionary.registerOre("nuggetChrome", Items.chromium_nugget);
		OreDictionary.registerOre("dustChrome", Items.chromium_powder);
		OreDictionary.registerOre("gearChrome", Items.chromium_gear);
		OreDictionary.registerOre("rodChrome", Items.chromium_rod);

		// Some people refer to it as Aluminium
		OreDictionary.registerOre("oreAluminium", Blocks.aluminum_ore);
		OreDictionary.registerOre("blockAluminium", Blocks.aluminum_block);
		OreDictionary.registerOre("plateAluminium", Blocks.aluminum_plate);
		OreDictionary.registerOre("barsAluminium", Blocks.aluminum_bars);
//		OreDictionary.registerOre("doorAluminium", Blocks.aluminum_door);
		OreDictionary.registerOre("trapdoorAluminium", Blocks.aluminum_trapdoor);
		OreDictionary.registerOre("ingotAluminium", Items.aluminum_ingot);
		OreDictionary.registerOre("nuggetAluminium", Items.aluminum_nugget);
		OreDictionary.registerOre("dustAluminium", Items.aluminum_powder);
		OreDictionary.registerOre("gearAluminium", Items.aluminum_gear);
		OreDictionary.registerOre("rodAluminium", Items.aluminum_rod);

		// Some people refer to it as Aluminium
		OreDictionary.registerOre("blockAluminiumbrass", Blocks.aluminumbrass_block);
		OreDictionary.registerOre("plateAluminiumbrass", Blocks.aluminumbrass_plate);
		OreDictionary.registerOre("barsAluminiumbrass", Blocks.aluminumbrass_bars);
//		OreDictionary.registerOre("doorAluminiumbrass", Blocks.aluminumbrass_door);
		OreDictionary.registerOre("trapdoorAluminiumbrass", Blocks.aluminumbrass_trapdoor);
		OreDictionary.registerOre("dustAluminiumbrass", Items.aluminumbrass_blend);
		OreDictionary.registerOre("ingotAluminiumbrass", Items.aluminumbrass_ingot);
		OreDictionary.registerOre("nuggetAluminiumbrass", Items.aluminumbrass_nugget);
		OreDictionary.registerOre("dustAluminiumbrass", Items.aluminumbrass_powder);
		OreDictionary.registerOre("gearAluminiumbrass", Items.aluminumbrass_gear);
		OreDictionary.registerOre("rodAluminiumbrass", Items.aluminumbrass_rod);

		// Make Tinkers Construct happy
		OreDictionary.registerOre("blockAlubrass", Blocks.aluminumbrass_block);
		OreDictionary.registerOre("plateAlubrass", Blocks.aluminumbrass_plate);
		OreDictionary.registerOre("barsAlurass", Blocks.aluminumbrass_bars);
//		OreDictionary.registerOre("doorAlubrass", Blocks.aluminumbrass_door);
		OreDictionary.registerOre("trapdoorAlubrass", Blocks.aluminumbrass_trapdoor);
		OreDictionary.registerOre("dustAlubrass", Items.aluminumbrass_blend);
		OreDictionary.registerOre("ingotAlubrass", Items.aluminumbrass_ingot);
		OreDictionary.registerOre("nuggetAlubrass", Items.aluminumbrass_nugget);
		OreDictionary.registerOre("dustAlubrass", Items.aluminumbrass_powder);
		OreDictionary.registerOre("gearAlubrass", Items.aluminumbrass_gear);
		OreDictionary.registerOre("rodAlubrass", Items.aluminumbrass_rod);
		



	}
}

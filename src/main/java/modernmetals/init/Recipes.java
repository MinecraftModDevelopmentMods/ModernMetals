package modernmetals.init;

import modernmetals.util.Config.Options;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.*;

/**
 *
 * @author Jasmine Iwanek
 *
 */
public class Recipes extends cyano.basemetals.init.Recipes {

	private static boolean initDone = false;

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		Materials.init();
		Blocks.init();
		Items.init();

		initModSpecificRecipes();

		initDone = true;
	}

	private static void initModSpecificRecipes() {
		// alloy blends
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.aluminumbrass_blend, 2), "dustAluminum", "dustBrass"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.galvanizedsteel_blend, 2), "dustSteel", "dustZinc"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.nichrome_blend, 2), "dustNickel", "dustChromium"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.stainlesssteel_blend, 2), "dustSteel", "dustChromium"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.titanium_blend, 2), "dustRutile", "dustMagnesium"));

		// small alloy blends
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.aluminumbrass_smallblend, 2), "smalldustAluminum", "smalldustBrass"));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.galvanizedsteel_smallblend, 2), "smalldustSteel", "smalldustZinc"));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.nichrome_smallblend, 2), "smalldustNickel", "smalldustChromium"));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.stainlesssteel_smallblend, 2), "smalldustSteel", "smalldustChromium"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.titanium_smallblend, 2), "smalldustRutile", "smalldustMagnesium"));

		// Alt oreDict Chrome
		if (Options.ENABLE_CHROMIUM) {
			OreDictionary.registerOre("oreChrome", Blocks.chromium_ore);
			OreDictionary.registerOre("blockChrome", Blocks.chromium_block);
			OreDictionary.registerOre("plateChrome", Blocks.chromium_plate);
			OreDictionary.registerOre("barsChrome", Blocks.chromium_bars);
//			OreDictionary.registerOre("doorChrome", Blocks.chromium_door);
			OreDictionary.registerOre("trapdoorChrome", Blocks.chromium_trapdoor);
			OreDictionary.registerOre("ingotChrome", Items.chromium_ingot);
			OreDictionary.registerOre("nuggetChrome", Items.chromium_nugget);
			OreDictionary.registerOre("dustChrome", Items.chromium_powder);
			OreDictionary.registerOre("gearChrome", Items.chromium_gear);
			OreDictionary.registerOre("rodChrome", Items.chromium_rod);
		}

		// Alt oreDict Tungsten
		if (Options.ENABLE_TUNGSTEN) {
			OreDictionary.registerOre("oreWolfram", Blocks.tungsten_ore);
			OreDictionary.registerOre("blockWolfram", Blocks.tungsten_block);
			OreDictionary.registerOre("plateWolfram", Blocks.tungsten_plate);
			OreDictionary.registerOre("barsWolfram", Blocks.tungsten_bars);
//			OreDictionary.registerOre("doorWolfram", Blocks.tungsten_door);
			OreDictionary.registerOre("trapdoorWolfram", Blocks.tungsten_trapdoor);
			OreDictionary.registerOre("ingotWolfram", Items.tungsten_ingot);
			OreDictionary.registerOre("nuggetWolfram", Items.tungsten_nugget);
			OreDictionary.registerOre("dustWolfram", Items.tungsten_powder);
			OreDictionary.registerOre("gearWolfram", Items.tungsten_gear);
			OreDictionary.registerOre("rodWolfram", Items.tungsten_rod);
		}

		// Some people refer to it as Aluminium
		if (Options.ENABLE_ALUMINUM) {
			OreDictionary.registerOre("oreAluminium", Blocks.aluminum_ore);
			OreDictionary.registerOre("blockAluminium", Blocks.aluminum_block);
			OreDictionary.registerOre("plateAluminium", Blocks.aluminum_plate);
			OreDictionary.registerOre("barsAluminium", Blocks.aluminum_bars);
//			OreDictionary.registerOre("doorAluminium", Blocks.aluminum_door);
			OreDictionary.registerOre("trapdoorAluminium", Blocks.aluminum_trapdoor);
			OreDictionary.registerOre("ingotAluminium", Items.aluminum_ingot);
			OreDictionary.registerOre("nuggetAluminium", Items.aluminum_nugget);
			OreDictionary.registerOre("dustAluminium", Items.aluminum_powder);
			OreDictionary.registerOre("gearAluminium", Items.aluminum_gear);
			OreDictionary.registerOre("rodAluminium", Items.aluminum_rod);
		}

		// Some people refer to it as Aluminium
		if (Options.ENABLE_ALUMINUMBRASS) {
			OreDictionary.registerOre("blockAluminiumbrass", Blocks.aluminumbrass_block);
			OreDictionary.registerOre("plateAluminiumbrass", Blocks.aluminumbrass_plate);
			OreDictionary.registerOre("barsAluminiumbrass", Blocks.aluminumbrass_bars);
//			OreDictionary.registerOre("doorAluminiumbrass", Blocks.aluminumbrass_door);
			OreDictionary.registerOre("trapdoorAluminiumbrass", Blocks.aluminumbrass_trapdoor);
			OreDictionary.registerOre("dustAluminiumbrass", Items.aluminumbrass_blend);
			OreDictionary.registerOre("ingotAluminiumbrass", Items.aluminumbrass_ingot);
			OreDictionary.registerOre("nuggetAluminiumbrass", Items.aluminumbrass_nugget);
			OreDictionary.registerOre("dustAluminiumbrass", Items.aluminumbrass_powder);
			OreDictionary.registerOre("gearAluminiumbrass", Items.aluminumbrass_gear);
			OreDictionary.registerOre("rodAluminiumbrass", Items.aluminumbrass_rod);
		}

		// Make Tinkers Construct happy
		if (Options.ENABLE_ALUMINUMBRASS) {
			OreDictionary.registerOre("blockAlubrass", Blocks.aluminumbrass_block);
			OreDictionary.registerOre("plateAlubrass", Blocks.aluminumbrass_plate);
			OreDictionary.registerOre("barsAlurass", Blocks.aluminumbrass_bars);
//			OreDictionary.registerOre("doorAlubrass", Blocks.aluminumbrass_door);
			OreDictionary.registerOre("trapdoorAlubrass", Blocks.aluminumbrass_trapdoor);
			OreDictionary.registerOre("dustAlubrass", Items.aluminumbrass_blend);
			OreDictionary.registerOre("ingotAlubrass", Items.aluminumbrass_ingot);
			OreDictionary.registerOre("nuggetAlubrass", Items.aluminumbrass_nugget);
			OreDictionary.registerOre("dustAlubrass", Items.aluminumbrass_powder);
			OreDictionary.registerOre("gearAlubrass", Items.aluminumbrass_gear);
			OreDictionary.registerOre("rodAlubrass", Items.aluminumbrass_rod);
		}
	}
}

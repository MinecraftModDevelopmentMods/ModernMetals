package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.util.Config.Options;

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
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Materials.aluminumbrass.blend, 2), "dustAluminum", "dustBrass"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Materials.galvanizedsteel.blend, 2), "dustSteel", "dustZinc"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Materials.nichrome.blend, 2), "dustNickel", "dustChromium"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Materials.stainlesssteel.blend, 2), "dustSteel", "dustChromium"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Materials.titanium.blend, 2), "dustRutile", "dustMagnesium"));

		// small alloy blends
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Materials.aluminumbrass.smallblend, 2), "smalldustAluminum", "smalldustBrass"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Materials.galvanizedsteel.smallblend, 2), "smalldustSteel", "smalldustZinc"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Materials.nichrome.smallblend, 2), "smalldustNickel", "smalldustChromium"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Materials.stainlesssteel.smallblend, 2), "smalldustSteel", "smalldustChromium"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Materials.titanium.smallblend, 2), "smalldustRutile", "smalldustMagnesium"));

		// Alt oreDict Chrome
		if (Options.ENABLE_CHROMIUM) {
			OreDictionary.registerOre("oreChrome", Materials.chromium.ore);
			OreDictionary.registerOre("blockChrome", Materials.chromium.block);
			OreDictionary.registerOre("plateChrome", Materials.chromium.plate);
			OreDictionary.registerOre("barsChrome", Materials.chromium.bars);
//			OreDictionary.registerOre("doorChrome", Materials.chromium.doorBlock);
			OreDictionary.registerOre("trapdoorChrome", Materials.chromium.trapdoor);
			OreDictionary.registerOre("ingotChrome", Materials.chromium.ingot);
			OreDictionary.registerOre("nuggetChrome", Materials.chromium.nugget);
			OreDictionary.registerOre("dustChrome", Materials.chromium.powder);
			OreDictionary.registerOre("gearChrome", Materials.chromium.gear);
			OreDictionary.registerOre("rodChrome", Materials.chromium.rod);
		}

		// Alt oreDict Tungsten
		if (Options.ENABLE_TUNGSTEN) {
			OreDictionary.registerOre("oreWolfram", Materials.tungsten.ore);
			OreDictionary.registerOre("blockWolfram", Materials.tungsten.block);
			OreDictionary.registerOre("plateWolfram", Materials.tungsten.plate);
			OreDictionary.registerOre("barsWolfram", Materials.tungsten.bars);
//			OreDictionary.registerOre("doorWolfram", Materials.tungsten.doorBlock);
			OreDictionary.registerOre("trapdoorWolfram", Materials.tungsten.trapdoor);
			OreDictionary.registerOre("ingotWolfram", Materials.tungsten.ingot);
			OreDictionary.registerOre("nuggetWolfram", Materials.tungsten.nugget);
			OreDictionary.registerOre("dustWolfram", Materials.tungsten.powder);
			OreDictionary.registerOre("gearWolfram", Materials.tungsten.gear);
			OreDictionary.registerOre("rodWolfram", Materials.tungsten.rod);
		}

		// Some people refer to it as Aluminium
		if (Options.ENABLE_ALUMINUM) {
			OreDictionary.registerOre("oreAluminium", Materials.aluminum.ore);
			OreDictionary.registerOre("blockAluminium", Materials.aluminum.block);
			OreDictionary.registerOre("plateAluminium", Materials.aluminum.plate);
			OreDictionary.registerOre("barsAluminium", Materials.aluminum.bars);
//			OreDictionary.registerOre("doorAluminium", Materials.aluminum.doorBlock);
			OreDictionary.registerOre("trapdoorAluminium", Materials.aluminum.trapdoor);
			OreDictionary.registerOre("ingotAluminium", Materials.aluminum.ingot);
			OreDictionary.registerOre("nuggetAluminium", Materials.aluminum.nugget);
			OreDictionary.registerOre("dustAluminium", Materials.aluminum.powder);
			OreDictionary.registerOre("gearAluminium", Materials.aluminum.gear);
			OreDictionary.registerOre("rodAluminium", Materials.aluminum.rod);
		}

		// Some people refer to it as Aluminium
		if (Options.ENABLE_ALUMINUMBRASS) {
			OreDictionary.registerOre("blockAluminiumbrass", Materials.aluminumbrass.block);
			OreDictionary.registerOre("plateAluminiumbrass", Materials.aluminumbrass.plate);
			OreDictionary.registerOre("barsAluminiumbrass", Materials.aluminumbrass.bars);
//			OreDictionary.registerOre("doorAluminiumbrass", Materials.aluminumbrass.doorBlock);
			OreDictionary.registerOre("trapdoorAluminiumbrass", Materials.aluminumbrass.trapdoor);
			OreDictionary.registerOre("dustAluminiumbrass", Materials.aluminumbrass.blend);
			OreDictionary.registerOre("ingotAluminiumbrass", Materials.aluminumbrass.ingot);
			OreDictionary.registerOre("nuggetAluminiumbrass", Materials.aluminumbrass.nugget);
			OreDictionary.registerOre("dustAluminiumbrass", Materials.aluminumbrass.powder);
			OreDictionary.registerOre("gearAluminiumbrass", Materials.aluminumbrass.gear);
			OreDictionary.registerOre("rodAluminiumbrass", Materials.aluminumbrass.rod);
		}

		// Make Tinkers Construct happy
		if (Options.ENABLE_ALUMINUMBRASS) {
			OreDictionary.registerOre("blockAlubrass", Materials.aluminumbrass.block);
			OreDictionary.registerOre("plateAlubrass", Materials.aluminumbrass.plate);
			OreDictionary.registerOre("barsAlurass", Materials.aluminumbrass.bars);
//			OreDictionary.registerOre("doorAlubrass", Materials.aluminumbrass_doorBlock);
			OreDictionary.registerOre("trapdoorAlubrass", Materials.aluminumbrass.trapdoor);
			OreDictionary.registerOre("dustAlubrass", Materials.aluminumbrass.blend);
			OreDictionary.registerOre("ingotAlubrass", Materials.aluminumbrass.ingot);
			OreDictionary.registerOre("nuggetAlubrass", Materials.aluminumbrass.nugget);
			OreDictionary.registerOre("dustAlubrass", Materials.aluminumbrass.powder);
			OreDictionary.registerOre("gearAlubrass", Materials.aluminumbrass.gear);
			OreDictionary.registerOre("rodAlubrass", Materials.aluminumbrass.rod);
		}
	}
}

package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.init.Materials;
import com.mcmoddev.modernmetals.util.Config.Options;
import com.mcmoddev.lib.util.Oredicts;

import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author Jasmine Iwanek
 *
 */
public class Recipes extends com.mcmoddev.lib.init.Recipes {

	private static boolean initDone = false;

	private Recipes() {
		throw new IllegalAccessError("Not a instantiable class");
	}

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
		if (Options.enableAluminum) {
			String oreDictName = "Bauxite";

			addAdditionalOredicts(Materials.aluminum, "Aluminium");
			OreDictionary.registerOre(Oredicts.ORE + oreDictName, Materials.aluminum.ore);
			OreDictionary.registerOre(Oredicts.DUST + oreDictName, Materials.aluminum.powder);
			OreDictionary.registerOre(Oredicts.DUSTTINY + oreDictName, Materials.aluminum.smallpowder);
			OreDictionary.registerOre(Oredicts.DUSTSMALL + oreDictName, Materials.aluminum.smallpowder);
		}

		if (Options.enableAluminumBrass) {
			addSimpleAlloyRecipe(Materials.aluminumBrass, 2, "Aluminum", "Brass");
			addAdditionalOredicts(Materials.aluminumBrass, "AluminumBrass");
			addAdditionalOredicts(Materials.aluminumBrass, "Aluminiumbrass");
			addAdditionalOredicts(Materials.aluminumBrass, "AluminiumBrass");
			addAdditionalOredicts(Materials.aluminumBrass, "Alubrass");
			addAdditionalOredicts(Materials.aluminumBrass, "AluBrass");
		}

		if (Options.enableChromium) {
			addAdditionalOredicts(Materials.chromium, "Chrome");
		}

		if (Options.enableGalvanizedSteel) {
			addSimpleAlloyRecipe(Materials.galvanizedSteel, 2, "Steel", "Zinc");
			addAdditionalOredicts(Materials.galvanizedSteel, "GalvinizedSteel");
		}

		if (Options.enableNichrome) {
			addSimpleAlloyRecipe(Materials.nichrome, 2, "Nickel", "Chromium");
		}

		if (Options.enableStainlessSteel) {
			addSimpleAlloyRecipe(Materials.stainlessSteel, 2, "Steel", "Chromium");
			addAdditionalOredicts(Materials.stainlessSteel, "StainlessSteel");
		}

		if (Options.enableTitanium) {
			addSimpleAlloyRecipe(Materials.titanium, 2, "Rutile", "Magnesium");
		}

		if (Options.enableTungsten) {
			addAdditionalOredicts(Materials.tungsten, "Wolfram");
		}
	}
}

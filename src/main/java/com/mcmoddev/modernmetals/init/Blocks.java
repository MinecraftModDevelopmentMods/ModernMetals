package com.mcmoddev.modernmetals.init;


import com.mcmoddev.lib.util.TabContainer;
import com.mcmoddev.modernmetals.util.Config.Options;

/**
 * This class initializes all blocks in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Blocks extends com.mcmoddev.lib.init.Blocks {

	private static boolean initDone = false;
	private static TabContainer myTabs = new TabContainer( ItemGroups.blocksTab, ItemGroups.itemsTab, ItemGroups.toolsTab );

	protected Blocks() {
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
		ItemGroups.init();

		if (Options.enableAluminum) {
			createBlocksFull(Materials.getMaterialByName("aluminum"), myTabs);
		}

		if (Options.enableAluminumBrass) {
			createBlocksFull(Materials.getMaterialByName("aluminumbrass"), myTabs);
		}

		if (Options.enableCadmium) {
			createBlocksFull(Materials.getMaterialByName("cadmium"), myTabs);
		}

		if (Options.enableChromium) {
			createBlocksFull(Materials.getMaterialByName("chromium"), myTabs);
		}

		if (Options.enableGalvanizedSteel) {
			createBlocksFull(Materials.getMaterialByName("galvanizedsteel"), myTabs);
		}

		if (Options.enableIridium) {
			createBlocksFull(Materials.getMaterialByName("iridium"), myTabs);
		}

		if (Options.enableMagnesium) {
			createBlocksFull(Materials.getMaterialByName("magnesium"), myTabs);
		}

		if (Options.enableManganese) {
			createBlocksFull(Materials.getMaterialByName("manganese"), myTabs);
		}

		if (Options.enableNichrome) {
			createBlocksFull(Materials.getMaterialByName("nichrome"), myTabs);
		}

		if (Options.enableOsmium) {
			createBlocksFull(Materials.getMaterialByName("osmium"), myTabs);
		}

		if (Options.enablePlutonium) {
			createBlocksFull(Materials.getMaterialByName("plutonium"), myTabs);
		}

		if (Options.enableRutile) {
			createBlocksFull(Materials.getMaterialByName("rutile"), myTabs);
		}

		if (Options.enableStainlessSteel) {
			createBlocksFull(Materials.getMaterialByName("stainlesssteel"), myTabs);
		}

		if (Options.enableTantalum) {
			createBlocksFull(Materials.getMaterialByName("tantalum"), myTabs);
		}

		if (Options.enableTitanium) {
			createBlocksFull(Materials.getMaterialByName("titanium"), myTabs);
		}

		if (Options.enableTungsten) {
			createBlocksFull(Materials.getMaterialByName("tungsten"), myTabs);
		}

		if (Options.enableUranium) {
			createBlocksFull(Materials.getMaterialByName("uranium"), myTabs);
		}

		if (Options.enableZirconium) {
			createBlocksFull(Materials.getMaterialByName("zirconium"), myTabs);
		}

		initDone = true;
	}
}

package com.mcmoddev.modernmetals.init;

import com.mcmoddev.basemetals.init.ItemGroups;
import com.mcmoddev.lib.util.TabContainer;
import com.mcmoddev.modernmetals.util.Config.Options;

/**
 * This class initializes all items in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Items extends com.mcmoddev.lib.init.Items {

	private static boolean initDone = false;
	private static TabContainer myTabs = new TabContainer( ItemGroups.blocksTab, ItemGroups.itemsTab, ItemGroups.toolsTab );

	protected Items() {
		throw new IllegalAccessError("Not a instantiable class");
	}

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		Blocks.init();

		if (Options.enableAluminum) {
			createItemsFull(Materials.getMaterialByName("aluminum"), myTabs);
			createItemsModSupport(Materials.getMaterialByName("aluminum"), myTabs);
		}

		if (Options.enableAluminumBrass) {
			createItemsFull(Materials.getMaterialByName("aluminumBrass"), myTabs);
		}

		if (Options.enableCadmium) {
			createItemsFull(Materials.getMaterialByName("cadmium"), myTabs);
			createItemsModSupport(Materials.getMaterialByName("cadmium"), myTabs);
		}

		if (Options.enableChromium) {
			createItemsFull(Materials.getMaterialByName("chromium"), myTabs);
			createItemsModSupport(Materials.getMaterialByName("chromium"), myTabs);
		}

		if (Options.enableGalvanizedSteel) {
			createItemsFull(Materials.getMaterialByName("galvanizedSteel"), myTabs);
		}

		if (Options.enableIridium) {
			createItemsFull(Materials.getMaterialByName("iridium"), myTabs);
			createItemsModSupport(Materials.getMaterialByName("iridium"), myTabs);
		}

		if (Options.enableMagnesium) {
			createItemsFull(Materials.getMaterialByName("magnesium"), myTabs);
			createItemsModSupport(Materials.getMaterialByName("magnesium"), myTabs);
		}

		if (Options.enableManganese) {
			createItemsFull(Materials.getMaterialByName("manganese"), myTabs);
			createItemsModSupport(Materials.getMaterialByName("manganese"), myTabs);
		}

		if (Options.enableNichrome) {
			createItemsFull(Materials.getMaterialByName("nichrome"), myTabs);
		}

		if (Options.enableOsmium) {
			createItemsFull(Materials.getMaterialByName("osmium"), myTabs);
			createItemsModIC2(Materials.getMaterialByName("osmium"), myTabs);
		}

		if (Options.enablePlutonium) {
			createItemsFull(Materials.getMaterialByName("plutonium"), myTabs);
			createItemsModSupport(Materials.getMaterialByName("plutonium"), myTabs);
		}

		if (Options.enableRutile) {
			createItemsFull(Materials.getMaterialByName("rutile"), myTabs);
			createItemsModSupport(Materials.getMaterialByName("rutile"), myTabs);
		}

		if (Options.enableStainlessSteel) {
			createItemsFull(Materials.getMaterialByName("stainlessSteel"), myTabs);
		}

		if (Options.enableTantalum) {
			createItemsFull(Materials.getMaterialByName("tantalum"), myTabs);
			createItemsModSupport(Materials.getMaterialByName("tantalum"), myTabs);
		}

		if (Options.enableTitanium) {
			createItemsFull(Materials.getMaterialByName("titanium"), myTabs);
			createItemsModSupport(Materials.getMaterialByName("titanium"), myTabs);
		}

		if (Options.enableTungsten) {
			createItemsFull(Materials.getMaterialByName("tungsten"), myTabs);
			createItemsModSupport(Materials.getMaterialByName("tungsten"), myTabs);
		}

		if (Options.enableUranium) {
			createItemsFull(Materials.getMaterialByName("uranium"), myTabs);
			createItemsModMekanism(Materials.getMaterialByName("uranium"), myTabs);
		}

		if (Options.enableZirconium) {
			createItemsFull(Materials.getMaterialByName("zirconium"), myTabs);
			createItemsModSupport(Materials.getMaterialByName("zirconium"), myTabs);
		}

		// addToMetList() // May not be needed, check

		initDone = true;
	}
}

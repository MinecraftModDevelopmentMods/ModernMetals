package com.mcmoddev.modernmetals.init;


import com.mcmoddev.lib.util.TabContainer;
import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.lib.util.ConfigBase.Options;

/**
 * This class initializes all items in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Items extends com.mcmoddev.lib.init.Items {

	private static boolean initDone = false;
	private static TabContainer myTabs = ItemGroups.myTabs;

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

		if (Options.isMaterialEnabled(MaterialNames.ALUMINUM)) {
			createItemsFull(MaterialNames.ALUMINUM, myTabs);
			createItemsModSupport(MaterialNames.ALUMINUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.ALUMINUM_BRASS)) {
			createItemsFull(MaterialNames.ALUMINUM_BRASS, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.CADMIUM)) {
			createItemsFull(MaterialNames.CADMIUM, myTabs);
			createItemsModSupport(MaterialNames.CADMIUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.CHROMIUM)) {
			createItemsFull(MaterialNames.CHROMIUM, myTabs);
			createItemsModSupport(MaterialNames.CHROMIUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.GALVANIZED_STEEL)) {
			createItemsFull(MaterialNames.GALVANIZED_STEEL, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.IRIDIUM)) {
			createItemsFull(MaterialNames.IRIDIUM, myTabs);
			createItemsModSupport(MaterialNames.IRIDIUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.MAGNESIUM)) {
			createItemsFull(MaterialNames.MAGNESIUM, myTabs);
			createItemsModSupport(MaterialNames.MAGNESIUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.MANGANESE)) {
			createItemsFull(MaterialNames.MANGANESE, myTabs);
			createItemsModSupport(MaterialNames.MANGANESE, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.NICHROME)) {
			createItemsFull(MaterialNames.NICHROME, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.OSMIUM)) {
			createItemsFull(MaterialNames.OSMIUM, myTabs);
			createItemsModIC2(MaterialNames.OSMIUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.PLUTONIUM)) {
			createItemsFull(MaterialNames.PLUTONIUM, myTabs);
			createItemsModSupport(MaterialNames.PLUTONIUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.RUTILE)) {
			createItemsFull(MaterialNames.RUTILE, myTabs);
			createItemsModSupport(MaterialNames.RUTILE, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.STAINLESS_STEEL)) {
			createItemsFull(MaterialNames.STAINLESS_STEEL, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.TANTALUM)) {
			createItemsFull(MaterialNames.TANTALUM, myTabs);
			createItemsModSupport(MaterialNames.TANTALUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.TITANIUM)) {
			createItemsFull(MaterialNames.TITANIUM, myTabs);
			createItemsModSupport(MaterialNames.TITANIUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.TUNGSTEN)) {
			createItemsFull(MaterialNames.TUNGSTEN, myTabs);
			createItemsModSupport(MaterialNames.TUNGSTEN, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.URANIUM)) {
			createItemsFull(MaterialNames.URANIUM, myTabs);
			createItemsModMekanism(MaterialNames.URANIUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.ZIRCONIUM)) {
			createItemsFull(MaterialNames.ZIRCONIUM, myTabs);
			createItemsModSupport(MaterialNames.ZIRCONIUM, myTabs);
		}

		// addToMetList() // May not be needed, check

		initDone = true;
	}
}

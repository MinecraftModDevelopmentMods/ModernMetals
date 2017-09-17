package com.mcmoddev.modernmetals.init;

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
			createItemsFull(MaterialNames.ALUMINUM, ItemGroups.myTabs);
			createItemsModSupport(MaterialNames.ALUMINUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.ALUMINUM_BRASS)) {
			createItemsFull(MaterialNames.ALUMINUM_BRASS, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.CADMIUM)) {
			createItemsFull(MaterialNames.CADMIUM, ItemGroups.myTabs);
			createItemsModSupport(MaterialNames.CADMIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.CHROMIUM)) {
			createItemsFull(MaterialNames.CHROMIUM, ItemGroups.myTabs);
			createItemsModSupport(MaterialNames.CHROMIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.GALVANIZED_STEEL)) {
			createItemsFull(MaterialNames.GALVANIZED_STEEL, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.IRIDIUM)) {
			createItemsFull(MaterialNames.IRIDIUM, ItemGroups.myTabs);
			createItemsModSupport(MaterialNames.IRIDIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.MAGNESIUM)) {
			createItemsFull(MaterialNames.MAGNESIUM, ItemGroups.myTabs);
			createItemsModSupport(MaterialNames.MAGNESIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.MANGANESE)) {
			createItemsFull(MaterialNames.MANGANESE, ItemGroups.myTabs);
			createItemsModSupport(MaterialNames.MANGANESE, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.NICHROME)) {
			createItemsFull(MaterialNames.NICHROME, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.OSMIUM)) {
			createItemsFull(MaterialNames.OSMIUM, ItemGroups.myTabs);
			createItemsModIC2(MaterialNames.OSMIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.PLUTONIUM)) {
			createItemsFull(MaterialNames.PLUTONIUM, ItemGroups.myTabs);
			createItemsModSupport(MaterialNames.PLUTONIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.RUTILE)) {
			createItemsFull(MaterialNames.RUTILE, ItemGroups.myTabs);
			createItemsModSupport(MaterialNames.RUTILE, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.STAINLESS_STEEL)) {
			createItemsFull(MaterialNames.STAINLESS_STEEL, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.TANTALUM)) {
			createItemsFull(MaterialNames.TANTALUM, ItemGroups.myTabs);
			createItemsModSupport(MaterialNames.TANTALUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.TITANIUM)) {
			createItemsFull(MaterialNames.TITANIUM, ItemGroups.myTabs);
			createItemsModSupport(MaterialNames.TITANIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.TUNGSTEN)) {
			createItemsFull(MaterialNames.TUNGSTEN, ItemGroups.myTabs);
			createItemsModSupport(MaterialNames.TUNGSTEN, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.URANIUM)) {
			createItemsFull(MaterialNames.URANIUM, ItemGroups.myTabs);
			createItemsModMekanism(MaterialNames.URANIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.ZIRCONIUM)) {
			createItemsFull(MaterialNames.ZIRCONIUM, ItemGroups.myTabs);
			createItemsModSupport(MaterialNames.ZIRCONIUM, ItemGroups.myTabs);
		}

		// addToMetList() // TODO: May not be needed, check

		initDone = true;
	}
}

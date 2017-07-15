package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.lib.util.ConfigBase.Options;

/**
 * This class initializes all blocks in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Blocks extends com.mcmoddev.lib.init.Blocks {

	private static boolean initDone = false;

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

		if (Options.isMaterialEnabled(MaterialNames.ALUMINUM)) {
			createBlocksFull(MaterialNames.ALUMINUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.ALUMINUM_BRASS)) {
			createBlocksFull(MaterialNames.ALUMINUM_BRASS, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.CADMIUM)) {
			createBlocksFull(MaterialNames.CADMIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.CHROMIUM)) {
			createBlocksFull(MaterialNames.CHROMIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.GALVANIZED_STEEL)) {
			createBlocksFull(MaterialNames.GALVANIZED_STEEL, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.IRIDIUM)) {
			createBlocksFull(MaterialNames.IRIDIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.MAGNESIUM)) {
			createBlocksFull(MaterialNames.MAGNESIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.MANGANESE)) {
			createBlocksFull(MaterialNames.MANGANESE, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.NICHROME)) {
			createBlocksFull(MaterialNames.NICHROME, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.OSMIUM)) {
			createBlocksFull(MaterialNames.OSMIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.PLUTONIUM)) {
			createBlocksFull(MaterialNames.PLUTONIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.RUTILE)) {
			createBlocksFull(MaterialNames.RUTILE, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.STAINLESS_STEEL)) {
			createBlocksFull(MaterialNames.STAINLESS_STEEL, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.TANTALUM)) {
			createBlocksFull(MaterialNames.TANTALUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.TITANIUM)) {
			createBlocksFull(MaterialNames.TITANIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.TUNGSTEN)) {
			createBlocksFull(MaterialNames.TUNGSTEN, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.URANIUM)) {
			createBlocksFull(MaterialNames.URANIUM, ItemGroups.myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.ZIRCONIUM)) {
			createBlocksFull(MaterialNames.ZIRCONIUM, ItemGroups.myTabs);
		}

		initDone = true;
	}
}

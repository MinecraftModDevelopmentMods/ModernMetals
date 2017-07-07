package com.mcmoddev.modernmetals.init;


import com.mcmoddev.lib.util.TabContainer;
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
	private static TabContainer myTabs = ItemGroups.myTabs;

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
			createBlocksFull(MaterialNames.ALUMINUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.ALUMINUM_BRASS)) {
			createBlocksFull(MaterialNames.ALUMINUM_BRASS, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.CADMIUM)) {
			createBlocksFull(MaterialNames.CADMIUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.CHROMIUM)) {
			createBlocksFull(MaterialNames.CHROMIUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.GALVANIZED_STEEL)) {
			createBlocksFull(MaterialNames.GALVANIZED_STEEL, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.IRIDIUM)) {
			createBlocksFull(MaterialNames.IRIDIUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.MAGNESIUM)) {
			createBlocksFull(MaterialNames.MAGNESIUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.MANGANESE)) {
			createBlocksFull(MaterialNames.MANGANESE, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.NICHROME)) {
			createBlocksFull(MaterialNames.NICHROME, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.OSMIUM)) {
			createBlocksFull(MaterialNames.OSMIUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.PLUTONIUM)) {
			createBlocksFull(MaterialNames.PLUTONIUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.RUTILE)) {
			createBlocksFull(MaterialNames.RUTILE, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.STAINLESS_STEEL)) {
			createBlocksFull(MaterialNames.STAINLESS_STEEL, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.TANTALUM)) {
			createBlocksFull(MaterialNames.TANTALUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.TITANIUM)) {
			createBlocksFull(MaterialNames.TITANIUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.TUNGSTEN)) {
			createBlocksFull(MaterialNames.TUNGSTEN, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.URANIUM)) {
			createBlocksFull(MaterialNames.URANIUM, myTabs);
		}

		if (Options.isMaterialEnabled(MaterialNames.ZIRCONIUM)) {
			createBlocksFull(MaterialNames.ZIRCONIUM, myTabs);
		}

		initDone = true;
	}
}

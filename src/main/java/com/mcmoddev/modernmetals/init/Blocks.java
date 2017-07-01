package com.mcmoddev.modernmetals.init;


import com.mcmoddev.lib.util.TabContainer;
import com.mcmoddev.modernmetals.data.MaterialNames;
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

		if (Options.materialEnabled(MaterialNames.ALUMINUM)) {
			createBlocksFull(MaterialNames.ALUMINUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.ALUMINUM_BRASS)) {
			createBlocksFull(MaterialNames.ALUMINUM_BRASS, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.CADMIUM)) {
			createBlocksFull(MaterialNames.CADMIUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.CHROMIUM)) {
			createBlocksFull(MaterialNames.CHROMIUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.GALVANIZED_STEEL)) {
			createBlocksFull(MaterialNames.GALVANIZED_STEEL, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.IRIDIUM)) {
			createBlocksFull(MaterialNames.IRIDIUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.MAGNESIUM)) {
			createBlocksFull(MaterialNames.MAGNESIUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.MANGANESE)) {
			createBlocksFull(MaterialNames.MANGANESE, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.NICHROME)) {
			createBlocksFull(MaterialNames.NICHROME, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.OSMIUM)) {
			createBlocksFull(MaterialNames.OSMIUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.PLUTONIUM)) {
			createBlocksFull(MaterialNames.PLUTONIUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.RUTILE)) {
			createBlocksFull(MaterialNames.RUTILE, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.STAINLESS_STEEL)) {
			createBlocksFull(MaterialNames.STAINLESS_STEEL, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.TANTALUM)) {
			createBlocksFull(MaterialNames.TANTALUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.TITANIUM)) {
			createBlocksFull(MaterialNames.TITANIUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.TUNGSTEN)) {
			createBlocksFull(MaterialNames.TUNGSTEN, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.URANIUM)) {
			createBlocksFull(MaterialNames.URANIUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.ZIRCONIUM)) {
			createBlocksFull(MaterialNames.ZIRCONIUM, myTabs);
		}

		initDone = true;
	}
}

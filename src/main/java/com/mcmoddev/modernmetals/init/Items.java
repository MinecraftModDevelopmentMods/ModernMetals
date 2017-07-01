package com.mcmoddev.modernmetals.init;


import com.mcmoddev.lib.util.TabContainer;
import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.modernmetals.util.Config.Options;

/**
 * This class initializes all items in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Items extends com.mcmoddev.lib.init.Items {

	private static boolean initDone = false;
	private static TabContainer myTabs = new TabContainer(ItemGroups.blocksTab, ItemGroups.itemsTab, ItemGroups.toolsTab);

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

		if (Options.materialEnabled(MaterialNames.ALUMINUM)) {
			createItemsFull(MaterialNames.ALUMINUM, myTabs);
			createItemsModSupport(MaterialNames.ALUMINUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.ALUMINUM_BRASS)) {
			createItemsFull(MaterialNames.ALUMINUM_BRASS, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.CADMIUM)) {
			createItemsFull(MaterialNames.CADMIUM, myTabs);
			createItemsModSupport(MaterialNames.CADMIUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.CHROMIUM)) {
			createItemsFull(MaterialNames.CHROMIUM, myTabs);
			createItemsModSupport(MaterialNames.CHROMIUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.GALVANIZED_STEEL)) {
			createItemsFull(MaterialNames.GALVANIZED_STEEL, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.IRIDIUM)) {
			createItemsFull(MaterialNames.IRIDIUM, myTabs);
			createItemsModSupport(MaterialNames.IRIDIUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.MAGNESIUM)) {
			createItemsFull(MaterialNames.MAGNESIUM, myTabs);
			createItemsModSupport(MaterialNames.MAGNESIUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.MANGANESE)) {
			createItemsFull(MaterialNames.MANGANESE, myTabs);
			createItemsModSupport(MaterialNames.MANGANESE, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.NICHROME)) {
			createItemsFull(MaterialNames.NICHROME, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.OSMIUM)) {
			createItemsFull(MaterialNames.OSMIUM, myTabs);
			createItemsModIC2(MaterialNames.OSMIUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.PLUTONIUM)) {
			createItemsFull(MaterialNames.PLUTONIUM, myTabs);
			createItemsModSupport(MaterialNames.PLUTONIUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.RUTILE)) {
			createItemsFull(MaterialNames.RUTILE, myTabs);
			createItemsModSupport(MaterialNames.RUTILE, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.STAINLESS_STEEL)) {
			createItemsFull(MaterialNames.STAINLESS_STEEL, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.TANTALUM)) {
			createItemsFull(MaterialNames.TANTALUM, myTabs);
			createItemsModSupport(MaterialNames.TANTALUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.TITANIUM)) {
			createItemsFull(MaterialNames.TITANIUM, myTabs);
			createItemsModSupport(MaterialNames.TITANIUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.TUNGSTEN)) {
			createItemsFull(MaterialNames.TUNGSTEN, myTabs);
			createItemsModSupport(MaterialNames.TUNGSTEN, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.URANIUM)) {
			createItemsFull(MaterialNames.URANIUM, myTabs);
			createItemsModMekanism(MaterialNames.URANIUM, myTabs);
		}

		if (Options.materialEnabled(MaterialNames.ZIRCONIUM)) {
			createItemsFull(MaterialNames.ZIRCONIUM, myTabs);
			createItemsModSupport(MaterialNames.ZIRCONIUM, myTabs);
		}

		// addToMetList() // May not be needed, check

		initDone = true;
	}
}

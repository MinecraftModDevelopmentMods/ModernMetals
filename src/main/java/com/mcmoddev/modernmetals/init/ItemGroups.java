package com.mcmoddev.modernmetals.init;

/**
 * This class initializes all item groups in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class ItemGroups extends com.mcmoddev.lib.init.ItemGroups {

	private static boolean initDone = false;

	private ItemGroups() {
		throw new IllegalAccessError("Not a instantiable class");
	}

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

//		blocksTab = addTab("blocks", true, Materials.vanilla_iron);
//		itemsTab = addTab("items", true, Materials.vanilla_iron);
//		toolsTab = addTab("tools", true, Materials.vanilla_iron);

		initDone = true;
	}
}

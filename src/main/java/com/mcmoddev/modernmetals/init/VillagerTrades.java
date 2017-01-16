package com.mcmoddev.modernmetals.init;

/**
 *
 * @author Jasmine Iwanek
 *
 */
public class VillagerTrades extends cyano.basemetals.init.VillagerTrades {

	private static boolean initDone = false;

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		Materials.init();
		Items.init();

		registerCommonTrades();

		initDone = true;
	}
}

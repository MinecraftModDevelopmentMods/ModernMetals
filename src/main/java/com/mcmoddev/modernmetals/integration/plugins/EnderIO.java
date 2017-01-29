package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.integration.ModernMetalsPlugin;
import com.mcmoddev.basemetals.integration.IIntegration;

/**
 *
 * @author Jasmine Iwanek
 *
 */
@ModernMetalsPlugin(EnderIO.PLUGIN_MODID)
public class EnderIO extends com.mcmoddev.basemetals.integration.plugins.EnderIO implements IIntegration {

	private static boolean initDone = false;

	/**
	 *
	 */
	@Override
	public void init() {
		if (initDone || !com.mcmoddev.basemetals.util.Config.Options.ENABLE_ENDER_IO) {
			return;
		}

		// TODO: Verify blocks, Slabs, Ingots, Oreblocks
		addSagMillRecipe("aluminum", null, 3600);
		addSagMillRecipe("aluminumbrass", null, 3600);
		addSagMillRecipe("cadmium", null, 3600);
		addSagMillRecipe("chromium", null, 3600);
		addSagMillRecipe("galvanizedsteel", null, 3600);
		addSagMillRecipe("iridium", null, 3600);
		addSagMillRecipe("magnesium", null, 3600);
		addSagMillRecipe("manganese", null, 3600);
		addSagMillRecipe("nichrome", null, 3600);
		addSagMillRecipe("osmium", null, 3600);
		addSagMillRecipe("plutonium", null, 3600);
		addSagMillRecipe("rutile", null, 3600);
		addSagMillRecipe("stainlesssteel", null, 3600);
		addSagMillRecipe("tantalum", null, 3600);
		addSagMillRecipe("titanium", null, 3600);
		addSagMillRecipe("tungsten", null, 3600);
		addSagMillRecipe("uranium", null, 3600);
		addSagMillRecipe("zirconium", null, 3600);

		initDone = true;
	}
}

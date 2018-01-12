package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.modernmetals.ModernMetals;

@MMDPlugin(addonId = ModernMetals.MODID, pluginId = Thaumcraft.PLUGIN_MODID)
public class Thaumcraft extends com.mcmoddev.lib.integration.plugins.ThaumcraftBase implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !Options.isModEnabled(Thaumcraft.PLUGIN_MODID)) {
			return;
		}

		initDone = true;
	}
}

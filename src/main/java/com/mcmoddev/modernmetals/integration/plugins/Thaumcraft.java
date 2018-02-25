package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.modernmetals.ModernMetals;

@MMDPlugin(addonId = ModernMetals.MODID, pluginId = Thaumcraft.PLUGIN_MODID)
public class Thaumcraft extends com.mcmoddev.lib.integration.plugins.ThaumcraftBase implements IIntegration {
	@Override
	public void init() {
		if (!Options.isModEnabled(PLUGIN_MODID)) {
			return;
		}
	}
}

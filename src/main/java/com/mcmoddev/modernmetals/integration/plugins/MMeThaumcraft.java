package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.util.Config.Options;
import com.mcmoddev.modernmetals.ModernMetals;

@MMDPlugin(addonId = ModernMetals.MODID, pluginId = MMeThaumcraft.PLUGIN_MODID)
public class MMeThaumcraft extends com.mcmoddev.lib.integration.plugins.Thaumcraft implements IIntegration {

	@Override
	public void init() {
		if (!Options.isModEnabled(PLUGIN_MODID)) {
			return;
		}
	}
}

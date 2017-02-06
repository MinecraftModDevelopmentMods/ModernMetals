package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.integration.ModernMetalsPlugin;
import com.mcmoddev.lib.integration.IIntegration;

@ModernMetalsPlugin(Thaumcraft.PLUGIN_MODID)
public class Thaumcraft extends com.mcmoddev.lib.integration.plugins.Thaumcraft implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !com.mcmoddev.basemetals.util.Config.Options.enableThaumcraft) {
			return;
		}

		initDone = true;
	}
}

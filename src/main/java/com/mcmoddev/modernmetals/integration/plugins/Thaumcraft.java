package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.integration.ModernMetalsPlugin;
import com.mcmoddev.basemetals.integration.IIntegration;

@ModernMetalsPlugin(Thaumcraft.PLUGIN_MODID)
public class Thaumcraft extends com.mcmoddev.basemetals.integration.plugins.Thaumcraft implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !com.mcmoddev.basemetals.util.Config.Options.ENABLE_THAUMCRAFT) {
			return;
		}

		initDone = true;
	}
}

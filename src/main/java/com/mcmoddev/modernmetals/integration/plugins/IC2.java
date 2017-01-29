package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.integration.ModernMetalsPlugin;
import com.mcmoddev.basemetals.integration.IIntegration;

@ModernMetalsPlugin(IC2.PLUGIN_MODID)
public class IC2 extends com.mcmoddev.basemetals.integration.plugins.IC2 implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !com.mcmoddev.basemetals.util.Config.Options.ENABLE_IC2) {
			return;
		}

		initDone = true;
	}
}

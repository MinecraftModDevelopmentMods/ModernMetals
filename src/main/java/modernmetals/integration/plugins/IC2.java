package modernmetals.integration.plugins;

import cyano.basemetals.integration.BaseMetalsPlugin;
import cyano.basemetals.integration.IIntegration;

@BaseMetalsPlugin(IC2.PLUGIN_MODID)
public class IC2 extends cyano.basemetals.integration.plugins.IC2 implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !cyano.basemetals.util.Config.Options.ENABLE_IC2) {
			return;
		}

		initDone = true;
	}
}

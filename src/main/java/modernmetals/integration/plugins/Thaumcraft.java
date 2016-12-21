package modernmetals.integration.plugins;

import cyano.basemetals.integration.BaseMetalsPlugin;
import cyano.basemetals.integration.IIntegration;

@BaseMetalsPlugin(Thaumcraft.PLUGIN_MODID)
public class Thaumcraft extends cyano.basemetals.integration.plugins.Thaumcraft implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !cyano.basemetals.util.Config.Options.ENABLE_THAUMCRAFT) {
			return;
		}

		initDone = true;
	}
}

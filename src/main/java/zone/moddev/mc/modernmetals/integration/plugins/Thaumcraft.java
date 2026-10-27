package zone.moddev.mc.modernmetals.integration.plugins;

import zone.moddev.mc.modernmetals.ModernMetals;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.integration.IIntegration;

@MMDPlugin(addonId = ModernMetals.MODID, pluginId = Thaumcraft.PLUGIN_MODID)
public class Thaumcraft extends com.mcmoddev.lib.integration.plugins.ThaumcraftBase implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !zone.moddev.mc.basemetals.util.Config.Options.isModEnabled("thaumcraft")) {
			return;
		}

		initDone = true;
	}
}

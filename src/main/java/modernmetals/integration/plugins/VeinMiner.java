package modernmetals.integration.plugins;

import cyano.basemetals.integration.BaseMetalsPlugin;
import cyano.basemetals.integration.IIntegration;

/**
 * VeinMiner Integration Plugin
 *
 * @author Jasmine Iwanek
 *
 */
@BaseMetalsPlugin(VeinMiner.PLUGIN_MODID)
public class VeinMiner extends cyano.basemetals.integration.plugins.VeinMiner implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !cyano.basemetals.util.Config.Options.ENABLE_VEINMINER) {
			return;
		}

		addToolsForMaterial("aluminum");
		addToolsForMaterial("aluminumbrass");
		addToolsForMaterial("cadmium");
		addToolsForMaterial("chromium");
		addToolsForMaterial("galvanizedsteel");
		addToolsForMaterial("iridium");
		addToolsForMaterial("magnesium");
		addToolsForMaterial("manganese");
		addToolsForMaterial("nichrome");
		addToolsForMaterial("osmium");
		addToolsForMaterial("plutonium");
		addToolsForMaterial("rutile");
		addToolsForMaterial("stainlesssteel");
		addToolsForMaterial("tantalum");
		addToolsForMaterial("titanium");
		addToolsForMaterial("tungsten");
		addToolsForMaterial("uranium");
		addToolsForMaterial("zirconium");

		initDone = true;
	}
}

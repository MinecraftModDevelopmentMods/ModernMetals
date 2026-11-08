package zone.moddev.mc.modernmetals.integration.plugins;

import zone.moddev.mc.modernmetals.ModernMetals;
import zone.moddev.mc.modernmetals.data.MaterialNames;
import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.util.Oredicts;

@MMDPlugin(addonId = ModernMetals.MODID, pluginId = DenseOres.PLUGIN_MODID)
public class DenseOres extends com.mcmoddev.lib.integration.plugins.DenseOresBase implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !Options.isModEnabled(DenseOres.PLUGIN_MODID)) {
			return;
		}

		registerOres();

		initDone = true;
	}

	/**
	 * Register all ores that are currently known by the materials registry
	 * 
	 * @author Daniel Hazelton &lt;dshadowwolf@gmail.com&gt;
	 */
	private static void registerOres() {
		final String[] baseNames = new String[] {
				MaterialNames.ALUMINUM,
				MaterialNames.BERYLLIUM,
				MaterialNames.BORON,
				MaterialNames.CADMIUM,
				MaterialNames.CHROMIUM,
				MaterialNames.IRIDIUM,
				MaterialNames.MAGNESIUM,
				MaterialNames.MANGANESE,
				MaterialNames.OSMIUM,
				MaterialNames.PLUTONIUM,
				MaterialNames.RUTILE,
				MaterialNames.TANTALUM,
				MaterialNames.THORIUM,
				MaterialNames.TITANIUM,
				MaterialNames.TUNGSTEN,
				MaterialNames.URANIUM,
				MaterialNames.ZIRCONIUM
		};

		for (final String materialName : baseNames) {
			final MMDMaterial material = Materials.getMaterialByName(materialName);
			if (material != null && Materials.hasMaterial(materialName)) {
				registerOre(String.format("%s_%s", materialName, Oredicts.ORE), Oredicts.STONE, 0);
			}
		}
	}
}

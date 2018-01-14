package com.mcmoddev.modernmetals.integration.plugins;

import java.util.Arrays;
import java.util.List;

import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;

/**
 *
 * @author Jasmine Iwanek
 *
 */
@MMDPlugin(addonId = ModernMetals.MODID, pluginId = EnderIO.PLUGIN_MODID)
public class EnderIO extends com.mcmoddev.lib.integration.plugins.EnderIOBase implements IIntegration {

	private static boolean initDone = false;

	/**
	 *
	 */
	@Override
	public void init() {
		if (initDone || !Options.isModEnabled(EnderIO.PLUGIN_MODID)) {
			return;
		}

		final List<String> materials = Arrays.asList(MaterialNames.ALUMINUM, MaterialNames.ALUMINUM_BRASS,
				MaterialNames.CADMIUM, MaterialNames.CHROMIUM, MaterialNames.GALVANIZED_STEEL, MaterialNames.IRIDIUM,
				MaterialNames.MAGNESIUM, MaterialNames.MANGANESE, MaterialNames.NICHROME, MaterialNames.OSMIUM,
				MaterialNames.PLUTONIUM, MaterialNames.RUTILE, MaterialNames.STAINLESS_STEEL, MaterialNames.TANTALUM,
				MaterialNames.TITANIUM, MaterialNames.TUNGSTEN, MaterialNames.URANIUM, MaterialNames.ZIRCONIUM);

		materials.stream().filter(Materials::hasMaterial)
				.filter(materialName -> !Materials.getMaterialByName(materialName).equals(Materials.emptyMaterial))
				.forEach(materialName -> addSagMillRecipe(materialName, 3600));

		initDone = true;
	}
}

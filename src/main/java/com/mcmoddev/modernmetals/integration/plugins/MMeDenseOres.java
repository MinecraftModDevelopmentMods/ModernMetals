package com.mcmoddev.modernmetals.integration.plugins;

import java.util.Arrays;
import java.util.List;

import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.util.Config.Options;
import com.mcmoddev.lib.util.Oredicts;
import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;

@MMDPlugin(addonId = ModernMetals.MODID, pluginId = MMeDenseOres.PLUGIN_MODID)
public class MMeDenseOres extends com.mcmoddev.lib.integration.plugins.DenseOres implements IIntegration {

	@Override
	public void init() {
		if (!Options.isModEnabled(PLUGIN_MODID)) {
			return;
		}

		registerOres();
	}

	/**
	 * Register all ores that are currently known by the materials registry.
	 * 
	 * @author Daniel Hazelton &lt;dshadowwolf@gmail.com&gt;
	 */
	private static void registerOres() {
		final List<String> materials = Arrays.asList(MaterialNames.ALUMINUM, MaterialNames.CADMIUM,
				MaterialNames.CHROMIUM, MaterialNames.IRIDIUM, MaterialNames.MAGNESIUM, MaterialNames.MANGANESE,
				MaterialNames.OSMIUM, MaterialNames.PLUTONIUM, MaterialNames.RUTILE, MaterialNames.TANTALUM,
				MaterialNames.TITANIUM, MaterialNames.TUNGSTEN, MaterialNames.URANIUM, MaterialNames.ZIRCONIUM);

		materials.stream().filter(Materials::hasMaterial)
				.filter(materialName -> !Materials.getMaterialByName(materialName).isEmpty())
				.forEach(materialName -> {
					final MMDMaterial material = Materials.getMaterialByName(materialName);
					if (material.isEmpty()) {
						registerOre(String.format("%s_%s", materialName, Oredicts.ORE), Oredicts.STONE, 0);
					}
				});
	}
}

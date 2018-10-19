package com.mcmoddev.modernmetals.util;

import com.mcmoddev.lib.registry.CrusherRecipeRegistry;
import com.mcmoddev.lib.util.MaterialConfigOptions;
import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

/**
 * @author Jasmine Iwanek
 *
 */
public class MMeConfig extends com.mcmoddev.lib.util.Config {

	private static Configuration configuration;
	private static final String CONFIG_FILE = "config/ModernMetals.cfg";

	private static final MaterialConfigOptions[] MATERIAL_CONFIG_OPTIONS = new MaterialConfigOptions[]{
		new MaterialConfigOptions(MaterialNames.ALUMINUM, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.ALUMINUM_BRASS, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.BERYLLIUM, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.BORON, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.CADMIUM, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.CHROMIUM, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.GALVANIZED_STEEL, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.IRIDIUM, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.MAGNESIUM, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.MANGANESE, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.NICHROME, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.OSMIUM, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.PLUTONIUM, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.RUTILE, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.STAINLESS_STEEL, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.TANTALUM, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.THORIUM, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.TITANIUM, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.TUNGSTEN, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.URANIUM, false, true, true, true, true),
		new MaterialConfigOptions(MaterialNames.ZIRCONIUM, false, true, true, true, true),
	};
	/**
	 * 
	 * @param event The Event.
	 */
	@SubscribeEvent
	public void onConfigChange(final ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(ModernMetals.MODID)) {
			init();
		}
	}

	/**
	 * 
	 */
	public static void init() {
		if (configuration == null) {
			configuration = new Configuration(new File(CONFIG_FILE));
			MinecraftForge.EVENT_BUS.register(new MMeConfig());
		}

		configMaterialOptions(MATERIAL_CONFIG_OPTIONS, configuration);

		if (configuration.hasChanged()) {
			configuration.save();
		}
	}

	public static void postInit() {
		CrusherRecipeRegistry.getInstance().clearCache();
	}
}

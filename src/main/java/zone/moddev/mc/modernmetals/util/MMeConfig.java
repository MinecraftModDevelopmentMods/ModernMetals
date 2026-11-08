package zone.moddev.mc.modernmetals.util;

import com.mcmoddev.lib.registry.CrusherRecipeRegistry;
import com.mcmoddev.lib.util.MaterialConfigOptions;
import zone.moddev.mc.modernmetals.ModernMetals;
import zone.moddev.mc.modernmetals.data.MaterialNames;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraftforge.common.config.Property;
import org.apache.commons.lang3.text.WordUtils;

/**
 * @author Jasmine Iwanek
 *
 */
public class MMeConfig extends com.mcmoddev.lib.util.Config {

	public static final List<String> MATERIAL_CATEGORIES = Collections.unmodifiableList(
            Arrays.asList("Metals", "Fluids", "Forced Traits"));
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

        final Property mode = configuration.get("General", "contentMode", "high_fantasy",
                "Applies when a compatible Base Metals build is absent. Restart after changing.",
                new String[] { "high_fantasy", "low_fantasy" });
        mode.setLanguageKey("config.modernmetals.content_mode").setRequiresMcRestart(true);
        mode.setValidValuesDisplay(new String[] {
                "config.modernmetals.content_mode.high_fantasy", "config.modernmetals.content_mode.low_fantasy" });
        configuration.get("General", "enableVillagerTrades", true)
                .setLanguageKey("config.modernmetals.villager_trades").setRequiresMcRestart(true);
        configMaterialOptions(MATERIAL_CONFIG_OPTIONS, configuration);
        for (final String category : MATERIAL_CATEGORIES) {
            configuration.getCategory(category).setRequiresMcRestart(true);
        }
        for (final MaterialConfigOptions material : MATERIAL_CONFIG_OPTIONS) {
            final String name = material.getIdentifier();
            final String suffix = WordUtils.capitalizeFully(name);
            decorate("Metals", "Enable" + suffix, name);
            decorate("Fluids", "Enabled " + suffix, name);
            decorate("Forced Traits", "Force" + suffix + "TraitRegistration", name);
        }

		if (configuration.hasChanged()) {
			configuration.save();
		}
	}

    private static void decorate(final String category, final String key, final String material) {
        final Property property = configuration.getCategory(category).get(key);
        if (property != null) {
            property.setLanguageKey("config.modernmetals.enable_" + material).setRequiresMcRestart(true);
        }
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static String configuredContentMode() {
        return configuration.getCategory("General").get("contentMode").getString();
    }

    public static boolean villagerTradesEnabled() {
        return configuration.getCategory("General").get("enableVillagerTrades").getBoolean();
    }

	public static void postInit() {
		CrusherRecipeRegistry.getInstance().clearCache();
	}
}

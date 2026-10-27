package zone.moddev.mc.modernmetals.util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

import zone.moddev.mc.modernmetals.ModernMetals;
import com.mcmoddev.lib.registry.CrusherRecipeRegistry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.MissingModsException;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;

/**
 * @author p455w0rd
 *
 */
public class Config {

	private static Configuration configuration;
	private static final String CONFIG_FILE = "config/ModernMetals.cfg";
	private static final String MATERIALS_CAT = "Metals";

	@SubscribeEvent
	public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent e) {
		if (e.getModID().equals(ModernMetals.MODID)) {
			configuration.save(); // Registry switches take effect on the next game start.
		}
	}

	public static void init() {
		if (configuration == null) {
			configuration = new Configuration(new File(CONFIG_FILE));
			MinecraftForge.EVENT_BUS.register(new Config());
		}
		zone.moddev.mc.modernmetals.content.ContentPolicy.initialize(contentMode(configuration));

		// METALS
		Options.enableThorium = material("thorium", "EnableThorium", "Thorium");
		Options.enableBoron = material("boron", "EnableBoron", "Boron");
		Options.enableBeryllium = material("beryllium", "EnableBeryllium", "Beryllium");
		Options.enableAluminum = material("aluminum", "EnableAluminum", "Aluminum");
		Options.enableAluminumBrass = material("aluminumbrass", "EnableAluminumBrass", "Aluminum Brass");
		Options.enableCadmium = material("cadmium", "EnableCadmium", "Cadmium");
		Options.enableChromium = material("chromium", "EnableChromium", "Chromium");
		Options.enableGalvanizedSteel = material("galvanizedsteel", "EnableGalvanizedSteel", "Galvanized Steel");
		Options.enableIridium = material("iridium", "EnableIridium", "Iridium");
		Options.enableMagnesium = material("magnesium", "EnableMagnesium", "Magnesium");
		Options.enableManganese = material("manganese", "EnableManganese", "Manganese");
		Options.enableNichrome = material("nichrome", "EnableNichrome", "Nichrome");
		Options.enableOsmium = material("osmium", "EnableOsmium", "Osmium");
		Options.enablePlutonium = material("plutonium", "EnablePlutonium", "Plutonium");
		Options.enableRutile = material("rutile", "EnableRutile", "Rutile");
		Options.enableStainlessSteel = material("stainlesssteel", "EnableStainlessSteel", "Stainless Steel");
		Options.enableTantalum = material("tantalum", "EnableTantalum", "Tantalum");
		Options.enableTitanium = material("titanium", "EnableTitanium", "Titanium");
		Options.enableTungsten = material("tungsten", "EnableTungsten", "Tungsten");
		Options.enableUranium = material("uranium", "EnableUranium", "Uranium");
		Options.enableZirconium = material("zirconium", "EnableZirconium", "Zirconium");

		if (configuration.hasChanged()) {
			configuration.save();
		}
	}

    /** Configuration values are persisted immediately; live registries are never rebuilt. */
    public static Configuration getConfiguration() {
        if (configuration == null) init();
        return configuration;
    }

    /** Define the restart-required selector and normalize unsupported file values. */
    static String contentMode(final Configuration config) {
        final net.minecraftforge.common.config.Property property = config.get("General", "contentMode",
                "high_fantasy", "Fantasy level for Modern Metals recipes and equipment. Requires a game restart.");
        property.setValidValues(new String[]{"high_fantasy", "low_fantasy"});
        property.setLanguageKey("config.modernmetals.content_mode");
        property.setRequiresMcRestart(true);
        final String mode = zone.moddev.mc.modernmetals.content.ContentPolicy.forMode(property.getString())
                .lowFantasy() ? "low_fantasy" : "high_fantasy";
        property.set(mode);
        return mode;
    }

    private static boolean material(final String name, final String key, final String display) {
        final net.minecraftforge.common.config.Property property = configuration.get(MATERIALS_CAT,
                key, true, "Enable " + display + " Items and Materials. Requires a game restart.");
        property.setRequiresMcRestart(true);
        property.setLanguageKey("config.modernmetals.enable_" + name);
        final boolean enabled = property.getBoolean();
        com.mcmoddev.lib.util.ConfigBase.Options.materialEnabled(name, enabled);
        com.mcmoddev.lib.util.ConfigBase.Options.fluidEnabled(name, enabled);
        return enabled;
    }

	public static class Options {

		// MATERIALS
		public static boolean enableThorium = true;
		public static boolean enableBoron = true;
		public static boolean enableBeryllium = true;
		public static boolean enableAluminum = true;
		public static boolean enableAluminumBrass = true;
		public static boolean enableCadmium= true;
		public static boolean enableChromium = true;
		public static boolean enableGalvanizedSteel = true;
		public static boolean enableIridium = true;
		public static boolean enableMagnesium = true;
		public static boolean enableManganese = true;
		public static boolean enableNichrome= true;
		public static boolean enableOsmium = true;
		public static boolean enablePlutonium = true;
		public static boolean enableRutile = true;
		public static boolean enableStainlessSteel = true;
		public static boolean enableTantalum = true;
		public static boolean enableTitanium = true;
		public static boolean enableTungsten = true;
		public static boolean enableUranium = true;
		public static boolean enableZirconium = true;

		private Options() {
			throw new IllegalAccessError("Not a instantiable class");
		}
	}

	public static void postInit() {
		CrusherRecipeRegistry.getInstance().clearCache();
	}
}

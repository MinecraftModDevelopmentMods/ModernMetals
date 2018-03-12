package com.mcmoddev.modernmetals.util;

import java.io.File;

import com.mcmoddev.lib.registry.CrusherRecipeRegistry;
import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Jasmine Iwanek
 *
 */
public class Config extends com.mcmoddev.lib.util.ConfigBase {

	private static Configuration configuration;
	private static final String CONFIG_FILE = "config/ModernMetals.cfg";
	private static final String MATERIALS_CAT = "Metals";
	private static final String FLUIDS_CAT = "Fluids";

	@SubscribeEvent
	public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent e) {
		if (e.getModID().equals(ModernMetals.MODID)) {
			init();
		}
	}

	public static void init() {
		if (configuration == null) {
			configuration = new Configuration(new File(CONFIG_FILE));
			MinecraftForge.EVENT_BUS.register(new Config());
		}

		// METALS
		Options.materialEnabled(MaterialNames.ALUMINUM,
				configuration.getBoolean("EnableAluminum", MATERIALS_CAT, true, "Enable Aluminum Items and Materials"));
		Options.materialEnabled(MaterialNames.ALUMINUM_BRASS, configuration.getBoolean("EnableAluminumBrass",
				MATERIALS_CAT, true, "Enable Aluminum Brass Items and Materials"));
		Options.materialEnabled(MaterialNames.BERYLLIUM, configuration.getBoolean("EnableBeryllium", MATERIALS_CAT,
				true, "Enable Beryllium Items and Materials"));
		Options.materialEnabled(MaterialNames.BORON,
				configuration.getBoolean("EnableBoron", MATERIALS_CAT, true, "Enable Boron Items and Materials"));
		Options.materialEnabled(MaterialNames.CADMIUM,
				configuration.getBoolean("EnableCadmium", MATERIALS_CAT, true, "Enable Cadmium Items and Materials"));
		Options.materialEnabled(MaterialNames.CHROMIUM,
				configuration.getBoolean("EnableChromium", MATERIALS_CAT, true, "Enable Chromium Items and Materials"));
		Options.materialEnabled(MaterialNames.GALVANIZED_STEEL, configuration.getBoolean("EnableGalvanizedSteel",
				MATERIALS_CAT, true, "Enable Galvanized Steel Items and Materials"));
		Options.materialEnabled(MaterialNames.IRIDIUM,
				configuration.getBoolean("EnableIridium", MATERIALS_CAT, true, "Enable Iridium Items and Materials"));
		Options.materialEnabled(MaterialNames.MAGNESIUM, configuration.getBoolean("EnableMagnesium", MATERIALS_CAT,
				true, "Enable Magnesium Items and Materials"));
		Options.materialEnabled(MaterialNames.MANGANESE, configuration.getBoolean("EnableManganese", MATERIALS_CAT,
				true, "Enable Manganese Items and Materials"));
		Options.materialEnabled(MaterialNames.NICHROME,
				configuration.getBoolean("EnableNichrome", MATERIALS_CAT, true, "Enable Nichrome Items and Materials"));
		Options.materialEnabled(MaterialNames.OSMIUM,
				configuration.getBoolean("EnableOsmium", MATERIALS_CAT, true, "Enable Osmium Items and Materials"));
		Options.materialEnabled(MaterialNames.PLUTONIUM, configuration.getBoolean("EnablePlutonium", MATERIALS_CAT,
				true, "Enable Plutonium Items and Materials"));
		Options.materialEnabled(MaterialNames.RUTILE,
				configuration.getBoolean("EnableRutile", MATERIALS_CAT, true, "Enable Rutile Items and Materials"));
		Options.materialEnabled(MaterialNames.STAINLESS_STEEL, configuration.getBoolean("EnableStainlessSteel",
				MATERIALS_CAT, true, "Enable Stainless Steel Items and Materials"));
		Options.materialEnabled(MaterialNames.TANTALUM,
				configuration.getBoolean("EnableTantalum", MATERIALS_CAT, true, "Enable Tantalum Items and Materials"));
		Options.materialEnabled(MaterialNames.THORIUM,
				configuration.getBoolean("EnableThorium", MATERIALS_CAT, true, "Enable Thorium Items and Materials"));
		Options.materialEnabled(MaterialNames.TITANIUM,
				configuration.getBoolean("EnableTitanium", MATERIALS_CAT, true, "Enable Titanium Items and Materials"));
		Options.materialEnabled(MaterialNames.TUNGSTEN,
				configuration.getBoolean("EnableTungsten", MATERIALS_CAT, true, "Enable Tungsten Items and Materials"));
		Options.materialEnabled(MaterialNames.URANIUM,
				configuration.getBoolean("EnableUranium", MATERIALS_CAT, true, "Enable Uranium Items and Materials"));
		Options.materialEnabled(MaterialNames.ZIRCONIUM, configuration.getBoolean("EnableZirconium", MATERIALS_CAT,
				true, "Enable Zirconium Items and Materials"));

		Options.fluidEnabled("Aluminum",
				configuration.getBoolean("Enabled Aluminum", FLUIDS_CAT, true, "Enable the molten fluid of Aluminum"));
		Options.fluidEnabled("Beryllium",
				configuration.getBoolean("Enabled Beryllium", FLUIDS_CAT, true, "Enable the molten fluid of Beryllium"));
		Options.fluidEnabled("Boron",
				configuration.getBoolean("Enabled Boron", FLUIDS_CAT, true, "Enable the molten fluid of Boron"));
		Options.fluidEnabled("Chromium",
				configuration.getBoolean("Enabled Chromium", FLUIDS_CAT, true, "Enable the molten fluid of Chromium"));
		Options.fluidEnabled("Magnesium",
				configuration.getBoolean("Enabled Magnesium", FLUIDS_CAT, true, "Enable the molten fluid of Magnesium"));
		Options.fluidEnabled("Manganese",
				configuration.getBoolean("Enabled Manganese", FLUIDS_CAT, true, "Enable the molten fluid of Manganese"));
		Options.fluidEnabled("Osmium",
				configuration.getBoolean("Enabled Osmium", FLUIDS_CAT, true, "Enable the molten fluid of Osmium"));
		Options.fluidEnabled("Rutile",
				configuration.getBoolean("Enabled Rutile", FLUIDS_CAT, true, "Enable the molten fluid of Rutile"));
		Options.fluidEnabled("Tantalum",
				configuration.getBoolean("Enabled Tantalum", FLUIDS_CAT, true, "Enable the molten fluid of Tantalum"));
		Options.fluidEnabled("Thorium",
				configuration.getBoolean("Enabled Thorium", FLUIDS_CAT, true, "Enable the molten fluid of Thorium"));
		Options.fluidEnabled("Tungsten",
				configuration.getBoolean("Enabled Tungsten", FLUIDS_CAT, true, "Enable the molten fluid of Tungsten"));
		Options.fluidEnabled("Zirconium",
				configuration.getBoolean("Enabled Zirconium", FLUIDS_CAT, true, "Enable the molten fluid of Zirconium"));
		Options.fluidEnabled("Cadmium",
				configuration.getBoolean("Enabled Cadmium", FLUIDS_CAT, true, "Enable the molten fluid of Cadmium"));
		Options.fluidEnabled("Iridium",
				configuration.getBoolean("Enabled Iridium", FLUIDS_CAT, true, "Enable the molten fluid of Iridium"));
		Options.fluidEnabled("Plutonium",
				configuration.getBoolean("Enabled Plutonium", FLUIDS_CAT, true, "Enable the molten fluid of Plutonium"));
		Options.fluidEnabled("Uranium",
				configuration.getBoolean("Enabled Uranium", FLUIDS_CAT, true, "Enable the molten fluid of Uranium"));
		Options.fluidEnabled("AluminumBrass",
				configuration.getBoolean("Enabled Aluminum Brass", FLUIDS_CAT, true, "Enable the molten fluid of Aluminum Brass"));
		Options.fluidEnabled("GalvanizedSteel",
				configuration.getBoolean("Enabled Galvanized Steel", FLUIDS_CAT, true, "Enable the molten fluid of Galvanized Steel"));
		Options.fluidEnabled("Nichrome",
				configuration.getBoolean("Enabled Nichrome", FLUIDS_CAT, true, "Enable the molten fluid of Nichrome"));
		Options.fluidEnabled("StainlessSteel",
				configuration.getBoolean("Enabled Stainless Steel", FLUIDS_CAT, true, "Enable the molten fluid of Stainless Steel"));
		Options.fluidEnabled("Titanium",
				configuration.getBoolean("Enabled Titanium", FLUIDS_CAT, true, "Enable the molten fluid of Titanium"));

		if (configuration.hasChanged()) {
			configuration.save();
		}
	}

	public static void postInit() {
		CrusherRecipeRegistry.getInstance().clearCache();
	}
}

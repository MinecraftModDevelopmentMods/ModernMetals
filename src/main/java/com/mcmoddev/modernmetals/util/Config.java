package com.mcmoddev.modernmetals.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.AdditionalLootTables;
import com.mcmoddev.modernmetals.data.MaterialNames;
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
 * @author Jasmine Iwanek
 *
 */
public class Config extends com.mcmoddev.lib.util.ConfigBase {

	private static Configuration configuration;
	private static final String CONFIG_FILE = "config/ModernMetals.cfg";
	private static final String MATERIALS_CAT = "Metals";

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
		Options.materialEnabled(MaterialNames.ALUMINUM, configuration.getBoolean("EnableAluminum", MATERIALS_CAT, true, "Enable Aluminum Items and Materials"));
		Options.materialEnabled(MaterialNames.ALUMINUM_BRASS, configuration.getBoolean("EnableAluminumBrass", MATERIALS_CAT, true, "Enable Aluminum Brass Items and Materials"));
		Options.materialEnabled(MaterialNames.CADMIUM, configuration.getBoolean("EnableCadmium", MATERIALS_CAT, true, "Enable Cadmium Items and Materials"));
		Options.materialEnabled(MaterialNames.CHROMIUM, configuration.getBoolean("EnableChromium", MATERIALS_CAT, true, "Enable Chromium Items and Materials"));
		Options.materialEnabled(MaterialNames.GALVANIZED_STEEL, configuration.getBoolean("EnableGalvanizedSteel", MATERIALS_CAT, true, "Enable Galvanized Steel Items and Materials"));
		Options.materialEnabled(MaterialNames.IRIDIUM, configuration.getBoolean("EnableIridium", MATERIALS_CAT, true, "Enable Iridium Items and Materials"));
		Options.materialEnabled(MaterialNames.MAGNESIUM, configuration.getBoolean("EnableMagnesium", MATERIALS_CAT, true, "Enable Magnesium Items and Materials"));
		Options.materialEnabled(MaterialNames.MANGANESE, configuration.getBoolean("EnableManganese", MATERIALS_CAT, true, "Enable Manganese Items and Materials"));
		Options.materialEnabled(MaterialNames.NICHROME, configuration.getBoolean("EnableNichrome", MATERIALS_CAT, true, "Enable Nichrome Items and Materials"));
		Options.materialEnabled(MaterialNames.OSMIUM, configuration.getBoolean("EnableOsmium", MATERIALS_CAT, true, "Enable Osmium Items and Materials"));
		Options.materialEnabled(MaterialNames.PLUTONIUM, configuration.getBoolean("EnablePlutonium", MATERIALS_CAT, true, "Enable Plutonium Items and Materials"));
		Options.materialEnabled(MaterialNames.RUTILE, configuration.getBoolean("EnableRutile", MATERIALS_CAT, true, "Enable Rutile Items and Materials"));
		Options.materialEnabled(MaterialNames.STAINLESS_STEEL, configuration.getBoolean("EnableStainlessSteel", MATERIALS_CAT, true, "Enable Stainless Steel Items and Materials"));
		Options.materialEnabled(MaterialNames.TANTALUM, configuration.getBoolean("EnableTantalum", MATERIALS_CAT, true, "Enable Tantalum Items and Materials"));
		Options.materialEnabled(MaterialNames.TITANIUM, configuration.getBoolean("EnableTitanium", MATERIALS_CAT, true, "Enable Titanium Items and Materials"));
		Options.materialEnabled(MaterialNames.TUNGSTEN, configuration.getBoolean("EnableTungsten", MATERIALS_CAT, true, "Enable Tungsten Items and Materials"));
		Options.materialEnabled(MaterialNames.URANIUM, configuration.getBoolean("EnableUranium", MATERIALS_CAT, true, "Enable Uranium Items and Materials"));
		Options.materialEnabled(MaterialNames.ZIRCONIUM, configuration.getBoolean("EnableZirconium", MATERIALS_CAT, true, "Enable Zirconium Items and Materials"));

		if (configuration.hasChanged()) {
			configuration.save();
		}

		if ((com.mcmoddev.basemetals.util.Config.Options.requireMMDOreSpawn()) && (!Loader.isModLoaded("orespawn"))) {
			final HashSet<ArtifactVersion> orespawnMod = new HashSet<>();
			orespawnMod.add(new DefaultArtifactVersion("3.1.0"));
			throw new MissingModsException(orespawnMod, "orespawn", "MMD Ore Spawn Mod");
		}
	}

	public static void postInit() {
		CrusherRecipeRegistry.getInstance().clearCache();
	}
}

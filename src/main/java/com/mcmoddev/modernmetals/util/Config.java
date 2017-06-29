package com.mcmoddev.modernmetals.util;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.AdditionalLootTables;
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
	private static final String ALT_CFG_PATH = "config/additional-loot-tables";

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
		Options.enableAluminum = configuration.getBoolean("EnableAluminum", MATERIALS_CAT, true, "Enable Aluminum Items and Materials");
		Options.enableAluminumBrass = configuration.getBoolean("EnableAluminumBrass", MATERIALS_CAT, true, "Enable Aluminum Brass Items and Materials");
		Options.enableCadmium = configuration.getBoolean("EnableCadmium", MATERIALS_CAT, true, "Enable Cadmium Items and Materials");
		Options.enableChromium = configuration.getBoolean("EnableChromium", MATERIALS_CAT, true, "Enable Chromium Items and Materials");
		Options.enableGalvanizedSteel = configuration.getBoolean("EnableGalvanizedSteel", MATERIALS_CAT, true, "Enable Galvanized Steel Items and Materials");
		Options.enableIridium = configuration.getBoolean("EnableIridium", MATERIALS_CAT, true, "Enable Iridium Items and Materials");
		Options.enableMagnesium = configuration.getBoolean("EnableMagnesium", MATERIALS_CAT, true, "Enable Magnesium Items and Materials");
		Options.enableManganese = configuration.getBoolean("EnableManganese", MATERIALS_CAT, true, "Enable Manganese Items and Materials");
		Options.enableNichrome = configuration.getBoolean("EnableNichrome", MATERIALS_CAT, true, "Enable Nichrome Items and Materials");
		Options.enableOsmium = configuration.getBoolean("EnableOsmium", MATERIALS_CAT, true, "Enable Osmium Items and Materials");
		Options.enablePlutonium = configuration.getBoolean("EnablePlutonium", MATERIALS_CAT, true, "Enable Plutonium Items and Materials");
		Options.enableRutile = configuration.getBoolean("EnableRutile", MATERIALS_CAT, true, "Enable Rutile Items and Materials");
		Options.enableStainlessSteel = configuration.getBoolean("EnableStainlessSteel", MATERIALS_CAT, true, "Enable Stainless Steel Items and Materials");
		Options.enableTantalum = configuration.getBoolean("EnableTantalum", MATERIALS_CAT, true, "Enable Tantalum Items and Materials");
		Options.enableTitanium = configuration.getBoolean("EnableTitanium", MATERIALS_CAT, true, "Enable Titanium Items and Materials");
		Options.enableTungsten = configuration.getBoolean("EnableTungsten", MATERIALS_CAT, true, "Enable Tungsten Items and Materials");
		Options.enableUranium = configuration.getBoolean("EnableUranium", MATERIALS_CAT, true, "Enable Uranium Items and Materials");
		Options.enableZirconium = configuration.getBoolean("EnableZirconium", MATERIALS_CAT, true, "Enable Zirconium Items and Materials");

		if (configuration.hasChanged()) {
			configuration.save();
		}

		if (com.mcmoddev.basemetals.util.Config.Options.requireMMDOreSpawn()) {
			if (!Loader.isModLoaded("orespawn")) {
				final HashSet<ArtifactVersion> orespawnMod = new HashSet<>();
				orespawnMod.add(new DefaultArtifactVersion("3.0.0"));
				throw new MissingModsException(orespawnMod, "orespawn", "MMD Ore Spawn Mod");
			}
		}

		final Path myLootFolder = Paths.get(ALT_CFG_PATH, ModernMetals.MODID);
		if (!(myLootFolder.toFile().exists())) {
			try {
				final String chests = "chests";
				Files.createDirectories(myLootFolder.resolve(chests));
				Files.write(myLootFolder.resolve(chests).resolve("abandoned_mineshaft.json"), Collections.singletonList(AdditionalLootTables.ABANDONED_MINESHAFT));
				Files.write(myLootFolder.resolve(chests).resolve("desert_pyramid.json"), Collections.singletonList(AdditionalLootTables.DESERT_PYRAMID));
				Files.write(myLootFolder.resolve(chests).resolve("end_city_treasure.json"), Collections.singletonList(AdditionalLootTables.END_CITY_TREASURE));
				Files.write(myLootFolder.resolve(chests).resolve("jungle_temple.json"), Collections.singletonList(AdditionalLootTables.JUNGLE_TEMPLE));
				Files.write(myLootFolder.resolve(chests).resolve("nether_bridge.json"), Collections.singletonList(AdditionalLootTables.NETHER_BRIDGE));
				Files.write(myLootFolder.resolve(chests).resolve("simple_dungeon.json"), Collections.singletonList(AdditionalLootTables.SIMPLE_DUNGEON));
				Files.write(myLootFolder.resolve(chests).resolve("spawn_bonus_chest.json"), Collections.singletonList(AdditionalLootTables.SPAWN_BONUS_CHEST));
				Files.write(myLootFolder.resolve(chests).resolve("stronghold_corridor.json"), Collections.singletonList(AdditionalLootTables.STRONGHOLD_CORRIDOR));
				Files.write(myLootFolder.resolve(chests).resolve("stronghold_crossing.json"), Collections.singletonList(AdditionalLootTables.STRONGHOLD_CROSSING));
				Files.write(myLootFolder.resolve(chests).resolve("village_blacksmith.json"), Collections.singletonList(AdditionalLootTables.VILLAGE_BLACKSMITH));
			} catch (final IOException ex) {
				ModernMetals.logger.error("Failed to extract additional loot tables", ex);
			}
		}
	}

	public static class Options {

		// MATERIALS
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

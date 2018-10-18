package com.mcmoddev.modernmetals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mcmoddev.modernmetals.proxy.CommonProxy;
import com.mcmoddev.modernmetals.util.MMeConfig;
import com.mcmoddev.lib.data.SharedStrings;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * This is the entry point for this Mod.
 *
 * @author Jasmine Iwanek
 *
 */
@Mod(
		modid = ModernMetals.MODID,
		name = ModernMetals.NAME,
		version = ModernMetals.VERSION,
		dependencies = "required-after:forge@[14.21.1.2387,);required-after:basemetals;after:tconstruct;after:conarm;before:buildingbricks",
		acceptedMinecraftVersions = "[1.12,)",
		certificateFingerprint = "@FINGERPRINT@",
		updateJSON = ModernMetals.UPDATEJSON)
public class ModernMetals {

	@Instance
	public static ModernMetals instance;

	/** ID of this Mod. */
	public static final String MODID = "modernmetals";

	/** Display name of this Mod. */
	protected static final String NAME = "Modern Metals";

	/**
	 * Version number, in Major.Minor.Build format. The minor number is
	 * increased whenever a change is made that has the potential to break
	 * compatibility with other mods that depend on this one.
	 */
	protected static final String VERSION = "2.5.0-beta6";

	protected static final String UPDATEJSON = SharedStrings.UPDATE_JSON_URL + "ModernMetals/master/update.json";

	private static final String PROXY_BASE = SharedStrings.MMD_PROXY_GROUP + MODID + SharedStrings.DOT_PROXY_DOT;

	@SidedProxy(clientSide = PROXY_BASE + SharedStrings.CLIENTPROXY, serverSide = PROXY_BASE
		+ SharedStrings.SERVERPROXY)
	public static CommonProxy proxy;

	public static final Logger logger = LogManager.getFormatterLogger(ModernMetals.MODID);

	/**
	 * 
	 * @param event The Event.
	 */
	@EventHandler
	public void onFingerprintViolation(FMLFingerprintViolationEvent event) {
		if (!(Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment")) {
//			logger.warn("The Mod " + MODID + " is expecting signature " + event.getExpectedFingerprint() + " for source "+ event.getSource() + ", however there is no signature matching that description")
			logger.warn(SharedStrings.INVALID_FINGERPRINT);
		}
	}

	@EventHandler
	public static void constructing(final FMLConstructionEvent event) {
		MMeConfig.init();
	}

	/**
	 * 
	 * @param event The Event.
	 */
	@EventHandler
	public static void preInit(final FMLPreInitializationEvent event) {
		proxy.preInit(event);

		MinecraftForge.EVENT_BUS.register(com.mcmoddev.modernmetals.init.Items.class);
		MinecraftForge.EVENT_BUS.register(com.mcmoddev.modernmetals.init.Blocks.class);
	}

	@EventHandler
	public static void init(final FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public static void postInit(final FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}

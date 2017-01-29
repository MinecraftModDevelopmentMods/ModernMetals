package com.mcmoddev.modernmetals;

import com.mcmoddev.modernmetals.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * This is the entry point for this Mod.
 *
 * @author Jasmine Iwanek
 *
 */
@Mod(modid = ModernMetals.MODID, name = ModernMetals.NAME, version = ModernMetals.VERSION, dependencies = "required-after:Forge@[12.17.0.1976,);required-after:basemetals;before:buildingbricks", acceptedMinecraftVersions = "[1.10.2,)", updateJSON = "https://raw.githubusercontent.com/MinecraftModDevelopment/ModernMetals/master/update.json")
public class ModernMetals {

	@Instance
	public static ModernMetals INSTANCE;

	/** ID of this Mod */
	public static final String MODID = "modernmetals";

	/** Display name of this Mod */
	public static final String NAME = "Modern Metals";

	/**
	 * Version number, in Major.Minor.Build format. The minor number is
	 * increased whenever a change is made that has the potential to break
	 * compatibility with other mods that depend on this one.
	 */
	public static final String VERSION = "0.12.0-beta1";

	@SidedProxy(clientSide = "com.mcmoddev.modernmetals.proxy.ClientProxy", serverSide = "com.mcmoddev.modernmetals.proxy.ServerProxy")
	public static CommonProxy PROXY;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		PROXY.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		PROXY.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		PROXY.postInit(event);
	}

	@EventHandler
	public void onRemap(FMLMissingMappingsEvent event) {
		PROXY.onRemap(event);
	}
}

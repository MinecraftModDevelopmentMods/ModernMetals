package com.mcmoddev.modernmetals.proxy;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.modernmetals.init.*;
import com.mcmoddev.modernmetals.util.Config;
import com.mcmoddev.modernmetals.util.EventHandler;
import com.mcmoddev.lib.integration.IntegrationManager;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent.MissingMapping;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Modern Metals Common Proxy
 *
 * @author Jasmine Iwanek
 *
 */
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {

		Config.init();

		Materials.init();
		Fluids.init();
		ItemGroups.init();
		Blocks.init();
		Items.init();
		VillagerTrades.init();
		
		FMLInterModComms.sendFunctionMessage("orespawn", "api", "com.mcmoddev.orespawn.ModernMetalsOreSpawn");

		IntegrationManager.INSTANCE.preInit(event);
	}

	public void onRemap(FMLMissingMappingsEvent event) {
		for (final MissingMapping mapping : event.get()) {
			if (mapping.resourceLocation.getResourceDomain().equals(ModernMetals.MODID)) {
				if (mapping.type.equals(GameRegistry.Type.BLOCK)) {
					// Dummy
				} else if (mapping.type.equals(GameRegistry.Type.ITEM)) {
					// Dummy
				}
			}
		}
	}

	public void init(FMLInitializationEvent event) {
		Recipes.init();

		Achievements.init();

		MinecraftForge.EVENT_BUS.register(new EventHandler());

		ItemGroups.setupIcons(MaterialNames.ALUMINUM);
	}

	public void postInit(FMLPostInitializationEvent event) {
		Config.postInit();
	}
}

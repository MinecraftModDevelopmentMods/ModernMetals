package com.mcmoddev.modernmetals.proxy;

import java.util.HashSet;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.init.*;
import com.mcmoddev.modernmetals.util.Config;
import com.mcmoddev.modernmetals.util.EventHandler;
import com.mcmoddev.lib.integration.IntegrationManager;
import com.mcmoddev.lib.util.ConfigBase.Options;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.MissingModsException;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * Modern Metals Common Proxy
 *
 * @author Jasmine Iwanek
 *
 */
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ModernMetals.logger.debug("CommonProxy preInit() with event %s", event.description());
		MinecraftForge.EVENT_BUS.register(com.mcmoddev.modernmetals.init.Items.class);
		MinecraftForge.EVENT_BUS.register(com.mcmoddev.modernmetals.init.Blocks.class);

		Config.init();

		if ((Options.requireMMDOreSpawn()) && (!Loader.isModLoaded("orespawn"))) {
			final HashSet<ArtifactVersion> orespawnMod = new HashSet<>();
			orespawnMod.add(new DefaultArtifactVersion("3.1.0"));
			throw new MissingModsException(orespawnMod, "orespawn", "MMD Ore Spawn Mod");
		}

		Materials.init();
		Fluids.init();
		ItemGroups.init();
		Blocks.init();
		Items.init();
		VillagerTrades.init();
		ItemGroups.setupIcons();
		
		IntegrationManager.INSTANCE.preInit(event);
		MinecraftForge.EVENT_BUS.register(com.mcmoddev.modernmetals.proxy.CommonProxy.class);
	}
	
	@SubscribeEvent
	public void onRemapBlock(RegistryEvent.MissingMappings<Block> event) {
		for ( final RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getAllMappings() ) {
			if( mapping.key.getResourceDomain().equals(ModernMetals.MODID) ) {
				// dummy
			}
		}
	}

	@SubscribeEvent
	public void onRemapItem(RegistryEvent.MissingMappings<Item> event) {
		for ( final RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getAllMappings() ) {
			if( mapping.key.getResourceDomain().equals(ModernMetals.MODID) ) {
				// dummy
			}
		}
	}


	public void init(FMLInitializationEvent event) {
		ModernMetals.logger.debug("CommonProxy init() with event %s", event.description());
		Recipes.init();

		Achievements.init();

		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}

	public void postInit(FMLPostInitializationEvent event) {
		ModernMetals.logger.debug("CommonProxy postInit() with event %s", event.description());
		Config.postInit();
	}
}

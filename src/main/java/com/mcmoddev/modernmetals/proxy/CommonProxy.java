package com.mcmoddev.modernmetals.proxy;

import java.util.HashSet;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.init.*;
import com.mcmoddev.modernmetals.util.Config;
import com.mcmoddev.modernmetals.util.EventHandler;
import com.mcmoddev.lib.integration.IntegrationManager;
import com.mcmoddev.lib.util.ConfigBase.Options;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.MissingModsException;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;

/**
 * Modern Metals Common Proxy
 *
 * @author Jasmine Iwanek
 *
 */
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ModernMetals.logger.debug("CommonProxy preInit() with event %s", event.description());

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
	}

	/*  Right now this isn't needed, but might be at some point
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
	}*/

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

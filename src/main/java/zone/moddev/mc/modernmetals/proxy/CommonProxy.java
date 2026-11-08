package zone.moddev.mc.modernmetals.proxy;

import zone.moddev.mc.modernmetals.content.ContentModeNetwork;
import zone.moddev.mc.modernmetals.compat.LegacyRegistryMappings;
import zone.moddev.mc.modernmetals.data.MaterialNames;
import zone.moddev.mc.modernmetals.init.*;
import zone.moddev.mc.modernmetals.util.Config;
import zone.moddev.mc.modernmetals.util.EventHandler;
import com.mcmoddev.lib.integration.IntegrationManager;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Modern Metals Common Proxy
 *
 * @author Jasmine Iwanek
 *
 */
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {

		Config.init();
		ContentModeNetwork.register();

		Materials.init();
		Fluids.init();
		ItemGroups.init();
		Blocks.init();
		Items.init();
		VillagerTrades.init();

		IntegrationManager.INSTANCE.preInit(event);
	}

	public void onRemap(FMLMissingMappingsEvent event) {
		LegacyRegistryMappings.remap(event);
	}

	public void init(FMLInitializationEvent event) {
		zone.moddev.mc.modernmetals.worldgen.ModernMetalsWorldgenProvider.enqueue();
        if (net.minecraftforge.fml.common.Loader.isModLoaded("mekanism"))
            zone.moddev.mc.modernmetals.integration.plugins.Mekanism.registerRecipes();
        if (net.minecraftforge.fml.common.Loader.isModLoaded("ic2"))
            zone.moddev.mc.modernmetals.integration.plugins.IC2.registerRecipes();
		Recipes.init();

		Achievements.init();

		MinecraftForge.EVENT_BUS.register(new EventHandler());

		ItemGroups.setupIcons(MaterialNames.ALUMINUM);
	}

	public void postInit(FMLPostInitializationEvent event) {
		Config.postInit();
	}
}

package zone.moddev.mc.modernmetals.proxy;


import zone.moddev.mc.modernmetals.content.ContentModeNetwork;
import zone.moddev.mc.modernmetals.data.MaterialNames;
import zone.moddev.mc.modernmetals.init.ItemGroups;
import zone.moddev.mc.modernmetals.init.VillagerTrades;
import zone.moddev.mc.modernmetals.util.MMeConfig;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Modern Metals Common Proxy.
 *
 * @author Jasmine Iwanek
 *
 */
public class CommonProxy {

	/**
	 *
	 * @param event The Event.
	 */
	@SuppressWarnings("deprecation")
	public void preInit(final FMLPreInitializationEvent event) {

        ContentModeNetwork.register();
        if (MMeConfig.villagerTradesEnabled()) {
            VillagerTrades.init();
        }
	}

	/**
	 *
	 * @param event The Event.
	 */
	public void init(final FMLInitializationEvent event) {
		ItemGroups.setupIcons(MaterialNames.ALUMINUM);
	}

	/**
	 *
	 * @param event The Event.
	 */
	public void postInit(FMLPostInitializationEvent event) {
		MMeConfig.postInit();
	}
}

package com.mcmoddev.modernmetals.proxy;

import com.mcmoddev.lib.client.registrations.RegistrationHelper;
import com.mcmoddev.modernmetals.init.Blocks;
import com.mcmoddev.modernmetals.init.Fluids;
import com.mcmoddev.modernmetals.init.Items;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Modern Metals Client Proxy
 *
 * @author Jasmine Iwanek
 *
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		for (final String name : Items.getItemRegistry().keySet()) {
			RegistrationHelper.registerItemRender(name);
		}

		for (final String name : Blocks.getBlockRegistry().keySet()) {
			RegistrationHelper.registerBlockRender(name);
		}

		for (final String name : Fluids.getFluidBlockRegistry().keySet()) {
			RegistrationHelper.registerFluidRender(name);
		}
	}
}

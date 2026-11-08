package zone.moddev.mc.modernmetals.proxy;

import com.mcmoddev.lib.client.registrations.RegistrationHelper;
import com.mcmoddev.lib.material.MMDMaterial;
import zone.moddev.mc.modernmetals.init.Blocks;
import zone.moddev.mc.modernmetals.init.Fluids;
import zone.moddev.mc.modernmetals.init.Items;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import zone.moddev.mc.modernmetals.ModernMetals;
import zone.moddev.mc.modernmetals.client.ModernMetalsConfigScreen;
import zone.moddev.mc.orespawn.api.client.WorldSettingsExtensionRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Modern Metals Client Proxy.
 *
 * @author Jasmine Iwanek
 *
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(final FMLPreInitializationEvent event) {
		super.preInit(event);

		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void init(final FMLInitializationEvent event) {
		super.init(event);
		WorldSettingsExtensionRegistry.registerConfigScreen(ModernMetals.MODID, ModernMetalsConfigScreen::new);
	}

	/**
	 * 
	 * @param event The Event.
	 */
	@SubscribeEvent
	public void fluidRendering(final RegistryEvent.Register<MMDMaterial> event) {
		for (final String name : Fluids.getFluidBlockRegistry().keySet()) {
			RegistrationHelper.registerFluidRender(name);
		}
	}

	/**
	 * 
	 * @param event The Event.
	 */
	@SubscribeEvent
	public void modelRegistry(final ModelRegistryEvent event) {
		for (final String name : Items.getItemRegistry().keySet()) {
			RegistrationHelper.registerItemRender(name);
		}

		for (final String name : Blocks.getBlockRegistry().keySet()) {
			RegistrationHelper.registerBlockRender(name);
		}
	}
}

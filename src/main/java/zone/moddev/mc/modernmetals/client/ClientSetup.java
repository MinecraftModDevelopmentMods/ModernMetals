package zone.moddev.mc.modernmetals.client;

import zone.moddev.mc.modernmetals.content.FluidContent;
import zone.moddev.mc.modernmetals.content.ModContent;
import zone.moddev.mc.modernmetals.entity.MaterialProjectile;

import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import zone.moddev.mc.orespawn.api.client.WorldSettingsExtensionRegistry;

public final class ClientSetup {
    private ClientSetup() {}

    public static void register() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY,
                () -> (minecraft, parent) -> new ModernMetalsConfigScreen(parent));
        WorldSettingsExtensionRegistry.registerConfigScreen("modernmetals", ModernMetalsConfigScreen::new);
        ClientMoltenMetalRenderer.register();
        MinecraftForge.EVENT_BUS.addListener(ClientSetup::registerBucketColours);
        MinecraftForge.EVENT_BUS.addListener(ModsListConfigButton::onDraw);
        RenderingRegistry.registerEntityRenderingHandler(MaterialProjectile.class,
                manager -> new MaterialProjectileRenderer(manager));
    }

    private static void registerBucketColours(ColorHandlerEvent.Item event) {
        for (final FluidContent content : ModContent.fluids().values()) {
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 1
                    ? ModContent.fluidColour(content.source().get()) : 0xFFFFFF,
                    content.bucket().get());
        }
    }
}

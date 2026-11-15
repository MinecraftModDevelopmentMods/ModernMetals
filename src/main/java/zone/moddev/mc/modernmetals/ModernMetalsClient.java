package zone.moddev.mc.modernmetals;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import zone.moddev.mc.modernmetals.content.LegacyProjectile;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import zone.moddev.mc.modernmetals.content.CompatibilityContent;
import zone.moddev.mc.modernmetals.content.ModContent;
import zone.moddev.mc.orespawn.api.client.WorldSettingsExtensionRegistry;

/**
 * Client-only entry point. MMDLib's runtime client installs projectile renderers,
 * fluid rendering and item properties for the registered content.
 */
@Mod(value = ModernMetals.MOD_ID, dist = Dist.CLIENT)
public final class ModernMetalsClient {
    public ModernMetalsClient(IEventBus bus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        WorldSettingsExtensionRegistry.registerConfigScreen(ModernMetals.MOD_ID,
                parent -> new ConfigurationScreen(container, parent));
        bus.addListener((EntityRenderersEvent.RegisterRenderers event) -> {
            for (var type : java.util.List.of(CompatibilityContent.OLD_ARROW, CompatibilityContent.OLD_BOLT)) {
                event.registerEntityRenderer(type.get(), context -> new ArrowRenderer<LegacyProjectile>(context) {
                    @Override public ResourceLocation getTextureLocation(LegacyProjectile entity) {
                        return ResourceLocation.withDefaultNamespace("textures/entity/projectiles/arrow.png");
                    }
                });
            }
        });
        bus.addListener((BuildCreativeModeTabContentsEvent event) -> {
            if (event.getTabKey().location().equals(ResourceLocation.fromNamespaceAndPath(ModernMetals.MOD_ID, "materials"))
                    && !ModList.get().isLoaded("basemetals")) event.accept(CompatibilityContent.COAL_POWDER.get());
        });
        bus.addListener((RegisterColorHandlersEvent.Block event) -> CompatibilityContent.legacyOres().forEach((name,block) ->
                event.register((state, world, pos, layer) -> ModContent.materials().get(name).properties().color(), block.get())));
        bus.addListener((RegisterColorHandlersEvent.Item event) -> CompatibilityContent.legacyOres().forEach((name,block) ->
                event.register((stack, layer) -> ModContent.materials().get(name).properties().color(), block.get().asItem())));
    }
}

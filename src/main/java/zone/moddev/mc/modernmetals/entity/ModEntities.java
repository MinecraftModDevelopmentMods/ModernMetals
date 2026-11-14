package zone.moddev.mc.modernmetals.entity;

import zone.moddev.mc.modernmetals.ModernMetals;
import zone.moddev.mc.modernmetals.content.RegistryHandle;

import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModernMetals.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModEntities {
    public static final RegistryHandle<EntityType<MaterialProjectile>> CUSTOM_ARROW =
            new RegistryHandle<EntityType<MaterialProjectile>>("custom_arrow");
    public static final RegistryHandle<EntityType<MaterialProjectile>> CUSTOM_BOLT =
            new RegistryHandle<EntityType<MaterialProjectile>>("custom_bolt");

    private ModEntities() {}

    public static void initialize() {
        // Register the event subscriber before item factories ask for these entities.
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        CUSTOM_ARROW.bind(register(event, "custom_arrow", CUSTOM_ARROW));
        CUSTOM_BOLT.bind(register(event, "custom_bolt", CUSTOM_BOLT));
    }

    private static EntityType<MaterialProjectile> register(RegistryEvent.Register<EntityType<?>> event,
            String name, final RegistryHandle<EntityType<MaterialProjectile>> self) {
        EntityType<MaterialProjectile> type = EntityType.Builder
                .create(MaterialProjectile.class, world -> new MaterialProjectile(self.get(), world))
                .tracker(64, 20, true)
                .build(ModernMetals.MOD_ID + ":" + name);
        type.setRegistryName(ModernMetals.MOD_ID, name);
        event.getRegistry().register(type);
        return type;
    }
}

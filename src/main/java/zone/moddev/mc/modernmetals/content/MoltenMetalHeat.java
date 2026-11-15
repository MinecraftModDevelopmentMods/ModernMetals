package zone.moddev.mc.modernmetals.content;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import zone.moddev.mc.modernmetals.config.ModernMetalsConfig;

/** Molten metal burns creatures that wade into it, as lava does. */
public final class MoltenMetalHeat {
    private MoltenMetalHeat() {}

    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity entity) || entity.level().isClientSide()) return;
        if (!entity.isInFluidType((type, height) -> ModContent.moltenTypes().contains(type))) return;
        if (!ModernMetalsConfig.MOLTEN_METAL_BURNS.get()) return;
        // Respects fire immunity and Fire Resistance, as vanilla lava does.
        entity.lavaHurt();
    }
}

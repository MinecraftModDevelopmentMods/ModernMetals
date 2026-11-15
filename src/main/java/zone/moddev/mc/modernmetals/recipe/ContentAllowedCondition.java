package zone.moddev.mc.modernmetals.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import zone.moddev.mc.modernmetals.ModernMetals;
import zone.moddev.mc.modernmetals.config.ContentPolicy;

/**
 * Loads a recipe only when the Fantasy Level allows its output. The level is fixed
 * at startup, so the recipe set does not change while a world is open.
 *
 * <pre>{"type": "modernmetals:content_allowed", "item": "modernmetals:aluminum_sword"}</pre>
 */
public record ContentAllowedCondition(ResourceLocation item) implements ICondition {
    public static final MapCodec<ContentAllowedCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(ResourceLocation.CODEC.fieldOf("item").forGetter(ContentAllowedCondition::item))
                    .apply(instance, ContentAllowedCondition::new));

    private static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS =
            DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, ModernMetals.MOD_ID);

    static {
        CONDITIONS.register("content_allowed", () -> CODEC);
    }

    public static void register(IEventBus bus) {
        CONDITIONS.register(bus);
    }

    @Override
    public boolean test(IContext context) {
        return ContentPolicy.active().allows(item);
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}

package zone.moddev.mc.modernmetals.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import zone.moddev.mc.modernmetals.ModernMetals;

/** Dynamic recipes owned by Modern Metals; crushing remains in MMDLib. */
public final class ModRecipes {
    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, ModernMetals.MOD_ID);
    public static final DeferredHolder<RecipeSerializer<?>, PlateRepairRecipe.Serializer> PLATE_REPAIR_SERIALIZER =
            SERIALIZERS.register("plate_repair", PlateRepairRecipe.Serializer::new);
    private ModRecipes() {}
    public static void register(IEventBus bus) { SERIALIZERS.register(bus); }
}

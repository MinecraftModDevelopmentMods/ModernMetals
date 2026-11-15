package zone.moddev.mc.modernmetals.content;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import zone.moddev.mc.modernmetals.ModernMetals;

/** Content retained for older saves, plus the standalone coal-dust fallback. */
public final class CompatibilityContent {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, ModernMetals.MOD_ID);
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, ModernMetals.MOD_ID);
    private static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, ModernMetals.MOD_ID);
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, ModernMetals.MOD_ID);
    public static final Supplier<EntityType<LegacyProjectile>> OLD_ARROW = projectile("custom_arrow");
    public static final Supplier<EntityType<LegacyProjectile>> OLD_BOLT = projectile("custom_bolt");
    public static final Supplier<Item> COAL_POWDER = ITEMS.register("coal_powder", () -> new Item(new Item.Properties()));
    private static final Map<String, Supplier<Block>> LEGACY_ORES = new LinkedHashMap<>();
    private CompatibilityContent() {}

    static void register(IEventBus bus) {
        // Attach after the material registrar so alias targets register first.
        BLOCKS.register(bus);
        ITEMS.register(bus);
        FLUIDS.register(bus);
        ENTITIES.register(bus);
    }

    static void queue() {
        for (String name : ModContent.materials().keySet()) {
            ResourceLocation molten = id(name + "_molten");
            BLOCKS.addAlias(id(name), molten);
            FLUIDS.addAlias(id(name), molten);
            FLUIDS.addAlias(id("flowing_" + name), id("flowing_" + name + "_molten"));
            ITEMS.addAlias(id(name + "_bucket"), id(name + "_molten_bucket"));
            // These old fluid block items are hidden, but still preserve saved stacks.
            ITEMS.register(name, () -> new BlockItem(ModContent.registrar().blocks().get(molten).get(), new Item.Properties()));
        }
        for (String name : new String[] {"galvanizedsteel", "nichrome", "stainlesssteel"}) {
            var material = ModContent.materials().get(name).properties();
            Supplier<Block> block = BLOCKS.register(name + "_ore", () -> new Block(BlockBehaviour.Properties.of()
                    .strength((float) (material.hardness() * 0.5), (float) (material.strength() * 2.5))
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));
            LEGACY_ORES.put(name, block);
            ITEMS.register(name + "_ore", () -> new BlockItem(block.get(), new Item.Properties()));
        }
    }

    public static Map<String, Supplier<Block>> legacyOres() { return Collections.unmodifiableMap(LEGACY_ORES); }
    private static Supplier<EntityType<LegacyProjectile>> projectile(String name) {
        return ENTITIES.register(name, () -> EntityType.Builder.<LegacyProjectile>of(LegacyProjectile::new, MobCategory.MISC)
                .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build(id(name).toString()));
    }
    private static ResourceLocation id(String name) { return ResourceLocation.fromNamespaceAndPath(ModernMetals.MOD_ID, name); }
}

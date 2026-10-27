package zone.moddev.mc.modernmetals.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import zone.moddev.mc.modernmetals.ModernMetals;
import zone.moddev.mc.orespawn.api.GeologyFamily;
import zone.moddev.mc.orespawn.api.OreDimensionSelector;
import zone.moddev.mc.orespawn.api.OrePattern;
import zone.moddev.mc.orespawn.api.OreSpawnApi;
import zone.moddev.mc.orespawn.api.WorldgenProvider;

/** Native declarations only; OreSpawn resolves, bakes and executes generation. */
public final class ModernMetalsWorldgenProvider {
    private static final Ore[] ORES = {
        new Ore("boron", 0, 31, 8D, 8, 8, true),
        new Ore("thorium", 0, 31, 8D, 8, 4, true),
        new Ore("beryllium", 0, 127, 20D, 5, 3, true),
        new Ore("aluminum", 0, 95, 8D, 10, 8, true),
        new Ore("cadmium", 0, 95, 8D, 4, 8, true),
        new Ore("chromium", 0, 31, 3D, 2, 4, true),
        new Ore("iridium", 0, 63, 5D, 6, 4, true),
        new Ore("magnesium", 0, 95, 8D, 6, 4, true),
        new Ore("manganese", 0, 63, 6D, 6, 4, true),
        new Ore("osmium", 0, 95, 8D, 10, 4, true),
        new Ore("plutonium", 0, 31, 4D, 2, 4, true),
        new Ore("rutile", 0, 63, 6D, 6, 4, true),
        new Ore("tantalum", 0, 63, 6D, 6, 4, true),
        new Ore("tungsten", 0, 31, 6D, 4, 4, true),
        new Ore("uranium", 0, 31, 6D, 2, 4, true),
        new Ore("zirconium", 0, 63, 6D, 8, 4, true),
        new Ore("titanium", 0, 63, 6D, 6, 4, false)
    };
    private ModernMetalsWorldgenProvider() { }
    public static void enqueue() {
        build(ForgeRegistries.BLOCKS::containsKey).ifPresent(provider -> {
            if (!OreSpawnApi.enqueue(provider)) throw new IllegalStateException("Modern Metals provider registered twice");
        });
        for (final Ore ore : ORES) {
            if (ore.size - ore.variation < 1 && ForgeRegistries.BLOCKS.containsKey(id("modernmetals", ore.name + "_ore")))
                ModernMetals.logger.info("OreSpawn native quantity minimum for %s is clamped from %s to 1, matching the legacy importer",
                    ore.name, ore.size - ore.variation);
        }
    }
    public static Optional<WorldgenProvider> build(final Predicate<ResourceLocation> available) {
        final List<WorldgenProvider.OreDefinition> ores = new ArrayList<>();
        for (final Ore rule : ORES) {
            final ResourceLocation block = id("modernmetals", rule.name + "_ore");
            if (!available.test(block)) continue;
            final WorldgenProvider.OreDefinition.Builder ore = WorldgenProvider.OreDefinition
                .builder(id("modernmetals", "legacy/" + rule.name + "_ore"), block)
                .enabled(rule.enabled).nativeGeneration(false).retrogen(false)
                .material(id("orespawn", rule.name)).output(block, 100D);
            ore.dimensionSelector(OreDimensionSelector.ALL_EXCEPT_NETHER_AND_END, placement -> {
                placement.yRange(rule.minY, rule.maxY).attempts(rule.frequency)
                    .quantityRange(Math.max(1, rule.size - rule.variation), Math.min(64, rule.size + rule.variation - 1))
                    .pattern(OrePattern.DEFAULT).placementChannel(id("orespawn", "standard"))
                    .hostBlock(id("minecraft", "stone")).hostBlock(id("minecraft", "netherrack"))
                    .hostBlock(id("minecraft", "end_stone"));
                for (final GeologyFamily family : GeologyFamily.values()) placement.hostFamily(family);
            });
            ores.add(ore.build());
        }
        if (ores.isEmpty()) return Optional.empty();
        final WorldgenProvider.Builder provider = WorldgenProvider.builder("modernmetals", 1);
        ores.forEach(provider::ore);
        return Optional.of(provider.build());
    }
    private static ResourceLocation id(final String namespace, final String path) { return new ResourceLocation(namespace, path); }
    private static final class Ore {
        final String name;
        final int minY, maxY, size, variation;
        final double frequency;
        final boolean enabled;
        Ore(String name, int minY, int maxY, double frequency, int size, int variation, boolean enabled) {
            this.name = name; this.minY = minY; this.maxY = maxY; this.frequency = frequency;
            this.size = size; this.variation = variation; this.enabled = enabled;
        }
    }
}

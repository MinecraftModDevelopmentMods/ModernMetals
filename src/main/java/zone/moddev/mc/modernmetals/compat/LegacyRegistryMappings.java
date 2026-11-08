package zone.moddev.mc.modernmetals.compat;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zone.moddev.mc.modernmetals.ModernMetals;

/** Registry names written by Modern Metals 0.11.0. */
public final class LegacyRegistryMappings {
    private static final String OLD_BRASS = "alubrass";
    private static final ResourceLocation MOLTEN_BRASS = id("aluminumbrass");
    private static final Map<ResourceLocation, ResourceLocation> ITEM_ALIASES = itemAliases();

    private LegacyRegistryMappings() {}

    public static void remap(final FMLMissingMappingsEvent event) {
        for (final FMLMissingMappingsEvent.MissingMapping mapping : event.get()) {
            if (mapping.type == GameRegistry.Type.BLOCK && mapping.resourceLocation.equals(id(OLD_BRASS))
                    && ForgeRegistries.BLOCKS.containsKey(MOLTEN_BRASS)) {
                mapping.remap(ForgeRegistries.BLOCKS.getValue(MOLTEN_BRASS));
            } else if (mapping.type == GameRegistry.Type.ITEM) {
                final ResourceLocation replacement = ITEM_ALIASES.get(mapping.resourceLocation);
                // Some saves contain both the door block's item ID and the old door item ID.
                if (replacement != null && ForgeRegistries.ITEMS.containsKey(replacement)) mapping.ignore();
            }
        }
    }

    public static void restoreItemAliases() {
        final Map<ResourceLocation, ResourceLocation> aliases = new LinkedHashMap<>();
        ITEM_ALIASES.forEach((oldId, newId) -> {
            if (ForgeRegistries.ITEMS.containsKey(newId)) aliases.put(oldId, newId);
        });
        GameData.getItemRegistry().loadAliases(aliases);
    }

    private static Map<ResourceLocation, ResourceLocation> itemAliases() {
        final Map<ResourceLocation, ResourceLocation> aliases = new LinkedHashMap<>();
        aliases.put(id(OLD_BRASS), MOLTEN_BRASS);
        for (final String material : new String[] { "aluminum", "aluminumbrass", "cadmium", "chromium",
                "galvanizedsteel", "iridium", "magnesium", "manganese", "nichrome", "osmium",
                "plutonium", "rutile", "stainlesssteel", "tantalum", "titanium", "tungsten", "uranium", "zirconium" }) {
            aliases.put(id(material + "_door_item"), id(material + "_door"));
            aliases.put(id(material + "_fishingrod"), id(material + "_fishing_rod"));
            aliases.put(id(material + "_horsearmor"), id(material + "_horse_armor"));
        }
        return aliases;
    }

    private static ResourceLocation id(final String path) {
        return new ResourceLocation(ModernMetals.MODID, path);
    }
}

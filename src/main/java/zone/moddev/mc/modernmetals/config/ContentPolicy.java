package zone.moddev.mc.modernmetals.config;

import java.util.Set;

import net.minecraft.resources.ResourceLocation;

import zone.moddev.mc.modernmetals.ModernMetals;

/**
 * Limits which Modern Metals items can be made under Low Fantasy. Every item stays
 * registered, so existing stacks and placed blocks remain usable.
 */
public final class ContentPolicy {
    private static final Set<String> TOOLS = Set.of("axe", "crackhammer", "hoe", "pickaxe", "scythe",
            "shears", "shovel", "sword", "arrow", "bolt");
    private static final Set<String> PROTECTION = Set.of("boots", "chestplate", "helmet", "leggings",
            "horse_armor", "shield");
    private static final Set<String> RANGED = Set.of("bow", "crossbow", "fishing_rod");
    private static final Set<String> STRUCTURES = Set.of("bars", "button", "door", "lever",
            "pressure_plate", "slab", "stairs", "trapdoor", "wall");

    private static final Set<String> FULL_TOOLS = Set.of("galvanizedsteel", "stainlesssteel", "titanium");
    private static final Set<String> ARMOR = Set.of("aluminum", "aluminumbrass", "galvanizedsteel",
            "stainlesssteel", "titanium");
    private static final Set<String> GEARS = ARMOR;
    private static final Set<String> STRUCTURAL_METALS = Set.of("aluminum", "aluminumbrass",
            "galvanizedsteel", "nichrome", "stainlesssteel", "tantalum", "titanium", "tungsten", "zirconium");

    private static final ContentPolicy HIGH = new ContentPolicy(false);
    private static final ContentPolicy LOW = new ContentPolicy(true);

    private final boolean low;

    private ContentPolicy(boolean low) {
        this.low = low;
    }

    public static ContentPolicy forMode(ContentMode mode) {
        return mode == ContentMode.LOW_FANTASY ? LOW : HIGH;
    }

    public static ContentPolicy active() {
        return forMode(ModernMetalsConfig.activeMode());
    }

    public boolean lowFantasy() {
        return low;
    }

    /** Whether an item may be obtained; items from other mods are always allowed. */
    public boolean allows(ResourceLocation item) {
        if (!low || !ModernMetals.MOD_ID.equals(item.getNamespace())) return true;
        String path = item.getPath();
        int split = path.indexOf('_');
        // Material names have no underscore, so the first one separates the form.
        return split < 0 || allows(path.substring(0, split), path.substring(split + 1));
    }

    /** Whether {@code form} (such as {@code pickaxe}) may be made from {@code material}. */
    public boolean allows(String material, String form) {
        if (!low) return true;
        if (TOOLS.contains(form)) return FULL_TOOLS.contains(material);
        if (PROTECTION.contains(form)) return ARMOR.contains(material);
        if (RANGED.contains(form)) return false;
        if ("gear".equals(form)) return GEARS.contains(material);
        if (STRUCTURES.contains(form)) return STRUCTURAL_METALS.contains(material);
        return true;
    }
}

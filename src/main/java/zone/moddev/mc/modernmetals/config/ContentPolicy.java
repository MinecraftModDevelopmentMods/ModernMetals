package zone.moddev.mc.modernmetals.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import zone.moddev.mc.modernmetals.ModernMetals;

/** Restricts obtainability, while retaining every registered item and saved stack. */
public final class ContentPolicy {
    private static final Set<String> TOOLS = names("axe","crackhammer","hoe","pickaxe","scythe","shears","shovel","sword","arrow","bolt");
    private static final Set<String> PROTECTION = names("boots","chestplate","helmet","leggings","horse_armor","horsearmor","shield");
    private static final Set<String> FULL_TOOLS = names("galvanizedsteel","stainlesssteel","titanium");
    private static final Set<String> ARMOR = names("aluminum","aluminumbrass","galvanizedsteel","stainlesssteel","titanium");
    private static final Set<String> GEARS = names("aluminum","aluminumbrass","galvanizedsteel","stainlesssteel","titanium");
    private static final Set<String> STRUCTURAL_FORMS = names("bars","bookshelf","button","door","double_slab","doubleslab","fence","fence_gate","flower_pot","ladder","lever","pressure_plate","pressureplate","slab","stairs","trapdoor","tripwire_hook","wall");
    private static final Set<String> STRUCTURAL_METALS = names("aluminum","aluminumbrass","galvanizedsteel","nichrome","stainlesssteel","tantalum","titanium","tungsten","zirconium");
    private final boolean low;

    private ContentPolicy(boolean low) { this.low = low; }

    public static ContentPolicy active() { return EffectivePolicy.INSTANCE; }
    public boolean lowFantasy() { return low; }
    public ContentMode mode() { return low ? ContentMode.LOW_FANTASY : ContentMode.HIGH_FANTASY; }

    private static final class EffectivePolicy {
        private static final ContentPolicy INSTANCE = load();
    }

    private static ContentPolicy load() {
        String mode = inheritsBaseMetals() ? baseMode() : ModernMetalsConfig.activeMode();
        return new ContentPolicy("low_fantasy".equals(mode.toLowerCase(Locale.ROOT)));
    }

    public static boolean inheritsBaseMetals() {
        return ModList.get().isLoaded("basemetals");
    }

    private static String baseMode() {
        // Keep the optional Base Metals dependency off the standalone runtime classpath.
        try {
            Class<?> config = Class.forName("zone.moddev.mc.basemetals.config.BaseMetalsConfig");
            return config.getMethod("activeMode").invoke(null).toString();
        } catch (ReflectiveOperationException error) {
            throw new IllegalStateException("Could not read Base Metals Fantasy Level", error);
        }
    }

    public boolean allows(final String material,final String form) {
        if(!low || material==null || form==null) return true;
        if(TOOLS.contains(form)) return FULL_TOOLS.contains(material);
        if(PROTECTION.contains(form)) return ARMOR.contains(material);
        if ("bow".equals(form) || "crossbow".equals(form) || "fishing_rod".equals(form)
                || "fishingrod".equals(form)) return false;
        if("gear".equals(form)) return GEARS.contains(material);
        if("anvil".equals(form)) return "stainlesssteel".equals(material) || "galvanizedsteel".equals(material);
        if(STRUCTURAL_FORMS.contains(form)) return STRUCTURAL_METALS.contains(material);
        return true;
    }
    public boolean allows(String item) {
        ResourceLocation id = new ResourceLocation(item);
        if (!ModernMetals.MOD_ID.equals(id.getNamespace())) return true;
        String path = id.getPath();
        int split = path.indexOf('_');
        return split < 0 || allows(path.substring(0, split), path.substring(split + 1));
    }

    private static Set<String> names(final String... values) {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(values)));
    }
}

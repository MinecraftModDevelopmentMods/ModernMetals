package zone.moddev.mc.modernmetals.content;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;
import zone.moddev.mc.modernmetals.ModernMetals;
import zone.moddev.mc.modernmetals.util.MMeConfig;

/** Restricts obtainability, while retaining every registered item and saved stack. */
public final class ContentPolicy {
    private static final Set<String> TOOLS = names("axe","crackhammer","hoe","pickaxe","scythe","shears","shovel","sword","arrow","bolt");
    private static final Set<String> PROTECTION = names("boots","chestplate","helmet","leggings","horse_armor","horsearmor","shield");
    private static final Set<String> FULL_TOOLS = names("galvanizedsteel","stainlesssteel","titanium");
    private static final Set<String> ARMOR = names("aluminum","aluminumbrass","galvanizedsteel","stainlesssteel","titanium");
    private static final Set<String> GEARS = names("aluminum","aluminumbrass","galvanizedsteel","stainlesssteel","titanium");
    private static final Set<String> STRUCTURAL_FORMS = names("bars","bookshelf","button","door","double_slab","doubleslab","fence","fence_gate","flower_pot","ladder","lever","pressure_plate","pressureplate","slab","stairs","trapdoor","tripwire_hook","wall");
    private static final Set<String> STRUCTURAL_METALS = names("aluminum","aluminumbrass","galvanizedsteel","nichrome","stainlesssteel","tantalum","titanium","tungsten","zirconium");
    private static boolean initialized;
    private static ContentPolicy active = forMode("high_fantasy");
    private final boolean low;
    private ContentPolicy(final boolean low) { this.low=low; }
    public static ContentPolicy forMode(final String mode) {
        return new ContentPolicy(mode!=null && "low_fantasy".equals(mode.trim().toLowerCase(Locale.ROOT)));
    }
    public static ContentPolicy active() { return active; }
    public boolean lowFantasy() { return low; }
    /** Base Metals owns the mode when its startup configuration API is available. */
    public static boolean inheritsBaseMetals() {
        final ModContainer mod = Loader.instance().getIndexedModList().get("basemetals");
        return mod != null && new DefaultArtifactVersion(mod.getVersion())
                .compareTo(new DefaultArtifactVersion("2.6.1.112021")) >= 0;
    }

    public static void initialize() {
        if (initialized) return;
        final boolean inherited = inheritsBaseMetals();
        active = forMode(inherited ? BaseMetalsMode.read() : MMeConfig.configuredContentMode());
        initialized = true;
        ModernMetals.logger.info("Modern Metals Fantasy Level %s (%s)",
                active.low ? "low_fantasy" : "high_fantasy", inherited ? "Base Metals" : "Modern Metals");
    }

    // This class is linked only in a pack with the compatible Base Metals API.
    private static final class BaseMetalsMode {
        static String read() {
            return com.mcmoddev.basemetals.util.BMeConfig.getActiveContentMode().serializedName();
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
    public boolean allows(final ItemStack stack) {
        if(!low || stack==null || stack.isEmpty()) return true;
        final ResourceLocation id=stack.getItem().getRegistryName();
        if(id==null || !ModernMetals.MODID.equals(id.getNamespace())) return true;
        final String path=id.getPath().startsWith("double_") ? id.getPath().substring(7) : id.getPath();
        final int split=path.indexOf('_');
        return split<0 || allows(path.substring(0,split),path.substring(split+1));
    }
    private static Set<String> names(final String... values) {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(values)));
    }
}

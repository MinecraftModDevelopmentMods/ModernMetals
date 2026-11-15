package zone.moddev.mc.modernmetals.config;

import java.lang.reflect.Method;

import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.ModConfigSpec;

import zone.moddev.mc.modernmetals.ModernMetals;

/** Modern Metals' common settings, stored in {@code config/modernmetals-common.toml}. */
public final class ModernMetalsConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.EnumValue<ContentMode> CONTENT_MODE;
    public static final ModConfigSpec.BooleanValue VILLAGER_TRADES;
    public static final ModConfigSpec.BooleanValue MOLTEN_METAL_BURNS;

    private static final String BASE_METALS = "basemetals";
    private static final String BASE_METALS_CONFIG = "zone.moddev.mc.basemetals.config.BaseMetalsConfig";

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        CONTENT_MODE = builder
                .comment("HIGH_FANTASY keeps every recipe.",
                        "LOW_FANTASY restricts implausible equipment and structures. Existing items remain usable.",
                        "When Base Metals is installed, its Fantasy Level is used instead.")
                .worldRestart()
                .defineEnum("contentMode", ContentMode.HIGH_FANTASY);
        VILLAGER_TRADES = builder
                .comment("Let blacksmiths trade Modern Metals ingots and equipment.")
                .define("villagerTrades", true);
        MOLTEN_METAL_BURNS = builder
                .comment("Molten metal sets creatures alight and burns them, as lava does.")
                .define("moltenMetalBurns", true);
        SPEC = builder.build();
    }

    private ModernMetalsConfig() {}

    /** The active Fantasy Level: Base Metals' when it is installed, otherwise this mod's own. */
    public static ContentMode activeMode() {
        if (ModList.get().isLoaded(BASE_METALS)) {
            ContentMode inherited = baseMetalsMode();
            if (inherited != null) return inherited;
        }
        return CONTENT_MODE.get();
    }

    /** Whether the Fantasy Level comes from Base Metals. */
    public static boolean inheritsBaseMetals() {
        return ModList.get().isLoaded(BASE_METALS) && baseMetalsMode() != null;
    }

    // Base Metals is optional, so its configuration is read without a compile-time dependency.
    private static ContentMode baseMetalsMode() {
        try {
            Method activeMode = Class.forName(BASE_METALS_CONFIG).getMethod("activeMode");
            Object mode = activeMode.invoke(null);
            Method name = mode.getClass().getMethod("serializedName");
            return ContentMode.parse((String) name.invoke(mode));
        } catch (ReflectiveOperationException | ClassCastException exception) {
            ModernMetals.LOGGER.warn("Could not read the Base Metals Fantasy Level; using Modern Metals' own", exception);
            return null;
        }
    }
}

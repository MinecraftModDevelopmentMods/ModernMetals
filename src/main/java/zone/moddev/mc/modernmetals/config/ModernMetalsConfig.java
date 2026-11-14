package zone.moddev.mc.modernmetals.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import zone.moddev.mc.modernmetals.ModernMetals;

public final class ModernMetalsConfig {
    private static final Pattern LEGACY_MODE = Pattern.compile("(?im)^\\s*S:contentMode\\s*=\\s*(high_fantasy|low_fantasy)\\s*$");
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<String> CONTENT_MODE;
    public static final ForgeConfigSpec.BooleanValue VILLAGER_TRADES;
    private static String activeMode = "high_fantasy";
    private static ModConfig loadedConfig;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        CONTENT_MODE = builder.comment("Fantasy Level for installations without Base Metals. Restart to apply.")
                .worldRestart().define("contentMode", legacyMode(),
                        value -> "high_fantasy".equals(value) || "low_fantasy".equals(value));
        VILLAGER_TRADES = builder.comment("Allow blacksmiths to trade Modern Metals items.")
                .worldRestart().define("villagerTrades", true);
        SPEC = builder.build();
    }

    private ModernMetalsConfig() {}

    private static String legacyMode() {
        Path legacy = FMLPaths.CONFIGDIR.get().resolve("ModernMetals.cfg");
        if (Files.isRegularFile(legacy)) {
            try {
                Matcher matcher = LEGACY_MODE.matcher(new String(Files.readAllBytes(legacy), StandardCharsets.UTF_8));
                if (matcher.find()) return ContentMode.parse(matcher.group(1)).serializedName();
            } catch (IOException error) {
                ModernMetals.LOGGER.warn("Could not import Fantasy Level from {}", legacy, error);
            }
        }
        return "high_fantasy";
    }

    public static void onConfigLoading(ModConfig.Loading event) {
        if (event.getConfig().getSpec() == SPEC && loadedConfig == null) {
            loadedConfig = event.getConfig();
            activeMode = CONTENT_MODE.get();
        }
    }

    public static String activeMode() { return activeMode; }

    public static void saveMode(String value) {
        loadedConfig.getConfigData().set(CONTENT_MODE.getPath(), value);
        loadedConfig.save();
    }
}

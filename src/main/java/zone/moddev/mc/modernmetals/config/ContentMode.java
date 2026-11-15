package zone.moddev.mc.modernmetals.config;

import java.util.Locale;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.TranslatableEnum;

/** The Fantasy Level: which equipment and building recipes are available. */
public enum ContentMode implements TranslatableEnum {
    /** Every recipe is available. */
    HIGH_FANTASY,
    /** Implausible equipment and structures cannot be made. */
    LOW_FANTASY;

    public String serializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    @Override
    public Component getTranslatedName() {
        return Component.translatable("config.modernmetals.content_mode." + serializedName());
    }

    /** The mode with the given serialized name, or high fantasy for anything else. */
    public static ContentMode parse(String value) {
        if (value != null) {
            String normalized = value.trim().toLowerCase(Locale.ROOT);
            for (ContentMode mode : values()) {
                if (mode.serializedName().equals(normalized)) return mode;
            }
        }
        return HIGH_FANTASY;
    }
}

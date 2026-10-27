package zone.moddev.mc.modernmetals.util;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

final class ContentModeConfigurationTests {
    @Test void defaultSelectorIsHighFantasyAndRequiresRestart() {
        final Configuration config = new Configuration();
        assertEquals("high_fantasy", Config.contentMode(config));
        final Property selector = config.getCategory("General").get("contentMode");
        assertArrayEquals(new String[]{"high_fantasy", "low_fantasy"}, selector.getValidValues());
        assertTrue(selector.requiresMcRestart());
        assertEquals("config.modernmetals.content_mode", selector.getLanguageKey());
    }

    @Test void selectedLowFantasySurvivesRepeatedReads() {
        final Configuration config = new Configuration();
        config.get("General", "contentMode", "high_fantasy").set(" LOW_FANTASY ");
        assertEquals("low_fantasy", Config.contentMode(config));
        assertEquals("low_fantasy", Config.contentMode(config));
    }

    @Test void invalidModeFallsBackToHighFantasy() {
        final Configuration config = new Configuration();
        config.get("General", "contentMode", "high_fantasy").set("invalid_value");
        assertEquals("high_fantasy", Config.contentMode(config));
        assertEquals("high_fantasy", config.getCategory("General").get("contentMode").getString());
    }
}

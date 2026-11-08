package zone.moddev.mc.modernmetals.client;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import zone.moddev.mc.modernmetals.ModernMetals;
import zone.moddev.mc.modernmetals.content.ContentPolicy;
import zone.moddev.mc.modernmetals.util.MMeConfig;

/** Shared configuration screen for the Forge and OreSpawn mod lists. */
public final class ModernMetalsConfigScreen extends GuiConfig {
    public ModernMetalsConfigScreen(final GuiScreen parent) {
        super(parent, elements(), ModernMetals.MODID, false, true,
                I18n.format("config.modernmetals.title"),
                I18n.format(ContentPolicy.inheritsBaseMetals()
                        ? "config.modernmetals.inherited_mode" : "config.modernmetals.description"));
    }

    private static List<IConfigElement> elements() {
        final Configuration config = MMeConfig.getConfiguration();
        final List<IConfigElement> elements = new ArrayList<>(
                new ConfigElement(config.getCategory("General")).getChildElements());
        if (ContentPolicy.inheritsBaseMetals()) {
            elements.removeIf(element -> "contentMode".equals(element.getName()));
        }
        for (final String category : MMeConfig.MATERIAL_CATEGORIES) {
            elements.add(new ConfigElement(config.getCategory(category)));
        }
        return elements;
    }
}

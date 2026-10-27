package zone.moddev.mc.modernmetals.client;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zone.moddev.mc.modernmetals.ModernMetals;
import zone.moddev.mc.modernmetals.util.Config;

/** One parent-aware screen for Forge's mod list and OreSpawn's child-mod list. */
@SideOnly(Side.CLIENT)
public final class ModernMetalsConfigScreen extends GuiConfig {
    public ModernMetalsConfigScreen(final GuiScreen parent) {
        super(parent, elements(),
              ModernMetals.MODID, false, true, I18n.format("config.modernmetals.title"),
              I18n.format("config.modernmetals.description"));
    }
    private static List<IConfigElement> elements() {
        final List<IConfigElement> elements = new ArrayList<>(
                new ConfigElement(Config.getConfiguration().getCategory("General")).getChildElements());
        elements.addAll(new ConfigElement(Config.getConfiguration().getCategory("Metals")).getChildElements());
        return elements;
    }
}

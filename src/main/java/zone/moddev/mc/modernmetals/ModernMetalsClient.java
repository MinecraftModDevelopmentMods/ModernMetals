package zone.moddev.mc.modernmetals;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * Client-only entry point. MMDLib's runtime client installs projectile renderers,
 * fluid rendering and item properties for the registered content.
 */
@Mod(value = ModernMetals.MOD_ID, dist = Dist.CLIENT)
public final class ModernMetalsClient {
    public ModernMetalsClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}

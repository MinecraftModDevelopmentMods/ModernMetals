package zone.moddev.mc.modernmetals.compat;

import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

/** Relinks old add-on names without installing a second Base Metals transformer. */
@IFMLLoadingPlugin.Name("ModernMetals")
@IFMLLoadingPlugin.MCVersion("1.10.2")
@IFMLLoadingPlugin.SortingIndex(1002)
@IFMLLoadingPlugin.TransformerExclusions("zone.moddev.mc.modernmetals.compat.")
public final class ModernMetalsLoadingPlugin implements IFMLLoadingPlugin {
    @Override public String[] getASMTransformerClass() {
        return new String[] { "zone.moddev.mc.modernmetals.compat.LegacyNamespaceTransformer" };
    }
    @Override public String getModContainerClass() { return null; }
    @Override public String getSetupClass() { return null; }
    @Override public void injectData(final Map<String, Object> data) { }
    @Override public String getAccessTransformerClass() { return null; }
}

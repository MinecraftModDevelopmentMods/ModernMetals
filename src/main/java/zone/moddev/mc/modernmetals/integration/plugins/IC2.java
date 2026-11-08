package zone.moddev.mc.modernmetals.integration.plugins;

import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.integration.plugins.IC2Base;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.util.ConfigBase.Options;
import zone.moddev.mc.modernmetals.ModernMetals;

@MMDPlugin(addonId = ModernMetals.MODID, pluginId = IC2.PLUGIN_MODID)
public class IC2 extends IC2Base implements IIntegration {
    private static boolean registered;
    @Override public void init() { }
    /** Recipe managers are available after IC2's pre-initialization. */
    public static void registerRecipes() {
        if (registered || !Options.isModEnabled(PLUGIN_MODID)) return;
        final IC2 integration = new IC2();
        for (final MMDMaterial material : Materials.getMaterialsByMod(ModernMetals.MODID)) {
            if (!Materials.hasMaterial(material.getName()) || !material.hasItem(Names.CRUSHED)) continue;
            integration.registerVanillaRecipes(material.getName());
            integration.addMaceratorRecipes(material.getName());
            integration.addOreWashingPlantRecipes(material.getName());
            integration.addThermalCentrifugeRecipes(material.getName());
            integration.addMetalFormerRecipes(material.getName());
            integration.addCompressorRecipes(material.getName());
        }
        registered = true;
    }
}

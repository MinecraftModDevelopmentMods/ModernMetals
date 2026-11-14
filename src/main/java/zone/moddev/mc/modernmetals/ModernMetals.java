package zone.moddev.mc.modernmetals;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import zone.moddev.mc.modernmetals.config.ModernMetalsConfig;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import zone.moddev.mc.modernmetals.client.ClientSetup;
import zone.moddev.mc.modernmetals.entity.ModEntities;
import zone.moddev.mc.modernmetals.migration.LegacyWorldDataHook;
import zone.moddev.mc.modernmetals.recipe.CrushingRecipe;
import zone.moddev.mc.modernmetals.network.ContentModeNetwork;

@Mod(ModernMetals.MOD_ID)
public final class ModernMetals {
    public static final String MOD_ID = "modernmetals";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public ModernMetals() {
        ModEntities.initialize();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModernMetalsConfig.SPEC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModernMetalsConfig::onConfigLoading);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> ClientSetup::register);
    }

    private void setup(FMLCommonSetupEvent event) {
        // Creating a channel grows Forge 25's shared listener tables.
        DeferredWorkQueue.runLater(ContentModeNetwork::register);
        // Forge's world-save hook list is shared and is not thread-safe.
        DeferredWorkQueue.runLater(LegacyWorldDataHook::register);
        DeferredWorkQueue.runLater(CrushingRecipe::register);
    }
}

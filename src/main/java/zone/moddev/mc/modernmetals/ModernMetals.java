package zone.moddev.mc.modernmetals;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

import zone.moddev.mc.modernmetals.config.ModernMetalsConfig;
import zone.moddev.mc.modernmetals.content.ModContent;
import zone.moddev.mc.modernmetals.content.MoltenMetalHeat;
import zone.moddev.mc.modernmetals.recipe.ContentAllowedCondition;
import zone.moddev.mc.modernmetals.trade.ModernMetalsTrades;

/**
 * Modern Metals adds industrial metals and alloys. The materials themselves are
 * MMDLib definitions under {@code mmdlib/material/}; this class wires up the
 * runtime registration, configuration and gameplay hooks.
 *
 * @author Jasmine Iwanek
 */
@Mod(ModernMetals.MOD_ID)
public final class ModernMetals {
    /** ID of this mod. */
    public static final String MOD_ID = "modernmetals";

    public static final Logger LOGGER = LogUtils.getLogger();

    public ModernMetals(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, ModernMetalsConfig.SPEC);
        ModContent.register(modBus);
        ContentAllowedCondition.register(modBus);

        NeoForge.EVENT_BUS.addListener(MoltenMetalHeat::onEntityTick);
        NeoForge.EVENT_BUS.addListener(ModernMetalsTrades::onVillagerTrades);
    }
}

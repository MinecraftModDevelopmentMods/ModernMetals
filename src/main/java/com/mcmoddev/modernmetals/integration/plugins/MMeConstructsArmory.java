package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.integration.plugins.ConstructsArmory;
import com.mcmoddev.lib.integration.plugins.tinkers.events.MaterialRegistrationEvent;
import com.mcmoddev.lib.util.Config;
import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@MMDPlugin(addonId = ModernMetals.MODID, pluginId = MMeConstructsArmory.PLUGIN_MODID, versions = MMeConstructsArmory.PLUGIN_MODID
        + "@[1.12.2-2.10.1.87,)")
public final class MMeConstructsArmory implements IIntegration {

    public static final String PLUGIN_MODID = ConstructsArmory.PLUGIN_MODID;

    public MMeConstructsArmory() {
        // do nothing
    }

    @Override
    public void init() {
        ConstructsArmory.INSTANCE.init();
        if (!Config.Options.isModEnabled(PLUGIN_MODID)) {
            return;
        }
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void materialRegistration(MaterialRegistrationEvent ev) {
        if(Config.Options.isModEnabled(PLUGIN_MODID)){
            ev.getRegistry().getEntries().stream()
                    .map(ent -> ent.getValue())
                    .forEach(mat -> {
                        switch (mat.getName()){
                            case MaterialNames.BERYLLIUM:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.BORON:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.CADMIUM:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.CHROMIUM:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.GALVANIZED_STEEL:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.IRIDIUM:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.MAGNESIUM:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.MANGANESE:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.NICHROME:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.OSMIUM:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.PLUTONIUM:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.RUTILE:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.STAINLESS_STEEL:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.TANTALUM:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.THORIUM:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.TITANIUM:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.TUNGSTEN:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.URANIUM:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                            case MaterialNames.ZIRCONIUM:
//                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.vengeful, ArmorTraits.prideful);
                                break;
                        }
                    });
        }
    }
}

package com.mcmoddev.modernmetals.integration.plugins;

import c4.conarm.common.armor.traits.ArmorTraits;
import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.integration.plugins.ConstructsArmory;
import com.mcmoddev.lib.integration.plugins.armory.traits.MMDTraitsCA;
import com.mcmoddev.lib.integration.plugins.tinkers.events.MaterialRegistrationEvent;
import com.mcmoddev.lib.integration.plugins.tinkers.traits.MMDTraits;
import com.mcmoddev.lib.util.Config;
import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.mcmoddev.lib.integration.plugins.ConstructsArmory.addArmorTrait;

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
                            case MaterialNames.CHROMIUM:
                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.magnetic2);
                                break;
                            case MaterialNames.GALVANIZED_STEEL:
                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.steady, ArmorTraits.indomitable);
                                break;
                            case MaterialNames.IRIDIUM:
                                addArmorTrait(mat.getTinkerMaterial(), MMDTraitsCA.brittle);
                                break;
                            case MaterialNames.MAGNESIUM:
                                addArmorTrait(mat.getTinkerMaterial(), MMDTraitsCA.reactive);
                                break;
                            case MaterialNames.MANGANESE:
                                addArmorTrait(mat.getTinkerMaterial(), MMDTraitsCA.brittle);
                                break;
                            case MaterialNames.OSMIUM:
                                addArmorTrait(mat.getTinkerMaterial(), MMDTraitsCA.brittle);
                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.heavy);
                                break;
                            case MaterialNames.PLUTONIUM:
                                addArmorTrait(mat.getTinkerMaterial(), MMDTraitsCA.toxic, MMDTraits.radioactive);
                                break;
                            case MaterialNames.STAINLESS_STEEL:
                                addArmorTrait(mat.getTinkerMaterial(), ArmorTraits.steady, ArmorTraits.indomitable);
                                break;
                            case MaterialNames.URANIUM:
                                addArmorTrait(mat.getTinkerMaterial(), MMDTraitsCA.poisonous, MMDTraits.radioactive);
                                break;
                        }
                    });
        }
    }
}

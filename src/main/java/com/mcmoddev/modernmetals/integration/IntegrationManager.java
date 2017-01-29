package com.mcmoddev.modernmetals.integration;

import com.google.common.collect.Lists;
import com.mcmoddev.basemetals.integration.IIntegration;
import com.mcmoddev.basemetals.util.AnnotationChecker;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.List;

public enum IntegrationManager {
    INSTANCE;

    private List<IIntegration> integrations = Lists.newArrayList();

    public void preInit(FMLPreInitializationEvent event) {
        AnnotationChecker.getInstances(event.getAsmData(), ModernMetalsPlugin.class, IIntegration.class).stream()
                .forEachOrdered(integration -> {
                    Class<? extends IIntegration> integrationClass = integration.getClass();
                    ModernMetalsPlugin plugin = integrationClass.getAnnotation(ModernMetalsPlugin.class);
                    if (Loader.isModLoaded(plugin.value())) {
                    	FMLLog.severe("MODERNMETALS: Loaded " + plugin.value());
                        integrations.add(integration);
                        integration.init();
                    }
                });
    }
}
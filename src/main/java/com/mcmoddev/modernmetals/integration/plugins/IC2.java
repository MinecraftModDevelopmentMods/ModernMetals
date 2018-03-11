package com.mcmoddev.modernmetals.integration.plugins;

import java.util.Arrays;
import java.util.List;

import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.crafting.IRecipe;

@MMDPlugin(addonId = ModernMetals.MODID, pluginId = IC2.PLUGIN_MODID, initCallback = "doHammerRecipes")
public final class IC2 extends com.mcmoddev.lib.integration.plugins.IC2Base implements IIntegration {

	@Override
	public void init() {
		if (!Options.isModEnabled(PLUGIN_MODID)) {
			return;
		}

		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void mainInteraction(final RegistryEvent.Register<IRecipe> event) {
		Arrays.asList(MaterialNames.ALUMINUM, MaterialNames.BERYLLIUM,
				MaterialNames.BORON, MaterialNames.CADMIUM, MaterialNames.CHROMIUM, MaterialNames.IRIDIUM,
				MaterialNames.MAGNESIUM, MaterialNames.MANGANESE, MaterialNames.PLUTONIUM, MaterialNames.RUTILE,
				MaterialNames.TANTALUM, MaterialNames.THORIUM, MaterialNames.TITANIUM, MaterialNames.TUNGSTEN,
				MaterialNames.ZIRCONIUM).stream()
				.filter(Materials::hasMaterial)
				.filter(materialName -> !Materials.getMaterialByName(materialName).isEmpty())
				.forEach(materialName -> {
					registerVanillaRecipes(materialName);
					addMaceratorRecipes(materialName);
					addOreWashingPlantRecipes(materialName);
					addThermalCentrifugeRecipes(materialName);
					addMetalFormerRecipes(materialName);
					addCompressorRecipes(materialName);
				});

		MinecraftForge.EVENT_BUS.register(this);
	}

	public void doHammerRecipes() {
		Arrays.asList(MaterialNames.ALUMINUM, MaterialNames.BERYLLIUM,
				MaterialNames.BORON, MaterialNames.CADMIUM, MaterialNames.CHROMIUM, MaterialNames.IRIDIUM,
				MaterialNames.MAGNESIUM, MaterialNames.MANGANESE, MaterialNames.PLUTONIUM, MaterialNames.RUTILE,
				MaterialNames.TANTALUM, MaterialNames.THORIUM, MaterialNames.TITANIUM, MaterialNames.TUNGSTEN,
				MaterialNames.URANIUM, MaterialNames.ZIRCONIUM).stream()
		.filter(Materials::hasMaterial)
		.filter(name->!Materials.getMaterialByName(name).isEmpty())
		.forEach(this::addForgeHammerRecipe);
	}
}

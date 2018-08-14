package com.mcmoddev.modernmetals.integration.plugins;

import java.util.Arrays;

import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.IntegrationInitEvent;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.util.Config.Options;
import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.crafting.IRecipe;

@MMDPlugin(addonId = ModernMetals.MODID, pluginId = MMeIC2.PLUGIN_MODID)
public final class MMeIC2 extends com.mcmoddev.lib.integration.plugins.IC2 implements IIntegration {

	@Override
	public void init() {
		if (!Options.isModEnabled(PLUGIN_MODID)) {
			return;
		}

		MinecraftForge.EVENT_BUS.register(this);
	}

	/**
	 * 
	 * @param event The Event.
	 */
	@SubscribeEvent
	public void mainInteraction(final RegistryEvent.Register<IRecipe> event) {
		MinecraftForge.EVENT_BUS.register(this);
		Arrays.asList(MaterialNames.ALUMINUM, MaterialNames.ALUMINUM_BRASS, MaterialNames.BERYLLIUM,
				MaterialNames.BORON, MaterialNames.CADMIUM, MaterialNames.CHROMIUM, MaterialNames.GALVANIZED_STEEL,
				MaterialNames.IRIDIUM, MaterialNames.MAGNESIUM, MaterialNames.MANGANESE, MaterialNames.NICHROME,
				MaterialNames.OSMIUM, MaterialNames.PLUTONIUM, MaterialNames.RUTILE, MaterialNames.STAINLESS_STEEL,
				MaterialNames.TANTALUM, MaterialNames.THORIUM, MaterialNames.TITANIUM, MaterialNames.TUNGSTEN,
				MaterialNames.ZIRCONIUM).stream()
				.filter(Materials::hasMaterial)
				.filter(materialName -> !Materials.getMaterialByName(materialName).isEmpty())
				.forEach(materialName -> {
					registerVanillaRecipes(materialName);
					if ((!Loader.isModLoaded("techreborn"))
							|| (!materialName.equalsIgnoreCase(MaterialNames.IRIDIUM))) {
						addMaceratorRecipes(materialName);
						addOreWashingPlantRecipes(materialName);
						addThermalCentrifugeRecipes(materialName);
					}
					addMetalFormerRecipes(materialName);
					addCompressorRecipes(materialName);
				});
		
		if (Materials.hasMaterial(MaterialNames.URANIUM)) {
			final MMDMaterial uranium = Materials.getMaterialByName(MaterialNames.URANIUM);

					registerVanillaRecipes(uranium);
					addMaceratorRecipes(uranium);
					addMetalFormerRecipes(uranium);
					addCompressorRecipes(uranium);
		}

		MinecraftForge.EVENT_BUS.register(this);
	}

	/**
	 * 
	 * @param event The Event.
	 */
	@SubscribeEvent
	public void pluginInit(final IntegrationInitEvent event) {
		Arrays.asList(MaterialNames.ALUMINUM, MaterialNames.ALUMINUM_BRASS, MaterialNames.BERYLLIUM,
				MaterialNames.BORON, MaterialNames.CADMIUM, MaterialNames.CHROMIUM, MaterialNames.GALVANIZED_STEEL,
				MaterialNames.IRIDIUM, MaterialNames.MAGNESIUM, MaterialNames.MANGANESE, MaterialNames.NICHROME,
				MaterialNames.OSMIUM, MaterialNames.PLUTONIUM, MaterialNames.RUTILE, MaterialNames.STAINLESS_STEEL,
				MaterialNames.TANTALUM, MaterialNames.THORIUM, MaterialNames.TITANIUM, MaterialNames.TUNGSTEN,
				MaterialNames.URANIUM, MaterialNames.ZIRCONIUM).stream()
		.filter(Materials::hasMaterial)
		.filter(name -> !Materials.getMaterialByName(name).isEmpty())
		.forEach(this::addForgeHammerRecipe);
	}
}

package com.mcmoddev.modernmetals.integration.plugins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;

import static com.mcmoddev.lib.integration.plugins.tinkers.TinkerMaterial.TinkersTraitLocation;

import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.modernmetals.data.TraitNames;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 *
 * @author Jasmine Iwanek
 *
 */
@MMDPlugin(addonId = ModernMetals.MODID, pluginId = TinkersConstruct.PLUGIN_MODID,
           versions = TinkersConstruct.PLUGIN_MODID + "@[1.12.2-2.7.4.0,)")

public class TinkersConstruct extends com.mcmoddev.lib.integration.plugins.TinkersConstructBase
		implements IIntegration {

	@Override
	public void init() {
		com.mcmoddev.modernmetals.ModernMetals.logger.fatal("ModernMetals - TiCon Interface init");
		if (!Options.isModEnabled(PLUGIN_MODID)) {
			return;
		}

/*
 *  Strangely enough, Aluminum doesn't trigger a "this already exists" check like Lead or Copper do...
 *  Lets make sure we exclude it and Aluminum Brass here, for safety.

		registerMaterial(Materials.hasMaterial(MaterialNames.ALUMINUM), MaterialNames.ALUMINUM, true, false,
				TraitNames.LIGHTWEIGHT)

		registerMaterial(Materials.hasMaterial(MaterialNames.ALUMINUM_BRASS), MaterialNames.ALUMINUM_BRASS, true,
				false)
 */
		registerMaterial(Materials.hasMaterial(MaterialNames.BERYLLIUM), MaterialNames.BERYLLIUM, true, false);

		registerMaterial(Materials.hasMaterial(MaterialNames.BORON), MaterialNames.BORON, true, false);

		registerMaterial(Materials.hasMaterial(MaterialNames.CADMIUM), MaterialNames.CADMIUM, true, false);

		registerMaterial(Materials.hasMaterial(MaterialNames.CHROMIUM), MaterialNames.CHROMIUM, true, false,
				TraitNames.MAGNETIC, TinkersTraitLocation.HEAD, TraitNames.MAGNETIC2);

		registerMaterial(Materials.hasMaterial(MaterialNames.GALVANIZED_STEEL), MaterialNames.GALVANIZED_STEEL, true,
				false, TinkersTraitLocation.HEAD, TraitNames.SHARP, TraitNames.STIFF);

		registerMaterial(Materials.hasMaterial(MaterialNames.IRIDIUM), MaterialNames.IRIDIUM, true, false,
				TraitNames.BRITTLE);

		registerMaterial(Materials.hasMaterial(MaterialNames.MAGNESIUM), MaterialNames.MAGNESIUM, true, false,
				TraitNames.REACTIVE);

		registerMaterial(Materials.hasMaterial(MaterialNames.MANGANESE), MaterialNames.MANGANESE, true, false,
				TraitNames.BRITTLE);

		registerMaterial(Materials.hasMaterial(MaterialNames.NICHROME), MaterialNames.NICHROME, true, false);

		registerMaterial(Materials.hasMaterial(MaterialNames.OSMIUM), MaterialNames.OSMIUM, true, false,
				TraitNames.HEAVY, TraitNames.BRITTLE);

		registerMaterial(Materials.hasMaterial(MaterialNames.PLUTONIUM), MaterialNames.PLUTONIUM, true, false,
				TraitNames.RADIOACTIVE, TinkersTraitLocation.HEAD, TraitNames.TOXIC, TinkersTraitLocation.PROJECTILE,
				TraitNames.TOXIC);

		registerMaterial(Materials.hasMaterial(MaterialNames.RUTILE), MaterialNames.RUTILE, true, false);

		registerMaterial(Materials.hasMaterial(MaterialNames.STAINLESS_STEEL), MaterialNames.STAINLESS_STEEL, true,
				false, TinkersTraitLocation.HEAD, TraitNames.SHARP, TraitNames.STIFF);

		registerMaterial(Materials.hasMaterial(MaterialNames.TANTALUM), MaterialNames.TANTALUM, true, false);

		registerMaterial(Materials.hasMaterial(MaterialNames.THORIUM), MaterialNames.THORIUM, true, false);

		registerMaterial(Materials.hasMaterial(MaterialNames.TITANIUM), MaterialNames.TITANIUM, true, false);

		registerMaterial(Materials.hasMaterial(MaterialNames.TUNGSTEN), MaterialNames.TUNGSTEN, true, false);

		registerMaterial(Materials.hasMaterial(MaterialNames.URANIUM), MaterialNames.URANIUM, true, false,
				TraitNames.RADIOACTIVE, TinkersTraitLocation.HEAD, TraitNames.POISONOUS, TinkersTraitLocation.PROJECTILE,
				TraitNames.POISONOUS);

		registerMaterial(Materials.hasMaterial(MaterialNames.ZIRCONIUM), MaterialNames.ZIRCONIUM, true, false);
		registerAlloys();
	}

	private void registerMaterial(final boolean active, final String name, final boolean castable, final boolean craftable, final Object...traits) {
		if (active) {
			this.registerMaterial(name, castable, craftable, traits);
		}
	}
	
	private void registerMaterial(final boolean active, final String name, final boolean castable, final boolean craftable) {
		if (active) {
			this.registerMaterial(name, castable, craftable);		
		}
	}

	private void registerAlloy(final String outputMaterialName, final int outputAmount, final Object[] recipe) {
		final List<String> materialNames = new ArrayList<>();
		materialNames.add(outputMaterialName);
		Arrays.asList(recipe).stream()
		.filter(elem -> elem instanceof String)
		.forEach(elem -> materialNames.add((String)elem));
		if (!hasMaterials(materialNames)) {
			return;
		}
		final FluidStack output = FluidRegistry.getFluidStack(outputMaterialName, outputAmount);
		final FluidStack[] rest = new FluidStack[recipe.length / 2];
		for (int i = 0, j = 0; i < recipe.length; i += 2, j++) {
			rest[j] = FluidRegistry.getFluidStack((String)recipe[i], (int)recipe[i + 1]);
		}
		this.registerAlloy(outputMaterialName, output, rest);
	}

	private boolean hasMaterials(final List<String> materialNames) {
		for (final String name : materialNames) {
			if (!Materials.hasMaterial(name)) {
				return false;
			}
		}
		return true;
	}

	private void registerAlloys() {
		registerAlloy(MaterialNames.GALVANIZED_STEEL, 2, new Object[] { com.mcmoddev.basemetals.data.MaterialNames.STEEL, 1, com.mcmoddev.basemetals.data.MaterialNames.ZINC, 1 });
		registerAlloy(MaterialNames.NICHROME, 2, new Object[] { com.mcmoddev.basemetals.data.MaterialNames.NICKEL, 1, MaterialNames.CHROMIUM, 1 });
		registerAlloy(MaterialNames.STAINLESS_STEEL, 2, new Object[] { com.mcmoddev.basemetals.data.MaterialNames.STEEL, 1, MaterialNames.CHROMIUM, 1 });
		registerAlloy(MaterialNames.TITANIUM, 2, new Object[] { MaterialNames.RUTILE, 1, MaterialNames.MAGNESIUM, 1 });
	}
}

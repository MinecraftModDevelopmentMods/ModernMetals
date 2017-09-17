package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.modernmetals.data.TraitNames;
import com.mcmoddev.modernmetals.init.Materials;
import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.integration.plugins.tinkers.TraitLocations;

import com.mcmoddev.lib.integration.IIntegration;

/**
 *
 * @author Jasmine Iwanek
 *
 */
@MMDPlugin(addonId = ModernMetals.MODID, pluginId = TinkersConstruct.PLUGIN_MODID)
public class TinkersConstruct extends com.mcmoddev.lib.integration.plugins.TinkersConstructBase implements IIntegration {

	private static boolean initDone = false;
	
	@Override
	public void init() {
		if (initDone || !Options.isModEnabled(TinkersConstruct.PLUGIN_MODID)) {
			return;
		}

		if (Options.isMaterialEnabled(MaterialNames.ALUMINUM)) {
			registry.getMaterial(MaterialNames.ALUMINUM, Materials.getMaterialByName(MaterialNames.ALUMINUM)).addTrait(TraitNames.LIGHTWEIGHT).setCastable(true).settle();
		}

		if (Options.isMaterialEnabled(MaterialNames.ALUMINUM_BRASS)) {
			registry.getMaterial(MaterialNames.ALUMINUM_BRASS, Materials.getMaterialByName(MaterialNames.ALUMINUM_BRASS)).setCastable(true).settle();
		}

		if (Options.isMaterialEnabled(MaterialNames.CADMIUM)) {
			registry.getMaterial(MaterialNames.CADMIUM, Materials.getMaterialByName(MaterialNames.CADMIUM)).setCastable(true).settle();
		}

		if (Options.isMaterialEnabled(MaterialNames.CHROMIUM)) {
			registry.getMaterial(MaterialNames.CHROMIUM, Materials.getMaterialByName(MaterialNames.CHROMIUM)).setCastable(true).addTrait(TraitNames.MAGNETIC).addTrait(TraitNames.MAGNETIC2,TraitLocations.HEAD).settle();
		}

		if (Options.isMaterialEnabled(MaterialNames.GALVANIZED_STEEL)) {
			registry.getMaterial(MaterialNames.GALVANIZED_STEEL, Materials.getMaterialByName(MaterialNames.GALVANIZED_STEEL)).addTrait(TraitNames.SHARP, TraitLocations.HEAD).addTrait(TraitNames.STIFF).setCastable(true).settle();
			registry.registerAlloy(MaterialNames.GALVANIZED_STEEL, Materials.getMaterialByName(MaterialNames.GALVANIZED_STEEL).getFluid(), 2, com.mcmoddev.basemetals.data.MaterialNames.STEEL, 1, com.mcmoddev.basemetals.data.MaterialNames.ZINC, 1);
		}

		if (Options.isMaterialEnabled(MaterialNames.IRIDIUM)) {
			registry.getMaterial(MaterialNames.IRIDIUM, Materials.getMaterialByName(MaterialNames.IRIDIUM)).setCastable(true).addTrait(TraitNames.HEAVY).addTrait(TraitNames.BRITTLE).settle();
		}

		if (Options.isMaterialEnabled(MaterialNames.MAGNESIUM)) {
			registry.getMaterial(MaterialNames.MAGNESIUM, Materials.getMaterialByName(MaterialNames.MAGNESIUM)).setCastable(true).addTrait(TraitNames.REACTIVE).settle();
		}

		if (Options.isMaterialEnabled(MaterialNames.MANGANESE)) {
			registry.getMaterial(MaterialNames.MANGANESE, Materials.getMaterialByName(MaterialNames.MANGANESE)).addTrait(TraitNames.BRITTLE).setCastable(true).settle();
		}

		if (Options.isMaterialEnabled(MaterialNames.NICHROME)) {
			registry.getMaterial(MaterialNames.NICHROME, Materials.getMaterialByName(MaterialNames.NICHROME)).setCastable(true).settle();
			registry.registerAlloy(MaterialNames.NICHROME, Materials.getMaterialByName(MaterialNames.NICHROME).getFluid(), 2, com.mcmoddev.basemetals.data.MaterialNames.NICKEL, 1, MaterialNames.CHROMIUM, 1);
		}

		if (Options.isMaterialEnabled(MaterialNames.OSMIUM)) {
			registry.getMaterial(MaterialNames.OSMIUM, Materials.getMaterialByName(MaterialNames.OSMIUM)).setCastable(true).addTrait(TraitNames.HEAVY).addTrait(TraitNames.BRITTLE).settle();
		}

		if (Options.isMaterialEnabled(MaterialNames.PLUTONIUM)) {
			registry.getMaterial(MaterialNames.PLUTONIUM, Materials.getMaterialByName(MaterialNames.PLUTONIUM)).addTrait(TraitNames.RADIOACTIVE).addTrait(TraitNames.TOXIC, TraitLocations.HEAD).addTrait(TraitNames.TOXIC, TraitLocations.PROJECTILE).setCastable(true).settle();
		}

		// TODO: request is for custom modifier item to grant +50% durability
		// This is not yet in the API - might require extra classes in MMDLib
		if (Options.isMaterialEnabled(MaterialNames.RUTILE)) {
			registry.getMaterial(MaterialNames.RUTILE, Materials.getMaterialByName(MaterialNames.RUTILE)).setCastable(true).settle();
		}

		if (Options.isMaterialEnabled(MaterialNames.STAINLESS_STEEL)) {
			registry.getMaterial(MaterialNames.STAINLESS_STEEL, Materials.getMaterialByName(MaterialNames.STAINLESS_STEEL)).setCastable(true).addTrait(TraitNames.SHARP, TraitLocations.HEAD).addTrait(TraitNames.STIFF).settle();
			registry.registerAlloy(MaterialNames.STAINLESS_STEEL, Materials.getMaterialByName(MaterialNames.STAINLESS_STEEL).getFluid(), 2, com.mcmoddev.basemetals.data.MaterialNames.STEEL, 1, MaterialNames.CHROMIUM, 1);
		}

		if (Options.isMaterialEnabled(MaterialNames.TANTALUM)) {
			registry.getMaterial(MaterialNames.TANTALUM, Materials.getMaterialByName(MaterialNames.TANTALUM)).setCastable(true).settle();
		}

		if (Options.isMaterialEnabled(MaterialNames.TITANIUM)) {
			registry.getMaterial(MaterialNames.TITANIUM, Materials.getMaterialByName(MaterialNames.TITANIUM)).setCastable(true).settle();
			registry.registerAlloy(MaterialNames.TITANIUM, Materials.getMaterialByName(MaterialNames.TITANIUM).getFluid(), 2, MaterialNames.RUTILE, 1, MaterialNames.MAGNESIUM, 1 );
		}

		if (Options.isMaterialEnabled(MaterialNames.TUNGSTEN)) {
			registry.getMaterial(MaterialNames.TUNGSTEN, Materials.getMaterialByName(MaterialNames.TUNGSTEN)).setCastable(true).settle();
		}

		if (Options.isMaterialEnabled(MaterialNames.URANIUM)) {
			registry.getMaterial(MaterialNames.URANIUM, Materials.getMaterialByName(MaterialNames.URANIUM)).setCastable(true).addTrait(TraitNames.POISONOUS, TraitLocations.HEAD).addTrait(TraitNames.POISONOUS, TraitLocations.PROJECTILE).addTrait(TraitNames.RADIOACTIVE).settle();
		}

		if (Options.isMaterialEnabled(MaterialNames.ZIRCONIUM)) {
			registry.getMaterial(MaterialNames.ZIRCONIUM, Materials.getMaterialByName(MaterialNames.ZIRCONIUM)).setCastable(true).settle();
		}

		registry.registerAll();
		initDone = true;
	}
}

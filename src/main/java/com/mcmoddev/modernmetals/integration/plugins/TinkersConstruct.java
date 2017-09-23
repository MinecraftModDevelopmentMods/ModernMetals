package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.modernmetals.data.TraitNames;
import com.mcmoddev.modernmetals.init.Materials;
import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.integration.plugins.tinkers.TCMaterial;
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
		if (initDone || !Options.isModEnabled("tinkersconstruct")) {
			return;
		}

		registerMaterial(Options.isMaterialEnabled(MaterialNames.ALUMINUM), 
				MaterialNames.ALUMINUM, true, false, TraitNames.LIGHTWEIGHT);

		registerMaterial(Options.isMaterialEnabled(MaterialNames.ALUMINUM_BRASS),
				MaterialNames.ALUMINUM_BRASS, true, false);

		registerMaterial(Options.isMaterialEnabled(MaterialNames.CADMIUM),
				MaterialNames.CADMIUM, true, false);
		
		registerMaterial(Options.isMaterialEnabled(MaterialNames.CHROMIUM),
				MaterialNames.CHROMIUM, true, false, TraitNames.MAGNETIC,
				TraitLocations.HEAD, TraitNames.MAGNETIC2 );

		registerMaterial(Options.isMaterialEnabled(MaterialNames.GALVANIZED_STEEL),
				MaterialNames.GALVANIZED_STEEL, true, false,
				TraitLocations.HEAD, TraitNames.SHARP, TraitNames.STIFF );

		registerMaterial(Options.isMaterialEnabled(MaterialNames.IRIDIUM), 
				MaterialNames.IRIDIUM, true, false, TraitNames.BRITTLE);

		registerMaterial(Options.isMaterialEnabled(MaterialNames.MAGNESIUM), 
				MaterialNames.MAGNESIUM, true, false, TraitNames.REACTIVE);
		
		registerMaterial(Options.isMaterialEnabled(MaterialNames.MANGANESE), 
				MaterialNames.MANGANESE, true, false, TraitNames.BRITTLE);

		registerMaterial(Options.isMaterialEnabled(MaterialNames.NICHROME),
				MaterialNames.NICHROME, true, false );
		
		registerMaterial(Options.isMaterialEnabled(MaterialNames.OSMIUM), 
				MaterialNames.OSMIUM, true, false, TraitNames.HEAVY, TraitNames.BRITTLE);

		registerMaterial(Options.isMaterialEnabled(MaterialNames.PLUTONIUM), 
				MaterialNames.PLUTONIUM, true, false, TraitNames.RADIOACTIVE,
				TraitLocations.HEAD, TraitNames.TOXIC, TraitLocations.PROJECTILE, TraitNames.TOXIC);

		registerMaterial(Options.isMaterialEnabled(MaterialNames.RUTILE),
				MaterialNames.RUTILE, true, false );
		
		registerMaterial(Options.isMaterialEnabled(MaterialNames.STAINLESS_STEEL),
				MaterialNames.STAINLESS_STEEL, true, false,
				TraitLocations.HEAD, TraitNames.SHARP, TraitNames.STIFF );
		
		registerMaterial(Options.isMaterialEnabled(MaterialNames.TANTALUM),
				MaterialNames.TANTALUM, true, false );
		
		registerMaterial(Options.isMaterialEnabled(MaterialNames.TITANIUM),
				MaterialNames.TITANIUM, true, false );

		registerMaterial(Options.isMaterialEnabled(MaterialNames.TUNGSTEN),
				MaterialNames.TUNGSTEN, true, false );
		
		registerMaterial(Options.isMaterialEnabled(MaterialNames.URANIUM), 
				MaterialNames.URANIUM, true, false, TraitNames.RADIOACTIVE,
				TraitLocations.HEAD, TraitNames.POISONOUS, TraitLocations.PROJECTILE, TraitNames.POISONOUS);

		registerMaterial(Options.isMaterialEnabled(MaterialNames.ZIRCONIUM),
				MaterialNames.ZIRCONIUM, true, false );
		
		if (Options.isMaterialEnabled(MaterialNames.GALVANIZED_STEEL) &&
				Options.isMaterialEnabled(com.mcmoddev.basemetals.data.MaterialNames.STEEL) &&
				Options.isMaterialEnabled(com.mcmoddev.basemetals.data.MaterialNames.ZINC) ) {
			registry.registerAlloy(MaterialNames.GALVANIZED_STEEL, Materials.getMaterialByName(MaterialNames.GALVANIZED_STEEL).getFluid(), 2, com.mcmoddev.basemetals.data.MaterialNames.STEEL, 1, com.mcmoddev.basemetals.data.MaterialNames.ZINC, 1);
		}

		if (Options.isMaterialEnabled(MaterialNames.NICHROME) &&
				Options.isMaterialEnabled(com.mcmoddev.basemetals.data.MaterialNames.NICKEL) &&
				Options.isMaterialEnabled(MaterialNames.CHROMIUM)) {
			registry.registerAlloy(MaterialNames.NICHROME, Materials.getMaterialByName(MaterialNames.NICHROME).getFluid(), 2, com.mcmoddev.basemetals.data.MaterialNames.NICKEL, 1, MaterialNames.CHROMIUM, 1);
		}

		if (Options.isMaterialEnabled(MaterialNames.STAINLESS_STEEL) &&
			Options.isMaterialEnabled(com.mcmoddev.basemetals.data.MaterialNames.STEEL) &&
			Options.isMaterialEnabled(MaterialNames.CHROMIUM) ) {
			registry.registerAlloy(MaterialNames.STAINLESS_STEEL, Materials.getMaterialByName(MaterialNames.STAINLESS_STEEL).getFluid(), 2, com.mcmoddev.basemetals.data.MaterialNames.STEEL, 1, MaterialNames.CHROMIUM, 1);
		}

		if (Options.isMaterialEnabled(MaterialNames.TITANIUM) && Options.isMaterialEnabled(MaterialNames.RUTILE)) {
			registry.registerAlloy(MaterialNames.TITANIUM, Materials.getMaterialByName(MaterialNames.TITANIUM).getFluid(), 2, MaterialNames.RUTILE, 1, MaterialNames.MAGNESIUM, 1 );
		}

		registry.registerAll();
		initDone = true;
	}

	private boolean isTraitLoc(String loc) {
		switch (loc) {
		case TraitLocations.BOW:
		case TraitLocations.BOWSTRING:
		case TraitLocations.EXTRA:
		case TraitLocations.FLETCHING:
		case TraitLocations.HANDLE:
		case TraitLocations.HEAD:
		case TraitLocations.PROJECTILE:
		case TraitLocations.SHAFT:
			return true;
		default:
			return false;
		}
	}

	private void addTraits(TCMaterial mat, String[] traits) {
		int i = 0;
		while (i < traits.length) {
			if (i + 1 >= traits.length) {
				mat.addTrait(traits[i]);
				i++;
				continue;
			}

			if (isTraitLoc(traits[i + 1])) {
				mat.addTrait(traits[i], traits[i + 1]);
				i++;
			} else {
				mat.addTrait(traits[i]);
			}
			i++;
		}
	}


	private void registerMaterial(boolean enabled, String materialName, boolean castable, boolean craftable, String... traits) {
		if( enabled ) {
			registerMaterial( materialName, castable, craftable, traits );
		}
	}

	private void registerMaterial(String name, boolean castable, boolean craftable, String[] traits) {
		TCMaterial mat = registry.getMaterial(name, Materials.getMaterialByName(name)).setCastable(castable).setCraftable(craftable);
		if (traits.length > 0) {
			addTraits(mat, traits);
		}
		mat.settle();
	}
}

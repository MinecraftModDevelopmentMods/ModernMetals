package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.modernmetals.data.TraitNames;
import com.mcmoddev.modernmetals.init.Materials;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.integration.plugins.tinkers.TCMaterial;
import com.mcmoddev.lib.integration.plugins.tinkers.TraitLocations;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.integration.IIntegration;

/**
 *
 * @author Jasmine Iwanek
 *
 */
@MMDPlugin(addonId = ModernMetals.MODID,
           pluginId = TinkersConstruct.PLUGIN_MODID, 
           preInitCallback="preInit", 
           initCallback="initCallback",
           postInitCallback="postInit")
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
		
		initDone = true;
	}

	public void preInit() {
		super.preInitSetup();
		super.setMaterialsVisible();
	}
	
	public void initCallback() {
		registerAlloys();
		super.initSetup();
	}

	public void postInit() {
		super.postInitSetup();
	}

	private void registerAlloys() {
		if (Options.isMaterialEnabled(MaterialNames.GALVANIZED_STEEL) &&
				Options.isMaterialEnabled(com.mcmoddev.basemetals.data.MaterialNames.STEEL) &&
				Options.isMaterialEnabled(com.mcmoddev.basemetals.data.MaterialNames.ZINC) ) {
			FluidStack output = FluidRegistry.getFluidStack(MaterialNames.GALVANIZED_STEEL, 2);
			FluidStack steel = FluidRegistry.getFluidStack(com.mcmoddev.basemetals.data.MaterialNames.STEEL, 1);
			FluidStack zinc = FluidRegistry.getFluidStack(com.mcmoddev.basemetals.data.MaterialNames.ZINC, 1);
			registry.registerAlloy(MaterialNames.GALVANIZED_STEEL, output, steel, zinc); 
		}

		if (Options.isMaterialEnabled(MaterialNames.NICHROME) &&
				Options.isMaterialEnabled(com.mcmoddev.basemetals.data.MaterialNames.NICKEL) &&
				Options.isMaterialEnabled(MaterialNames.CHROMIUM)) {
			FluidStack output = FluidRegistry.getFluidStack(MaterialNames.NICHROME, 2);
			FluidStack nickel = FluidRegistry.getFluidStack(com.mcmoddev.basemetals.data.MaterialNames.NICKEL, 1);
			FluidStack chrome = FluidRegistry.getFluidStack(MaterialNames.CHROMIUM, 1);
			registry.registerAlloy(MaterialNames.NICHROME, output, nickel, chrome );
		}

		if (Options.isMaterialEnabled(MaterialNames.STAINLESS_STEEL) &&
			Options.isMaterialEnabled(com.mcmoddev.basemetals.data.MaterialNames.STEEL) &&
			Options.isMaterialEnabled(MaterialNames.CHROMIUM) ) {
			FluidStack output = FluidRegistry.getFluidStack(MaterialNames.GALVANIZED_STEEL, 2);
			FluidStack steel = FluidRegistry.getFluidStack(com.mcmoddev.basemetals.data.MaterialNames.STEEL, 1);
			FluidStack chrome = FluidRegistry.getFluidStack(MaterialNames.CHROMIUM, 1);
			registry.registerAlloy(MaterialNames.STAINLESS_STEEL, output, steel, chrome);
		}

		if (Options.isMaterialEnabled(MaterialNames.TITANIUM) && Options.isMaterialEnabled(MaterialNames.RUTILE)) {
			FluidStack output = FluidRegistry.getFluidStack(MaterialNames.TITANIUM, 2);
			FluidStack rutile = FluidRegistry.getFluidStack(MaterialNames.RUTILE, 1);
			FluidStack magnesium = FluidRegistry.getFluidStack(MaterialNames.MAGNESIUM, 1);
			registry.registerAlloy(MaterialNames.TITANIUM, output, rutile, magnesium );
		}	
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
			if( i == (traits.length - 1) ) {
				// can only be a "general" trait
				mat.addTrait("general", traits[i]);
			} else {
				String item = traits[i];
				if( isTraitLoc(item) ) {
					if( i+1 >= traits.length ) {
						return;
					}
					i++;
					mat.addTrait(item, traits[i]);
				} else {
					mat.addTrait("general", traits[i]);
				}
			}
			i++;
		}
	}

	private void registerMaterial(boolean enabled, String name, boolean castable, boolean craftable, String... traits) {
		if (enabled) {
				registerMaterial(name, castable, craftable, traits);
		}
	}

	private void registerMaterial(String name, boolean castable, boolean craftable, String... traits) {
		MMDMaterial mmdMat = Materials.getMaterialByName(name);
		TCMaterial mat = registry.newMaterial(name, mmdMat.getTintColor());
		
		if( castable )
			mat.setCastable();
		if( craftable )
			mat.setCraftable();
		
		mat.setSourceMaterial(mmdMat);
		mat.genStatsFromSource();
		
		if (traits.length > 0) {
			addTraits(mat, traits);
		}
		
		mat.settle();
	}
}

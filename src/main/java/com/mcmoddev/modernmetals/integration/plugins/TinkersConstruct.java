package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.init.Materials;
import com.mcmoddev.modernmetals.util.Config.Options;
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
		if (initDone || !com.mcmoddev.basemetals.util.Config.Options.modEnabled.get("tinkersconstruct")) {
			return;
		}

		if (Options.enableAluminum) {
			registry.getMaterial("aluminum", Materials.getMaterialByName("aluminum")).addTrait("lightweight").setCastable(true).settle();
		}

		if (Options.enableAluminumBrass) {
			registry.getMaterial("aluminumbrass", Materials.getMaterialByName("aluminumbrass")).setCastable(true).settle();
		}

		if (Options.enableCadmium) {
			registry.getMaterial("cadmium", Materials.getMaterialByName("cadmium")).setCastable(true).settle();
		}

		if (Options.enableChromium) {
			registry.getMaterial("chromium", Materials.getMaterialByName("chromium")).setCastable(true).addTrait("magnetic").addTrait("magnetic2",TraitLocations.HEAD).settle();
		}

		if (Options.enableGalvanizedSteel) {
			registry.getMaterial("galvanizedsteel", Materials.getMaterialByName("galvanizedsteel")).addTrait("sharp", TraitLocations.HEAD).addTrait("stiff").setCastable(true).settle();
			registry.registerAlloy("galvanizedsteel",Materials.getMaterialByName("galvanizedsteel").getFluid(), 2, "steel", 1, "zinc", 1);
		}

		if (Options.enableIridium) {
			registry.getMaterial("iridium", Materials.getMaterialByName("iridium")).setCastable(true).addTrait("heavy").addTrait("brittle").settle();
		}

		if (Options.enableMagnesium) {
			registry.getMaterial("magnesium", Materials.getMaterialByName("magnesium")).setCastable(true).addTrait("reactive").settle();
		}

		if (Options.enableManganese) {
			registry.getMaterial("manganese", Materials.getMaterialByName("manganese")).addTrait("brittle").setCastable(true).settle();
		}

		if (Options.enableNichrome) {
			registry.getMaterial("nichrome", Materials.getMaterialByName("nichrome")).setCastable(true).settle();
			registry.registerAlloy("nichrome", Materials.getMaterialByName("nichrome").getFluid(), 2, "nickel", 1, "chromium", 1);
		}

		if (Options.enableOsmium) {
			registry.getMaterial("osmium", Materials.getMaterialByName("osmium")).setCastable(true).addTrait("heavy").addTrait("brittle").settle();
		}

		if (Options.enablePlutonium) {
			registry.getMaterial("plutonium", Materials.getMaterialByName("plutonium")).addTrait("radioactive").addTrait("toxic", TraitLocations.HEAD).addTrait("toxic", TraitLocations.PROJECTILE).setCastable(true).settle();
		}

		// request is for custom modifier item to grant +50% durability
		// This is not yet in the API - might require extra classes in MMDLib
		if (Options.enableRutile) {
			registry.getMaterial("rutile", Materials.getMaterialByName("rutile")).setCastable(true).settle();
		}

		if (Options.enableStainlessSteel) {
			registry.getMaterial("stainlesssteel", Materials.getMaterialByName("stainlesssteel")).setCastable(true).addTrait("sharp", TraitLocations.HEAD).addTrait("stiff").settle();
			registry.registerAlloy("stainlesssteel", Materials.getMaterialByName("stainlesssteel").getFluid(), 2, "steel", 1, "chromium", 1);
		}

		if (Options.enableTantalum) {
			registry.getMaterial("tantalum", Materials.getMaterialByName("tantalum")).setCastable(true).settle();
		}

		if (Options.enableTitanium) {
			registry.getMaterial("titanium", Materials.getMaterialByName("titanium")).setCastable(true).settle();
			registry.registerAlloy("titanium", Materials.getMaterialByName("titanium").getFluid(), 2, "rutile", 1, "magnesium", 1 );
		}

		if (Options.enableTungsten) {
			registry.getMaterial("tungsten", Materials.getMaterialByName("tungsten")).setCastable(true).settle();
		}

		if (Options.enableUranium) {
			registry.getMaterial("uranium", Materials.getMaterialByName("uranium")).setCastable(true).addTrait("poisonous", TraitLocations.HEAD).addTrait("poisonous", TraitLocations.PROJECTILE).addTrait("radioactive").settle();
		}

		if (Options.enableZirconium) {
			registry.getMaterial("zirconium", Materials.getMaterialByName("zirconium")).setCastable(true).settle();
		}

		registry.registerAll();
		initDone = true;
	}
}

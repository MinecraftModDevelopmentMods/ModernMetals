package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.util.Config.Options;
import com.mcmoddev.modernmetals.init.Materials;
import com.mcmoddev.modernmetals.integration.ModernMetalsPlugin;
import com.mcmoddev.lib.integration.plugins.tinkers.TCMetalMaterial;
import com.mcmoddev.lib.integration.plugins.tinkers.traits.MMDTraits;
import slimeknights.tconstruct.tools.TinkerTraits;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import com.mcmoddev.lib.integration.IIntegration;

/**
 *
 * @author Jasmine Iwanek
 *
 */
@ModernMetalsPlugin(TinkersConstruct.PLUGIN_MODID)
public class TinkersConstruct extends com.mcmoddev.lib.integration.plugins.TinkersConstruct implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !com.mcmoddev.basemetals.util.Config.Options.enableTinkersConstruct) {
			return;
		}

		if (Options.enableAluminum) {
			TCMetalMaterial aluminumTC = new TCMetalMaterial(Materials.aluminum);
			aluminumTC.craftable=false;

			aluminumTC.addTrait(TinkerTraits.lightweight);
			registerMaterial(aluminumTC);
		}

		if (Options.enableAluminumBrass) {
			TCMetalMaterial alubrassTC = new TCMetalMaterial(Materials.aluminumBrass);
			alubrassTC.craftable = false;
			
			registerMaterial(alubrassTC);
		}

		if (Options.enableCadmium) {
			TCMetalMaterial cadmiumTC = new TCMetalMaterial(Materials.cadmium);
			cadmiumTC.craftable = false;
			
			registerMaterial(cadmiumTC);
		}

		if (Options.enableChromium) {
			TCMetalMaterial chromiumTC = new TCMetalMaterial(Materials.chromium);
			chromiumTC.craftable = false;
			
			chromiumTC.addTrait(TinkerTraits.magnetic);
			chromiumTC.addTrait(TinkerTraits.magnetic2, MaterialTypes.HEAD);
			registerMaterial(chromiumTC);
		}

		if (Options.enableGalvanizedSteel) {
			TCMetalMaterial galsteelTC = new TCMetalMaterial(Materials.galvanizedSteel);
			galsteelTC.craftable = false;
			
			galsteelTC.addTrait(TinkerTraits.sharp, MaterialTypes.HEAD);
			galsteelTC.addTrait(TinkerTraits.stiff);
			
			registerMaterial(galsteelTC);
			registerAlloy(Materials.galvanizedSteel.fluid.getName(), 2, new String[] { "steel", "zinc" }, new int[] { 1, 1 });
		}

		// TODO: add TraitHeavy to plugins.tinkers.traits - adds knockback
		// TODO: add TraitBrittle - takes damage when hitting ore:stone blocks
		if (Options.enableIridium) {
			TCMetalMaterial iridiumTC = new TCMetalMaterial(Materials.iridium);
			iridiumTC.craftable = false;
			
			iridiumTC.addTrait( MMDTraits.heavy );
			iridiumTC.addTrait( MMDTraits.brittle );
			registerMaterial(iridiumTC);
		}

		// TODO: add TraitReactive - extra damage to water mobs, extra damage when used in water
		if (Options.enableMagnesium) {
			TCMetalMaterial magnesiumTC = new TCMetalMaterial(Materials.magnesium);
			magnesiumTC.craftable = false;
			
			// magnesiumTC.addTrait(MMDTraits.reactive);
			registerMaterial(magnesiumTC);
		}

		if (Options.enableManganese) {
			TCMetalMaterial manganeseTC = new TCMetalMaterial(Materials.manganese);
			manganeseTC.craftable = false;
			
			manganeseTC.addTrait(MMDTraits.brittle);
			registerMaterial(manganeseTC);
		}

		if (Options.enableNichrome) {
			TCMetalMaterial nichromeTC = new TCMetalMaterial(Materials.nichrome);
			nichromeTC.craftable = false;
			
			registerMaterial(nichromeTC);
			registerAlloy(Materials.nichrome.fluid.getName(), 2, new String[] { "nickel", "chromium" }, new int[] { 1, 1 });
		}

		if (Options.enableOsmium) {
			TCMetalMaterial osmiumTC = new TCMetalMaterial(Materials.osmium);
			osmiumTC.craftable = false;
			
			osmiumTC.addTrait( MMDTraits.heavy );
			osmiumTC.addTrait( MMDTraits.brittle );

			registerMaterial(osmiumTC);
		}

		// TODO: add TraitRadioactive - deals damage to user if weapon doesn't have modifier:lead
		// TODO: add TraitToxic - poison II on enemies
		if (Options.enablePlutonium) {
			TCMetalMaterial plutoniumTC = new TCMetalMaterial(Materials.plutonium);
			plutoniumTC.craftable = false;
			
			//plutoniumTC.addTrait(MMDTraits.radioactive);
			plutoniumTC.addTrait(MMDTraits.toxic, MaterialTypes.HEAD);
			plutoniumTC.addTrait(MMDTraits.toxic, MaterialTypes.PROJECTILE);
			
			registerMaterial(plutoniumTC);
		}

		// request is for custom modifier item to grant +50% durability
		// This is not yet in the API - might require extra classes in MMDLib
		if (Options.enableRutile) {
			TCMetalMaterial rutileTC = new TCMetalMaterial(Materials.rutile);
			rutileTC.craftable = false;
			
			registerMaterial(rutileTC);
		}

		if (Options.enableStainlessSteel) {
			TCMetalMaterial stainlessTC = new TCMetalMaterial(Materials.stainlessSteel);
			stainlessTC.craftable = false;
			
			stainlessTC.addTrait(TinkerTraits.sharp, MaterialTypes.HEAD);
			stainlessTC.addTrait(TinkerTraits.stiff);
			
			registerMaterial(stainlessTC);
			registerAlloy(Materials.stainlessSteel.fluid.getName(), 2, new String[] { "steel", "chromium" }, new int[] { 1, 1 });
		}

		if (Options.enableTantalum) {
			TCMetalMaterial tantalumTC = new TCMetalMaterial(Materials.tantalum);
			tantalumTC.craftable = false;
			
			registerMaterial(tantalumTC);
		}

		if (Options.enableTitanium) {
			TCMetalMaterial titaniumTC = new TCMetalMaterial(Materials.titanium);
			titaniumTC.craftable = false;
			
			registerMaterial(titaniumTC);
			registerAlloy(Materials.titanium.fluid.getName(), 2, new String[] { "rutile", "magnesium" }, new int[] { 1, 1 });
		}

		if (Options.enableTungsten) {
			TCMetalMaterial tungstenTC = new TCMetalMaterial(Materials.tungsten);
			tungstenTC.craftable = false;
			
			registerMaterial(tungstenTC);
		}

		if (Options.enableUranium) {
			TCMetalMaterial uraniumTC = new TCMetalMaterial(Materials.uranium);
			uraniumTC.craftable = false;
			
			uraniumTC.addTrait(TinkerTraits.poisonous, MaterialTypes.HEAD);
			uraniumTC.addTrait(TinkerTraits.poisonous, MaterialTypes.PROJECTILE);
			//uraniumTC.addTrait(traits.radioactive);
			
			registerMaterial(uraniumTC);
		}

		if (Options.enableZirconium) {
			TCMetalMaterial zirconiumTC = new TCMetalMaterial(Materials.zirconium);
			zirconiumTC.craftable = false;
			
			registerMaterial(zirconiumTC);
		}

		initDone = true;
	}
}

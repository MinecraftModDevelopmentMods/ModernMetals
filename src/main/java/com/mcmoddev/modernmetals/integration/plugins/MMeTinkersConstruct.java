package com.mcmoddev.modernmetals.integration.plugins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.integration.plugins.TinkersConstruct;
import com.mcmoddev.lib.integration.plugins.tinkers.TinkerTraitLocation;
import com.mcmoddev.lib.integration.plugins.tinkers.TinkersMaterial;
import com.mcmoddev.lib.integration.plugins.tinkers.events.MaterialRegistrationEvent;
import com.mcmoddev.lib.integration.plugins.tinkers.events.TinkersAlloyRecipeEvent;

import com.mcmoddev.lib.util.Config.Options;
import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.modernmetals.data.TraitNames;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 *
 * @author Jasmine Iwanek
 *
 */
@MMDPlugin(addonId = ModernMetals.MODID, pluginId = MMeTinkersConstruct.PLUGIN_MODID,
           versions = MMeTinkersConstruct.PLUGIN_MODID + "@[1.12.2-2.7.4.0,)")

public class MMeTinkersConstruct implements IIntegration {

	public static final String PLUGIN_MODID = TinkersConstruct.PLUGIN_MODID;
	
	@Override
	public void init() {
		TinkersConstruct.INSTANCE.init();
		if (!Options.isModEnabled(PLUGIN_MODID)) {
			return;
		}

		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void materialRegistration(MaterialRegistrationEvent ev) {
		ModContainer oldActive = Loader.instance().activeModContainer();
		ModContainer newActive = Loader.instance().getIndexedModList().get(ModernMetals.MODID);
		Loader.instance().setActiveModContainer(newActive);
/*
 *  Strangely enough, Aluminum doesn't trigger a "this already exists" check like Lead or Copper do...
 *  Lets make sure we exclude it and Aluminum Brass here, for safety.

		registerMaterial(Materials.hasMaterial(MaterialNames.ALUMINUM), MaterialNames.ALUMINUM, true, false,
				TraitNames.LIGHTWEIGHT)

		registerMaterial(Materials.hasMaterial(MaterialNames.ALUMINUM_BRASS), MaterialNames.ALUMINUM_BRASS, true,
				false)
 */
		registerMaterial(Materials.hasMaterial(MaterialNames.BERYLLIUM), MaterialNames.BERYLLIUM, true, false, ev);

		registerMaterial(Materials.hasMaterial(MaterialNames.BORON), MaterialNames.BORON, true, false, ev);

		registerMaterial(Materials.hasMaterial(MaterialNames.CADMIUM), MaterialNames.CADMIUM, true, false, ev);

		registerMaterial(Materials.hasMaterial(MaterialNames.CHROMIUM), MaterialNames.CHROMIUM, true, false, ev,
				TraitNames.MAGNETIC, TinkerTraitLocation.HEAD, TraitNames.MAGNETIC2);

		registerMaterial(Materials.hasMaterial(MaterialNames.GALVANIZED_STEEL), MaterialNames.GALVANIZED_STEEL, true,
				false, ev, TinkerTraitLocation.HEAD, TraitNames.SHARP, TraitNames.STIFF);

		registerMaterial(Materials.hasMaterial(MaterialNames.IRIDIUM), MaterialNames.IRIDIUM, true, false, ev,
				TraitNames.BRITTLE);

		registerMaterial(Materials.hasMaterial(MaterialNames.MAGNESIUM), MaterialNames.MAGNESIUM, true, false, ev,
				TraitNames.REACTIVE);

		registerMaterial(Materials.hasMaterial(MaterialNames.MANGANESE), MaterialNames.MANGANESE, true, false, ev,
				TraitNames.BRITTLE);

		registerMaterial(Materials.hasMaterial(MaterialNames.NICHROME), MaterialNames.NICHROME, true, false, ev);

		registerMaterial(Materials.hasMaterial(MaterialNames.OSMIUM), MaterialNames.OSMIUM, true, false, ev,
				TraitNames.HEAVY, TraitNames.BRITTLE);

		registerMaterial(Materials.hasMaterial(MaterialNames.PLUTONIUM), MaterialNames.PLUTONIUM, true, false, ev,
				TraitNames.RADIOACTIVE, TinkerTraitLocation.HEAD, TraitNames.TOXIC, TinkerTraitLocation.PROJECTILE,
				TraitNames.TOXIC);

		registerMaterial(Materials.hasMaterial(MaterialNames.RUTILE), MaterialNames.RUTILE, true, false, ev);

		registerMaterial(Materials.hasMaterial(MaterialNames.STAINLESS_STEEL), MaterialNames.STAINLESS_STEEL, true,
				false, ev, TinkerTraitLocation.HEAD, TraitNames.SHARP, TraitNames.STIFF);

		registerMaterial(Materials.hasMaterial(MaterialNames.TANTALUM), MaterialNames.TANTALUM, true, false, ev);

		registerMaterial(Materials.hasMaterial(MaterialNames.THORIUM), MaterialNames.THORIUM, true, false, ev);

		registerMaterial(Materials.hasMaterial(MaterialNames.TITANIUM), MaterialNames.TITANIUM, true, false, ev);

		registerMaterial(Materials.hasMaterial(MaterialNames.TUNGSTEN), MaterialNames.TUNGSTEN, true, false, ev);

		registerMaterial(Materials.hasMaterial(MaterialNames.URANIUM), MaterialNames.URANIUM, true, false, ev,
				TraitNames.RADIOACTIVE, TinkerTraitLocation.HEAD, TraitNames.POISONOUS, TinkerTraitLocation.PROJECTILE,
				TraitNames.POISONOUS);

		registerMaterial(Materials.hasMaterial(MaterialNames.ZIRCONIUM), MaterialNames.ZIRCONIUM, true, false, ev);
		Loader.instance().setActiveModContainer(oldActive);
	}

	private void registerMaterial(final boolean active, final String name, final boolean castable, final boolean craftable,
			MaterialRegistrationEvent ev, final Object... traits) {
		if (active) {
			TinkersMaterial mat = new TinkersMaterial(Materials.getMaterialByName(name))
					.setCastable(castable).setCraftable(craftable).setToolForge(true);
			int i = 0;
			while(i < traits.length) {
				TinkerTraitLocation loc;
				String trait;
				Object item = traits[i];
				if(item instanceof TinkerTraitLocation) {
					loc = (TinkerTraitLocation)item;
					trait = ((String)traits[++i]).toLowerCase(Locale.US);
					
					mat.addTrait(trait, loc);
				} else {
					trait = ((String)item).toLowerCase(Locale.US);
					mat.addTrait(trait);
				}
				i++;
			}
			
			ev.getRegistry().register(mat.create());
		}
	}

	private void registerMaterial(final boolean active, final String name, final boolean castable,
			final boolean craftable, MaterialRegistrationEvent ev) {
		if (active) {
			TinkersMaterial mat = new TinkersMaterial(Materials.getMaterialByName(name))
					.setCastable(castable).setCraftable(craftable).setToolForge(true).create();

			ev.getRegistry().register(mat);
		}
	}

	private void registerAlloy(TinkersAlloyRecipeEvent ev, final String outputMaterialName, final int outputAmount, final Object[] recipe) {
		final List<String> materialNames = new ArrayList<>();
		materialNames.add(outputMaterialName);
		Arrays.asList(recipe).stream()
		.filter(elem -> elem instanceof String)
		.forEach(elem -> materialNames.add((String)elem));
		if (!hasMaterials(materialNames)) {
			return;
		}

		final FluidStack[] rest = new FluidStack[recipe.length / 2];
		for (int i = 0, j = 0; i < recipe.length; i += 2, j++) {
			rest[j] = FluidRegistry.getFluidStack((String)recipe[i], (int)recipe[i + 1]);
		}
		ev.addAlloyRecipe(outputMaterialName, outputAmount, rest);
	}

	private boolean hasMaterials(final List<String> materialNames) {
		for (final String name : materialNames) {
			if (!Materials.hasMaterial(name)) {
				return false;
			}
		}
		return true;
	}

	@SubscribeEvent
	public void registerMyAlloys(TinkersAlloyRecipeEvent ev) {
		ModContainer oldActive = Loader.instance().activeModContainer();
		ModContainer newActive = Loader.instance().getIndexedModList().get(ModernMetals.MODID);
		Loader.instance().setActiveModContainer(newActive);
		registerAlloy(ev, MaterialNames.GALVANIZED_STEEL, 2, new Object[] { com.mcmoddev.basemetals.data.MaterialNames.STEEL, 1, com.mcmoddev.basemetals.data.MaterialNames.ZINC, 1 });
		registerAlloy(ev, MaterialNames.NICHROME, 2, new Object[] { com.mcmoddev.basemetals.data.MaterialNames.NICKEL, 1, MaterialNames.CHROMIUM, 1 });
		registerAlloy(ev, MaterialNames.STAINLESS_STEEL, 2, new Object[] { com.mcmoddev.basemetals.data.MaterialNames.STEEL, 1, MaterialNames.CHROMIUM, 1 });
		registerAlloy(ev, MaterialNames.TITANIUM, 2, new Object[] { MaterialNames.RUTILE, 1, MaterialNames.MAGNESIUM, 1 });
		Loader.instance().setActiveModContainer(oldActive);
	}
}

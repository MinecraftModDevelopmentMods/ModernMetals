package com.mcmoddev.modernmetals.integration.plugins;

import com.mcmoddev.modernmetals.init.Materials;
import com.mcmoddev.modernmetals.util.Config.Options;

import com.mcmoddev.modernmetals.integration.ModernMetalsPlugin;
import com.mcmoddev.basemetals.integration.IIntegration;
import com.mcmoddev.basemetals.material.MetalMaterial;

import net.minecraft.item.ItemStack;

@ModernMetalsPlugin(Mekanism.PLUGIN_MODID)
public class Mekanism extends com.mcmoddev.basemetals.integration.plugins.Mekanism implements IIntegration {

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !com.mcmoddev.basemetals.util.Config.Options.ENABLE_MEKANISM) {
			return;
		}

		MetalMaterial material;

		if (Options.ENABLE_ALUMINUM) {
			material = Materials.aluminum;
			addCrusherRecipe(new ItemStack(material.clump), new ItemStack(material.powder_dirty));
			addCrusherRecipe(new ItemStack(material.ingot), new ItemStack(material.powder));

			addEnrichmentChamberRecipe(new ItemStack(material.ore), new ItemStack(material.powder, 2));
			addEnrichmentChamberRecipe(new ItemStack(material.powder_dirty), new ItemStack(material.powder));

			addPurificationChamberRecipe(new ItemStack(material.ore), new ItemStack(material.clump, 3));
			addPurificationChamberRecipe(new ItemStack(material.shard), new ItemStack(material.clump));

			addChemicalInjectionChamberRecipe(new ItemStack(material.ore), new ItemStack(material.shard, 4));
			addChemicalInjectionChamberRecipe(new ItemStack(material.crystal), new ItemStack(material.shard));
		}

		if (Options.ENABLE_CADMIUM) {
			material = Materials.cadmium;
			addCrusherRecipe(new ItemStack(material.clump), new ItemStack(material.powder_dirty));
			addCrusherRecipe(new ItemStack(material.ingot), new ItemStack(material.powder));

			addEnrichmentChamberRecipe(new ItemStack(material.ore), new ItemStack(material.powder, 2));
			addEnrichmentChamberRecipe(new ItemStack(material.powder_dirty), new ItemStack(material.powder));

			addPurificationChamberRecipe(new ItemStack(material.ore), new ItemStack(material.clump, 3));
			addPurificationChamberRecipe(new ItemStack(material.shard), new ItemStack(material.clump));

			addChemicalInjectionChamberRecipe(new ItemStack(material.ore), new ItemStack(material.shard, 4));
			addChemicalInjectionChamberRecipe(new ItemStack(material.crystal), new ItemStack(material.shard));
		}

		if (Options.ENABLE_CHROMIUM) {
			material = Materials.chromium;
			addCrusherRecipe(new ItemStack(material.clump), new ItemStack(material.powder_dirty));
			addCrusherRecipe(new ItemStack(material.ingot), new ItemStack(material.powder));

			addEnrichmentChamberRecipe(new ItemStack(material.ore), new ItemStack(material.powder, 2));
			addEnrichmentChamberRecipe(new ItemStack(material.powder_dirty), new ItemStack(material.powder));

			addPurificationChamberRecipe(new ItemStack(material.ore), new ItemStack(material.clump, 3));
			addPurificationChamberRecipe(new ItemStack(material.shard), new ItemStack(material.clump));

			addChemicalInjectionChamberRecipe(new ItemStack(material.ore), new ItemStack(material.shard, 4));
			addChemicalInjectionChamberRecipe(new ItemStack(material.crystal), new ItemStack(material.shard));
		}

		if (Options.ENABLE_IRIDIUM) {
			material = Materials.iridium;
			addCrusherRecipe(new ItemStack(material.clump), new ItemStack(material.powder_dirty));
			addCrusherRecipe(new ItemStack(material.ingot), new ItemStack(material.powder));

			addEnrichmentChamberRecipe(new ItemStack(material.ore), new ItemStack(material.powder, 2));
			addEnrichmentChamberRecipe(new ItemStack(material.powder_dirty), new ItemStack(material.powder));

			addPurificationChamberRecipe(new ItemStack(material.ore), new ItemStack(material.clump, 3));
			addPurificationChamberRecipe(new ItemStack(material.shard), new ItemStack(material.clump));

			addChemicalInjectionChamberRecipe(new ItemStack(material.ore), new ItemStack(material.shard, 4));
			addChemicalInjectionChamberRecipe(new ItemStack(material.crystal), new ItemStack(material.shard));
		}

		if (Options.ENABLE_MAGNESIUM) {
			material = Materials.magnesium;
			addCrusherRecipe(new ItemStack(material.clump), new ItemStack(material.powder_dirty));
			addCrusherRecipe(new ItemStack(material.ingot), new ItemStack(material.powder));

			addEnrichmentChamberRecipe(new ItemStack(material.ore), new ItemStack(material.powder, 2));
			addEnrichmentChamberRecipe(new ItemStack(material.powder_dirty), new ItemStack(material.powder));

			addPurificationChamberRecipe(new ItemStack(material.ore), new ItemStack(material.clump, 3));
			addPurificationChamberRecipe(new ItemStack(material.shard), new ItemStack(material.clump));

			addChemicalInjectionChamberRecipe(new ItemStack(material.ore), new ItemStack(material.shard, 4));
			addChemicalInjectionChamberRecipe(new ItemStack(material.crystal), new ItemStack(material.shard));
		}

		if (Options.ENABLE_MANGANESE) {
			material = Materials.manganese;
			addCrusherRecipe(new ItemStack(material.clump), new ItemStack(material.powder_dirty));
			addCrusherRecipe(new ItemStack(material.ingot), new ItemStack(material.powder));

			addEnrichmentChamberRecipe(new ItemStack(material.ore), new ItemStack(material.powder, 2));
			addEnrichmentChamberRecipe(new ItemStack(material.powder_dirty), new ItemStack(material.powder));

			addPurificationChamberRecipe(new ItemStack(material.ore), new ItemStack(material.clump, 3));
			addPurificationChamberRecipe(new ItemStack(material.shard), new ItemStack(material.clump));

			addChemicalInjectionChamberRecipe(new ItemStack(material.ore), new ItemStack(material.shard, 4));
			addChemicalInjectionChamberRecipe(new ItemStack(material.crystal), new ItemStack(material.shard));
		}

		if (Options.ENABLE_PLUTONIUM) {
			material = Materials.plutonium;
			addCrusherRecipe(new ItemStack(material.clump), new ItemStack(material.powder_dirty));
			addCrusherRecipe(new ItemStack(material.ingot), new ItemStack(material.powder));

			addEnrichmentChamberRecipe(new ItemStack(material.ore), new ItemStack(material.powder, 2));
			addEnrichmentChamberRecipe(new ItemStack(material.powder_dirty), new ItemStack(material.powder));

			addPurificationChamberRecipe(new ItemStack(material.ore), new ItemStack(material.clump, 3));
			addPurificationChamberRecipe(new ItemStack(material.shard), new ItemStack(material.clump));

			addChemicalInjectionChamberRecipe(new ItemStack(material.ore), new ItemStack(material.shard, 4));
			addChemicalInjectionChamberRecipe(new ItemStack(material.crystal), new ItemStack(material.shard));
		}

		if (Options.ENABLE_RUTILE) {
			material = Materials.rutile;
			addCrusherRecipe(new ItemStack(material.clump), new ItemStack(material.powder_dirty));
			addCrusherRecipe(new ItemStack(material.ingot), new ItemStack(material.powder));

			addEnrichmentChamberRecipe(new ItemStack(material.ore), new ItemStack(material.powder, 2));
			addEnrichmentChamberRecipe(new ItemStack(material.powder_dirty), new ItemStack(material.powder));

			addPurificationChamberRecipe(new ItemStack(material.ore), new ItemStack(material.clump, 3));
			addPurificationChamberRecipe(new ItemStack(material.shard), new ItemStack(material.clump));

			addChemicalInjectionChamberRecipe(new ItemStack(material.ore), new ItemStack(material.shard, 4));
			addChemicalInjectionChamberRecipe(new ItemStack(material.crystal), new ItemStack(material.shard));
		}

		if (Options.ENABLE_TANTALUM) {
			material = Materials.tantalum;
			addCrusherRecipe(new ItemStack(material.clump), new ItemStack(material.powder_dirty));
			addCrusherRecipe(new ItemStack(material.ingot), new ItemStack(material.powder));

			addEnrichmentChamberRecipe(new ItemStack(material.ore), new ItemStack(material.powder, 2));
			addEnrichmentChamberRecipe(new ItemStack(material.powder_dirty), new ItemStack(material.powder));

			addPurificationChamberRecipe(new ItemStack(material.ore), new ItemStack(material.clump, 3));
			addPurificationChamberRecipe(new ItemStack(material.shard), new ItemStack(material.clump));

			addChemicalInjectionChamberRecipe(new ItemStack(material.ore), new ItemStack(material.shard, 4));
			addChemicalInjectionChamberRecipe(new ItemStack(material.crystal), new ItemStack(material.shard));
		}

		if (Options.ENABLE_TITANIUM) {
			material = Materials.titanium;
			addCrusherRecipe(new ItemStack(material.clump), new ItemStack(material.powder_dirty));
			addCrusherRecipe(new ItemStack(material.ingot), new ItemStack(material.powder));

			addEnrichmentChamberRecipe(new ItemStack(material.ore), new ItemStack(material.powder, 2));
			addEnrichmentChamberRecipe(new ItemStack(material.powder_dirty), new ItemStack(material.powder));

			addPurificationChamberRecipe(new ItemStack(material.ore), new ItemStack(material.clump, 3));
			addPurificationChamberRecipe(new ItemStack(material.shard), new ItemStack(material.clump));

			addChemicalInjectionChamberRecipe(new ItemStack(material.ore), new ItemStack(material.shard, 4));
			addChemicalInjectionChamberRecipe(new ItemStack(material.crystal), new ItemStack(material.shard));
		}

		if (Options.ENABLE_TUNGSTEN) {
			material = Materials.tungsten;
			addCrusherRecipe(new ItemStack(material.clump), new ItemStack(material.powder_dirty));
			addCrusherRecipe(new ItemStack(material.ingot), new ItemStack(material.powder));

			addEnrichmentChamberRecipe(new ItemStack(material.ore), new ItemStack(material.powder, 2));
			addEnrichmentChamberRecipe(new ItemStack(material.powder_dirty), new ItemStack(material.powder));

			addPurificationChamberRecipe(new ItemStack(material.ore), new ItemStack(material.clump, 3));
			addPurificationChamberRecipe(new ItemStack(material.shard), new ItemStack(material.clump));

			addChemicalInjectionChamberRecipe(new ItemStack(material.ore), new ItemStack(material.shard, 4));
			addChemicalInjectionChamberRecipe(new ItemStack(material.crystal), new ItemStack(material.shard));
		}

		if (Options.ENABLE_URANIUM) {
			material = Materials.uranium;
			addCrusherRecipe(new ItemStack(material.clump), new ItemStack(material.powder_dirty));
			addCrusherRecipe(new ItemStack(material.ingot), new ItemStack(material.powder));

			addEnrichmentChamberRecipe(new ItemStack(material.ore), new ItemStack(material.powder, 2));
			addEnrichmentChamberRecipe(new ItemStack(material.powder_dirty), new ItemStack(material.powder));

			addPurificationChamberRecipe(new ItemStack(material.ore), new ItemStack(material.clump, 3));
			addPurificationChamberRecipe(new ItemStack(material.shard), new ItemStack(material.clump));

			addChemicalInjectionChamberRecipe(new ItemStack(material.ore), new ItemStack(material.shard, 4));
			addChemicalInjectionChamberRecipe(new ItemStack(material.crystal), new ItemStack(material.shard));
		}

		if (Options.ENABLE_ZIRCONIUM) {
			material = Materials.zirconium;
			addCrusherRecipe(new ItemStack(material.clump), new ItemStack(material.powder_dirty));
			addCrusherRecipe(new ItemStack(material.ingot), new ItemStack(material.powder));

			addEnrichmentChamberRecipe(new ItemStack(material.ore), new ItemStack(material.powder, 2));
			addEnrichmentChamberRecipe(new ItemStack(material.powder_dirty), new ItemStack(material.powder));

			addPurificationChamberRecipe(new ItemStack(material.ore), new ItemStack(material.clump, 3));
			addPurificationChamberRecipe(new ItemStack(material.shard), new ItemStack(material.clump));

			addChemicalInjectionChamberRecipe(new ItemStack(material.ore), new ItemStack(material.shard, 4));
			addChemicalInjectionChamberRecipe(new ItemStack(material.crystal), new ItemStack(material.shard));
		}

		initDone = true;
	}
}

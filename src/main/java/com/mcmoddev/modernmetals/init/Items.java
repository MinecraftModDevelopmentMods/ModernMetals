package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.util.Config.Options;

import com.mcmoddev.basemetals.material.MetalMaterial;
import net.minecraft.item.*;

/**
 * This class initializes all items in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Items extends com.mcmoddev.basemetals.init.Items {

	public static Item aluminum_boots;
	public static Item aluminum_chestplate;
	public static Item aluminum_helmet;
	public static Item aluminum_ingot;
	public static Item aluminum_leggings;
	public static Item aluminum_nugget;
	public static Item aluminum_powder;
	public static Item aluminum_sword;

	public static Item aluminumbrass_blend;
	public static Item aluminumbrass_boots;
	public static Item aluminumbrass_chestplate;
	public static Item aluminumbrass_helmet;
	public static Item aluminumbrass_ingot;
	public static Item aluminumbrass_leggings;
	public static Item aluminumbrass_nugget;
	public static Item aluminumbrass_powder;
	public static Item aluminumbrass_sword;

	public static Item cadmium_boots;
	public static Item cadmium_chestplate;
	public static Item cadmium_helmet;
	public static Item cadmium_ingot;
	public static Item cadmium_leggings;
	public static Item cadmium_nugget;
	public static Item cadmium_powder;
	public static Item cadmium_sword;

	public static Item chromium_boots;
	public static Item chromium_chestplate;
	public static Item chromium_helmet;
	public static Item chromium_ingot;
	public static Item chromium_leggings;
	public static Item chromium_nugget;
	public static Item chromium_powder;
	public static Item chromium_sword;

	public static Item galvanizedsteel_blend;
	public static Item galvanizedsteel_boots;
	public static Item galvanizedsteel_chestplate;
	public static Item galvanizedsteel_helmet;
	public static Item galvanizedsteel_ingot;
	public static Item galvanizedsteel_leggings;
	public static Item galvanizedsteel_nugget;
	public static Item galvanizedsteel_powder;
	public static Item galvanizedsteel_sword;

	public static Item iridium_boots;
	public static Item iridium_chestplate;
	public static Item iridium_helmet;
	public static Item iridium_ingot;
	public static Item iridium_leggings;
	public static Item iridium_nugget;
	public static Item iridium_powder;
	public static Item iridium_sword;

	public static Item magnesium_boots;
	public static Item magnesium_chestplate;
	public static Item magnesium_helmet;
	public static Item magnesium_ingot;
	public static Item magnesium_leggings;
	public static Item magnesium_nugget;
	public static Item magnesium_powder;
	public static Item magnesium_sword;

	public static Item manganese_boots;
	public static Item manganese_chestplate;
	public static Item manganese_helmet;
	public static Item manganese_ingot;
	public static Item manganese_leggings;
	public static Item manganese_nugget;
	public static Item manganese_powder;
	public static Item manganese_sword;

	public static Item nichrome_blend;
	public static Item nichrome_boots;
	public static Item nichrome_chestplate;
	public static Item nichrome_helmet;
	public static Item nichrome_ingot;
	public static Item nichrome_leggings;
	public static Item nichrome_nugget;
	public static Item nichrome_powder;
	public static Item nichrome_sword;

	public static Item osmium_boots;
	public static Item osmium_chestplate;
	public static Item osmium_helmet;
	public static Item osmium_ingot;
	public static Item osmium_leggings;
	public static Item osmium_nugget;
	public static Item osmium_powder;
	public static Item osmium_sword;

	public static Item plutonium_boots;
	public static Item plutonium_chestplate;
	public static Item plutonium_helmet;
	public static Item plutonium_ingot;
	public static Item plutonium_leggings;
	public static Item plutonium_nugget;
	public static Item plutonium_powder;
	public static Item plutonium_sword;

	public static Item rutile_boots;
	public static Item rutile_chestplate;
	public static Item rutile_helmet;
	public static Item rutile_ingot;
	public static Item rutile_leggings;
	public static Item rutile_nugget;
	public static Item rutile_powder;
	public static Item rutile_sword;

	public static Item stainlesssteel_blend;
	public static Item stainlesssteel_boots;
	public static Item stainlesssteel_chestplate;
	public static Item stainlesssteel_helmet;
	public static Item stainlesssteel_ingot;
	public static Item stainlesssteel_leggings;
	public static Item stainlesssteel_nugget;
	public static Item stainlesssteel_powder;
	public static Item stainlesssteel_sword;

	public static Item tantalum_boots;
	public static Item tantalum_chestplate;
	public static Item tantalum_helmet;
	public static Item tantalum_ingot;
	public static Item tantalum_leggings;
	public static Item tantalum_nugget;
	public static Item tantalum_powder;
	public static Item tantalum_sword;

	public static Item titanium_blend;
	public static Item titanium_boots;
	public static Item titanium_chestplate;
	public static Item titanium_helmet;
	public static Item titanium_ingot;
	public static Item titanium_leggings;
	public static Item titanium_nugget;
	public static Item titanium_powder;
	public static Item titanium_sword;

	public static Item tungsten_boots;
	public static Item tungsten_chestplate;
	public static Item tungsten_helmet;
	public static Item tungsten_ingot;
	public static Item tungsten_leggings;
	public static Item tungsten_nugget;
	public static Item tungsten_powder;
	public static Item tungsten_sword;

	public static Item uranium_boots;
	public static Item uranium_chestplate;
	public static Item uranium_helmet;
	public static Item uranium_ingot;
	public static Item uranium_leggings;
	public static Item uranium_nugget;
	public static Item uranium_powder;
	public static Item uranium_sword;

	public static Item zirconium_boots;
	public static Item zirconium_chestplate;
	public static Item zirconium_helmet;
	public static Item zirconium_ingot;
	public static Item zirconium_leggings;
	public static Item zirconium_nugget;
	public static Item zirconium_powder;
	public static Item zirconium_sword;

	private static boolean initDone = false;

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		Blocks.init();

		MetalMaterial material;

		if (Options.ENABLE_ALUMINUM) {
			material = Materials.aluminum;
			createItemsFull(material);
			createItemsModSupport(material);

			aluminum_boots = createBoots(material);
			aluminum_chestplate = createChestplate(material);
			aluminum_helmet = createHelmet(material);
			aluminum_ingot = createIngot(material);
			aluminum_leggings = createLeggings(material);
			aluminum_nugget = createNugget(material);
			aluminum_powder = createPowder(material);
			aluminum_sword = createSword(material);
		}

		if (Options.ENABLE_ALUMINUMBRASS) {
			material = Materials.aluminumbrass;
			createItemsFull(material);

			aluminumbrass_blend = createBlend(material);
			aluminumbrass_boots = createBoots(material);
			aluminumbrass_chestplate = createChestplate(material);
			aluminumbrass_helmet = createHelmet(material);
			aluminumbrass_ingot = createIngot(material);
			aluminumbrass_leggings = createLeggings(material);
			aluminumbrass_nugget = createNugget(material);
			aluminumbrass_powder = createPowder(material);
			aluminumbrass_sword = createSword(material);
		}

		if (Options.ENABLE_CADMIUM) {
			material = Materials.cadmium;
			createItemsFull(material);
			createItemsModSupport(material);

			cadmium_boots = createBoots(material);
			cadmium_chestplate = createChestplate(material);
			cadmium_helmet = createHelmet(material);
			cadmium_ingot = createIngot(material);
			cadmium_leggings = createLeggings(material);
			cadmium_nugget = createNugget(material);
			cadmium_powder = createPowder(material);
			cadmium_sword = createSword(material);
		}

		if (Options.ENABLE_CHROMIUM) {
			material = Materials.chromium;
			createItemsFull(material);
			createItemsModSupport(material);

			chromium_boots = createBoots(material);
			chromium_chestplate = createChestplate(material);
			chromium_helmet = createHelmet(material);
			chromium_ingot = createIngot(material);
			chromium_leggings = createLeggings(material);
			chromium_nugget = createNugget(material);
			chromium_powder = createPowder(material);
			chromium_sword = createSword(material);
		}

		if (Options.ENABLE_GALVANIZEDSTEEL) {
			material = Materials.galvanizedsteel;
			createItemsFull(material);

			galvanizedsteel_blend = createBlend(material);
			galvanizedsteel_boots = createBoots(material);
			galvanizedsteel_chestplate = createChestplate(material);
			galvanizedsteel_helmet = createHelmet(material);
			galvanizedsteel_ingot = createIngot(material);
			galvanizedsteel_leggings = createLeggings(material);
			galvanizedsteel_nugget = createNugget(material);
			galvanizedsteel_powder = createPowder(material);
			galvanizedsteel_sword = createSword(material);
		}

		if (Options.ENABLE_IRIDIUM) {
			material = Materials.iridium;
			createItemsFull(material);
			createItemsModSupport(material);

			iridium_boots = createBoots(material);
			iridium_chestplate = createChestplate(material);
			iridium_helmet = createHelmet(material);
			iridium_ingot = createIngot(material);
			iridium_leggings = createLeggings(material);
			iridium_nugget = createNugget(material);
			iridium_powder = createPowder(material);
			iridium_sword = createSword(material);
		}

		if (Options.ENABLE_MAGNESIUM) {
			material = Materials.magnesium;
			createItemsFull(material);
			createItemsModSupport(material);

			magnesium_boots = createBoots(material);
			magnesium_chestplate = createChestplate(material);
			magnesium_helmet = createHelmet(material);
			magnesium_ingot = createIngot(material);
			magnesium_leggings = createLeggings(material);
			magnesium_nugget = createNugget(material);
			magnesium_powder = createPowder(material);
			magnesium_sword = createSword(material);
		}

		if (Options.ENABLE_MANGANESE) {
			material = Materials.manganese;
			createItemsFull(material);
			createItemsModSupport(material);

			manganese_boots = createBoots(material);
			manganese_chestplate = createChestplate(material);
			manganese_helmet = createHelmet(material);
			manganese_ingot = createIngot(material);
			manganese_leggings = createLeggings(material);
			manganese_nugget = createNugget(material);
			manganese_powder = createPowder(material);
			manganese_sword = createSword(material);
		}

		if (Options.ENABLE_NICHROME) {
			material = Materials.nichrome;
			createItemsFull(material);

			nichrome_blend = createBlend(material);
			nichrome_boots = createBoots(material);
			nichrome_chestplate = createChestplate(material);
			nichrome_helmet = createHelmet(material);
			nichrome_ingot = createIngot(material);
			nichrome_leggings = createLeggings(material);
			nichrome_nugget = createNugget(material);
			nichrome_powder = createPowder(material);
			nichrome_sword = createSword(material);
		}

		if (Options.ENABLE_OSMIUM) {
			material = Materials.osmium;
			createItemsFull(material);
			createItemsModIC2(material);

			osmium_boots = createBoots(material);
			osmium_chestplate = createChestplate(material);
			osmium_helmet = createHelmet(material);
			osmium_ingot = createIngot(material);
			osmium_leggings = createLeggings(material);
			osmium_nugget = createNugget(material);
			osmium_powder = createPowder(material);
			osmium_sword = createSword(material);
		}

		if (Options.ENABLE_PLUTONIUM) {
			material = Materials.plutonium;
			createItemsFull(material);
			createItemsModSupport(material);

			plutonium_boots = createBoots(material);
			plutonium_chestplate = createChestplate(material);
			plutonium_helmet = createHelmet(material);
			plutonium_ingot = createIngot(material);
			plutonium_leggings = createLeggings(material);
			plutonium_nugget = createNugget(material);
			plutonium_powder = createPowder(material);
			plutonium_sword = createSword(material);
		}

		if (Options.ENABLE_RUTILE) {
			material = Materials.rutile;
			createItemsFull(material);
			createItemsModSupport(material);

			rutile_boots = createBoots(material);
			rutile_chestplate = createChestplate(material);
			rutile_helmet = createHelmet(material);
			rutile_ingot = createIngot(material);
			rutile_leggings = createLeggings(material);
			rutile_nugget = createNugget(material);
			rutile_powder = createPowder(material);
			rutile_sword = createSword(material);
		}

		if (Options.ENABLE_STAINLESSSTEEL) {
			material = Materials.stainlesssteel;
			createItemsFull(material);

			stainlesssteel_blend = createBlend(material);
			stainlesssteel_boots = createBoots(material);
			stainlesssteel_chestplate = createChestplate(material);
			stainlesssteel_helmet = createHelmet(material);
			stainlesssteel_ingot = createIngot(material);
			stainlesssteel_leggings = createLeggings(material);
			stainlesssteel_nugget = createNugget(material);
			stainlesssteel_powder = createPowder(material);
			stainlesssteel_sword = createSword(material);
		}

		if (Options.ENABLE_TANTALUM) {

			material = Materials.tantalum;
			createItemsFull(material);
			createItemsModSupport(material);

			tantalum_boots = createBoots(material);
			tantalum_chestplate = createChestplate(material);
			tantalum_helmet = createHelmet(material);
			tantalum_ingot = createIngot(material);
			tantalum_leggings = createLeggings(material);
			tantalum_nugget = createNugget(material);
			tantalum_powder = createPowder(material);
			tantalum_sword = createSword(material);
		}

		if (Options.ENABLE_TITANIUM) {
			material = Materials.titanium;
			createItemsFull(material);
			createItemsModSupport(material);

			titanium_blend = createBlend(material);
			titanium_boots = createBoots(material);
			titanium_chestplate = createChestplate(material);
			titanium_helmet = createHelmet(material);
			titanium_ingot = createIngot(material);
			titanium_leggings = createLeggings(material);
			titanium_nugget = createNugget(material);
			titanium_powder = createPowder(material);
			titanium_sword = createSword(material);
		}

		if (Options.ENABLE_TUNGSTEN) {
			material = Materials.tungsten;
			createItemsFull(material);
			createItemsModSupport(material);

			tungsten_boots = createBoots(material);
			tungsten_chestplate = createChestplate(material);
			tungsten_helmet = createHelmet(material);
			tungsten_ingot = createIngot(material);
			tungsten_leggings = createLeggings(material);
			tungsten_nugget = createNugget(material);
			tungsten_powder = createPowder(material);
			tungsten_sword = createSword(material);
		}

		if (Options.ENABLE_URANIUM) {
			material = Materials.uranium;
			createItemsFull(material);
			createItemsModMekanism(material);

			uranium_boots = createBoots(material);
			uranium_chestplate = createChestplate(material);
			uranium_helmet = createHelmet(material);
			uranium_ingot = createIngot(material);
			uranium_leggings = createLeggings(material);
			uranium_nugget = createNugget(material);
			uranium_powder = createPowder(material);
			uranium_sword = createSword(material);
		}

		if (Options.ENABLE_ZIRCONIUM) {
			material = Materials.zirconium;
			createItemsFull(material);
			createItemsModSupport(material);

			zirconium_boots = createBoots(material);
			zirconium_chestplate = createChestplate(material);
			zirconium_helmet = createHelmet(material);
			zirconium_ingot = createIngot(material);
			zirconium_leggings = createLeggings(material);
			zirconium_nugget = createNugget(material);
			zirconium_powder = createPowder(material);
			zirconium_sword = createSword(material);
		}

//		addToMetList() // May not be needed, check

		initDone = true;
	}
}

package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.util.Config.Options;

import cyano.basemetals.material.MetalMaterial;
import net.minecraft.block.*;

/**
 * This class initializes all blocks in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Blocks extends cyano.basemetals.init.Blocks {

	public static Block aluminum_block;
	public static Block aluminum_ore;

	public static Block aluminumbrass_block;

	public static Block cadmium_block;
	public static Block cadmium_ore;

	public static Block chromium_block;
	public static Block chromium_ore;

	public static Block galvanizedsteel_block;

	public static Block iridium_block;
	public static Block iridium_ore;

	public static Block magnesium_block;
	public static Block magnesium_ore;

	public static Block manganese_block;
	public static Block manganese_ore;

	public static Block nichrome_block;

	public static Block osmium_block;
	public static Block osmium_ore;

	public static Block plutonium_block;
	public static Block plutonium_ore;

	public static Block rutile_block;
	public static Block rutile_ore;

	public static Block stainlesssteel_block;

	public static Block tantalum_block;
	public static Block tantalum_ore;

	public static Block titanium_block;
	public static Block titanium_ore;

	public static Block tungsten_block;
	public static Block tungsten_ore;

	public static Block uranium_block;
	public static Block uranium_ore;

	public static Block zirconium_block;
	public static Block zirconium_ore;

	private static boolean initDone = false;

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		Materials.init();
		ItemGroups.init();

		MetalMaterial material;

		if (Options.ENABLE_ALUMINUM) {

			material = Materials.aluminum;
			createBlocksFull(material);

			aluminum_block = material.block;
			aluminum_ore = material.ore;
		}

		if (Options.ENABLE_ALUMINUMBRASS) {

			material = Materials.aluminumbrass;
			createBlocksFull(material);

			aluminumbrass_block = material.block;
		}

		if (Options.ENABLE_CADMIUM) {

			material = Materials.cadmium;
			createBlocksFull(material);

			cadmium_block = material.block;
			cadmium_ore = material.ore;
		}

		if (Options.ENABLE_CHROMIUM) {

			material = Materials.chromium;
			createBlocksFull(material);

			chromium_block = material.block;
			chromium_ore = material.ore;
		}

		if (Options.ENABLE_GALVANIZEDSTEEL) {

			material = Materials.galvanizedsteel;
			createBlocksFull(material);

			galvanizedsteel_block = material.block;
		}

		if (Options.ENABLE_IRIDIUM) {

			material = Materials.iridium;
			createBlocksFull(material);

			iridium_block = material.block;
			iridium_ore = material.ore;
		}

		if (Options.ENABLE_MAGNESIUM) {

			material = Materials.magnesium;
			createBlocksFull(material);

			magnesium_block = material.block;
			magnesium_ore = material.ore;
		}

		if (Options.ENABLE_MANGANESE) {

			material = Materials.manganese;
			createBlocksFull(material);

			manganese_block = material.block;
			manganese_ore = material.ore;
		}

		if (Options.ENABLE_NICHROME) {

			material = Materials.nichrome;
			createBlocksFull(material);

			nichrome_block = material.block;
		}

		if (Options.ENABLE_OSMIUM) {

			material = Materials.osmium;
			createBlocksFull(material);

			osmium_block = material.block;
			osmium_ore = material.ore;
		}

		if (Options.ENABLE_PLUTONIUM) {

			material = Materials.plutonium;
			createBlocksFull(material);

			plutonium_block = material.block;
			plutonium_ore = material.ore;
		}

		if (Options.ENABLE_RUTILE) {

			material = Materials.rutile;
			createBlocksFull(material);

			rutile_block = material.block;
			rutile_ore = material.ore;
		}

		if (Options.ENABLE_STAINLESSSTEEL) {

			material = Materials.stainlesssteel;
			createBlocksFull(material);

			stainlesssteel_block = material.block;
		}

		if (Options.ENABLE_TANTALUM) {

			material = Materials.tantalum;
			createBlocksFull(material);

			tantalum_block = material.block;
			tantalum_ore = material.ore;
		}

		if (Options.ENABLE_TITANIUM) {

			material = Materials.titanium;
			createBlocksFull(material);

			titanium_block = material.block;
			titanium_ore = material.ore;
		}

		if (Options.ENABLE_TUNGSTEN) {

			material = Materials.tungsten;
			createBlocksFull(material);

			tungsten_block = material.block;
			tungsten_ore = material.ore;
		}

		if (Options.ENABLE_URANIUM) {

			material = Materials.uranium;
			createBlocksFull(material);

			uranium_block = material.block;
			uranium_ore = material.ore;
		}

		if (Options.ENABLE_ZIRCONIUM) {

			material = Materials.zirconium;
			createBlocksFull(material);

			zirconium_block = material.block;
			zirconium_ore = material.ore;
		}

		initDone = true;
	}
}

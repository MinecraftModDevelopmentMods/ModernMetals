package com.mcmoddev.modernmetals.init;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import com.mcmoddev.lib.data.SharedStrings;
import com.mcmoddev.lib.material.MMDMaterialType.MaterialType;
import com.mcmoddev.lib.util.Config.Options;
import com.mcmoddev.modernmetals.data.MaterialNames;

/**
 * This class initializes all of the materials in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Materials extends com.mcmoddev.lib.init.Materials {
	private Materials() {
		throw new IllegalAccessError(SharedStrings.NOT_INSTANTIABLE);
	}

	/**
	 *
	 */
	public static void init() {
		final List<String> materials = Arrays.asList(MaterialNames.ALUMINUM, MaterialNames.BERYLLIUM,
				MaterialNames.BORON, MaterialNames.CHROMIUM, MaterialNames.MAGNESIUM, MaterialNames.MANGANESE,
				MaterialNames.OSMIUM, MaterialNames.RUTILE, MaterialNames.TANTALUM, MaterialNames.THORIUM,
				MaterialNames.TUNGSTEN, MaterialNames.ZIRCONIUM);

		final List<String> rareMaterials = Arrays.asList(MaterialNames.CADMIUM, MaterialNames.IRIDIUM,
				MaterialNames.PLUTONIUM, MaterialNames.URANIUM);

		final List<String> alloyMaterials = Arrays.asList(MaterialNames.ALUMINUM_BRASS, MaterialNames.GALVANIZED_STEEL,
				MaterialNames.NICHROME, MaterialNames.STAINLESS_STEEL);

		materials.stream().filter(Options::isMaterialEnabled).forEach(name -> createMaterial(name, MaterialType.METAL,
				getHardness(name), getStrength(name), getMagic(name), getColor(name)));

		alloyMaterials.stream().filter(Options::isMaterialEnabled).forEach(name -> createAlloyMaterial(name,
				MaterialType.METAL, getHardness(name), getStrength(name), getMagic(name), getColor(name)));

		rareMaterials.stream().filter(Options::isMaterialEnabled).forEach(name -> createRareMaterial(name,
				MaterialType.METAL, getHardness(name), getStrength(name), getMagic(name), getColor(name)));

		if (Options.isMaterialEnabled(MaterialNames.TITANIUM)) {
			final String name = MaterialNames.TITANIUM;
			createSpecialMaterial(name, MaterialType.METAL, getHardness(name), getStrength(name), getMagic(name),
					getColor(name));
		}
	}

	private static int getColor(@Nonnull final String name) {
		switch (name) {
			case MaterialNames.ALUMINUM:
				return 0xFFC5C8C1;
			case MaterialNames.ALUMINUM_BRASS:
				return 0xFFEBAA56;
			case MaterialNames.CADMIUM:
				return 0xFFC9D4DA;
			case MaterialNames.CHROMIUM:
				return 0xFFCDCDCF;
			case MaterialNames.GALVANIZED_STEEL:
				return 0xFF9BA6A2;
			case MaterialNames.IRIDIUM:
				return 0xFFF8EDCC;
			case MaterialNames.MAGNESIUM:
				return 0xFF7F7F77;
			case MaterialNames.MANGANESE:
				return 0xFFF5CFDA;
			case MaterialNames.NICHROME:
				return 0xFFDEA054;
			case MaterialNames.OSMIUM:
				return 0xFF7C8E99;
			case MaterialNames.PLUTONIUM:
				return 0xFFB333EA;
			case MaterialNames.RUTILE:
				return 0xFFBF928B;
			case MaterialNames.STAINLESS_STEEL:
				return 0xFFC5BFC1;
			case MaterialNames.TANTALUM:
				return 0xFFC4BEC2;
			case MaterialNames.TITANIUM:
				return 0xFF73787E;
			case MaterialNames.TUNGSTEN:
				return 0xFF969696;
			case MaterialNames.URANIUM:
				return 0xFFA7B345;
			case MaterialNames.ZIRCONIUM:
				return 0xFF929793;
			case MaterialNames.BERYLLIUM:
				return 0xFFD5D3D5;
			case MaterialNames.BORON:
				return 0xFF888888;
			case MaterialNames.THORIUM:
				return 0xFF4F5A5A;
			default:
				return 0xFF000000;
		}
	}

	private static double getMagic(@Nonnull final String name) {
		switch (name) {
			case MaterialNames.ALUMINUM:
			case MaterialNames.ALUMINUM_BRASS:
			case MaterialNames.CADMIUM:
			case MaterialNames.CHROMIUM:
			case MaterialNames.GALVANIZED_STEEL:
			case MaterialNames.IRIDIUM:
			case MaterialNames.MAGNESIUM:
			case MaterialNames.MANGANESE:
			case MaterialNames.NICHROME:
			case MaterialNames.OSMIUM:
			case MaterialNames.PLUTONIUM:
			case MaterialNames.RUTILE:
			case MaterialNames.STAINLESS_STEEL:
			case MaterialNames.TANTALUM:
			case MaterialNames.TITANIUM:
			case MaterialNames.TUNGSTEN:
			case MaterialNames.URANIUM:
			case MaterialNames.ZIRCONIUM:
				return 4.5d;
			case MaterialNames.BERYLLIUM:
			case MaterialNames.BORON:
			case MaterialNames.THORIUM:
				return 1.0d;
			default:
				return 1.0d;
		}
	}

	private static double getStrength(@Nonnull final String name) {
		switch (name) {
			case MaterialNames.TANTALUM:
				return 17.5d;
			case MaterialNames.TITANIUM:
			case MaterialNames.URANIUM:
				return 16.25d;
			case MaterialNames.STAINLESS_STEEL:
				return 16d;
			case MaterialNames.NICHROME:
				return 15.5d;
			case MaterialNames.GALVANIZED_STEEL:
				return 15.25d;
			case MaterialNames.TUNGSTEN:
				return 12.5d;
			case MaterialNames.BORON:
				return 8d;
			case MaterialNames.ALUMINUM_BRASS:
			case MaterialNames.PLUTONIUM:
				return 7.5d;
			case MaterialNames.ALUMINUM:
				return 3.75d;
			case MaterialNames.MAGNESIUM:
			case MaterialNames.ZIRCONIUM:
				return 3.5d;
			case MaterialNames.CHROMIUM:
			case MaterialNames.IRIDIUM:
				return 3d;
			case MaterialNames.MANGANESE:
			case MaterialNames.OSMIUM:
				return 2.75d;
			case MaterialNames.BERYLLIUM:
				return 2.5d;
			case MaterialNames.CADMIUM:
			case MaterialNames.THORIUM:
				return 1d;
			case MaterialNames.RUTILE:
				return 0.25d;
			default:
				return 1.0d;
		}
	}

	private static double getHardness(@Nonnull final String name) {
		switch (name) {
			case MaterialNames.BORON:
				return 9.5d;
			case MaterialNames.CHROMIUM:
				return 9d;
			case MaterialNames.TUNGSTEN:
				return 7.5d;
			case MaterialNames.OSMIUM:
				return 7d;
			case MaterialNames.IRIDIUM:
			case MaterialNames.TANTALUM:
			case MaterialNames.TITANIUM:
				return 6.5d;
			case MaterialNames.NICHROME:
			case MaterialNames.RUTILE:
			case MaterialNames.STAINLESS_STEEL:
			case MaterialNames.URANIUM:
				return 6d;
			case MaterialNames.BERYLLIUM:
			case MaterialNames.GALVANIZED_STEEL:
				return 5.5d;
			case MaterialNames.MANGANESE:
			case MaterialNames.ZIRCONIUM:
				return 5d;
			case MaterialNames.PLUTONIUM:
				return 4d;
			case MaterialNames.THORIUM:
				return 3d;
			case MaterialNames.ALUMINUM:
			case MaterialNames.MAGNESIUM:
				return 2.5d;
			case MaterialNames.CADMIUM:
				return 2d;
			case MaterialNames.ALUMINUM_BRASS:
				return 1.0d;
			default:
				return 1.0d;
		}
	}
}

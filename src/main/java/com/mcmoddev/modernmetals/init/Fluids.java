package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.util.Config.Options;

import net.minecraftforge.fluids.*;

/**
 * This class initializes all fluids in Modern Metals.
 *
 * @author DrCyano
 *
 */
public class Fluids extends cyano.basemetals.init.Fluids {

	public static Fluid fluidAluminum = null;
	public static BlockFluidBase fluidBlockAluminum = null;

	public static Fluid fluidAluminumBrass = null;
	public static BlockFluidBase fluidBlockAluminumBrass = null;

	public static Fluid fluidCadmium = null;
	public static BlockFluidBase fluidBlockCadmium = null;

	public static Fluid fluidChromium = null;
	public static BlockFluidBase fluidBlockChromium = null;

	public static Fluid fluidGalvanizedSteel = null;
	public static BlockFluidBase fluidBlockGalvanizedSteel = null;

	public static Fluid fluidIridium = null;
	public static BlockFluidBase fluidBlockIridium = null;

	public static Fluid fluidMagnesium = null;
	public static BlockFluidBase fluidBlockMagnesium = null;

	public static Fluid fluidManganese = null;
	public static BlockFluidBase fluidBlockManganese = null;

	public static Fluid fluidNichrome = null;
	public static BlockFluidBase fluidBlockNichrome = null;

	public static Fluid fluidOsmium = null;
	public static BlockFluidBase fluidBlockOsmium = null;

	public static Fluid fluidPlutonium = null;
	public static BlockFluidBase fluidBlockPlutonium = null;

	public static Fluid fluidRutile = null;
	public static BlockFluidBase fluidBlockRutile = null;

	public static Fluid fluidStainlessSteel = null;
	public static BlockFluidBase fluidBlockStainlessSteel = null;

	public static Fluid fluidTantalum = null;
	public static BlockFluidBase fluidBlockTantalum = null;

	public static Fluid fluidTitanium = null;
	public static BlockFluidBase fluidBlockTitanium = null;

	public static Fluid fluidTungsten = null;
	public static BlockFluidBase fluidBlockTungsten = null;

	public static Fluid fluidUranium = null;
	public static BlockFluidBase fluidBlockUranium = null;

	public static Fluid fluidZirconium = null;
	public static BlockFluidBase fluidBlockZirconium = null;

	private static boolean initDone = false;

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		String materialName;

		if (Options.ENABLE_ALUMINUM) {
			materialName = "aluminum";

			fluidAluminum = addFluid(materialName, 2000, 10000, 330, 10);
			fluidBlockAluminum = addFluidBlock(materialName);
		}
		if (Options.ENABLE_ALUMINUMBRASS) {
			materialName = "alubrass";

			fluidAluminumBrass = addFluid(materialName, 2000, 10000, 500, 10);
			fluidBlockAluminumBrass = addFluidBlock(materialName);
		}
		if (Options.ENABLE_CADMIUM) {
			materialName = "cadmium";

			fluidCadmium = addFluid(materialName, 2000, 10000, 300, 10);
			fluidBlockCadmium = addFluidBlock(materialName);
		}
		if (Options.ENABLE_CHROMIUM) {
			materialName = "chromium";

			fluidChromium = addFluid(materialName, 2000, 10000, 769, 10);
			fluidBlockChromium = addFluidBlock(materialName);
		}
		if (Options.ENABLE_GALVANIZEDSTEEL) {
			materialName = "galvanizedsteel";

			fluidGalvanizedSteel = addFluid(materialName, 2000, 10000,769, 10);
			fluidBlockGalvanizedSteel = addFluidBlock(materialName);
		}
		if (Options.ENABLE_IRIDIUM) {
			materialName = "iridium";

			fluidIridium = addFluid(materialName, 2000, 10000, 769, 10);
			fluidBlockIridium = addFluidBlock(materialName);
		}
		if (Options.ENABLE_MAGNESIUM) {
			materialName = "magnesium";

			fluidMagnesium = addFluid(materialName, 2000, 10000, 769, 10);
			fluidBlockMagnesium = addFluidBlock(materialName);
		}
		if (Options.ENABLE_MANGANESE) {
			materialName = "manganese";

			fluidManganese = addFluid(materialName, 2000, 10000, 769, 10);
			fluidBlockManganese = addFluidBlock(materialName);
		}
		if (Options.ENABLE_NICHROME) {
			materialName = "nichrome";

			fluidNichrome = addFluid(materialName, 2000, 10000, 769, 10);
			fluidBlockNichrome = addFluidBlock(materialName);
		}
		if (Options.ENABLE_OSMIUM) {
			materialName = "osmium";

			fluidOsmium = addFluid(materialName, 2000, 10000, 769, 10);
			fluidBlockOsmium = addFluidBlock(materialName);
		}
		if (Options.ENABLE_PLUTONIUM) {
			materialName = "plutonium";

			fluidPlutonium = addFluid(materialName, 2000, 10000, 769, 10);
			fluidBlockPlutonium = addFluidBlock(materialName);
		}
		if (Options.ENABLE_RUTILE) {
			materialName = "rutile";

			fluidRutile = addFluid(materialName, 2000, 10000, 769, 10);
			fluidBlockRutile = addFluidBlock(materialName);
		}
		if (Options.ENABLE_STAINLESSSTEEL) {
			materialName = "stainlesssteel";

			fluidStainlessSteel = addFluid(materialName, 2000, 10000, 769, 10);
			fluidBlockStainlessSteel = addFluidBlock(materialName);
		}
		if (Options.ENABLE_TANTALUM) {
			materialName = "tantalum";

			fluidTantalum = addFluid(materialName, 2000, 10000, 769, 10);
			fluidBlockTantalum = addFluidBlock(materialName);
		}
		if (Options.ENABLE_TITANIUM) {
			materialName = "titanium";

			fluidTitanium = addFluid(materialName, 2000, 10000, 769, 10);
			fluidBlockTitanium = addFluidBlock(materialName);
		}
		if (Options.ENABLE_TUNGSTEN) {
			materialName = "tungsten";

			fluidTungsten = addFluid(materialName, 2000, 10000, 769, 10);
			fluidBlockTungsten = addFluidBlock(materialName);
		}
		if (Options.ENABLE_URANIUM) {
			materialName = "uranium";

			fluidUranium = addFluid(materialName, 2000, 10000, 769, 10);
			fluidBlockUranium = addFluidBlock(materialName);
		}
		if (Options.ENABLE_ZIRCONIUM) {
			materialName = "zirconium";

			fluidZirconium = addFluid(materialName, 2000, 10000, 769, 10);
			fluidBlockZirconium = addFluidBlock(materialName);
		}

		initDone = true;
	}
}

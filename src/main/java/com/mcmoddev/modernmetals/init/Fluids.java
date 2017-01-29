package com.mcmoddev.modernmetals.init;

import com.mcmoddev.basemetals.material.MetalMaterial;
import com.mcmoddev.modernmetals.util.Config.Options;

import net.minecraftforge.fluids.*;

/**
 * This class initializes all fluids in Modern Metals.
 *
 * @author DrCyano
 *
 */
public class Fluids extends com.mcmoddev.basemetals.init.Fluids {

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

		MetalMaterial material;

		if (Options.ENABLE_ALUMINUM) {
			material = Materials.aluminum;

			fluidAluminum = addFluid(material, 2000, 10000, 330, 10);
			fluidBlockAluminum = addFluidBlock(material);
		}
		if (Options.ENABLE_ALUMINUMBRASS) {
			material = Materials.aluminumbrass;

			fluidAluminumBrass = addFluid(material, 2000, 10000, 500, 10);
			fluidBlockAluminumBrass = addFluidBlock(material);
		}
		if (Options.ENABLE_CADMIUM) {
			material = Materials.cadmium;

			fluidCadmium = addFluid(material, 2000, 10000, 300, 10);
			fluidBlockCadmium = addFluidBlock(material);
		}
		if (Options.ENABLE_CHROMIUM) {
			material = Materials.chromium;

			fluidChromium = addFluid(material, 2000, 10000, 769, 10);
			fluidBlockChromium = addFluidBlock(material);
		}
		if (Options.ENABLE_GALVANIZEDSTEEL) {
			material = Materials.galvanizedsteel;

			fluidGalvanizedSteel = addFluid(material, 2000, 10000,769, 10);
			fluidBlockGalvanizedSteel = addFluidBlock(material);
		}
		if (Options.ENABLE_IRIDIUM) {
			material = Materials.iridium;

			fluidIridium = addFluid(material, 2000, 10000, 769, 10);
			fluidBlockIridium = addFluidBlock(material);
		}
		if (Options.ENABLE_MAGNESIUM) {
			material = Materials.magnesium;

			fluidMagnesium = addFluid(material, 2000, 10000, 769, 10);
			fluidBlockMagnesium = addFluidBlock(material);
		}
		if (Options.ENABLE_MANGANESE) {
			material = Materials.manganese;

			fluidManganese = addFluid(material, 2000, 10000, 769, 10);
			fluidBlockManganese = addFluidBlock(material);
		}
		if (Options.ENABLE_NICHROME) {
			material = Materials.nichrome;

			fluidNichrome = addFluid(material, 2000, 10000, 769, 10);
			fluidBlockNichrome = addFluidBlock(material);
		}
		if (Options.ENABLE_OSMIUM) {
			material = Materials.osmium;

			fluidOsmium = addFluid(material, 2000, 10000, 769, 10);
			fluidBlockOsmium = addFluidBlock(material);
		}
		if (Options.ENABLE_PLUTONIUM) {
			material = Materials.plutonium;

			fluidPlutonium = addFluid(material, 2000, 10000, 769, 10);
			fluidBlockPlutonium = addFluidBlock(material);
		}
		if (Options.ENABLE_RUTILE) {
			material = Materials.rutile;

			fluidRutile = addFluid(material, 2000, 10000, 769, 10);
			fluidBlockRutile = addFluidBlock(material);
		}
		if (Options.ENABLE_STAINLESSSTEEL) {
			material = Materials.stainlesssteel;

			fluidStainlessSteel = addFluid(material, 2000, 10000, 769, 10);
			fluidBlockStainlessSteel = addFluidBlock(material);
		}
		if (Options.ENABLE_TANTALUM) {
			material = Materials.tantalum;

			fluidTantalum = addFluid(material, 2000, 10000, 769, 10);
			fluidBlockTantalum = addFluidBlock(material);
		}
		if (Options.ENABLE_TITANIUM) {
			material = Materials.titanium;

			fluidTitanium = addFluid(material, 2000, 10000, 769, 10);
			fluidBlockTitanium = addFluidBlock(material);
		}
		if (Options.ENABLE_TUNGSTEN) {
			material = Materials.tungsten;

			fluidTungsten = addFluid(material, 2000, 10000, 769, 10);
			fluidBlockTungsten = addFluidBlock(material);
		}
		if (Options.ENABLE_URANIUM) {
			material = Materials.uranium;

			fluidUranium = addFluid(material, 2000, 10000, 769, 10);
			fluidBlockUranium = addFluidBlock(material);
		}
		if (Options.ENABLE_ZIRCONIUM) {
			material = Materials.zirconium;

			fluidZirconium = addFluid(material, 2000, 10000, 769, 10);
			fluidBlockZirconium = addFluidBlock(material);
		}

		initDone = true;
	}
}

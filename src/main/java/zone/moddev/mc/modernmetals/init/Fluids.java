package zone.moddev.mc.modernmetals.init;

import zone.moddev.mc.modernmetals.util.Config.Options;

/**
 * This class initializes all fluids in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Fluids extends com.mcmoddev.lib.init.Fluids {

	private static boolean initDone = false;

	private Fluids() {
		throw new IllegalAccessError("Not a instantiable class");
	}

	/** Reuse another mod's registered fluid instead of retaining a rejected duplicate. */
	protected static net.minecraftforge.fluids.Fluid addFluid(final com.mcmoddev.lib.material.MMDMaterial material,
			final int density, final int viscosity, final int temperature, final int luminosity) {
		final net.minecraftforge.fluids.Fluid registered=net.minecraftforge.fluids.FluidRegistry.getFluid(material.getName());
		if(registered!=null) {
			material.setFluid(registered);
			net.minecraftforge.fluids.FluidRegistry.addBucketForFluid(registered);
			return registered;
		}
		return com.mcmoddev.lib.init.Fluids.addFluid(material,density,viscosity,temperature,luminosity);
	}

	/** Preserve the legacy Modern Metals query API for borrowed fluids too. */
	public static net.minecraftforge.fluids.Fluid getFluidByName(final String name) {
		for(final com.mcmoddev.lib.material.MMDMaterial material:Materials.getMaterialsByMod("modernmetals"))
			if(material.getName().equals(name)) return material.getFluid();
		return com.mcmoddev.lib.init.Fluids.getFluidByName(name);
	}
	public static String getNameOfFluid(final net.minecraftforge.fluids.Fluid fluid) {
		if(fluid==null) return null;
		for(final com.mcmoddev.lib.material.MMDMaterial material:Materials.getMaterialsByMod("modernmetals"))
			if(material.getFluid()==fluid) return material.getName();
		return com.mcmoddev.lib.init.Fluids.getNameOfFluid(fluid);
	}
	public static java.util.Map<String,net.minecraftforge.fluids.Fluid> getFluidRegistry() {
		final java.util.Map<String,net.minecraftforge.fluids.Fluid> view=new java.util.LinkedHashMap<>(com.mcmoddev.lib.init.Fluids.getFluidRegistry());
		for(final com.mcmoddev.lib.material.MMDMaterial material:Materials.getMaterialsByMod("modernmetals"))
			if(material.getFluid()!=null) view.put(material.getName(),material.getFluid());
		return java.util.Collections.unmodifiableMap(view);
	}

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		if (Options.enableAluminum) {
			addFluid(Materials.aluminum, 2000, 10000, 330, 10);
			addFluidBlock(Materials.aluminum);
		}

		if (Options.enableAluminumBrass) {
			addFluid(Materials.aluminumBrass, 2000, 10000, 500, 10);
			addFluidBlock(Materials.aluminumBrass);
		}

		if (Options.enableCadmium) {
			addFluid(Materials.cadmium, 2000, 10000, 300, 10);
			addFluidBlock(Materials.cadmium);
		}

		if (Options.enableChromium) {
			addFluid(Materials.chromium, 2000, 10000, 769, 10);
			addFluidBlock(Materials.chromium);
		}

		if (Options.enableGalvanizedSteel) {
			addFluid(Materials.galvanizedSteel, 2000, 10000, 769, 10);
			addFluidBlock(Materials.galvanizedSteel);
		}

		if (Options.enableIridium) {
			addFluid(Materials.iridium, 2000, 10000, 769, 10);
			addFluidBlock(Materials.iridium);
		}

		if (Options.enableMagnesium) {
			addFluid(Materials.magnesium, 2000, 10000, 769, 10);
			addFluidBlock(Materials.magnesium);
		}

		if (Options.enableManganese) {
			addFluid(Materials.manganese, 2000, 10000, 769, 10);
			addFluidBlock(Materials.manganese);
		}

		if (Options.enableNichrome) {
			addFluid(Materials.nichrome, 2000, 10000, 769, 10);
			addFluidBlock(Materials.nichrome);
		}

		if (Options.enableOsmium) {
			addFluid(Materials.osmium, 2000, 10000, 769, 10);
			addFluidBlock(Materials.osmium);
		}

		if (Options.enablePlutonium) {
			addFluid(Materials.plutonium, 2000, 10000, 769, 10);
			addFluidBlock(Materials.plutonium);
		}

		if (Options.enableRutile) {
			addFluid(Materials.rutile, 2000, 10000, 769, 10);
			addFluidBlock(Materials.rutile);
		}

		if (Options.enableStainlessSteel) {
			addFluid(Materials.stainlessSteel, 2000, 10000, 769, 10);
			addFluidBlock(Materials.stainlessSteel);
		}

		if (Options.enableTantalum) {
			addFluid(Materials.tantalum, 2000, 10000, 769, 10);
			addFluidBlock(Materials.tantalum);
		}

		if (Options.enableTitanium) {
			addFluid(Materials.titanium, 2000, 10000, 769, 10);
			addFluidBlock(Materials.titanium);
		}

		if (Options.enableTungsten) {
			addFluid(Materials.tungsten, 2000, 10000, 769, 10);
			addFluidBlock(Materials.tungsten);
		}

		if (Options.enableUranium) {
			addFluid(Materials.uranium, 2000, 10000, 769, 10);
			addFluidBlock(Materials.uranium);
		}

		if (Options.enableZirconium) {
			addFluid(Materials.zirconium, 2000, 10000, 769, 10);
			addFluidBlock(Materials.zirconium);
		}

		if (Options.enableBeryllium) {
			addFluid(Materials.beryllium, 2000, 10000, 400, 10);
			addFluidBlock(Materials.beryllium);
		}

		if (Options.enableBoron) {
			addFluid(Materials.boron, 2000, 10000, 400, 10);
			addFluidBlock(Materials.boron);
		}

		if (Options.enableThorium) {
			addFluid(Materials.thorium, 2000, 10000, 514, 10);
			addFluidBlock(Materials.thorium);
		}

		initDone = true;
	}
}

package modernmetals.init;

import cyano.basemetals.material.MetalMaterial;

/**
 * This class initializes all of the metal materials in Modern Metals. It also
 * contains utility methods for looking up materials by name and finding the 
 * tool and armor material equivalents for a given metal.
 * @author DrCyano
 *
 */
public abstract class Materials extends cyano.basemetals.init.Materials {

	public static MetalMaterial aluminum;
	public static MetalMaterial aluminumbrass;
	public static MetalMaterial cadmium;
	public static MetalMaterial chromium;
	public static MetalMaterial galvanizedsteel;
	public static MetalMaterial iridium;
	public static MetalMaterial magnesium;
	public static MetalMaterial manganese;
	public static MetalMaterial nichrome;
	public static MetalMaterial osmium;
	public static MetalMaterial plutonium;
	public static MetalMaterial rutile;
	public static MetalMaterial stainlesssteel;
	public static MetalMaterial tantalum;
	public static MetalMaterial titanium;
	public static MetalMaterial tungsten;
	public static MetalMaterial uranium;
	public static MetalMaterial zirconium;

	private static boolean initDone = false;

	/**
	 * 
	 */
	public static void init() {
		if(initDone)
			return;

		// Mod Metals
		aluminum = addMaterial("aluminum", 2.5, 3.75, 4.5, 0.3);
		aluminumbrass = addMaterial("aluminumbrass", 1, 7.5, 4.5, 0.3);
		cadmium = addMaterial("cadmium", 2, 1, 4.5, 0.3);
		chromium = addMaterial("chromium", 9, 3, 4.5, 0.3);
		galvanizedsteel = addMaterial("galvanizedsteel", 5.5, 15.25, 4.5, 0.3);
		iridium = addMaterial("iridium", 6.5, 3, 4.5, 0.3);
		magnesium = addMaterial("magnesium", 2.5, 3.5, 4.5, 0.3);
		manganese = addMaterial("manganese", 5, 2.75, 4.5, 0.3);
		nichrome= addMaterial("nichrome", 6, 15.5, 4.5, 0.3);
		osmium = addMaterial("osmium", 7, 2.75, 4.5, 0.3);
		plutonium = addMaterial("plutonium", 4, 7.5, 4.5, 0.3);
		rutile = addMaterial("rutile", 6, 0.25, 4.5, 0.3);
		stainlesssteel = addMaterial("stainlesssteel", 6, 16, 4.5, 0.3);
		tantalum = addMaterial("tantalum", 6.5, 17.5, 4.5, 0.3);
		titanium = addMaterial("titanium", 6.5, 16.25, 4.5, 0.3);
		tungsten = addMaterial("tungsten", 7.5, 12.5, 4.5, 0.3);
		uranium = addMaterial("uranium", 6, 16.25, 4.5, 0.3);
		zirconium = addMaterial("zirconium", 5, 3.5, 4.5, 0.3);

		initDone = true;
	}

	private static MetalMaterial addMaterial(String name, double hardness, double strength, double magic, double rarity) {
		MetalMaterial m = new MetalMaterial(name, (float)hardness, (float)strength, (float)magic, (int)(Math.max(50 * rarity, 1)));
		registerMaterial(name, m);
		return m;
	}

	@SuppressWarnings("unused")
	private static MetalMaterial addRareMaterial(String name, double hardness, double strength, double magic, double rarity) {
		MetalMaterial m = new MetalMaterial(name, (float)hardness, (float)strength, (float)magic, (int)(Math.max(50 * rarity, 1)));
		registerMaterial(name, m);
		return m;
	}
}

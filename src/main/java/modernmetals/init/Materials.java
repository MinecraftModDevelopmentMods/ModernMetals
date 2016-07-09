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
//	public static MetalMaterial antimony;
//	public static MetalMaterial bismuth;
	public static MetalMaterial cadmium;
	public static MetalMaterial chromium;
//	public static MetalMaterial cupronickel;
	public static MetalMaterial galvanizedsteel;
	public static MetalMaterial iridium;
//	public static MetalMaterial lithium;
	public static MetalMaterial magnesium;
	public static MetalMaterial manganese;
	public static MetalMaterial nichrome;
	public static MetalMaterial osmium;
//	public static MetalMaterial niter;
//	public static MetalMaterial pewter;
//	public static MetalMaterial phospherous;
//	public static MetalMaterial platinum;
	public static MetalMaterial plutonium;
	public static MetalMaterial rutile;
	public static MetalMaterial silicon;
	public static MetalMaterial stainlesssteel;
//	public static MetalMaterial sulfur;
	public static MetalMaterial tantalum;
	public static MetalMaterial titanium;
	public static MetalMaterial tungsten;
	public static MetalMaterial uranium;
	public static MetalMaterial zirconium;

	private static boolean initDone = false;
	public static void init() {
		if(initDone) return;

		// Mod Metals
		aluminum = addMaterial("aluminum", 3, 6, 4.5);
		aluminumbrass = addMaterial("aluminumbrass", 2, 8, 4.5);
//		antimony = addMaterial("antimony", 8, 8, 4.5);
//		bismuth = addMaterial("bismuth", 8, 8, 4.5);
		cadmium = addMaterial("cadmium", 3, 8, 4.5);
		chromium = addMaterial("chromium", 4, 8, 4.5);
		galvanizedsteel = addMaterial("galvanizedsteel", 5, 8, 4.5);
		iridium = addMaterial("iridium", 1, 7, 4.5);
//		lithium = addMaterial("lithium", 8, 8, 4.5);
		magnesium = addMaterial("magnesium", 2, 7, 4.5);
		manganese = addMaterial("manganese", 3, 7, 4.5);
		nichrome= addMaterial("nichrome", 4, 7, 4.5);
//		niter = addMaterial("niter", 8, 8, 4.5);
		osmium = addMaterial("osmium", 5, 7, 4.5);
//		pewter = addMaterial("pewter", 8, 8, 4.5);
//		phospherous = addMaterial("phospherous", 8, 8, 4.5);
		plutonium = addMaterial("plutonium", 8, 8, 4.5);
		rutile = addMaterial("rutile", 8, 8, 4.5);
//		silicon = addMaterial("silicon", 8, 8, 4.5);
		stainlesssteel = addMaterial("stainlesssteel", 8, 8, 4.5);
//		sulfur = addMaterial("sulfur", 8, 8, 4.5);
		tantalum = addMaterial("tantalum", 8, 8, 4.5);
		titanium = addMaterial("titanium", 8, 8, 4.5);
		tungsten = addMaterial("tungsten", 8, 8, 4.5);
		uranium = addMaterial("uranium", 8, 8, 4.5);
		zirconium = addMaterial("zirconium", 8, 8, 4.5);

		initDone = true;
	}

	private static MetalMaterial addMaterial(String name, double hardness, double strength, double magic) {
		MetalMaterial m = new MetalMaterial(name, (float)hardness, (float)strength, (float)magic);
		registerMaterial(name, m);
		return m;
	}

	private static MetalMaterial addRareMaterial(String name, double hardness, double strength, double magic) {
		MetalMaterial m = new MetalMaterial(name, (float)hardness, (float)strength, (float)magic);
		registerMaterial(name, m);
		return m;
	}
}

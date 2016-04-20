package modernmetals.init;

import cyano.basemetals.material.MetalMaterial;

/**
 * This class initializes all of the metal materials in Base Metals. It also 
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
	public static MetalMaterial cupronickel;
	public static MetalMaterial iridium;
	public static MetalMaterial lithium;
	public static MetalMaterial magnesium;
	public static MetalMaterial manganese;
	public static MetalMaterial osmium;
	public static MetalMaterial niter;
	public static MetalMaterial phospherous;
	public static MetalMaterial platinum;
	public static MetalMaterial plutonium;
	public static MetalMaterial rutile;
	public static MetalMaterial silicon;
	public static MetalMaterial sulfur;
	public static MetalMaterial tantalum;
	public static MetalMaterial titanium;
	public static MetalMaterial tungsten;
	public static MetalMaterial uranium;
	public static MetalMaterial zirconium;

	private static boolean initDone = false;
	public static void init(){
		if(initDone)return;
		
		// TODO: rebalance metals (make it so none of them are completely worthless)
		// mod metals
		aluminum = addMaterial("aluminum",8,8,4.5,0.3);
		aluminumbrass = addMaterial("aluminumbrass",8,8,4.5,0.3);
		cadmium = addMaterial("cadmium",8,8,4.5,0.3);
		chromium = addMaterial("chromium",8,8,4.5,0.3);
//		cupronickel = addMaterial("cupronickel",6,6,6,0.2);
		iridium = addMaterial("iridium",8,8,4.5,0.3);
		lithium = addMaterial("lithium",8,8,4.5,0.3);
		magnesium = addMaterial("magnesium",8,8,4.5,0.3);
		manganese = addMaterial("manganese",8,8,4.5,0.3);
		niter = addMaterial("niter",8,8,4.5,0.3);
		osmium = addMaterial("osmium",8,8,4.5,0.3);
		phospherous = addMaterial("phospherous",8,8,4.5,0.3);
//		platinum = addMaterial("platinum",3,5,12,0.01);
		plutonium = addMaterial("plutonium",8,8,4.5,0.3);
		rutile = addMaterial("rutile",8,8,4.5,0.3);
		silicon = addMaterial("silicon",8,8,4.5,0.3);
		sulfur = addMaterial("sulfur",8,8,4.5,0.3);
		tantalum = addMaterial("tantalum",8,8,4.5,0.3);
		titanium = addMaterial("titanium",8,8,4.5,0.3);
		tungsten = addMaterial("tungsten",8,8,4.5,0.3);
		uranium = addMaterial("uranium",8,8,4.5,0.3);
		zirconium = addMaterial("zirconium",8,8,4.5,0.3);

		initDone = true;
	}

	private static MetalMaterial addMaterial(String name, double hardness, double strength, double magic, double rarity){
		MetalMaterial m = new MetalMaterial(name,(float)hardness,(float)strength,(float)magic,(int)(Math.max(50*rarity,1)));
		registerMaterial(name, m);
		return m;
	}

}

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
	public static MetalMaterial cupronickel;
	public static MetalMaterial magnesium;
	public static MetalMaterial osmium;
	public static MetalMaterial platinum;
	public static MetalMaterial rutile;
	public static MetalMaterial titanium;
	public static MetalMaterial tungsten;
	public static MetalMaterial uranium;

	private static boolean initDone = false;
	public static void init(){
		if(initDone)return;
		
		// TODO: rebalance metals (make it so none of them are completely worthless)
		// mod metals
		aluminum = addMaterial("aluminum",8,8,4.5,0.3);
		aluminumbrass = addMaterial("aluminumbrass",8,8,4.5,0.3);
		cupronickel = addMaterial("cupronickel",8,8,4.5,0.3);
		magnesium = addMaterial("magnesium",8,8,4.5,0.3);
		osmium = addMaterial("osmium",8,8,4.5,0.3);
		platinum = addMaterial("platinum",8,8,4.5,0.3);
		rutile = addMaterial("rutile",8,8,4.5,0.3);
		titanium = addMaterial("titanium",8,8,4.5,0.3);
		tungsten = addMaterial("tungsten",8,8,4.5,0.3);
		uranium = addMaterial("uranium",8,8,4.5,0.3);

		initDone = true;
	}

	private static MetalMaterial addMaterial(String name, double hardness, double strength, double magic, double rarity){
		MetalMaterial m = new MetalMaterial(name,(float)hardness,(float)strength,(float)magic,(int)(Math.max(50*rarity,1)));
		registerMaterial(name, m);
		return m;
	}

}

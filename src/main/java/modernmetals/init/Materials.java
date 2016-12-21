package modernmetals.init;

import cyano.basemetals.material.MetalMaterial;
import modernmetals.util.Config.Options;

/**
 * This class initializes all of the materials in Modern Metals.
 *
 * @author DrCyano
 *
 */
public class Materials extends cyano.basemetals.init.Materials {

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
		if (initDone) {
			return;
		}

		// Mod Materials
		if (Options.ENABLE_ALUMINUM) {
			aluminum = createMaterial("aluminum", 2.5, 3.75, 4.5, 0xFFC5C8C1);
		}
		if (Options.ENABLE_ALUMINUMBRASS) {
			aluminumbrass = createMaterial("aluminumbrass", 1, 7.5, 4.5, 0xFFEBAA56);
		}
		if (Options.ENABLE_CADMIUM) {
			cadmium = createMaterial("cadmium", 2, 1, 4.5, 0xFFC9D4DA);
		}
		if (Options.ENABLE_CHROMIUM) {
			chromium = createMaterial("chromium", 9, 3, 4.5, 0xFFCDCDCF);
		}
		if (Options.ENABLE_GALVANIZEDSTEEL) {
			galvanizedsteel = createMaterial("galvanizedsteel", 5.5, 15.25, 4.5, 0xFF9BA6A2);
		}
		if (Options.ENABLE_IRIDIUM) {
			iridium = createMaterial("iridium", 6.5, 3, 4.5, 0xFFF8EDCC);
		}
		if (Options.ENABLE_MAGNESIUM) {
			magnesium = createMaterial("magnesium", 2.5, 3.5, 4.5, 0xFF7F7F77);
		}
		if (Options.ENABLE_MANGANESE) {
			manganese = createMaterial("manganese", 5, 2.75, 4.5, 0xFFF5CFDA);
		}
		if (Options.ENABLE_NICHROME) {
			nichrome= createMaterial("nichrome", 6, 15.5, 4.5, 0xFFDEA054);
		}
		if (Options.ENABLE_OSMIUM) {
			osmium = createMaterial("osmium", 7, 2.75, 4.5, 0xFF7C8E99);
		}
		if (Options.ENABLE_PLUTONIUM) {
			plutonium = createMaterial("plutonium", 4, 7.5, 4.5, 0xFFB333EA);
		}
		if (Options.ENABLE_RUTILE) {
			rutile = createMaterial("rutile", 6, 0.25, 4.5, 0xFFBF928B);
		}
		if (Options.ENABLE_STAINLESSSTEEL) {
			stainlesssteel = createMaterial("stainlesssteel", 6, 16, 4.5, 0xFFC5BFC1);
		}
		if (Options.ENABLE_TANTALUM) {
			tantalum = createMaterial("tantalum", 6.5, 17.5, 4.5, 0xFFC4BEC2);
		}
		if (Options.ENABLE_TITANIUM) {
			titanium = createMaterial("titanium", 6.5, 16.25, 4.5, 0xFF73787E);
		}
		if (Options.ENABLE_TUNGSTEN) {
			tungsten = createMaterial("tungsten", 7.5, 12.5, 4.5, 0xFF969696);
		}
		if (Options.ENABLE_URANIUM) {
			uranium = createMaterial("uranium", 6, 16.25, 4.5, 0xFFA7B345);
		}
		if (Options.ENABLE_ZIRCONIUM) {
			zirconium = createMaterial("zirconium", 5, 3.5, 4.5, 0xFF929793);
		}

		initDone = true;
	}
}

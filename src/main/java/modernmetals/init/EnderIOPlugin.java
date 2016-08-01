package modernmetals.init;

import modernmetals.ModernMetals;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 * 
 * @author Jasmine Iwanek
 *
 */
public class EnderIOPlugin {

	private static boolean initDone = false;

	private static final String ALLOY_SMELTER_MSG =
			"<recipeGroup name=\"" + ModernMetals.MODID + "\" >" +
			 "<recipe name=\"%s\" energyCost=\"%d\" >" +
			  "<input>" +
			   "<itemStack modID=\"" + ModernMetals.MODID + "\" itemName=\"%s\" />" +
			  "</input>" +
			  "<output>" +
			   "<itemStack modID=\"" + ModernMetals.MODID + "\" itemName=\"%s\" />" +
			  "</output>" +
			 "</recipe>" +
			"</recipeGroup>";
			
	private static final String SAG_MILL_MSG =
			"<recipeGroup name=\"" + ModernMetals.MODID + "\">" +
			 "<recipe name=\"%s\" energyCost=\"%d\">" +
			  "<input>" +
			   "<itemStack modID=\"" + ModernMetals.MODID + "\" itemName=\"%s\" itemMeta=\"%d\" />" +
			  "</input>" +
			  "<output>" +
			   "<itemStack modID=\"" + ModernMetals.MODID + "\" itemName=\"%s\" itemMeta=\"%d\" number=\"2\" />" +
			   "<itemStack modID=\"" + ModernMetals.MODID + "\" itemName=\"%s\" itemMeta=\"%d\" number=\"1\" chance=\"0.1\" />" +
			   "<itemStack modID=\"minecraft\" itemName=\"%s\" chance=\"0.15\"/>" +
			  "</output>" +
			 "</recipe>" + 
			"</recipeGroup>";

	/**
	 * 
	 */
	public static void init() {
		if(initDone)
			return;

		// TODO: Verify blocks, Slabs, Ingots, Oreblocks
		if(Loader.isModLoaded("EnderIO")) {
			addSagMillRecipe("aluminum", null, 3600);
			addSagMillRecipe("aluminumbrass", null, 3600);
			addSagMillRecipe("cadmium", null, 3600);
			addSagMillRecipe("chromium", null, 3600);
			addSagMillRecipe("galvanizedsteel", null, 3600);
			addSagMillRecipe("iridium", null, 3600);
			addSagMillRecipe("magnesium", null, 3600);
			addSagMillRecipe("manganese", null, 3600);
			addSagMillRecipe("nichrome", null, 3600);
			addSagMillRecipe("osmium", null, 3600);
			addSagMillRecipe("plutonium", null, 3600);
			addSagMillRecipe("rutile", null, 3600);
			addSagMillRecipe("stainlesssteel", null, 3600);
			addSagMillRecipe("tantalum", null, 3600);
			addSagMillRecipe("titanium", null, 3600);
			addSagMillRecipe("tungsten", null, 3600);
			addSagMillRecipe("uranium", null, 3600);
			addSagMillRecipe("zirconium", null, 3600);
		}

		initDone = true;
	}

	// TODO: Use Oredicts for inputs
	public static void addAlloySmelterRecipe(String metal, String outputSecondary, int energy) {
		String str = String.format(ALLOY_SMELTER_MSG, metal, energy, metal + "_ore", metal + "_ingot");
		FMLInterModComms.sendMessage("EnderIO", "recipe:alloysmelter", str);
	}

	// TODO: Use Oredicts for inputs
	public static void addSagMillRecipe(String metal, String outputSecondary, int energy) {

		String str = String.format(SAG_MILL_MSG, metal, energy, metal + "_ore", 0, metal + "_powder", 0, outputSecondary, 0, "cobblestone");
		FMLInterModComms.sendMessage("EnderIO", "recipe:sagmill", str);
	}
}

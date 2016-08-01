package modernmetals.init;

import java.util.List;

import com.google.common.collect.Lists;

import modernmetals.utils.StringUtilities;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

/**
 * 
 * @author Jasmine Iwanek
 *
 */
public class TinkersConstructPlugin {

	private static boolean initDone = false;

	private static List<MaterialIntegration> integrateList = Lists.newArrayList(); // List of materials needed to be integrated
	 
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	public static void init() {
		if(initDone)
			return;

		if(Loader.isModLoaded("tconstruct")) {
			double d = 0; //durabilityFactorGeneral;
			System.out.println("DURABILITY FACTOR" + d);
			float s = 0;// (float) speedFactorGeneral;
			System.out.println("SPEED FACTOR" + s);
			float a = 0; //(float) attackFactorGeneral;
			System.out.println("ATTACK FACTOR" + a);

			final Material aluminum = new Material("aluminum", 0xFFC5C8C1);
			final Material aluminumbrass = new Material("aluminumbrass", 0xFFEBAA56);
			final Material cadmium = new Material("cadmium", 0xFFC9D4DA);
			final Material chromium = new Material("chromium", 0xFFCDCDCF);
			final Material galvanizedsteel = new Material("galvanizedsteel", 0xFF9BA6A2);
			final Material iridium = new Material("iridium", 0xFFF8EDCC);
			final Material magnesium = new Material("magnesium", 0xFF7F7F77);
			final Material manganese = new Material("manganese", 0xFFF5CFDA);
			final Material nichrome = new Material("nichrome", 0xFFDEA054);
			final Material osmium = new Material("osmium", 0xFF7C8E99);
			final Material plutonium = new Material("plutonium", 0xFFB333EA);
			final Material rutile = new Material("rutile", 0xFFBF928B);
			final Material stainlesssteel = new Material("stainlesssteel", 0xFFC5BFC1);
			final Material tantalum = new Material("tantalum", 0xFFC4BEC2);
			final Material titanium = new Material("titanium", 0xFF73787E);
			final Material tungsten = new Material("tungsten", 0xFF969696);
			final Material uranium = new Material("uranium", 0xFFA7B345);
			final Material zirconium = new Material("zirconium", 0xFF929793);

//			registerFluid(Fluids.fluidAluminum); // TCon already has this
//			registerTinkerMaterial(aluminum, Fluids.fluidAluminum, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
//			registerFluid(Fluids.fluidAluminumBrass); // TCon already has this
//			registerTinkerMaterial(aluminumbrass, Fluids.fluidAluminumBrass, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			registerFluid(Fluids.fluidCadmium, true);
			registerTinkerMaterial(cadmium, Fluids.fluidCadmium, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			registerFluid(Fluids.fluidChromium, true);
			registerTinkerMaterial(chromium, Fluids.fluidChromium, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			registerFluid(Fluids.fluidGalvanizedSteel, true);
			registerTinkerMaterial(galvanizedsteel, Fluids.fluidGalvanizedSteel, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			registerFluid(Fluids.fluidIridium, true);
			registerTinkerMaterial(iridium, Fluids.fluidIridium, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			registerFluid(Fluids.fluidMagnesium, true);
			registerTinkerMaterial(magnesium, Fluids.fluidMagnesium, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			registerFluid(Fluids.fluidManganese, true);
			registerTinkerMaterial(manganese, Fluids.fluidManganese, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			registerFluid(Fluids.fluidNichrome, true);
			registerTinkerMaterial(nichrome, Fluids.fluidNichrome, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			registerFluid(Fluids.fluidOsmium, true);
			registerTinkerMaterial(osmium, Fluids.fluidOsmium, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			registerFluid(Fluids.fluidPlutonium, true);
			registerTinkerMaterial(plutonium, Fluids.fluidPlutonium, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			registerFluid(Fluids.fluidRutile, true);
			registerTinkerMaterial(rutile, Fluids.fluidRutile, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			registerFluid(Fluids.fluidStainlessSteel, true);
			registerTinkerMaterial(stainlesssteel, Fluids.fluidStainlessSteel, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			registerFluid(Fluids.fluidTantalum, true);
			registerTinkerMaterial(tantalum, Fluids.fluidTantalum, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			registerFluid(Fluids.fluidTitanium, true);
			registerTinkerMaterial(titanium, Fluids.fluidTitanium, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			registerFluid(Fluids.fluidTungsten, true);
			registerTinkerMaterial(tungsten, Fluids.fluidTungsten, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			registerFluid(Fluids.fluidUranium, true);
			registerTinkerMaterial(uranium, Fluids.fluidUranium, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			registerFluid(Fluids.fluidZirconium, true);
			registerTinkerMaterial(zirconium, Fluids.fluidZirconium, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			
//			registerAlloy("aluminumbrass", 2, "aluminum", 1, "brass", 1); // TCon already has Aluminum Brass alloy
			registerAlloy("galvanizedsteel", 2, new String[] {"steel", "zinc"}, new int[] {1, 1});
			registerAlloy("nichrome", 2,  new String[] {"nickel", "chrome"}, new int[] {1, 1});
			registerAlloy("stainlesssteel", 2,  new String[] {"steel", "chrome"}, new int[] {1, 1});
			registerAlloy("titanium", 2,  new String[] {"rutile", "magnesium"}, new int[] {1, 1});
		}

		initDone = true;
	}

	/**
	 * 
	 * @param fluid
	 * @param toolforge
	 */
	public static void registerFluid(Fluid fluid, boolean toolforge) {
		if(Loader.isModLoaded("tconstruct")) {
			NBTTagCompound message = new NBTTagCompound();
			message.setString("fluid", fluid.getName());
			message.setString("ore", StringUtilities.upperCaseFirst(fluid.getName()));
			message.setBoolean("toolforge", toolforge);
			//message.setTag("alloy", alloysTagList); // you can also send an alloy with the registration (see below)

			// send the NBT to TCon
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", message);
		}
	}

	/**
	 * 
	 * @param outputName
	 * @param outputQty
	 * @param inputName
	 * @param inputQty
	 */
	public static void registerAlloy(String outputName, int outputQty, String[] inputName, int[] inputQty)
	{
		if(Loader.isModLoaded("tconstruct")) {
			if(inputName.length != inputQty.length)
				throw new RuntimeException("Alloy must have the same amount of inputName and intQty");

			NBTTagList tagList = new NBTTagList();
			
			// if you have access to the fluid-instance you can also do FluidStack.writeToNBT
			NBTTagCompound fluid = new NBTTagCompound();
			fluid.setString("FluidName", outputName);
			fluid.setInteger("Amount", outputQty); // 144 = 1 ingot, 16 = 1 nugget
			tagList.appendTag(fluid);
		
			// alloy fluid
			for(int i = 0; i < inputName.length; i++) {
				fluid = new NBTTagCompound();
				fluid.setString("FluidName", inputName[i]);
				fluid.setInteger("Amount", inputQty[i]);
				tagList.appendTag(fluid);
			}

			NBTTagCompound message = new NBTTagCompound();
			message.setTag("alloy", tagList);
			FMLInterModComms.sendMessage("tconstruct", "alloy", message);
		}
	}
	
	private static void registerTinkerMaterial(Material material, Fluid fluid, int headDura, float headSpeed, float headAttack, float handleMod, int handleDura, int extra, int headLevel, boolean craft, boolean cast) {

		TinkerRegistry.addMaterialStats(material, new HeadMaterialStats(headDura, headSpeed, headAttack, headLevel)); // Sets stats for head
		TinkerRegistry.addMaterialStats(material, new HandleMaterialStats(handleMod, handleDura)); // Sets Stats for handle
		TinkerRegistry.addMaterialStats(material, new ExtraMaterialStats(extra)); // Sets stats for everything else

		Item item = Items.getItemByName(material.identifier + "_ingot"); // Why do we need to get an item here?
		
		// Set fluid used, Set whether craftable, set whether castable, adds the item with the value 144.
		material.setFluid(fluid).setCraftable(craft).setCastable(cast).addItem(item, 1, Material.VALUE_Ingot);
		material.setRepresentativeItem(item); // Uses item as the picture?

		// Probably don't need this
//		proxy.setRenderInfo(material);
		MaterialIntegration integration = new MaterialIntegration(material, fluid, StringUtilities.upperCaseFirst(fluid.getName()));
		integration.integrate();
		integrateList.add(integration);
	}
}
package modernmetals.init;

import modernmetals.init.Fluids;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 * 
 * @author Jasmine Iwanek
 *
 */
public class TinkersConstructPlugin {
	private static boolean initDone = false;

	/**
	 * 
	 */
	public static void init() {
		if(initDone) return;

		if(Loader.isModLoaded("tconstruct")) {
//			registerFluid(Fluids.fluidAluminum); // TCon already has this
//			registerFluid(Fluids.fluidAluminumBrass); // TCon already has this
			registerFluid(Fluids.fluidCadmium, true);
			registerFluid(Fluids.fluidChromium, true);
			registerFluid(Fluids.fluidGalvanizedSteel, true);
			registerFluid(Fluids.fluidIridium, true);
			registerFluid(Fluids.fluidMagnesium, true);
			registerFluid(Fluids.fluidManganese, true);
			registerFluid(Fluids.fluidNichrome, true);
			registerFluid(Fluids.fluidOsmium, true);
			registerFluid(Fluids.fluidPlutonium, true);
			registerFluid(Fluids.fluidRutile, true);
			registerFluid(Fluids.fluidStainlessSteel, true);
			registerFluid(Fluids.fluidTantalum, true);
			registerFluid(Fluids.fluidTitanium, true);
			registerFluid(Fluids.fluidTungsten, true);
			registerFluid(Fluids.fluidUranium, true);
			registerFluid(Fluids.fluidZirconium, true);
			
//			registerAlloy("aluminumbrass", 2, "aluminum", 1, "brass", 1); // TCon already has Aluminum Brass alloy
			registerAlloy("galvanizedsteel", 2, "steel", 1, "zinc", 1);
			registerAlloy("nichrome", 2, "nickel", 1, "chrome", 1);
			registerAlloy("stainlesssteel", 2, "steel", 1, "chrome", 1);
			registerAlloy("titanium", 2, "rutile", 1, "magnesium", 1);
		}

		initDone = true;
	}

	/**
	 * 
	 * @param fluid
	 */
	public static void registerFluid(Fluid fluid, boolean toolforge) {
		if(Loader.isModLoaded("tconstruct")) {
			NBTTagCompound message = new NBTTagCompound();
			message.setString("fluid", fluid.getName());
			message.setString("ore", upperCaseFirst(fluid.getName()));
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
	 * @param input1Name
	 * @param input1Qty
	 * @param input2Name
	 * @param input2Qty
	 */
	public static void registerAlloy(String outputName, int outputQty, String input1Name, int input1Qty, String input2Name, int input2Qty)
	{
		// TODO: Make this accept more than two input fluids
		if(Loader.isModLoaded("tconstruct")) {
			NBTTagList tagList = new NBTTagList();
			
			// if you have access to the fluid-instance you can also do FluidStack.writeToNBT
			NBTTagCompound fluid = new NBTTagCompound();
			fluid.setString("FluidName", outputName);
			fluid.setInteger("Amount", outputQty); // 144 = 1 ingot, 16 = 1 nugget
			tagList.appendTag(fluid);
		
			// first alloy fluid
			fluid = new NBTTagCompound();
			fluid.setString("FluidName", input1Name);
			fluid.setInteger("Amount", input1Qty);
			tagList.appendTag(fluid);
		
			// second alloy fluid
			fluid = new NBTTagCompound();
			fluid.setString("FluidName", input2Name);
			fluid.setInteger("Amount", input2Qty);
			tagList.appendTag(fluid);

			NBTTagCompound message = new NBTTagCompound();
			message.setTag("alloy", tagList);
			FMLInterModComms.sendMessage("tconstruct", "alloy", message);
		}
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String upperCaseFirst(String value) {

		char[] array = value.toCharArray();
		array[0] = Character.toUpperCase(array[0]);
		return new String(array);
	}
}
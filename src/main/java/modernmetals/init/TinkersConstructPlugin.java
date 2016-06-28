package modernmetals.init;

import modernmetals.init.Fluids;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class TinkersConstructPlugin {
	private static boolean initDone = false;

	public static void init() {
		if(initDone) return;

		if(Loader.isModLoaded("tconstruct")) {
//			registerFluid(Fluids.fluidAluminum); // TCon already has this
//			registerFluid(Fluids.fluidAluminumBrass); // TCon already has this
			registerFluid(Fluids.fluidCadmium);
			registerFluid(Fluids.fluidChromium);
			registerFluid(Fluids.fluidGalvanizedSteel);
			registerFluid(Fluids.fluidIridium);
			registerFluid(Fluids.fluidMagnesium);
			registerFluid(Fluids.fluidManganese);
			registerFluid(Fluids.fluidNichrome);
			registerFluid(Fluids.fluidOsmium);
			registerFluid(Fluids.fluidPlutonium);
			registerFluid(Fluids.fluidRutile);
			registerFluid(Fluids.fluidStainlessSteel);
			registerFluid(Fluids.fluidTantalum);
			registerFluid(Fluids.fluidTitanium);
			registerFluid(Fluids.fluidTungsten);
			registerFluid(Fluids.fluidUranium);
			registerFluid(Fluids.fluidZirconium);
			
//			registerAlloy("aluminumbrass", 2, "aluminum", 1, "brass", 1); // TCon already has Aluminum Brass alloy
			registerAlloy("galvanizedsteel", 2, "steel", 1, "zinc", 1);
			registerAlloy("nichrome", 2, "nickel", 1, "chrome", 1);
			registerAlloy("stainlesssteel", 2, "steel", 1, "chrome", 1);
			registerAlloy("titanium", 2, "rutile", 1, "magnesium", 1);
		}

		initDone = true;
	}
	
	public static void registerFluid(Fluid fluid) {
		// TODO: Make toolforge configurable
		if(Loader.isModLoaded("tconstruct")) {
			NBTTagCompound message = new NBTTagCompound();
			message.setString("fluid", fluid.getName());
			message.setString("ore", upperCaseFirst(fluid.getName()));
			message.setBoolean("toolforge", true);
			//message.setTag("alloy", alloysTagList); // you can also send an alloy with the registration (see below)

			// send the NBT to TCon
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", message);
		}
	}

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
	
	public static String upperCaseFirst(String value) {

		char[] array = value.toCharArray();
		array[0] = Character.toUpperCase(array[0]);
		return new String(array);
	}
}
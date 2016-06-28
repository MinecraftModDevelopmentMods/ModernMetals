package modernmetals.init;

import modernmetals.ModernMetals;
import modernmetals.blocks.BlockMoltenFluid;
import cyano.basemetals.fluids.CustomFluid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

/**
 * This class initializes all fluids in Modern Metals and provides some utility 
 * methods for looking up fluids. 
 * @author DrCyano
 *
 */
public abstract class Fluids {

	static {
		FluidRegistry.enableUniversalBucket();
	}

	public static Fluid fluidAluminum = null;
	public static BlockFluidBase fluidBlockAluminum = null;

	public static Fluid fluidAluminumBrass = null;
	public static BlockFluidBase fluidBlockAluminumBrass = null;

	public static Fluid fluidCadmium = null;
	public static BlockFluidBase fluidBlockCadmium = null;

	public static Fluid fluidChromium = null;
	public static BlockFluidBase fluidBlockChromium = null;

	public static Fluid fluidGalvanizedSteel = null;
	public static BlockFluidBase fluidBlockGalvanizedSteel = null;

	public static Fluid fluidIridium = null;
	public static BlockFluidBase fluidBlockIridium = null;

	public static Fluid fluidMagnesium = null;
	public static BlockFluidBase fluidBlockMagnesium = null;

	public static Fluid fluidManganese = null;
	public static BlockFluidBase fluidBlockManganese = null;

	public static Fluid fluidNichrome = null;
	public static BlockFluidBase fluidBlockNichrome = null;

	public static Fluid fluidOsmium = null;
	public static BlockFluidBase fluidBlockOsmium = null;

	public static Fluid fluidPlutonium = null;
	public static BlockFluidBase fluidBlockPlutonium = null;

	public static Fluid fluidRutile = null;
	public static BlockFluidBase fluidBlockRutile = null;

	public static Fluid fluidStainlessSteel = null;
	public static BlockFluidBase fluidBlockStainlessSteel = null;

	public static Fluid fluidTantalum = null;
	public static BlockFluidBase fluidBlockTantalum = null;

	public static Fluid fluidTitanium = null;
	public static BlockFluidBase fluidBlockTitanium = null;

	public static Fluid fluidTungsten = null;
	public static BlockFluidBase fluidBlockTungsten = null;

	public static Fluid fluidUranium = null;
	public static BlockFluidBase fluidBlockUranium = null;

	public static Fluid fluidZirconium = null;
	public static BlockFluidBase fluidBlockZirconium = null;

	private static final Map<Fluid,BlockFluidBase> fluidBlocks = new HashMap<>();
	private static final Map<BlockFluidBase,String> fluidBlockNames = new HashMap<>();

	private static boolean initDone = false;
	public static void init() {
		if(initDone) return;

		// fluids
		fluidAluminum = newFluid(ModernMetals.MODID, "aluminum", 2000, 10000, 330, 10, 0xFFC5C8C1);

		fluidAluminumBrass = newFluid(ModernMetals.MODID, "alubrass", 2000, 10000, 500, 10, 0xFFEBAA56);

		fluidCadmium = newFluid(ModernMetals.MODID, "cadmium", 2000, 10000, 300, 10, 0xFFC9D4DA);

		fluidChromium = newFluid(ModernMetals.MODID, "chromium", 2000, 10000, 769, 10, 0xFFCDCDCF);

		fluidGalvanizedSteel = newFluid(ModernMetals.MODID, "galvanizedsteel", 2000, 10000,769, 10, 0xFF9BA6A2);

		fluidIridium = newFluid(ModernMetals.MODID, "iridium", 2000, 10000, 769, 10, 0xFFF8EDCC);

		fluidMagnesium = newFluid(ModernMetals.MODID, "magnesium", 2000,10000,769,10, 0xFF7F7F77);

		fluidManganese = newFluid(ModernMetals.MODID, "manganese", 2000,10000,769,10, 0xFFF5CFDA);

		fluidNichrome = newFluid(ModernMetals.MODID, "nichrome", 2000,10000,769,10, 0xFFDEA054);

		fluidOsmium = newFluid(ModernMetals.MODID, "osmium", 2000,10000,769,10, 0xFF7C8E99);

		fluidPlutonium = newFluid(ModernMetals.MODID, "plutonium", 2000,769,300,10, 0xFFB333EA);

		fluidRutile = newFluid(ModernMetals.MODID, "rutile", 2000,10000,769,10, 0xFFBF928B);

		fluidStainlessSteel = newFluid(ModernMetals.MODID, "stainlesssteel", 2000,10000,769,10, 0xFFC5BFC1);

		fluidTantalum = newFluid(ModernMetals.MODID, "tantalum", 2000,10000,769,10, 0xFFC4BEC2);

		fluidTitanium = newFluid(ModernMetals.MODID, "titanium", 2000,10000,769,10, 0xFF73787E);

		fluidTungsten = newFluid(ModernMetals.MODID, "tungsten", 2000,10000,769,10, 0xFF969696);

		fluidUranium = newFluid(ModernMetals.MODID, "uranium", 2000,10000,769,10, 0xFFA7B345);

		fluidZirconium = newFluid(ModernMetals.MODID, "zirconium", 2000,10000,769,10, 0xFF929793);

		// fluid blocks
		fluidBlockAluminum = registerFluidBlock(fluidAluminum, new BlockMoltenFluid(fluidAluminum), "aluminum");

		fluidBlockAluminumBrass = registerFluidBlock(fluidAluminumBrass, new BlockMoltenFluid(fluidAluminumBrass),"alubrass");

		fluidBlockCadmium = registerFluidBlock(fluidCadmium, new BlockMoltenFluid(fluidCadmium),"cadmium");

		fluidBlockChromium = registerFluidBlock(fluidChromium, new BlockMoltenFluid(fluidChromium),"chromium");

		fluidBlockGalvanizedSteel = registerFluidBlock(fluidGalvanizedSteel, new BlockMoltenFluid(fluidGalvanizedSteel),"galvanizedsteel");

		fluidBlockIridium = registerFluidBlock(fluidIridium, new BlockMoltenFluid(fluidIridium),"iridium");

		fluidBlockMagnesium = registerFluidBlock(fluidMagnesium, new BlockMoltenFluid(fluidMagnesium),"magnesium");

		fluidBlockManganese = registerFluidBlock(fluidManganese, new BlockMoltenFluid(fluidManganese),"manganese");

		fluidBlockNichrome = registerFluidBlock(fluidNichrome, new BlockMoltenFluid(fluidNichrome),"nichrome");

		fluidBlockOsmium = registerFluidBlock(fluidOsmium, new BlockMoltenFluid(fluidOsmium),"osmium");

		fluidBlockPlutonium = registerFluidBlock(fluidPlutonium, new BlockMoltenFluid(fluidPlutonium),"plutonium");

		fluidBlockRutile = registerFluidBlock(fluidRutile, new BlockMoltenFluid(fluidRutile),"rutile");

		fluidBlockStainlessSteel = registerFluidBlock(fluidStainlessSteel, new BlockMoltenFluid(fluidStainlessSteel),"stainlesssteel");

		fluidBlockTantalum = registerFluidBlock(fluidTantalum, new BlockMoltenFluid(fluidTantalum),"tantalum");

		fluidBlockTitanium = registerFluidBlock(fluidTitanium, new BlockMoltenFluid(fluidTitanium),"titanium");

		fluidBlockTungsten = registerFluidBlock(fluidTungsten, new BlockMoltenFluid(fluidTungsten),"tungsten");

		fluidBlockUranium = registerFluidBlock(fluidUranium, new BlockMoltenFluid(fluidUranium),"uranium");

		fluidBlockZirconium = registerFluidBlock(fluidZirconium, new BlockMoltenFluid(fluidZirconium),"zirconium");

		initDone = true;
	}

	@SideOnly(Side.CLIENT)
	public static void bakeModels(String modID) {
		for(Fluid fluid : fluidBlocks.keySet()) {
			BlockFluidBase block = fluidBlocks.get(fluid);
			Item item = Item.getItemFromBlock(block);
			final ModelResourceLocation fluidModelLocation = new ModelResourceLocation(
					modID.toLowerCase() + ":" + fluidBlockNames.get(block), "fluid");
            ModelBakery.registerItemVariants(item);
			ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
			{
				@Override
				public ModelResourceLocation getModelLocation(ItemStack stack)
				{
					return fluidModelLocation;
				}
			});
			ModelLoader.setCustomStateMapper(block, new StateMapperBase()
			{
				@Override
				protected ModelResourceLocation getModelResourceLocation(IBlockState state)
				{
					return fluidModelLocation;
				}
			});
		}
	}

	private static Fluid newFluid(String modID, String name, int density, int viscosity, int temperature, int luminosity, int tintColor) {
		Fluid fluid = new CustomFluid(name, new ResourceLocation(modID+":blocks/molten_metal_still"), new ResourceLocation(modID+":blocks/molten_metal_flow"), tintColor);
		fluid.setDensity(density);
		fluid.setViscosity(viscosity);
		fluid.setTemperature(temperature);
		fluid.setLuminosity(luminosity);
		fluid.setUnlocalizedName(modID+"."+name);
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
		return fluid;
	}

	private static BlockFluidClassic registerFluidBlock(Fluid fluid, BlockFluidClassic block, String name) {
		ResourceLocation location = new ResourceLocation(ModernMetals.MODID, name);
		block.setRegistryName(location);
		block.setUnlocalizedName(location.toString());
		GameRegistry.register(block);
		block.setCreativeTab(CreativeTabs.MISC);

		ItemBlock itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(location);
		itemBlock.setUnlocalizedName(location.toString());
		GameRegistry.register(itemBlock);
		
		fluidBlocks.put(fluid, block);
		fluidBlockNames.put(block, name);
		return block;
	}
}

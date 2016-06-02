package modernmetals.init;

import java.util.HashMap;
import java.util.Map;

import modernmetals.ModernMetals;
import cyano.basemetals.blocks.BlockMetalDoor;
import cyano.basemetals.blocks.InteractiveFluidBlock;
import cyano.basemetals.fluids.CustomFluid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    private static final ResourceLocation dizzyPotionKey = new ResourceLocation("nausea");

	private static boolean initDone = false;
	public static void init() {
		if(initDone) return;

		// fluids
		fluidAluminum = newFluid(ModernMetals.MODID, "aluminum", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidAluminum);

		fluidAluminumBrass = newFluid(ModernMetals.MODID, "alubrass", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidAluminumBrass);

		fluidCadmium = newFluid(ModernMetals.MODID, "cadmium", 13594,2000,300,0, 0xFF49FF00);
		FluidRegistry.addBucketForFluid(fluidCadmium);

		fluidChromium = newFluid(ModernMetals.MODID, "chromium", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidChromium);

		fluidGalvanizedSteel = newFluid(ModernMetals.MODID, "galvanizedsteel", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidGalvanizedSteel);

		fluidIridium = newFluid(ModernMetals.MODID, "iridium", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidIridium);

		fluidMagnesium = newFluid(ModernMetals.MODID, "magnesium", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidMagnesium);

		fluidManganese = newFluid(ModernMetals.MODID, "manganese", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidManganese);

		fluidNichrome = newFluid(ModernMetals.MODID, "nichrome", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidNichrome);

		fluidOsmium = newFluid(ModernMetals.MODID, "osmium", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidOsmium);

		fluidPlutonium = newFluid(ModernMetals.MODID, "plutonium", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidPlutonium);

		fluidRutile = newFluid(ModernMetals.MODID, "rutile", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidRutile);

		fluidStainlessSteel = newFluid(ModernMetals.MODID, "stainlesssteel", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidStainlessSteel);

		fluidTantalum = newFluid(ModernMetals.MODID, "tantalum", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidTantalum);

		fluidTitanium = newFluid(ModernMetals.MODID, "titanium", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidTitanium);

		fluidTungsten = newFluid(ModernMetals.MODID, "tungsten", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidTungsten);

		fluidUranium = newFluid(ModernMetals.MODID, "uranium", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidUranium);

		fluidZirconium = newFluid(ModernMetals.MODID, "zirconium", 13594,2000,300,0, 0xFFD8D8D8);
		FluidRegistry.addBucketForFluid(fluidZirconium);

		// fluid blocks
		fluidBlockAluminum = registerFluidBlock(fluidAluminum, new InteractiveFluidBlock(
				fluidAluminum, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"aluminum");

		fluidBlockAluminumBrass = registerFluidBlock(fluidAluminumBrass, new InteractiveFluidBlock(
				fluidAluminumBrass, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"alubrass");

		fluidBlockCadmium = registerFluidBlock(fluidCadmium, new InteractiveFluidBlock(
				fluidCadmium, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"cadmium");

		fluidBlockChromium = registerFluidBlock(fluidChromium, new InteractiveFluidBlock(
				fluidChromium, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"chromium");

		fluidBlockGalvanizedSteel = registerFluidBlock(fluidGalvanizedSteel, new InteractiveFluidBlock(
				fluidGalvanizedSteel, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"galvanizedsteel");

		fluidBlockIridium = registerFluidBlock(fluidIridium, new InteractiveFluidBlock(
				fluidIridium, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"iridium");

		fluidBlockMagnesium = registerFluidBlock(fluidMagnesium, new InteractiveFluidBlock(
				fluidMagnesium, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"magnesium");

		fluidBlockManganese = registerFluidBlock(fluidManganese, new InteractiveFluidBlock(
				fluidManganese, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"manganese");

		fluidBlockNichrome = registerFluidBlock(fluidNichrome, new InteractiveFluidBlock(
				fluidNichrome, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"nichrome");

		fluidBlockOsmium = registerFluidBlock(fluidOsmium, new InteractiveFluidBlock(
				fluidOsmium, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"osmium");

		fluidBlockPlutonium = registerFluidBlock(fluidPlutonium, new InteractiveFluidBlock(
				fluidPlutonium, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"plutonium");

		fluidBlockRutile = registerFluidBlock(fluidRutile, new InteractiveFluidBlock(
				fluidRutile, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"rutile");

		fluidBlockStainlessSteel = registerFluidBlock(fluidStainlessSteel, new InteractiveFluidBlock(
				fluidStainlessSteel, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"stainlesssteel");

		fluidBlockTantalum = registerFluidBlock(fluidTantalum, new InteractiveFluidBlock(
				fluidTantalum, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"tantalum");

		fluidBlockTitanium = registerFluidBlock(fluidTitanium, new InteractiveFluidBlock(
				fluidTitanium, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"titanium");

		fluidBlockTungsten = registerFluidBlock(fluidTungsten, new InteractiveFluidBlock(
				fluidTungsten, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"tungsten");

		fluidBlockUranium = registerFluidBlock(fluidUranium, new InteractiveFluidBlock(
				fluidUranium, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"uranium");

		fluidBlockZirconium = registerFluidBlock(fluidZirconium, new InteractiveFluidBlock(
				fluidZirconium, false, (World w, EntityLivingBase e)->{
					if(w.rand.nextInt(32) == 0) e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30*20, 2));
				}),"zirconium");

		initDone = true;
	}

	@SideOnly(Side.CLIENT)
	public static void bakeModels(String modID){
		for(Fluid fluid : fluidBlocks.keySet()){
			BlockFluidBase block = fluidBlocks.get(fluid);
			Item item = Item.getItemFromBlock(block);
			final ModelResourceLocation fluidModelLocation = new ModelResourceLocation(
					modID.toLowerCase() + ":" + fluidBlockNames.get(block), "fluid");
            ModelBakery.registerItemVariants(item);
			ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
			{
				public ModelResourceLocation getModelLocation(ItemStack stack)
				{
					return fluidModelLocation;
				}
			});
			ModelLoader.setCustomStateMapper(block, new StateMapperBase()
			{
				protected ModelResourceLocation getModelResourceLocation(IBlockState state)
				{
					return fluidModelLocation;
				}
			});
		}
	}
	

	private static Fluid newFluid(String modID, String name, int density, int viscosity, int temperature, int luminosity, int tintColor) {
		Fluid fluid = new CustomFluid(name, new ResourceLocation(modID+":blocks/"+name+"_still"), new ResourceLocation(modID+":blocks/"+name+"_flow"), tintColor);
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
//		block.setUnlocalizedName(ModernMetals.MODID+"."+name);
//		GameRegistry.registerBlock(block, name);
//		block.setCreativeTab(CreativeTabs.MISC);

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

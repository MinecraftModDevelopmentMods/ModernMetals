package modernmetals.init;

import java.util.HashMap;
import java.util.List;
import com.google.common.collect.Lists;
import cyano.basemetals.material.MetalMaterial;
import modernmetals.init.Fluids;
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
import slimeknights.tconstruct.library.traits.ITrait;

import java.util.List;

/**
 *
 * @author Jasmine Iwanek
 *
 */
public class TinkersConstructPlugin {
	private static final String TCONSTRUCT_MODID = "tconstruct";
	private static HashMap<String, MaterialCorrelation> correlation = new HashMap<String,MaterialCorrelation>(); 
	private static boolean initDone = false;

	private static List<MaterialIntegration> integrateList = Lists.newArrayList(); // List of materials needed to be integrated
	
	/**
	 *
	 */
	@SuppressWarnings("unused")
	public static void init() {
		if(initDone)
			return;

		if(Loader.isModLoaded(TCONSTRUCT_MODID)) {
			final double d = 0; // durabilityFactorGeneral;
			// System.out.println("DURABILITY FACTOR" + d);
			final float s = 0;// (float) speedFactorGeneral;
			// System.out.println("SPEED FACTOR" + s);
			final float a = 0; // (float) attackFactorGeneral;
			// System.out.println("ATTACK FACTOR" + a);
			
			final Material aluminum = createTCMaterial("aluminum", 0xFFC5C8C1);
			final Material aluminumbrass = createTCMaterial("aluminumbrass", 0xFFEBAA56);
			final Material cadmium = createTCMaterial("cadmium", 0xFFC9D4DA, Fluids.fluidCadmium);
			final Material chromium = createTCMaterial("chromium", 0xFFCDCDCF, Fluids.fluidChromium);
			final Material galvanizedsteel = createTCMaterial("galvanizedsteel", 0xFF9BA6A2, Fluids.fluidGalvanizedSteel);
			final Material iridium = createTCMaterial("iridium", 0xFFF8EDCC, Fluids.fluidIridium);
			final Material magnesium = createTCMaterial("magnesium", 0xFF7F7F77, Fluids.fluidMagnesium);
			final Material manganese = createTCMaterial("manganese", 0xFFF5CFDA, Fluids.fluidManganese);
			final Material nichrome = createTCMaterial("nichrome", 0xFFDEA054, Fluids.fluidNichrome);
			final Material osmium = createTCMaterial("osmium", 0xFF7C8E99, Fluids.fluidOsmium);
			final Material plutonium = createTCMaterial("plutonium", 0xFFB333EA, Fluids.fluidPlutonium);
			final Material rutile = createTCMaterial("rutile", 0xFFBF928B, Fluids.fluidRutile);
			final Material stainlesssteel = createTCMaterial("stainlesssteel", 0xFFC5BFC1, Fluids.fluidStainlessSteel);
			final Material tantalum = createTCMaterial("tantalum", 0xFFC4BEC2, Fluids.fluidTantalum);
			final Material titanium = createTCMaterial("titanium", 0xFF73787E, Fluids.fluidTitanium);
			final Material tungsten = createTCMaterial("tungsten", 0xFF969696, Fluids.fluidTungsten);
			final Material uranium = createTCMaterial("uranium", 0xFFA7B345, Fluids.fluidUranium);
			final Material zirconium = createTCMaterial("zirconium", 0xFF929793, Fluids.fluidZirconium);

//			registerFluid(Fluids.fluidAluminum); // TCon already has this
//			registerTinkerMaterial(aluminum, Fluids.fluidAluminum, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
//			registerFluid(Fluids.fluidAluminumBrass); // TCon already has this
//			registerTinkerMaterial(aluminumbrass, Fluids.fluidAluminumBrass, (int) (235 * d), 5.33f * s, 3.80f * a, 1.15f, 17, 117, 1, false, true);
			
			for(String name : correlation.keySet()) {
				if (null != correlation.get(name).getMeltFluid()) {
				// skip items with no declared melt fluid - eg Aluminum, Aluminum Brass
				// 		also skips items where the melt fluid process went awol, which is nice.
					setupTConSmeltAndParts(name, d, s, a);
				}
			}
			
//			registerAlloy("aluminumbrass", 2, "aluminum", 1, "brass", 1); // TCon already has Aluminum Brass alloy
			registerAlloy("galvanizedsteel", 2, new String[] {"steel", "zinc"}, new int[] {1, 1});
			registerAlloy("nichrome", 2,  new String[] {"nickel", "chrome"}, new int[] {1, 1});
			registerAlloy("stainlesssteel", 2,  new String[] {"steel", "chrome"}, new int[] {1, 1});
			registerAlloy("titanium", 2,  new String[] {"rutile", "magnesium"}, new int[] {1, 1});
		}

		initDone = true;
	}

	/**
	 * Factory method to convert MetalMaterial statistics to Tinkers Construct statistics and create the TCon material. 
	 * @param name Material identifier
	 * @param durabilityFactor Overall scaling factor of toolpart durability
	 * @param speedFactor overall scaling factor of toolpart speed
	 * @param attackFactor overall scaling factor of toolpart damage
	 */
	private static void setupTConSmeltAndParts(final String name, double durabilityFactor, float speedFactor, float attackFactor) {
		final MaterialCorrelation metal = correlation.get(name);
		
		registerFluid(metal.getMeltFluid(), true);
		metal.getMetal().getBaseAttackDamage();
		
		registerTinkerMaterial(metal.getMaterial(), metal.getMeltFluid(), 
				(int) (metal.getMetal().getToolDurability()), 
				metal.getMetal().magicAffinity*3/2,
				metal.getMetal().getBaseAttackDamage()*4, 
				// Handle durability multiplier, handle durability
				(metal.getMetal().hardness+metal.getMetal().magicAffinity*2)/9, (int) (metal.getMetal().getToolDurability()/6.8), 
				// "Extra" - durability of non handle, non-head parts?
				(int) (metal.getMetal().getToolDurability()/10),
				// Mining level
				metal.getMetal().getToolHarvestLevel(),
				// Craft at workbench: always false, craft at smeltery: always true
				false, true);
	}

	/**
	 * Creates a Tinkers Construct {@link slimeknights.tconstruct.library.materials.Material} and associates it with the {@link cyano.basemetals.Material.MetalMaterial} and {@fluid net.minecraftforge.fluids.Fluid} it goes with. This association is saved in the {@link #correlation} map so later we can just loop over the correlation for stuff. 
	 * @param hexColor Color of the tool parts
	 * @param name Material identifier
	 * @param meltFluid fluid to be produced when this is melted in the smeltery
	 * @return
	 */
	private static Material createTCMaterial(String name, int hexColor, Fluid meltFluid) {
		final Material material = new Material(name,hexColor);
		correlation.put(name, new MaterialCorrelation(material, Materials.getMetalByName(name), meltFluid));
		
		return material;
	}

	/**
	 * Wrapper for {@link #createTCMaterial(String, int, Fluid) createTCMaterial(String name, int hexColor, Fluid meltFluid)} method, for when there is no fluid needed
	 * Creates a Tinkers Construct {@link slimeknights.tconstruct.library.materials.Material} and associates it with the {@link cyano.basemetals.Material.MetalMaterial} it goes with. This association is saved in the {@link #correlation} map so later we can just loop over the correlation for stuff. 
	 *
	 * @param hexColor Color of the tool parts
	 * @param name Material identifier
	 * @return
	 */
	private static Material createTCMaterial(String name, int hexColor) {
		return createTCMaterial(name, hexColor, null);
	}

	/**
	 * @param fluid
	 * @param toolforge
	 */
	public static void registerFluid(Fluid fluid, boolean toolforge) {
		if(Loader.isModLoaded(TCONSTRUCT_MODID)) {
			final NBTTagCompound message = new NBTTagCompound();
			message.setString("fluid", fluid.getName());
			message.setString("ore", StringUtilities.upperCaseFirst(fluid.getName()));
			message.setBoolean("toolforge", toolforge);
			//message.setTag("alloy", alloysTagList); // you can also send an alloy with the registration (see below)

			// send the NBT to TCon
			FMLInterModComms.sendMessage(TCONSTRUCT_MODID, "integrateSmeltery", message);
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
		if(Loader.isModLoaded(TCONSTRUCT_MODID)) {
			if(inputName.length != inputQty.length)
				throw new RuntimeException("Alloy must have the same amount of inputName and intQty");

			final NBTTagList tagList = new NBTTagList();

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
			FMLInterModComms.sendMessage(TCONSTRUCT_MODID, "alloy", message);
		}
	}
	
	private static void registerTinkerMaterial(Material material, Fluid fluid, int headDura, float headSpeed, float headAttack, float handleMod, int handleDura, int extra, int headLevel, boolean craft, boolean cast)
	{
		registerTinkerMaterial(material, fluid, headDura, headSpeed, headAttack, handleMod, handleDura, extra, headLevel, craft, cast, null);
	}
	
	private static void registerTinkerMaterial(Material material, Fluid fluid, int headDura, float headSpeed, float headAttack, float handleMod, int handleDura, int extra, int headLevel, boolean craft, boolean cast, ITrait trait) {
		final List<MaterialIntegration> integrateList = Lists.newArrayList(); // List of materials needed to be integrated

		TinkerRegistry.addMaterialStats(material, new HeadMaterialStats(headDura, headSpeed, headAttack, headLevel)); // Sets stats for head
		TinkerRegistry.addMaterialStats(material, new HandleMaterialStats(handleMod, handleDura)); // Sets Stats for handle
		TinkerRegistry.addMaterialStats(material, new ExtraMaterialStats(extra)); // Sets stats for everything else
		if (trait != null) {
			String stats = "temporary placeholder"; // TODO: find out what goes here
			TinkerRegistry.addMaterialTrait(material, trait, stats);
		}

		final Item item = Items.getItemByName(material.identifier + "_ingot"); // Why do we need to get an item here?
		
		// Set fluid used, Set whether craftable, set whether castable, adds the
		// item with the value 144.
		material.setFluid(fluid).setCraftable(craft).setCastable(cast).addItem(item, 1, Material.VALUE_Ingot);
		material.setRepresentativeItem(item); // Uses item as the picture?

		// Probably don't need this
		//proxy.setRenderInfo(material);
		final MaterialIntegration integration = new MaterialIntegration(material, fluid, StringUtilities.upperCaseFirst(fluid.getName()));
		integration.integrate();
		integrateList.add(integration);
	}
}
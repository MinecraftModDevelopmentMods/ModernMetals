package review.modern;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.material.MMDMaterial;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import zone.moddev.mc.orespawn.api.OreSpawnApi;

/** Inspects the installed jar, registered content, recipes, profile and saved data. */
@Mod(modid = "modernmetalsprobe", name = "Modern Metals runtime checks", version = "1",
     acceptableRemoteVersions = "*", dependencies = "required-after:modernmetals")
public final class ModernMetalsProbe {
    private static final String[] MATERIALS = { "aluminum", "aluminumbrass", "beryllium", "boron",
        "cadmium", "chromium", "galvanizedsteel", "iridium", "magnesium", "manganese", "nichrome",
        "osmium", "plutonium", "rutile", "stainlesssteel", "tantalum", "thorium", "titanium",
        "tungsten", "uranium", "zirconium" };

    private static net.minecraft.world.DimensionType testDimension;
    @Mod.EventHandler public void preInit(final net.minecraftforge.fml.common.event.FMLPreInitializationEvent event) {
        if(Boolean.getBoolean("modern.disabledIntegrationTest")) OptionalIntegrationChecks.seedForeign();
        if(Boolean.getBoolean("modern.customDimension")) {
            testDimension=net.minecraft.world.DimensionType.register("modernmetals_check","_modern_check",42,OrdinaryDimension.class,false);
            net.minecraftforge.common.DimensionManager.registerDimension(42,testDimension);
        }
    }
    public static final class OrdinaryDimension extends net.minecraft.world.WorldProviderSurface {
        @Override public net.minecraft.world.DimensionType getDimensionType() {return testDimension;}
    }

    @Mod.EventHandler
    public void started(final FMLServerStartedEvent event) throws Exception {
        if (System.getProperty("modern.phase") == null) return;
        final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if (server.isDedicatedServer()) {
            run(server);
            server.initiateShutdown();
        }
    }

    public static void run(final MinecraftServer server) throws Exception {
        final String phase = System.getProperty("modern.phase", "first");
        final JsonObject report = new JsonObject();
        final JsonArray failures = new JsonArray();
        final Set<String> disabled = new HashSet<>(Arrays.asList(System.getProperty("modern.disabled", "").split(",")));
        final JsonObject catalog = new JsonObject();
        check("2.5.1.110021".equals(Loader.instance().getIndexedModList().get("modernmetals").getVersion()), "mod version", failures);
        for (final String name : MATERIALS) {
            final MMDMaterial material = Materials.getMaterialByName(name);
            if (disabled.contains(name)) {
                check(material.isEmpty(), "Disabled material registered: " + name, failures);
                continue;
            }
            check(!material.isEmpty(), "Missing material: " + name, failures);
            if (material.isEmpty()) continue;
            final JsonObject entry = new JsonObject();
            entry.addProperty("toolDurability", material.getToolDurability());
            entry.addProperty("fluid", material.getFluid() == null ? "" : material.getFluid().getName());
            for (final Names form : new Names[] {Names.INGOT, Names.NUGGET, Names.POWDER, Names.PICKAXE, Names.HELMET}) {
                check(material.hasItem(form), "Missing " + name + ":" + form, failures);
            }
            check(material.hasBlock(Names.BLOCK), "Missing storage block: " + name, failures);
            check(material.getFluid() != null && FluidRegistry.getFluid(material.getFluid().getName()) != null,
                  "Missing registered fluid: " + name, failures);
            if(material.getFluid()!=null) {
                check(material.getFluid()==FluidRegistry.getFluid(name),"Material retains an unregistered duplicate fluid: "+name,failures);
                check(zone.moddev.mc.modernmetals.init.Fluids.getFluidByName(name)==material.getFluid()
                    && zone.moddev.mc.modernmetals.init.Fluids.getFluidRegistry().get(name)==material.getFluid()
                    && name.equals(zone.moddev.mc.modernmetals.init.Fluids.getNameOfFluid(material.getFluid())),"Legacy fluid query lost borrowed material: "+name,failures);
            }
            check(material.getFluidBlock() != null, "Missing fluid block: " + name, failures);
            if (material.getFluid() != null) check(FluidRegistry.getBucketFluids().contains(material.getFluid()), "Missing bucket: " + name, failures);
            catalog.add(name, entry);
        }
        report.add("materials", catalog);
        for(final String[] alias:new String[][]{{"chromium","Chrome"},{"chromium","Chromium"},{"tungsten","Wolfram"},{"tungsten","Tungsten"},{"aluminum","Aluminium"}}) {
            if(!Materials.hasMaterial(alias[0])) continue;
            boolean found=false;
            for(final ItemStack stack:net.minecraftforge.oredict.OreDictionary.getOres("ingot"+alias[1]))
                if(stack.getItem()==Materials.getMaterialByName(alias[0]).getItem(Names.INGOT)) found=true;
            check(found,"Missing ingot alias: "+alias[1],failures);
        }
        final java.util.Set<Integer> durability=new java.util.HashSet<>();
        for(final MMDMaterial material:Materials.getMaterialsByMod("modernmetals")) durability.add(material.getToolDurability());
        if(catalog.entrySet().size()>4) check(durability.size()>4,"Material statistics unexpectedly uniform",failures);
        if (System.getProperty("modern.lowFantasy") != null) {
            final boolean low=Boolean.getBoolean("modern.lowFantasy");
            check(zone.moddev.mc.modernmetals.content.ContentPolicy.active().lowFantasy()==low,"Active fantasy mode does not match the startup selection",failures);
            for(final String name:new String[]{"plutonium","uranium","thorium","cadmium","rutile","titanium","stainlesssteel","galvanizedsteel"}) {
                final MMDMaterial material=Materials.getMaterialByName(name);
                if(material.isEmpty()) continue;
                final boolean suitable=Arrays.asList("titanium","stainlesssteel","galvanizedsteel").contains(name);
                boolean recipe=false;
                for(final net.minecraft.item.crafting.IRecipe value:CraftingManager.getInstance().getRecipeList()) {
                    final ItemStack output=value.getRecipeOutput();
                    if(output!=null && output.getItem()==material.getItem(Names.PICKAXE)) recipe=true;
                }
                check(recipe==(!low || suitable),"Fantasy mode pickaxe recipe incorrect: "+name,failures);
                check(material.hasItem(Names.PICKAXE) && material.hasBlock(Names.ORE)==material.hasOre(),"Fantasy mode changed registry identities: "+name,failures);
            }
            if(low) {
                for(final net.minecraft.item.crafting.IRecipe recipe:CraftingManager.getInstance().getRecipeList()) {
                    final ItemStack output=recipe.getRecipeOutput();
                    if(output==null || output.getItem()==null || output.getItem().getRegistryName()==null) continue;
                    final ResourceLocation id=output.getItem().getRegistryName();
                    if(!"modernmetals".equals(id.getResourceDomain())) continue;
                    final String path=id.getResourcePath();
                    for(final String material:new String[]{"plutonium","uranium","thorium","boron","beryllium","cadmium","rutile","chromium","iridium","osmium"})
                        for(final String form:new String[]{"gear","door","trapdoor","bars","stairs","slab","pressure_plate"})
                            check(!path.equals(material+"_"+form),"Low Fantasy unrealistic product remains: "+path,failures);
                }
            }
        }
        OptionalIntegrationChecks.run(failures);
        final WorldServer world = server.worldServerForDimension(0);
        for (final String[] alloy : new String[][] {{"aluminumbrass","aluminum","brass"},
                {"galvanizedsteel","steel","zinc"}, {"nichrome","nickel","chromium"},
                {"stainlesssteel","steel","chromium"}, {"titanium","rutile","magnesium"}}) {
            if (!Materials.hasMaterial(alloy[0]) || !Materials.hasMaterial(alloy[1]) || !Materials.hasMaterial(alloy[2])) continue;
            final InventoryCrafting grid = new InventoryCrafting(new Container() {
                @Override public boolean canInteractWith(final net.minecraft.entity.player.EntityPlayer player) { return true; }
            }, 2, 2);
            grid.setInventorySlotContents(0, Materials.getMaterialByName(alloy[1]).getItemStack(Names.POWDER));
            grid.setInventorySlotContents(1, Materials.getMaterialByName(alloy[2]).getItemStack(Names.POWDER));
            final ItemStack result = CraftingManager.getInstance().findMatchingRecipe(grid, world);
            check(result != null && result.getItem() == Materials.getMaterialByName(alloy[0]).getItem(Names.BLEND)
                  && result.stackSize == 2, "Unobtainable alloy blend: " + alloy[0], failures);
        }
        if (OreSpawnApi.getActiveProfile(server).isPresent()) {
            final JsonObject profile = OreSpawnApi.getActiveProfile(server).get().toJson();
            report.add("profile", profile);
            if (System.getProperty("modern.expected") != null) {
                final JsonObject expected = new JsonParser().parse(new String(Files.readAllBytes(
                    Paths.get(System.getProperty("modern.expected"))), StandardCharsets.UTF_8)).getAsJsonObject();
                subset(expected, profile, "profile", failures);
            }
            if (Boolean.getBoolean("modern.sample") || "later".equals(phase)) sample(server, profile.getAsJsonObject("ores"), report, failures);
        }
        if (!Boolean.getBoolean("modern.baseline")) {
            check(OreSpawnApi.isOreTakeoverActive("modernmetals") != Boolean.getBoolean("modern.allDisabled"), "Native provider status incorrect", failures);
            try { Class.forName("com.mcmoddev.orespawn.ModernMetalsOreSpawn"); failures.add(new JsonPrimitive("Retired OS3 plugin packaged")); }
            catch (final ClassNotFoundException expected) { }
        }
        if (Boolean.getBoolean("modern.saved")) saved(world, phase, failures);
        report.add("failures", failures);
        report.addProperty("passed", failures.size() == 0);
        report.addProperty("phase", phase);
        Files.write(Paths.get(phase + "-modern.json"), (new GsonBuilder().setPrettyPrinting().create().toJson(report) + "\n").getBytes(StandardCharsets.UTF_8));
        server.saveAllWorlds(false);
        System.out.println("MODERN_METALS_RUNTIME_" + (failures.size() == 0 ? "PASS" : "FAIL") + " " + failures);
    }

    private static void sample(final MinecraftServer server, final JsonObject ores, final JsonObject report, final JsonArray failures) {
        final JsonObject result = new JsonObject();
        for (final int dimension : Boolean.getBoolean("modern.customDimension") ? new int[]{0,-1,1,42} : new int[]{0,-1,1}) {
            if (net.minecraftforge.common.DimensionManager.getWorld(dimension) == null)
                net.minecraftforge.common.DimensionManager.initDimension(dimension);
            final WorldServer world = server.worldServerForDimension(dimension);
            final int origin = "later".equals(System.getProperty("modern.phase")) ? 96 : 64;
            for (int x=origin; x<=origin+6; x++) for (int z=origin; z<=origin+6; z++) world.getChunkFromChunkCoords(x,z);
            final JsonObject counts = new JsonObject();
            final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            for (int cx=origin+2;cx<=origin+4;cx++) for (int cz=origin+2;cz<=origin+4;cz++) {
                final net.minecraft.world.chunk.Chunk chunk = world.getChunkFromChunkCoords(cx,cz);
                for(int x=0;x<16;x++) for(int z=0;z<16;z++) for(int y=0;y<128;y++) {
                    pos.setPos(cx*16+x,y,cz*16+z);
                    final Block block = chunk.getBlockState(pos).getBlock();
                    if (!"modernmetals".equals(block.getRegistryName().getResourceDomain()) || !block.getRegistryName().getResourcePath().endsWith("_ore")) continue;
                    final String name = block.getRegistryName().getResourcePath();
                    check(dimension==0 || dimension==42,"Modern Metals ore generated in excluded dimension: "+dimension+":"+name,failures);
                    final JsonObject rule = ores.getAsJsonObject("modernmetals:legacy/"+name);
                    check(rule != null && rule.get("enabled").getAsBoolean(),"Disabled or undeclared ore generated: "+name,failures);
                    if (rule != null && rule.has("dimension_selectors")) {
                        final JsonObject placement=rule.getAsJsonObject("dimension_selectors").getAsJsonObject("orespawn:all_except_nether_end");
                        if(placement!=null) check(y>=placement.get("min_y").getAsInt() && y<=placement.get("max_y").getAsInt(),"Ore outside height: "+name,failures);
                    }
                    counts.addProperty(name,counts.has(name)?counts.get(name).getAsInt()+1:1);
                }
            }
            if((dimension==0 || dimension==42) && !Boolean.getBoolean("modern.noOres")) {
                for(final String name:new String[]{"boron_ore","beryllium_ore","thorium_ore","rutile_ore"}) {
                    final JsonObject rule=ores.getAsJsonObject("modernmetals:legacy/"+name);
                    if(rule!=null && rule.get("enabled").getAsBoolean()) check(counts.has(name),"No generated sample: "+name,failures);
                }
                check(counts.has("titanium_ore")==Boolean.getBoolean("modern.titanium"),"Titanium placement does not match its switch",failures);
            }
            result.add(String.valueOf(dimension),counts);
        }
        report.add("oreSamples",result);
    }
    private static void subset(final JsonElement expected, final JsonElement actual, final String path, final JsonArray failures) {
        if(actual==null) { check(false,"Missing "+path,failures); return; }
        if(expected.isJsonObject()) {
            if(!actual.isJsonObject()) { check(false,"Wrong type at "+path,failures); return; }
            for(final java.util.Map.Entry<String,JsonElement> entry:expected.getAsJsonObject().entrySet())
                subset(entry.getValue(),actual.getAsJsonObject().get(entry.getKey()),path+"/"+entry.getKey(),failures);
        } else check(expected.equals(actual),"Changed "+path+": expected "+expected+", found "+actual,failures);
    }

    private static void saved(final WorldServer world, final String phase, final JsonArray failures) {
        final BlockPos pos = new BlockPos(8, 200, 8);
        if ("first".equals(phase)) {
            world.setBlockState(pos, Blocks.CHEST.getDefaultState(), 2);
            final TileEntityChest chest = (TileEntityChest) world.getTileEntity(pos);
            int slot = 0;
            for (final String name : new String[] {"boron","beryllium","thorium","titanium"}) {
                final MMDMaterial material = Materials.getMaterialByName(name);
                if (material.isEmpty()) continue;
                final ItemStack stack = material.getItemStack(Names.INGOT, 3);
                stack.setStackDisplayName("Saved " + name);
                stack.getTagCompound().setString("ModernMetalsCheck", name);
                chest.setInventorySlotContents(slot++, stack);
            }
            if(Materials.hasMaterial("plutonium")) {
                final ItemStack tool=Materials.getMaterialByName("plutonium").getItemStack(Names.PICKAXE);
                tool.setItemDamage(13);tool.setStackDisplayName("Saved plutonium pickaxe");
                tool.getTagCompound().setString("ModernMetalsCheck","preserve equipment across content modes");
                chest.setInventorySlotContents(4,tool);
            }
        }
        final TileEntityChest chest = (TileEntityChest) world.getTileEntity(pos);
        check(chest != null, "Saved chest missing", failures);
        if (chest == null) return;
        int slot = 0;
        for (final String name : new String[] {"boron","beryllium","thorium","titanium"}) {
            final ItemStack stack = chest.getStackInSlot(slot++);
            check(stack != null && stack.stackSize == 3 && ("modernmetals:" + name + "_ingot").equals(stack.getItem().getRegistryName().toString())
                && ("Saved " + name).equals(stack.getDisplayName()) && name.equals(stack.getTagCompound().getString("ModernMetalsCheck")), "Saved stack changed: " + name, failures);
        }
        if(Materials.hasMaterial("plutonium")) {
            final ItemStack tool=chest.getStackInSlot(4);
            check(tool!=null && tool.getItem()==Materials.getMaterialByName("plutonium").getItem(Names.PICKAXE)
                && tool.getItemDamage()==13 && "Saved plutonium pickaxe".equals(tool.getDisplayName())
                && "preserve equipment across content modes".equals(tool.getTagCompound().getString("ModernMetalsCheck")),"Saved equipment changed in content mode",failures);
        }
        world.getChunkFromBlockCoords(pos).setChunkModified();
    }
    private static void check(final boolean pass, final String detail, final JsonArray failures) { if (!pass) failures.add(new JsonPrimitive(detail)); }
}

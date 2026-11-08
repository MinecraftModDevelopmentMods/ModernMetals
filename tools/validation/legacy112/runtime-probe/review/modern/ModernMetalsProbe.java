package review.modern;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mcmoddev.lib.registry.CrusherRecipeRegistry;
import com.mcmoddev.lib.registry.recipe.ICrusherRecipe;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import zone.moddev.mc.modernmetals.content.ContentPolicy;
import zone.moddev.mc.modernmetals.worldgen.ModernMetalsWorldgenProvider;
import zone.moddev.mc.orespawn.api.OreSpawnApi;

@Mod(modid = "modernmetalsreview", version = "1", dependencies = "required-after:modernmetals")
public final class ModernMetalsProbe {
    @Mod.EventHandler
    public void clientInit(final FMLInitializationEvent event) {
        if (event.getSide().isClient() && Boolean.getBoolean("review.client")) {
            ClientChecks.install();
        }
    }

    @Mod.EventHandler
    public void verify(final FMLServerStartedEvent event) throws Exception {
        final WorldServer world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
        final boolean low = Boolean.getBoolean("review.low");
        require(ContentPolicy.active().lowFantasy() == low, "Wrong active Fantasy Level");
        require(ContentPolicy.inheritsBaseMetals() == Boolean.getBoolean("review.inherited"),
                "Wrong Fantasy Level owner");
        if (Boolean.getBoolean("review.historical")) {
            final String[] expected = { "boron_ore", "titanium_ore", "aluminum_plate" };
            for (int x = 0; x < expected.length; x++) {
                final Block saved = world.getBlockState(new BlockPos(x, 180, 0)).getBlock();
                require(id(expected[x]).equals(saved.getRegistryName()), "Legacy block did not survive: " + expected[x]);
            }
        }

        final List<Block> ores = ForgeRegistries.BLOCKS.getValuesCollection().stream()
                .filter(block -> block.getRegistryName() != null
                        && block.getRegistryName().getNamespace().equals("modernmetals")
                        && block.getRegistryName().getPath().endsWith("_ore"))
                .collect(Collectors.toList());
        for (final Block ore : ores) {
            final ResourceLocation id = ore.getRegistryName();
            final String name = id.getPath().replaceFirst("_ore$", "");
            final ICrusherRecipe recipe = CrusherRecipeRegistry.getRecipeForInputItem(new ItemStack(ore));
            require(recipe != null && !recipe.getOutput().isEmpty()
                    && recipe.getOutput().getItem().getRegistryName().equals(id(name + "_powder")),
                    "Shared crusher cannot resolve " + id);
        }

        final boolean disabled = Boolean.getBoolean("review.boronDisabled");
        require(ForgeRegistries.BLOCKS.containsKey(id("boron_ore")) != disabled, "Boron toggle failed");
        for (final String name : new String[] { "beryllium", "thorium", "titanium" }) {
            final Fluid fluid = FluidRegistry.getFluid(name);
            require(fluid != null && fluid.getBlock() != null, "Missing molten " + name);
            require(!FluidUtil.getFilledBucket(new FluidStack(fluid, 1000)).isEmpty(), "Missing " + name + " bucket");
        }

        final Block molten = ForgeRegistries.BLOCKS.getValue(id("titanium"));
        final EntityPig pig = new EntityPig(world);
        final BlockPos position = world.getSpawnPoint().up(3);
        pig.setPosition(position.getX() + 0.5, position.getY(), position.getZ() + 0.5);
        final float health = pig.getHealth();
        molten.onEntityCollision(world, position, molten.getDefaultState(), pig);
        require(pig.getHealth() < health && pig.isBurning(), "Molten metal contact did not damage the entity");

        for (final String recipe : new String[] { "plutonium_pickaxe", "uranium_sword", "rutile_axe",
                "plutonium_fishingrod", "plutonium_horsearmor", "plutonium_pressureplate" }) {
            final IRecipe entry = ForgeRegistries.RECIPES.getValue(id(recipe));
            require(entry != null && entry.getRecipeOutput().isEmpty() == low, "Wrong policy for " + recipe);
        }
        final IRecipe titanium = ForgeRegistries.RECIPES.getValue(id("titanium_blend_base"));
        require(titanium != null && titanium.getRecipeOutput().getItem() == item("titanium_blend"),
                "Titanium blend recipe missing");
        require(FurnaceRecipes.instance().getSmeltingResult(new ItemStack(item("titanium_blend"))).getItem()
                == item("titanium_ingot"), "Titanium blend cannot be smelted");
        if (ContentPolicy.inheritsBaseMetals()) {
            for (final String material : new String[] { "stainlesssteel", "nichrome" }) {
                final IRecipe recipe = ForgeRegistries.RECIPES.getValue(id(material + "_blend_base"));
                require(recipe != null && recipe.getIngredients().stream()
                        .allMatch(ingredient -> ingredient.getMatchingStacks().length > 0),
                        "Missing alloy ingredients for " + material);
                require(FurnaceRecipes.instance().getSmeltingResult(new ItemStack(item(material + "_blend"))).getItem()
                        == item(material + "_ingot"), "Cannot smelt " + material);
            }
        }
        if (Loader.isModLoaded("tconstruct")) {
            TinkersChecks.verify(low);
        }
        for (final String alias : new String[] { "oreAluminum", "oreAluminium", "oreBauxite", "oreChrome" }) {
            final Item ore = item(alias.equals("oreChrome") ? "chromium_ore" : "aluminum_ore");
            require(OreDictionary.getOres(alias).stream().anyMatch(stack -> stack.getItem() == ore),
                    "Missing ore alias " + alias);
        }

        require(OreSpawnApi.isOreTakeoverActive("modernmetals"), "OreSpawn takeover is inactive");
        final JsonObject profile = OreSpawnApi.getActiveProfile(world.getMinecraftServer()).get().toJson();
        final Block titaniumOre = ForgeRegistries.BLOCKS.getValue(id("titanium_ore"));
        final Block rutileOre = ForgeRegistries.BLOCKS.getValue(id("rutile_ore"));
        final BlockPos.MutableBlockPos sample = new BlockPos.MutableBlockPos();
        final int centerX = world.getSpawnPoint().getX();
        final int centerZ = world.getSpawnPoint().getZ();
        int titaniumBlocks = 0;
        int rutileBlocks = 0;
        for (int x = centerX - 32; x < centerX + 32; x++) {
            for (int z = centerZ - 32; z < centerZ + 32; z++) {
                for (int y = 0; y <= 63; y++) {
                    final Block block = world.getBlockState(sample.setPos(x, y, z)).getBlock();
                    if (block == titaniumOre) titaniumBlocks++;
                    if (block == rutileOre) rutileBlocks++;
                }
            }
        }
        require((titaniumBlocks > 0) == Boolean.getBoolean("review.titanium"), "Wrong titanium ore placement");
        require(rutileBlocks > 0, "Rutile ore did not generate");
        final JsonObject report = new JsonObject();
        report.addProperty("ore_count", ores.size());
        report.addProperty("titanium_blocks", titaniumBlocks);
        report.addProperty("rutile_blocks", rutileBlocks);
        report.addProperty("low_fantasy", low);
        report.add("profile", profile);
        report.add("provider", ModernMetalsWorldgenProvider.build(ForgeRegistries.BLOCKS::containsKey).get().toJson());
        Files.write(Paths.get("modernmetals-review.json"), new GsonBuilder().setPrettyPrinting().create()
                .toJson(report).getBytes(StandardCharsets.UTF_8));
        world.getMinecraftServer().initiateShutdown();
    }

    private static Item item(final String path) {
        final Item item = ForgeRegistries.ITEMS.getValue(id(path));
        require(item != null, "Missing " + path);
        return item;
    }

    private static ResourceLocation id(final String path) {
        return new ResourceLocation("modernmetals", path);
    }

    private static void require(final boolean passes, final String message) {
        if (!passes) throw new IllegalStateException(message);
    }
}

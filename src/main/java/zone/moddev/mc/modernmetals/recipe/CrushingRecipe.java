package zone.moddev.mc.modernmetals.recipe;

import com.google.gson.JsonObject;

import zone.moddev.mc.modernmetals.ModernMetals;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.ModList;

/**
 * Crushing recipes supplied by Modern Metals and other data packs. The JSON serializer
 * accepts the same ingredient and result objects as Base Metals. When Base Metals is
 * loaded, the serializer keeps the recipe ID and loads it under the Base Metals type.
 */
@net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = ModernMetals.MOD_ID, bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD)
public final class CrushingRecipe implements IRecipe<IInventory> {
    public static final ResourceLocation TYPE_ID = new ResourceLocation(ModernMetals.MOD_ID, "crushing");
    public static final IRecipeType<CrushingRecipe> TYPE = IRecipeType.register("modernmetals:crushing");
    public static final IRecipeSerializer<IRecipe<?>> SERIALIZER = new Serializer();
    public static final IRecipeSerializer<LegacySmeltingRecipe> LEGACY_SMELTING_SERIALIZER =
            new LegacySmeltingRecipe.Serializer();
    public static final IRecipeSerializer<LegacyBlastingRecipe> LEGACY_BLASTING_SERIALIZER =
            new LegacyBlastingRecipe.Serializer();
    public static final IRecipeSerializer<PlateRepairRecipe> PLATE_REPAIR_SERIALIZER =
            new PlateRepairRecipe.Serializer();

    private final ResourceLocation id;
    private final Ingredient ingredient;
    private final ItemStack result;

    public CrushingRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result) {
        this.id = id;
        this.ingredient = ingredient;
        this.result = result.copy();
    }

    @net.minecraftforge.eventbus.api.SubscribeEvent
    public static void register(net.minecraftforge.event.RegistryEvent.Register<IRecipeSerializer<?>> event) {
        NonEmptyTagCondition.register();
        event.getRegistry().registerAll(SERIALIZER, LEGACY_SMELTING_SERIALIZER, LEGACY_BLASTING_SERIALIZER, PLATE_REPAIR_SERIALIZER, ContentCraftingRecipe.SERIALIZER);
    }

    @Override public boolean matches(IInventory inventory, World world) {
        return inventory.getSizeInventory() > 0 && ingredient.test(inventory.getStackInSlot(0));
    }
    @Override public ItemStack getCraftingResult(IInventory inventory) { return result.copy(); }
    @Override public boolean canFit(int width, int height) { return width * height >= 1; }
    @Override public ItemStack getRecipeOutput() { return result.copy(); }
    @Override public ResourceLocation getId() { return id; }
    @Override public IRecipeSerializer<?> getSerializer() { return SERIALIZER; }
    @Override public IRecipeType<CrushingRecipe> getType() { return TYPE; }
    @Override public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(ingredient);
        return ingredients;
    }
    @Override public boolean isDynamic() { return true; }

    public static final class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<IRecipe<?>> {
        private Serializer() { setRegistryName(TYPE_ID); }
        @Override public IRecipe read(ResourceLocation id, JsonObject json) {
            if (ModList.get().isLoaded("basemetals")) {
                // Base Metals tools and add-ons consume its recipe type. Preserve our recipe ID.
                JsonObject shared = new JsonObject();
                shared.addProperty("type", "basemetals:crushing");
                shared.add("ingredient", json.get("ingredient"));
                shared.add("result", json.get("result"));
                return RecipeManager.deserializeRecipe(id, shared);
            }
            Ingredient ingredient = Ingredient.deserialize(json.get("ingredient"));
            ItemStack result = CraftingHelper.getItemStack(json.getAsJsonObject("result"), true);
            return new CrushingRecipe(id, ingredient, result);
        }
        @Override public CrushingRecipe read(ResourceLocation id, PacketBuffer buffer) {
            return new CrushingRecipe(id, Ingredient.read(buffer), buffer.readItemStack());
        }
        @Override public void write(PacketBuffer buffer, IRecipe value) {
            CrushingRecipe recipe = (CrushingRecipe) value;
            recipe.ingredient.write(buffer);
            buffer.writeItemStack(recipe.result);
        }
    }
}

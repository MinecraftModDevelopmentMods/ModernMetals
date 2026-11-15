package zone.moddev.mc.modernmetals;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.neoforged.testframework.junit.EphemeralTestServerProvider;
import net.neoforged.fml.ModList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import zone.moddev.mc.lib.common.recipes.CrushingRecipe;
import zone.moddev.mc.modernmetals.content.ModContent;

/** Tests native recipe codecs and tags loaded by Minecraft, rather than just JSON shapes. */
@ExtendWith(EphemeralTestServerProvider.class)
class ProcessingIntegrationTest {
    private static ResourceLocation id(String name) { return ResourceLocation.fromNamespaceAndPath("modernmetals", name); }
    private static Item item(String name) { return BuiltInRegistries.ITEM.get(id(name)); }
    private static ItemStack stack(String name) { return new ItemStack(item(name)); }

    @Test void materialAndLegacyRegistrationsResolve(MinecraftServer server) {
        assertEquals(21, ModContent.materials().size());
        for (String name : ModContent.materials().keySet()) {
            assertNotSame(Items.AIR, item(name + "_ingot"));
            assertSame(item(name + "_molten_bucket"), item(name + "_bucket"));
            assertSame(BuiltInRegistries.BLOCK.get(id(name + "_molten")), BuiltInRegistries.BLOCK.get(id(name)));
            assertSame(BuiltInRegistries.FLUID.get(id(name + "_molten")), BuiltInRegistries.FLUID.get(id(name)));
        }
        for (String name : List.of("galvanizedsteel", "nichrome", "stainlesssteel")) {
            assertNotSame(Items.AIR, item(name + "_ore"));
        }
        assertNotSame(Items.AIR, item("coal_powder"));
    }

    @Test void everyNativeBookRecipeHasAnAdvancement(MinecraftServer server) {
        int count = 0;
        for (var holder : server.getRecipeManager().getRecipes()) {
            if (!holder.id().getNamespace().equals("modernmetals")) continue;
            var recipe = holder.value();
            if (recipe.isSpecial() || !(recipe.getType() == RecipeType.CRAFTING || recipe.getType() == RecipeType.SMELTING || recipe.getType() == RecipeType.BLASTING)) continue;
            var advancement = server.getAdvancements().get(id("recipes/" + holder.id().getPath()));
            assertNotNull(advancement, holder.id().toString());
            assertTrue(advancement.value().rewards().recipes().contains(holder.id()), holder.id().toString());
            count++;
        }
        assertTrue(count > 1600, "Loaded book recipe count " + count);
    }

    @Test void rawProcessingAndStorageHaveCorrectAmounts(MinecraftServer server) {
        int count = 0;
        for (String name : ModContent.materials().keySet()) {
            if (!BuiltInRegistries.ITEM.containsKey(id(name + "_raw"))) continue;
            var raw = stack(name + "_raw");
            var crushed = server.getRecipeManager().getAllRecipesFor(CrushingRecipe.TYPE.get()).stream()
                    .filter(r -> r.value().matches(new SingleRecipeInput(raw), null)).toList();
            assertEquals(1, crushed.size(), name);
            assertEquals(1, crushed.getFirst().value().getResultItem(server.registryAccess()).getCount());
            assertSame(item(name + "_powder"), crushed.getFirst().value().getResultItem(server.registryAccess()).getItem());
            for (String method : List.of("smelting", "blasting")) {
                var recipe = server.getRecipeManager().byKey(id(name + "_raw_" + method)).orElseThrow().value();
                assertSame(item(name + "_ingot"), recipe.getResultItem(server.registryAccess()).getItem());
                assertEquals(1, recipe.getResultItem(server.registryAccess()).getCount());
            }
            var packed = server.getRecipeManager().byKey(id(name + "_raw_block_unpack")).orElseThrow().value();
            assertEquals(9, packed.getResultItem(server.registryAccess()).getCount());
            count++;
        }
        assertEquals(17, count);
    }

    @Test void rodsYieldFourAndRecycleWithoutGain(MinecraftServer server) {
        var rod = server.getRecipeManager().byKey(id("aluminum_rod")).orElseThrow().value();
        assertEquals(4, rod.getResultItem(server.registryAccess()).getCount());
        var recycled = server.getRecipeManager().byKey(id("smelting/aluminum_rod_recycling")).orElseThrow().value().getResultItem(server.registryAccess());
        assertSame(item("aluminum_nugget"), recycled.getItem());
        assertEquals(4, recycled.getCount());
    }

    @Test void plateRepairPreservesComponents(MinecraftServer server) {
        var recipe = server.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING).stream()
                .filter(r -> r.id().equals(id("titanium_helmet_plate_repair"))).findFirst().orElseThrow().value();
        ItemStack helmet = stack("titanium_helmet");
        helmet.setDamageValue(31);
        helmet.set(DataComponents.CUSTOM_NAME, Component.literal("Saved test helmet"));
        var input = CraftingInput.of(2, 1, List.of(helmet, stack("titanium_plate")));
        assertTrue(recipe.matches(input, null));
        var result = recipe.assemble(input, server.registryAccess());
        assertEquals(0, result.getDamageValue());
        assertEquals(helmet.get(DataComponents.CUSTOM_NAME), result.get(DataComponents.CUSTOM_NAME));
        assertEquals(31, helmet.getDamageValue(), "Input must not be mutated");
        assertFalse(recipe.matches(CraftingInput.of(2, 1, List.of(helmet, stack("aluminum_plate"))), null));
    }

    @Test void coalFallbackUsesTheSharedRecipeType(MinecraftServer server) {
        var recipes = server.getRecipeManager().getAllRecipesFor(CrushingRecipe.TYPE.get()).stream()
                .filter(r -> r.value().matches(new SingleRecipeInput(new ItemStack(Items.COAL)), null)).toList();
        assertEquals(1, recipes.size());
        boolean base = ModList.get().isLoaded("basemetals");
        assertEquals(base ? "basemetals" : "modernmetals", recipes.getFirst().id().getNamespace());
        assertEquals(base ? "basemetals" : "modernmetals", BuiltInRegistries.ITEM.getKey(recipes.getFirst().value().getResultItem(server.registryAccess()).getItem()).getNamespace());
        assertEquals(!base, server.getRecipeManager().byKey(id("coal_block_dust")).isPresent());
    }

    @Test void optionalAlloysFollowResolvedContributorTags(MinecraftServer server) {
        boolean contributors = Boolean.getBoolean("modernmetals.test.contributors");
        boolean base = ModList.get().isLoaded("basemetals");
        for (String alloy : List.of("aluminumbrass", "galvanizedsteel", "nichrome", "stainlesssteel")) {
            assertEquals(contributors || base, server.getRecipeManager().byKey(id(alloy + "_blend_base")).isPresent(), alloy);
        }
        if (!contributors) return;
        var galvanized = server.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING).stream()
                .filter(r -> r.id().equals(id("galvanizedsteel_blend_base"))).findFirst().orElseThrow().value();
        assertTrue(galvanized.matches(CraftingInput.of(2,1,List.of(new ItemStack(Items.IRON_INGOT),new ItemStack(Items.GOLD_NUGGET))), null));
        assertEquals(2, galvanized.getResultItem(server.registryAccess()).getCount());
        var stainless = server.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING).stream()
                .filter(r -> r.id().equals(id("stainlesssteel_blend_base"))).findFirst().orElseThrow().value();
        assertTrue(stainless.matches(CraftingInput.of(2,1,List.of(new ItemStack(Items.IRON_INGOT),new ItemStack(Items.DIAMOND))), null));
    }

    @Test void nativeAlloyIngredientsAndYieldsMatchPublishedBaseMetals(MinecraftServer server) {
        assertBlend(server,"titanium",1,List.of(stack("rutile_powder"),stack("magnesium_powder")));
        if(!ModList.get().isLoaded("basemetals"))return;
        var steel=new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse("basemetals:steel_blend")));
        var zinc=new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse("basemetals:zinc_powder")));
        var nickel=new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse("basemetals:nickel_powder")));
        var copper=new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse("basemetals:copper_powder")));
        for(var ingredient:List.of(steel,zinc,nickel,copper))assertFalse(ingredient.isEmpty(),"Published Base Metals ingredient exists");
        assertBlend(server,"galvanizedsteel",2,List.of(steel,zinc));
        assertBlend(server,"stainlesssteel",2,List.of(steel,stack("chromium_powder")));
        assertBlend(server,"nichrome",2,List.of(nickel,stack("chromium_powder")));
        assertBlend(server,"aluminumbrass",4,List.of(stack("aluminum_powder"),stack("aluminum_powder"),stack("aluminum_powder"),copper));
    }

    private static void assertBlend(MinecraftServer server,String alloy,int count,List<ItemStack> ingredients) {
        var recipe=server.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING).stream()
                .filter(r->r.id().equals(id(alloy+"_blend_base"))).findFirst().orElseThrow().value();
        assertTrue(recipe.matches(CraftingInput.of(2,ingredients.size()/2,ingredients),null),alloy+" ingredient match");
        assertEquals(count,recipe.getResultItem(server.registryAccess()).getCount(),alloy+" yield");
        assertSame(item(alloy+"_blend"),recipe.getResultItem(server.registryAccess()).getItem());
    }

}

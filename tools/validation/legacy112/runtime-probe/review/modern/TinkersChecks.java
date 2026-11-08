package review.modern;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.library.tools.IToolPart;

final class TinkersChecks {
    static void verify(final boolean low) {
        final Fluid titanium = FluidRegistry.getFluid("titanium");
        final Material material = TinkerRegistry.getMaterial("titanium");
        require(material != Material.UNKNOWN && material.getFluid() == titanium, "Titanium tool material is missing");
        final ItemStack ingot = new ItemStack(ForgeRegistries.ITEMS.getValue(
                new ResourceLocation("modernmetals", "titanium_ingot")));
        require(TinkerRegistry.getMelting(ingot) != null, "Titanium ingot cannot be melted");
        require(TinkerRegistry.getAlloys().stream().anyMatch(alloy ->
                alloy.getResult().getFluid() == titanium && alloy.getFluids().size() == 2
                && alloy.getFluids().stream().anyMatch(fluid -> "rutile".equals(fluid.getFluid().getName()))
                && alloy.getFluids().stream().anyMatch(fluid -> "magnesium".equals(fluid.getFluid().getName()))),
                "Missing titanium smeltery alloy");
        require(TinkerRegistry.getAllTableCastingRecipes().stream().filter(CastingRecipe.class::isInstance)
                .map(CastingRecipe.class::cast).map(CastingRecipe::getResult)
                .anyMatch(stack -> ItemStack.areItemsEqual(stack, ingot)), "Titanium ingot cannot be cast");
        final boolean plutoniumParts = TinkerRegistry.getAllTableCastingRecipes().stream()
                .filter(CastingRecipe.class::isInstance).map(CastingRecipe.class::cast)
                .map(CastingRecipe::getResult)
                .anyMatch(stack -> stack.getItem() instanceof IToolPart
                        && "plutonium".equals(((IToolPart) stack.getItem()).getMaterialID(stack)));
        require(plutoniumParts != low, "Wrong Low Fantasy policy for plutonium tool parts");
    }

    private static void require(final boolean passes, final String message) {
        if (!passes) throw new IllegalStateException(message);
    }

    private TinkersChecks() { }
}

package zone.moddev.mc.modernmetals.init;

import zone.moddev.mc.modernmetals.content.ContentPolicy;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

/** Disables restricted recipes without removing their saved registry names. */
final class PolicyRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	private final IRecipe delegate;
	private final ContentPolicy policy;
	private final boolean allowed;

	PolicyRecipe(final IRecipe delegate, final ContentPolicy policy) {
		this.delegate = delegate;

		this.policy = policy;
		this.allowed = policy.allows(delegate.getRecipeOutput());
		setRegistryName(delegate.getRegistryName());
	}

	@Override
	public boolean matches(final InventoryCrafting inventory, final World world) {
		return allowed && delegate.matches(inventory, world) && policy.allows(delegate.getCraftingResult(inventory));
	}

	@Override
	public ItemStack getCraftingResult(final InventoryCrafting inventory) {
		if (!allowed) return ItemStack.EMPTY;
        final ItemStack result = delegate.getCraftingResult(inventory);
        return policy.allows(result) ? result : ItemStack.EMPTY;
	}

	@Override
	public boolean canFit(final int width, final int height) {
		return allowed && delegate.canFit(width, height);
	}

	@Override
	public ItemStack getRecipeOutput() {
		return allowed ? delegate.getRecipeOutput() : ItemStack.EMPTY;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(final InventoryCrafting inventory) {
		return delegate.getRemainingItems(inventory);
	}

	@Override
	public NonNullList<net.minecraft.item.crafting.Ingredient> getIngredients() {
		return delegate.getIngredients();
	}

	@Override
	public boolean isDynamic() {
		return !allowed || delegate.isDynamic();
	}

	@Override
	public String getGroup() {
		return delegate.getGroup();
	}
}

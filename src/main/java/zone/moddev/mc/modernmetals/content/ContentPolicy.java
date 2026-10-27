package zone.moddev.mc.modernmetals.content;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.ResourceLocation;
import zone.moddev.mc.modernmetals.ModernMetals;

/** Restricts obtainability, while retaining every registered item and saved stack. */
public final class ContentPolicy {
    private static final Set<String> TOOLS = names("axe","crackhammer","hoe","pickaxe","scythe","shears","shovel","sword","arrow","bolt");
    private static final Set<String> PROTECTION = names("boots","chestplate","helmet","leggings","horse_armor","shield");
    private static final Set<String> FULL_TOOLS = names("galvanizedsteel","stainlesssteel","titanium");
    private static final Set<String> ARMOR = names("aluminum","aluminumbrass","galvanizedsteel","stainlesssteel","titanium");
    private static final Set<String> GEARS = names("aluminum","aluminumbrass","galvanizedsteel","stainlesssteel","titanium");
    private static final Set<String> STRUCTURAL_FORMS = names("bars","bookshelf","button","door","double_slab","fence","fence_gate","flower_pot","ladder","lever","pressure_plate","slab","stairs","trapdoor","tripwire_hook","wall");
    private static final Set<String> STRUCTURAL_METALS = names("aluminum","aluminumbrass","galvanizedsteel","nichrome","stainlesssteel","tantalum","titanium","tungsten","zirconium");
    private static boolean initialized;
    private static ContentPolicy active = forMode("high_fantasy");
    private final boolean low;
    private ContentPolicy(final boolean low) { this.low=low; }
    public static ContentPolicy forMode(final String mode) {
        return new ContentPolicy(mode!=null && "low_fantasy".equals(mode.trim().toLowerCase(Locale.ROOT)));
    }
    public static ContentPolicy active() { return active; }
    public boolean lowFantasy() { return low; }
    /** Latch Modern Metals' own startup choice until the next full game restart. */
    public static void initialize(final String mode) {
        if(initialized) return;
        active=forMode(mode);initialized=true;
        ModernMetals.logger.info("Modern Metals content mode: %s (fixed until restart)",active.low?"low_fantasy":"high_fantasy");
    }
    public boolean allows(final String material,final String form) {
        if(!low || material==null || form==null) return true;
        if(TOOLS.contains(form)) return FULL_TOOLS.contains(material);
        if(PROTECTION.contains(form)) return ARMOR.contains(material);
        if("bow".equals(form) || "crossbow".equals(form) || "fishing_rod".equals(form)) return false;
        if("gear".equals(form)) return GEARS.contains(material);
        if("anvil".equals(form)) return "stainlesssteel".equals(material) || "galvanizedsteel".equals(material);
        if(STRUCTURAL_FORMS.contains(form)) return STRUCTURAL_METALS.contains(material);
        return true;
    }
    public boolean allows(final ItemStack stack) {
        if(!low || stack==null || stack.getItem()==null) return true;
        final ResourceLocation id=stack.getItem().getRegistryName();
        if(id==null || !ModernMetals.MODID.equals(id.getResourceDomain())) return true;
        final String path=id.getResourcePath().startsWith("double_") ? id.getResourcePath().substring(7) : id.getResourcePath();
        final int split=path.indexOf('_');
        return split<0 || allows(path.substring(0,split),path.substring(split+1));
    }
    /** Called after all mods have registered their ordinary crafting recipes. */
    public static void filterRecipes() {
        if(!active.low) return;
        final int before=CraftingManager.getInstance().getRecipeList().size();
        CraftingManager.getInstance().getRecipeList().removeIf(recipe->!active.allows(recipe.getRecipeOutput()));
        final java.util.List<net.minecraft.item.crafting.IRecipe> recipes=CraftingManager.getInstance().getRecipeList();
        for(int i=0;i<recipes.size();i++) {
            final net.minecraft.item.crafting.IRecipe recipe=recipes.get(i);
            if(recipe.getClass().getName().startsWith("com.mcmoddev.lib.recipe.")) recipes.set(i,new RestrictedRecipe(recipe));
        }
        ModernMetals.logger.info("Low Fantasy removed %s Modern Metals equipment recipes",before-CraftingManager.getInstance().getRecipeList().size());
    }
    /** Dynamic shared-library repair/upgrade recipes must apply the same output policy. */
    private static final class RestrictedRecipe implements net.minecraft.item.crafting.IRecipe {
        private final net.minecraft.item.crafting.IRecipe delegate;
        RestrictedRecipe(final net.minecraft.item.crafting.IRecipe delegate) {this.delegate=delegate;}
        @Override public boolean matches(final net.minecraft.inventory.InventoryCrafting grid,final net.minecraft.world.World world) {
            return delegate.matches(grid,world) && active.allows(delegate.getCraftingResult(grid));
        }
        @Override public ItemStack getCraftingResult(final net.minecraft.inventory.InventoryCrafting grid) {
            final ItemStack result=delegate.getCraftingResult(grid);return active.allows(result)?result:null;
        }
        @Override public int getRecipeSize() {return delegate.getRecipeSize();}
        @Override public ItemStack getRecipeOutput() {return delegate.getRecipeOutput();}
        @Override public ItemStack[] getRemainingItems(final net.minecraft.inventory.InventoryCrafting grid) {return delegate.getRemainingItems(grid);}
    }
    private static Set<String> names(final String...values) {return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(values)));}
}

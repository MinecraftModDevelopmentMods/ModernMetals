package zone.moddev.mc.modernmetals.content;

import java.util.function.Supplier;

import zone.moddev.mc.modernmetals.material.MaterialDefinition;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public final class MaterialTier implements IItemTier {
    private final MaterialDefinition material;
    private final Supplier<Ingredient> repair;

    public MaterialTier(MaterialDefinition material) {
        this.material = material;
        final ItemTags.Wrapper tag = new ItemTags.Wrapper(new ResourceLocation(material.repairIngredientTag()));
        this.repair = () -> Ingredient.fromTag(tag);
    }

    public MaterialDefinition material() { return material; }
    @Override public int getMaxUses() { return material.toolDurability(); }
    @Override public float getEfficiency() { return material.toolEfficiency(); }
    @Override public float getAttackDamage() { return material.baseAttackDamage(); }
    @Override public int getHarvestLevel() { return material.toolLevel(); }
    @Override public int getEnchantability() { return material.enchantability(); }
    @Override public Ingredient getRepairMaterial() { return repair.get(); }
}

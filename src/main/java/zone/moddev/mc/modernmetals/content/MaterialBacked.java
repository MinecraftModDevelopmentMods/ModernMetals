package zone.moddev.mc.modernmetals.content;

import zone.moddev.mc.modernmetals.material.MaterialDefinition;

import net.minecraft.util.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.item.ItemStack;

public interface MaterialBacked {
    MaterialDefinition baseMetalsMaterial();

    default boolean isMaterialRepairIngredient(ItemStack repair) {
        return new ItemTags.Wrapper(new ResourceLocation(
                baseMetalsMaterial().repairIngredientTag())).contains(repair.getItem());
    }
}

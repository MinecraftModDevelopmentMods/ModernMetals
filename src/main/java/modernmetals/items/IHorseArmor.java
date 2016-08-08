package modernmetals.items;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.HorseType;
import net.minecraft.item.ItemStack;

public interface IHorseArmor {
    HorseType getArmorType();

    String getArmorTexture(EntityHorse entity, ItemStack stack);
}

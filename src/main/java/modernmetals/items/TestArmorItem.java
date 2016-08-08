package modernmetals.items;

import modernmetals.utils.HorseUtilities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.HorseType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TestArmorItem extends Item implements IHorseArmor {
    public TestArmorItem() {
        this.setUnlocalizedName("test_armor");
        this.setRegistryName(new ResourceLocation("modernmetals", "test_armor"));
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setMaxStackSize(1);
    }

    @Override
    public HorseType getArmorType() {
        return HorseUtilities.getType("test", 1024);
    }

    @Override
    public String getArmorTexture(EntityHorse entity, ItemStack stack) {
        return "modernmetals:textures/items/armor_test.png";
    }
}

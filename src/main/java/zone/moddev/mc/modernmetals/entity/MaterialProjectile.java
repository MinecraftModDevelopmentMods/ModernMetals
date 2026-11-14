package zone.moddev.mc.modernmetals.entity;

import zone.moddev.mc.modernmetals.content.ModContent;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public final class MaterialProjectile extends EntityArrow implements IEntityAdditionalSpawnData {
    private ItemStack ammunition = ItemStack.EMPTY;

    public MaterialProjectile(EntityType<?> type, World world) {
        super(type, world);
    }

    public MaterialProjectile(EntityType<?> type, World world, EntityLivingBase shooter, ItemStack ammunition) {
        super(type, shooter, world);
        this.ammunition = single(ammunition);
    }

    private static ItemStack single(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.setCount(1);
        return copy;
    }

    @Override
    protected ItemStack getArrowStack() {
        return ammunition.isEmpty() ? new ItemStack(ModContent.item("aluminum_arrow").get()) : ammunition.copy();
    }

    public ItemStack getAmmunition() {
        return getArrowStack();
    }

    @Override
    public void writeAdditional(NBTTagCompound tag) {
        super.writeAdditional(tag);
        if (!ammunition.isEmpty()) tag.setTag("Ammunition", ammunition.write(new NBTTagCompound()));
    }

    @Override
    public void readAdditional(NBTTagCompound tag) {
        super.readAdditional(tag);
        ammunition = tag.contains("Ammunition", 10)
                ? ItemStack.read(tag.getCompound("Ammunition")) : ItemStack.EMPTY;
    }

    @Override public void writeSpawnData(PacketBuffer buffer) { buffer.writeItemStack(ammunition); }
    @Override public void readSpawnData(PacketBuffer buffer) { ammunition = buffer.readItemStack(); }
}

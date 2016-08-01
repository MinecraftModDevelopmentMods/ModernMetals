package cyano.basemetals.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import modernmetals.init.Items;

public class EntityCustomArrow extends EntityTippedArrow {
	private ItemStack itemStack;

	public EntityCustomArrow (World worldIn) {
		super(worldIn);
	}

	public EntityCustomArrow (World worldIn, ItemStack stack, EntityPlayer shooter) {
		super(worldIn, shooter);
		this.itemStack = stack;
	}

	@Override
	protected ItemStack getArrowStack () {
		if(itemStack == null)
			itemStack = new ItemStack(Items.aluminum_arrow);

		return new ItemStack(itemStack.getItem(), 1, itemStack.getItemDamage());
	}

	@Override
	public void writeToNBT (NBTTagCompound compound) {
		super.writeToNBT(compound);

		NBTTagCompound itemStackCompound = new NBTTagCompound();
		itemStack.writeToNBT(itemStackCompound);

		compound.setTag("itemstack", itemStackCompound);
	}

	@Override
	public void readFromNBT (NBTTagCompound compound) {
		super.readFromNBT(compound);

		itemStack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("itemstack"));
	}
}

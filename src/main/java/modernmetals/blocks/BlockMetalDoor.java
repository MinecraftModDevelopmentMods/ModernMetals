package modernmetals.blocks;

import java.util.Random;

import cyano.basemetals.material.MetalMaterial;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoor.EnumDoorHalf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMetalDoor extends cyano.basemetals.blocks.BlockMetalDoor {

	public BlockMetalDoor(MetalMaterial metal) {
		super(metal);
		// TODO Auto-generated constructor stub
	}

	@SideOnly(Side.CLIENT)
    @Override
    public ItemStack getItem(final World w, final BlockPos c, final IBlockState bs) {
        return new ItemStack(modernmetals.init.Items.getDoorItemForBlock(this));
    }

	@Override
	public Item getItemDropped(final IBlockState bs, final Random prng, final int i) {
		return (bs.getValue(BlockDoor.HALF) == EnumDoorHalf.UPPER) ? null : modernmetals.init.Items.getDoorItemForBlock(this);
	}

}

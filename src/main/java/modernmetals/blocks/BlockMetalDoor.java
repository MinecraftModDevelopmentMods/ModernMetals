package modernmetals.blocks;

import cyano.basemetals.material.MetalMaterial;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Door Block
 * @author Jasmine Iwanek
 *
 */
public class BlockMetalDoor extends cyano.basemetals.blocks.BlockMetalDoor {

	/**
	 *
	 * @param metal
	 */
	public BlockMetalDoor(MetalMaterial metal) {
		super(metal);
	}

	@SideOnly(Side.CLIENT)
	@Override
    public Item getItem(final World w, final BlockPos c) {
        return modernmetals.init.Items.getDoorItemForBlock(this);
	}

	@Override
	public Item getItemDropped(final IBlockState bs, final Random prng, final int i) {
		return (bs.getValue(BlockDoor.HALF) == EnumDoorHalf.UPPER) ? null : modernmetals.init.Items.getDoorItemForBlock(this);
	}
}

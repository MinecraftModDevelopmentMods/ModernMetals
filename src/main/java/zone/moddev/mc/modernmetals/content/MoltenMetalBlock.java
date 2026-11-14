package zone.moddev.mc.modernmetals.content;

import net.minecraft.block.BlockFlowingFluid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class MoltenMetalBlock extends BlockFlowingFluid {
    public MoltenMetalBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public void onEntityCollision(IBlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (!world.isRemote && entity instanceof EntityLivingBase && !entity.isImmuneToFire()
                && !entity.isInLava()
                && entity.getBoundingBox().minY < pos.getY() + state.getFluidState().getHeight()) {
            entity.attackEntityFrom(DamageSource.LAVA, 4.0F);
            entity.setFire(15);
        }
    }
}

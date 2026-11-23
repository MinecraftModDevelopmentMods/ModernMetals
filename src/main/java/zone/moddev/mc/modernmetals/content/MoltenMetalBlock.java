package zone.moddev.mc.modernmetals.content;

import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class MoltenMetalBlock extends FlowingFluidBlock {
    public MoltenMetalBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (!world.isRemote && entity instanceof LivingEntity && !entity.isImmuneToFire()
                && !entity.isInLava()
                && entity.getBoundingBox().minY < pos.getY() + state.getFluidState().func_215679_a(world, pos)) {
            entity.attackEntityFrom(DamageSource.LAVA, 4.0F);
            entity.setFire(15);
        }
    }
}

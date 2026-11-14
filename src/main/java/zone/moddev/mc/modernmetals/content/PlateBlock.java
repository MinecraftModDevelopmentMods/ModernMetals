package zone.moddev.mc.modernmetals.content;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

/** A one-pixel-thick plate attached flush to the selected block face. */
public final class PlateBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    private static final VoxelShape[] SHAPES = {
            makeCuboidShape(0, 15, 0, 16, 16, 16), makeCuboidShape(0, 0, 0, 16, 1, 16),
            makeCuboidShape(0, 0, 15, 16, 16, 16), makeCuboidShape(0, 0, 0, 16, 16, 1),
            makeCuboidShape(15, 0, 0, 16, 16, 16), makeCuboidShape(0, 0, 0, 1, 16, 16)};
    private final int harvestLevel;

    public PlateBlock(Properties properties, int harvestLevel) {
        super(properties);
        this.harvestLevel = harvestLevel;
        setDefaultState(stateContainer.getBaseState().with(FACING, EnumFacing.NORTH));
    }

    @Override
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState().with(FACING, context.getFace());
    }

    @Override public VoxelShape getShape(IBlockState state, IBlockReader world, BlockPos pos) {
        return SHAPES[state.get(FACING).getIndex()];
    }
    @Override public IBlockState rotate(IBlockState state, Rotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }
    @Override public IBlockState mirror(IBlockState state, Mirror mirror) {
        return rotate(state, mirror.toRotation(state.get(FACING)));
    }
    @Override protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(FACING);
    }
    @Override public ToolType getHarvestTool(IBlockState state) { return ToolType.PICKAXE; }
    @Override public int getHarvestLevel(IBlockState state) { return harvestLevel; }
}

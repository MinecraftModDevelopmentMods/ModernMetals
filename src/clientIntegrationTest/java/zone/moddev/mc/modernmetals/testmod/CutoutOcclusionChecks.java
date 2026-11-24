package zone.moddev.mc.modernmetals.testmod;

import com.google.gson.GsonBuilder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.fluid.IFluidState;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.registries.ForgeRegistries;

/** Exercise the terrain-face culling path, independently of texture/model checks. */
final class CutoutOcclusionChecks {
    private static final class Neighbours implements IBlockReader {
        private final BlockState center;
        Neighbours(BlockState center) { this.center = center; }
        @Override public TileEntity getTileEntity(BlockPos pos) { return null; }
        @Override public BlockState getBlockState(BlockPos pos) {
            return pos.equals(BlockPos.ZERO) ? center : Blocks.STONE.getDefaultState();
        }
        @Override public IFluidState getFluidState(BlockPos pos) { return getBlockState(pos).getFluidState(); }
    }

    static void run() throws Exception {
        Map<String, Object> results = new LinkedHashMap<>();
        int totalStates = 0;
        int totalHiddenFaces = 0;
        int failedBlocks = 0;
        for (Block block : ForgeRegistries.BLOCKS.getValues()) {
            String namespace = block.getRegistryName().getNamespace();
            if (!namespace.equals("modernmetals") && !namespace.equals("basemetals")
                    && !namespace.equals("basegems") && !namespace.equals("baseminerals")) continue;
            Block reference = block instanceof DoorBlock ? Blocks.IRON_DOOR
                    : block instanceof TrapDoorBlock ? Blocks.IRON_TRAPDOOR
                    : block instanceof PaneBlock ? Blocks.IRON_BARS : null;
            if (reference == null) continue;
            int states = 0;
            int hiddenFaces = 0;
            int solidStates = 0;
            int collisionMismatches = 0;
            for (BlockState state : block.getStateContainer().getValidStates()) {
                states++;
                if (state.isSolid()) solidStates++;
                Neighbours reader = new Neighbours(state);
                for (Direction side : Direction.values()) {
                    BlockPos stone = BlockPos.ZERO.offset(side.getOpposite());
                    if (!Block.shouldSideBeRendered(Blocks.STONE.getDefaultState(), reader, stone, side)) hiddenFaces++;
                }
                BlockState vanilla = copyProperties(state, reference.getDefaultState());
                if (VoxelShapes.compare(state.getCollisionShape(reader, BlockPos.ZERO),
                        vanilla.getCollisionShape(new Neighbours(vanilla), BlockPos.ZERO), IBooleanFunction.NOT_SAME)) {
                    collisionMismatches++;
                }
            }
            boolean cutout = RenderTypeLookup.canRenderInLayer(block.getDefaultState(), RenderType.getCutoutMipped());
            boolean passed = cutout && solidStates == 0 && hiddenFaces == 0 && collisionMismatches == 0;
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("states", states);
            item.put("cutoutLayer", cutout);
            item.put("solidStates", solidStates);
            item.put("hiddenNeighbourFaces", hiddenFaces);
            item.put("collisionMismatches", collisionMismatches);
            item.put("passed", passed);
            results.put(block.getRegistryName().toString(), item);
            totalStates += states;
            totalHiddenFaces += hiddenFaces;
            if (!passed) failedBlocks++;
        }
        if (results.isEmpty()) throw new IllegalStateException("No cutout blocks checked");
        Map<String, Object> report = new LinkedHashMap<>();
        report.put("blocks", results.size());
        report.put("states", totalStates);
        report.put("neighbourFaceChecks", totalStates * Direction.values().length);
        report.put("hiddenNeighbourFaces", totalHiddenFaces);
        report.put("failedBlocks", failedBlocks);
        report.put("passed", failedBlocks == 0);
        report.put("results", results);
        Files.write(Paths.get("cutout-occlusion.json"), new GsonBuilder().setPrettyPrinting().create()
                .toJson(report).getBytes(StandardCharsets.UTF_8));
        System.out.println("CUTOUT_OCCLUSION " + (failedBlocks == 0 ? "PASS" : "FAIL")
                + " blocks=" + results.size() + " states=" + totalStates
                + " hiddenFaces=" + totalHiddenFaces + " failedBlocks=" + failedBlocks);
        if (failedBlocks != 0) throw new IllegalStateException("Cutout blocks hide terrain: " + failedBlocks
                + " blocks, " + totalHiddenFaces + " hidden neighbour faces; see cutout-occlusion.json");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static BlockState copyProperties(BlockState from, BlockState to) {
        for (IProperty property : to.getProperties()) {
            IProperty source = from.getBlock().getStateContainer().getProperty(property.getName());
            if (source != null) to = to.with(property, from.get(source));
        }
        return to;
    }
}

package zone.moddev.mc.modernmetals.content;

import net.minecraft.block.BlockFlowingFluid;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.Item;

public final class FluidContent {
    private final RegistryHandle<FlowingFluid> source;
    private final RegistryHandle<FlowingFluid> flowing;
    private final RegistryHandle<BlockFlowingFluid> block;
    private final RegistryHandle<Item> bucket;

    public FluidContent(RegistryHandle<FlowingFluid> source, RegistryHandle<FlowingFluid> flowing,
            RegistryHandle<BlockFlowingFluid> block, RegistryHandle<Item> bucket) {
        this.source = source;
        this.flowing = flowing;
        this.block = block;
        this.bucket = bucket;
    }

    public RegistryHandle<FlowingFluid> source() { return source; }
    public RegistryHandle<FlowingFluid> flowing() { return flowing; }
    public RegistryHandle<BlockFlowingFluid> block() { return block; }
    public RegistryHandle<Item> bucket() { return bucket; }
}

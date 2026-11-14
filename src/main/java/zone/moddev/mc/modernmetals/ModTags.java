package zone.moddev.mc.modernmetals;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public final class ModTags {
    public static final Tag<Block> SCYTHE_HARVESTABLE = new BlockTags.Wrapper(
            new ResourceLocation(ModernMetals.MOD_ID, "scythe_harvestable"));
    public static final Tag<Block> CRACKHAMMER_CRUSHABLE = new BlockTags.Wrapper(
            new ResourceLocation(ModernMetals.MOD_ID, "crackhammer_crushable"));

    private ModTags() {}
}

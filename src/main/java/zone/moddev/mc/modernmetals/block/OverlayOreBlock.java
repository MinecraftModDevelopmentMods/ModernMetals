package zone.moddev.mc.modernmetals.block;

import com.mcmoddev.lib.block.BlockMMDOre;
import com.mcmoddev.lib.material.MMDMaterial;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** The shared ore block with alpha testing enabled for its model's outer faces. */
public final class OverlayOreBlock extends BlockMMDOre {
    public OverlayOreBlock(MMDMaterial material) {
        super(material);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}

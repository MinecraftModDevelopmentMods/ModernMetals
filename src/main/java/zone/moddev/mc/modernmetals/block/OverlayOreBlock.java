package zone.moddev.mc.modernmetals.block;

import com.mcmoddev.lib.block.BlockMMDOre;
import com.mcmoddev.lib.material.MMDMaterial;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Lets the stone show through the ore texture's transparent pixels. */
public final class OverlayOreBlock extends BlockMMDOre {
    public OverlayOreBlock(final MMDMaterial material) {
        super(material);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}

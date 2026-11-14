package zone.moddev.mc.modernmetals.content;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;

/** A dedicated filled bucket; Forge 25 has no universal bucket replacement. */
public final class MetalBucketItem extends ItemBucket {
    public MetalBucketItem(Fluid fluid, Item.Properties properties) {
        super(fluid, properties);
    }
}

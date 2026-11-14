package zone.moddev.mc.modernmetals.content;

import java.util.function.Supplier;

import zone.moddev.mc.modernmetals.entity.MaterialProjectile;
import zone.moddev.mc.modernmetals.material.MaterialDefinition;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public final class MetalAmmoItem extends ItemArrow implements MaterialBacked {
    public enum Kind { ARROW, BOLT }
    private final MaterialDefinition material;
    private final Kind kind;
    private final Supplier<EntityType<MaterialProjectile>> entityType;

    public MetalAmmoItem(MaterialDefinition material, Kind kind,
            Supplier<EntityType<MaterialProjectile>> entityType, Item.Properties properties) {
        super(properties);
        this.material = material;
        this.kind = kind;
        this.entityType = entityType;
    }

    public Kind kind() { return kind; }
    @Override public MaterialDefinition baseMetalsMaterial() { return material; }

    @Override
    public EntityArrow createArrow(World world, ItemStack stack, EntityLivingBase shooter) {
        MaterialProjectile projectile = new MaterialProjectile(entityType.get(), world, shooter, stack);

        // A vanilla bow adds one damage. Material bows replace that with their own damage.
        projectile.setDamage(1.0D + material.baseAttackDamage());

        return projectile;
    }
}

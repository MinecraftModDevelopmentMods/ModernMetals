package zone.moddev.mc.modernmetals.client;

import zone.moddev.mc.modernmetals.entity.MaterialProjectile;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public final class MaterialProjectileRenderer extends RenderArrow<MaterialProjectile> {
    private static final ResourceLocation ARROW_TEXTURE =
            new ResourceLocation("textures/entity/projectiles/arrow.png");

    public MaterialProjectileRenderer(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(MaterialProjectile projectile) {
        return ARROW_TEXTURE;
    }
}

package zone.moddev.mc.modernmetals.init;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import com.mcmoddev.lib.block.BlockMoltenFluid;
import com.mcmoddev.lib.data.SharedStrings;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.util.Config.Options;
import zone.moddev.mc.modernmetals.data.MaterialNames;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

/**
 * This class initializes all fluids in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Fluids extends com.mcmoddev.lib.init.Fluids {
	private Fluids() {
		throw new IllegalAccessError(SharedStrings.NOT_INSTANTIABLE);
	}

	/**
	 *
	 */
	public static void init() {
		final List<String> materials = Arrays.asList(MaterialNames.ALUMINUM, MaterialNames.ALUMINUM_BRASS,
				MaterialNames.BERYLLIUM, MaterialNames.BORON, MaterialNames.CADMIUM, MaterialNames.CHROMIUM, MaterialNames.GALVANIZED_STEEL,
				MaterialNames.IRIDIUM, MaterialNames.MAGNESIUM, MaterialNames.MANGANESE, MaterialNames.NICHROME,
				MaterialNames.OSMIUM, MaterialNames.PLUTONIUM, MaterialNames.RUTILE, MaterialNames.STAINLESS_STEEL,
				MaterialNames.TANTALUM, MaterialNames.THORIUM, MaterialNames.TITANIUM, MaterialNames.TUNGSTEN,
				MaterialNames.URANIUM, MaterialNames.ZIRCONIUM);

		materials.stream().filter(Materials::hasMaterial)
				.filter(Options::isFluidEnabled).forEach(materialName -> {
                    final MMDMaterial material = Materials.getMaterialByName(materialName);
                    addFluid(material, 2000, 10000, getTemperature(materialName), 10);
                    material.setFluidBlockGetter(name -> new MoltenMetalBlock(material.getFluid()));
                    addFluidBlock(material);
				});
	}

    private static final class MoltenMetalBlock extends BlockMoltenFluid {
        MoltenMetalBlock(final Fluid fluid) {
            super(fluid);
        }

        @Override
        public void onEntityCollision(final World world, final BlockPos pos, final IBlockState state,
                final Entity entity) {
            super.onEntityCollision(world, pos, state, entity);
            if (world.isRemote || !(entity instanceof EntityLivingBase) || entity.isImmuneToFire()) {
                return;
            }
            final double surface = pos.getY() + getFluidHeightForRender(world, pos, world.getBlockState(pos.up()));
            if (entity.getEntityBoundingBox().minY >= surface || entity.isInLava()) {
                return;
            }

            // Minecraft handles damage once the entity enters the deeper lava contact bounds.
            entity.attackEntityFrom(DamageSource.LAVA, 4.0F);
            entity.setFire(15);
        }
    }

	private static int getTemperature(@Nonnull final String name) {
		switch (name) {
			case MaterialNames.ALUMINUM:
				return 330;
			case MaterialNames.ALUMINUM_BRASS:
				return 500;
			case MaterialNames.BERYLLIUM:
			case MaterialNames.BORON:
				return 400;
			case MaterialNames.CADMIUM:
				return 300;
			case MaterialNames.THORIUM:
				return 514;
			default:
				return 769;
		}
	}
}

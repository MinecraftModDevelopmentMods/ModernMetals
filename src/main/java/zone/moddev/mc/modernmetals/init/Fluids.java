package zone.moddev.mc.modernmetals.init;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import com.mcmoddev.lib.block.InteractiveFluidBlock;
import com.mcmoddev.lib.data.SharedStrings;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.util.Config.Options;
import zone.moddev.mc.modernmetals.data.MaterialNames;
import net.minecraft.util.DamageSource;

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
                    material.setFluidBlockGetter(name -> new InteractiveFluidBlock(material.getFluid(), (world, entity) -> {
                        if (!world.isRemote) {
                            entity.attackEntityFrom(DamageSource.LAVA, 4.0F);
                            entity.setFire(4);
                        }
                    }));
                    addFluidBlock(material);
				});
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

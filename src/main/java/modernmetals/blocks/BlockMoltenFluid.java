package modernmetals.blocks;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BlockMoltenFluid extends BlockFluidClassic {

	public BlockMoltenFluid(Fluid fluid) {
		super(fluid, Material.LAVA);

//		setCreativeTab(TinkerRegistry.tabSmeltery);
	}

	@Nonnull
	@Override
	public String getUnlocalizedName() {
		Fluid fluid = FluidRegistry.getFluid(fluidName);
		if(fluid != null) {
			return fluid.getUnlocalizedName();
		}
		return super.getUnlocalizedName();
	}
}
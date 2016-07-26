package modernmetals.blocks;

import cyano.basemetals.material.MetalMaterial;

public class BlockHalfMetalSlab extends BlockMetalSlab {

	public BlockHalfMetalSlab(MetalMaterial metal) {
		super(metal);
	}

	public boolean isDouble() {
		return false;
	}
}

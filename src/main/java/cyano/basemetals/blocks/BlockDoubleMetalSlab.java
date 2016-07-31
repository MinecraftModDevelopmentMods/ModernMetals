package cyano.basemetals.blocks;

import cyano.basemetals.material.MetalMaterial;

public class BlockDoubleMetalSlab extends BlockMetalSlab {

	public BlockDoubleMetalSlab(MetalMaterial metal) {
		super(metal);
	}

	public boolean isDouble() {
		return true;
	}
}

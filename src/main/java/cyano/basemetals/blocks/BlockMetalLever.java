package cyano.basemetals.blocks;

import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;
import net.minecraft.block.BlockLever;

public class BlockMetalLever extends BlockLever implements IOreDictionaryEntry, IMetalObject {

	final MetalMaterial metal;

	public BlockMetalLever(MetalMaterial metal) {
        this.setStepSound(modernmetals.init.Blocks.getBlockByName(metal.getName() + "_block").stepSound);
		this.metal = metal;
		this.blockHardness = metal.getMetalBlockHardness();
		this.blockResistance = metal.getBlastResistance();
		this.setHarvestLevel("pickaxe", metal.getRequiredHarvestLevel());
	}

	@Override
	public MetalMaterial getMetalMaterial() {
		return metal;
	}

	@Override
	public String getOreDictionaryName() {
		return "lever" + metal.getCapitalizedName();
	}
}

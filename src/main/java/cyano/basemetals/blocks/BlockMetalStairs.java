package cyano.basemetals.blocks;

import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;

public class BlockMetalStairs extends BlockStairs implements IOreDictionaryEntry, IMetalObject {

	final MetalMaterial metal;

	public BlockMetalStairs(MetalMaterial metal) {
		super(modernmetals.init.Blocks.getBlockByName(metal.getName( )+ "_block").getDefaultState());
		this.setSoundType(SoundType.METAL);
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
		return "stairs" + metal.getCapitalizedName();
	}
}

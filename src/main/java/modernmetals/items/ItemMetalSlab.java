package modernmetals.items;

import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

public class ItemMetalSlab extends ItemSlab implements IOreDictionaryEntry, IMetalObject {

	final MetalMaterial metal;

	public ItemMetalSlab(MetalMaterial metal, Block block, BlockSlab slab, BlockSlab doubleslab) {
		super(block, slab, doubleslab);
		this.metal = metal;
	}

	@Override
	public MetalMaterial getMetalMaterial() {
		return metal;
	}

	@Override
	public String getOreDictionaryName() {
		return "slab" + metal.getCapitalizedName();
	}

}

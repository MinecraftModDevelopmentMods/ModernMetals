package modernmetals.items;

import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
import net.minecraft.item.ItemFishingRod;

public class ItemMetalFishingRod extends ItemFishingRod implements IMetalObject {

	private final MetalMaterial metal;

	public ItemMetalFishingRod(MetalMaterial m){
		this.metal = m;
	}

	@Override public MetalMaterial getMetalMaterial(){
		return metal;
	}

}

package modernmetals.items;

import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;
import net.minecraft.creativetab.CreativeTabs;

public class ItemMetalShears extends net.minecraft.item.ItemShears implements IOreDictionaryEntry, IMetalObject{
	protected final MetalMaterial metal;
	private final String oreDict;
	public ItemMetalShears(MetalMaterial metal){
		this.metal = metal;
		this.setCreativeTab(CreativeTabs.MATERIALS);
		this.oreDict = "shears"+metal.getCapitalizedName();
	}

	public String getOreDictionaryName(){
		return oreDict;
	}

	@Override
	public MetalMaterial getMetalMaterial(){
		return metal;
	}

}

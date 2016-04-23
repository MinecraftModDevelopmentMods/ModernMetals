package modernmetals.items;

import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;
import net.minecraft.creativetab.CreativeTabs;

public class ItemMetalBow extends net.minecraft.item.ItemBow implements IOreDictionaryEntry, IMetalObject{
	protected final MetalMaterial metal;
	private final String oreDict;
	public ItemMetalBow(MetalMaterial metal){
		this.metal = metal;
		this.setCreativeTab(CreativeTabs.MATERIALS);
		this.oreDict = "bow"+metal.getCapitalizedName();
	}

	public String getOreDictionaryName(){
		return oreDict;
	}

	@Override
	public MetalMaterial getMetalMaterial(){
		return metal;
	}

}

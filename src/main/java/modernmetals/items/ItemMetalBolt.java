package modernmetals.items;

import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;
import net.minecraft.creativetab.CreativeTabs;

public class ItemMetalBolt extends net.minecraft.item.Item implements IOreDictionaryEntry, IMetalObject {

	protected final MetalMaterial metal;
	private final String oreDict;
	public ItemMetalBolt(MetalMaterial metal) {
		this.metal = metal;
		this.setCreativeTab(CreativeTabs.MATERIALS);
		this.oreDict = "ammoBolt";
	}

	@Override
	public String getOreDictionaryName() {
		return oreDict;
	}

	@Override
	public MetalMaterial getMetalMaterial() {
		return metal;
	}
}

package modernmetals.items;

import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;
import net.minecraft.creativetab.CreativeTabs;

/**
 * 
 * @author Jasmine Iwanek
 *
 */
public class ItemMetalArrow extends net.minecraft.item.ItemArrow implements IOreDictionaryEntry, IMetalObject {

	protected final MetalMaterial metal;
	private final String oreDict;

	/**
	 * 
	 * @param metal
	 */
	public ItemMetalArrow(MetalMaterial metal) {
		this.metal = metal;
		this.setCreativeTab(CreativeTabs.MATERIALS);
		this.oreDict = "arrow"+metal.getCapitalizedName();
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

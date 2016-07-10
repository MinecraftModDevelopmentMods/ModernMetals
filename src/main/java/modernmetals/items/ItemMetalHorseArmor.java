package modernmetals.items;

import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;

import net.minecraft.item.Item;

/**
 * 
 * @author Jasmine Iwanek
 *
 */
public class ItemMetalHorseArmor extends Item implements IMetalObject {

	private final MetalMaterial metal;

	/**
	 * 
	 * @param m
	 */
	public ItemMetalHorseArmor(MetalMaterial m) {
		this.metal = m;
	}

	@Override public MetalMaterial getMetalMaterial() {
		return metal;
	}
}

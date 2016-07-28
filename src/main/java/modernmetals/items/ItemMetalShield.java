package modernmetals.items;

import cyano.basemetals.init.Materials;
import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemShield;

public class ItemMetalShield extends ItemShield implements IOreDictionaryEntry, IMetalObject {

	final MetalMaterial metal;
	protected final String repairOreDictName;
	protected final boolean regenerates;
	protected final long regenInterval = 200;

	public ItemMetalShield(MetalMaterial metal) {
		this.metal = metal;
		this.setMaxDamage(metal.getToolDurability());
		this.setCreativeTab(CreativeTabs.TOOLS);
		repairOreDictName = "ingot" + metal.getCapitalizedName();
		regenerates = metal.equals(Materials.starsteel);
	}

	@Override
	public MetalMaterial getMetalMaterial() {
		return metal;
	}

	@Override
	public String getOreDictionaryName() {
		return "shield" + metal.getCapitalizedName();
	}
}

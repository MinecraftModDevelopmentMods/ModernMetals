package cyano.basemetals.items;

import java.util.List;

import cyano.basemetals.init.Materials;
import cyano.basemetals.items.MetalToolEffects;
import cyano.basemetals.material.MetalMaterial;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

/**
 * 
 * @author Jasmine Iwanek
 *
 */
public class ItemMetalBow extends ItemBow {

	protected final MetalMaterial metal;
	protected final String repairOreDictName;
	protected final boolean regenerates;
	protected final long regenInterval = 200; 

	/**
	 * 
	 * @param metal
	 */
	public ItemMetalBow(MetalMaterial metal) {
		this.metal = metal;
		this.setMaxDamage(metal.getToolDurability());
		this.setCreativeTab(CreativeTabs.COMBAT);
		repairOreDictName = "ingot" + metal.getCapitalizedName();
		regenerates = metal.equals(Materials.starsteel);
	}

	@Override
	public boolean getIsRepairable(final ItemStack intputItem, final ItemStack repairMaterial) {
		List<ItemStack> acceptableItems = OreDictionary.getOres(repairOreDictName);
		for(ItemStack i : acceptableItems)
			if(ItemStack.areItemsEqual(i, repairMaterial))
				return true;

		return false;
	}

	@Override
	public void onUpdate(final ItemStack item, final World world, final Entity player, final int inventoryIndex, final boolean isHeld) {
		if(regenerates && !world.isRemote && isHeld && item.getItemDamage() > 0 && world.getTotalWorldTime() % regenInterval == 0) {
			item.setItemDamage(item.getItemDamage() - 1);
		}
	}

	public String getMaterialName() {
		return metal.getName();
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean b) {
		super.addInformation(stack, player, list, b);
		MetalToolEffects.addToolSpecialPropertiesToolTip(metal, list);
	}
}

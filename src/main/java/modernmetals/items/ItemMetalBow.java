package modernmetals.items;

import java.util.List;

import cyano.basemetals.init.Materials;
import cyano.basemetals.items.MetalToolEffects;
import cyano.basemetals.material.MetalMaterial;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ItemMetalBow extends net.minecraft.item.ItemBow {

	protected final MetalMaterial metal;
	protected final String repairOreDictName;
	protected final boolean regenerates;
	protected final long regenInterval = 200; 

	public ItemMetalBow(MetalMaterial metal) {
		this.metal = metal;
		this.setMaxDamage(metal.getToolDurability());
		this.setCreativeTab(CreativeTabs.COMBAT);
		repairOreDictName = "ingot"+metal.getCapitalizedName();
		if(metal.equals(Materials.starsteel)) {
			regenerates = true;
		} else {
			regenerates = false;
		}
	}

	// TODO: Test this
	@Override
	public boolean getIsRepairable(final ItemStack intputItem, final ItemStack repairMaterial) {
		List<ItemStack> acceptableItems = OreDictionary.getOres(repairOreDictName);
		for(ItemStack i : acceptableItems ) {
			if(ItemStack.areItemsEqual(i, repairMaterial)) return true;
		}
		return false;
	}

	// TODO: Test this
	@Override
	public void onUpdate(final ItemStack item, final World world, final Entity player, final int inventoryIndex, final boolean isHeld) {
		if(regenerates && !world.isRemote && isHeld && item.getItemDamage() > 0 && world.getTotalWorldTime() % regenInterval == 0) {
			item.setItemDamage(item.getItemDamage() - 1);
		}
	}

	public String getMaterialName() {
		return metal.getName();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {
		super.addInformation(stack,player,list,b);
		MetalToolEffects.addToolSpecialPropertiesToolTip(metal,list);
	}
}

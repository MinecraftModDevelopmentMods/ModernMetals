package modernmetals.items;

import java.util.List;

import cyano.basemetals.init.Materials;
import cyano.basemetals.items.MetalToolEffects;
import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ItemMetalFishingRod extends ItemFishingRod implements IMetalObject {

	private final MetalMaterial metal;
	protected final String repairOreDictName;
	protected final boolean regenerates;
	protected final long regenInterval = 200; 

	public ItemMetalFishingRod(MetalMaterial m){
		this.metal = m;
        this.setMaxDamage(64);
		this.setCreativeTab(CreativeTabs.TOOLS);
		repairOreDictName = "ingot"+metal.getCapitalizedName();
		if(metal.equals(Materials.starsteel)){
			regenerates = true;
		} else {
			regenerates = false;
		}
        this.addPropertyOverride(new ResourceLocation("cast"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn)
            {
                return entityIn == null ? 0.0F : (entityIn.getHeldItemMainhand() == stack && entityIn instanceof EntityPlayer && ((EntityPlayer)entityIn).fishEntity != null ? 1.0F : 0.0F);
            }
        });
	}

	// TODO: Test this
	@Override
	public boolean getIsRepairable(final ItemStack intputItem, final ItemStack repairMaterial) {
		List<ItemStack> acceptableItems = OreDictionary.getOres(repairOreDictName);
		for(ItemStack i : acceptableItems ){
			if(ItemStack.areItemsEqual(i, repairMaterial)) return true;
		}
		return false;
	}

	// TODO: Test this
	@Override
	public void onUpdate(final ItemStack item, final World world, final Entity player, final int inventoryIndex, final boolean isHeld) {
		if(regenerates && !world.isRemote && isHeld && item.getItemDamage() > 0 && world.getTotalWorldTime() % regenInterval == 0){
			item.setItemDamage(item.getItemDamage() - 1);
		}
	}
	
	public String getMaterialName() {
		return metal.getName();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b){
		super.addInformation(stack,player,list,b);
		MetalToolEffects.addToolSpecialPropertiesToolTip(metal,list);
	}

	@Override public MetalMaterial getMetalMaterial(){
		return metal;
	}

}

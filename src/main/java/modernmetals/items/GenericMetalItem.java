package modernmetals.items;

import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
import modernmetals.init.Achievements;
import modernmetals.init.Materials;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * version of Item that stores a metal material
 * @author DrCyano
 *
 */
public class GenericMetalItem extends Item implements IMetalObject {

	private final MetalMaterial metal;

	/**
	 * 
	 * @param metal
	 */
	public GenericMetalItem(MetalMaterial metal) {
		this.metal = metal;
	}

	// TODO: Detect between blends & Ingots
	@Override
	public void onCreated(final ItemStack item, final World world, final EntityPlayer entityPlayer) {
		super.onCreated(item, world, entityPlayer);
		// achievement
		entityPlayer.addStat(cyano.basemetals.init.Achievements.this_is_new, 1); // Only on ingots

		if (this.getRegistryName().getResourcePath().contains("_blend")) {
			if((this.metal == Materials.aluminumbrass)
					|| (this.metal == Materials.galvanizedsteel)
					|| (this.metal == Materials.nichrome)
					|| (this.metal == Materials.stainlesssteel)
					|| (this.metal == Materials.titanium))
				entityPlayer.addStat(cyano.basemetals.init.Achievements.metallurgy, 1);
		} else if (this.getRegistryName().getResourcePath().contains("_ingot")) {
			if(this.metal == Materials.aluminumbrass) {
				entityPlayer.addStat(Achievements.aluminumbrass_maker, 1);
			} else if(this.metal == Materials.galvanizedsteel) {
				entityPlayer.addStat(Achievements.galvanized_steel_maker, 1);
			} else if(this.metal == Materials.nichrome) {
				entityPlayer.addStat(Achievements.nichrome_maker, 1);
			} else if(this.metal == Materials.stainlesssteel) {
				entityPlayer.addStat(Achievements.stainless_steel_maker, 1);
			} else if(this.metal == Materials.titanium) {
				entityPlayer.addStat(Achievements.titanium_maker, 1);
			}
		}
	}

	@Override
	public MetalMaterial getMetalMaterial() {
		return metal;
	}
}

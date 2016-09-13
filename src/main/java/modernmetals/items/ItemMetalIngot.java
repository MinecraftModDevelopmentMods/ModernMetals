package modernmetals.items;

import modernmetals.init.Achievements;
import modernmetals.init.Materials;
import cyano.basemetals.material.MetalMaterial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Ingots
 *
 * @author Jasmine Iwanek
 *
 */
public class ItemMetalIngot extends cyano.basemetals.items.ItemMetalIngot {

	/**
	 * 
	 * @param metal
	 */
	public ItemMetalIngot(MetalMaterial metal) {
		super(metal);
	}

	@Override
	public void onCreated(final ItemStack item, final World world, final EntityPlayer entityPlayer) {
		super.onCreated(item, world, entityPlayer);
		entityPlayer.addStat(cyano.basemetals.init.Achievements.this_is_new, 1);

		if(this.getMetalMaterial() == Materials.aluminumbrass)
			entityPlayer.addStat(Achievements.aluminumbrass_maker, 1);
		else if(this.getMetalMaterial() == Materials.galvanizedsteel)
			entityPlayer.addStat(Achievements.galvanized_steel_maker, 1);
		else if(this.getMetalMaterial() == Materials.nichrome)
			entityPlayer.addStat(Achievements.nichrome_maker, 1);
		else if(this.getMetalMaterial() == Materials.stainlesssteel)
			entityPlayer.addStat(Achievements.stainless_steel_maker, 1);
		else if(this.getMetalMaterial() == Materials.titanium)
			entityPlayer.addStat(Achievements.titanium_maker, 1);
	}
}

package modernmetals.items;

import cyano.basemetals.init.Achievements;
import modernmetals.init.Materials;
import cyano.basemetals.material.MetalMaterial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Blends
 *
 * @author Jasmine Iwanek
 *
 */
public class ItemMetalBlend extends cyano.basemetals.items.ItemMetalBlend {

	/**
	 *
	 * @param metal
	 */
	public ItemMetalBlend(MetalMaterial metal) {
		super(metal);
	}

	@Override
	public void onCreated(final ItemStack item, final World world, final EntityPlayer crafter) {
		super.onCreated(item, world, crafter);
		// achievement
		if((this.metal == Materials.aluminumbrass)
				|| (this.metal == Materials.galvanizedsteel)
				|| (this.metal == Materials.nichrome)
				|| (this.metal == Materials.stainlesssteel)
				|| (this.metal == Materials.titanium))
			crafter.addStat(Achievements.metallurgy, 1);
	}
}

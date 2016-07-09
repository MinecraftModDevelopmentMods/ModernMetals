package modernmetals.items;

import cyano.basemetals.init.Achievements;
import modernmetals.init.Materials;
import cyano.basemetals.material.MetalMaterial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Blends
 */
public class ItemMetalBlend extends cyano.basemetals.items.ItemMetalBlend {

	public ItemMetalBlend(MetalMaterial metal) {
		super(metal);
	}

	@Override
	public void onCreated(final ItemStack item, final World world, final EntityPlayer crafter) {
		super.onCreated(item, world, crafter);
		// achievement
		if(metal == Materials.aluminumbrass || metal == Materials.galvanizedsteel || metal == Materials.nichrome
				|| metal == Materials.stainlesssteel || metal == Materials.titanium) {
			crafter.addStat(Achievements.metallurgy, 1);
		}
	}
}

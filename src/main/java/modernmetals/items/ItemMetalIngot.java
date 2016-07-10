package modernmetals.items;

import modernmetals.init.Achievements;
import modernmetals.init.Materials;
import cyano.basemetals.material.MetalMaterial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Ingots
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
	public void onCreated(final ItemStack item, final World world, final EntityPlayer crafter) {
		super.onCreated(item, world, crafter);
		crafter.addStat(cyano.basemetals.init.Achievements.this_is_new, 1);

		if(metal == Materials.aluminumbrass) crafter.addStat(Achievements.aluminumbrass_maker, 1);
		if(metal == Materials.galvanizedsteel) crafter.addStat(Achievements.galvanized_steel_maker, 1);
		if(metal == Materials.nichrome) crafter.addStat(Achievements.nichrome_maker, 1);
		if(metal == Materials.stainlesssteel) crafter.addStat(Achievements.stainless_steel_maker, 1);
		if(metal == Materials.titanium) crafter.addStat(Achievements.titanium_maker, 1);
	}
}

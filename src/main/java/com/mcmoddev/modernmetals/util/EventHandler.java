package com.mcmoddev.modernmetals.util;

import com.mcmoddev.modernmetals.init.Achievements;
import com.mcmoddev.modernmetals.init.Materials;

import com.mcmoddev.lib.items.ItemMetalBlend;
import com.mcmoddev.lib.items.ItemMetalIngot;
import com.mcmoddev.basemetals.material.IMetalObject;
import com.mcmoddev.basemetals.material.MetalMaterial;
import com.mcmoddev.basemetals.util.Config.Options;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class EventHandler {

/*
	@SubscribeEvent
    public void attackEvent(LivingAttackEvent e) {
        float damage = e.getAmount();
        if (!(e.getEntityLiving() instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) e.getEntityLiving();
        if (player.getActiveItemStack() == null) {
            return;
        }
        ItemStack activeItemStack = player.getActiveItemStack();
        if ((damage > 0.0F) && (activeItemStack != null) && ((activeItemStack.getItem() instanceof ItemMetalShield))) {
            int i = 1 + MathHelper.floor(damage);
            activeItemStack.damageItem(i, player);
            if (activeItemStack.stackSize <= 0) {
                EnumHand enumhand = player.getActiveHand();
                ForgeEventFactory.onPlayerDestroyItem(player, activeItemStack, enumhand);
                if (enumhand == EnumHand.MAIN_HAND) {
                    player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, (ItemStack) null);
                }
                else {
                    player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, (ItemStack) null);
                }
                activeItemStack = null;
                if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
                    player.playSound(SoundEvents.BLOCK_ANVIL_BREAK, 0.8F, 0.8F + player.world.rand.nextFloat() * 0.4F);
                }
            }
        }
    }
*/

	@SubscribeEvent
	void event(ItemCraftedEvent event) {
		final Item item = event.crafting.getItem();
		if (item instanceof IMetalObject) {
			final MetalMaterial material = ((IMetalObject) item).getMaterial();
			if (item instanceof ItemMetalBlend) {
				if (Options.ENABLE_ACHIEVEMENTS) {
//					event.player.addStat(Achievements.metallurgy, 1);
				}
			}
		}
	}

	@SubscribeEvent
	void event(ItemSmeltedEvent event) {
		final Item item = event.smelting.getItem();
		if (item instanceof IMetalObject) {
			final MetalMaterial material = ((IMetalObject) item).getMaterial();
			if (item instanceof ItemMetalIngot) {
				if (Options.ENABLE_ACHIEVEMENTS) {
//					event.player.addStat(Achievements.this_is_new, 1);
					if (material == Materials.aluminumbrass) {
						event.player.addStat(Achievements.aluminumbrass_maker, 1);
					} else if (material == Materials.galvanizedsteel) {
						event.player.addStat(Achievements.galvanized_steel_maker, 1);
					} else if (material == Materials.nichrome) {
						event.player.addStat(Achievements.nichrome_maker, 1);
					} else if (material == Materials.stainlesssteel) {
						event.player.addStat(Achievements.stainless_steel_maker, 1);
					} else if (material == Materials.titanium) {
						event.player.addStat(Achievements.titanium_maker, 1);
					}
				}
			}
		}
	}
}

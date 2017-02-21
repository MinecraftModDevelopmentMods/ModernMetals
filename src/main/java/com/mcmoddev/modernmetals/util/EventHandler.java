package com.mcmoddev.modernmetals.util;

import com.mcmoddev.modernmetals.init.Achievements;
import com.mcmoddev.modernmetals.init.Materials;

import com.mcmoddev.lib.material.IMetalObject;
import com.mcmoddev.lib.material.MetalMaterial;
import com.mcmoddev.lib.items.ItemMetalIngot;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class EventHandler {

/*
	@SubscribeEvent
	void event(ItemCraftedEvent event) {
		final Item item = event.crafting.getItem();
		if (item instanceof IMetalObject) {
			final MetalMaterial material = ((IMetalObject) item).getMaterial();
			if (com.mcmoddev.basemetals.util.Config.Options.enableAchievements) {
				if (item instanceof ItemMetalBlend) {
//					event.player.addStat(Achievements.metallurgy, 1);
				}
			}
		}
	}
*/

	@SubscribeEvent
	void event(ItemSmeltedEvent event) {
		final Item item = event.smelting.getItem();
		if (item instanceof IMetalObject) {
			final MetalMaterial material = ((IMetalObject) item).getMaterial();
			if (com.mcmoddev.basemetals.util.Config.Options.enableAchievements) {
				if (item instanceof ItemMetalIngot) {
					// event.player.addStat(Achievements.this_is_new, 1);
					if (material == Materials.aluminumBrass) {
						event.player.addStat(Achievements.aluminumBrassMaker, 1);
					} else if (material == Materials.galvanizedSteel) {
						event.player.addStat(Achievements.galvanizedSteelMaker, 1);
					} else if (material == Materials.nichrome) {
						event.player.addStat(Achievements.nichromeMaker, 1);
					} else if (material == Materials.stainlessSteel) {
						event.player.addStat(Achievements.stainlessSteelMaker, 1);
					} else if (material == Materials.titanium) {
						event.player.addStat(Achievements.titaniumMaker, 1);
					}
				}
			}
		}
	}
}

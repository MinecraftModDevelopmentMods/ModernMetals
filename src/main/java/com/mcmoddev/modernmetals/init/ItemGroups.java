package com.mcmoddev.modernmetals.init;

import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.data.SharedStrings;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.init.MMDCreativeTab;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.util.TabContainer;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * This class initializes all item groups in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class ItemGroups extends com.mcmoddev.lib.init.ItemGroups {

	private static boolean initDone = false;

	private static final MMDCreativeTab blocksTab = addTab(SharedStrings.TAB_BLOCKS);
	private static final MMDCreativeTab itemsTab = addTab(SharedStrings.TAB_ITEMS);
	private static final MMDCreativeTab toolsTab = addTab(SharedStrings.TAB_TOOLS);
	private static final MMDCreativeTab combatTab = addTab(SharedStrings.TAB_COMBAT);

	private ItemGroups() {
		throw new IllegalAccessError(SharedStrings.NOT_INSTANTIABLE);
	}

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		initDone = true;
	}

	public static void setupIcons(String materialName) {
		if (Materials.hasMaterial(materialName)) {
			final MMDMaterial material = Materials.getMaterialByName(materialName);

			if ((blocksTab != null) && (material.hasBlock(Names.BLOCK))) {
				blocksTab.setTabIconItem(material.getBlock(Names.BLOCK));
			}

			if ((itemsTab != null) && (material.hasItem(Names.GEAR))) {
				itemsTab.setTabIconItem(material.getItem(Names.GEAR));
			}

			if ((toolsTab != null) && (material.hasItem(Names.PICKAXE))) {
				toolsTab.setTabIconItem(material.getItem(Names.PICKAXE));
			}

			if ((combatTab != null) && (material.hasItem(Names.SWORD))) {
				combatTab.setTabIconItem(material.getItem(Names.SWORD));
			}
		}
	}
}

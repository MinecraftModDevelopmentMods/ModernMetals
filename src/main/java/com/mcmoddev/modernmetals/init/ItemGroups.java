package com.mcmoddev.modernmetals.init;

import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.data.SharedStrings;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.modernmetals.ModernMetals;

/**
 * This class initializes all item groups in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class ItemGroups extends com.mcmoddev.lib.init.ItemGroups {
	private ItemGroups() {
		throw new IllegalAccessError(SharedStrings.NOT_INSTANTIABLE);
	}

	/**
	 *
	 */
	public static void init() {
		// Unused
	}

	/**
	 * 
	 * @param materialName The name of the material.
	 */
	public static void setupIcons(final String materialName) {
		if (Materials.hasMaterial(materialName)) {
			final MMDMaterial material = Materials.getMaterialByName(materialName);

			if (material.hasBlock(Names.BLOCK)) {
				getTab(ModernMetals.MODID, SharedStrings.TAB_BLOCKS).setTabIconItem(material.getBlock(Names.BLOCK));
			}

			if (material.hasItem(Names.GEAR)) {
				getTab(ModernMetals.MODID, SharedStrings.TAB_ITEMS).setTabIconItem(material.getItem(Names.GEAR));
			}

			if (material.hasItem(Names.PICKAXE)) {
				getTab(ModernMetals.MODID, SharedStrings.TAB_TOOLS).setTabIconItem(material.getItem(Names.PICKAXE));
			}

			if (material.hasItem(Names.SWORD)) {
				getTab(ModernMetals.MODID, SharedStrings.TAB_COMBAT).setTabIconItem(material.getItem(Names.SWORD));
			}
		}
	}
}

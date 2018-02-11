package com.mcmoddev.modernmetals.init;

import com.mcmoddev.basemetals.data.MaterialNames;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.init.MMDCreativeTab;
import com.mcmoddev.lib.util.TabContainer;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * This class initializes all item groups in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class ItemGroups extends com.mcmoddev.lib.init.ItemGroups {

	private static boolean initDone = false;
	private static final int blocksTabId  = addTab("blocks", true );;
	private static final int itemsTabId = addTab("items", true );
	private static final int toolsTabId = addTab("tools", true );
	private static final MMDCreativeTab blocksTab = getTab(blocksTabId);
	private static final MMDCreativeTab itemsTab = getTab(itemsTabId);
	private static final MMDCreativeTab toolsTab = getTab(toolsTabId); 
	public static final TabContainer myTabs = new TabContainer(blocksTab, itemsTab, toolsTab);

	private ItemGroups() {
		throw new IllegalAccessError("Not a instantiable class");
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
	
	public static void setupIcons() {
		Item blocksTabIconItem = Item.getItemFromBlock(Materials.hasMaterial(com.mcmoddev.basemetals.data.MaterialNames.STEEL)?Materials.getMaterialByName(MaterialNames.STEEL).getBlock(Names.BLOCK):(Block) Materials.getMaterialByName(MaterialNames.IRON).getBlock(Names.BLOCK));
		Item itemsTabIconItem = Materials.getMaterialByName(MaterialNames.STEEL).hasItem(Names.GEAR)?Materials.getMaterialByName(MaterialNames.STEEL).getItem(Names.GEAR):Items.STICK;
		Item toolsTabIconItem = Materials.getMaterialByName(MaterialNames.STEEL).hasItem(Names.SWORD)?Materials.getMaterialByName(MaterialNames.STEEL).getItem(Names.SWORD):Items.DIAMOND_SWORD;
		
		blocksTab.setTabIconItem(blocksTabIconItem);
		itemsTab.setTabIconItem(itemsTabIconItem);
		toolsTab.setTabIconItem(toolsTabIconItem);
	}
}

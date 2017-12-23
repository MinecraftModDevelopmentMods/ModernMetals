package com.mcmoddev.modernmetals.init;

import com.mcmoddev.basemetals.data.MaterialNames;
import com.mcmoddev.basemetals.init.Materials;
import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.init.MMDCreativeTab;
import com.mcmoddev.lib.util.TabContainer;

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
	private static final int BLOCKS_TAB_ID  = addTab("blocks", true);
	private static final int ITEMS_TAB_ID = addTab("items", true);
	private static final int TOOLS_TAB_ID = addTab("tools", true);
	private static final MMDCreativeTab blocksTab = getTab(BLOCKS_TAB_ID);
	private static final MMDCreativeTab itemsTab = getTab(ITEMS_TAB_ID);
	private static final MMDCreativeTab toolsTab = getTab(TOOLS_TAB_ID); 
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
		Item blocksTabIconItem = Item.getItemFromBlock(Options.isMaterialEnabled(com.mcmoddev.basemetals.data.MaterialNames.STEEL) ? Materials.getMaterialByName(MaterialNames.STEEL).getBlock(Names.BLOCK) : Materials.getMaterialByName(MaterialNames.IRON).getBlock(Names.BLOCK));
		Item itemsTabIconItem = Options.isThingEnabled("Gear") ? Materials.getMaterialByName(MaterialNames.STEEL).getItem(Names.GEAR) : Items.STICK;
		Item toolsTabIconItem = Options.isThingEnabled("BasicTools") ? Materials.getMaterialByName(MaterialNames.STEEL).getItem(Names.SWORD) : Items.DIAMOND_SWORD;

		blocksTab.setTabIconItem(blocksTabIconItem);
		itemsTab.setTabIconItem(itemsTabIconItem);
		toolsTab.setTabIconItem(toolsTabIconItem);
	}
}

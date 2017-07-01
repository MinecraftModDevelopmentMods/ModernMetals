package com.mcmoddev.modernmetals.init;

import com.mcmoddev.basemetals.data.MaterialNames;
import com.mcmoddev.basemetals.init.Materials;
import com.mcmoddev.basemetals.util.Config.Options;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.init.MMDCreativeTab;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * This class initializes all item groups in Modern Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class ItemGroups extends com.mcmoddev.lib.init.ItemGroups {

	private static boolean initDone = false;
	private static final int BLOCKS_TAB_ID  = addTab("blocks", true );
	private static final int ITEMS_TAB_ID = addTab("items", true );
	private static final int TOOLS_TAB_ID = addTab("tools", true );
	public static final MMDCreativeTab blocksTab = getTab(BLOCKS_TAB_ID);
	public static final MMDCreativeTab itemsTab = getTab(ITEMS_TAB_ID);
	public static final MMDCreativeTab toolsTab = getTab(TOOLS_TAB_ID); 

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
		Item blocksTabIconItem = Item.getItemFromBlock(Options.materialEnabled(com.mcmoddev.basemetals.data.MaterialNames.STEEL)?Materials.getMaterialByName(MaterialNames.STEEL).getBlock(Names.BLOCK):(Block) Materials.getMaterialByName(MaterialNames.IRON).getBlock(Names.BLOCK));
		Item itemsTabIconItem = Options.thingEnabled("Gear")?Materials.getMaterialByName(MaterialNames.STEEL).getItem(Names.GEAR):Items.STICK;
		Item toolsTabIconItem = Options.thingEnabled("BasicTools")?Materials.getMaterialByName(MaterialNames.STEEL).getItem(Names.SWORD):Items.DIAMOND_SWORD;
		
		blocksTab.setTabIconItem(new ItemStack(blocksTabIconItem));
		itemsTab.setTabIconItem(new ItemStack(itemsTabIconItem));
		toolsTab.setTabIconItem(new ItemStack(toolsTabIconItem));
	}
}

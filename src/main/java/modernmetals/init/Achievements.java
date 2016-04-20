package modernmetals.init;

import modernmetals.init.Items;
import modernmetals.ModernMetals;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class Achievements extends cyano.basemetals.init.Achievements {

	public static Achievement aluminumbrass_maker; // make blend
	public static Achievement cupronickel_maker; // make blend
	public static Achievement titanium_maker; // make blend

	private static boolean initDone = false;
	public static void init(){
		if(initDone)return;
		
		page = new AchievementPage(ModernMetals.NAME);
		AchievementPage.registerAchievementPage(page);

		aluminumbrass_maker = makeAchievement("aluminumbrass_maker",metallurgy,0,0,Items.aluminumbrass_ingot);
//		cupronickel_maker = makeAchievement("cupronickel_maker",metallurgy,0,1,Items.cupronickel_ingot);
		titanium_maker = makeAchievement("titanium_maker",metallurgy,0,1,Items.titanium_ingot);

		initDone = true;
	}
	private static Achievement makeAchievement(String baseName, Achievement requirement, int x, int y, Item icon) {
		return makeAchievement( baseName,  requirement,  x,  y, new ItemStack( icon));
	}
	private static Achievement makeAchievement(String baseName, Achievement requirement, int x, int y, Block icon) {
		return makeAchievement( baseName,  requirement,  x,  y, new ItemStack( icon));
	}
	private static Achievement makeAchievement(String baseName, Achievement requirement, int x, int y, ItemStack icon) {
		Achievement a = new Achievement(baseName,baseName,x,y,icon,requirement);
		a.registerStat();
		page.getAchievements().add(a);
		return a;
	}

}

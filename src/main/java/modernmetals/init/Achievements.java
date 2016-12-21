package modernmetals.init;

import cyano.basemetals.util.Config.Options;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.fml.common.Loader;

/** initializer for achievements */
public class Achievements extends cyano.basemetals.init.Achievements {

	public static Achievement aluminumbrass_maker; // make blend
	public static Achievement galvanized_steel_maker; // make blend
	public static Achievement nichrome_maker; // make blend
	public static Achievement stainless_steel_maker; // make blend
	public static Achievement titanium_maker; // make blend

	private static boolean initDone = false;

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		if (Options.ENABLE_ACHIEVEMENTS) {
			AchievementPage page = new AchievementPage(Loader.instance().activeModContainer().getModId());
			AchievementPage.registerAchievementPage(page);

			aluminumbrass_maker = makeAchievement("aluminumbrass_maker", metallurgy, 0, 0, Materials.getMaterialByName("aluminumbrass").ingot, page);
			galvanized_steel_maker = makeAchievement("galvanized_steel_maker", metallurgy, 0, 1, Materials.getMaterialByName("galvanizedsteel").ingot, page);
			nichrome_maker = makeAchievement("nichrome_maker", metallurgy, 0, 2, Materials.getMaterialByName("nichrome").ingot, page);
			stainless_steel_maker = makeAchievement("stainless_steel_maker", metallurgy, 0, 3, Materials.getMaterialByName("stainlesssteel").ingot, page);
			titanium_maker = makeAchievement("titanium_maker", metallurgy, 0, 4, Materials.getMaterialByName("titanium").ingot, page);
		}

		initDone = true;
	}
}

package com.mcmoddev.modernmetals.init;

import com.mcmoddev.lib.util.ConfigBase.Options;

/*import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
*/

/** initializer for achievements */
public class Achievements extends com.mcmoddev.lib.init.Achievements {

	private static boolean initDone = false;

	private Achievements() {
		throw new IllegalAccessError("Not a instantiable class");
	}

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		if (Options.enableAchievements()) {
/*			AchievementPage page = new AchievementPage(Loader.instance().activeModContainer().getModId());
			AchievementPage.registerAchievementPage(page);

			final Achievement metallurgy = getAchievementByName(AchievementNames.METALLURGY);
			if (metallurgy != null) {
				if (Options.isMaterialEnabled(MaterialNames.ALUMINUM_BRASS)) {
					makeAchievement(AchievementNames.ALUMINUM_BRASS_MAKER, metallurgy, 0, 0, Materials.getMaterialByName(MaterialNames.ALUMINUM_BRASS).getItem(Names.INGOT), page);
				}

				if (Options.isMaterialEnabled(MaterialNames.GALVANIZED_STEEL)) {
					makeAchievement(AchievementNames.GALVANIZED_STEEL_MAKER, metallurgy, 0, 1, Materials.getMaterialByName(MaterialNames.GALVANIZED_STEEL).getItem(Names.INGOT), page);
				}

				if (Options.isMaterialEnabled(MaterialNames.NICHROME)) {
					makeAchievement(AchievementNames.NICHROME_MAKER, metallurgy, 0, 2, Materials.getMaterialByName(MaterialNames.NICHROME).getItem(Names.INGOT), page);
				}

				if (Options.isMaterialEnabled(MaterialNames.STAINLESS_STEEL)) {
					makeAchievement(AchievementNames.STAINLESS_STEEL_MAKER, metallurgy, 0, 3, Materials.getMaterialByName(MaterialNames.STAINLESS_STEEL).getItem(Names.INGOT), page);
				}

				if (Options.isMaterialEnabled(MaterialNames.STAINLESS_STEEL)) {
					makeAchievement(AchievementNames.TITANIUM_MAKER, metallurgy, 0, 4, Materials.getMaterialByName(MaterialNames.TITANIUM).getItem(Names.INGOT), page);
				}
			}
*/		}

		initDone = true;
	}
}

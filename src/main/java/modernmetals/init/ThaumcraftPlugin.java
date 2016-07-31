package modernmetals.init;

import net.minecraftforge.fml.common.Loader;

/**
 * 
 * @author Jasmine Iwanek
 *
 */
public class ThaumcraftPlugin {

	private static boolean initDone = false;

	/**
	 * 
	 */
	public static void init() {
		if(initDone)
			return;

		if(Loader.isModLoaded("skeleton")) {
			
		}

		initDone = true;
	}
}

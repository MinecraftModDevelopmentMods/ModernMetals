package com.mcmoddev.orespawn;

import com.mcmoddev.modernmetals.ModernMetals;
import com.mcmoddev.orespawn.api.os3.OS3API;
import com.mcmoddev.orespawn.api.plugin.IOreSpawnPlugin;
import com.mcmoddev.orespawn.api.plugin.OreSpawnPlugin;

@OreSpawnPlugin(modid = ModernMetals.MODID, resourcePath = "orespawn")
public class ModernMetalsOreSpawn implements IOreSpawnPlugin {

	@Override
	public void register(final OS3API apiInterface) {
		// nothing for us to do - all of our ores are in the
		// jar and the code handles that
	}
}

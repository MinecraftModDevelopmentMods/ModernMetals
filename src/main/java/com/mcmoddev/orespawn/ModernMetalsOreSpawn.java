package com.mcmoddev.orespawn;

import com.google.common.base.Function;
import com.mcmoddev.modernmetals.init.Materials;
import mmd.orespawn.api.OreSpawnAPI;
import mmd.orespawn.api.SpawnLogic;

public class ModernMetalsOreSpawn implements Function<OreSpawnAPI, SpawnLogic> {

	@Override
	public SpawnLogic apply(OreSpawnAPI api) {
		SpawnLogic logic = api.createSpawnLogic();

		logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD)
				.addOre(Materials.aluminum.ore.getDefaultState(), 8, 4, 8, 0,  96)
				.addOre(Materials.cadmium.ore.getDefaultState(), 8, 4,  8, 0,  96)
				.addOre(Materials.chromium.ore.getDefaultState(), 8, 4, 3, 0, 32)
				.addOre(Materials.iridium.ore.getDefaultState(), 8, 4, 5, 0, 64)
				.addOre(Materials.magnesium.ore.getDefaultState(), 8, 4, 8, 0, 96)
				.addOre(Materials.manganese.ore.getDefaultState(), 8, 4, 6, 0, 64)
				.addOre(Materials.osmium.ore.getDefaultState(), 8, 4, 6, 0, 96)
				.addOre(Materials.plutonium.ore.getDefaultState(), 8, 4, 4, 0, 32)
				.addOre(Materials.rutile.ore.getDefaultState(), 8, 4, 6, 0, 64)
				.addOre(Materials.tantalum.ore.getDefaultState(), 8, 4, 6, 0, 64)
				.addOre(Materials.tungsten.ore.getDefaultState(), 8, 4, 6, 0, 32)
				.addOre(Materials.uranium.ore.getDefaultState(), 8, 4, 6, 0, 32)
				.addOre(Materials.zirconium.ore.getDefaultState(), 8, 4, 6, 0, 64);

		return logic;
	}
}

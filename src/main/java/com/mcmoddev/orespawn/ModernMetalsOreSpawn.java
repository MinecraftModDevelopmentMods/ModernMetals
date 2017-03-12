package com.mcmoddev.orespawn;

import com.google.common.base.Function;
import com.mcmoddev.modernmetals.init.Materials;
import mmd.orespawn.api.OreSpawnAPI;
import mmd.orespawn.api.SpawnLogic;
import com.mcmoddev.modernmetals.util.Config.Options;

public class ModernMetalsOreSpawn implements Function<OreSpawnAPI, SpawnLogic> {

	@Override
	public SpawnLogic apply(OreSpawnAPI api) {
		SpawnLogic logic = api.createSpawnLogic();

		if( Options.enableAluminum ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.aluminum.ore.getDefaultState(), 10, 8, 8, 0,  96);
		}

		if( Options.enableCadmium ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.cadmium.ore.getDefaultState(), 4, 8,  8, 0,  96);
		}

		if( Options.enableChromium ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.chromium.ore.getDefaultState(), 2, 4, 3, 0, 32);
		}

		if( Options.enableIridium ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.iridium.ore.getDefaultState(), 6, 4, 5, 0, 64);
		}

		if( Options.enableMagnesium ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.magnesium.ore.getDefaultState(), 6, 4, 8, 0, 96);
		}

		if( Options.enableManganese ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.manganese.ore.getDefaultState(), 6, 4, 6, 0, 64);
		}

		if( Options.enableOsmium ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.osmium.ore.getDefaultState(), 10, 4, 8, 0, 96);
		}

		if( Options.enablePlutonium ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.plutonium.ore.getDefaultState(), 2, 4, 4, 0, 32);
		}

		if( Options.enableRutile ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.rutile.ore.getDefaultState(), 6, 4, 6, 0, 64);
		}

		if( Options.enableTantalum ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.tantalum.ore.getDefaultState(), 6, 4, 6, 0, 64);
		}

		if( Options.enableTungsten ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.tungsten.ore.getDefaultState(), 4, 4, 6, 0, 32);
		}

		if( Options.enableUranium ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.uranium.ore.getDefaultState(), 2, 4, 6, 0, 32);
		}

		if( Options.enableZirconium ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.zirconium.ore.getDefaultState(), 8, 4, 6, 0, 64);
		}

		return logic;
	}
}

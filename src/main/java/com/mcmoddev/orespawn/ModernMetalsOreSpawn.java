package com.mcmoddev.orespawn;

import com.google.common.base.Function;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.modernmetals.init.Materials;
import com.mcmoddev.orespawn.api.OreSpawnAPI;
import com.mcmoddev.orespawn.api.SpawnLogic;
import com.mcmoddev.modernmetals.util.Config.Options;

public class ModernMetalsOreSpawn implements Function<OreSpawnAPI, SpawnLogic> {

	@Override
	public SpawnLogic apply(OreSpawnAPI api) {
		SpawnLogic logic = api.createSpawnLogic();

		if( Options.enableAluminum ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName("aluminum").getBlock(Names.ORE).getDefaultState(), 10, 8, 8, 0,  96);
		}

		if( Options.enableCadmium ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName("cadmium").getBlock(Names.ORE).getDefaultState(), 4, 8,  8, 0,  96);
		}

		if( Options.enableChromium ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName("chromium").getBlock(Names.ORE).getDefaultState(), 2, 4, 3, 0, 32);
		}

		if( Options.enableIridium ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName("iridium").getBlock(Names.ORE).getDefaultState(), 6, 4, 5, 0, 64);
		}

		if( Options.enableMagnesium ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName("magnesium").getBlock(Names.ORE).getDefaultState(), 6, 4, 8, 0, 96);
		}

		if( Options.enableManganese ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName("manganese").getBlock(Names.ORE).getDefaultState(), 6, 4, 6, 0, 64);
		}

		if( Options.enableOsmium ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName("osmium").getBlock(Names.ORE).getDefaultState(), 10, 4, 8, 0, 96);
		}

		if( Options.enablePlutonium ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName("plutonium").getBlock(Names.ORE).getDefaultState(), 2, 4, 4, 0, 32);
		}

		if( Options.enableRutile ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName("rutile").getBlock(Names.ORE).getDefaultState(), 6, 4, 6, 0, 64);
		}

		if( Options.enableTantalum ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName("tantalum").getBlock(Names.ORE).getDefaultState(), 6, 4, 6, 0, 64);
		}

		if( Options.enableTungsten ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName("tungsten").getBlock(Names.ORE).getDefaultState(), 4, 4, 6, 0, 32);
		}

		if( Options.enableUranium ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName("uranium").getBlock(Names.ORE).getDefaultState(), 2, 4, 6, 0, 32);
		}

		if( Options.enableZirconium ) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName("zirconium").getBlock(Names.ORE).getDefaultState(), 8, 4, 6, 0, 64);
		}

		return logic;
	}
}

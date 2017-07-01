package com.mcmoddev.orespawn;

import com.google.common.base.Function;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.modernmetals.data.MaterialNames;
import com.mcmoddev.modernmetals.init.Materials;
import com.mcmoddev.orespawn.api.OreSpawnAPI;
import com.mcmoddev.orespawn.api.SpawnLogic;
import com.mcmoddev.modernmetals.util.Config.Options;

public class ModernMetalsOreSpawn implements Function<OreSpawnAPI, SpawnLogic> {

	@Override
	public SpawnLogic apply(OreSpawnAPI api) {
		SpawnLogic logic = api.createSpawnLogic();

		if (Options.materialEnabled(MaterialNames.ALUMINUM)) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName(MaterialNames.ALUMINUM).getBlock(Names.ORE).getDefaultState(), 10, 8, 8, 0,  96);
		}

		if (Options.materialEnabled(MaterialNames.CADMIUM)) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName(MaterialNames.CADMIUM).getBlock(Names.ORE).getDefaultState(), 4, 8,  8, 0,  96);
		}

		if (Options.materialEnabled(MaterialNames.CHROMIUM)) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName(MaterialNames.CHROMIUM).getBlock(Names.ORE).getDefaultState(), 2, 4, 3, 0, 32);
		}

		if (Options.materialEnabled(MaterialNames.IRIDIUM)) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName(MaterialNames.IRIDIUM).getBlock(Names.ORE).getDefaultState(), 6, 4, 5, 0, 64);
		}

		if (Options.materialEnabled(MaterialNames.MAGNESIUM)) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName(MaterialNames.MAGNESIUM).getBlock(Names.ORE).getDefaultState(), 6, 4, 8, 0, 96);
		}

		if (Options.materialEnabled(MaterialNames.MANGANESE)) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName(MaterialNames.MANGANESE).getBlock(Names.ORE).getDefaultState(), 6, 4, 6, 0, 64);
		}

		if (Options.materialEnabled(MaterialNames.OSMIUM)) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName(MaterialNames.OSMIUM).getBlock(Names.ORE).getDefaultState(), 10, 4, 8, 0, 96);
		}

		if (Options.materialEnabled(MaterialNames.PLUTONIUM)) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName(MaterialNames.PLUTONIUM).getBlock(Names.ORE).getDefaultState(), 2, 4, 4, 0, 32);
		}

		if (Options.materialEnabled(MaterialNames.RUTILE)) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName(MaterialNames.RUTILE).getBlock(Names.ORE).getDefaultState(), 6, 4, 6, 0, 64);
		}

		if (Options.materialEnabled(MaterialNames.TANTALUM)) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName(MaterialNames.TANTALUM).getBlock(Names.ORE).getDefaultState(), 6, 4, 6, 0, 64);
		}

		if (Options.materialEnabled(MaterialNames.TUNGSTEN)) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName(MaterialNames.TUNGSTEN).getBlock(Names.ORE).getDefaultState(), 4, 4, 6, 0, 32);
		}

		if (Options.materialEnabled(MaterialNames.URANIUM)) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName(MaterialNames.URANIUM).getBlock(Names.ORE).getDefaultState(), 2, 4, 6, 0, 32);
		}

		if (Options.materialEnabled(MaterialNames.ZIRCONIUM)) {
			logic.getDimension(OreSpawnAPI.DIMENSION_WILDCARD).addOre(Materials.getMaterialByName(MaterialNames.ZIRCONIUM).getBlock(Names.ORE).getDefaultState(), 8, 4, 6, 0, 64);
		}

		return logic;
	}
}

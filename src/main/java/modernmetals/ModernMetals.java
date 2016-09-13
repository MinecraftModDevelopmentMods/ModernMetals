package modernmetals;

import modernmetals.init.*;

import modernmetals.data.AdditionalLootTables;
import modernmetals.data.DataConstants;

import cyano.basemetals.registry.CrusherRecipeRegistry;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.MissingModsException;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * This is the entry point for this mod.
 * @author Jasmine Iwanek
 *
 */
@Mod(modid = ModernMetals.MODID, name = ModernMetals.NAME, version = ModernMetals.VERSION,
		dependencies = "required-after:Forge@[12.17.0.1976,);required-after:basemetals@[2.3,);before:buildingbricks",
		acceptedMinecraftVersions = "1.9.4,)",
		//certificateFingerprint = "",
		updateJSON = "https://raw.githubusercontent.com/jriwanek/ModernMetals/master/update.json")
public class ModernMetals
{
	@Instance
	public static ModernMetals INSTANCE = null;

	/** ID of this mod */
	public static final String MODID = "modernmetals";

	/** display name of this mod */
	public static final String NAME = "Modern Metals";

	/** Version number, in Major.Minor.Build format. The minor number is increased whenever a change 
	 * is made that has the potential to break compatibility with other mods that depend on this one. */
	public static final String VERSION = "0.12.0";

	/** All ore-spawn files discovered in the ore-spawn folder */
	protected static final List<Path> oreSpawnConfigFiles = new LinkedList<>();

	/** If true, hammers cannot be crafted */
	public static boolean disableAllHammers = false;

	/** location of ore-spawn files */
	public static Path oreSpawnFolder = null;

	/** if true, then this mod will require the orespawn mod */
	public static boolean requireOreSpawn = true;

	/**
	 * 
	 * @param event
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		INSTANCE = this;

		// load config
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();


		disableAllHammers = false;


		requireOreSpawn = true;

		config.save();

		if(requireOreSpawn) {
			if(!net.minecraftforge.fml.common.Loader.isModLoaded("orespawn")) {
				HashSet<ArtifactVersion> orespawnMod = new HashSet<>();
				orespawnMod.add(new DefaultArtifactVersion("1.0.0"));
				throw new MissingModsException(orespawnMod, "orespawn", "DrCyano's Ore Spawn Mod");
			}
			oreSpawnFolder = Paths.get(event.getSuggestedConfigurationFile().toPath().getParent().toString(), "orespawn");
			Path oreSpawnFile = Paths.get(oreSpawnFolder.toString(), MODID + ".json");
			if (!(Files.exists(oreSpawnFile))) {
				try {
					Files.createDirectories(oreSpawnFile.getParent());
					Files.write(oreSpawnFile, Arrays.asList(DataConstants.DEFAULT_ORESPAWN_JSON.split("\n")), Charset.forName("UTF-8"));
				} catch (IOException e) {
					FMLLog.severe(MODID + ": Error: Failed to write file " + oreSpawnFile);
				}
			}
		}


		Fluids.init();
		Materials.init();
		ItemGroups.init();
		Blocks.init();
		Items.init();
		VillagerTrades.init();
		EnderIOPlugin.init();
		if(Loader.isModLoaded("tconstruct"))
			TinkersConstructPlugin.init();
		VeinMinerPlugin.init();

		Path ALTPath = Paths.get(event.getSuggestedConfigurationFile().getParent(), "additional-loot-tables");
		Path myLootFolder = ALTPath.resolve(MODID);
		if(Files.notExists(myLootFolder)) {
			try {
				Files.createDirectories(myLootFolder.resolve("chests"));
				Files.write(myLootFolder.resolve("chests").resolve("abandoned_mineshaft.json"),
						Collections.singletonList(AdditionalLootTables.abandoned_mineshaft));
				Files.write(myLootFolder.resolve("chests").resolve("desert_pyramid.json"),
						Collections.singletonList(AdditionalLootTables.desert_pyramid));
				Files.write(myLootFolder.resolve("chests").resolve("end_city_treasure.json"),
						Collections.singletonList(AdditionalLootTables.end_city_treasure));
				Files.write(myLootFolder.resolve("chests").resolve("jungle_temple.json"),
						Collections.singletonList(AdditionalLootTables.jungle_temple));
				Files.write(myLootFolder.resolve("chests").resolve("nether_bridge.json"),
						Collections.singletonList(AdditionalLootTables.nether_bridge));
				Files.write(myLootFolder.resolve("chests").resolve("simple_dungeon.json"),
						Collections.singletonList(AdditionalLootTables.simple_dungeon));
				Files.write(myLootFolder.resolve("chests").resolve("spawn_bonus_chest.json"),
						Collections.singletonList(AdditionalLootTables.spawn_bonus_chest));
				Files.write(myLootFolder.resolve("chests").resolve("stronghold_corridor.json"),
						Collections.singletonList(AdditionalLootTables.stronghold_corridor));
				Files.write(myLootFolder.resolve("chests").resolve("stronghold_crossing.json"),
						Collections.singletonList(AdditionalLootTables.stronghold_crossing));
				Files.write(myLootFolder.resolve("chests").resolve("village_blacksmith.json"),
						Collections.singletonList(AdditionalLootTables.village_blacksmith));
			} catch(IOException ex) {
				FMLLog.log(Level.ERROR, ex, "%s: Failed to extract additional loot tables", MODID);
			}
		}

		if(event.getSide() == Side.CLIENT) {
			clientPreInit(event);
		}

		if(event.getSide() == Side.SERVER) {
			serverPreInit(event);
		}
	}

	@SideOnly(Side.CLIENT)
	private void clientPreInit(FMLPreInitializationEvent event) {
		// client-only code
		Fluids.bakeModels(MODID);
	}

	@SideOnly(Side.SERVER)
	private void serverPreInit(FMLPreInitializationEvent event) {
		// server-only code
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler
	public void init(FMLInitializationEvent event) {

		Recipes.init();
		Achievements.init();

		if(event.getSide() == Side.CLIENT) {
			clientInit(event);
		}

		if(event.getSide() == Side.SERVER) {
			serverInit(event);
		}
	}

	@SideOnly(Side.CLIENT)
	private void clientInit(FMLInitializationEvent event) {
		// client-only code
		Items.registerItemRenders(event);
		Blocks.registerItemRenders(event);
	}

	@SideOnly(Side.SERVER)
	private void serverInit(FMLInitializationEvent event) {
		// server-only code
	}

	/**
	 * 
	 * @param event
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if(event.getSide() == Side.CLIENT) {
			clientPostInit(event);
		}

		if(event.getSide() == Side.SERVER) {
			serverPostInit(event);
		}

		CrusherRecipeRegistry.getInstance().clearCache();
	}

	@SideOnly(Side.CLIENT)
	private void clientPostInit(FMLPostInitializationEvent event) {
		// client-only code
	}

	@SideOnly(Side.SERVER)
	private void serverPostInit(FMLPostInitializationEvent event) {
		// server-only code
	}
}

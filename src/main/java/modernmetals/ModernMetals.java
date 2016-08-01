package modernmetals;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Level;

import cyano.basemetals.entity.EntityCustomArrow;
import modernmetals.init.*;

import modernmetals.data.AdditionalLootTables;
import modernmetals.data.DataConstants;
import modernmetals.proxy.CommonProxy;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import cyano.basemetals.registry.CrusherRecipeRegistry;

/**
 * This is the entry point for this mod.
 * @author Jasmine Iwanek
 *
 */
@Mod(
		modid = ModernMetals.MODID,
		name = ModernMetals.NAME,
		version = ModernMetals.VERSION,
		dependencies = "required-after:Forge@[12.16.1.1887,);required-after:basemetals@[2.2,);before:buildingbricks",
		acceptedMinecraftVersions = "1.9,)",
		//certificateFingerprint = "",
		updateJSON = "https://raw.githubusercontent.com/jriwanek/ModernMetals/master/update.json")
public class ModernMetals
{
	@Instance
	public static ModernMetals INSTANCE = null;

	@SidedProxy(clientSide = "modernmetals.proxy.ClientProxy", serverSide = "modernmetals.proxy.CommonProxy")
	public static CommonProxy PROXY = null;

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

		requireOreSpawn = false;

//		if(requireOreSpawn) {
//			if(!net.minecraftforge.fml.common.Loader.isModLoaded("orespawn")) {
//				HashSet<ArtifactVersion> orespawnMod = new HashSet<>();
//				orespawnMod.add(new DefaultArtifactVersion("1.0.0"));
//				throw new MissingModsException(orespawnMod, "orespawn", "DrCyano's Ore Spawn Mod");
//			}
			oreSpawnFolder = Paths.get(event.getSuggestedConfigurationFile().toPath().getParent().toString(), "orespawn");
			Path oreSpawnFile = Paths.get(oreSpawnFolder.toString(), MODID + ".json");
			if(!Files.exists(oreSpawnFile)) {
				try {
					Files.createDirectories(oreSpawnFile.getParent());
					Files.write(oreSpawnFile, Arrays.asList(DataConstants.defaultOreSpawnJSON.split("\n")), Charset.forName("UTF-8"));
				} catch (IOException e) {
					FMLLog.severe(MODID + ": Error: Failed to write file " + oreSpawnFile);
				}
			}
//		}

		config.save();

		Fluids.init();
		Materials.init();
		ItemGroups.init();
		Blocks.init();
		Items.init();
		VillagerTrades.init();
		EnderIOPlugin.init();
//		TinkersConstructPlugin.init();
		VeinMinerPlugin.init();
		EntityRegistry.registerModEntity(EntityCustomArrow.class, "customArrow", 0, INSTANCE, 80, 3, true);

		Path ALTPath = Paths.get(event.getSuggestedConfigurationFile().getParent(), "additional-loot-tables");
		Path myLootFolder = ALTPath.resolve(MODID);
		if(Files.notExists(myLootFolder)) {
			try {
				Files.createDirectories(myLootFolder.resolve("chests"));
				Files.write(myLootFolder.resolve("chests").resolve("abandoned_mineshaft.json"),
						Arrays.asList(AdditionalLootTables.abandoned_mineshaft));
				Files.write(myLootFolder.resolve("chests").resolve("desert_pyramid.json"),
						Arrays.asList(AdditionalLootTables.desert_pyramid));
				Files.write(myLootFolder.resolve("chests").resolve("end_city_treasure.json"),
						Arrays.asList(AdditionalLootTables.end_city_treasure));
				Files.write(myLootFolder.resolve("chests").resolve("jungle_temple.json"),
						Arrays.asList(AdditionalLootTables.jungle_temple));
				Files.write(myLootFolder.resolve("chests").resolve("nether_bridge.json"),
						Arrays.asList(AdditionalLootTables.nether_bridge));
				Files.write(myLootFolder.resolve("chests").resolve("simple_dungeon.json"),
						Arrays.asList(AdditionalLootTables.simple_dungeon));
				Files.write(myLootFolder.resolve("chests").resolve("spawn_bonus_chest.json"),
						Arrays.asList(AdditionalLootTables.spawn_bonus_chest));
				Files.write(myLootFolder.resolve("chests").resolve("stronghold_corridor.json"),
						Arrays.asList(AdditionalLootTables.stronghold_corridor));
				Files.write(myLootFolder.resolve("chests").resolve("stronghold_crossing.json"),
						Arrays.asList(AdditionalLootTables.stronghold_crossing));
				Files.write(myLootFolder.resolve("chests").resolve("village_blacksmith.json"),
						Arrays.asList(AdditionalLootTables.village_blacksmith));
			} catch(IOException ex) {
				FMLLog.log(Level.ERROR, ex, "%s: Failed to extract additional loot tables", MODID);
			}
		}

		PROXY.preInit();
	}

	/**
	 *
	 * @param event
	 */
	@EventHandler
	public void init(FMLInitializationEvent event) {

		Recipes.init();
		Achievements.init();

		PROXY.init();
	}

	/**
	 *
	 * @param event
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		CrusherRecipeRegistry.getInstance().clearCache();

		PROXY.postInit();
	}

}

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

import modernmetals.data.AdditionalLootTables;
import modernmetals.data.DataConstants;
import cyano.basemetals.registry.CrusherRecipeRegistry;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(
		modid = ModernMetals.MODID,
		name = ModernMetals.NAME,
		version = ModernMetals.VERSION,
		dependencies = "required-after:Forge@[12.16.0.1865,);required-after:basemetals@[2.2,)",
		acceptedMinecraftVersions = "1.9",
		//certificateFingerprint = "",
		updateJSON = "https://raw.githubusercontent.com/jriwanek/ModernMetals/master/update.json")
public class ModernMetals
{
	@Instance
    public static ModernMetals INSTANCE = null;

    public static final String MODID = "modernmetals";
    public static final String NAME = "Modern Metals";
    public static final String VERSION = "0.10.2";

	/** All ore-spawn files discovered in the ore-spawn folder */
	public static final List<Path> oreSpawnConfigFiles = new LinkedList<>();

	/** location of ore-spawn files */
	public static Path oreSpawnFolder = null;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
		INSTANCE = this;
		// load config
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	config.load();
    	
		oreSpawnFolder = Paths.get(event.getSuggestedConfigurationFile().toPath().getParent().toString(),"orespawn");
		Path oreSpawnFile = Paths.get(oreSpawnFolder.toString(),MODID+".json");
		if(Files.exists(oreSpawnFile) == false){
			try {
				Files.createDirectories(oreSpawnFile.getParent());
				Files.write(oreSpawnFile, Arrays.asList(DataConstants.defaultOreSpawnJSON.split("\n")), Charset.forName("UTF-8"));
			} catch (IOException e) {
				FMLLog.severe(MODID+": Error: Failed to write file "+oreSpawnFile);
			}
		}

    	config.save();
    	
		modernmetals.init.Fluids.init();
		modernmetals.init.Materials.init();
		modernmetals.init.ItemGroups.init();
		modernmetals.init.Blocks.init();
		modernmetals.init.Items.init();
		modernmetals.init.VillagerTrades.init();
		modernmetals.init.TinkersConstructPlugin.init();
		
		
		
		Path ALTPath = Paths.get(event.getSuggestedConfigurationFile().getParent(),"additional-loot-tables");
		Path myLootFolder = ALTPath.resolve(MODID);
		if(Files.notExists(myLootFolder)){
			try{
				Files.createDirectories(myLootFolder.resolve("chests"));
				Files.write(myLootFolder.resolve("chests").resolve("abandoned_mineshaft.json"),
						Arrays.asList(         AdditionalLootTables.abandoned_mineshaft));
				Files.write(myLootFolder.resolve("chests").resolve("desert_pyramid.json"),
						Arrays.asList(         AdditionalLootTables.desert_pyramid));
				Files.write(myLootFolder.resolve("chests").resolve("end_city_treasure.json"),
						Arrays.asList(         AdditionalLootTables.end_city_treasure));
				Files.write(myLootFolder.resolve("chests").resolve("jungle_temple.json"),
						Arrays.asList(         AdditionalLootTables.jungle_temple));
				Files.write(myLootFolder.resolve("chests").resolve("nether_bridge.json"),
						Arrays.asList(         AdditionalLootTables.nether_bridge));
				Files.write(myLootFolder.resolve("chests").resolve("simple_dungeon.json"),
						Arrays.asList(         AdditionalLootTables.simple_dungeon));
				Files.write(myLootFolder.resolve("chests").resolve("spawn_bonus_chest.json"),
						Arrays.asList(         AdditionalLootTables.spawn_bonus_chest));
				Files.write(myLootFolder.resolve("chests").resolve("stronghold_corridor.json"),
						Arrays.asList(         AdditionalLootTables.stronghold_corridor));
				Files.write(myLootFolder.resolve("chests").resolve("stronghold_crossing.json"),
						Arrays.asList(         AdditionalLootTables.stronghold_crossing));
				Files.write(myLootFolder.resolve("chests").resolve("village_blacksmith.json"),
						Arrays.asList(         AdditionalLootTables.village_blacksmith));
			} catch(IOException ex){
				FMLLog.log(Level.ERROR,ex,"%s: Failed to extract additional loot tables",MODID);
			}
		}

		if(event.getSide() == Side.CLIENT){
			clientPreInit(event);
		}
		if(event.getSide() == Side.SERVER){
			serverPreInit(event);
		}
//    	event.getVersionProperties();
//   	event.getModMetadata();
    }

	@SideOnly(Side.CLIENT)
	private void clientPreInit(FMLPreInitializationEvent event){
		// client-only code
		modernmetals.init.Fluids.bakeModels(MODID);

	}
	@SideOnly(Side.SERVER)
	private void serverPreInit(FMLPreInitializationEvent event){
		// server-only code
	}

	@EventHandler
    public void init(FMLInitializationEvent event)
    {
		modernmetals.init.Recipes.init();
		modernmetals.init.Achievements.init();
		
		if(event.getSide() == Side.CLIENT){
			clientInit(event);
		}
		if(event.getSide() == Side.SERVER){
			serverInit(event);
		}
    }
    
	@SideOnly(Side.CLIENT)
	private void clientInit(FMLInitializationEvent event){
		// client-only code
		modernmetals.init.Items.registerItemRenders(event);
		modernmetals.init.Blocks.registerItemRenders(event);
	}
	@SideOnly(Side.SERVER)
	private void serverInit(FMLInitializationEvent event){
		// server-only code
	}

	@EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
		
		if(event.getSide() == Side.CLIENT){
			clientPostInit(event);
		}
		if(event.getSide() == Side.SERVER){
			serverPostInit(event);
		}
		
		CrusherRecipeRegistry.getInstance().clearCache();
    	
    }

	@SideOnly(Side.CLIENT)
	private void clientPostInit(FMLPostInitializationEvent event){
		// client-only code
	}
	@SideOnly(Side.SERVER)
	private void serverPostInit(FMLPostInitializationEvent event){
		// server-only code
	}
}

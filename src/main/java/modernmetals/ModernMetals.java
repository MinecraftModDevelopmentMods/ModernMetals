package modernmetals;


import modernmetals.init.*;

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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * This is the entry point for this mod. If you are writing your own mod that 
 * uses this mod, the classes of interest to you are the init classes (classes 
 * in package cyano.basemetals.init) and the CrusherRecipeRegistry class (in 
 * package cyano.basemetals.registry). Note that you should add 
 * 'dependencies = "required-after:basemetals"' to your &#64;Mod annotation 
 * (e.g. <br> 
 * &#64;Mod(modid = "moremetals", name="More Metals!", version = "1.2.3", dependencies = "required-after:basemetals")
 * <br>)
 * @author DrCyano
 *
 */
@Mod(
		modid = ModernMetals.MODID,
		name = ModernMetals.NAME,
		version = ModernMetals.VERSION,
		dependencies = "required-after:Forge@[11.15.1.1722,);required-after:basemetals@[1.8,);before:buildingbricks",
		acceptedMinecraftVersions = "1.8.9",
		//certificateFingerprint = "",
		updateJSON = "https://raw.githubusercontent.com/jriwanek/ModernMetals/master/update.json")
public class ModernMetals
{
	@Instance
	public static ModernMetals INSTANCE = null;

	public static final String MODID = "modernmetals";
	public static final String NAME = "Modern Metals";
	public static final String VERSION = "0.11.0";

	/** All ore-spawn files discovered in the ore-spawn folder */
	protected static final List<Path> oreSpawnConfigFiles = new LinkedList<>();

	/** location of ore-spawn files */
	public static Path oreSpawnFolder = null;

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


		if(event.getSide() == Side.CLIENT) {
			clientPreInit(event);
		}

		if(event.getSide() == Side.SERVER) {
			serverPreInit(event);
		}
//		event.getVersionProperties();
//		event.getModMetadata();
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

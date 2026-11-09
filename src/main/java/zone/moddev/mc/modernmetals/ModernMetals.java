package zone.moddev.mc.modernmetals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import zone.moddev.mc.modernmetals.proxy.CommonProxy;
import zone.moddev.mc.modernmetals.content.ContentPolicy;
import zone.moddev.mc.modernmetals.util.MMeConfig;
import com.mcmoddev.lib.data.SharedStrings;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * This is the entry point for this Mod.
 *
 * @author Jasmine Iwanek
 *
 */
@Mod(
		modid = ModernMetals.MODID,
		name = ModernMetals.NAME,
		version = ModernMetals.VERSION,
		dependencies = "required-after:forge@[14.23.5.2859,);required-after:mmdlib@[1.0.0-rc2,2.0.0);required-after:orespawn@[4.1.6.112021,5.0.0);after:basemetals;after:tconstruct;after:conarm;before:buildingbricks",
		acceptedMinecraftVersions = "[1.12.2]",
		updateJSON = ModernMetals.UPDATEJSON,
		guiFactory = "zone.moddev.mc.modernmetals.client.ModernMetalsGuiFactory")
public class ModernMetals {

	@Instance
	public static ModernMetals instance;

	/** ID of this Mod. */
	public static final String MODID = "modernmetals";

	/** Display name of this Mod. */
	protected static final String NAME = "Modern Metals";

	/**
	 * Version number, in Major.Minor.Build format. The minor number is
	 * increased whenever a change is made that has the potential to break
	 * compatibility with other mods that depend on this one.
	 */
	protected static final String VERSION = "2.5.2.112021";

	protected static final String UPDATEJSON = SharedStrings.UPDATE_JSON_URL + "ModernMetals/master/update.json";

	private static final String PROXY_BASE = "zone.moddev.mc.modernmetals.proxy.";

	@SidedProxy(clientSide = PROXY_BASE + SharedStrings.CLIENTPROXY, serverSide = PROXY_BASE
		+ SharedStrings.SERVERPROXY)
	public static CommonProxy proxy;

	public static final Logger logger = LogManager.getFormatterLogger(ModernMetals.MODID);

	/**
	 * 
	 * @param event The Event.
	 */
	@EventHandler
	public void onFingerprintViolation(FMLFingerprintViolationEvent event) {
		if (!(Boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment")) {
//			logger.warn("The Mod " + MODID + " is expecting signature " + event.getExpectedFingerprint() + " for source "+ event.getSource() + ", however there is no signature matching that description")
			logger.warn(SharedStrings.INVALID_FINGERPRINT);
		}
	}

	@EventHandler
	public static void constructing(final FMLConstructionEvent event) {
		MMeConfig.init();
		ContentPolicy.initialize();
	}

	/**
	 * 
	 * @param event The Event.
	 */
	@EventHandler
	public static void preInit(final FMLPreInitializationEvent event) {
		proxy.preInit(event);

	}

	@EventHandler
	public static void init(final FMLInitializationEvent event) {
		zone.moddev.mc.modernmetals.worldgen.ModernMetalsWorldgenProvider.enqueue();
		proxy.init(event);
	}

	@EventHandler
	public static void postInit(final FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}

package zone.moddev.mc.modernmetals.integration.plugins;

import zone.moddev.mc.modernmetals.ModernMetals;
import zone.moddev.mc.modernmetals.init.Materials;
import zone.moddev.mc.modernmetals.util.Config.Options;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.integration.plugins.tinkers.TraitLocations;

import com.mcmoddev.lib.integration.IIntegration;

/**
 *
 * @author Jasmine Iwanek
 *
 */
@MMDPlugin(addonId = ModernMetals.MODID, pluginId = TinkersConstruct.PLUGIN_MODID)
public class TinkersConstruct extends com.mcmoddev.lib.integration.plugins.TinkersConstructBase implements IIntegration {

	private static boolean initDone = false;
	
	@Override
	public void init() {
		if (initDone || !zone.moddev.mc.basemetals.util.Config.Options.isModEnabled(PLUGIN_MODID)) {
			return;
		}
		if (zone.moddev.mc.modernmetals.content.ContentPolicy.active().lowFantasy())
			net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);

		if (Options.enableAluminum) {
			registry.getMaterial("aluminum", Materials.getMaterialByName("aluminum")).addTrait("lightweight").setCastable(true).settle();
		}

		if (Options.enableAluminumBrass) {
			registry.getMaterial("aluminumbrass", Materials.getMaterialByName("aluminumbrass")).setCastable(true).settle();
		}

		if (Options.enableCadmium) {
			registry.getMaterial("cadmium", Materials.getMaterialByName("cadmium")).setCastable(true).settle();
		}

		if (Options.enableChromium) {
			registry.getMaterial("chromium", Materials.getMaterialByName("chromium")).setCastable(true).addTrait("magnetic").addTrait("magnetic2",TraitLocations.HEAD).settle();
		}

		if (Options.enableGalvanizedSteel) {
			registry.getMaterial("galvanizedsteel", Materials.getMaterialByName("galvanizedsteel")).addTrait("sharp", TraitLocations.HEAD).addTrait("stiff").setCastable(true).settle();
			registry.registerAlloy("galvanizedsteel",Materials.getMaterialByName("galvanizedsteel").getFluid(), 2, "steel", 1, "zinc", 1);
		}

		if (Options.enableIridium) {
			registry.getMaterial("iridium", Materials.getMaterialByName("iridium")).setCastable(true).addTrait("heavy").addTrait("brittle").settle();
		}

		if (Options.enableMagnesium) {
			registry.getMaterial("magnesium", Materials.getMaterialByName("magnesium")).setCastable(true).addTrait("reactive").settle();
		}

		if (Options.enableManganese) {
			registry.getMaterial("manganese", Materials.getMaterialByName("manganese")).addTrait("brittle").setCastable(true).settle();
		}

		if (Options.enableNichrome) {
			registry.getMaterial("nichrome", Materials.getMaterialByName("nichrome")).setCastable(true).settle();
			registry.registerAlloy("nichrome", Materials.getMaterialByName("nichrome").getFluid(), 2, "nickel", 1, "chromium", 1);
		}

		if (Options.enableOsmium) {
			registry.getMaterial("osmium", Materials.getMaterialByName("osmium")).setCastable(true).addTrait("heavy").addTrait("brittle").settle();
		}

		if (Options.enablePlutonium) {
			registry.getMaterial("plutonium", Materials.getMaterialByName("plutonium")).addTrait("radioactive").addTrait("toxic", TraitLocations.HEAD).addTrait("toxic", TraitLocations.PROJECTILE).setCastable(true).settle();
		}

		// request is for custom modifier item to grant +50% durability
		// This is not yet in the API - might require extra classes in MMDLib
		if (Options.enableRutile) {
			registry.getMaterial("rutile", Materials.getMaterialByName("rutile")).setCastable(true).settle();
		}

		if (Options.enableStainlessSteel) {
			registry.getMaterial("stainlesssteel", Materials.getMaterialByName("stainlesssteel")).setCastable(true).addTrait("sharp", TraitLocations.HEAD).addTrait("stiff").settle();
			registry.registerAlloy("stainlesssteel", Materials.getMaterialByName("stainlesssteel").getFluid(), 2, "steel", 1, "chromium", 1);
		}

		if (Options.enableTantalum) {
			registry.getMaterial("tantalum", Materials.getMaterialByName("tantalum")).setCastable(true).settle();
		}

		if (Options.enableTitanium) {
			registry.getMaterial("titanium", Materials.getMaterialByName("titanium")).setCastable(true).settle();
			registry.registerAlloy("titanium", Materials.getMaterialByName("titanium").getFluid(), 2, "rutile", 1, "magnesium", 1 );
		}

		if (Options.enableTungsten) {
			registry.getMaterial("tungsten", Materials.getMaterialByName("tungsten")).setCastable(true).settle();
		}

		if (Options.enableUranium) {
			registry.getMaterial("uranium", Materials.getMaterialByName("uranium")).setCastable(true).addTrait("poisonous", TraitLocations.HEAD).addTrait("poisonous", TraitLocations.PROJECTILE).addTrait("radioactive").settle();
		}

		if (Options.enableZirconium) {
			registry.getMaterial("zirconium", Materials.getMaterialByName("zirconium")).setCastable(true).settle();
		}

		if (Options.enableBeryllium) { registry.getMaterial("beryllium", Materials.getMaterialByName("beryllium")).setCastable(true).settle(); }

		if (Options.enableBoron) { registry.getMaterial("boron", Materials.getMaterialByName("boron")).setCastable(true).settle(); }

		if (Options.enableThorium) { registry.getMaterial("thorium", Materials.getMaterialByName("thorium")).setCastable(true).settle(); }

		registry.registerAll();
		initDone = true;
	}

	/** Keep molten processing while preventing restricted tool-part and ranged bypasses. */
	public static void applyContentPolicy() {
		if(!com.mcmoddev.lib.util.ConfigBase.Options.isModEnabled(PLUGIN_MODID)) return;
		final zone.moddev.mc.modernmetals.content.ContentPolicy policy=zone.moddev.mc.modernmetals.content.ContentPolicy.active();
		if(!policy.lowFantasy()) return;
		for(final com.mcmoddev.lib.material.MMDMaterial material:Materials.getMaterialsByMod(ModernMetals.MODID)) {
			final slimeknights.tconstruct.library.materials.Material tc=slimeknights.tconstruct.library.TinkerRegistry.getMaterial(material.getName());
			if(tc==slimeknights.tconstruct.library.materials.Material.UNKNOWN) continue;
			tc.getAllStats().removeIf(stat->!policy.allows(material.getName(),"pickaxe")
				|| "bow".equals(stat.getIdentifier()) || "bowstring".equals(stat.getIdentifier()));
		}
	}

	@net.minecraftforge.fml.common.eventhandler.SubscribeEvent
	public void tableCasting(final slimeknights.tconstruct.library.events.TinkerRegisterEvent.TableCastingRegisterEvent event) {
		restrictCasting(event);
	}
	@net.minecraftforge.fml.common.eventhandler.SubscribeEvent
	public void basinCasting(final slimeknights.tconstruct.library.events.TinkerRegisterEvent.BasinCastingRegisterEvent event) {
		restrictCasting(event);
	}
	/** Reject fixed restricted outputs before Tinkers freezes its casting recipes. */
	private static void restrictCasting(final slimeknights.tconstruct.library.events.TinkerRegisterEvent<slimeknights.tconstruct.library.smeltery.ICastingRecipe> event) {
		if(!(event.getRecipe() instanceof slimeknights.tconstruct.library.smeltery.CastingRecipe)) return;
		final net.minecraft.item.ItemStack output=((slimeknights.tconstruct.library.smeltery.CastingRecipe)event.getRecipe()).getResult();
		final zone.moddev.mc.modernmetals.content.ContentPolicy policy=zone.moddev.mc.modernmetals.content.ContentPolicy.active();
		if(!policy.allows(output)) {event.setCanceled(true);return;}
		if(output==null || !(output.getItem() instanceof slimeknights.tconstruct.library.tools.IToolPart)) return;
		final slimeknights.tconstruct.library.tools.IToolPart part=(slimeknights.tconstruct.library.tools.IToolPart)output.getItem();
		final String name=part.getMaterialID(output);
		final boolean ours=Materials.getMaterialsByMod(ModernMetals.MODID).stream().anyMatch(material->material.getName().equals(name));
		if(ours && (!policy.allows(name,"pickaxe") || part.hasUseForStat("bow") || part.hasUseForStat("bowstring")))
			event.setCanceled(true);
	}
}

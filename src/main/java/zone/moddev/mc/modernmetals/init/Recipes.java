package zone.moddev.mc.modernmetals.init;

import java.util.ArrayList;
import java.util.List;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.data.SharedStrings;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.material.MMDMaterial;
import zone.moddev.mc.modernmetals.ModernMetals;
import zone.moddev.mc.modernmetals.content.ContentPolicy;
import zone.moddev.mc.modernmetals.data.MaterialNames;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 *
 * @author Jasmine Iwanek
 *
 */
@Mod.EventBusSubscriber(modid = ModernMetals.MODID)
public class Recipes extends com.mcmoddev.lib.init.Recipes {

	private Recipes() {
		throw new IllegalAccessError(SharedStrings.NOT_INSTANTIABLE);
	}

	/**
	 *
	 */
	public static void init() {
		initModSpecificRecipes();
	}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerRecipes(final RegistryEvent.Register<IRecipe> event) {
        initModSpecificRecipes();
        register(event);
        registerBlendSmelting();
        final ContentPolicy policy = ContentPolicy.active();
        if (!policy.lowFantasy()) return;

        final List<IRecipe> restricted = new ArrayList<>();
        for (final IRecipe recipe : event.getRegistry()) {
            final String type = recipe.getClass().getName();
            final boolean sharedDynamic = recipe.getRecipeOutput().isEmpty()
                    && (type.startsWith("com.mcmoddev.lib.recipe.") || type.startsWith("zone.moddev.mc.mmdlib.recipe."));
            if (!policy.allows(recipe.getRecipeOutput()) || sharedDynamic) {
                restricted.add(recipe);
            }
        }
        for (final IRecipe recipe : restricted) {
            event.getRegistry().register(new PolicyRecipe(recipe, policy));
        }
    }

    private static void registerBlendSmelting() {
        for (final MMDMaterial material : Materials.getMaterialsByMod(ModernMetals.MODID)) {
            if (!material.hasItem(Names.BLEND) || !material.hasItem(Names.INGOT)) continue;
            final ItemStack blend = material.getItemStack(Names.BLEND);
            if (FurnaceRecipes.instance().getSmeltingResult(blend).isEmpty()) {
                GameRegistry.addSmelting(blend, material.getItemStack(Names.INGOT), material.getOreSmeltXP());
            }
        }
    }

	private static void initModSpecificRecipes() {
		if (Materials.hasMaterial(MaterialNames.ALUMINUM)) {
			final MMDMaterial aluminum = Materials.getMaterialByName(MaterialNames.ALUMINUM);

			addAdditionalOredicts(aluminum, "Aluminium");
			addAdditionalOredicts(aluminum, "Bauxite");
		}

		if (Materials.hasMaterial(MaterialNames.ALUMINUM_BRASS)) {
			final MMDMaterial aluminumBrass = Materials.getMaterialByName(MaterialNames.ALUMINUM_BRASS);

			addAdditionalOredicts(aluminumBrass, "AluminumBrass");
			addAdditionalOredicts(aluminumBrass, "Aluminiumbrass");
			addAdditionalOredicts(aluminumBrass, "AluminiumBrass");
			addAdditionalOredicts(aluminumBrass, "Alubrass");
			addAdditionalOredicts(aluminumBrass, "AluBrass");
		}

		if (Materials.hasMaterial(MaterialNames.CHROMIUM)) {
			addAdditionalOredicts(MaterialNames.CHROMIUM, "Chrome");
		}

		if (Materials.hasMaterial(MaterialNames.GALVANIZED_STEEL)) {
			final MMDMaterial galvanizedSteel = Materials.getMaterialByName(MaterialNames.GALVANIZED_STEEL);

			addAdditionalOredicts(galvanizedSteel, "GalvinizedSteel");
		}

		if (Materials.hasMaterial(MaterialNames.NICHROME)) {
			final MMDMaterial nichrome = Materials.getMaterialByName(MaterialNames.NICHROME);

			addAdditionalOredicts(nichrome, "Nichrome");
		}

		if (Materials.hasMaterial(MaterialNames.STAINLESS_STEEL)) {
			final MMDMaterial stainlessSteel = Materials.getMaterialByName(MaterialNames.STAINLESS_STEEL);

			addAdditionalOredicts(stainlessSteel, "StainlessSteel");
		}

		if (Materials.hasMaterial(MaterialNames.TUNGSTEN)) {
			addAdditionalOredicts(MaterialNames.TUNGSTEN, "Wolfram");
		}
	}
}

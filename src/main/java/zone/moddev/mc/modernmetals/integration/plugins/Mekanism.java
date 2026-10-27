package zone.moddev.mc.modernmetals.integration.plugins;

import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.integration.IIntegration;
import com.mcmoddev.lib.integration.MMDPlugin;
import com.mcmoddev.lib.integration.plugins.MekanismBase;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.util.ConfigBase.Options;
import zone.moddev.mc.modernmetals.ModernMetals;

@MMDPlugin(addonId = ModernMetals.MODID, pluginId = Mekanism.PLUGIN_MODID)
public class Mekanism extends MekanismBase implements IIntegration {
    private static boolean registered;
    /** Discovery happens before the optional mod's registries are ready. */
    @Override public void init() { }
    /** Called after Mekanism's initialization and before its IMC processing. */
    public static void registerRecipes() {
        if (registered || !Options.isModEnabled(PLUGIN_MODID)) return;
        for (final MMDMaterial material : Materials.getMaterialsByMod(ModernMetals.MODID)) {
            if (!Materials.hasMaterial(material.getName()) || !material.hasOre() || !material.hasBlock(Names.ORE)) continue;
            registerMaterial(material);
        }
        registered = true;
    }

    private static void registerMaterial(final MMDMaterial material) {
        final String name = material.getName();
        final mekanism.api.gas.Gas raw = gas(name, material.getCapitalizedName());
        final mekanism.api.gas.Gas clean = gas("clean" + name, "Clean" + material.getCapitalizedName());
        final net.minecraft.item.ItemStack ore = material.getBlockItemStack(Names.ORE);
        if (material.hasItem(Names.POWDER)) {
            mekanism.common.recipe.RecipeHandler.addEnrichmentChamberRecipe(ore, material.getItemStack(Names.POWDER, 2));
            mekanism.common.recipe.RecipeHandler.addCrusherRecipe(material.getItemStack(Names.INGOT), material.getItemStack(Names.POWDER));
        }
        if (material.hasItem(Names.CLUMP))
            mekanism.common.recipe.RecipeHandler.addPurificationChamberRecipe(ore, material.getItemStack(Names.CLUMP, 3));
        if (material.hasItem(Names.POWDER_DIRTY) && material.hasItem(Names.CLUMP)) {
            mekanism.common.recipe.RecipeHandler.addCrusherRecipe(material.getItemStack(Names.CLUMP), material.getItemStack(Names.POWDER_DIRTY));
            mekanism.common.recipe.RecipeHandler.addEnrichmentChamberRecipe(material.getItemStack(Names.POWDER_DIRTY), material.getItemStack(Names.POWDER));
        }
        if (material.hasItem(Names.SHARD)) {
            final mekanism.api.gas.Gas chloride = mekanism.api.gas.GasRegistry.getGas("hydrogenChloride");
            if (chloride == null) throw new IllegalStateException("Mekanism hydrogen chloride is unavailable after initialization");
            mekanism.common.recipe.RecipeHandler.addChemicalInjectionChamberRecipe(ore, chloride, material.getItemStack(Names.SHARD, 4));
            if (material.hasItem(Names.CLUMP))
                mekanism.common.recipe.RecipeHandler.addPurificationChamberRecipe(material.getItemStack(Names.SHARD), material.getItemStack(Names.CLUMP));
        }
        if (material.hasItem(Names.CRYSTAL)) {
            mekanism.common.recipe.RecipeHandler.addChemicalDissolutionChamberRecipe(ore, new mekanism.api.gas.GasStack(raw, 1000));
            mekanism.common.recipe.RecipeHandler.addChemicalWasherRecipe(new mekanism.api.gas.GasStack(raw, 1), new mekanism.api.gas.GasStack(clean, 1));
            mekanism.common.recipe.RecipeHandler.addChemicalCrystallizerRecipe(new mekanism.api.gas.GasStack(clean, 200), material.getItemStack(Names.CRYSTAL));
            if (material.hasItem(Names.SHARD))
                mekanism.common.recipe.RecipeHandler.addChemicalInjectionChamberRecipe(material.getItemStack(Names.CRYSTAL),
                    mekanism.api.gas.GasRegistry.getGas("hydrogenChloride"), material.getItemStack(Names.SHARD));
        }
    }

    private static mekanism.api.gas.Gas gas(final String name, final String display) {
        mekanism.api.gas.Gas gas = mekanism.api.gas.GasRegistry.getGas(name);
        if (gas == null) {
            gas = new mekanism.api.gas.Gas(name, name + "-Icon");
            gas.setUnlocalizedName("gas" + display);
            mekanism.api.gas.GasRegistry.register(gas);
        }
        return gas;
    }
}

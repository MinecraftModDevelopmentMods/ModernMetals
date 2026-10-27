package review.modern;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.init.Materials;
import com.mcmoddev.lib.material.MMDMaterial;
import net.minecraftforge.fml.common.Loader;

/** Optional API linkage stays in nested classes that are loaded only with that mod. */
public final class OptionalIntegrationChecks {
    private OptionalIntegrationChecks() { }
    public static void run(final JsonArray failures) {
        if (Loader.isModLoaded("tconstruct")) {
            if(com.mcmoddev.lib.util.ConfigBase.Options.isModEnabled("tconstruct")) Tinkers.verify(failures);
            else if(Boolean.getBoolean("modern.disabledIntegrationTest")) Tinkers.verifyForeign(failures);
        }
        if (Loader.isModLoaded("IC2")) IndustrialCraft.verify(failures);
        if (Loader.isModLoaded("Mekanism")) Mekanism.verify(failures);
    }
    public static void seedForeign() {Tinkers.seedForeign();}
    private static void check(final boolean pass, final String message, final JsonArray failures) {
        if (!pass) failures.add(new JsonPrimitive(message));
    }
    private static final class Tinkers {
        static void seedForeign() {
            final slimeknights.tconstruct.library.materials.Material foreign=new slimeknights.tconstruct.library.materials.Material("plutonium",0x123456);
            slimeknights.tconstruct.library.TinkerRegistry.addMaterial(foreign);
            slimeknights.tconstruct.library.TinkerRegistry.addMaterialStats(foreign,
                new slimeknights.tconstruct.library.materials.HeadMaterialStats(200,6F,2F,2),
                new slimeknights.tconstruct.library.materials.BowMaterialStats(1F,1F,1F));
        }
        static void verifyForeign(final JsonArray failures) {
            final slimeknights.tconstruct.library.materials.Material foreign=slimeknights.tconstruct.library.TinkerRegistry.getMaterial("plutonium");
            check(foreign.hasStats("head") && foreign.hasStats("bow"),"Disabled integration modified another mod's Tinkers material",failures);
        }
        static void verify(final JsonArray failures) {
            for (final MMDMaterial material : Materials.getMaterialsByMod("modernmetals")) {
                final String name = material.getName();
                final slimeknights.tconstruct.library.materials.Material tc = slimeknights.tconstruct.library.TinkerRegistry.getMaterial(name);
                check(tc != slimeknights.tconstruct.library.materials.Material.UNKNOWN, "Tinkers material missing: " + name, failures);
                check(tc.matches(material.getItemStack(Names.INGOT)) != null, "Tinkers repair ingredient missing: " + name, failures);
                final boolean allowed=zone.moddev.mc.modernmetals.content.ContentPolicy.active().allows(name,"pickaxe");
                check(slimeknights.tconstruct.tools.TinkerTools.pickHead.canUseMaterial(tc)==allowed,"Tinkers tool policy incorrect: "+name,failures);
                if(allowed) {
                    final slimeknights.tconstruct.library.materials.Material wood=slimeknights.tconstruct.library.TinkerRegistry.getMaterial("wood");
                    final net.minecraft.item.ItemStack tool=slimeknights.tconstruct.library.utils.ToolBuilder.tryBuildTool(new net.minecraft.item.ItemStack[]{
                        slimeknights.tconstruct.tools.TinkerTools.toolRod.getItemstackWithMaterial(wood),
                        slimeknights.tconstruct.tools.TinkerTools.pickHead.getItemstackWithMaterial(tc),
                        slimeknights.tconstruct.tools.TinkerTools.binding.getItemstackWithMaterial(wood),null},"Modern Metals regression tool");
                    check(tool!=null,"Tinkers tool creation failed: "+name,failures);
                    if(tool!=null) {
                        tool.setItemDamage(5);
                        final net.minecraft.item.ItemStack repaired=slimeknights.tconstruct.library.utils.ToolBuilder.tryRepairTool(
                            new net.minecraft.item.ItemStack[]{material.getItemStack(Names.INGOT)},tool,false);
                        check(repaired!=null && repaired.getItemDamage()<5,"Tinkers tool repair failed: "+name,failures);
                    }
                } else check(!slimeknights.tconstruct.tools.TinkerTools.toolRod.canUseMaterial(tc),"Tinkers restricted handle bypass: "+name,failures);
                if(zone.moddev.mc.modernmetals.content.ContentPolicy.active().lowFantasy())
                    check(!slimeknights.tconstruct.tools.TinkerTools.bowLimb.canUseMaterial(tc),"Tinkers ranged policy bypass: "+name,failures);
                final slimeknights.tconstruct.library.smeltery.MeltingRecipe melting = slimeknights.tconstruct.library.TinkerRegistry.getMelting(material.getItemStack(Names.INGOT));
                check(melting != null && melting.getResult() != null && melting.getResult().amount == 144,
                      "Tinkers ingot melting missing: " + name, failures);
                final net.minecraftforge.fluids.Fluid fluid = net.minecraftforge.fluids.FluidRegistry.getFluid(name);
                check(slimeknights.tconstruct.library.TinkerRegistry.getTableCasting(
                    slimeknights.tconstruct.smeltery.TinkerSmeltery.castIngot, fluid) != null, "Tinkers ingot casting missing: " + name, failures);
                final net.minecraft.item.ItemStack headCast=slimeknights.tconstruct.library.tools.Pattern.setTagForPart(
                    new net.minecraft.item.ItemStack(slimeknights.tconstruct.smeltery.TinkerSmeltery.cast),slimeknights.tconstruct.tools.TinkerTools.pickHead);
                check((slimeknights.tconstruct.library.TinkerRegistry.getTableCasting(headCast,fluid)!=null)==allowed,
                    "Tinkers part casting policy incorrect: "+name,failures);
                if(zone.moddev.mc.modernmetals.content.ContentPolicy.active().lowFantasy()
                        && !zone.moddev.mc.modernmetals.content.ContentPolicy.active().allows(name,"gear"))
                    check(slimeknights.tconstruct.library.TinkerRegistry.getTableCasting(slimeknights.tconstruct.smeltery.TinkerSmeltery.castGear,fluid)==null,
                        "Tinkers restricted gear casting remains: "+name,failures);
            }
            boolean titanium = false;
            for (final slimeknights.tconstruct.library.smeltery.AlloyRecipe recipe : slimeknights.tconstruct.library.TinkerRegistry.getAlloys()) {
                check(recipe.getResult() != null && recipe.getResult().getFluid() != null && recipe.getResult().amount > 0, "Invalid alloy output", failures);
                for (final net.minecraftforge.fluids.FluidStack input : recipe.getFluids())
                    check(input != null && input.getFluid() != null && input.amount > 0, "Invalid alloy input", failures);
                if (recipe.getResult() != null && recipe.getResult().getFluid() != null && "titanium".equals(recipe.getResult().getFluid().getName())) {
                    final java.util.Set<String> names = new java.util.HashSet<>();
                    for (final net.minecraftforge.fluids.FluidStack input : recipe.getFluids()) if (input != null && input.getFluid() != null) names.add(input.getFluid().getName());
                    int inputs = 0;
                    for (final net.minecraftforge.fluids.FluidStack input : recipe.getFluids()) if (input != null) inputs += input.amount;
                    titanium |= names.contains("rutile") && names.contains("magnesium") && recipe.getResult().amount == inputs;
                }
            }
            if(Materials.hasMaterial("titanium") && Materials.hasMaterial("rutile") && Materials.hasMaterial("magnesium"))
                check(titanium, "Tinkers titanium alloy chain missing", failures);
        }
    }
    private static final class IndustrialCraft {
        static void verify(final JsonArray failures) {
            for (final String name : new String[]{"boron","beryllium","thorium","titanium","chromium"}) {
                if (!Materials.hasMaterial(name)) continue;
                final MMDMaterial material = Materials.getMaterialByName(name);
                check(ic2.api.recipe.Recipes.macerator.getOutputFor(material.getBlockItemStack(Names.ORE), false) != null,
                      "IC2 ore processing missing: " + name, failures);
            }
        }
    }
    private static final class Mekanism {
        static void verify(final JsonArray failures) {
            for (final String name : new String[]{"boron","beryllium","thorium","titanium","chromium"}) {
                if (!Materials.hasMaterial(name)) continue;
                final MMDMaterial material = Materials.getMaterialByName(name);
                check(mekanism.api.gas.GasRegistry.getGas(name) != null, "Mekanism gas missing: " + name, failures);
                check(mekanism.api.gas.GasRegistry.getGas("clean" + name) != null, "Mekanism clean gas missing: " + name, failures);
                check(mekanism.common.recipe.RecipeHandler.Recipe.ENRICHMENT_CHAMBER.containsRecipe(material.getBlockItemStack(Names.ORE)),
                      "Mekanism enrichment missing: " + name, failures);
            }
        }
    }
}

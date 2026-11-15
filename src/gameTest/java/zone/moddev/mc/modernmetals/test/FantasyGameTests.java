package zone.moddev.mc.modernmetals.test;

import java.util.List;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import zone.moddev.mc.modernmetals.config.ContentMode;
import zone.moddev.mc.modernmetals.config.ModernMetalsConfig;
import zone.moddev.mc.modernmetals.recipe.RecipeDiscovery;

@GameTestHolder("modernmetals_tests")
@PrefixGameTestTemplate(false)
public final class FantasyGameTests {
    @SuppressWarnings("unchecked")
    @GameTest(template="empty", batch="fantasy-reload", timeoutTicks=1200)
    public static void earnedRecipesSurviveFantasyReloads(GameTestHelper helper) throws Exception {
        var server=helper.getLevel().getServer();
        var player=ProcessingGameTests.survivalPlayer(helper);
        var key=ResourceLocation.parse("modernmetals:plutonium_pickaxe");
        var advKey=ResourceLocation.parse("modernmetals:recipes/plutonium_pickaxe");
        var own=ModernMetalsConfig.CONTENT_MODE.get();
        var base=ModList.get().isLoaded("basemetals") ? (ModConfigSpec.ConfigValue<String>)Class.forName("zone.moddev.mc.basemetals.config.BaseMetalsConfig").getField("CONTENT_MODE").get(null):null;
        String savedBase=base==null?null:base.get();
        if(base!=null || ModernMetalsConfig.activeMode()==ContentMode.LOW_FANTASY) {
            if(base!=null)helper.assertTrue(ModernMetalsConfig.inheritsBaseMetals(),"Base Metals owns Fantasy Level");
            boolean low=ModernMetalsConfig.activeMode()==ContentMode.LOW_FANTASY;
            helper.assertTrue(server.getRecipeManager().byKey(key).isEmpty()==low,"Recipes follow the startup Fantasy Level");
            helper.assertTrue((server.getAdvancements().get(advKey)==null)==low,"Discovery follows the startup Fantasy Level");
            server.getPlayerList().remove(player);
            helper.succeed();
            return;
        }
        var advancement=server.getAdvancements().get(advKey);
        player.getAdvancements().award(advancement,"has_recipe");
        var recipe=server.getRecipeManager().byKey(key).orElseThrow();
        player.resetRecipes(List.of(recipe));
        player.getInventory().clearContent();
        ModernMetalsConfig.CONTENT_MODE.set(base==null?ContentMode.LOW_FANTASY:ContentMode.HIGH_FANTASY);
        ModernMetalsConfig.CONTENT_MODE.clearCache();
        if(base!=null)base.set("low_fantasy");
        helper.assertTrue(ModernMetalsConfig.activeMode()==ContentMode.LOW_FANTASY,"Base Fantasy inheritance or standalone setting");
        try {
            var lowReload=server.reloadResources(server.getPackRepository().getSelectedIds());
            server.managedBlock(lowReload::isDone); lowReload.join();
            helper.assertTrue(server.getRecipeManager().byKey(key).isEmpty(),"Restricted recipe absent in Low Fantasy");
            helper.assertTrue(server.getAdvancements().get(advKey)==null,"Restricted discovery absent in Low Fantasy");
            ModernMetalsConfig.CONTENT_MODE.set(ContentMode.HIGH_FANTASY);
            ModernMetalsConfig.CONTENT_MODE.clearCache();
            var highReload=server.reloadResources(server.getPackRepository().getSelectedIds());
            server.managedBlock(highReload::isDone); highReload.join();
            var restored=server.getRecipeManager().byKey(key).orElseThrow();
            RecipeDiscovery.discover(player);
            helper.assertTrue(player.getRecipeBook().contains(restored),"Previously earned recipe restored without holding its ingredient");
            helper.succeed();
        } finally {
            ModernMetalsConfig.CONTENT_MODE.set(own);
            ModernMetalsConfig.CONTENT_MODE.clearCache();
            server.getPlayerList().remove(player);
        }
    }
}

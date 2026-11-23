package zone.moddev.mc.modernmetals.recipe;

import java.util.Collections;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import zone.moddev.mc.modernmetals.ModernMetals;

/** Restores discovery for inventories and advancement progress saved before a recipe update. */
@Mod.EventBusSubscriber(modid = ModernMetals.MOD_ID)
public final class RecipeDiscovery {
    private static MinecraftServer pendingReload;
    private RecipeDiscovery() {}

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayerEntity)) return;
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        player.getServer().execute(() -> discover(player));
    }

    @SubscribeEvent
    public static void onServerStarting(FMLServerAboutToStartEvent event) {
        MinecraftServer server = event.getServer();
        server.getResourceManager().addReloadListener((net.minecraft.resources.IResourceManagerReloadListener) resources -> pendingReload = server);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || pendingReload == null) return;
        MinecraftServer server = pendingReload;
        pendingReload = null;
        // Player advancements are reloaded after the resource listeners finish.
        server.getPlayerList().getPlayers().forEach(RecipeDiscovery::discover);
    }

    public static void discover(ServerPlayerEntity player) {
        CriteriaTriggers.INVENTORY_CHANGED.trigger(player, player.inventory);
        for (IRecipe recipe : player.getServer().getRecipeManager().getRecipes()) {
            if (!ModernMetals.MOD_ID.equals(recipe.getId().getNamespace()) || recipe.isDynamic()
                    || player.getRecipeBook().isUnlocked(recipe)) continue;
            Advancement advancement = player.getServer().getAdvancementManager().getAdvancement(
                    new ResourceLocation(ModernMetals.MOD_ID, "recipes/" + recipe.getId().getPath()));
            if (advancement != null && player.getAdvancements().getProgress(advancement).isDone()) {
                player.unlockRecipes(Collections.singletonList(recipe));
            }
        }
    }
}

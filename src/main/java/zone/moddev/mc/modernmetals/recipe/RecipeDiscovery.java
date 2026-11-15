package zone.moddev.mc.modernmetals.recipe;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import zone.moddev.mc.modernmetals.ModernMetals;

/** Rechecks held ingredients and retains earned recipes across data/Fantasy reloads. */
public final class RecipeDiscovery {
    private static volatile boolean reloadPending;
    private RecipeDiscovery() {}

    public static void earned(AdvancementEvent.AdvancementProgressEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        // NeoForge only fires EarnEvent for advancements with a display; recipe unlocks have none.
        if (event.getProgressType() != AdvancementEvent.AdvancementProgressEvent.ProgressType.GRANT
                || !event.getAdvancementProgress().isDone()) return;
        var history = history(player);
        for (var recipe : event.getAdvancement().value().rewards().recipes()) {
            if (ModernMetals.MOD_ID.equals(recipe.getNamespace())) history.putBoolean(recipe.toString(), true);
        }
    }

    private static CompoundTag history(ServerPlayer player) {
        var data = player.getPersistentData();
        if (!data.contains(Player.PERSISTED_NBT_TAG, 10)) data.put(Player.PERSISTED_NBT_TAG, new CompoundTag());
        var persisted = data.getCompound(Player.PERSISTED_NBT_TAG);
        if (!persisted.contains("ModernMetalsRecipes", 10)) persisted.put("ModernMetalsRecipes", new CompoundTag());
        return persisted.getCompound("ModernMetalsRecipes");
    }

    public static void login(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) player.server.execute(() -> discover(player));
    }

    public static void reload(AddReloadListenerEvent event) {
        event.addListener((barrier, resources, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor) ->
                CompletableFuture.completedFuture(null).thenCompose(barrier::wait)
                        .thenRunAsync(() -> reloadPending = true, applyExecutor));
    }

    public static void tick(ServerTickEvent.Post event) {
        if (!reloadPending) return;
        reloadPending = false;
        event.getServer().getPlayerList().getPlayers().forEach(RecipeDiscovery::discover);
    }

    public static void discover(ServerPlayer player) {
        var earned = history(player);
        // A single-item predicate checks the changed stack, not the whole inventory.
        // Replaying EMPTY therefore misses dust already held on login or reload.
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (!stack.isEmpty()) CriteriaTriggers.INVENTORY_CHANGED.trigger(player, player.getInventory(), stack);
        }
        for (var recipe : player.server.getRecipeManager().getRecipes()) {
            if (!ModernMetals.MOD_ID.equals(recipe.id().getNamespace()) || recipe.value().isSpecial()) continue;
            if (player.getRecipeBook().contains(recipe)) earned.putBoolean(recipe.id().toString(), true);
            if (earned.getBoolean(recipe.id().toString())) {
                player.awardRecipes(List.of(recipe));
                continue;
            }
            for (String path : discoveryPaths(recipe.id().getPath())) {
                var advancement = player.server.getAdvancements().get(ResourceLocation.fromNamespaceAndPath(ModernMetals.MOD_ID, path));
                if (advancement != null && player.getAdvancements().getOrStartProgress(advancement).isDone()) {
                    player.awardRecipes(List.of(recipe));
                    earned.putBoolean(recipe.id().toString(), true);
                    break;
                }
            }
        }
    }

    private static List<String> discoveryPaths(String recipe) {
        String regular = "recipes/" + recipe;
        for (String form : List.of("ore", "powder", "blend", "smallpowder", "smallblend")) {
            String suffix = "_" + form + "_smelting";
            if (recipe.endsWith(suffix)) return List.of(regular,
                    "recipes/smelting/" + recipe.substring(0, recipe.length() - suffix.length()) + "_" + form);
        }
        return List.of(regular);
    }
}

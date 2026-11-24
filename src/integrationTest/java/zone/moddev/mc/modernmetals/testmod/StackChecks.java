package zone.moddev.mc.modernmetals.testmod;

import java.util.*;
import net.minecraft.block.Block;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import zone.moddev.mc.modernmetals.content.ModContent;

/** Native shared-registry and survival-mining checks for installed siblings. */
final class StackChecks {
    static int run(MinecraftServer server, ServerWorld world) {
        int checks = 0;
        String owner = ModList.get().isLoaded("basemetals") ? "basemetals" : "modernmetals";
        for (String namespace : new String[]{"basegems", "baseminerals"}) {
            if (!ModList.get().isLoaded(namespace)) continue;
            int recipes = 0;
            for (IRecipe<?> recipe : server.getRecipeManager().getRecipes()) {
                ResourceLocation type = Registry.RECIPE_TYPE.getKey(recipe.getType());
                if (!namespace.equals(recipe.getId().getNamespace()) || !"crushing".equals(type.getPath())) continue;
                require(owner.equals(type.getNamespace()), "Wrong shared crusher for " + recipe.getId());
                require(recipe.getType() == Registry.RECIPE_TYPE.getOrDefault(new ResourceLocation(owner, "crushing")),
                        "Separate recipe-type instance for " + recipe.getId());
                recipes++; checks++;
            }
            require(recipes > 0, "No shared crushing contributions from " + namespace);
            ResourceLocation oreId = new ResourceLocation(namespace, namespace.equals("basegems") ? "jasper_ore" : "salt_ore");
            Block block = ForgeRegistries.BLOCKS.getValue(oreId);
            BlockPos pos = new BlockPos(220, 130, namespace.equals("basegems") ? 220 : 224);
            world.setBlockState(pos, block.getDefaultState(), 3);
            Inventory input = new Inventory(new ItemStack(block));
            List<IRecipe> matches = new ArrayList<>();
            for (IRecipe recipe : server.getRecipeManager().getRecipes()) {
                ResourceLocation type = Registry.RECIPE_TYPE.getKey(recipe.getType());
                if ("crushing".equals(type.getPath()) && recipe.matches(input, world)) matches.add(recipe);
            }
            require(matches.size() == 1, "Ambiguous ore crushing " + oreId + ": " + matches.size());
            ItemStack expected = matches.get(0).getRecipeOutput();
            net.minecraft.entity.player.ServerPlayerEntity player = new net.minecraft.entity.player.ServerPlayerEntity(
                    server, world, new com.mojang.authlib.GameProfile(UUID.randomUUID(), "StackMining"),
                    new net.minecraft.server.management.PlayerInteractionManager(world));
            player.connection = new net.minecraft.network.play.ServerPlayNetHandler(server,
                    new net.minecraft.network.NetworkManager(net.minecraft.network.PacketDirection.SERVERBOUND), player);
            player.interactionManager.setGameType(GameType.SURVIVAL);
            ItemStack hammer = new ItemStack(ModContent.item("galvanizedsteel_crackhammer").get());
            player.setHeldItem(Hand.MAIN_HAND, hammer);
            require(hammer.canHarvestBlock(block.getDefaultState()), "Galvanized steel cannot harvest " + oreId);
            require(hammer.getDestroySpeed(block.getDefaultState()) > 1, "Slow hammer mining " + oreId);
            AxisAlignedBB area = new AxisAlignedBB(pos).grow(1);
            for (ItemEntity entity : world.getEntitiesWithinAABB(ItemEntity.class, area)) entity.remove();
            player.interactionManager.tryHarvestBlock(pos);
            require(world.isAirBlock(pos), "Hammer failed to remove " + oreId);
            int drops = 0;
            for (ItemEntity entity : world.getEntitiesWithinAABB(ItemEntity.class, area)) {
                require(entity.getItem().getItem() == expected.getItem(), "Wrong hammer drops " + oreId);
                drops += entity.getItem().getCount(); entity.remove();
            }
            require(drops == expected.getCount() && hammer.getDamage() == 1, "Hammer output/durability " + oreId);
            checks += 6;
            System.out.println("STACK_CRUSHING PASS mod=" + namespace + " owner=" + owner + " recipes=" + recipes + " ore=" + oreId);
        }
        return checks;
    }
    private static void require(boolean value, String message) {
        if (!value) throw new IllegalStateException(message);
    }
}

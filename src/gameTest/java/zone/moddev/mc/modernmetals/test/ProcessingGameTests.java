package zone.moddev.mc.modernmetals.test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.mojang.authlib.GameProfile;
import io.netty.channel.embedded.EmbeddedChannel;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import zone.moddev.mc.lib.api.MaterialEventContexts;
import zone.moddev.mc.lib.common.runtime.CrackhammerEvents;
import zone.moddev.mc.modernmetals.content.ModContent;
import zone.moddev.mc.modernmetals.recipe.RecipeDiscovery;

@GameTestHolder("modernmetals_tests")
@PrefixGameTestTemplate(false)
public final class ProcessingGameTests {
    private static ResourceLocation id(String name) { return ResourceLocation.fromNamespaceAndPath("modernmetals", name); }
    private static ItemStack item(String name) { return new ItemStack(BuiltInRegistries.ITEM.get(id(name))); }

    static ServerPlayer survivalPlayer(GameTestHelper helper) {
        UUID uuid = UUID.randomUUID();
        var cookie = CommonListenerCookie.createInitial(new GameProfile(uuid, "mmd-" + uuid.toString().substring(0, 8)), false);
        var level = helper.getLevel();
        var player = new ServerPlayer(level.getServer(), level, cookie.gameProfile(), cookie.clientInformation());
        var connection = new Connection(PacketFlow.SERVERBOUND);
        new EmbeddedChannel(connection);
        level.getServer().getPlayerList().placeNewPlayer(connection, player, cookie);
        player.gameMode.changeGameModeForPlayer(GameType.SURVIVAL);
        return player;
    }

    @GameTest(template = "empty", timeoutTicks = 100)
    public static void oldWallPaletteAndProjectileSurvive(GameTestHelper helper) {
        var level = helper.getLevel();
        var pos = helper.absolutePos(new BlockPos(2,2,2));
        var wall = BuiltInRegistries.BLOCK.get(id("aluminum_wall"));
        level.setBlockAndUpdate(pos, wall.defaultBlockState());
        var chunk = level.getChunkAt(pos);
        var saved = net.minecraft.world.level.chunk.storage.ChunkSerializer.write(level, chunk);
        for (var section : saved.getList("sections", 10)) {
            for (var entry : ((net.minecraft.nbt.CompoundTag)section).getCompound("block_states").getList("palette",10)) {
                var palette = (net.minecraft.nbt.CompoundTag)entry;
                if (palette.getString("Name").equals("modernmetals:aluminum_wall")) {
                    for (String side : List.of("north","east","south","west")) palette.getCompound("Properties").putString(side, side.equals("north")?"true":"false");
                }
            }
        }
        var restored = net.minecraft.world.level.chunk.storage.ChunkSerializer.read(level,level.getPoiManager(),
                new net.minecraft.world.level.chunk.storage.RegionStorageInfo("modernmetals-test",level.dimension(),"chunk"),chunk.getPos(),saved);
        helper.assertTrue(restored.getBlockState(pos).is(wall), "Old wall must not erase its section");
        helper.assertTrue(restored.getBlockState(pos).getValue(net.minecraft.world.level.block.WallBlock.NORTH_WALL)==net.minecraft.world.level.block.state.properties.WallSide.LOW,"Old boolean wall connection survives");
        helper.succeed();
    }

    @GameTest(template = "empty", timeoutTicks = 100)
    public static void oldProjectileRetainsItsAmmunition(GameTestHelper helper) {
        var level = helper.getLevel();
        var entityTag = new net.minecraft.nbt.CompoundTag();
        entityTag.putString("id","modernmetals:custom_bolt");
        var ammo = new net.minecraft.nbt.CompoundTag(); ammo.putString("id","modernmetals:titanium_bolt"); ammo.putByte("Count",(byte)1);
        entityTag.put("Ammunition",ammo);
        var entity = net.minecraft.world.entity.EntityType.loadEntityRecursive(entityTag,level,e->e);
        helper.assertTrue(entity instanceof net.minecraft.world.entity.projectile.AbstractArrow,"Old projectile type must load");
        var after = new net.minecraft.nbt.CompoundTag(); entity.saveWithoutId(after);
        helper.assertTrue(after.getCompound("item").getString("id").equals("modernmetals:titanium_bolt"),"Old projectile retains exact ammunition");
        helper.succeed();
    }

    @GameTest(template = "empty", timeoutTicks = 100)
    public static void rawLootAndHammerIgnoreFortune(GameTestHelper helper) {
        var level = helper.getLevel();
        var player = survivalPlayer(helper);
        var enchantments = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        int checked = 0;
        for (String name : ModContent.materials().keySet()) {
            if (!BuiltInRegistries.ITEM.containsKey(id(name + "_raw"))) continue;
            var state = BuiltInRegistries.BLOCK.get(id(name + "_ore")).defaultBlockState();
            var pos = helper.absolutePos(new BlockPos(1, 2, 1));
            ItemStack pickaxe = new ItemStack(Items.NETHERITE_PICKAXE);
            var normal = Block.getDrops(state, level, pos, null, player, pickaxe);
            helper.assertTrue(normal.size() == 1 && normal.getFirst().is(item(name + "_raw").getItem()) && normal.getFirst().getCount() == 1, "Raw pickaxe drop " + name);
            pickaxe.enchant(enchantments.getHolderOrThrow(Enchantments.SILK_TOUCH), 1);
            var silk = Block.getDrops(state, level, pos, null, player, pickaxe);
            helper.assertTrue(silk.size() == 1 && silk.getFirst().is(state.getBlock().asItem()), "Silk Touch " + name);
            ItemStack hammer = item("titanium_crackhammer");
            hammer.enchant(enchantments.getHolderOrThrow(Enchantments.FORTUNE), 3);
            player.setItemInHand(InteractionHand.MAIN_HAND, hammer);
            var drops = new ArrayList<ItemEntity>();
            drops.add(new ItemEntity(level,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(item(name + "_raw").getItem(),4)));
            CrackhammerEvents.harvest(player, new MaterialEventContexts.Harvest(level,pos,state,hammer.copy(),drops));
            helper.assertTrue(drops.size() == 1 && drops.getFirst().getItem().is(item(name + "_powder").getItem()) && drops.getFirst().getItem().getCount() == 2, "Hammer must crush the ore once, without Fortune: " + name);
            helper.assertTrue(hammer.getDamageValue() == 1, "One crushing operation durability " + name);
            checked++;
        }
        helper.assertTrue(checked == 17, "All seventeen raw ores");
        level.getServer().getPlayerList().remove(player);
        helper.succeed();
    }

    @GameTest(template = "empty", timeoutTicks = 100)
    public static void furnaceAccumulatesAllRecyclingOutputs(GameTestHelper helper) {
        BlockPos local = new BlockPos(1, 2, 1);
        helper.setBlock(local, Blocks.FURNACE);
        var pos = helper.absolutePos(local);
        var level = helper.getLevel();
        FurnaceBlockEntity furnace = (FurnaceBlockEntity) level.getBlockEntity(pos);
        furnace.setItem(1, new ItemStack(Items.COAL, 16));
        for (int operation = 0; operation < 2; operation++) {
            furnace.setItem(0, item("aluminum_axe"));
            for (int tick = 0; tick < 240; tick++) AbstractFurnaceBlockEntity.serverTick(level,pos,level.getBlockState(pos),furnace);
            helper.assertTrue(furnace.getItem(2).is(item("aluminum_ingot").getItem()), "Recycled metal output");
            helper.assertTrue(furnace.getItem(2).getCount() == 3 * (operation + 1), "Furnace must accumulate full output counts");
        }
        helper.succeed();
    }

    @GameTest(template = "empty", timeoutTicks = 100)
    public static void existingDustInventoryRevealsFurnaceRecipes(GameTestHelper helper) {
        var player = survivalPlayer(helper);
        var server = helper.getLevel().getServer();
        var recipe = server.getRecipeManager().byKey(id("beryllium_powder_smelting")).orElseThrow();
        var advancement = server.getAdvancements().get(id("recipes/beryllium_powder_smelting"));
        player.getInventory().setItem(0, item("beryllium_powder"));
        player.resetRecipes(List.of(recipe));
        for (String criterion : advancement.value().criteria().keySet()) player.getAdvancements().revoke(advancement, criterion);
        RecipeDiscovery.discover(player);
        helper.assertTrue(player.getRecipeBook().contains(recipe), "Dust held before login/reload must reveal its furnace recipe");
        server.getPlayerList().remove(player);
        helper.succeed();
    }

    @GameTest(template = "empty", timeoutTicks = 100)
    public static void oldFurnaceAdvancementRestoresRenamedRecipe(GameTestHelper helper) {
        var player=survivalPlayer(helper);
        var server=helper.getLevel().getServer();
        var recipe=server.getRecipeManager().byKey(id("beryllium_powder_smelting")).orElseThrow();
        var old=server.getAdvancements().get(id("recipes/smelting/beryllium_powder"));
        helper.assertTrue(old!=null,"Old furnace advancement ID exists");
        player.getAdvancements().award(old,"has_ingredient_1");
        var current=server.getAdvancements().get(id("recipes/beryllium_powder_smelting"));
        for(String key:current.value().criteria().keySet())player.getAdvancements().revoke(current,key);
        player.getPersistentData().remove(net.minecraft.world.entity.player.Player.PERSISTED_NBT_TAG);
        player.getInventory().clearContent();
        player.resetRecipes(List.of(recipe));
        RecipeDiscovery.discover(player);
        helper.assertTrue(player.getRecipeBook().contains(recipe),"Earned old furnace advancement restores current recipe without ingredients");
        server.getPlayerList().remove(player);
        helper.succeed();
    }

    @GameTest(template = "empty", timeoutTicks = 100)
    public static void auditedMiningLevelsApplyToOresAndTools(GameTestHelper helper) {
        helper.assertTrue(item("galvanizedsteel_pickaxe").isCorrectToolForDrops(Blocks.DIAMOND_ORE.defaultBlockState()), "Galvanized steel must meet iron tier");
        helper.assertTrue(!item("aluminum_pickaxe").isCorrectToolForDrops(Blocks.DIAMOND_ORE.defaultBlockState()), "Soft aluminum must not meet iron tier");
        var ore = BuiltInRegistries.BLOCK.get(id("chromium_ore")).defaultBlockState();
        var block = BuiltInRegistries.BLOCK.get(id("chromium_block")).defaultBlockState();
        helper.assertTrue(new ItemStack(Items.STONE_PICKAXE).isCorrectToolForDrops(ore), "Chromium ore needs stone, independently of solid metal");
        helper.assertTrue(!new ItemStack(Items.STONE_PICKAXE).isCorrectToolForDrops(block), "Solid chromium needs iron");
        helper.succeed();
    }
}

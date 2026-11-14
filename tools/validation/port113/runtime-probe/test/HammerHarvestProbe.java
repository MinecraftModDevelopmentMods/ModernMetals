package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.registries.ForgeRegistries;

/** Packaged survival regression probe; never include this mod in release jars. */
@Mod("hammerharvestprobe")
public final class HammerHarvestProbe {
    private final List<String> failures = new ArrayList<>();
    private int checks;
    private int positions;
    private WorldServer world;
    private FakePlayer player;

    public HammerHarvestProbe() { MinecraftForge.EVENT_BUS.addListener(this::started); }

    private void started(FMLServerStartedEvent event) {
        world = event.getServer().getWorld(DimensionType.OVERWORLD);
        player = new FakePlayer(world, new GameProfile(UUID.randomUUID(), "HammerHarvestProbe"));
        new NetHandlerPlayServer(event.getServer(), new NetworkManager(EnumPacketDirection.SERVERBOUND), player);
        player.interactionManager.setGameType(GameType.SURVIVAL);
        player.onGround = true;
        try {
            Map<String, Integer> gems = new LinkedHashMap<>();
            add(gems, 0, "amber lepidolite");
            add(gems, 1, "agate carnelian garnet jade malachite moldavite moonstone opal peridot tanzanite turquoise");
            add(gems, 2, "alexandrite amethyst ametrine aquamarine beryl bluetopaz citrine goldenberyl heliodor indicolite iolite jasper morganite onyx ruby sapphire spinel topaz violetsapphire");
            add(gems, 3, "blackdiamond");
            check(gems.size() == 33, "Incomplete legacy gem catalogue");
            List<String> tools = new ArrayList<>(Arrays.asList("minecraft:stone_pickaxe", "minecraft:iron_pickaxe"));
            if (ModList.get().isLoaded("modernmetals")) tools.addAll(Arrays.asList(
                    "modernmetals:galvanizedsteel_crackhammer", "modernmetals:stainlesssteel_crackhammer",
                    "modernmetals:titanium_crackhammer", "modernmetals:boron_crackhammer"));
            if (ModList.get().isLoaded("basemetals")) tools.addAll(Arrays.asList(
                    "basemetals:stone_crackhammer", "basemetals:iron_crackhammer"));
            for (Map.Entry<String, Integer> gem : gems.entrySet()) {
                String name = gem.getKey();
                for (String form : Arrays.asList("ore", "block", "plate", "bars", "door", "trapdoor",
                        "button", "lever", "pressure_plate", "slab", "stairs", "wall")) {
                    Block block = block("basegems:" + name + "_" + form);
                    for (IBlockState state : block.getStateContainer().getValidStates())
                        check(state.getHarvestLevel() == gem.getValue(), name + "_" + form
                                + " tier " + state.getHarvestLevel() + " expected " + gem.getValue());
                }
                check(block("basegems:double_" + name + "_slab").getDefaultState().getHarvestLevel()
                        == gem.getValue(), name + " double slab tier");
                for (String tool : tools) mine(name, gem.getValue(), tool);
            }
            event.getServer().saveAllWorlds(true);
        } catch (Throwable error) {
            failures.add(error.toString());
            error.printStackTrace();
        } finally {
            for (String failure : failures) System.out.println("HAMMER_HARVEST_CASE FAIL " + failure);
            System.out.println("HAMMER_HARVEST_PROBE " + (failures.isEmpty() ? "PASS" : "FAIL")
                    + " checks=" + checks + " failures=" + failures.size());
            if (event.getServer().isDedicatedServer()) event.getServer().initiateShutdown();
        }
    }

    private void mine(String gem, int requiredTier, String toolId) {
        BlockPos pos = new BlockPos((positions % 12) * 3, 180, (positions++ / 12) * 3);
        IBlockState state = block("basegems:" + gem + "_ore").getDefaultState();
        world.setBlockState(pos, state, 3);
        Item toolItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(toolId));
        ItemStack tool = new ItemStack(toolItem);
        player.setHeldItem(EnumHand.MAIN_HAND, tool);
        int tier = toolId.contains("boron") || toolId.contains("stone_") ? 1 : 2;
        boolean expected = tier >= requiredTier;
        boolean canHarvest = state.canHarvestBlock(world, pos, player);
        float speed = toolItem.getDestroySpeed(tool, state);
        check(canHarvest == expected, toolId + " harvest permission for " + gem);
        boolean hammer = toolId.endsWith("crackhammer");
        if (hammer) check(expected ? speed > 1.0F : speed == 1.0F,
                toolId + " speed " + speed + " on " + gem + " expected " + (expected ? ">1" : "1"));
        player.interactionManager.tryHarvestBlock(pos);
        Map<String, Integer> drops = new HashMap<>();
        for (EntityItem entity : world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos).grow(0.4))) {
            ItemStack stack = entity.getItem();
            drops.merge(stack.getItem().getRegistryName().toString(), stack.getCount(), Integer::sum);
            entity.remove();
        }
        String output = "basegems:" + gem + (hammer ? "_powder" : "_ingot");
        check(expected ? drops.size() == 1 && drops.getOrDefault(output, 0) == (hammer ? 2 : 1)
                : drops.isEmpty(), toolId + " mining " + gem + " produced " + drops);
        if (expected && hammer) check(tool.getDamage() == 1, toolId + " durability on " + gem);
        check(world.isAirBlock(pos), "Ore remained after survival break: " + gem);
        if ("jasper".equals(gem)) System.out.println("HAMMER_JASPER " + toolId + " tier=" + tier
                + " required=" + requiredTier + " harvest=" + canHarvest + " speed=" + speed + " drops=" + drops);
    }

    private static void add(Map<String, Integer> gems, int tier, String names) {
        for (String name : names.split(" ")) gems.put(name, tier);
    }

    private static Block block(String id) {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(id));
    }

    private void check(boolean condition, String message) {
        checks++;
        if (!condition) failures.add(message);
    }
}

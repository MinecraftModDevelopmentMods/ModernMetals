package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Enchantments;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.registries.ForgeRegistries;

/** Isolated packaged probe. Test resources and this class stay out of the mod jars. */
@Mod("metalminingprobe")
public final class MetalMiningProbe {
    private final List<String> failures = new ArrayList<>();
    private final Map<String, int[]> profiles = new HashMap<>();
    private int checks;
    private int blocks;
    private int tools;
    private WorldServer world;
    private FakePlayer player;

    public MetalMiningProbe() { MinecraftForge.EVENT_BUS.addListener(this::started); }

    private void started(FMLServerStartedEvent event) {
        world = event.getServer().getWorld(DimensionType.OVERWORLD);
        player = new FakePlayer(world, new GameProfile(UUID.randomUUID(), "MetalMiningProbe"));
        new NetHandlerPlayServer(event.getServer(), new NetworkManager(EnumPacketDirection.SERVERBOUND), player);
        player.interactionManager.setGameType(GameType.SURVIVAL);
        player.onGround = true;
        try {
            JsonObject root = new JsonParser().parse(new InputStreamReader(
                    getClass().getResourceAsStream("/mining-profiles.json"), StandardCharsets.UTF_8)).getAsJsonObject();
            for (Map.Entry<String, JsonElement> mod : root.entrySet()) {
                String namespace = mod.getKey().equals("modern") ? "modernmetals" : "basemetals";
                for (Map.Entry<String, JsonElement> row : mod.getValue().getAsJsonObject().entrySet()) {
                    profiles.put(namespace + ":" + row.getKey(), new int[]{row.getValue().getAsJsonArray().get(0).getAsInt(),
                            row.getValue().getAsJsonArray().get(1).getAsInt(), row.getValue().getAsJsonArray().get(2).getAsInt()});
                }
            }
            savedStack();
            List<String> targets = new ArrayList<>();
            for (String target : new String[]{"minecraft:iron_ore", "minecraft:diamond_ore", "minecraft:obsidian",
                    "modernmetals:boron_ore", "modernmetals:tungsten_block", "basemetals:copper_ore",
                    "basemetals:adamantine_ore"}) targets.add(target);
            for (Block block : ForgeRegistries.BLOCKS) {
                if (block.getRegistryName().getNamespace().equals("basegems")
                        && block.getRegistryName().getPath().endsWith("_ore")) targets.add(block.getRegistryName().toString());
            }
            for (Block block : ForgeRegistries.BLOCKS) {
                String material = material(block.getRegistryName());
                if (material == null) continue;
                int required = profiles.get(material)[block.getRegistryName().getPath().endsWith("_ore") ? 2 : 1];
                boolean checked = false;
                for (IBlockState state : block.getStateContainer().getValidStates()) {
                    if (state.getHarvestTool() != ToolType.PICKAXE) continue;
                    check(state.getHarvestLevel() == required, block.getRegistryName() + " requires "
                            + state.getHarvestLevel() + " instead of " + required);
                    checked = true;
                }
                if (checked) blocks++;
            }
            for (Item item : ForgeRegistries.ITEMS) {
                String material = material(item.getRegistryName());
                if (material == null) continue;
                ItemStack stack = new ItemStack(item);
                int expected = profiles.get(material)[0];
                boolean checked = false;
                for (ToolType type : item.getToolTypes(stack)) {
                    check(item.getHarvestLevel(stack, type, player, null) == expected,
                            item.getRegistryName() + " " + type + " tier mismatch");
                    checked = true;
                }
                if (!checked) continue;
                tools++;
                if (item.getToolTypes(stack).contains(ToolType.PICKAXE)) {
                    player.setHeldItem(EnumHand.MAIN_HAND, stack);
                    for (String target : targets) {
                        ResourceLocation id = new ResourceLocation(target);
                        if (!ForgeRegistries.BLOCKS.containsKey(id)) continue;
                        IBlockState state = ForgeRegistries.BLOCKS.getValue(id).getDefaultState();
                        BlockPos pos = new BlockPos(0, 180, 0);
                        world.setBlockState(pos, state, 3);
                        check(state.canHarvestBlock(world, pos, player) == (expected >= state.getHarvestLevel()),
                                item.getRegistryName() + " harvest permission for " + target);
                    }
                }
            }
            if (Boolean.getBoolean("miningaudit.low")) {
                String namespace = ForgeRegistries.ITEMS.containsKey(new ResourceLocation("basemetals:steel_crackhammer"))
                        ? "basemetals" : "modernmetals";
                String owner = namespace.equals("basemetals") ? "BaseMetalsConfig" : "ModernMetalsConfig";
                Object mode = Class.forName("zone.moddev.mc." + namespace + ".config." + owner).getMethod("activeMode").invoke(null);
                check(mode.toString().toLowerCase(java.util.Locale.ROOT).contains("low"), "Low Fantasy was not loaded: " + mode);
            }
            jasper();
            check(blocks > 0 && tools > 0, "No registered metal blocks or tools were checked");
            event.getServer().saveAllWorlds(true);
        } catch (Throwable error) {
            failures.add(error.toString());
            error.printStackTrace();
        } finally {
            for (String failure : failures) System.out.println("METAL_MINING_CASE FAIL " + failure);
            System.out.println("METAL_MINING_PROBE " + (failures.isEmpty() ? "PASS" : "FAIL")
                    + " checks=" + checks + " blocks=" + blocks + " tools=" + tools + " failures=" + failures.size());
            if (event.getServer().isDedicatedServer()) event.getServer().initiateShutdown();
        }
    }

    private String material(ResourceLocation id) {
        for (String name : profiles.keySet()) {
            ResourceLocation material = new ResourceLocation(name);
            if (id.getNamespace().equals(material.getNamespace()) && (id.getPath().startsWith(material.getPath() + "_")
                    || id.getPath().startsWith("double_" + material.getPath() + "_"))) return name;
        }
        return null;
    }

    private void savedStack() throws Exception {
        boolean modern = ForgeRegistries.ITEMS.containsKey(new ResourceLocation("modernmetals:galvanizedsteel_crackhammer"));
        String id = modern ? "modernmetals:galvanizedsteel_crackhammer" : "basemetals:tin_crackhammer";
        File file = new File("mining-stack.nbt");
        NBTTagCompound stored;
        if (file.isFile()) {
            try (FileInputStream input = new FileInputStream(file)) { stored = CompressedStreamTools.readCompressed(input); }
        } else {
            ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(id)));
            stack.setDamage(7);
            stack.setDisplayName(new TextComponentString("Mining audit hammer"));
            stack.addEnchantment(Enchantments.UNBREAKING, 3);
            stack.getOrCreateTag().setString("AuditMarker", "saved before mining-policy update");
            stored = stack.write(new NBTTagCompound());
            try (FileOutputStream output = new FileOutputStream(file)) { CompressedStreamTools.writeCompressed(stored, output); }
        }
        ItemStack loaded = ItemStack.read(stored);
        check(stored.equals(loaded.write(new NBTTagCompound())), "Saved item NBT changed");
        check(loaded.getDamage() == 7 && loaded.getTag().getString("AuditMarker").equals("saved before mining-policy update"),
                "Saved item lost durability or custom data");
        int expected = modern ? 2 : 0;
        int actual = loaded.getItem().getHarvestLevel(loaded, ToolType.PICKAXE, player, null);
        check(actual == expected, "Saved " + id + " tier " + actual + " expected " + expected);
        System.out.println("METAL_SAVED_STACK " + id + " tier=" + actual + " damage=" + loaded.getDamage());
    }

    private void jasper() {
        ResourceLocation ore = new ResourceLocation("basegems:jasper_ore");
        ResourceLocation hammer = new ResourceLocation("modernmetals:galvanizedsteel_crackhammer");
        if (!ForgeRegistries.BLOCKS.containsKey(ore) || !ForgeRegistries.ITEMS.containsKey(hammer)) return;
        BlockPos pos = new BlockPos(5, 180, 5);
        world.setBlockState(pos, ForgeRegistries.BLOCKS.getValue(ore).getDefaultState(), 3);
        ItemStack tool = new ItemStack(ForgeRegistries.ITEMS.getValue(hammer));
        player.setHeldItem(EnumHand.MAIN_HAND, tool);
        player.interactionManager.tryHarvestBlock(pos);
        int count = 0;
        for (EntityItem drop : world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos).grow(0.4))) {
            check(drop.getItem().getItem().getRegistryName().toString().equals("basegems:jasper_powder"), "Wrong Jasper output");
            count += drop.getItem().getCount();
            drop.remove();
        }
        check(count == 2 && tool.getDamage() == 1, "Galvanized steel did not crush Jasper with one durability: " + count);
        System.out.println("METAL_JASPER galvanizedsteel powder=" + count + " damage=" + tool.getDamage());
    }

    private void check(boolean condition, String message) {
        checks++;
        if (!condition) failures.add(message);
    }
}

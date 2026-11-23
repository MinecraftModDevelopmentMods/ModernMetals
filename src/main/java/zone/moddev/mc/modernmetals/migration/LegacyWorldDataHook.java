package zone.moddev.mc.modernmetals.migration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;


import com.mojang.datafixers.Dynamic;

import cpw.mods.modlauncher.api.INameMappingService;
import zone.moddev.mc.modernmetals.ModernMetals;
import zone.moddev.mc.modernmetals.content.ModContent;
import zone.moddev.mc.modernmetals.content.PlateBlock;

import net.minecraft.block.Block;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.SlabType;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.fixes.BlockStateFlatteningMap;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.WorldPersistenceHooks;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

/** Translates Modern Metals data before the Forge 25 flattening pass. */
public final class LegacyWorldDataHook implements WorldPersistenceHooks.WorldPersistenceHook {
    private static final String SAVE_KEY = "ModernMetalsLegacyRegistry";
    private static final String CHUNK_MARKER = "ModernMetalsLegacyPreserveChunk";
    private static final Map<String, String> VANILLA_BLOCK_ENTITY_IDS = vanillaBlockEntityIds();
    private static final Map<Integer, Dynamic<?>> ownedStates = new HashMap<>();
    private static final Map<Integer, String> itemIds = new HashMap<>();
    private static volatile Map<Integer, Set<Long>> existingChunks = Collections.emptyMap();
    private static File activeWorld;
    private static CompoundNBT savedRegistry;
    private static boolean registered;

    private LegacyWorldDataHook() {}

    public static synchronized void register() {
        if (!registered) {
            WorldPersistenceHooks.addHook(new LegacyWorldDataHook());
            registered = true;
        }
    }

    /** Read the world's own numeric IDs before Minecraft starts flattening any of its data. */
    public static synchronized void prepareLegacyWorld(File levelDat) {
        if (!levelDat.isFile()) return;
        try (FileInputStream input = new FileInputStream(levelDat)) {
            CompoundNBT root = CompressedStreamTools.readCompressed(input);
            File world = levelDat.getParentFile().getCanonicalFile();
            CompoundNBT legacy = root.getCompound("FML").getCompound("Registries");
            CompoundNBT persisted = root.getCompound(SAVE_KEY);
            if (persisted.contains("minecraft:blocks", 10)) legacy = persisted;
            installRegistry(world, legacy);
            if (savedRegistry == null) return;
            if (migrateLegacyItems(root.getCompound("Data").getCompound("Player"))) {
                writeWithBackup(levelDat, root);
            }
            File[] players = new File(world, "playerdata").listFiles((dir, name) -> name.endsWith(".dat"));
            if (players != null) {
                for (File player : players) {
                    try (FileInputStream playerInput = new FileInputStream(player)) {
                        CompoundNBT data = CompressedStreamTools.readCompressed(playerInput);
                        if (migrateLegacyItems(data)) writeWithBackup(player, data);
                    }
                }
            }
        } catch (IOException error) {
            throw new IllegalStateException("Could not safely read the legacy world at " + levelDat, error);
        }
    }

    @Override public String getModId() { return SAVE_KEY; }

    @Override
    public CompoundNBT getDataForWriting(SaveHandler handler, WorldInfo info) {
        if (savedRegistry == null || !sameWorld(handler.getWorldDirectory())) return new CompoundNBT();
        return savedRegistry.copy();
    }

    @Override
    public void readData(SaveHandler handler, WorldInfo info, CompoundNBT tag) {
        // prepareLegacyWorld installs the table before Forge reaches this callback.
    }

    private static boolean sameWorld(File world) {
        try { return world.getCanonicalFile().equals(activeWorld); }
        catch (IOException error) { throw new IllegalStateException("Could not resolve the world directory", error); }
    }

    private static void installRegistry(File world, CompoundNBT registries) throws IOException {
        CompoundNBT blocks = registries.getCompound("minecraft:blocks");
        Map<Integer, String> blockIds = ownedIds(blocks);
        if (world.equals(activeWorld) && savedRegistry != null && blockIds.isEmpty()) return;
        Dynamic<?>[] table = expandFlatteningTable(0);
        for (Map.Entry<Integer, Dynamic<?>> entry : ownedStates.entrySet()) {
            if (table[entry.getKey()] == entry.getValue()) table[entry.getKey()] = null;
        }
        ownedStates.clear();
        itemIds.clear();
        existingChunks = Collections.emptyMap();
        activeWorld = world;
        savedRegistry = null;
        if (blockIds.isEmpty()) return;

        // Resolve all target names before writing anything to disk or letting DFU consume the table.
        for (Map.Entry<Integer, String> entry : blockIds.entrySet()) {
            int numeric = entry.getKey();
            String path = entry.getValue();
            String target = path.startsWith("double_") ? path.substring(7) : canonicalPath(path);
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ModernMetals.MOD_ID, target));
            if (block == null || block == net.minecraft.block.Blocks.AIR) {
                throw new IllegalStateException("No flattened Modern Metals block for " + path + " (ID " + numeric + ")");
            }
            for (int meta = 0; meta < 16; meta++) {
                BlockState state = legacyState(block, path, meta);
                ownedStates.put((numeric << 4) | meta,
                        BlockStateFlatteningMap.makeDynamic(NBTUtil.writeBlockState(state).toString()));
            }
        }
        itemIds.putAll(ownedIds(registries.getCompound("minecraft:items")));
        int max = Collections.max(ownedStates.keySet());
        table = expandFlatteningTable(max + 1);
        for (Map.Entry<Integer, Dynamic<?>> entry : ownedStates.entrySet()) {
            table[entry.getKey()] = entry.getValue();
        }
        savedRegistry = new CompoundNBT();
        savedRegistry.put("minecraft:blocks", compactSnapshot(blockIds));
        savedRegistry.put("minecraft:items", compactSnapshot(itemIds));
        if (registries.contains("protectedChunks", 10)) {
            existingChunks = readChunkIndex(registries.getCompound("protectedChunks"));
        } else {
            existingChunks = indexRegions(world);
        }
        savedRegistry.put("protectedChunks", writeChunkIndex(existingChunks));
        ModernMetals.LOGGER.info("Prepared {} legacy Modern Metals states from the saved registry in '{}'",
                ownedStates.size(), world);
    }

    private static CompoundNBT compactSnapshot(Map<Integer, String> source) {
        ListNBT list = new ListNBT();
        for (Map.Entry<Integer, String> entry : source.entrySet()) {
            CompoundNBT id = new CompoundNBT();
            id.putString("K", "modernmetals:" + entry.getValue());
            id.putInt("V", entry.getKey());
            list.add(id);
        }
        CompoundNBT snapshot = new CompoundNBT();
        snapshot.put("ids", list);
        return snapshot;
    }

    private static Map<Integer, String> ownedIds(CompoundNBT registry) {
        Map<Integer, String> result = new LinkedHashMap<>();
        ListNBT entries = registry.getList("ids", 10);
        for (int i = 0; i < entries.size(); i++) {
            CompoundNBT entry = entries.getCompound(i);
            String name = entry.getString("K").toLowerCase(Locale.ROOT);
            if (!name.startsWith("modernmetals:")) continue;
            int numeric = entry.getInt("V");
            if (numeric < 0 || numeric > 65535) {
                throw new IllegalStateException("Invalid saved Modern Metals registry ID " + numeric);
            }
            String previous = result.put(numeric, name.substring("modernmetals:".length()));
            if (previous != null) {
                throw new IllegalStateException("Duplicate saved Modern Metals registry ID " + numeric);
            }
        }
        return result;
    }

    public static synchronized void prepareLegacyChunk(CompoundNBT root) {
        if (root == null || savedRegistry == null || root.getInt("DataVersion") >= 1451
                || !root.contains("Level", 10)) return;
        CompoundNBT level = root.getCompound("Level");
        migrateLegacyItems(level);
        // An unpopulated source chunk can contain a player's only chest or placed block.
        level.putBoolean("TerrainPopulated", true);
        level.putBoolean("LightPopulated", true);
        level.putBoolean(CHUNK_MARKER, true);
    }

    public static CompoundNBT finalizeLegacyChunk(CompoundNBT root) {
        if (root != null && root.contains("Level", 10)) {
            CompoundNBT level = root.getCompound("Level");
            if (level.getBoolean(CHUNK_MARKER)) {
                LegacyDoorStates.joinHalves(level);
                level.putString("Status", "full");
                level.remove(CHUNK_MARKER);
            }
        }
        return root;
    }

    public static boolean shouldBlockWorldgenWrite(net.minecraft.world.gen.WorldGenRegion region,
            net.minecraft.util.math.BlockPos pos) {
        Map<Integer, Set<Long>> chunks = existingChunks;
        if (chunks.isEmpty()) return false;
        Set<Long> dimension = chunks.get(region.getDimension().getType().getId());
        return dimension != null && ((LongSet) dimension).contains(chunkKey(pos.getX() >> 4, pos.getZ() >> 4));
    }

    private static Map<Integer, Set<Long>> indexRegions(File world) throws IOException {
        Map<Integer, Set<Long>> result = new HashMap<>();
        result.put(0, indexRegionDirectory(new File(world, "region")));
        File[] dimensions = world.listFiles(file -> file.isDirectory() && file.getName().matches("DIM-?[0-9]+"));
        if (dimensions != null) {
            for (File dimension : dimensions) {
                int id = Integer.parseInt(dimension.getName().substring(3));
                result.put(id, indexRegionDirectory(new File(dimension, "region")));
            }
        }
        return result;
    }

    private static Set<Long> indexRegionDirectory(File folder) throws IOException {
        Set<Long> result = new LongOpenHashSet();
        File[] regions = folder.listFiles((dir, name) -> name.matches("r\\.-?[0-9]+\\.-?[0-9]+\\.mca"));
        if (regions == null) return result;
        for (File region : regions) {
            String[] parts = region.getName().split("\\.");
            int originX = Integer.parseInt(parts[1]) * 32;
            int originZ = Integer.parseInt(parts[2]) * 32;
            try (java.io.DataInputStream input = new java.io.DataInputStream(Files.newInputStream(region.toPath()))) {
                for (int index = 0; index < 1024; index++) {
                    if (input.readInt() != 0) result.add(chunkKey(originX + (index & 31), originZ + (index >> 5)));
                }
            }
        }
        return result;
    }

    private static CompoundNBT writeChunkIndex(Map<Integer, Set<Long>> index) {
        CompoundNBT result = new CompoundNBT();
        index.forEach((dimension, chunks) -> {
            long[] values = chunks.stream().mapToLong(Long::longValue).sorted().toArray();
            result.putLongArray(dimension.toString(), values);
        });
        return result;
    }

    private static Map<Integer, Set<Long>> readChunkIndex(CompoundNBT index) {
        Map<Integer, Set<Long>> result = new HashMap<>();
        for (String dimension : index.keySet()) {
            Set<Long> chunks = new LongOpenHashSet();
            for (long chunk : index.getLongArray(dimension)) chunks.add(chunk);
            result.put(Integer.valueOf(dimension), chunks);
        }
        return result;
    }

    static String canonicalPath(String path) {
        if ("alubrass".equals(path)) return "aluminumbrass";
        if (path.endsWith("_door_item")) return path.substring(0, path.length() - 5);
        if (path.endsWith("_fishingrod")) return path.substring(0, path.length() - 10) + "fishing_rod";
        if (path.endsWith("_horsearmor")) return path.substring(0, path.length() - 10) + "horse_armor";
        return path;
    }

    private static boolean migrateLegacyItems(INBT value) {
        boolean changed = false;
        if (value instanceof CompoundNBT) {
            CompoundNBT compound = (CompoundNBT) value;
            String id = compound.getString("id");
            String blockEntity = VANILLA_BLOCK_ENTITY_IDS.get(id);
            if (blockEntity != null && !compound.contains("Count", 99)) {
                compound.putString("id", blockEntity);
                changed = true;
            }
            if (compound.contains("Count", 99)) changed |= migrateStack(compound, id);
            for (String key : new ArrayList<>(compound.keySet())) {
                changed |= migrateLegacyItems(compound.get(key));
            }
        } else if (value instanceof ListNBT) {
            ListNBT list = (ListNBT) value;
            for (int index = 0; index < list.size(); index++) changed |= migrateLegacyItems(list.get(index));
        }
        return changed;
    }

    private static boolean migrateStack(CompoundNBT stack, String id) {
        String path = null;
        String name = id.toLowerCase(Locale.ROOT);
        if (name.startsWith("modernmetals:")) path = canonicalPath(name.substring(13));
        if (stack.contains("id", 99) && itemIds.containsKey(stack.getInt("id"))) {
            path = canonicalPath(itemIds.get(stack.getInt("id")));
        }
        if ("forge:bucketfilled".equals(name)) {
            CompoundNBT tag = stack.getCompound("tag");
            String fluid = tag.getString("FluidName").toLowerCase(Locale.ROOT);
            boolean explicitlyOwned = fluid.startsWith("modernmetals:");
            String candidate = canonicalPath(explicitlyOwned ? fluid.substring(13) : fluid);
            if (ModContent.fluids().containsKey(candidate)
                    && (explicitlyOwned || !fluid.contains(":"))) {
                path = candidate + "_bucket";
                tag.remove("FluidName");
                tag.remove("Amount");
                if (tag.isEmpty()) stack.remove("tag");
            }
        }
        if (path == null) return false;
        ResourceLocation target = new ResourceLocation(ModernMetals.MOD_ID, path);
        net.minecraft.item.Item item = ForgeRegistries.ITEMS.getValue(target);
        if (item == null || item == net.minecraft.item.Items.AIR) {
            throw new IllegalStateException("No flattened Modern Metals item for " + id);
        }
        boolean changed = !target.toString().equals(id);
        if (changed) stack.putString("id", target.toString());
        if (stack.getInt("Damage") > 0 && item.getMaxDamage() > 0) {
            CompoundNBT tag = stack.getCompound("tag");
            if (!tag.contains("Damage", 99)) {
                tag.putInt("Damage", stack.getInt("Damage"));
                stack.put("tag", tag);
                changed = true;
            }
        }
        return changed;
    }

    private static void writeWithBackup(File file, CompoundNBT data) throws IOException {
        Path path = file.toPath();
        Path backup = path.resolveSibling(file.getName() + ".modernmetals-legacy-backup");
        if (!Files.exists(backup)) Files.copy(path, backup, StandardCopyOption.COPY_ATTRIBUTES);
        Path temp = Files.createTempFile(path.getParent(), "modernmetals-legacy-", ".tmp");
        try {
            try (FileOutputStream output = new FileOutputStream(temp.toFile())) {
                CompressedStreamTools.writeCompressed(data, output);
            }
            Files.move(temp, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } finally {
            Files.deleteIfExists(temp);
        }
    }

    static BlockState legacyState(Block block, String path, int meta) {
        BlockState state = block.getDefaultState();
        if (block instanceof PlateBlock) return state.with(PlateBlock.FACING, Direction.byIndex(meta));
        if (path.startsWith("double_") && block instanceof SlabBlock) return state.with(SlabBlock.TYPE, SlabType.DOUBLE);
        if (block instanceof SlabBlock) return state.with(SlabBlock.TYPE,
                (meta & 8) == 0 ? SlabType.BOTTOM : SlabType.TOP);
        if (block instanceof StairsBlock) {
            Direction facing = Direction.byIndex(5 - (meta & 3));
            return state.with(StairsBlock.FACING, facing)
                    .with(StairsBlock.HALF, (meta & 4) == 0 ? Half.BOTTOM : Half.TOP)
                    .with(StairsBlock.SHAPE, StairsShape.STRAIGHT)
                    .with(StairsBlock.WATERLOGGED, Boolean.FALSE);
        }
        if (block instanceof DoorBlock) {
            if ((meta & 8) != 0) {
                return state.with(DoorBlock.HALF, DoubleBlockHalf.UPPER)
                        .with(DoorBlock.HINGE, (meta & 1) != 0 ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT)
                        .with(DoorBlock.POWERED, Boolean.valueOf((meta & 2) != 0));
            }
            return state.with(DoorBlock.HALF, DoubleBlockHalf.LOWER)
                    .with(DoorBlock.FACING, Direction.byHorizontalIndex(meta & 3).rotateYCCW())
                    .with(DoorBlock.OPEN, Boolean.valueOf((meta & 4) != 0));
        }
        if (block instanceof TrapDoorBlock) {
            Direction[] facing = { Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST };
            return state.with(HorizontalBlock.HORIZONTAL_FACING, facing[meta & 3])
                    .with(TrapDoorBlock.OPEN, Boolean.valueOf((meta & 4) != 0))
                    .with(TrapDoorBlock.HALF, (meta & 8) == 0 ? Half.BOTTOM : Half.TOP)
                    .with(TrapDoorBlock.POWERED, Boolean.FALSE)
                    .with(TrapDoorBlock.WATERLOGGED, Boolean.FALSE);
        }
        if (block instanceof AbstractButtonBlock) return attachedState(state, meta, AbstractButtonBlock.POWERED);
        if (block instanceof LeverBlock) return legacyLeverState(state, meta);
        if (block instanceof PressurePlateBlock) return state.with(PressurePlateBlock.POWERED,
                Boolean.valueOf(meta > 0));
        if (block instanceof FlowingFluidBlock) return state.with(FlowingFluidBlock.LEVEL,
                Integer.valueOf(Math.min(15, meta)));
        return state;
    }

    private static BlockState attachedState(BlockState state, int meta,
            net.minecraft.state.BooleanProperty powered) {
        Direction oldFacing;
        switch (meta & 7) {
            case 0: oldFacing = Direction.DOWN; break;
            case 1: oldFacing = Direction.EAST; break;
            case 2: oldFacing = Direction.WEST; break;
            case 3: oldFacing = Direction.SOUTH; break;
            case 4: oldFacing = Direction.NORTH; break;
            default: oldFacing = Direction.UP;
        }
        AttachFace face = oldFacing == Direction.DOWN ? AttachFace.CEILING
                : oldFacing == Direction.UP ? AttachFace.FLOOR : AttachFace.WALL;
        Direction horizontal = oldFacing.getAxis().isHorizontal() ? oldFacing : Direction.NORTH;
        return state.with(HorizontalFaceBlock.FACE, face)
                .with(HorizontalBlock.HORIZONTAL_FACING, horizontal)
                .with(powered, Boolean.valueOf((meta & 8) != 0));
    }

    private static BlockState legacyLeverState(BlockState state, int meta) {
        int orientation = meta & 7;
        AttachFace face;
        Direction horizontal;
        if (orientation == 0 || orientation == 7) {
            face = AttachFace.CEILING;
            horizontal = orientation == 0 ? Direction.EAST : Direction.NORTH;
        } else if (orientation == 5 || orientation == 6) {
            face = AttachFace.FLOOR;
            horizontal = orientation == 6 ? Direction.EAST : Direction.NORTH;
        } else {
            face = AttachFace.WALL;
            horizontal = new Direction[] { Direction.NORTH, Direction.EAST, Direction.WEST,
                    Direction.SOUTH, Direction.NORTH }[orientation];
        }
        return state.with(HorizontalFaceBlock.FACE, face)
                .with(HorizontalBlock.HORIZONTAL_FACING, horizontal)
                .with(LeverBlock.POWERED, Boolean.valueOf((meta & 8) != 0));
    }

    private static Map<String, String> vanillaBlockEntityIds() {
        Map<String, String> ids = new LinkedHashMap<String, String>();
        ids.put("Airportal", "minecraft:end_portal");
        ids.put("Banner", "minecraft:banner");
        ids.put("Beacon", "minecraft:beacon");
        ids.put("Cauldron", "minecraft:brewing_stand");
        ids.put("Chest", "minecraft:chest");
        ids.put("Comparator", "minecraft:comparator");
        ids.put("Control", "minecraft:command_block");
        ids.put("DLDetector", "minecraft:daylight_detector");
        ids.put("Dropper", "minecraft:dropper");
        ids.put("EnchantTable", "minecraft:enchanting_table");
        ids.put("EndGateway", "minecraft:end_gateway");
        ids.put("EnderChest", "minecraft:ender_chest");
        ids.put("FlowerPot", "minecraft:flower_pot");
        ids.put("Furnace", "minecraft:furnace");
        ids.put("Hopper", "minecraft:hopper");
        ids.put("MobSpawner", "minecraft:mob_spawner");
        ids.put("Music", "minecraft:noteblock");
        ids.put("Piston", "minecraft:piston");
        ids.put("RecordPlayer", "minecraft:jukebox");
        ids.put("Sign", "minecraft:sign");
        ids.put("Skull", "minecraft:skull");
        ids.put("Structure", "minecraft:structure_block");
        ids.put("Trap", "minecraft:dispenser");
        return Collections.unmodifiableMap(ids);
    }

    private static long chunkKey(int x, int z) {
        return ((long) x & 0xffffffffL) << 32 | ((long) z & 0xffffffffL);
    }

    @SuppressWarnings("unchecked")
    private static Dynamic<?>[] expandFlatteningTable(int requiredLength) {
        try {
            String fieldName = ObfuscationReflectionHelper.remapName(INameMappingService.Domain.FIELD, "field_199200_b");
            Field valuesField = BlockStateFlatteningMap.class.getDeclaredField(fieldName);
            valuesField.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(valuesField, valuesField.getModifiers() & ~Modifier.FINAL);
            Dynamic<?>[] current = (Dynamic<?>[]) valuesField.get(null);
            if (current.length >= requiredLength) return current;
            Dynamic<?>[] expanded = Arrays.copyOf(current, requiredLength);
            valuesField.set(null, expanded);
            return expanded;
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Could not expand Minecraft's legacy block-state flattening table", exception);
        }
    }

}

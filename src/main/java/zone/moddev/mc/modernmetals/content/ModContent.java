package zone.moddev.mc.modernmetals.content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import zone.moddev.mc.modernmetals.ModernMetals;
import zone.moddev.mc.modernmetals.ModTabs;
import zone.moddev.mc.modernmetals.entity.ModEntities;
import zone.moddev.mc.modernmetals.material.MaterialCatalogue;
import zone.moddev.mc.modernmetals.material.MaterialDefinition;

import net.minecraft.block.Block;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

/** Registers material blocks, items and fluids from the catalogue. */
@Mod.EventBusSubscriber(modid = ModernMetals.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModContent {
    private static final Map<String, RegistryHandle<Block>> BLOCKS_BY_ID =
            new LinkedHashMap<String, RegistryHandle<Block>>();
    private static final Map<String, RegistryHandle<Item>> ITEMS_BY_ID =
            new LinkedHashMap<String, RegistryHandle<Item>>();
    private static final Map<String, Supplier<? extends Block>> BLOCK_FACTORIES =
            new LinkedHashMap<String, Supplier<? extends Block>>();
    private static final Map<String, Supplier<? extends Item>> ITEM_FACTORIES =
            new LinkedHashMap<String, Supplier<? extends Item>>();
    private static final Map<String, MaterialContent> MATERIALS =
            new LinkedHashMap<String, MaterialContent>();
    private static final Map<String, FluidContent> FLUID_CONTENT =
            new LinkedHashMap<String, FluidContent>();
    private static final Map<String, Integer> FLUID_COLOURS =
            new LinkedHashMap<String, Integer>();
    private static final List<String> MATERIAL_BLOCK_FORMS = list(
            "block", "plate", "bars", "door", "trapdoor", "button", "slab", "lever",
            "pressure_plate", "stairs", "wall");
    private static final List<String> BASIC_ITEM_FORMS = list("ingot", "nugget", "powder", "smallpowder");
    private static final List<String> PROCESSING_FORMS = list(
            "casing", "dense_plate", "crushed", "crushed_purified", "crystal", "shard", "clump", "powder_dirty");

    static {
        for (MaterialDefinition material : MaterialCatalogue.ALL) {
            registerMaterial(material);
            registerFluid(material.name(), material.colour());
        }
        registerItem("coal_powder", () -> {
            Item.Properties properties = new Item.Properties();
            if (!ModList.get().isLoaded("basemetals")) properties.group(ModTabs.ITEMS);
            return new Item(properties);
        });
    }

    private ModContent() {}

    /** Create the paired fluids before blocks and buckets ask for them. */
    private static void initializeFluids() {
        for (Map.Entry<String, FluidContent> entry : FLUID_CONTENT.entrySet()) {
            FluidContent content = entry.getValue();
            if (isBound(content.source())) continue;
            MoltenFluid flowing = new MoltenFluid.Flowing(entry.getKey());
            MoltenFluid source = new MoltenFluid.Source(entry.getKey());
            content.flowing().bind(flowing);
            content.source().bind(source);
            flowing.setRegistryName(id("flowing_" + entry.getKey()));
            source.setRegistryName(id(entry.getKey()));
        }
    }

    @SubscribeEvent
    public static void registerFluids(RegistryEvent.Register<Fluid> event) {
        for (FluidContent content : FLUID_CONTENT.values()) {
            event.getRegistry().registerAll(content.source().get(), content.flowing().get());
        }
    }

    private static boolean isBound(RegistryHandle<?> handle) {
        try { handle.get(); return true; } catch (IllegalStateException ignored) { return false; }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        initializeFluids();
        for (Map.Entry<String, Supplier<? extends Block>> entry : BLOCK_FACTORIES.entrySet()) {
            Block value = entry.getValue().get();
            value.setRegistryName(ModernMetals.MOD_ID, entry.getKey());
            BLOCKS_BY_ID.get(entry.getKey()).bind(value);
            event.getRegistry().register(value);
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Map.Entry<String, Supplier<? extends Item>> entry : ITEM_FACTORIES.entrySet()) {
            Item value = entry.getValue().get();
            value.setRegistryName(ModernMetals.MOD_ID, entry.getKey());
            ITEMS_BY_ID.get(entry.getKey()).bind(value);
            event.getRegistry().register(value);
        }
    }

    public static boolean usesOreOverlay(Block block) {
        return block instanceof HarvestOre && ((HarvestOre) block).overlay;
    }

    public static Map<String, MaterialContent> materials() { return Collections.unmodifiableMap(MATERIALS); }
    public static Map<String, RegistryHandle<Block>> blocksById() { return Collections.unmodifiableMap(BLOCKS_BY_ID); }
    public static Map<String, RegistryHandle<Item>> itemsById() { return Collections.unmodifiableMap(ITEMS_BY_ID); }
    public static Map<String, FluidContent> fluids() { return Collections.unmodifiableMap(FLUID_CONTENT); }

    public static int fluidColour(Fluid fluid) {
        for (Map.Entry<String, FluidContent> entry : FLUID_CONTENT.entrySet()) {
            FluidContent content = entry.getValue();
            if (fluid == content.source().get() || fluid == content.flowing().get()) {
                Integer colour = FLUID_COLOURS.get(entry.getKey());
                return colour == null ? 0xFFFFFF : colour.intValue();
            }
        }
        return 0xFFFFFF;
    }

    public static boolean isModernMetalsFluid(Fluid fluid) {
        for (FluidContent content : FLUID_CONTENT.values()) {
            if (fluid == content.source().get() || fluid == content.flowing().get()) return true;
        }
        return false;
    }

    public static RegistryHandle<Item> item(String id) {
        RegistryHandle<Item> item = ITEMS_BY_ID.get(id);
        if (item == null) throw new IllegalArgumentException("Unknown item " + id);
        return item;
    }

    public static FluidContent fluid(String id) {
        FluidContent fluid = FLUID_CONTENT.get(id);
        if (fluid == null) throw new IllegalArgumentException("Unknown fluid " + id);
        return fluid;
    }

    private static void registerMaterial(MaterialDefinition material) {
        String name = material.name();
        Map<String, RegistryHandle<Block>> blocks = new LinkedHashMap<>();
        Map<String, RegistryHandle<Item>> items = new LinkedHashMap<>();
        for (String form : MATERIAL_BLOCK_FORMS) {
            String id = name + "_" + form;
            RegistryHandle<Block> block = registerMaterialBlock(id, form, material, true,
                    () -> metalProperties(material), null);
            blocks.put(form, block);
            items.put(form, ITEMS_BY_ID.get(id));
        }
        boolean historicalAlloyOre = "galvanizedsteel".equals(name) || "nichrome".equals(name)
                || "stainlesssteel".equals(name);
        if (material.hasOre() || historicalAlloyOre) {
            String id = name + "_ore";
            blocks.put("ore", registerBlock(id,
                    () -> new HarvestOre(oreProperties(material), material.requiredOreHarvestLevel(),
                            !"thorium".equals(name) && !historicalAlloyOre), !historicalAlloyOre));
            if (historicalAlloyOre) {
                registerItem(id, () -> new BlockItem(BLOCKS_BY_ID.get(id).get(), new Item.Properties()));
            }
            items.put("ore", ITEMS_BY_ID.get(id));
        }
        for (String form : BASIC_ITEM_FORMS) {
            items.put(form, registerSimpleMaterialItem(name + "_" + form, material));
        }
        if (material.isAlloy()) {
            items.put("blend", registerSimpleMaterialItem(name + "_blend", material));
            items.put("smallblend", registerSimpleMaterialItem(name + "_smallblend", material));
        }
        registerEquipment(material, items);
        for (String form : PROCESSING_FORMS) {
            if (LegacyRegistry.containsItem(name + "_" + form)) {
                items.put(form, registerSimpleMaterialItem(name + "_" + form, material));
            }
        }
        MATERIALS.put(name, new MaterialContent(material, blocks, items));
    }

    private static RegistryHandle<Block> registerMaterialBlock(String id, String form,
            final MaterialDefinition material, boolean hasItem,
            final Supplier<Block.Properties> properties, final Supplier<Block> stairBase) {
        Supplier<Block> factory;
        if ("plate".equals(form)) {
            factory = new Supplier<Block>() { @Override public Block get() { return new PlateBlock(properties.get(), material.requiredHarvestLevel()); } };
        } else if ("bars".equals(form)) {
            factory = new Supplier<Block>() { @Override public Block get() { return new HarvestPane(properties.get().variableOpacity().notSolid(), material.requiredHarvestLevel()); } };
        } else if ("door".equals(form)) {
            factory = new Supplier<Block>() { @Override public Block get() { return new HarvestDoor(properties.get().variableOpacity().notSolid(), material.requiredHarvestLevel()); } };
        } else if ("trapdoor".equals(form)) {
            factory = new Supplier<Block>() { @Override public Block get() { return new HarvestTrapDoor(properties.get().variableOpacity().notSolid(), material.requiredHarvestLevel()); } };
        } else if ("button".equals(form)) {
            factory = new Supplier<Block>() { @Override public Block get() { return new HarvestButton(properties.get().doesNotBlockMovement(), material.requiredHarvestLevel()); } };
        } else if ("slab".equals(form)) {
            factory = new Supplier<Block>() { @Override public Block get() { return new HarvestSlab(properties.get(), material.requiredHarvestLevel()); } };
        } else if ("lever".equals(form)) {
            factory = new Supplier<Block>() { @Override public Block get() { return new HarvestLever(properties.get().doesNotBlockMovement(), material.requiredHarvestLevel()); } };
        } else if ("pressure_plate".equals(form)) {
            factory = new Supplier<Block>() { @Override public Block get() { return new HarvestPressurePlate(properties.get(), material.requiredHarvestLevel()); } };
        } else if ("stairs".equals(form)) {
            factory = new Supplier<Block>() { @Override public Block get() {
                Block base = stairBase == null ? Blocks.IRON_BLOCK : stairBase.get();
                return new HarvestStairs(base.getDefaultState(), properties.get(), material.requiredHarvestLevel());
            } };
        } else if ("wall".equals(form)) {
            factory = new Supplier<Block>() { @Override public Block get() { return new HarvestWall(properties.get(), material.requiredHarvestLevel()); } };
        } else {
            factory = new Supplier<Block>() { @Override public Block get() { return new HarvestBlock(properties.get(), material.requiredHarvestLevel()); } };
        }
        return registerBlock(id, factory, hasItem);
    }

    private static void registerEquipment(final MaterialDefinition material,
            Map<String, RegistryHandle<Item>> items) {
        items.put("pickaxe", registerItem(material.name() + "_pickaxe", new Supplier<Item>() { @Override public Item get() { return new MaterialItems.Pickaxe(material, toolProperties()); } }));
        items.put("axe", registerItem(material.name() + "_axe", new Supplier<Item>() { @Override public Item get() { return new MaterialItems.Axe(material, toolProperties()); } }));
        items.put("shovel", registerItem(material.name() + "_shovel", new Supplier<Item>() { @Override public Item get() { return new MaterialItems.Shovel(material, toolProperties()); } }));
        items.put("hoe", registerItem(material.name() + "_hoe", new Supplier<Item>() { @Override public Item get() { return new MaterialItems.Hoe(material, toolProperties()); } }));
        items.put("sword", registerItem(material.name() + "_sword", new Supplier<Item>() { @Override public Item get() { return new MaterialItems.Sword(material, combatProperties()); } }));
        items.put("crackhammer", registerItem(material.name() + "_crackhammer", new Supplier<Item>() { @Override public Item get() { return new CrackhammerItem(material, toolProperties()); } }));
        items.put("scythe", registerItem(material.name() + "_scythe", new Supplier<Item>() { @Override public Item get() { return new ScytheItem(material, toolProperties()); } }));
        items.put("shears", registerItem(material.name() + "_shears", new Supplier<Item>() { @Override public Item get() { return new MaterialItems.Shears(material, toolProperties()); } }));
        items.put("fishing_rod", registerItem(material.name() + "_fishing_rod", new Supplier<Item>() { @Override public Item get() { return new MaterialItems.FishingRod(material, toolProperties()); } }));
        items.put("bow", registerItem(material.name() + "_bow", new Supplier<Item>() { @Override public Item get() { return new MaterialItems.Bow(material, combatProperties()); } }));
        items.put("crossbow", registerItem(material.name() + "_crossbow", new Supplier<Item>() { @Override public Item get() { return new MaterialItems.Crossbow(material, combatProperties()); } }));
        items.put("shield", registerItem(material.name() + "_shield", new Supplier<Item>() { @Override public Item get() { return new MaterialItems.Shield(material, combatProperties()); } }));
        items.put("arrow", registerAmmo(material, MetalAmmoItem.Kind.ARROW));
        items.put("bolt", registerAmmo(material, MetalAmmoItem.Kind.BOLT));
        items.put("rod", registerSimpleMaterialItem(material.name() + "_rod", material));
        items.put("gear", registerSimpleMaterialItem(material.name() + "_gear", material));
        items.put("horse_armor", registerItem(material.name() + "_horse_armor", new Supplier<Item>() { @Override public Item get() { return new MaterialItems.HorseArmor(material, combatProperties()); } }));
        items.put("helmet", registerArmor(material, EquipmentSlotType.HEAD));
        items.put("chestplate", registerArmor(material, EquipmentSlotType.CHEST));
        items.put("leggings", registerArmor(material, EquipmentSlotType.LEGS));
        items.put("boots", registerArmor(material, EquipmentSlotType.FEET));
    }

    private static RegistryHandle<Item> registerAmmo(final MaterialDefinition material, final MetalAmmoItem.Kind kind) {
        String suffix = kind == MetalAmmoItem.Kind.ARROW ? "_arrow" : "_bolt";
        return registerItem(material.name() + suffix, new Supplier<Item>() {
            @Override public Item get() {
                return new MetalAmmoItem(material, kind,
                        kind == MetalAmmoItem.Kind.ARROW ? ModEntities.CUSTOM_ARROW : ModEntities.CUSTOM_BOLT,
                        combatProperties());
            }
        });
    }

    private static RegistryHandle<Item> registerArmor(final MaterialDefinition material,
            final EquipmentSlotType slot) {
        String suffix;
        switch (slot) {
            case HEAD: suffix = "helmet"; break;
            case CHEST: suffix = "chestplate"; break;
            case LEGS: suffix = "leggings"; break;
            case FEET: suffix = "boots"; break;
            default: throw new IllegalArgumentException("Not armor: " + slot);
        }
        return registerItem(material.name() + "_" + suffix, new Supplier<Item>() {
            @Override public Item get() { return new MaterialItems.Armor(material, slot, combatProperties()); }
        });
    }

    private static void registerFluid(final String name, int colour) {
        final RegistryHandle<FlowingFluid> source = new RegistryHandle<FlowingFluid>(name);
        final RegistryHandle<FlowingFluid> flowing = new RegistryHandle<FlowingFluid>("flowing_" + name);
        final RegistryHandle<FlowingFluidBlock> block = new RegistryHandle<FlowingFluidBlock>(name);
        final RegistryHandle<Item> bucket = new RegistryHandle<Item>(name + "_bucket");
        FLUID_CONTENT.put(name, new FluidContent(source, flowing, block, bucket));
        FLUID_COLOURS.put(name, Integer.valueOf(colour));
        RegistryHandle<Block> blockHandle = registerBlock(name, new Supplier<Block>() { @Override public Block get() {
            MoltenMetalBlock value = new MoltenMetalBlock(source.get(),
                    Block.Properties.create(Material.LAVA).doesNotBlockMovement()
                            .hardnessAndResistance(100.0F).variableOpacity());
            block.bind(value);
            return value;
        } }, false);
        registerItem(name, new Supplier<Item>() { @Override public Item get() {
            return new BlockItem(blockHandle.get(), new Item.Properties());
        } });
        registerItem(name + "_bucket", new Supplier<Item>() { @Override public Item get() {
            MetalBucketItem value = new MetalBucketItem(source.get(),
                    new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ModTabs.ITEMS));
            bucket.bind(value);
            return value;
        } });
    }

    private static RegistryHandle<Block> registerBlock(String id, Supplier<? extends Block> factory, boolean hasItem) {
        if (BLOCKS_BY_ID.containsKey(id)) throw new IllegalStateException("Duplicate block " + id);
        final RegistryHandle<Block> handle = new RegistryHandle<Block>(id);
        BLOCKS_BY_ID.put(id, handle);
        BLOCK_FACTORIES.put(id, factory);
        if (hasItem) registerItem(id, new Supplier<Item>() { @Override public Item get() {
            return new BlockItem(handle.get(), new Item.Properties().group(ModTabs.BLOCKS));
        } });
        return handle;
    }

    private static RegistryHandle<Item> registerSimpleMaterialItem(final String id,
            final MaterialDefinition material) {
        return registerItem(id, new Supplier<Item>() { @Override public Item get() {
            return new MaterialItems.Basic(material, -1, new Item.Properties().group(ModTabs.ITEMS));
        } });
    }

    private static RegistryHandle<Item> registerItem(String id, Supplier<? extends Item> factory) {
        if (ITEMS_BY_ID.containsKey(id)) throw new IllegalStateException("Duplicate item " + id);
        RegistryHandle<Item> handle = new RegistryHandle<Item>(id);
        ITEMS_BY_ID.put(id, handle);
        ITEM_FACTORIES.put(id, factory);
        return handle;
    }

    private static Item.Properties toolProperties() { return new Item.Properties().group(ModTabs.TOOLS); }
    private static Item.Properties combatProperties() { return new Item.Properties().group(ModTabs.COMBAT); }
    private static Block.Properties metalProperties(MaterialDefinition material) {
        return Block.Properties.create(Material.IRON).sound(SoundType.METAL)
                .hardnessAndResistance(material.blockHardness(), material.blastResistance());
    }
    private static Block.Properties oreProperties(MaterialDefinition material) {
        return Block.Properties.create(Material.ROCK).sound(SoundType.STONE)
                .hardnessAndResistance(material.oreHardness(), material.blastResistance());
    }
    private static ResourceLocation id(String path) { return new ResourceLocation(ModernMetals.MOD_ID, path); }
    private static List<String> list(String... values) {
        return Collections.unmodifiableList(Arrays.asList(values));
    }

    private static class HarvestBlock extends Block {
        private final int level;
        HarvestBlock(Properties properties, int level) { super(properties); this.level = level; }
        @Override public ToolType getHarvestTool(net.minecraft.block.BlockState state) { return ToolType.PICKAXE; }
        @Override public int getHarvestLevel(net.minecraft.block.BlockState state) { return level; }
    }

    private static class HarvestOre extends HarvestBlock {
        private final boolean overlay;
        HarvestOre(Properties properties, int level, boolean overlay) {
            super(properties, level);
            this.overlay = overlay;
        }

    }
    private static class HarvestPane extends PaneBlock {
        private final int level;
        HarvestPane(Properties properties, int level) { super(properties); this.level = level; }
        @Override public ToolType getHarvestTool(net.minecraft.block.BlockState state) { return ToolType.PICKAXE; }
        @Override public int getHarvestLevel(net.minecraft.block.BlockState state) { return level; }
    }
    private static class HarvestDoor extends DoorBlock {
        private final int level;
        HarvestDoor(Properties properties, int level) { super(properties); this.level = level; }
        @Override public ToolType getHarvestTool(net.minecraft.block.BlockState state) { return ToolType.PICKAXE; }
        @Override public int getHarvestLevel(net.minecraft.block.BlockState state) { return level; }
    }
    private static class HarvestTrapDoor extends TrapDoorBlock {
        private final int level;
        HarvestTrapDoor(Properties properties, int level) { super(properties); this.level = level; }
        @Override public ToolType getHarvestTool(net.minecraft.block.BlockState state) { return ToolType.PICKAXE; }
        @Override public int getHarvestLevel(net.minecraft.block.BlockState state) { return level; }
    }
    private static class HarvestButton extends StoneButtonBlock {
        private final int level;
        HarvestButton(Properties properties, int level) { super(properties); this.level = level; }
        @Override public ToolType getHarvestTool(net.minecraft.block.BlockState state) { return ToolType.PICKAXE; }
        @Override public int getHarvestLevel(net.minecraft.block.BlockState state) { return level; }
    }
    private static class HarvestSlab extends SlabBlock {
        private final int level;
        HarvestSlab(Properties properties, int level) { super(properties); this.level = level; }
        @Override public ToolType getHarvestTool(net.minecraft.block.BlockState state) { return ToolType.PICKAXE; }
        @Override public int getHarvestLevel(net.minecraft.block.BlockState state) { return level; }
    }
    private static class HarvestLever extends LeverBlock {
        private final int level;
        HarvestLever(Properties properties, int level) { super(properties); this.level = level; }
        @Override public ToolType getHarvestTool(net.minecraft.block.BlockState state) { return ToolType.PICKAXE; }
        @Override public int getHarvestLevel(net.minecraft.block.BlockState state) { return level; }
    }
    private static class HarvestPressurePlate extends PressurePlateBlock {
        private final int level;
        HarvestPressurePlate(Properties properties, int level) { super(Sensitivity.MOBS, properties); this.level = level; }
        @Override public ToolType getHarvestTool(net.minecraft.block.BlockState state) { return ToolType.PICKAXE; }
        @Override public int getHarvestLevel(net.minecraft.block.BlockState state) { return level; }
    }
    private static class HarvestStairs extends StairsBlock {
        private final int level;
        HarvestStairs(net.minecraft.block.BlockState base, Properties properties, int level) { super(base, properties); this.level = level; }
        @Override public ToolType getHarvestTool(net.minecraft.block.BlockState state) { return ToolType.PICKAXE; }
        @Override public int getHarvestLevel(net.minecraft.block.BlockState state) { return level; }
    }
    private static class HarvestWall extends WallBlock {
        private final int level;
        HarvestWall(Properties properties, int level) { super(properties); this.level = level; }
        @Override public ToolType getHarvestTool(net.minecraft.block.BlockState state) { return ToolType.PICKAXE; }
        @Override public int getHarvestLevel(net.minecraft.block.BlockState state) { return level; }
    }
}

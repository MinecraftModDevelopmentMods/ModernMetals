package review.modern;

import java.util.List;
import java.util.concurrent.Future;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public final class OreOverlayChecks {
    private static final String STONE = "minecraft:blocks/stone";
    private static final int TEST_STONE = 0xFF39B48E;

    static void verify(final Minecraft minecraft) {
        final IBakedModel missing = minecraft.getRenderItem().getItemModelMesher().getModelManager().getMissingModel();
        int overlays = 0;
        int opaque = 0;
        for (final Block ore : ForgeRegistries.BLOCKS.getValuesCollection()) {
            final ResourceLocation id = ore.getRegistryName();
            if (id == null || !"modernmetals".equals(id.getNamespace()) || !id.getPath().endsWith("_ore")) continue;

            final IBlockState state = ore.getDefaultState();
            final Item item = Item.getItemFromBlock(ore);
            final boolean thorium = "thorium_ore".equals(id.getPath());
            require(id.equals(item.getRegistryName()), "Ore item registry ID differs for " + id);
            final BlockRenderLayer layer = thorium ? BlockRenderLayer.SOLID : BlockRenderLayer.CUTOUT_MIPPED;
            require(ore.canRenderInLayer(state, layer), id + " cannot render in " + layer
                    + " (block layer " + ore.getRenderLayer() + ")");

            final IBakedModel block = minecraft.getBlockRendererDispatcher().getBlockModelShapes().getModelForState(state);
            final IBakedModel inventory = minecraft.getRenderItem().getItemModelMesher().getItemModel(new ItemStack(item));
            require(block != missing && inventory != missing, "Missing baked model for " + id);
            final String base = thorium ? "modernmetals:blocks/thorium_ore" : STONE;
            final String overlay = thorium ? null : "modernmetals:blocks/ore_overlays/" + id.getPath();
            verifyFaces(block, state, base, overlay);
            verifyFaces(inventory, null, base, overlay);
            if (thorium) opaque++; else overlays++;
        }
        require(overlays == 16 && opaque == 1, "Unexpected ore set: " + overlays + " overlays and " + opaque + " opaque");
        final int stonePixel = minecraft.getTextureMapBlocks().getAtlasSprite(STONE).getFrameTextureData(0)[0][0];
        require((stonePixel == TEST_STONE) == Boolean.getBoolean("review.testStone"), "Unexpected stone texture");
        System.out.println("MODERN_METALS_ORE_OVERLAYS_PASS overlays=" + overlays + " opaque=" + opaque
                + " replacementStone=" + Boolean.getBoolean("review.testStone"));
    }

    private static void verifyFaces(final IBakedModel model, final IBlockState state, final String base, final String overlay) {
        require(base.equals(model.getParticleTexture().getIconName()), "Wrong particle texture for " + base);
        for (final EnumFacing face : EnumFacing.values()) {
            final List<BakedQuad> quads = model.getQuads(state, face, 0);
            require(quads.size() == (overlay == null ? 1 : 2), "Wrong face count for " + base + " " + face);
            require(hasSprite(quads, base), "Missing base sprite " + base);
            if (overlay != null) require(hasSprite(quads, overlay), "Missing overlay sprite " + overlay);
        }
    }

    private static boolean hasSprite(final List<BakedQuad> quads, final String name) {
        return quads.stream().anyMatch(quad -> name.equals(quad.getSprite().getIconName()));
    }

    static void installDisplay() {
        MinecraftForge.EVENT_BUS.register(new OreOverlayChecks());
    }

    private boolean launched;
    private Future<?> display;
    private int ticks;

    @SubscribeEvent
    public void capture(final TickEvent.ClientTickEvent event) throws Exception {
        if (event.phase != TickEvent.Phase.END) return;
        final Minecraft minecraft = Minecraft.getMinecraft();
        if (!launched && minecraft.currentScreen instanceof GuiMainMenu) {
            launched = true;
            minecraft.launchIntegratedServer("ore-display", "Modern Metals ore display",
                    new WorldSettings(8675309L, GameType.CREATIVE, false, false, WorldType.FLAT));
        } else if (minecraft.world != null && minecraft.player != null) {
            if (display == null) {
                final MinecraftServer server = minecraft.getIntegratedServer();
                display = server.addScheduledTask(() -> placeDisplay(server));
            } else if (display.isDone() && ++ticks >= 60) {
                display.get();
                minecraft.gameSettings.hideGUI = true;
                ScreenShotHelper.saveScreenshot(minecraft.gameDir, "ore-overlay-display.png",
                        minecraft.displayWidth, minecraft.displayHeight, minecraft.getFramebuffer());
                verify(minecraft);
                minecraft.shutdown();
            }
        }
    }

    private static void placeDisplay(final MinecraftServer server) {
        final WorldServer world = server.getWorld(0);
        world.setWorldTime(6000);
        for (int x = -2; x <= 6; x++) {
            for (int z = -6; z <= 3; z++) {
                world.setBlockState(new BlockPos(x, 199, z), Blocks.STONE.getDefaultState());
            }
        }
        final String[] ores = {"aluminum_ore", "boron_ore", "rutile_ore", "thorium_ore"};
        for (int x = 0; x < ores.length; x++) {
            final Block ore = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("modernmetals", ores[x]));
            world.setBlockState(new BlockPos(x, 200, 0), ore.getDefaultState());
        }
        world.setBlockState(new BlockPos(4, 200, 0), Blocks.STONE.getDefaultState());
        server.getPlayerList().getPlayers().get(0).connection.setPlayerLocation(2.5, 200, -4, 0, 13);
    }

    private static void require(final boolean condition, final String message) {
        if (!condition) throw new IllegalStateException(message);
    }
}

package review.modern;

import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.init.Materials;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

/** Checks the installed client's baked models, including the inventory forms. */
final class OreOverlayChecks {
    private static final String STONE = "minecraft:blocks/stone";
    private static final int TEST_STONE = 0xFF39B48E;

    static void verify(Minecraft minecraft) {
        final IBakedModel missing = minecraft.getRenderItem().getItemModelMesher().getModelManager().getMissingModel();
        int overlaid = 0;
        int fallback = 0;
        for (com.mcmoddev.lib.material.MMDMaterial material : Materials.getMaterialsByMod("modernmetals")) {
            if (!material.hasBlock(Names.ORE)) continue;
            final Block ore = material.getBlock(Names.ORE);
            final IBlockState state = ore.getDefaultState();
            final Item item = Item.getItemFromBlock(ore);
            final String name = material.getName() + "_ore";
            require(ore.getRegistryName().equals(new ResourceLocation("modernmetals", name)), "Ore registry ID changed: " + name);
            require(ore.getRegistryName().equals(item.getRegistryName()), "Ore item registry ID changed: " + name);
            require(OreDictionary.getOres("ore" + material.getCapitalizedName()).stream().anyMatch(s -> s.getItem() == item),
                    "Ore Dictionary entry missing: " + name);
            final boolean thorium = name.equals("thorium_ore");
            require(ore.getBlockLayer() == (thorium ? BlockRenderLayer.SOLID : BlockRenderLayer.CUTOUT_MIPPED),
                    "Wrong render layer: " + name);
            final IBakedModel blockModel = minecraft.getBlockRendererDispatcher().getBlockModelShapes().getModelForState(state);
            final IBakedModel itemModel = minecraft.getRenderItem().getItemModelMesher().getItemModel(new ItemStack(item));
            require(blockModel != missing && itemModel != missing, "Missing baked ore block or item: " + name);
            verifyModel(blockModel, state, name, thorium);
            verifyModel(itemModel, null, name, thorium);
            if (thorium) fallback++; else overlaid++;
        }
        require(overlaid == 19 && fallback == 1, "Unexpected registered ore set: " + overlaid + " overlays, " + fallback + " fallback");
        final TextureAtlasSprite stone = minecraft.getTextureMapBlocks().getAtlasSprite(STONE);
        require((stone.getFrameTextureData(0)[0][0] == TEST_STONE) == Boolean.getBoolean("modern.oreHostPack"),
                "The expected stone resource pack was not applied");
        System.out.println("MODERN_METALS_ORE_OVERLAYS_PASS ores=" + overlaid + " fallback=" + fallback
                + " hostPack=" + Boolean.getBoolean("modern.oreHostPack"));
    }

    private static void verifyModel(IBakedModel model, IBlockState state, String name, boolean thorium) {
        final String base = thorium ? "modernmetals:blocks/thorium_ore" : STONE;
        final String overlay = "modernmetals:blocks/ore_overlays/" + name;
        require(base.equals(model.getParticleTexture().getIconName()), "Wrong particles: " + name);
        for (EnumFacing face : EnumFacing.values()) {
            final List<BakedQuad> quads = model.getQuads(state, face, 0);
            require(quads.size() == (thorium ? 1 : 2), "Wrong face count: " + name + " " + face);
            verifyFace(quads, face, base, 0.0F);
            if (!thorium) verifyFace(quads, face, overlay, 0.05F / 16.0F);
        }
    }

    private static void verifyFace(List<BakedQuad> quads, EnumFacing face, String texture, float offset) {
        final BakedQuad quad = quads.stream().filter(q -> texture.equals(q.getSprite().getIconName()))
                .findFirst().orElseThrow(() -> new AssertionError("Missing baked sprite: " + texture));
        final int axis = face.getFrontOffsetX() != 0 ? 0 : face.getFrontOffsetY() != 0 ? 1 : 2;
        final float expected = face.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE ? 1.0F + offset : -offset;
        final int[] data = quad.getVertexData();
        for (int vertex = 0; vertex < 4; vertex++) {
            require(Math.abs(Float.intBitsToFloat(data[vertex * (data.length / 4) + axis]) - expected) < 0.0001F,
                    "Wrong face depth for " + texture + " " + face);
        }
    }

    static void placeDisplay(MinecraftServer server) {
        final WorldServer world = server.getWorld(0);
        for (int x = 0; x < 5; x++) {
            for (int z = -5; z <= 0; z++) {
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

    private static void require(boolean condition, String detail) {
        if (!condition) throw new AssertionError(detail);
    }
}

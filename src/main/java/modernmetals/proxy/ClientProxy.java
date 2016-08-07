package modernmetals.proxy;

import cyano.basemetals.entity.EntityCustomArrow;
import modernmetals.ModernMetals;
import cyano.basemetals.client.renderer.RenderCustomArrow;
import modernmetals.init.Blocks;
import modernmetals.init.Fluids;
import modernmetals.init.Items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/**
 *
 * @author Jasmine Iwanek
 *
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		Fluids.bakeModels(ModernMetals.MODID);
		RenderingRegistry.registerEntityRenderingHandler(EntityCustomArrow.class, RenderCustomArrow::new);
	}

	@Override
	public void init() {
		final ItemModelMesher itemModelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		for (final Item item : Items.getItemRegistry().keySet())
			itemModelMesher.register(item, 0, new ModelResourceLocation(new ResourceLocation(ModernMetals.MODID, Items.getItemRegistry().get(item)), "inventory"));

		for (final String blockName : Blocks.getBlockRegistry().keySet()) {
			final Block block = Blocks.getBlockByName(blockName);
			if (block instanceof BlockDoor)
				continue; // do not add door blocks
			itemModelMesher.register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(ModernMetals.MODID, blockName), "inventory"));
		}
	}
}

package cyano.basemetals.blocks;

import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
//import cyano.basemetals.registry.IOreDictionaryEntry;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.item.ItemStack;

public class BlockMetalSlab extends BlockSlab implements IMetalObject {

    public static final PropertyEnum<BlockMetalSlab.EnumBlockHalf> HALF = PropertyEnum.<BlockMetalSlab.EnumBlockHalf>create("half", BlockMetalSlab.EnumBlockHalf.class);

	final MetalMaterial metal;

	public BlockMetalSlab(MetalMaterial metal) {
		super(Material.iron);
		this.metal = metal;
		this.blockHardness = metal.getMetalBlockHardness();
		this.blockResistance = metal.getBlastResistance();
		this.setHarvestLevel("pickaxe", metal.getRequiredHarvestLevel());
	}


	@Override
	public String getUnlocalizedName(int meta) {
		return super.getUnlocalizedName();
	}

	@Override
	public boolean isDouble() {
		return false;
	}

	@Override
	public MetalMaterial getMetalMaterial() {
		return metal;
	}


	@Override
	public IProperty<?> getVariantProperty() {
		return null;
	}


	@Override
	public Object getVariant(ItemStack stack) {
		return null;
	}

//	@Override
//	public String getOreDictionaryName() {
//		return "slab" + metal.getCapitalizedName();
//	}
}
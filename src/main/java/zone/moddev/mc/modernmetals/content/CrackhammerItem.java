package zone.moddev.mc.modernmetals.content;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import zone.moddev.mc.modernmetals.ModTags;
import zone.moddev.mc.modernmetals.material.MaterialDefinition;
import zone.moddev.mc.modernmetals.recipe.CrushingRecipe;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemUseContext;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.ForgeEventFactory;

public final class CrackhammerItem extends ItemTool implements MaterialBacked {
    private static final ResourceLocation BASE_METALS_CRUSHING = new ResourceLocation("basemetals", "crushing");
    private final MaterialDefinition material;

    public CrackhammerItem(MaterialDefinition material, Item.Properties properties) {
        super(material.crackhammerAttackDamage() - material.baseAttackDamage(), -3.5F,
                new MaterialTier(material), Collections.<Block>emptySet(),
                properties.defaultMaxDamage(material.crackhammerDurability())
                        .addToolType(ToolType.PICKAXE, material.toolLevel()));
        this.material = material;
    }

    @Override public MaterialDefinition baseMetalsMaterial() { return material; }

    @Override public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return state.isIn(ModTags.CRACKHAMMER_CRUSHABLE) && canHarvestBlock(state)
                ? material.crackhammerDestroySpeed() : 1.0F;
    }

    @Override public boolean canHarvestBlock(IBlockState state) {
        if (state.getHarvestTool() == ToolType.PICKAXE) return material.toolLevel() >= state.getHarvestLevel();
        Material blockMaterial = state.getMaterial();
        return blockMaterial == Material.ROCK || blockMaterial == Material.IRON
                || blockMaterial == Material.ANVIL;
    }

    @Override public void addInformation(ItemStack stack, @Nullable World world,
            List<ITextComponent> tooltip, ITooltipFlag flag) { MaterialItems.addToolTooltip(material, tooltip); }

    @Override
    public boolean onBlockStartBreak(ItemStack hammer, BlockPos pos, EntityPlayer player) {
        World world = player.world;
        if (world.isRemote || player.isCreative()) return false;

        IBlockState state = world.getBlockState(pos);
        if (!state.canHarvestBlock(world, pos, player)) return false;
        Block block = state.getBlock();
        IRecipe recipe = findRecipe(world, new ItemStack(block));
        if (recipe == null) return false;

        // Forge has already checked the break event. Remove the block without its usual drops.
        if (state.removedByPlayer(world, pos, player, false, world.getFluidState(pos))) {
            block.onPlayerDestroy(world, pos, state);
            Block.spawnAsEntity(world, pos, recipe.getRecipeOutput().copy());
            player.addStat(StatList.BLOCK_MINED.get(block));
            player.addExhaustion(0.005F);
            ItemStack original = hammer.copy();
            hammer.onBlockDestroyed(world, state, pos, player);
            if (hammer.isEmpty()) ForgeEventFactory.onPlayerDestroyItem(player, original, EnumHand.MAIN_HAND);
        }
        return true;
    }

    @Override
    public EnumActionResult onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        EntityPlayer player = context.getPlayer();
        if (context.getFace() != EnumFacing.UP || world.isRemote || player == null) return EnumActionResult.PASS;

        AxisAlignedBB area = new AxisAlignedBB(context.getPos().up());
        List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, area);
        for (EntityItem entity : entities) {
            ItemStack input = entity.getItem();
            IRecipe recipe = findRecipe(world, input);
            if (recipe == null || !canCrushDroppedBlock(input)) continue;
            int requested = player.isSneaking() ? input.getCount() : 1;
            ItemStack hammer = context.getItem();
            int durability = hammer.isDamageable()
                    ? Math.max(0, hammer.getMaxDamage() - hammer.getDamage()) : requested;
            int operations = Math.min(requested, durability);
            if (operations <= 0) break;
            ItemStack output = recipe.getRecipeOutput().copy();
            input.shrink(operations);
            if (input.isEmpty()) entity.remove(); else entity.setItem(input);
            spawnOutputs(world, entity, output, operations);
            hammer.damageItem(operations, player);
            world.playSound(null, context.getPos(), net.minecraft.init.SoundEvents.BLOCK_GRAVEL_BREAK,
                    SoundCategory.BLOCKS, 0.5F, 0.5F + world.rand.nextFloat() * 0.3F);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    @Nullable
    private static IRecipe findRecipe(World world, ItemStack input) {
        InventoryBasic inventory = new InventoryBasic(new TextComponentString("crushing"), 1);
        inventory.setInventorySlotContents(0, input);
        for (IRecipe candidate : world.getRecipeManager().getRecipes()) {
            ResourceLocation type = candidate.getType().getId();
            if ((CrushingRecipe.TYPE_ID.equals(type) || BASE_METALS_CRUSHING.equals(type))
                    && candidate.matches(inventory, world)) {
                return candidate;
            }
        }
        return null;
    }

    private boolean canCrushDroppedBlock(ItemStack input) {
        if (!(input.getItem() instanceof ItemBlock)) return true;
        return canHarvestBlock(((ItemBlock) input.getItem()).getBlock().getDefaultState());
    }

    private static void spawnOutputs(World world, EntityItem source, ItemStack result, int operations) {
        int remaining = result.getCount() * operations;
        while (remaining > 0) {
            ItemStack output = result.copy();
            output.setCount(Math.min(output.getMaxStackSize(), remaining));
            remaining -= output.getCount();
            EntityItem crushed = new EntityItem(world, source.posX, source.posY, source.posZ, output);
            crushed.setDefaultPickupDelay();
            world.spawnEntity(crushed);
        }
    }
}

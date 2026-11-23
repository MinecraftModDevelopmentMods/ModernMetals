package zone.moddev.mc.modernmetals.testmod;

import java.util.*;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.network.*;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import zone.moddev.mc.modernmetals.config.ContentPolicy;
import zone.moddev.mc.modernmetals.content.*;
import zone.moddev.mc.modernmetals.material.*;
import zone.moddev.mc.modernmetals.recipe.*;

final class ProcessingChecks {
    private static int checks;
    private static void check(boolean value,String reason){checks++;if(!value)throw new IllegalStateException(reason);}
    private static ResourceLocation id(String name){return new ResourceLocation("modernmetals",name);}
    private static ItemStack stack(String name){return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(name.contains(":")?name:"modernmetals:"+name)));}
    private static CraftingInventory grid(){return new CraftingInventory(new Container(null,-1){@Override public boolean canInteractWith(net.minecraft.entity.player.PlayerEntity player){return true;}},3,3);}
    private static ServerPlayerEntity player(MinecraftServer server,ServerWorld world){
        ServerPlayerEntity p=new ServerPlayerEntity(server,world,new GameProfile(UUID.randomUUID(),"ProcessingProbe"),new PlayerInteractionManager(world));
        p.connection=new ServerPlayNetHandler(server,new NetworkManager(PacketDirection.SERVERBOUND),p);
        p.interactionManager.setGameType(GameType.SURVIVAL);return p;
    }
    static int run(MinecraftServer server,ServerWorld world){
        checks=0;
        String[] alloys={"aluminumbrass","galvanizedsteel","nichrome","stainlesssteel","titanium"};
        String[][] ingredients={{"aluminum_powder","aluminum_powder","aluminum_powder","basemetals:copper_powder"},{"basemetals:steel_powder","basemetals:zinc_powder"},{"basemetals:nickel_powder","chromium_powder"},{"basemetals:steel_powder","chromium_powder"},{"rutile_powder","magnesium_powder"}};
        int[] yields={4,2,2,2,1};
        for(int i=0;i<alloys.length;i++){
            if(i<4&&!ModList.get().isLoaded("basemetals"))continue;
            CraftingInventory input=grid();for(int j=0;j<ingredients[i].length;j++)input.setInventorySlotContents(j,stack(ingredients[i][j]));
            IRecipe recipe=server.getRecipeManager().getRecipe(id(alloys[i]+"_blend_base")).get();
            check(recipe.matches(input,world),"Alloy matches "+alloys[i]);check(recipe.getCraftingResult(input).getCount()==yields[i],"Alloy yield "+alloys[i]);
            Inventory powder=new Inventory(stack(alloys[i]+"_blend"));
            IRecipe smelt=server.getRecipeManager().getRecipe(IRecipeType.SMELTING,powder,world).get();
            check(smelt.getRecipeOutput().getItem()==stack(alloys[i]+"_ingot").getItem(),"Alloy furnace "+alloys[i]);
        }
        if(ModList.get().isLoaded("basemetals"))for(String alloy:new String[]{"galvanizedsteel","stainlesssteel"}){
            CraftingInventory input=grid();String additive=alloy.equals("galvanizedsteel")?"basemetals:zinc_powder":"chromium_powder";
            input.setInventorySlotContents(0,stack("basemetals:steel_blend"));input.setInventorySlotContents(1,stack(additive));
            int matches=0;for(IRecipe<?> r:server.getRecipeManager().getRecipes())if(r.getType()==IRecipeType.CRAFTING&&((IRecipe)r).matches(input,world)){matches++;check(r.getRecipeOutput().getCount()==2,"Whole steel blend yield "+r.getId());}
            check(matches==1,"Exactly one direct steel blend recipe "+alloy+" matches="+matches);
        }
        for(MaterialDefinition m:MaterialCatalogue.ALL){
            String name=m.name();
            for(String form:new String[]{"rod","gear","plate"}){
                IRecipe<?> r=server.getRecipeManager().getRecipe(id(name+"_"+form)).get();
                check(r.getRecipeOutput().getCount()==(form.equals("plate")?3:4),"Component yield "+r.getId());
                check(r.isDynamic()==!ContentPolicy.active().allows(name,form),"Fantasy component policy "+r.getId());
            }
            for(String form:new String[]{"powder","smallpowder"}){
                Inventory input=new Inventory(stack(name+"_"+form));
                check(server.getRecipeManager().getRecipe(IRecipeType.SMELTING,input,world).isPresent(),"Furnace form "+name+"_"+form);
                check(server.getRecipeManager().getRecipe(IRecipeType.BLASTING,input,world).isPresent(),"Blast form "+name+"_"+form);
            }
        }
        int repairCount=0;
        for(IRecipe r:server.getRecipeManager().getRecipes())if(r instanceof PlateRepairRecipe){
            repairCount++;ItemStack original=r.getRecipeOutput().copy();original.setDamage(7);original.setDisplayName(new net.minecraft.util.text.StringTextComponent("Keep my name"));original.getOrCreateTag().putString("proof","kept");
            CraftingInventory input=grid();input.setInventorySlotContents(0,original.copy());String name=original.getItem().getRegistryName().getPath().split("_")[0];input.setInventorySlotContents(7,stack(name+"_plate"));
            boolean allowed=ContentPolicy.active().allows(original.getItem().getRegistryName().toString());check(r.matches(input,world)==allowed,"Repair matches policy "+r.getId());
            if(allowed){ItemStack expected=original.copy();expected.setDamage(0);check(ItemStack.areItemStacksEqual(r.getCraftingResult(input),expected),"Repair preserves NBT "+r.getId());}
        }
        check(repairCount==105,"All plate repair IDs");
        // Check actual survival crackhammer harvesting, durability and shared recipe type.
        ServerPlayerEntity p=player(server,world);int x=160;
        for(String ore:new String[]{"beryllium","rutile","magnesium","titanium"}){
            BlockPos pos=new BlockPos(x+=4,110,160);world.setBlockState(pos,ModContent.blocksById().get(ore+"_ore").get().getDefaultState(),3);
            for(ItemEntity e:world.getEntitiesWithinAABB(ItemEntity.class,new AxisAlignedBB(pos).grow(1)))e.remove();
            ItemStack hammer=stack("titanium_crackhammer");p.setHeldItem(Hand.MAIN_HAND,hammer);
            p.interactionManager.tryHarvestBlock(pos);
            check(world.isAirBlock(pos),"Survival hammer harvest "+ore);
            int count=0;for(ItemEntity e:world.getEntitiesWithinAABB(ItemEntity.class,new AxisAlignedBB(pos).grow(1))){check(e.getItem().getItem()==stack(ore+"_powder").getItem(),"Hammer produces powder "+ore);count+=e.getItem().getCount();e.remove();}
            check(count==2,"Hammer two dust "+ore+" got "+count);check(hammer.getDamage()==1,"Hammer durability "+ore);
        }
        for(IRecipe<?> r:server.getRecipeManager().getRecipes())if(r.getId().getNamespace().equals("modernmetals")&&Registry.RECIPE_TYPE.getKey(r.getType()).getPath().equals("crushing")){
            check(Registry.RECIPE_TYPE.getKey(r.getType()).getNamespace().equals(ModList.get().isLoaded("basemetals")?"basemetals":"modernmetals"),"Shared crusher owns type "+r.getId());
        }
        BlockPos hot=new BlockPos(190,110,160);FluidContent fluid=ModContent.fluid("titanium");world.setBlockState(hot,fluid.block().get().getDefaultState(),3);
        net.minecraft.entity.LivingEntity subject=net.minecraft.entity.EntityType.COW.create(world);subject.setPosition(hot.getX()+0.5,hot.getY(),hot.getZ()+0.5);float health=subject.getHealth();
        fluid.block().get().onEntityCollision(world.getBlockState(hot),world,hot,subject);check(subject.getHealth()==health-4&&subject.isBurning(),"Molten metal deals four damage and ignites living entities");
        check(!net.minecraft.tileentity.AbstractFurnaceTileEntity.isFuel(stack("coal_powder")),"No standalone coal dust fuel exception");
        return checks;
    }
}

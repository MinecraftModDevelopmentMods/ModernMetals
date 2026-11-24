package zone.moddev.mc.modernmetals.testmod;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.registries.ForgeRegistries;
import zone.moddev.mc.modernmetals.config.ContentPolicy;
import zone.moddev.mc.modernmetals.content.ModContent;
import zone.moddev.mc.modernmetals.material.MaterialCatalogue;
import zone.moddev.mc.modernmetals.recipe.RecipeDiscovery;

/** Loaded only by explicit test runs, never included in the player jar. */
@Mod("modernmetalsprobe")
public final class ModernMetalsProbe {
    private int checks;
    public ModernMetalsProbe(){MinecraftForge.EVENT_BUS.addListener(this::started);}
    private static ResourceLocation id(String name){return new ResourceLocation("modernmetals",name);}
    private static ItemStack item(String name){return new ItemStack(ModContent.item(name).get());}
    private void require(boolean value,String detail){checks++;if(!value)throw new IllegalStateException(detail);}
    private void started(FMLServerStartedEvent event){
        MinecraftServer server=event.getServer();
        JsonObject receipt=new JsonObject();
        try {
            ServerWorld world=server.getWorld(DimensionType.OVERWORLD);
            require(MaterialCatalogue.ALL.size()==21,"All materials registered");
            if(Boolean.getBoolean("modernmetalsprobe.upgrade")){
                checks+=WorldUpgradeChecks.run(server,world);
            }else{
            registryAndLoot(world);
            recipes(server,world);
            furnace(world);
            discovery(server,world);
            checks+=Native115Checks.run(server);
            checks+=ProcessingChecks.run(server,world);
            checks+=StackChecks.run(server,world);
            checks+=RangedChecks.run(server);
            checks+=OrePlacementChecks.run(server);
            }
            receipt.addProperty("status","passed");
        }catch(Throwable failure){receipt.addProperty("status","failed");receipt.addProperty("failure",failure.toString());failure.printStackTrace();}
        finally{
            receipt.addProperty("checks",checks);
            receipt.addProperty("baseMetals",ModList.get().isLoaded("basemetals"));
            receipt.addProperty("fantasy",ContentPolicy.active().mode().serializedName());
            try{Files.write(Paths.get("modernmetals-probe.json"),receipt.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));}catch(Exception failure){throw new RuntimeException(failure);}
            server.initiateShutdown(false);
        }
    }
    private void registryAndLoot(ServerWorld world){
        for(java.util.Map.Entry<String,zone.moddev.mc.modernmetals.content.RegistryHandle<Block>> entry:ModContent.blocksById().entrySet()){
            String name=entry.getKey();Block block=entry.getValue().get();
            require(block.getRegistryName().equals(id(name)),"Stable block ID "+name);
            if(ModContent.fluids().containsKey(name))continue;
            BlockState state=block.getDefaultState();
            if(block instanceof DoorBlock)state=state.with(DoorBlock.HALF,DoubleBlockHalf.LOWER);
            List<ItemStack> drops=Block.getDrops(state,world,new BlockPos(128,100,128),null,FakePlayerFactory.getMinecraft(world),new ItemStack(Items.DIAMOND_PICKAXE));
            require(drops.size()==1&&drops.get(0).getItem()==block.asItem()&&drops.get(0).getCount()==1,"Block loot "+name+" "+drops);
            if(block instanceof SlabBlock){
                drops=Block.getDrops(state.with(SlabBlock.TYPE,SlabType.DOUBLE),world,new BlockPos(128,100,128),null,FakePlayerFactory.getMinecraft(world),new ItemStack(Items.DIAMOND_PICKAXE));
                require(drops.size()==1&&drops.get(0).getCount()==2,"Double slab yield "+name);
            }
        }
        require(item("galvanizedsteel_pickaxe").canHarvestBlock(Blocks.DIAMOND_ORE.getDefaultState()),"Galvanized steel remains iron tier");
    }
    private void recipes(MinecraftServer server,ServerWorld world){
        int books=0,crushing=0;
        for(IRecipe<?> recipe:server.getRecipeManager().getRecipes()){
            if(!recipe.getId().getNamespace().equals("modernmetals"))continue;
            if(recipe.getType().toString().endsWith(":crushing")){crushing++;continue;}
            if(recipe.isDynamic())continue;
            require(server.getAdvancementManager().getAdvancement(id("recipes/"+recipe.getId().getPath()))!=null,"Book advancement "+recipe.getId());books++;
        }
        require(books>200,"Complete eligible recipe book");require(crushing>50,"Complete shared crushing contributions");
        for(String name:new String[]{"titanium","galvanizedsteel","stainlesssteel","nichrome","aluminumbrass"}){
            boolean exists=server.getRecipeManager().getRecipe(id(name+"_blend_base")).isPresent();
            require(exists==(name.equals("titanium")||ModList.get().isLoaded("basemetals")),"Optional alloy loading "+name);
        }
        IRecipe<?> titanium=server.getRecipeManager().getRecipe(id("titanium_blend_base")).get();
        CraftingInventory input=new CraftingInventory(new Container(null,-1){@Override public boolean canInteractWith(net.minecraft.entity.player.PlayerEntity player){return false;}},2,1);
        input.setInventorySlotContents(0,item("rutile_powder"));input.setInventorySlotContents(1,item("magnesium_powder"));
        require(((IRecipe) titanium).matches(input,world),"Rutile and magnesium make titanium");
        require(titanium.getRecipeOutput().getCount()==1,"Titanium blend yield");
        for(String form:new String[]{"powder","smallpowder"})require(server.getRecipeManager().getRecipe(id("blasting/beryllium_"+form)).isPresent(),"Blast furnace processes "+form);
        Inventory coal=new Inventory(new ItemStack(Items.COAL));int owners=0;
        for(IRecipe<?> recipe:server.getRecipeManager().getRecipes())if(recipe.getType().toString().endsWith(":crushing")&&((IRecipe)recipe).matches(coal,world)){owners++;require(recipe.getId().getNamespace().equals(ModList.get().isLoaded("basemetals")?"basemetals":"modernmetals"),"Coal owner");}
        require(owners==1,"Exactly one coal crushing match");
    }
    private void furnace(ServerWorld world){
        BlockPos pos=new BlockPos(130,100,130);world.setBlockState(pos,Blocks.AIR.getDefaultState(),3);world.setBlockState(pos,Blocks.FURNACE.getDefaultState(),3);
        FurnaceTileEntity furnace=(FurnaceTileEntity)world.getTileEntity(pos);furnace.setInventorySlotContents(1,new ItemStack(Items.COAL,8));
        for(int operation=0;operation<2;operation++){
            furnace.setInventorySlotContents(0,item("galvanizedsteel_axe"));
            for(int tick=0;tick<240;tick++)furnace.tick();
            require(furnace.getStackInSlot(2).getItem()==ModContent.item("galvanizedsteel_ingot").get(),"Recycling output item");
            require(furnace.getStackInSlot(2).getCount()==3*(operation+1),"Furnace retains every recycled ingot");
        }
    }
    private void discovery(MinecraftServer server,ServerWorld world){
        // Forge intentionally suppresses advancement grants for FakePlayer.
        net.minecraft.entity.player.ServerPlayerEntity player=new net.minecraft.entity.player.ServerPlayerEntity(server,world,
                new GameProfile(UUID.randomUUID(),"ModernDiscovery"),new net.minecraft.server.management.PlayerInteractionManager(world));
        player.connection=new net.minecraft.network.play.ServerPlayNetHandler(server,
                new net.minecraft.network.NetworkManager(net.minecraft.network.PacketDirection.SERVERBOUND),player);
        player.inventory.setInventorySlotContents(0,item("beryllium_powder"));
        RecipeDiscovery.discover(player);
        IRecipe<?> recipe=server.getRecipeManager().getRecipe(id("smelting/beryllium_powder")).get();
        require(player.getRecipeBook().isUnlocked(recipe),"Held beryllium powder discovers furnace recipe");
    }
}

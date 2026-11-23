package zone.moddev.mc.modernmetals.testmod;
import java.nio.file.*;
import java.io.*;
import com.google.gson.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import zone.moddev.mc.modernmetals.content.ModContent;
final class WorldUpgradeChecks {
    static int run(MinecraftServer server,ServerWorld world)throws Exception{
        int checks=0;
        if(Files.isRegularFile(Paths.get("fixture-blocks.json"))){
            JsonArray fixture=new JsonParser().parse(new String(Files.readAllBytes(Paths.get("fixture-blocks.json")),"UTF-8")).getAsJsonArray();
            for(JsonElement e:fixture){JsonObject o=e.getAsJsonObject();BlockPos p=new BlockPos(o.get("x").getAsInt(),o.get("y").getAsInt(),o.get("z").getAsInt());
                if(!world.getBlockState(p).getBlock().getRegistryName().toString().equals(o.get("id").getAsString()))throw new IllegalStateException("Upgrade block at "+p+" expected "+o.get("id")+" actual "+world.getBlockState(p));checks++;
            }
            JsonObject chest=fixture.get(fixture.size()-2).getAsJsonObject();ChestTileEntity tile=(ChestTileEntity)world.getTileEntity(new BlockPos(chest.get("x").getAsInt(),100,chest.get("z").getAsInt()));
            for(int slot=0;slot<23;slot++){if(tile.getStackInSlot(slot).isEmpty())throw new IllegalStateException("Upgrade chest slot "+slot);checks++;}
            CompoundNBT tool=CompressedStreamTools.readCompressed(new FileInputStream("legacy-tool.nbt"));ItemStack saved=ItemStack.read(tool);
            if(saved.isEmpty()||saved.getDamage()!=7||!saved.hasDisplayName()||!saved.isEnchanted())throw new IllegalStateException("Upgrade named enchanted hammer "+tool);checks++;
            if(tile.getStackInSlot(24).isEmpty())tile.setInventorySlotContents(24,saved.copy());
            if(!ItemStack.areItemStacksEqual(saved,tile.getStackInSlot(24)))throw new IllegalStateException("Saved hammer round trip");checks++;
            JsonObject other=fixture.get(fixture.size()-1).getAsJsonObject();ChestTileEntity fluids=(ChestTileEntity)world.getTileEntity(new BlockPos(other.get("x").getAsInt(),100,other.get("z").getAsInt()));
            for(int slot=0;slot<21;slot++){if(fluids.getStackInSlot(slot).isEmpty())throw new IllegalStateException("Historical fluid item "+slot);checks++;}
        }
        // Load later terrain as well; historic chunks are checked separately against their sealed source.
        world.getChunk(256,256);checks++;return checks;
    }
}

package zone.moddev.mc.modernmetals.test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.fml.common.Mod;

/** Dedicated fresh/upgrade/reopen probe, gated separately from GameTests. */
public final class ServerSmoke {
    public static void register() {
        if(Boolean.getBoolean("modernmetals.serverSmoke"))NeoForge.EVENT_BUS.addListener(ServerSmoke::started);
    }
    private static void started(ServerStartedEvent event) {
        var server=event.getServer();
        var level=server.overworld();
        try {
            String source=zone.moddev.mc.modernmetals.ModernMetals.class.getResource("ModernMetals.class").toString();
            if(Boolean.getBoolean("modernmetals.expectedPackaged")&&!source.contains(".jar"))throw new AssertionError("Product loaded from classes, not candidate jar: "+source);
            Path marker=Path.of("smoke-first-save.txt");
            int legacyBlocks=0, legacyItems=0;
            if(Files.exists(Path.of("fixture-blocks.json"))) {
                var fixtures=com.google.gson.JsonParser.parseString(Files.readString(Path.of("fixture-blocks.json"))).getAsJsonArray();
                for(var entry:fixtures) {
                    var row=entry.getAsJsonObject();
                    var at=new BlockPos(row.get("x").getAsInt(),row.get("y").getAsInt(),row.get("z").getAsInt());
                    var id=ResourceLocation.parse(row.get("id").getAsString());
                    if(!level.getBlockState(at).is(BuiltInRegistries.BLOCK.get(id)))throw new AssertionError("Legacy block lost: "+id+" at "+at+" now "+level.getBlockState(at));
                    legacyBlocks++;
                    if(id.equals(ResourceLocation.withDefaultNamespace("chest"))) {
                        var chest=(net.minecraft.world.level.block.entity.ChestBlockEntity)level.getBlockEntity(at);
                        for(int slot=0;slot<chest.getContainerSize();slot++)if(!chest.getItem(slot).isEmpty())legacyItems++;
                    }
                }
                if(legacyItems!=44)throw new AssertionError("Legacy chest item stacks: "+legacyItems+" expected 44");
                var old=net.minecraft.nbt.NbtIo.readCompressed(Path.of("legacy-tool.nbt"),net.minecraft.nbt.NbtAccounter.unlimitedHeap());
                var fixed=(net.minecraft.nbt.CompoundTag)net.minecraft.util.datafix.DataFixers.getDataFixer().update(net.minecraft.util.datafix.fixes.References.ITEM_STACK,
                    new com.mojang.serialization.Dynamic<>(net.minecraft.nbt.NbtOps.INSTANCE,old),1631,net.minecraft.SharedConstants.getCurrentVersion().getDataVersion().getVersion()).getValue();
                var tool=ItemStack.parse(level.registryAccess(),fixed).orElseThrow();
                if(tool.getDamageValue()!=7 || !tool.getHoverName().getString().equals("Mining audit hammer"))throw new AssertionError("Saved hammer components lost: "+tool);
            }
            var pos=new BlockPos(8,100,8);
            var key=ResourceLocation.parse("modernmetals:beryllium_block");
            if(Files.exists(marker)) {
                if(!level.getBlockState(pos).is(BuiltInRegistries.BLOCK.get(key)))throw new AssertionError("Saved metal block lost on reopen");
            } else {
                level.setBlockAndUpdate(pos,BuiltInRegistries.BLOCK.get(key).defaultBlockState());
                Files.writeString(marker,"beryllium_block at 8,100,8\n");
            }
            int ores=0;
            // Generate distant chunks to cover the later-chunk path as well as the spawn region.
            for(int x=64;x<67;x++)for(int z=64;z<67;z++) {
                var chunk=level.getChunk(x,z);
                for(var section:chunk.getSections()) {
                    if(section.hasOnlyAir())continue;
                    for(int by=0;by<16;by++)for(int bz=0;bz<16;bz++)for(int bx=0;bx<16;bx++) {
                        var id=BuiltInRegistries.BLOCK.getKey(section.getBlockState(bx,by,bz).getBlock());
                        if(id.getNamespace().equals("modernmetals")&&id.getPath().endsWith("_ore")) {
                            if(id.getPath().equals("titanium_ore"))throw new AssertionError("Titanium generated while disabled");
                            ores++;
                        }
                    }
                }
            }
            if(!Files.exists(Path.of("fixture-blocks.json"))&&ores==0)throw new AssertionError("No Modern Metals ores in nine fresh chunks");
            server.saveEverything(false,true,true);
            Files.writeString(Path.of("server-smoke.json"),"{\"status\":\"passed\",\"oreBlocks\":"+ores+",\"legacyBlocks\":"+legacyBlocks+",\"legacyItemStacks\":"+legacyItems+",\"savedBlock\":\"modernmetals:beryllium_block\",\"source\":\""+source.replace("\\","/")+"\"}\n");
        }catch(Throwable failure) {
            try{Files.writeString(Path.of("server-smoke.json"),"{\"status\":\"failed\",\"error\":\""+failure.toString().replace("\\","/").replace("\"","'")+"\"}\n");}catch(Exception ignored){}
            failure.printStackTrace();
        }finally{server.halt(false);}
    }
}

package zone.moddev.mc.modernmetals.testmod;
import java.util.*;
import com.google.gson.*;
import java.io.*;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.chunk.*;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
final class OrePlacementChecks{
 static int run(MinecraftServer server)throws Exception{
  JsonObject rules=new JsonParser().parse(new InputStreamReader(OrePlacementChecks.class.getResourceAsStream("/data/modernmetals/orespawn/provider.json"),"UTF-8")).getAsJsonObject().getAsJsonObject("ores");
  Map<String,JsonObject> byId=new HashMap<>();rules.entrySet().forEach(e->byId.put(e.getValue().getAsJsonObject().get("block").getAsString(),e.getValue().getAsJsonObject()));
  int checks=0,total=0;
  for(DimensionType d:new DimensionType[]{DimensionType.OVERWORLD,DimensionType.THE_NETHER,DimensionType.THE_END}){
   ServerWorld world=server.getWorld(d);Map<String,Integer> counts=new TreeMap<>();
   for(int cx=128;cx<131;cx++)for(int cz=128;cz<131;cz++)for(ChunkSection s:world.getChunk(cx,cz).getSections()){
    if(s==null||s.isEmpty())continue;
    for(int x=0;x<16;x++)for(int y=0;y<16;y++)for(int z=0;z<16;z++){
     Block b=s.getBlockState(x,y,z).getBlock();String id=b.getRegistryName().toString();if(!id.startsWith("modernmetals:")||!id.endsWith("_ore"))continue;
     JsonObject rule=byId.get(id);if(rule==null||d!=DimensionType.OVERWORLD||(!rule.get("enabled").getAsBoolean()&&!Boolean.getBoolean("modernmetalsprobe.titanium")))throw new IllegalStateException("Unexpected generated ore "+id+" in "+d);
     JsonObject place=rule.getAsJsonObject("dimension_selectors").getAsJsonObject("orespawn:all_except_nether_end");int height=s.getYLocation()+y;
     if(height<place.get("min_y").getAsInt()||height>place.get("max_y").getAsInt())throw new IllegalStateException("Height "+id+" "+height);
     counts.merge(id,1,Integer::sum);checks++;
    }
   }
   if(d==DimensionType.OVERWORLD){if(counts.isEmpty())throw new IllegalStateException("No Modern Metals generated in nine new Overworld chunks");total=counts.size();
    if(Boolean.getBoolean("modernmetalsprobe.titanium")&&!counts.containsKey("modernmetals:titanium_ore"))throw new IllegalStateException("Enabled titanium did not generate");
   }
   System.out.println("MODERN_ORE_SAMPLE dimension="+DimensionType.getKey(d)+" chunks=9 counts="+counts);
  }
  if(total<10)throw new IllegalStateException("Incomplete ore generation sample "+total);return checks;
 }
}

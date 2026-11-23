package zone.moddev.mc.modernmetals.testmod;
import java.util.*;
import net.minecraft.block.Block;
import java.nio.file.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.model.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.Mod;
import zone.moddev.mc.modernmetals.client.ModernMetalsConfigScreen;
import zone.moddev.mc.modernmetals.config.*;
import zone.moddev.mc.modernmetals.content.*;
import zone.moddev.mc.orespawn.api.client.WorldSettingsExtensionRegistry;

/** Explicit automated client gate; excluded from production and manual profiles. */
@Mod("modernmetalsclientprobe")
@Mod.EventBusSubscriber(modid="modernmetalsclientprobe",value=Dist.CLIENT)
public final class ModernMetalsClientProbe {
    private static int state,ticks,loaded;
    private static net.minecraft.server.MinecraftServer stopping;
    private static void require(boolean value,String message){if(!value)throw new IllegalStateException(message);}
    @SubscribeEvent public static void tick(TickEvent.ClientTickEvent event){
        if(event.phase!=TickEvent.Phase.END||state==5)return;
        Minecraft mc=Minecraft.getInstance();
        try{
            if(ticks%200==0)System.out.println("MODERN_CLIENT_STAGE "+state+" screen="+(mc.currentScreen==null?"null":mc.currentScreen.getClass().getName()));
            if(mc.currentScreen instanceof net.minecraftforge.fml.client.gui.LoadingErrorScreen){
                StringBuilder details=new StringBuilder();
                for(java.lang.reflect.Field f:mc.currentScreen.getClass().getDeclaredFields())if(java.util.List.class.isAssignableFrom(f.getType())){
                    f.setAccessible(true);for(Object message:(java.util.List<?>)f.get(mc.currentScreen)){
                        if(message instanceof net.minecraftforge.fml.ModLoadingWarning)details.append(((net.minecraftforge.fml.ModLoadingWarning)message).formatToString());
                        else details.append(message);
                    }
                }
                throw new IllegalStateException("Forge loading screen: "+details);
            }
            if(++ticks>3600)throw new IllegalStateException("Client gate timeout in stage "+state);
            if(state==0&&mc.currentScreen instanceof MainMenuScreen){
                validate(mc);
                if(Boolean.getBoolean("modernmetalsclientprobe.menuOnly")){
                    Files.write(Paths.get("client-probe.json"),("{\"status\":\"passed\",\"menuOnly\":true,\"expectedSiblings\":\""+System.getProperty("modernmetalsclientprobe.expectedSiblings","")+"\"}").getBytes(java.nio.charset.StandardCharsets.UTF_8));state=5;mc.shutdown();return;
                }
                mc.launchIntegratedServer("modernmetals-smoke","Modern Metals 1.14 smoke",new WorldSettings(14162026L,GameType.CREATIVE,false,false,WorldType.DEFAULT));state=1;ticks=0;
            }else if(state==1&&mc.world!=null&&mc.player!=null&&++loaded>80){
                stopping=mc.getIntegratedServer();mc.world.sendQuittingDisconnectingPacket();mc.loadWorld(null);mc.displayGuiScreen(new MainMenuScreen());state=2;ticks=0;loaded=0;
            }else if(state==2&&mc.currentScreen instanceof MainMenuScreen&&!stopping.getExecutionThread().isAlive()){
                mc.launchIntegratedServer("modernmetals-smoke","Modern Metals 1.14 smoke",null);state=3;ticks=0;
            }else if(state==3&&mc.world!=null&&mc.player!=null&&++loaded>80){
                stopping=mc.getIntegratedServer();mc.world.sendQuittingDisconnectingPacket();mc.loadWorld(null);mc.displayGuiScreen(new MainMenuScreen());state=4;ticks=0;
            }else if(state==4&&!stopping.getExecutionThread().isAlive()){
                Files.write(Paths.get("client-probe.json"),("{\"status\":\"passed\",\"items\":"+ModContent.itemsById().size()+",\"ores\":20,\"worldReopened\":true}").getBytes(java.nio.charset.StandardCharsets.UTF_8));state=5;mc.shutdown();
            }
        }catch(Throwable e){
            e.printStackTrace();try{Files.write(Paths.get("client-probe.json"),("{\"status\":\"failed\",\"failure\":\""+e.toString().replace("\"","'")+"\"}").getBytes(java.nio.charset.StandardCharsets.UTF_8));}catch(Exception ignored){}state=5;mc.shutdown();
        }
    }
    private static void validate(Minecraft mc){
        require(ModList.get().isLoaded("basemetals")==Boolean.getBoolean("modernmetalsclientprobe.expectBase"),"Expected Base Metals installation");
        String siblings=System.getProperty("modernmetalsclientprobe.expectedSiblings");
        if(siblings!=null){
            Set<String> expected=new HashSet<>(Arrays.asList(siblings.split(",")));
            for(String mod:new String[]{"basemetals","basegems","baseminerals"})require(ModList.get().isLoaded(mod)==expected.contains(mod),"Exact sibling combination: "+mod);
            System.out.println("MODERN_PROFILE_CHECK PASS siblings="+siblings);
        }
        Screen parent=mc.currentScreen;
        Screen config=ModList.get().getModContainerById("modernmetals").get().getCustomExtension(ExtensionPoint.CONFIGGUIFACTORY).get().apply(mc,parent);
        require(config instanceof ModernMetalsConfigScreen,"Mods-list config factory");
        String original=ModernMetalsConfig.CONTENT_MODE.get();
        mc.displayGuiScreen(config);List<Button> buttons=new ArrayList<>();for(Object c:config.children())if(c instanceof Button)buttons.add((Button)c);
        require(buttons.size()==3,"Config controls");require(buttons.get(0).active!=ContentPolicy.inheritsBaseMetals(),"Config inherited lock");
        if(buttons.get(0).active)buttons.get(0).onPress();buttons.get(2).onPress();require(mc.currentScreen==parent,"Cancel returns to parent");require(original.equals(ModernMetalsConfig.CONTENT_MODE.get()),"Cancel preserves config");
        Screen child=WorldSettingsExtensionRegistry.extensions().stream().filter(e->"modernmetals:configuration".equals(e.id().toString())).findFirst().get().createScreen(parent);
        require(child instanceof ModernMetalsConfigScreen,"OreSpawn child config");mc.displayGuiScreen(child);child.onClose();require(mc.currentScreen==parent,"Child closes to parent");
        BarModelChecks.run(mc,"modernmetals");
        if(ModList.get().isLoaded("basemetals")){
            BarModelChecks.run(mc,"basemetals");
            int baseOres=0;
            for(Block block:net.minecraftforge.registries.ForgeRegistries.BLOCKS.getValues()){
                if(!"basemetals".equals(block.getRegistryName().getNamespace())||!block.getRegistryName().getPath().endsWith("_ore"))continue;
                String name=block.getRegistryName().getPath();String host=(name.equals("adamantine_ore")||name.equals("coldiron_ore"))?"minecraft:block/netherrack":name.equals("starsteel_ore")?"minecraft:block/end_stone":"minecraft:block/stone";
                validateOreModel(mc.getBlockRendererDispatcher().getModelForState(block.getDefaultState()),block,name,host,"basemetals:block/ore_overlays/"+name);
                validateOreModel(mc.getItemRenderer().getItemModelWithOverrides(new ItemStack(block),null,null),null,name,host,"basemetals:block/ore_overlays/"+name);baseOres++;
            }
            require(baseOres==13,"All Base Metals ore overlay gaps");System.out.println("BASE_ORE_GAP_CHECK PASS ores="+baseOres);
        }
        int ores=0;
        for(Map.Entry<String,RegistryHandle<net.minecraft.block.Block>> e:ModContent.blocksById().entrySet())if(e.getKey().endsWith("_ore")){
            ores++;net.minecraft.block.Block block=e.getValue().get();String name=e.getKey();
            if(Arrays.asList("thorium_ore","galvanizedsteel_ore","nichrome_ore","stainlesssteel_ore").contains(name)){
                require(block.getRenderLayer()==BlockRenderLayer.SOLID,"Historic solid ore "+name);continue;
            }
            require(block.getRenderLayer()==BlockRenderLayer.CUTOUT_MIPPED,"Ore cutout "+name);
            validateOreModel(mc.getBlockRendererDispatcher().getModelForState(block.getDefaultState()),block,name,"minecraft:block/stone","modernmetals:blocks/ore_overlays/"+name);
            validateOreModel(mc.getItemRenderer().getItemModelWithOverrides(new ItemStack(block),null,null),null,name,"minecraft:block/stone","modernmetals:blocks/ore_overlays/"+name);
        }
        require(ores==20,"All ore overlays");
        for(String name:ModContent.itemsById().keySet()){
            ItemStack item=new ItemStack(ModContent.item(name).get());IBakedModel model=mc.getItemRenderer().getItemModelWithOverrides(item,null,null);
            require(model!=mc.getModelManager().getMissingModel(),"Item model "+name);require(!item.getDisplayName().getString().startsWith("item.modernmetals."),"Item translation "+name);
        }
        for(String name:ModContent.fluids().keySet())validateBucketModel(mc,name+"_bucket");
    }
    private static void validateOreModel(IBakedModel model, Block block, String name, String host, String overlay) {
        require(host.equals(model.getParticleTexture().getName().toString()), name + " particle host");
        Random random = new Random(42L);
        require(model.getQuads(block == null ? null : block.getDefaultState(), null, random).isEmpty(),
                name + " has unexpected unculled faces");

        for (Direction side : Direction.values()) {
            List<BakedQuad> quads = model.getQuads(block == null ? null : block.getDefaultState(), side, random);
            require(quads.size() == 2, name + " should have base and overlay on " + side);
            boolean hasBase = false;
            boolean hasOverlay = false;
            for (BakedQuad quad : quads) {
                String texture = quad.getSprite().getName().toString();
                hasBase |= host.equals(texture);
                hasOverlay |= overlay.equals(texture);
                require(!"missingno".equals(quad.getSprite().getName().getPath()), name + " missing texture");
                validateOreVertices(quad, overlay.equals(texture), name);
                if (overlay.equals(texture)) {
                    boolean transparent = false;
                    boolean visible = false;

                    for (int y = 0; y < quad.getSprite().getHeight(); y++) {
                        for (int x = 0; x < quad.getSprite().getWidth(); x++) {
                            if (quad.getSprite().isPixelTransparent(0, x, y)) transparent = true;
                            else visible = true;
                        }
                    }

                    require(transparent && visible, name + " overlay must contain ore and transparent host pixels");
                }
            }

            require(hasBase && hasOverlay, name + " lost a texture layer on " + side);
        }
    }

    private static void validateOreVertices(BakedQuad quad, boolean overlay, String name) {
        int[] vertices = quad.getVertexData();
        int stride = vertices.length / 4;
        float low = overlay ? -0.05F / 16 : 0;
        float high = overlay ? 16.05F / 16 : 1;

        for (int vertex = 0; vertex < 4; vertex++) {
            float u = quad.getSprite().getUnInterpolatedU(Float.intBitsToFloat(vertices[vertex * stride + 4]));
            float v = quad.getSprite().getUnInterpolatedV(Float.intBitsToFloat(vertices[vertex * stride + 5]));
            require(u >= -0.01F && u <= 16.01F && v >= -0.01F && v <= 16.01F, name + " samples outside its texture sprite");
            for (int axis = 0; axis < 3; axis++) {
                float coordinate = Float.intBitsToFloat(vertices[vertex * stride + axis]);
                require(Math.min(Math.abs(coordinate - low), Math.abs(coordinate - high)) < 0.000001F,
                        name + " incorrect " + (overlay ? "overlay" : "base") + " vertex " + coordinate + " sprite=" + quad.getSprite().getName());
            }
        }
    }

    private static void validateBucketModel(Minecraft minecraft, String name) {
        ItemStack stack = new ItemStack(ModContent.item(name).get());
        IBakedModel model = minecraft.getItemRenderer().getItemModelWithOverrides(stack, null, null);
        Random random = new Random(42L);
        List<BakedQuad> quads = new ArrayList<BakedQuad>(model.getQuads(null, null, random));
        for (net.minecraft.util.Direction side : net.minecraft.util.Direction.values()) {
            quads.addAll(model.getQuads(null, side, random));
        }
        require(!quads.isEmpty(), name + " has no rendered quads");
        boolean hasTintedFluidLayer = false;
        for (BakedQuad quad : quads) {
            require(!"missingno".equals(quad.getSprite().getName().getPath()),
                    name + " uses the missing-texture sprite");
            if (quad.hasTintIndex() && quad.getTintIndex() == 1) hasTintedFluidLayer = true;
        }
        require(hasTintedFluidLayer, name + " has no tintable fluid layer");
        String fluidName = name.substring(0, name.length() - "_bucket".length());
        FluidContent fluid = ModContent.fluids().get(fluidName);
        require(fluid != null, name + " has no matching fluid content");
        require(minecraft.getItemColors().getColor(stack, 1) == ModContent.fluidColour(fluid.source().get()),
                name + " does not use its material fluid colour");
    }

}

package zone.moddev.mc.modernmetals.test;

import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import zone.moddev.mc.orespawn.api.client.WorldSettingsExtensionRegistry;

/** Only loaded by the explicit clientSmoke run; never packaged with the mod. */
@Mod(value="modernmetals_tests",dist=Dist.CLIENT)
public final class ClientSmoke {
    private int step, ticks, items, overlays;
    private Screen parent;
    public ClientSmoke() {
        if(Boolean.getBoolean("modernmetals.clientSmoke"))NeoForge.EVENT_BUS.addListener(this::tick);
    }
    private void tick(ClientTickEvent.Post event) {
        var mc=Minecraft.getInstance();
        if(mc.getOverlay()!=null || mc.getModelManager()==null)return;
        try {
            if(step==0) {
                if(mc.screen==null)return;
                // A disposable first launch can show accessibility onboarding before the title screen.
                if(!(mc.screen instanceof TitleScreen))mc.setScreen(new TitleScreen());
                if(Boolean.getBoolean("modernmetals.expectedPackaged")&&!zone.moddev.mc.modernmetals.ModernMetals.class.getResource("ModernMetals.class").toString().contains(".jar"))throw new AssertionError("Client product must load from candidate jar");
                if(Boolean.getBoolean("modernmetals.expectedBase")&&!ModList.get().isLoaded("basemetals"))throw new AssertionError("Base Metals missing from combined launch");
                for(var key:BuiltInRegistries.ITEM.keySet())if(key.getNamespace().equals("modernmetals")) {
                    var item=BuiltInRegistries.ITEM.get(key);
                    var model=mc.getItemRenderer().getModel(new ItemStack(item),null,null,0);
                    if(model==mc.getModelManager().getMissingModel())throw new AssertionError("Missing model "+key);
                    if(!net.minecraft.locale.Language.getInstance().has(item.getDescriptionId()))throw new AssertionError("Missing name "+key+" "+item.getDescriptionId());
                    items++;
                }
                for(var key:BuiltInRegistries.BLOCK.keySet())if(key.getNamespace().equals("modernmetals")&&key.getPath().endsWith("_ore")) {
                    var state=BuiltInRegistries.BLOCK.get(key).defaultBlockState();
                    var model=mc.getBlockRenderer().getBlockModel(state);
                    for(var face:Direction.values())for(var quad:model.getQuads(state,face,RandomSource.create(0))) {
                        var sprite=quad.getSprite();
                        if(!sprite.contents().name().getPath().contains("ore_overlays"))continue;
                        overlays++;
                        var vertices=quad.getVertices();int stride=vertices.length/4;
                        for(int v=0;v<4;v++) {
                            float u=Float.intBitsToFloat(vertices[v*stride+4]),w=Float.intBitsToFloat(vertices[v*stride+5]);
                            if(u<sprite.getU0()-0.00001 || u>sprite.getU1()+0.00001 || w<sprite.getV0()-0.00001 || w>sprite.getV1()+0.00001)throw new AssertionError("Overlay outside atlas sprite "+key);
                        }
                    }
                }
                if(overlays<102)throw new AssertionError("Expected all ore overlay faces, got "+overlays);
                parent=mc.screen;
                var container=ModList.get().getModContainerById("modernmetals").orElseThrow();
                mc.setScreen(container.getCustomExtension(IConfigScreenFactory.class).orElseThrow().createScreen(container,parent));
                step=1;ticks=0;
            } else if(++ticks>=5) {
                mc.screen.onClose();
                if(mc.screen!=parent)throw new AssertionError("Configuration screen did not return to its parent");
                if(step==1) {
                    var extension=WorldSettingsExtensionRegistry.extensions().stream().filter(e->e.id().getNamespace().equals("modernmetals")).findFirst().orElseThrow();
                    mc.setScreen(extension.createScreen(parent));step=2;ticks=0;
                } else {
                    finish(mc,"{\"status\":\"passed\",\"items\":"+items+",\"overlayFaces\":"+overlays+",\"configEntries\":2}");
                }
            }
        } catch(Throwable failure) {finish(mc,"{\"status\":\"failed\",\"error\":\""+failure.toString().replace("\\","/").replace("\"","'")+"\"}");}
    }
    private void finish(Minecraft mc,String result) {
        step=3;
        try {Files.writeString(Path.of("client-smoke.json"),result+"\n");}catch(Exception failure){throw new RuntimeException(failure);}
        mc.stop();
    }
}

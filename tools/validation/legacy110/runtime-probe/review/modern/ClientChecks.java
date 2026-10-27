package review.modern;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.init.Materials;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.reflect.Method;
import java.util.concurrent.Future;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries.BooleanEntry;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CycleValueEntry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zone.moddev.mc.modernmetals.util.Config;
import zone.moddev.mc.modernmetals.content.ContentPolicy;
import zone.moddev.mc.orespawn.api.client.WorldSettingsExtension;
import zone.moddev.mc.orespawn.api.client.WorldSettingsExtensionRegistry;

/** Automated graphical acceptance probes, isolated from all release archives. */
@Mod(modid="modernmetalsclientprobe",name="Modern Metals client checks",version="1",
     acceptableRemoteVersions="*",dependencies="required-after:modernmetalsprobe")
public final class ClientChecks {
    @Mod.EventHandler public void init(final FMLInitializationEvent event) {
        if (Boolean.getBoolean("modern.client")) MinecraftForge.EVENT_BUS.register(new Checks());
    }
    private static void require(final boolean pass, final String detail) {
        if (!pass) throw new AssertionError(detail);
    }
    public static final class Checks {
        private long started=System.currentTimeMillis();
        private int ticks;
        private int state;
        private GuiScreen parent;
        private GuiConfig screen;
        private Future<?> check;
        private boolean finished;
        @SubscribeEvent public void tick(final TickEvent.ClientTickEvent event) throws Exception {
            if (event.phase!=TickEvent.Phase.END || finished) return;
            final Minecraft mc=Minecraft.getMinecraft();
            try {
                if (state==0 && mc.currentScreen instanceof GuiMainMenu && ++ticks>=20) {
                    parent=mc.currentScreen;
                    final IModGuiFactory factory=FMLClientHandler.instance().getGuiFactoryFor(Loader.instance().getIndexedModList().get("modernmetals"));
                    require(factory!=null,"Forge Mods config factory missing");
                    screen=(GuiConfig)factory.mainConfigGuiClass().getConstructor(GuiScreen.class).newInstance(parent);
                    mc.displayGuiScreen(screen); state=1;ticks=0;
                } else if(state==1 && ++ticks>=20) {
                    require(screen.parentScreen==parent && screen.configElements.size()==22,"Forge config parent or fantasy/material options incorrect");
                    require(screen.allRequireMcRestart,"Registry switches must require a restart");
                    require(!screen.title.startsWith("config."),"Untranslated config title");
                    for(final net.minecraftforge.fml.client.config.IConfigElement element:screen.configElements)
                        require(net.minecraft.client.resources.I18n.hasKey(element.getLanguageKey()),"Untranslated material label: "+element.getName());
                    final String startupMode=Boolean.getBoolean("modern.lowFantasy")?"low_fantasy":"high_fantasy";
                    require(mode(screen).getCurrentValue().equals(startupMode),"Wrong startup fantasy selection");
                    require(ContentPolicy.active().lowFantasy()==Boolean.getBoolean("modern.lowFantasy"),"Wrong active fantasy mode");
                    screenshot(mc,"forge-mods-config.png");
                    mode(screen).valueButtonPressed(0);
                    final BooleanEntry entry=entry(screen,"EnableBoron");
                    entry.valueButtonPressed(0);
                    invoke(screen,"keyTyped",new Class<?>[]{char.class,int.class},'\0',1);
                    require(mc.currentScreen==parent,"Escape did not return to Forge parent");
                    require(Config.getConfiguration().get("Metals","EnableBoron",true).getBoolean(),"Escape saved a discarded switch");
                    require(Config.getConfiguration().getCategory("General").get("contentMode").getString().equals(startupMode),"Escape saved a discarded fantasy selection");
                    final WorldSettingsExtension extension=WorldSettingsExtensionRegistry.extensions().stream()
                        .filter(e->"modernmetals".equals(e.id().getResourceDomain())).findFirst().orElse(null);
                    require(extension!=null,"OreSpawn child Mods config entry missing");
                    screen=(GuiConfig)extension.createScreen(parent);
                    mc.displayGuiScreen(screen);state=2;ticks=0;
                } else if(state==2 && ++ticks>=20) {
                    require(screen.parentScreen==parent && screen.configElements.size()==22,"OreSpawn config parent or options incorrect");
                    screenshot(mc,"orespawn-child-mods-config.png");
                    mode(screen).valueButtonPressed(0);
                    screen.entryList.scrollBy(10000);
                    require(screen.entryList.getAmountScrolled()>0,"Material list cannot scroll");
                    entry(screen,"EnableBoron").valueButtonPressed(0);
                    invoke(screen,"actionPerformed",new Class<?>[]{GuiButton.class},new GuiButton(2000,0,0,"Done"));
                    require(!Config.getConfiguration().get("Metals","EnableBoron",true).getBoolean(),"Done did not save the switch");
                    require(Config.Options.enableBoron && Materials.hasMaterial("boron"),"GUI rebuilt live registries");
                    final String pendingMode=Boolean.getBoolean("modern.lowFantasy")?"high_fantasy":"low_fantasy";
                    require(Config.getConfiguration().getCategory("General").get("contentMode").getString().equals(pendingMode),"Done did not save the fantasy selection");
                    require(ContentPolicy.active().lowFantasy()==Boolean.getBoolean("modern.lowFantasy"),"GUI applied fantasy recipes without a restart");
                    require(new String(Files.readAllBytes(Paths.get("config/ModernMetals.cfg")),StandardCharsets.UTF_8).contains("B:EnableBoron=false"),"Done did not persist the file");
                    require(new String(Files.readAllBytes(Paths.get("config/ModernMetals.cfg")),StandardCharsets.UTF_8).contains("S:contentMode="+pendingMode),"Done did not persist the fantasy setting");
                    // Restore the fixture through the same save path before the reload test.
                    screen=new zone.moddev.mc.modernmetals.client.ModernMetalsConfigScreen(parent);
                    mc.displayGuiScreen(screen);
                    entry(screen,"EnableBoron").valueButtonPressed(0);
                    invoke(screen,"actionPerformed",new Class<?>[]{GuiButton.class},new GuiButton(2000,0,0,"Done"));
                    mc.displayGuiScreen(parent);
                    mc.launchIntegratedServer("modern-check","Modern Metals disposable checks",new WorldSettings(8675309L,GameType.CREATIVE,false,false,WorldType.DEFAULT));
                    state=3;
                } else if(state==3 && mc.world!=null && mc.player!=null && check==null) {
                    for(final net.minecraft.block.Block block:net.minecraftforge.fml.common.registry.ForgeRegistries.BLOCKS.getValues()) {
                        if(!"modernmetals".equals(block.getRegistryName().getResourceDomain())) continue;
                        for(final net.minecraft.block.state.IBlockState variant:block.getBlockState().getValidStates())
                            require(mc.getBlockRendererDispatcher().getBlockModelShapes().getModelForState(variant)
                                !=mc.getRenderItem().getItemModelMesher().getModelManager().getMissingModel(),"Missing baked block variant: "+variant);
                    }
                    for(final com.mcmoddev.lib.material.MMDMaterial material:Materials.getMaterialsByMod("modernmetals")) {
                        boolean tab=false;
                        for(final net.minecraft.creativetab.CreativeTabs value:material.getItem(Names.INGOT).getCreativeTabs())
                            if(value!=null && value.getTabLabel().startsWith("modernmetals.")) tab=true;
                        require(tab,"Missing Modern Metals creative tab: "+material.getName());
                    }
                    for(final String label:new String[]{"aluminumbrass_maker","galvanized_steel_maker","nichrome_maker","stainless_steel_maker","titanium_maker"}) {
                        require(net.minecraft.client.resources.I18n.hasKey("achievement."+label),"Missing achievement label: "+label);
                        require(net.minecraft.client.resources.I18n.hasKey("achievement."+label+".desc"),"Missing achievement description: "+label);
                    }
                    for(final com.mcmoddev.lib.material.MMDMaterial material:Materials.getMaterialsByMod("modernmetals")) {
                        final String name=material.getName();
                        for(final Names form:new Names[]{Names.INGOT,Names.PLATE,Names.PICKAXE}) {
                            final ItemStack stack=Materials.getMaterialByName(name).hasItem(form)
                                ? Materials.getMaterialByName(name).getItemStack(form)
                                : Materials.getMaterialByName(name).getBlockItemStack(form);
                            require(stack!=null,"Missing model input: "+name+":"+form);
                            final IBakedModel model=mc.getRenderItem().getItemModelMesher().getItemModel(stack);
                            require(model!=mc.getRenderItem().getItemModelMesher().getModelManager().getMissingModel(),"Missing baked model: "+name+":"+form);
                        }
                    }
                    check=mc.getIntegratedServer().addScheduledTask(()->{
                        try { ModernMetalsProbe.run(mc.getIntegratedServer()); }
                        catch(Exception failure) {throw new RuntimeException(failure);}
                    });
                } else if(check!=null && check.isDone()) {
                    check.get(); screenshot(mc,"integrated-world.png"); report(true,"Forge Mods and OreSpawn config, fantasy selector save/cancel and full restart, material switches, baked models and integrated saved world");
                    finished=true;mc.shutdown();
                }
                if(System.currentTimeMillis()-started>240000) throw new AssertionError("Client check timed out, state="+state);
            } catch(Throwable failure) {report(false,failure.toString());failure.printStackTrace();finished=true;mc.shutdown();}
        }
        private static BooleanEntry entry(final GuiConfig screen,final String name) {
            for(int i=0;i<screen.configElements.size();i++) if(name.equals(screen.configElements.get(i).getName()))
                return (BooleanEntry)screen.entryList.listEntries.get(i);
            throw new AssertionError("Missing config entry: "+name);
        }
        private static CycleValueEntry mode(final GuiConfig screen) {
            for(int i=0;i<screen.configElements.size();i++) if("contentMode".equals(screen.configElements.get(i).getName()))
                return (CycleValueEntry)screen.entryList.listEntries.get(i);
            throw new AssertionError("Missing fantasy selector");
        }
        private static void invoke(final GuiConfig screen,final String name,final Class<?>[] types,final Object...args) throws Exception {
            Method method;
            try {method=GuiConfig.class.getDeclaredMethod(name,types);}
            catch(final NoSuchMethodException packaged) {
                method=GuiConfig.class.getDeclaredMethod("keyTyped".equals(name)?"func_73869_a":"func_146284_a",types);
            }
            method.setAccessible(true);method.invoke(screen,args);
        }
        private static void screenshot(final Minecraft mc,final String name) {
            net.minecraft.util.ScreenShotHelper.saveScreenshot(mc.mcDataDir,name,mc.displayWidth,mc.displayHeight,mc.getFramebuffer());
        }
        private static void report(final boolean passed,final String detail) throws Exception {
            final JsonObject report=new JsonObject();report.addProperty("passed",passed);report.addProperty("detail",detail);
            Files.write(Paths.get(System.getProperty("modern.phase")+"-client.json"),(new GsonBuilder().setPrettyPrinting().create().toJson(report)+"\n").getBytes(StandardCharsets.UTF_8));
            System.out.println("MODERN_METALS_CLIENT_"+(passed?"PASS":"FAIL")+" "+detail);
        }
    }
}

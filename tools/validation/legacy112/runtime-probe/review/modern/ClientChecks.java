package review.modern;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import zone.moddev.mc.modernmetals.content.ContentPolicy;

public final class ClientChecks {
    public static void install() {
        if (Boolean.getBoolean("review.oreDisplay")) {
            OreOverlayChecks.installDisplay();
            return;
        }
        final String server = System.getProperty("review.remoteServer");
        MinecraftForge.EVENT_BUS.register(server == null ? new ClientChecks() : new RemoteCheck(server));
    }

    private GuiScreen menu;
    private GuiConfig screen;
    private int phase;

    @SubscribeEvent
    public void check(final TickEvent.ClientTickEvent event) throws Exception {
        if (event.phase != TickEvent.Phase.END || phase == 3) return;
        final Minecraft minecraft = Minecraft.getMinecraft();
        if (phase == 0 && minecraft.currentScreen instanceof GuiMainMenu) {
            menu = minecraft.currentScreen;
            final IModGuiFactory factory = FMLClientHandler.instance()
                    .getGuiFactoryFor(Loader.instance().getIndexedModList().get("modernmetals"));
            require(factory != null && factory.hasConfigGui(), "Forge's Mods factory is missing");
            screen = (GuiConfig) factory.createConfigGui(menu);
            minecraft.displayGuiScreen(screen);
            phase++;
        } else if (phase == 1) {
            verifyScreen(screen, menu);
            final Class<?> directoryType = Class.forName("zone.moddev.mc.orespawn.client.OreSpawnModsScreen");
            final Constructor<?> constructor = directoryType.getDeclaredConstructor(GuiScreen.class);
            constructor.setAccessible(true);
            final GuiScreen directory = (GuiScreen) constructor.newInstance(menu);
            final Field entries = directoryType.getDeclaredField("entries");
            entries.setAccessible(true);
            for (final Object entry : (Iterable<?>) entries.get(directory)) {
                final Field modId = entry.getClass().getDeclaredField("modId");
                modId.setAccessible(true);
                if ("modernmetals".equals(modId.get(entry))) {
                    minecraft.displayGuiScreen(directory);
                    final Method open = directoryType.getDeclaredMethod("open", entry.getClass());
                    open.setAccessible(true);
                    open.invoke(directory, entry);
                    require(minecraft.currentScreen instanceof GuiConfig, "OreSpawn did not open the config");
                    screen = (GuiConfig) minecraft.currentScreen;
                    menu = directory;
                    phase++;
                    return;
                }
            }
            throw new IllegalStateException("Modern Metals is absent from OreSpawn's Mods list");
        } else if (phase == 2) {
            verifyScreen(screen, menu);
            for (final String path : new String[] { "aluminum_plate", "boron_ore", "titanium_ore" }) {
                final ResourceLocation id = new ResourceLocation("modernmetals", path);
                require(minecraft.getBlockRendererDispatcher().getBlockModelShapes().getModelForState(
                        ForgeRegistries.BLOCKS.getValue(id).getDefaultState())
                        != minecraft.getRenderItem().getItemModelMesher().getModelManager().getMissingModel(),
                        "Missing block model " + id);
            }
            OreOverlayChecks.verify(minecraft);
            Files.write(Paths.get("modernmetals-client-pass.txt"),
                    Collections.singleton("Forge and OreSpawn screens and three baked block models passed."),
                    StandardCharsets.UTF_8);
            phase++;
            minecraft.shutdown();
        }
    }

    public static final class RemoteCheck {
        private final String address;
        private final String expectedDisconnect = System.getProperty("review.expectedDisconnect", "");
        private final long deadline = System.currentTimeMillis() + 90000;
        private boolean connecting;
        private boolean finished;

        RemoteCheck(final String address) {
            this.address = address;
        }

        @SubscribeEvent
        public void check(final TickEvent.ClientTickEvent event) throws Exception {
            if (event.phase != TickEvent.Phase.END || finished) return;
            final Minecraft minecraft = Minecraft.getMinecraft();
            if (!connecting && minecraft.currentScreen instanceof GuiMainMenu) {
                minecraft.displayGuiScreen(new GuiConnecting(minecraft.currentScreen, minecraft,
                        new ServerData("Disposable Forge", address, false)));
                connecting = true;
            } else if (minecraft.world != null && minecraft.player != null) {
                require(expectedDisconnect.isEmpty(), "Mismatched player entered the world");
                finish(minecraft, "Joined dedicated server");
            } else if (connecting && minecraft.currentScreen instanceof GuiDisconnected) {
                for (final Field field : GuiDisconnected.class.getDeclaredFields()) {
                    if (field.getType() != ITextComponent.class) continue;
                    field.setAccessible(true);
                    final String reason = ((ITextComponent) field.get(minecraft.currentScreen)).getUnformattedText();
                    require(!expectedDisconnect.isEmpty() && reason.contains(expectedDisconnect), reason);
                    finish(minecraft, reason);
                    return;
                }
                throw new IllegalStateException("Missing disconnect reason");
            } else {
                require(System.currentTimeMillis() < deadline, "Dedicated server login timed out");
            }
        }

        private void finish(final Minecraft minecraft, final String result) throws Exception {
            Files.write(Paths.get("modernmetals-connection-pass.txt"),
                    Collections.singleton(result), StandardCharsets.UTF_8);
            finished = true;
            minecraft.shutdown();
        }
    }

    private static void verifyScreen(final GuiConfig screen, final GuiScreen parent) {
        require(screen.parentScreen == parent && screen.allRequireMcRestart, "Wrong config parent or restart policy");
        require(I18n.hasKey("config.modernmetals.title"), "Missing localized title");
        final boolean hasMode = screen.configElements.stream().anyMatch(element -> "contentMode".equals(element.getName()));
        require(hasMode != ContentPolicy.inheritsBaseMetals(), "Fantasy selector belongs to the wrong mod");
    }

    private static void require(final boolean passes, final String message) {
        if (!passes) throw new IllegalStateException(message);
    }
}

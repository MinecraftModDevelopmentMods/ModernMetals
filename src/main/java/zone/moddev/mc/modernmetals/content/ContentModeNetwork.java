package zone.moddev.mc.modernmetals.content;

import java.util.Set;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.CustomPacketRegistrationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/** Advertise the active recipe mode in Forge's login channel registration. */
public final class ContentModeNetwork {
    private static final String HIGH = "ModernMetals|High";
    private static final String LOW = "ModernMetals|Low";

    private ContentModeNetwork() {}

    public static void register() {
        NetworkRegistry.INSTANCE.newEventDrivenChannel(ContentPolicy.active().lowFantasy() ? LOW : HIGH);
        MinecraftForge.EVENT_BUS.register(new ContentModeNetwork());
    }

    @SubscribeEvent
    public void checkMode(final CustomPacketRegistrationEvent<?> event) {
        final Set<String> channels = event.getRegistrations();
        // Forge includes FML|HS in its login registration, not ordinary channel updates.
        if (!"REGISTER".equals(event.getOperation()) || !channels.contains("FML|HS")) return;

        final boolean localLow = ContentPolicy.active().lowFantasy();
        final boolean remoteLow = channels.contains(LOW);
        final boolean remoteHigh = channels.contains(HIGH);
        final boolean server = event.getSide().isServer();
        final ITextComponent reason;
        if (remoteLow == remoteHigh) {
            // A client with no mode channel may also lack the translation key.
            reason = server
                    ? new TextComponentString("Modern Metals could not check the Fantasy Level. "
                            + "Use the same Modern Metals build on the server and client.")
                    : new TextComponentTranslation("config.modernmetals.connection.protocol");
        } else if (remoteLow == localLow) {
            return;
        } else {
            final boolean serverLow = server ? localLow : remoteLow;
            reason = new TextComponentTranslation("config.modernmetals.connection.mismatch",
                    modeLabel(serverLow), modeLabel(!serverLow));
        }

        final NetworkManager manager = event.getManager();
        manager.channel().config().setAutoRead(false);
        if (server) {
            manager.sendPacket(new SPacketDisconnect(reason), future -> manager.closeChannel(reason));
        } else {
            manager.closeChannel(reason);
        }
    }

    private static ITextComponent modeLabel(final boolean low) {
        return new TextComponentTranslation("config.modernmetals.content_mode." + (low ? "low_fantasy" : "high_fantasy"));
    }
}

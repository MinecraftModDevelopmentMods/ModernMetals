package zone.moddev.mc.modernmetals.test;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;

/** Explicitly loaded test mod, excluded from product jars and ordinary launches. */
@Mod("modernmetals_tests")
public final class ModernMetalsTestMod {
    public ModernMetalsTestMod(IEventBus bus) {
        ServerSmoke.register();
        bus.addListener((RegisterGameTestsEvent event) -> event.register(ProcessingGameTests.class));
        bus.addListener((RegisterGameTestsEvent event) -> event.register(FantasyGameTests.class));
    }
}

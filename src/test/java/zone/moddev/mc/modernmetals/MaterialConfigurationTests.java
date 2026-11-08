package zone.moddev.mc.modernmetals;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import zone.moddev.mc.modernmetals.util.Config;

class MaterialConfigurationTests {
    private void enabled(final String name) throws Exception {
        final Field field = Config.Options.class.getField("enable" + name);
        assertTrue(field.getBoolean(null), name + " must be enabled by default");
    }
    @Test void boronIsConfigurable() throws Exception { enabled("Boron"); }
    @Test void berylliumIsConfigurable() throws Exception { enabled("Beryllium"); }
    @Test void thoriumIsConfigurable() throws Exception { enabled("Thorium"); }
    @Test void titaniumMaterialRemainsEnabled() throws Exception { enabled("Titanium"); }
}

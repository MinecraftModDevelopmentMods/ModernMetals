package zone.moddev.mc.modernmetals;

import static org.junit.jupiter.api.Assertions.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.util.ResourceLocation;
import org.junit.jupiter.api.Test;
import zone.moddev.mc.orespawn.api.WorldgenProvider;

class NativeProviderTests {
    private Optional<WorldgenProvider> build(final Predicate<ResourceLocation> available) throws Exception {
        final Class<?> provider = Class.forName("zone.moddev.mc.modernmetals.worldgen.ModernMetalsWorldgenProvider");
        return (Optional<WorldgenProvider>) provider.getMethod("build", Predicate.class).invoke(null, available);
    }
    @Test void preservesHistoricalRulesAndAddsOptionalTitanium() throws Exception {
        final JsonObject nativeProvider = build(id -> true).get().toJson();
        Files.createDirectories(Paths.get("build/reports"));
        Files.write(Paths.get("build/reports/native-provider.json"), (nativeProvider.toString()+"\n").getBytes(StandardCharsets.UTF_8));
        assertEquals(5, nativeProvider.get("schema_version").getAsInt());
        final JsonObject ores = nativeProvider.getAsJsonObject("ores");
        assertEquals(17, ores.entrySet().size());
        final JsonObject old = new JsonParser().parse(new String(Files.readAllBytes(Paths.get(
            "tools/validation/legacy110/fixtures/orespawn3-modernmetals.json")), StandardCharsets.UTF_8)).getAsJsonObject().getAsJsonObject("spawns");
        for (final Map.Entry<String, com.google.gson.JsonElement> entry : old.entrySet()) {
            final JsonObject params = entry.getValue().getAsJsonObject().getAsJsonObject("parameters");
            final JsonObject ore = ores.getAsJsonObject("modernmetals:legacy/"+entry.getKey());
            assertTrue(ore.get("enabled").getAsBoolean());
            assertFalse(ore.get("retrogen").getAsBoolean());
            assertFalse(ore.get("native_generation").getAsBoolean());
            final JsonObject placement = ore.getAsJsonObject("dimension_selectors").getAsJsonObject("orespawn:all_except_nether_end");
            assertEquals(params.get("frequency").getAsDouble(), placement.get("frequency").getAsDouble());
            assertEquals(params.get("maxHeight").getAsInt()-1, placement.get("max_y").getAsInt());
            assertEquals(Math.max(1,params.get("size").getAsInt()-params.get("variation").getAsInt()), placement.get("min_quantity").getAsInt());
            assertEquals(params.get("size").getAsInt()+params.get("variation").getAsInt()-1, placement.get("max_quantity").getAsInt());
            assertEquals("orespawn:standard",placement.get("placement_channel").getAsString());
        }
        final JsonObject titanium = ores.getAsJsonObject("modernmetals:legacy/titanium_ore");
        assertFalse(titanium.get("enabled").getAsBoolean());
        assertEquals(ores.getAsJsonObject("modernmetals:legacy/rutile_ore").get("dimension_selectors"),titanium.get("dimension_selectors"));
        for (final String section : new String[]{"rocks","terrain_dimensions","geomes","fluid_deposits"})
            assertEquals(0,nativeProvider.getAsJsonObject(section).entrySet().size());
    }
    @Test void omitsMissingOrDisabledBlocks() throws Exception {
        assertFalse(build(id -> false).isPresent());
        final JsonObject ores = build(id -> !id.getResourcePath().equals("boron_ore")).get().toJson().getAsJsonObject("ores");
        assertEquals(16, ores.entrySet().size());
        assertFalse(ores.has("modernmetals:legacy/boron_ore"));
    }
}

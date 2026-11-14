package zone.moddev.mc.modernmetals.content;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

final class LegacyRegistry {
    private static final Set<String> ITEMS = new HashSet<>();

    static {
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(
                LegacyRegistry.class.getResourceAsStream("/data/modernmetals/migration/registry_1_12.json")),
                StandardCharsets.UTF_8);
        try (InputStreamReader closeable = reader) {
            for (JsonElement element : new JsonParser().parse(closeable).getAsJsonObject().getAsJsonArray("items")) {
                ITEMS.add(element.getAsString());
            }
        } catch (java.io.IOException exception) {
            throw new IllegalStateException("Could not read the historical item registry", exception);
        }
    }

    private LegacyRegistry() {}

    static boolean containsItem(String id) { return ITEMS.contains(id); }
}

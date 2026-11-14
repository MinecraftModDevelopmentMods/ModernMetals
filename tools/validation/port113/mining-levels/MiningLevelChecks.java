package validation;

import java.util.ArrayList;
import java.util.List;
import zone.moddev.mc.modernmetals.material.MaterialCatalogue;
import zone.moddev.mc.modernmetals.material.MaterialDefinition;

/** Independent acceptance table for the reviewed 1.13.2 mining policy. */
public final class MiningLevelChecks {
    public static void main(String[] args) throws Exception {
        List<String> failures = new ArrayList<>();
        String[] expected = {
            "aluminum:0:0:1", "aluminumbrass:1:1:1", "beryllium:1:1:1",
            "boron:1:2:1", "cadmium:0:0:1", "chromium:1:2:1",
            "galvanizedsteel:2:2:1", "iridium:1:2:1", "magnesium:0:0:1",
            "manganese:1:1:1", "nichrome:2:2:1", "osmium:1:2:1",
            "plutonium:1:1:1", "rutile:1:2:1", "stainlesssteel:2:2:1",
            "tantalum:1:1:1", "thorium:1:1:1", "titanium:2:2:1",
            "tungsten:1:2:1", "uranium:1:1:1", "zirconium:1:1:1"
        };
        if (MaterialCatalogue.ALL.size() != expected.length) failures.add("Incomplete material audit");
        for (int index = 0; index < expected.length; index++) {
            String[] row = expected[index].split(":");
            MaterialDefinition material = MaterialCatalogue.ALL.get(index);
            if (!material.name().equals(row[0])) failures.add("Unexpected catalogue order: " + material.name());
            check(failures, material.name(), "tool", Integer.parseInt(row[1]), material.toolLevel());
            check(failures, material.name(), "solid block", Integer.parseInt(row[2]), material.requiredHarvestLevel());
            int ore;
            try {
                ore = (Integer) material.getClass().getMethod("requiredOreHarvestLevel").invoke(material);
            } catch (NoSuchMethodException legacy) {
                ore = material.requiredHarvestLevel();
            }
            check(failures, material.name(), "ore", Integer.parseInt(row[3]), ore);
        }
        if (!failures.isEmpty()) throw new AssertionError(String.join("\n", failures));
        System.out.println("MINING_LEVEL_CHECKS PASS: 21 complete material profiles");
    }

    private static void check(List<String> failures, String name, String form, int expected, int actual) {
        if (expected != actual) failures.add(name + " " + form + ": expected " + expected + ", was " + actual);
    }
}

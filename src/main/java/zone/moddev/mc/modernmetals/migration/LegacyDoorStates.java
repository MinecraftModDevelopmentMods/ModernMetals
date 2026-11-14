package zone.moddev.mc.modernmetals.migration;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BitArray;

/** In old saves each door half held only part of the state. Forge 25 needs the full state on both. */
final class LegacyDoorStates {
    private static final int SECTION_SIZE = 4096;
    private static final int LAYER_SIZE = 256;

    private LegacyDoorStates() {}

    static void joinHalves(NBTTagCompound level) {
        NBTTagList sections = level.getList("Sections", 10);
        Map<Integer, Section> byY = new HashMap<>();
        for (int index = 0; index < sections.size(); index++) {
            NBTTagCompound section = sections.getCompound(index);
            if (section.contains("Palette", 9) && section.contains("BlockStates", 12)) {
                byY.put(section.getInt("Y"), new Section(section));
            }
        }
        for (Map.Entry<Integer, Section> entry : byY.entrySet()) {
            Section section = entry.getValue();
            if (!section.hasDoor) continue;
            for (int index = 0; index < SECTION_SIZE; index++) {
                NBTTagCompound lower = section.get(index);
                if (!isDoor(lower) || !"lower".equals(lower.getCompound("Properties").getString("half"))) continue;
                boolean crossesSection = index >= SECTION_SIZE - LAYER_SIZE;
                Section above = crossesSection ? byY.get(entry.getKey() + 1) : section;
                if (above == null) continue;
                int upperIndex = (index + LAYER_SIZE) % SECTION_SIZE;
                NBTTagCompound upper = above.get(upperIndex);
                if (!lower.getString("Name").equals(upper.getString("Name"))
                        || !"upper".equals(upper.getCompound("Properties").getString("half"))) continue;
                NBTTagCompound fullLower = lower.copy();
                NBTTagCompound fullUpper = upper.copy();
                copy(upper, fullLower, "hinge");
                copy(upper, fullLower, "powered");
                copy(lower, fullUpper, "facing");
                copy(lower, fullUpper, "open");
                section.set(index, fullLower);
                above.set(upperIndex, fullUpper);
            }
        }
        for (Section section : byY.values()) {
            if (section.changed) section.nbt.setLongArray("BlockStates", section.bits.getBackingLongArray());
        }
    }

    private static boolean isDoor(NBTTagCompound state) {
        String name = state.getString("Name");
        return name.startsWith("modernmetals:") && name.endsWith("_door");
    }

    private static void copy(NBTTagCompound from, NBTTagCompound to, String property) {
        to.getCompound("Properties").setString(property, from.getCompound("Properties").getString(property));
    }

    private static final class Section {
        private final NBTTagCompound nbt;
        private final NBTTagList palette;
        private final Map<NBTTagCompound, Integer> entries = new HashMap<>();
        private final boolean hasDoor;
        private BitArray bits;
        private boolean changed;

        Section(NBTTagCompound nbt) {
            this.nbt = nbt;
            palette = nbt.getList("Palette", 10);
            bits = new BitArray(bitsNeeded(palette.size()), SECTION_SIZE, nbt.getLongArray("BlockStates"));
            boolean foundDoor = false;
            for (int i = 0; i < palette.size(); i++) {
                NBTTagCompound state = palette.getCompound(i);
                entries.put(state, i);
                foundDoor |= isDoor(state);
            }
            hasDoor = foundDoor;
        }

        NBTTagCompound get(int index) { return palette.getCompound(bits.getAt(index)); }

        void set(int index, NBTTagCompound state) {
            if (state.equals(get(index))) return;
            Integer entry = entries.get(state);
            if (entry == null) {
                entry = palette.size();
                palette.add(state);
                entries.put(state, entry);
                int width = bitsNeeded(palette.size());
                if (width != bits.bitsPerEntry()) {
                    BitArray expanded = new BitArray(width, SECTION_SIZE);
                    for (int i = 0; i < SECTION_SIZE; i++) expanded.setAt(i, bits.getAt(i));
                    bits = expanded;
                }
            }
            bits.setAt(index, entry);
            changed = true;
        }

        private static int bitsNeeded(int size) {
            return Math.max(4, Integer.SIZE - Integer.numberOfLeadingZeros(size - 1));
        }
    }
}

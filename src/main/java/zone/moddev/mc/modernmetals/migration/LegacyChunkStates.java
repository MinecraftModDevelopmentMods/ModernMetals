package zone.moddev.mc.modernmetals.migration;

import net.minecraft.nbt.CompoundTag;

/** Converts the mod's pre-1.16 wall properties before palette decoding. */
public final class LegacyChunkStates {
    private LegacyChunkStates() {}

    public static void prepare(CompoundTag chunk) {
        for (var section : chunk.getList("sections", 10)) {
            for (var entry : ((CompoundTag) section).getCompound("block_states").getList("palette", 10)) {
                var state = (CompoundTag) entry;
                String name = state.getString("Name");
                if (!name.startsWith("modernmetals:") || !name.endsWith("_wall")) continue;
                var properties = state.getCompound("Properties");
                for (String side : new String[] {"north", "east", "south", "west"}) {
                    String value = properties.getString(side);
                    if (value.equals("true")) properties.putString(side, "low");
                    else if (value.equals("false")) properties.putString(side, "none");
                }
            }
        }
    }
}

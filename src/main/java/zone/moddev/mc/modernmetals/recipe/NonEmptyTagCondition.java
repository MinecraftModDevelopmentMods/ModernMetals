package zone.moddev.mc.modernmetals.recipe;

import com.google.gson.JsonObject;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

/** Keeps optional cross-mod alloys absent until their ingredient tags exist. */
public final class NonEmptyTagCondition implements ICondition {
    private static final ResourceLocation ID = new ResourceLocation("modernmetals", "non_empty_tag");
    private final ResourceLocation tag;
    private NonEmptyTagCondition(ResourceLocation tag) { this.tag = tag; }
    @Override public ResourceLocation getID() { return ID; }
    @Override public boolean test() {
        net.minecraft.tags.Tag<net.minecraft.item.Item> values = ItemTags.getCollection().get(tag);
        return values != null && !values.getAllElements().isEmpty();
    }
    public static void register() { CraftingHelper.register(new Serializer()); }
    private static final class Serializer implements IConditionSerializer<NonEmptyTagCondition> {
        @Override public ResourceLocation getID() { return ID; }
        @Override public void write(JsonObject json, NonEmptyTagCondition condition) { json.addProperty("tag", condition.tag.toString()); }
        @Override public NonEmptyTagCondition read(JsonObject json) { return new NonEmptyTagCondition(new ResourceLocation(JSONUtils.getString(json,"tag"))); }
    }
}

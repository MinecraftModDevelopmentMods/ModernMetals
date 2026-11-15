package zone.moddev.mc.modernmetals.content;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.nbt.NbtOps;
import net.minecraft.SharedConstants;
import com.mojang.serialization.Dynamic;

/** Retains the generic arrow/bolt IDs and their saved 1.13 ammunition. */
public final class LegacyProjectile extends AbstractArrow {
    public LegacyProjectile(EntityType<? extends LegacyProjectile> type, Level level) { super(type, level); }

    @Override protected ItemStack getDefaultPickupItem() { return ItemStack.EMPTY; }

    @Override public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Ammunition", 10) && !tag.contains("item", 10)) {
            // Vanilla's entity fixer does not know our custom nested item field.
            var converted = (CompoundTag) DataFixers.getDataFixer().update(References.ITEM_STACK,
                    new Dynamic<>(NbtOps.INSTANCE, tag.getCompound("Ammunition")), 1631,
                    SharedConstants.getCurrentVersion().getDataVersion().getVersion()).getValue();
            setPickupItemStack(ItemStack.parse(registryAccess(), converted).orElseThrow(
                    () -> new IllegalStateException("Could not preserve legacy Modern Metals ammunition: " + converted)));
        }
    }
}

package zone.moddev.mc.modernmetals.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import net.minecraft.world.level.chunk.storage.RegionStorageInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zone.moddev.mc.modernmetals.migration.LegacyChunkStates;

/** ChunkDataEvent.Load runs after palette decoding, when these properties are already lost. */
@Mixin(ChunkSerializer.class)
abstract class ChunkSerializerMixin {
    @Inject(method = "read", at = @At("HEAD"))
    private static void modernmetals$oldWallProperties(ServerLevel level, PoiManager poi, RegionStorageInfo storage,
            ChunkPos pos, CompoundTag tag, CallbackInfoReturnable<ProtoChunk> callback) {
        LegacyChunkStates.prepare(tag);
    }
}

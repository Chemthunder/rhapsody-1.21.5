package net.chemthunder.rhapsody.mixin;

import net.chemthunder.rhapsody.impl.cca.world.RiftbreakWorldEventComponent;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SpawnHelper.class)
public abstract class SpawnHelperMixin {

    @Inject(method = "spawn", at = @At("HEAD"), cancellable = true)
    private static void cancelSpawns(ServerWorld world, WorldChunk chunk, SpawnHelper.Info info, List<SpawnGroup> spawnableGroups, CallbackInfo ci) {
        RiftbreakWorldEventComponent re = RiftbreakWorldEventComponent.KEY.get(world);

        if (re.isActive) {
            ci.cancel();
        }
    }
}

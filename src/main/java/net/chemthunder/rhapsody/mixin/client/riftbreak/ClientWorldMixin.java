package net.chemthunder.rhapsody.mixin.client.riftbreak;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.chemthunder.rhapsody.impl.cca.world.RiftbreakWorldEventComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {

    @ModifyReturnValue(method = "getSkyColor", at = @At("RETURN"))
    private int customskycolor(int original) {
        ClientWorld world = MinecraftClient.getInstance().world;

        if (world != null) {
            if (RiftbreakWorldEventComponent.KEY.get(world).isActive) {
                return 0x5e1a2f;
            }
        }

        return original;
    }
}

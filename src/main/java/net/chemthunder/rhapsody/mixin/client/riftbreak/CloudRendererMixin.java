package net.chemthunder.rhapsody.mixin.client.riftbreak;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.chemthunder.rhapsody.compat.RhapConfig;
import net.chemthunder.rhapsody.impl.cca.world.RiftbreakWorldEventComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.CloudRenderer;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CloudRenderer.class)
public abstract class CloudRendererMixin {


    @WrapOperation(
            method = "renderClouds",
            at = @At(value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V",
                    ordinal = 0
            )
    )
    private void cloudColors(float red, float green, float blue, float alpha, Operation<Void> original) {
        ClientWorld clientWorld = MinecraftClient.getInstance().world;

        if (clientWorld != null) {
            if (RiftbreakWorldEventComponent.KEY.get(clientWorld).isActive && RhapConfig.changeCloudColor) {
                original.call(0f, 0f, 0f, 1f);
            } else {
                original.call(red, green, blue, alpha);
            }
        }
    }
}

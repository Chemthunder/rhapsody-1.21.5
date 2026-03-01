package net.chemthunder.rhapsody.mixin.client.riftbreak;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.chemthunder.rhapsody.compat.RhapConfig;
import net.chemthunder.rhapsody.impl.Rhapsody;
import net.chemthunder.rhapsody.impl.cca.world.RiftbreakWorldEventComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.SkyRendering;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkyRendering.class)
public abstract class SkyRenderingMixin {

    @Unique
    private static final Identifier RECOGNITION_SUN = Rhapsody.id("textures/environment/recognition.png");

    @WrapOperation(method = "renderSun", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getCelestial(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer textureswap(Identifier texture, Operation<RenderLayer> original) {
        ClientWorld clientWorld = MinecraftClient.getInstance().world;

        if (clientWorld != null && RiftbreakWorldEventComponent.KEY.get(clientWorld).isActive) {
            return original.call(RECOGNITION_SUN);
        }

        return original.call(texture);
    }

    @Inject(method = "renderMoon", at = @At("HEAD"), cancellable = true)
    private void disableMoon(int phase, float alpha, VertexConsumerProvider vertexConsumers, MatrixStack matrices, CallbackInfo ci) {
        ClientWorld clientWorld = MinecraftClient.getInstance().world;

        if (clientWorld != null && RiftbreakWorldEventComponent.KEY.get(clientWorld).isActive) {
            ci.cancel();
        }
    }

    @WrapOperation(method = "renderStars", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V", ordinal = 0))
    private void stars(float red, float green, float blue, float alpha, Operation<Void> original) {
        ClientWorld clientWorld = MinecraftClient.getInstance().world;

        if (clientWorld != null) {
            if (RhapConfig.changeStarColor) {
                if (RiftbreakWorldEventComponent.KEY.get(clientWorld).isActive) {
                    original.call(255f, 0f, 0f, alpha);
                } else {
                    original.call(red, green, blue, alpha);
                }
            }
        }
    }
}
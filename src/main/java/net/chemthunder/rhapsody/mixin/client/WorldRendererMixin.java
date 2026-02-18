package net.chemthunder.rhapsody.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.chemthunder.rhapsody.impl.cca.world.RiftbreakWorldEventComponent;
import net.chemthunder.rhapsody.impl.index.RhapsodyItems;
import net.chemthunder.rhapsody.impl.item.HyacinthItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.SynchronousResourceReloader;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin implements SynchronousResourceReloader, AutoCloseable {
    @Unique
    private float opacity = 0.9f;


    @Inject(method = "renderTargetBlockOutline", at = @At("HEAD"))
    private void customColors(Camera camera, VertexConsumerProvider.Immediate vertexConsumers, MatrixStack matrices, boolean translucent, CallbackInfo ci) {
        ClientWorld world = MinecraftClient.getInstance().world;

        if (world != null) {
            if (RiftbreakWorldEventComponent.KEY.get(world).isActive) {
                RenderSystem.setShaderColor(0.7803921568627451f, 0.09411764705882353f, 0.2549019607843137f, opacity);
            }
        }
    }

    @Inject(method = "renderEntity", at = @At("HEAD"))
    private void customColors(Entity entity, double cameraX, double cameraY, double cameraZ, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        ClientWorld world = MinecraftClient.getInstance().world;
        boolean isChem = false;

        if (world != null) {
            if (RiftbreakWorldEventComponent.KEY.get(world).isActive) {
                if (entity instanceof PlayerEntity player && player.getInventory().contains(stack -> stack.isOf(RhapsodyItems.HYACINTH))) {
                    isChem = true;
                }

                if (!isChem) {
                    RenderSystem.setShaderColor(0.7803921568627451f, 0.09411764705882353f, 0.2549019607843137f, opacity);
                } else {
                    RenderSystem.setShaderColor(1, 1, 1, 1);
                }
            }
        }
    }

    @Inject(method = "renderSky", at = @At("TAIL"))
    private void customColors(FrameGraphBuilder frameGraphBuilder, Camera camera, float tickProgress, Fog fog, CallbackInfo ci) {
        ClientWorld world = MinecraftClient.getInstance().world;

        if (world != null) {
            if (RiftbreakWorldEventComponent.KEY.get(world).isActive) {
                RenderSystem.setShaderColor(0.7803921568627451f, 0.09411764705882353f, 0.2549019607843137f, opacity);
            }
        }
    }
}

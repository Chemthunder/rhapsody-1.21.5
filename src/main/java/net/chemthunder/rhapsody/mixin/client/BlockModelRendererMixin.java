package net.chemthunder.rhapsody.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.chemthunder.rhapsody.impl.cca.world.RiftbreakWorldEventComponent;
import net.fabricmc.fabric.api.renderer.v1.render.FabricBlockModelRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockModelRenderer.class)
public abstract class BlockModelRendererMixin implements FabricBlockModelRenderer {

    @WrapOperation(method = "renderQuad", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/color/block/BlockColors;getColor(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/util/math/BlockPos;I)I"))
    private int color(BlockColors instance, BlockState state, BlockRenderView world, BlockPos pos, int tintIndex, Operation<Integer> original) {

        assert MinecraftClient.getInstance().world != null;
        if (RiftbreakWorldEventComponent.KEY.get(MinecraftClient.getInstance().world).isActive) {
            return ColorHelper.mix(original.call(instance, state, world, pos, tintIndex), 0xFFc71841);
        }

        return original.call(instance, state, world, pos, tintIndex);
    }
}

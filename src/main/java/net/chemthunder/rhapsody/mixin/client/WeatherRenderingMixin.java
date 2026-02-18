package net.chemthunder.rhapsody.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.chemthunder.rhapsody.impl.Rhapsody;
import net.chemthunder.rhapsody.impl.cca.world.RiftbreakWorldEventComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.WeatherRendering;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WeatherRendering.class)
public abstract class WeatherRenderingMixin {

    @Unique
    private static final Identifier ECLIPSE_RAIN_TEXTURE = Rhapsody.id("textures/misc/silly_rain.png");

    @WrapOperation(method = "renderPrecipitation(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/util/math/Vec3d;IFLjava/util/List;Ljava/util/List;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getWeather(Lnet/minecraft/util/Identifier;Z)Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer customRain(Identifier texture, boolean allMask, Operation<RenderLayer> original) {
        World world = MinecraftClient.getInstance().world;
        if (world != null && RiftbreakWorldEventComponent.KEY.get(world).isActive) {
            return original.call(ECLIPSE_RAIN_TEXTURE, true);
        }
        return original.call(texture, allMask);
    }
}

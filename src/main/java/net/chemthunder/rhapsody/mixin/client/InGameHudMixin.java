package net.chemthunder.rhapsody.mixin.client;

import net.chemthunder.rhapsody.impl.Rhapsody;
import net.chemthunder.rhapsody.impl.cca.entity.PlayerFlashComponent;
import net.chemthunder.rhapsody.impl.cca.entity.SecondaryFlashComponent;
import net.chemthunder.rhapsody.impl.cca.world.RiftbreakWorldEventComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import net.minecraft.world.SpawnHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow
    protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Unique
    private static final Identifier WHITE_FLASH = Rhapsody.id("textures/misc/flash.png");
    @Unique
    private static final Identifier SILLY_VIGNETTE = Rhapsody.id("textures/misc/vignette_rift.png");
    @Unique
    private static final Identifier SILLY_FLASH = Rhapsody.id("textures/misc/rift_flash.png");

    @Inject(method = "renderMiscOverlays", at = @At("TAIL"))
    private void extraOverlays(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity player) {

            int ticks = PlayerFlashComponent.KEY.get(player).flashTicks;
            if (ticks > 0) {
                float opacity = ticks > 50 ? 1f : ticks / 50.0f;
                this.renderOverlay(context, WHITE_FLASH, opacity);
            }

            int twoTicks = SecondaryFlashComponent.KEY.get(player).flashTicks;
            if (twoTicks > 0) {
                float opacity = twoTicks > 50 ? 1f : twoTicks / 50.0f;
                this.renderOverlay(context, SILLY_FLASH, opacity);
            }


            if (RiftbreakWorldEventComponent.KEY.get(player.getWorld()).isActive) {
                this.renderOverlay(context, SILLY_VIGNETTE, 0.3f);
            }
        }
    }
}
package net.chemthunder.rhapsody.mixin.nitrogen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = HandledScreen.class, priority = 1001)
public abstract class HandledScreenMixin<T extends ScreenHandler> extends Screen implements ScreenHandlerProvider<T> {
    @Shadow
    @Nullable
    protected Slot focusedSlot;

    @Shadow @Final
    protected T handler;

    @Shadow protected abstract List<Text> getTooltipFromItem(ItemStack stack);

    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "drawMouseoverTooltip", at = @At("HEAD"), cancellable = true)
    private void ordnance$addBeltTooltipForItem(DrawContext context, int x, int y, CallbackInfo ci) {
        net.chemthunder.rhapsody.ported.NitrogenContext nitrogenContext = new net.chemthunder.rhapsody.ported.NitrogenContext(context.getScaledWindowWidth(), context.getScaledWindowHeight(), context.getMatrices(), context, ((DrawContextAccessor) context).getVertexConsumers());
        if (handler.getCursorStack().isEmpty() && this.focusedSlot != null && this.focusedSlot.hasStack()) {
            ItemStack itemStack = this.focusedSlot.getStack();
            if (itemStack.getItem() instanceof net.chemthunder.rhapsody.ported.api.ColorableItem) {
                ci.cancel();
                nitrogenContext.drawTooltip(this.textRenderer, this.getTooltipFromItem(itemStack), itemStack.getTooltipData(), x, y, itemStack);
            }
        }
    }
}
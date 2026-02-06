package net.chemthunder.rhapsody.mixin;

import net.chemthunder.rhapsody.impl.index.RhapsodyItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"))
    private void replaceGildedThing(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        PlayerEntity player = context.getPlayer();

        if (player != null) {
            ItemStack stack = player.getMainHandStack();
            ItemStack offStack = player.getOffHandStack();
            BlockPos pos = context.getBlockPos();
            BlockState state = context.getWorld().getBlockState(pos);
            BlockPos belowPos = pos.down();
            BlockState belowState = context.getWorld().getBlockState(belowPos);

            if (stack.isOf(Items.NETHERITE_SWORD) && offStack.isOf(Items.QUARTZ)) {
                if (state.isOf(Blocks.WATER_CAULDRON) && belowState.isOf(Blocks.CAMPFIRE)) {
                    stack.decrement(1);
                    offStack.decrement(1);

                    player.giveItemStack(new ItemStack(RhapsodyItems.HYACINTH));

                    player.playSoundToPlayer(SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1, 1);
                }
            }
        }
    }
}

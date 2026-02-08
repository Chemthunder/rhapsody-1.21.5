package net.chemthunder.rhapsody.mixin;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

//    @Inject(method = "useOnBlock", at = @At("HEAD"))
//    private void replaceGildedThing(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
//        PlayerEntity player = context.getPlayer();
//
//        if (player != null) {
//            ItemStack stack = player.getMainHandStack();
//            ItemStack offStack = player.getOffHandStack();
//            BlockPos pos = context.getBlockPos();
//            BlockState state = context.getWorld().getBlockState(pos);
//            BlockPos belowPos = pos.down();
//            BlockState belowState = context.getWorld().getBlockState(belowPos);
//
//            if (stack.isOf(Items.NETHERITE_SWORD) && offStack.isOf(Items.QUARTZ)) {
//                if (state.isOf(Blocks.WATER_CAULDRON) && belowState.isOf(Blocks.CAMPFIRE)) {
//                    stack.decrement(1);
//                    offStack.decrement(1);
//
//                    player.giveItemStack(new ItemStack(RhapsodyItems.HYACINTH));
//
//                    player.playSoundToPlayer(SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1, 1);
//                }
//            }
//        }
//    }
}

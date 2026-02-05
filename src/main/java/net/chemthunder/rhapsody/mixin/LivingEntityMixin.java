package net.chemthunder.rhapsody.mixin;

import net.chemthunder.rhapsody.impl.index.RhapsodyItems;
import net.chemthunder.rhapsody.impl.index.RhapsodySounds;
import net.chemthunder.rhapsody.impl.item.HyacinthItem;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tryAttack", at = @At("HEAD"), cancellable = true)
    private void exaltisripoff(ServerWorld world, Entity target, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living = (LivingEntity) (Object) this;

        if (target instanceof PlayerEntity player) {
            if (player.getStackInHand(player.getActiveHand()).getItem() instanceof HyacinthItem && player.isUsingItem()) {
                player.getItemCooldownManager().set(RhapsodyItems.HYACINTH.getDefaultStack(), 200);
                living.playSound(RhapsodySounds.HYACINTH_SLASH);

                living.setVelocity(player.getPos().subtract(living.getPos()).multiply(1));
                living.addVelocity(0, 0.5f, 0);
                living.velocityModified = true;

                cir.cancel();
            }
        }
    }
}

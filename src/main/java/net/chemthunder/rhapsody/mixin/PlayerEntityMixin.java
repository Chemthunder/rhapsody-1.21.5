package net.chemthunder.rhapsody.mixin;

import net.chemthunder.rhapsody.impl.cca.entity.PlayerFlashComponent;
import net.chemthunder.rhapsody.impl.index.RhapsodyItems;
import net.chemthunder.rhapsody.impl.index.RhapsodySounds;
import net.chemthunder.rhapsody.impl.index.data.RhapsodyDamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void hyacinthParry(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        Entity test = source.getAttacker();

        if (test instanceof LivingEntity living) {
            if (living != null) {
                if (player.getStackInHand(player.getActiveHand()).isOf(RhapsodyItems.HYACINTH) && player.isUsingItem()) {
                    if (!player.getItemCooldownManager().isCoolingDown(RhapsodyItems.HYACINTH.getDefaultStack())) {
                        int cooldown = 600;
                        int multiplier = 4;


                        cooldown = 20;

                        PlayerFlashComponent flash = PlayerFlashComponent.KEY.get(player);
                        flash.flashTicks = 15;
                        flash.sync();

                        player.getItemCooldownManager().set(RhapsodyItems.HYACINTH.getDefaultStack(), cooldown);
                        player.playSoundToPlayer(RhapsodySounds.HYACINTH_SLASH, SoundCategory.PLAYERS, 1, 1);
                        living.damage(world, living.getDamageSources().create(RhapsodyDamageTypes.BACKLASH), (amount / multiplier));
                        player.damage(world, living.getDamageSources().create(RhapsodyDamageTypes.BACKLASH), (amount / multiplier));

                        if (living instanceof PlayerEntity victim) {
                            victim.sendMessage(Text.translatable("text.hyacinth.backlash").formatted(Formatting.ITALIC).withColor(0xFF941957), true);
                            victim.playSoundToPlayer(RhapsodySounds.HYACINTH_SLASH, SoundCategory.PLAYERS, 1, 1);
                            player.sendMessage(Text.translatable("text.hyacinth.backlash").formatted(Formatting.ITALIC).withColor(0xFF941957), true);

                            PlayerFlashComponent flasher = PlayerFlashComponent.KEY.get(victim);
                            flasher.flashTicks = 15;
                            flasher.sync();
                        }

                        living.setVelocity(player.getPos().subtract(living.getPos()).multiply(-amount / 16));
                        living.addVelocity(0, 0.4f, 0);
                        living.velocityModified = true;
                        cir.setReturnValue(false);
                    }
                }
            }
        }
    }
}

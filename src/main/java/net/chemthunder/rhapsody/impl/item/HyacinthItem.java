package net.chemthunder.rhapsody.impl.item;

import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.acoyt.acornlib.api.items.CustomHitParticleItem;
import net.acoyt.acornlib.api.items.CustomKillSourceItem;
import net.acoyt.acornlib.api.items.KillEffectItem;
import net.acoyt.acornlib.api.util.ParticleUtils;
import net.acoyt.acornlib.impl.client.particle.SweepParticleEffect;
import net.chemthunder.rhapsody.impl.cca.entity.PlayerFlashComponent;
import net.chemthunder.rhapsody.impl.index.RhapsodySounds;
import net.chemthunder.rhapsody.impl.index.data.RhapsodyDamageTypes;
import net.chemthunder.rhapsody.ported.api.ColorableItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class HyacinthItem extends Item implements CustomHitParticleItem, KillEffectItem, ColorableItem, CustomKillSourceItem {
    public int startColor(ItemStack itemStack) {return 0xFF08060a;}
    public int endColor(ItemStack itemStack) {return 0xFF342b38;}
    public int backgroundColor(ItemStack itemStack) {return 0xF008060a;}

    public HyacinthItem(Settings settings) {
        super(settings);
    }

    public static final SweepParticleEffect[] EFFECTS = new SweepParticleEffect[]{
            new SweepParticleEffect(0x2b1f2e, 0x161018),
            new SweepParticleEffect(0x16111a, 0x0f0c12)
    };

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 8.5F, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -2.9F, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    public void spawnHitParticles(PlayerEntity player, Entity entity) {
        ParticleUtils.spawnSweepParticles(EFFECTS[player.getRandom().nextInt(EFFECTS.length)], player);
    }

    public boolean canMine(ItemStack stack, BlockState state, World world, BlockPos pos, LivingEntity user) {
        return !user.isInCreativeMode();
    }

    public void killEntity(World world, ItemStack stack, LivingEntity user, LivingEntity victim) {
      //  user.playSound(FmtSounds.EPITHET_EXECUTE, 1, 1);
        BlockPos pos = victim.getBlockPos();

        Box area = new Box(pos).expand(100);
        List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, area, entity -> true);

        for (LivingEntity entity : entities) {
            if (entity instanceof PlayerEntity player) {
                PlayerFlashComponent component = PlayerFlashComponent.KEY.get(player);

                component.flashTicks = 40;
                component.sync();

                player.playSoundToPlayer(RhapsodySounds.HYACINTH_EXECUTE, SoundCategory.PLAYERS, 1, 1);

                if (player instanceof ScreenShaker shaker) {
                    shaker.addScreenShake(1.5f, 20);
                }


            }
        }

        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.END_ROD,
                    victim.getX() + 0.5f,
                    victim.getY() + 1.0f,
                    victim.getZ() + 0.5f,
                    40,
                    0,
                    0,
                    0,
                    0.5f
            );

            LightningEntity bolt = new LightningEntity(EntityType.LIGHTNING_BOLT, serverWorld);
            bolt.setPos(victim.getX() + 0.5f, victim.getY(), victim.getZ() + 0.5f);

            serverWorld.spawnEntity(bolt);
        }
    }

    public DamageSource getKillSource(LivingEntity livingEntity) {
        return livingEntity.getDamageSources().create(RhapsodyDamageTypes.EMBRACE);
    }
}

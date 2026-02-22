package net.chemthunder.rhapsody.impl.item;

import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.acoyt.acornlib.api.items.CustomHitParticleItem;
import net.acoyt.acornlib.api.items.CustomHitSoundItem;
import net.acoyt.acornlib.api.items.CustomKillSourceItem;
import net.acoyt.acornlib.api.items.KillEffectItem;
import net.acoyt.acornlib.api.util.ParticleUtils;
import net.acoyt.acornlib.impl.client.particle.SweepParticleEffect;
import net.chemthunder.lux.api.LuxFlashRenderer;
import net.chemthunder.lux.impl.util.Easing;
import net.chemthunder.rhapsody.impl.cca.entity.PlayerFlashComponent;
import net.chemthunder.rhapsody.impl.cca.entity.SecondaryFlashComponent;
import net.chemthunder.rhapsody.impl.cca.world.RiftbreakWorldEventComponent;
import net.chemthunder.rhapsody.impl.index.RhapsodyDataComponents;
import net.chemthunder.rhapsody.impl.index.RhapsodyParticles;
import net.chemthunder.rhapsody.impl.index.RhapsodySounds;
import net.chemthunder.rhapsody.impl.index.data.RhapsodyDamageTypes;
import net.chemthunder.rhapsody.ported.api.ColorableItem;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
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
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class HyacinthItem extends Item implements CustomHitParticleItem, KillEffectItem, ColorableItem, CustomKillSourceItem, CustomHitSoundItem {
    public int startColor(ItemStack itemStack) {
        return itemStack.getOrDefault(RhapsodyDataComponents.IS_FRAGMENTED, false) ?  0xFF63001c : 0xFF08060a;
    }

    public int endColor(ItemStack itemStack) {
        return itemStack.getOrDefault(RhapsodyDataComponents.IS_FRAGMENTED, false) ? 0xFFd1003b : 0xFF342b38;
    }

    public int backgroundColor(ItemStack itemStack) {
        return itemStack.getOrDefault(RhapsodyDataComponents.IS_FRAGMENTED, false) ? 0xFF26050e : 0xF008060a;
    }

    public Text getName(ItemStack stack) {
        return super.getName(stack).copy().withColor(stack.getOrDefault(RhapsodyDataComponents.IS_FRAGMENTED, false) ? 0xFFd1003b : 0x2b1f2e);
    }

    public HyacinthItem(Settings settings) {
        super(settings
                .component(RhapsodyDataComponents.EMBRACED_SOULS, List.of())
                .component(RhapsodyDataComponents.IS_FRAGMENTED, false));
    }

    public static final SweepParticleEffect[] EFFECTS = new SweepParticleEffect[]{
            new SweepParticleEffect(0x2b1f2e, 0x161018),
            new SweepParticleEffect(0x16111a, 0x0f0c12)
    };

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 8.0F, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
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
        List<String> comp = stack.getOrDefault(RhapsodyDataComponents.EMBRACED_SOULS, List.of());
        List<String> souls = new ArrayList<>(comp);
        var dubios = RhapsodyDataComponents.IS_FRAGMENTED;
        BlockPos pos = victim.getBlockPos();

        if (souls.size() == 3 || stack.getOrDefault(dubios, false) == true) {
            victim.setVelocity(0, 0, 0);

            Box area = new Box(pos).expand(100);
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, area, entity -> true);

            for (LivingEntity entity : entities) {
                if (entity instanceof PlayerEntity player) {
                    if (player instanceof ScreenShaker shaker) {
//                        PlayerFlashComponent component = PlayerFlashComponent.KEY.get(player);
//
//                        component.flashTicks = 40;
//                        component.sync();

                        LuxFlashRenderer.sendFlash(player, 0xffffff, Easing.linear, 40);

                        player.playSoundToPlayer(RhapsodySounds.HYACINTH_EXECUTE, SoundCategory.PLAYERS, 1, 1);
                        player.playSoundToPlayer(SoundEvents.ENTITY_ALLAY_DEATH, SoundCategory.PLAYERS, 1, 0.1f);
                        player.playSoundToPlayer(SoundEvents.ENTITY_WARDEN_DEATH, SoundCategory.PLAYERS, 1, 5f);
                        player.playSoundToPlayer(SoundEvents.ENTITY_HORSE_DEATH, SoundCategory.PLAYERS, 1, 0.1f);
                        player.playSoundToPlayer(SoundEvents.ENTITY_MULE_DEATH, SoundCategory.PLAYERS, 1, 0.1f);
                        player.playSoundToPlayer(SoundEvents.ENTITY_RABBIT_DEATH, SoundCategory.PLAYERS, 1, 0.1f);
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

                serverWorld.spawnParticles(RhapsodyParticles.REFLECTION,
                        victim.getX() + 0.5f,
                        victim.getY() + 1.0f,
                        victim.getZ() + 0.5f,
                        40,
                        0,
                        0,
                        0,
                        0.5f
                );

                serverWorld.spawnParticles(RhapsodyParticles.ABSTRACTION,
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

                for (ServerPlayerEntity serverPlayer : serverWorld.getPlayers()) {
                    String entityName = null;
                    boolean shouldShowMessage = false;
                    if (victim instanceof LivingEntity) {
                        entityName = victim.getType().getTranslationKey();
                    }

                    if (victim instanceof PlayerEntity player) {
                        entityName = player.getNameForScoreboard();
                        shouldShowMessage = true;
                    }

                    if (shouldShowMessage) {
                        serverPlayer.sendMessage(Text.translatable(entityName).append(Text.literal(" had their fate set still")), false);
                    }
                }
            }
        }

        if (stack.getOrDefault(dubios, false) == false) {
            if (souls.size() < 3) {
                if (victim instanceof PlayerEntity player) {
                    souls.add(player.getNameForScoreboard());
                } else {
                    souls.add(victim.getType().getTranslationKey().trim());
                }
                stack.set(RhapsodyDataComponents.EMBRACED_SOULS, souls);
                user.playSound(SoundEvents.ENTITY_ALLAY_AMBIENT_WITH_ITEM);
            } else {
                souls.clear();
                stack.set(RhapsodyDataComponents.EMBRACED_SOULS, souls);
            }
        }
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        List<String> comp = new ArrayList<>(stack.getOrDefault(RhapsodyDataComponents.EMBRACED_SOULS, List.of()));

        if (stack.getOrDefault(RhapsodyDataComponents.IS_FRAGMENTED, false) == false) {
            textConsumer.accept(Text.translatable("lore.hyacinth").formatted(Formatting.ITALIC).withColor(0xFF941957));

            if (!comp.isEmpty()) {
                for (String value : comp) {
                    textConsumer.accept(Text.translatable(value.trim()).formatted(Formatting.ITALIC).withColor(0xFF75415b));
                }
            } else {
                textConsumer.accept(Text.literal("Empty.").formatted(Formatting.ITALIC).withColor(0xFF75415b));
            }
        } else {
            textConsumer.accept(Text.translatable("lore.hyacinth.fragmented.1").formatted(Formatting.ITALIC).formatted(Formatting.BOLD).withColor(0xFF9c002a));
            textConsumer.accept(Text.translatable("lore.hyacinth.fragmented.2").formatted(Formatting.ITALIC).formatted(Formatting.BOLD).withColor(0xFF9c002a));
            textConsumer.accept(Text.translatable("lore.hyacinth.fragmented.3").formatted(Formatting.ITALIC).formatted(Formatting.BOLD).withColor(0xFF9c002a));
        }
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }

    public DamageSource getKillSource(LivingEntity livingEntity) {
        ItemStack stack = livingEntity.getStackInArm(livingEntity.getMainArm());
        return stack.getOrDefault(RhapsodyDataComponents.IS_FRAGMENTED, false) == true ? RhapsodyDamageTypes.embrace(livingEntity) : livingEntity.getDamageSources().create(RhapsodyDamageTypes.TORN);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return super.use(world, user, hand);
    }

    public void playHitSound(PlayerEntity player, Entity target) {
        player.playSound(SoundEvents.ENTITY_ALLAY_HURT, 1.0F, (float) (1.0F + player.getRandom().nextGaussian() / 10f) * player.getAttackCooldownProgress(0.5F));
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity livingEntity = context.getPlayer();
        RiftbreakWorldEventComponent we = RiftbreakWorldEventComponent.KEY.get(context.getWorld());

        if (livingEntity != null) {
            ItemStack stack = livingEntity.getOffHandStack();
            List<String> comp = new ArrayList<>(stack.getOrDefault(RhapsodyDataComponents.EMBRACED_SOULS, List.of()));
            if (context.getWorld() instanceof ServerWorld serverWorld) {
                if (livingEntity.getOffHandStack().isOf(this)) {
                    if (livingEntity.isSneaking()) {
                        stack.set(RhapsodyDataComponents.EMBRACED_SOULS, comp);

                        if (!we.isActive) {
                            we.isActive = true;
                            we.sync();

                            stack.set(RhapsodyDataComponents.IS_FRAGMENTED, true);
                            comp.clear();

                            serverWorld.setTimeOfDay(18000);

                            for (ServerPlayerEntity serverPlayer : serverWorld.getPlayers()) {
                                if (serverPlayer instanceof ScreenShaker shaker) {
                                    shaker.addScreenShake(1, 70);

//                                    SecondaryFlashComponent flashComponent = SecondaryFlashComponent.KEY.get(serverPlayer);
//
//                                    flashComponent.flashTicks = 60;
//                                    flashComponent.sync();

                                    LuxFlashRenderer.sendFlash(serverPlayer, 0xff004c,   Easing.easeInOutExpo,130);

                                    serverPlayer.sendMessage(Text.translatable("riftbreak.activate").withColor(0xFFfc036b), true);

                                    serverPlayer.playSoundToPlayer(RhapsodySounds.EVENT_BOOM, SoundCategory.MASTER, 1, 1);
                                    serverPlayer.playSoundToPlayer(RhapsodySounds.EVENT_CRASH, SoundCategory.MASTER, 1, 1);
                                    serverPlayer.playSoundToPlayer(RhapsodySounds.EVENT_GEO, SoundCategory.MASTER, 1, 1);
                                    serverPlayer.playSoundToPlayer(RhapsodySounds.EVENT_SHATTER, SoundCategory.MASTER, 1, 1);
                                }
                            }
                        } else {
                            we.isActive = false;
                            we.sync();

                            stack.set(RhapsodyDataComponents.IS_FRAGMENTED, false);

                            for (ServerPlayerEntity serverPlayer : serverWorld.getPlayers()) {
//                                PlayerFlashComponent flashComponent = PlayerFlashComponent.KEY.get(serverPlayer);
//
//                                flashComponent.flashTicks = 40;
//                                flashComponent.sync();
                                LuxFlashRenderer.sendFlash(serverPlayer, 0xffffff);

                                serverPlayer.playSoundToPlayer(SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.MASTER, 1, 1);

                                if (serverPlayer instanceof ScreenShaker shaker) {
                                    shaker.addScreenShake(1, 45);
                                }
                            }
                        }
                    }
                }
            }
        }

        return super.useOnBlock(context);
    }

    // itembar -------
    public boolean hasGlint(ItemStack stack) {
        return false;
    }

    public boolean isItemBarVisible(ItemStack stack) {
        List<String> comp = new ArrayList<>(stack.getOrDefault(RhapsodyDataComponents.EMBRACED_SOULS, List.of()));
        return !comp.isEmpty();
    }

    public int getItemBarStep(ItemStack stack) {
        List<String> comp = new ArrayList<>(stack.getOrDefault(RhapsodyDataComponents.EMBRACED_SOULS, List.of()));

        return Math.round((float) comp.size() / 3 * 13);
    }

    public int getItemBarColor(ItemStack stack) {
        return 0xFF941957;
    }
}
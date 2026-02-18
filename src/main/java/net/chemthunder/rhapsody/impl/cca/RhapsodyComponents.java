package net.chemthunder.rhapsody.impl.cca;

import net.chemthunder.rhapsody.impl.cca.entity.PlayerFlashComponent;
import net.chemthunder.rhapsody.impl.cca.entity.SecondaryFlashComponent;
import net.chemthunder.rhapsody.impl.cca.world.RiftbreakWorldEventComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public class RhapsodyComponents implements EntityComponentInitializer, WorldComponentInitializer {

    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, PlayerFlashComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(PlayerFlashComponent::new);
        registry.beginRegistration(PlayerEntity.class, SecondaryFlashComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(SecondaryFlashComponent::new);
    }

    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(RiftbreakWorldEventComponent.KEY, RiftbreakWorldEventComponent::new);
    }
}

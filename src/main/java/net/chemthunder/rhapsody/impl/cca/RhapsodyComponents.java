package net.chemthunder.rhapsody.impl.cca;

import net.chemthunder.rhapsody.impl.cca.entity.PlayerFlashComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class RhapsodyComponents implements EntityComponentInitializer {

    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, PlayerFlashComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(PlayerFlashComponent::new);
    }
}

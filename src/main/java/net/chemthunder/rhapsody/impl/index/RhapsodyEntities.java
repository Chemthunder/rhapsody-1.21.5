package net.chemthunder.rhapsody.impl.index;

import net.chemthunder.rhapsody.impl.Rhapsody;
import net.chemthunder.rhapsody.impl.entity.KatzeRitualEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public interface RhapsodyEntities {
    EntityType<KatzeRitualEntity> KATZE_RITUAL = create(
            "katze_ritual",
            EntityType.Builder.create(
                    KatzeRitualEntity::new,
                    SpawnGroup.MISC
            ).dimensions(1.0f, 1.0f)
    );

    private static <T extends Entity> EntityType<T> create(String name, EntityType.Builder<T> builder) {
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Rhapsody.id(name));
        return Registry.register(Registries.ENTITY_TYPE, key.getValue(), builder.build(key));
    }

    static void init() {
        //
    }

    static void clientInit() {
        EntityRendererRegistry.register(KATZE_RITUAL, EmptyEntityRenderer::new);
    }
}

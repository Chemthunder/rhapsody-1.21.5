package net.chemthunder.rhapsody.impl.index;

import net.chemthunder.rhapsody.impl.Rhapsody;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface RhapsodyParticles {
    SimpleParticleType REFLECTION = FabricParticleTypes.simple(true);
    SimpleParticleType ABSTRACTION = FabricParticleTypes.simple(true);

    private static void create(String name, ParticleType<?> particle) {
        Registry.register(Registries.PARTICLE_TYPE, Rhapsody.id(name), particle);
    }

    static void init() {
        create("reflection", REFLECTION);
        create("abstraction", ABSTRACTION);
    }

    static void clientInit() {
        ParticleFactoryRegistry.getInstance().register(REFLECTION, EndRodParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ABSTRACTION, EndRodParticle.Factory::new);
    }
}

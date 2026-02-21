package net.chemthunder.rhapsody.impl.index.data;

import net.chemthunder.rhapsody.impl.Rhapsody;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface RhapsodyDamageTypes {
    RegistryKey<DamageType> EMBRACE = of("embrace");
    RegistryKey<DamageType> BACKLASH = of("backlash");
    RegistryKey<DamageType> TORN = of("torn");

    private static RegistryKey<DamageType> of(String id) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Rhapsody.id(id));
    }

    static DamageSource embrace(LivingEntity entity) {
        return entity.getDamageSources().create(EMBRACE);
    }

    static void bootstrap(Registerable<DamageType> registerable) {
        registerable.register(EMBRACE, new DamageType("embrace", DamageScaling.NEVER, 0.1F));
        registerable.register(BACKLASH, new DamageType("backlash", DamageScaling.NEVER, 0.1F));
        registerable.register(TORN, new DamageType("torn", DamageScaling.NEVER, 0.1F));
    }

    static DamageSource create(World world, RegistryKey<DamageType> key, @Nullable Entity source, @Nullable Entity attacker) {
        return new DamageSource(world.getRegistryManager().getOrThrow(RegistryKeys.DAMAGE_TYPE).getOrThrow(key), source, attacker);
    }

    static DamageSource create(World world, RegistryKey<DamageType> key, @Nullable Entity attacker) {
        return new DamageSource(world.getRegistryManager().getOrThrow(RegistryKeys.DAMAGE_TYPE).getOrThrow(key), attacker);
    }

    static DamageSource create(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().getOrThrow(RegistryKeys.DAMAGE_TYPE).getOrThrow(key));
    }
}

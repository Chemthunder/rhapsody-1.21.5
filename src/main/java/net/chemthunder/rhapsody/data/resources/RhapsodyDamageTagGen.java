package net.chemthunder.rhapsody.data.resources;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

import static net.chemthunder.rhapsody.impl.index.data.RhapsodyDamageTypes.BACKLASH;

public class RhapsodyDamageTagGen extends FabricTagProvider<DamageType> {
    public RhapsodyDamageTagGen(FabricDataOutput output, RegistryKey<? extends Registry<DamageType>> registryKey, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registryKey, registriesFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(DamageTypeTags.NO_KNOCKBACK)
                .add(BACKLASH);

        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN)
                .add(BACKLASH);
    }
}

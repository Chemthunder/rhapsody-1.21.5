package net.chemthunder.rhapsody.data.other;

import net.chemthunder.rhapsody.impl.Rhapsody;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RhapsodyDynamicRegistryGen extends FabricDynamicRegistryProvider {
    public RhapsodyDynamicRegistryGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup registries, FabricDynamicRegistryProvider.Entries entries) {
        entries.addAll(registries.getOrThrow(RegistryKeys.DAMAGE_TYPE));
    }

    public String getName() {
        return Rhapsody.MOD_ID + "_dynamic";
    }
}

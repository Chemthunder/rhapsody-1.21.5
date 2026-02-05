package net.chemthunder.rhapsody.data;

import net.chemthunder.rhapsody.data.other.RhapsodyDynamicRegistryGen;
import net.chemthunder.rhapsody.data.resources.RhapsodyLangGen;
import net.chemthunder.rhapsody.impl.index.data.RhapsodyDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class RhapsodyDataGenerator implements DataGeneratorEntrypoint {

	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(RhapsodyLangGen::new);

        pack.addProvider(RhapsodyDynamicRegistryGen::new);
	}

    public void buildRegistry(RegistryBuilder builder) {
        builder.addRegistry(RegistryKeys.DAMAGE_TYPE, RhapsodyDamageTypes::bootstrap);
    }
}

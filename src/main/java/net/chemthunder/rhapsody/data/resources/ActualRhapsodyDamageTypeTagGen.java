package net.chemthunder.rhapsody.data.resources;

import net.minecraft.data.DataOutput;
import net.minecraft.data.tag.vanilla.VanillaDamageTypeTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

import static net.chemthunder.rhapsody.impl.index.data.RhapsodyDamageTypes.BACKLASH;

public class ActualRhapsodyDamageTypeTagGen extends VanillaDamageTypeTagProvider {
    public ActualRhapsodyDamageTypeTagGen(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        this.getOrCreateTagBuilder(DamageTypeTags.NO_KNOCKBACK)
                .add(BACKLASH);

        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN)
                .add(BACKLASH);

        super.configure(registries);
    }
}

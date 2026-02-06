package net.chemthunder.rhapsody.data.resources;

import net.chemthunder.rhapsody.impl.index.data.RhapsodyDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import static net.chemthunder.rhapsody.impl.index.RhapsodyItems.HYACINTH;

public class RhapsodyLangGen extends FabricLanguageProvider {
    public RhapsodyLangGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        // items
        translationBuilder.add(HYACINTH, "Hyacinth");

        // dmg
        registerDamageType(translationBuilder,
                RhapsodyDamageTypes.EMBRACE,
                "%1$s's soul was condemned",
                "%1$s's soul was condemned",
                "%1$s's soul was condemned"
        );

        registerDamageType(translationBuilder,
                RhapsodyDamageTypes.BACKLASH,
                "%1$s's heart was ripped open",
                "%1$s's heart was ripped open whilst fighting %2$s wielding %3$s",
                "%1$s's heart was ripped open whilst fighting %2$s"
        );

        registerDamageType(translationBuilder,
                RhapsodyDamageTypes.TORN,
                "%1$s was slashed open",
                "%1$s was slashed open by %2$s wielding %3$s",
                "%1$s was slashed open by %2$s"
        );

        // misc
        translationBuilder.add("lore.hyacinth", "The God Butcher.");
        translationBuilder.add("text.hyacinth.backlash", "Your soul feels torn open.");
    }

    public void registerDamageType(TranslationBuilder builder, RegistryKey<DamageType> registryKey, String normal, String item, String player) {
        String key = "death.attack." + registryKey.getValue().getPath();
        builder.add(key, normal);
        builder.add(key + ".item", item);
        builder.add(key + ".player", player);
    }
}

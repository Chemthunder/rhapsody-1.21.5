package net.chemthunder.rhapsody.impl.index;

import com.mojang.serialization.Codec;
import net.chemthunder.rhapsody.impl.Rhapsody;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.List;
import java.util.function.UnaryOperator;

public interface RhapsodyDataComponents {
    ComponentType<List<String>> EMBRACED_SOULS = create("embraced_souls", builder -> builder.codec(Codec.STRING.listOf()));

    static <T> ComponentType<T> create(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Rhapsody.id(name), (builderOperator.apply(ComponentType.builder()).build()));
    }

    static void init() {
        // Components are Registered Statically
    }
}

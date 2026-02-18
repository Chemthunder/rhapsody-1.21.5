package net.chemthunder.rhapsody.impl.index;

import net.chemthunder.rhapsody.impl.Rhapsody;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface RhapsodySounds {
    Map<SoundEvent, Identifier> SOUNDS = new LinkedHashMap<>();

    SoundEvent HYACINTH_EXECUTE = create("item.hyacinth_execute");
    SoundEvent HYACINTH_SLASH = create("item.hyacinth_slash");

    SoundEvent EVENT_BOOM = create("event.bigass_boom");
    SoundEvent EVENT_GEO = create("event.geo");
    SoundEvent EVENT_SHATTER = create("event.shatter");
    SoundEvent EVENT_CRASH = create("event.crash");

    private static SoundEvent create(String name) {
        SoundEvent soundEvent = SoundEvent.of(Rhapsody.id(name));
        SOUNDS.put(soundEvent, Rhapsody.id(name));
        return soundEvent;
    }

    static void init() {
        SOUNDS.keySet().forEach(soundEvent -> {
            Registry.register(Registries.SOUND_EVENT, SOUNDS.get(soundEvent), soundEvent);
        });
    }
}

package net.chemthunder.rhapsody.impl.cca.world;

import net.chemthunder.rhapsody.impl.Rhapsody;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class RiftbreakWorldEventComponent implements AutoSyncedComponent {
    public static final ComponentKey<RiftbreakWorldEventComponent> KEY = ComponentRegistry.getOrCreate(Rhapsody.id("riftbreak"), RiftbreakWorldEventComponent.class);
    private final World world;
    public boolean isActive = false;

    public RiftbreakWorldEventComponent(World world) {
        this.world = world;
    }

    public void sync() {
        KEY.sync(world);
    }

    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.isActive = nbtCompound.getBoolean("isActive", isActive);
    }

    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("isActive", isActive);
    }
}

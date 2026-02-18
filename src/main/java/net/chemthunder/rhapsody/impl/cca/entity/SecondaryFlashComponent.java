package net.chemthunder.rhapsody.impl.cca.entity;

import net.chemthunder.rhapsody.impl.Rhapsody;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;

public class SecondaryFlashComponent implements AutoSyncedComponent, ClientTickingComponent {
    public static final ComponentKey<SecondaryFlashComponent> KEY = ComponentRegistry.getOrCreate(Rhapsody.id("secondary"), SecondaryFlashComponent.class);
    private final PlayerEntity player;
    public int flashTicks = 0;

    public SecondaryFlashComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void clientTick() {
        if (flashTicks > 0) {
            flashTicks--;
            if (flashTicks == 0) {
                sync();
            }
        }
    }

    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.flashTicks = nbtCompound.getInt("flashTicks", 0);
    }

    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("flashTicks", flashTicks);
    }
}

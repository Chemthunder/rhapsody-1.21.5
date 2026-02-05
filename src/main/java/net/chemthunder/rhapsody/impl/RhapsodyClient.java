package net.chemthunder.rhapsody.impl;

import net.chemthunder.rhapsody.impl.index.RhapsodyParticles;
import net.fabricmc.api.ClientModInitializer;

public class RhapsodyClient implements ClientModInitializer {

    public void onInitializeClient() {
        RhapsodyParticles.clientInit();
    }
}

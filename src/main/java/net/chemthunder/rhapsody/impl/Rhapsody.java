package net.chemthunder.rhapsody.impl;

import net.chemthunder.rhapsody.impl.index.RhapsodyItems;
import net.chemthunder.rhapsody.impl.index.RhapsodySounds;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rhapsody implements ModInitializer {
	public static final String MOD_ID = "rhapsody";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



	public void onInitialize() {
        RhapsodyItems.init();
        RhapsodySounds.init();

		LOGGER.info("Hello Fabric world!");
	}

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
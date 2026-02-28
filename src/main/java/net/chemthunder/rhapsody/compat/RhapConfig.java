package net.chemthunder.rhapsody.compat;

import eu.midnightdust.lib.config.MidnightConfig;

public class RhapConfig extends MidnightConfig {
    private static final String config = "config";

    @Entry(category = config)
    public static float overlayOpacity = 0.3f;
}

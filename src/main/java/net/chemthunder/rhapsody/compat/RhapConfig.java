package net.chemthunder.rhapsody.compat;

import eu.midnightdust.lib.config.MidnightConfig;

public class RhapConfig extends MidnightConfig {
    private static final String config = "config";

    @Entry(category = config)
    public static float overlayOpacity = 0.3f;

    @Entry(category = config)
    public static boolean changeSkyColor = true;

    @Entry(category = config)
    public static boolean changeStarColor = true;

    @Entry(category = config)
    public static boolean changeFogColor = true;

    @Entry(category = config)
    public static boolean changeCloudColor = true;
}

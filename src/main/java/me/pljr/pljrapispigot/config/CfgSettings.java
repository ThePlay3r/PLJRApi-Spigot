package me.pljr.pljrapispigot.config;

import me.pljr.pljrapispigot.managers.ConfigManager;

public class CfgSettings {
    public static boolean SOUNDS;
    public static int TELEPORT_DELAY;
    public static boolean VAULT;
    public static boolean HOLOGRAMS;
    public static boolean PLACEHOLDERS;
    public static boolean DISABLED_MESSAGES;

    public static void load(ConfigManager config){
        CfgSettings.SOUNDS = config.getBoolean("settings.sounds");
        CfgSettings.TELEPORT_DELAY = config.getInt("settings.teleport-delay");
        CfgSettings.VAULT = config.getBoolean("settings.vault");
        CfgSettings.HOLOGRAMS = config.getBoolean("settings.holograms");
        CfgSettings.PLACEHOLDERS = config.getBoolean("settings.placeholders");
        CfgSettings.DISABLED_MESSAGES = config.getBoolean("settings.disabled-messages");
    }
}

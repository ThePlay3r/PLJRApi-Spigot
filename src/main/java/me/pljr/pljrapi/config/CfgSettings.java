package me.pljr.pljrapi.config;

import me.pljr.pljrapi.managers.ConfigManager;

public class CfgSettings {
    public static boolean sounds;
    public static int teleportDelay;
    public static boolean vault;
    public static boolean holograms;
    public static boolean placeholders;
    public static boolean disabledMessages;

    public static void load(ConfigManager config){
        CfgSettings.sounds = config.getBoolean("settings.sounds");
        CfgSettings.teleportDelay = config.getInt("settings.teleport-delay");
        CfgSettings.vault = config.getBoolean("settings.vault");
        CfgSettings.holograms = config.getBoolean("settings.holograms");
        CfgSettings.placeholders = config.getBoolean("settings.placeholders");
        CfgSettings.disabledMessages = config.getBoolean("settings.disabled-messages");
    }
}

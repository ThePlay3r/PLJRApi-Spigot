package me.pljr.pljrapi.config;

import me.pljr.pljrapi.PLJRApi;
import me.pljr.pljrapi.managers.ConfigManager;

public class CfgSettings {
    private static final ConfigManager config = PLJRApi.getConfigManager();

    public static boolean sounds;
    public static boolean bungee;

    public static void load(){
        CfgSettings.sounds = config.getBoolean("settings.sounds");
        CfgSettings.bungee = config.getBoolean("settings.bungee");
    }
}

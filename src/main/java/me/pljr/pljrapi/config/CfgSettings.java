package me.pljr.pljrapi.config;

import me.pljr.pljrapi.managers.ConfigManager;

public class CfgSettings {
    public static boolean sounds;

    public static void load(ConfigManager config){
        CfgSettings.sounds = config.getBoolean("settings.sounds");
    }
}

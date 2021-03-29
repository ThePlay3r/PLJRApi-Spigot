package me.pljr.pljrapispigot.config;

import lombok.Getter;
import me.pljr.pljrapispigot.managers.ConfigManager;

@Getter
public class Settings {
    private final static String PATH = "settings";

    private final boolean sounds;
    private final int teleportDelay;
    private final boolean vault;
    private final boolean holograms;
    private final boolean placeholders;
    private final boolean disabledMessages;

    public Settings(ConfigManager config){
        sounds = config.getBoolean(PATH+".sounds");
        teleportDelay = config.getInt(PATH+".teleport-delay");
        vault = config.getBoolean(PATH+".vault");
        holograms = config.getBoolean(PATH+".holograms");
        placeholders = config.getBoolean(PATH+".placeholders");
        disabledMessages = config.getBoolean(PATH+".disabled-messages");
    }
}

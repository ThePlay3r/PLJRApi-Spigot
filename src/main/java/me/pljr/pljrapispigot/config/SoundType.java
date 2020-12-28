package me.pljr.pljrapispigot.config;

import me.pljr.pljrapispigot.managers.ConfigManager;

import java.util.HashMap;

public enum SoundType {
    COMMAND_FAIL,
    TELEPORT_TICK,
    TELEPORT_FAIL,
    TELEPORT_TP;

    private static HashMap<SoundType, String> soundType;

    public static void load(ConfigManager config){
        soundType = new HashMap<>();
        for (SoundType soundType : values()){
            SoundType.soundType.put(soundType, config.getString("sounds." + soundType.toString()));
        }
    }

    public String get(){
        return soundType.get(this);
    }
}

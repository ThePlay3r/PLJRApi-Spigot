package me.pljr.pljrapi.config;

import me.pljr.pljrapi.PLJRApi;
import me.pljr.pljrapi.enums.Sounds;
import me.pljr.pljrapi.managers.ConfigManager;
import org.bukkit.Sound;

import java.util.HashMap;

public class CfgSounds {
    private static final ConfigManager config = PLJRApi.getConfigManager();

    public static HashMap<Sounds, Sound> sounds = new HashMap<>();

    public static void load(){
        for (Sounds sounds : Sounds.values()){
            CfgSounds.sounds.put(sounds, config.getSound("sounds." + sounds.toString()));
        }
    }
}

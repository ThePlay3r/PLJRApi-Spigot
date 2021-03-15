package me.pljr.pljrapispigot.config;

import com.cryptomorin.xseries.XSound;
import me.pljr.pljrapispigot.builders.SoundBuilder;
import me.pljr.pljrapispigot.managers.ConfigManager;
import me.pljr.pljrapispigot.objects.PLJRSound;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public enum SoundType {
    COMMAND_FAIL(new SoundBuilder(XSound.ENTITY_VILLAGER_NO, 10, 1).create()),
    TELEPORT_TICK(new SoundBuilder(XSound.UI_BUTTON_CLICK, 10, 1).create()),
    TELEPORT_FAIL(new SoundBuilder(XSound.ENTITY_VILLAGER_HURT, 10, 1).create()),
    TELEPORT_TP(new SoundBuilder(XSound.ENTITY_CAT_PURR, 10, 1).create());

    private static HashMap<SoundType, PLJRSound> soundType;
    private final PLJRSound defaultValue;

    SoundType(PLJRSound defaultValue){
        this.defaultValue = defaultValue;
    }

    public static void load(ConfigManager config){
        FileConfiguration fileConfig = config.getConfig();
        soundType = new HashMap<>();
        for (SoundType soundType : values()){
            if (!fileConfig.isSet(soundType.toString())){
                config.setPLJRSound(soundType.toString(), soundType.defaultValue);
            }else{
                SoundType.soundType.put(soundType, config.getPLJRSound(soundType.toString()));
            }
        }
        config.save();
    }

    public PLJRSound get(){
        return soundType.getOrDefault(this, defaultValue);
    }
}

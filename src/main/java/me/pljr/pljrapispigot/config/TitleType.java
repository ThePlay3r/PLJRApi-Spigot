package me.pljr.pljrapispigot.config;

import me.pljr.pljrapispigot.builders.TitleBuilder;
import me.pljr.pljrapispigot.managers.ConfigManager;
import me.pljr.pljrapispigot.objects.PLJRTitle;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public enum TitleType {
    TELEPORT_TICKING(new TitleBuilder("§b§lTeleportation", "§7{time}§8s", 20, 40, 20).create()),
    TELEPORT_FAIL(new TitleBuilder("§b§lMove detected", "§cTeleportation canceled..", 20, 60, 20).create()),
    TELEPORT_TELEPORT(new TitleBuilder("§bTeleportation", "§7Teleporting..", 20, 60, 20).create());

    private static HashMap<TitleType, PLJRTitle> titleType;
    private final PLJRTitle defaultValue;

    TitleType(PLJRTitle defaultValue){
        this.defaultValue = defaultValue;
    }

    public static void load(ConfigManager config){
        FileConfiguration fileConfig = config.getConfig();
        titleType = new HashMap<>();
        for (TitleType title : values()){
            if (!fileConfig.isSet(title.toString())){
                config.setPLJRTitle(title.toString(), title.defaultValue);
            }else{
                TitleType.titleType.put(title, config.getPLJRTitle(title.toString()));
            }
        }
        config.save();
    }

    public PLJRTitle get(){
        return titleType.getOrDefault(this, defaultValue);
    }
}

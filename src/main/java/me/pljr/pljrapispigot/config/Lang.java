package me.pljr.pljrapispigot.config;

import me.pljr.pljrapispigot.managers.ConfigManager;
import me.pljr.pljrapispigot.objects.PLJRTitle;

import java.util.HashMap;

public enum Lang {
    NO_PERM,
    NO_NUMBER,
    NO_MATERIAL,
    OFFLINE,
    TIME_FORMAT_DAYS,
    TIME_FORMAT_HOURS,
    TIME_FORMAT_MINUTES,
    COMMAND_RESPONSE_PLAYER,
    COMMAND_RESPONSE_CONSOLE;
    public static PLJRTitle TELEPORT_TITLE_TICK;
    public static PLJRTitle TELEPORT_TITLE_FAIL;
    public static PLJRTitle TELEPORT_TITLE_TP;

    private static HashMap<Lang, String> lang;

    public static void load(ConfigManager config){
        TELEPORT_TITLE_TICK = config.getPLJRTitle("teleport-title.ticking");
        TELEPORT_TITLE_FAIL = config.getPLJRTitle("teleport-title.fail");
        TELEPORT_TITLE_TP = config.getPLJRTitle("teleport-title.teleport");
        lang = new HashMap<>();
        for (Lang lang : values()){
            Lang.lang.put(lang, config.getString("lang." + lang.toString()));
        }
    }

    public String get(){
        return lang.get(this);
    }
}

package me.pljr.pljrapispigot.config;

import me.pljr.pljrapispigot.managers.ConfigManager;
import me.pljr.pljrapispigot.objects.PLJRTitle;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public enum Lang {
    NO_PERM("&c&l! &8» &fYou don't have enough permissions to do this!"),
    NO_NUMBER("&c&l! &8» &b{arg} &fis not a number!"),
    NO_MATERIAL("&c&l! &8» &b{material} &fis not a material!"),
    NO_BALANCE("&c&l! &8» &fYou don't have enough balance! &7(${amount})"),
    OFFLINE("&c&l! &8» &e{name} &fis not on the server!"),
    TIME_FORMAT_DAYS("%d d, %02d h. %02d m. %02d s."),
    TIME_FORMAT_HOURS("%02d h. %02d m. %02d s."),
    TIME_FORMAT_MINUTES("00 h. %02d m. %02d s."),
    COMMAND_RESPONSE_PLAYER("&c&l! &8» &fYou can't use this command!"),
    COMMAND_RESPONSE_CONSOLE("&c&l! &8» &fThis command is for in-game use only!");

    private static HashMap<Lang, String> lang;
    private final String defaultValue;

    Lang(String defaultValue){
        this.defaultValue = defaultValue;
    }

    public static void load(ConfigManager config) {
        FileConfiguration fileConfig = config.getConfig();
        lang = new HashMap<>();
        for (Lang lang : values()){
            if (!fileConfig.isSet(lang.toString())){
                fileConfig.set(lang.toString(), lang.getDefault());
            }
            Lang.lang.put(lang, config.getString(lang.toString()));
        }
        config.save();
    }

    public String get(){
        return lang.get(this);
    }

    public String getDefault(){
        return this.defaultValue;
    }
}

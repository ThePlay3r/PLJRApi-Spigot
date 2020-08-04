package me.pljr.pljrapi.config;

import me.pljr.pljrapi.PLJRApi;
import me.pljr.pljrapi.enums.Lang;
import me.pljr.pljrapi.managers.ConfigManager;

import java.util.HashMap;

public class CfgLang {
    private final static ConfigManager config = PLJRApi.getConfigManager();

    public static HashMap<Lang, String> lang = new HashMap<>();

    public static void load(){
        CfgSettings.bungee = config.getBoolean("settings.bungee");
        for (Lang lang : Lang.values()){
            CfgLang.lang.put(lang, config.getString("lang." + lang.toString()));
        }
    }
}

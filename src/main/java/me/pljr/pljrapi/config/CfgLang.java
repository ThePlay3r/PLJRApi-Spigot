package me.pljr.pljrapi.config;

import me.pljr.pljrapi.PLJRApi;
import me.pljr.pljrapi.enums.Lang;
import me.pljr.pljrapi.managers.ConfigManager;
import me.pljr.pljrapi.objects.PLJRTitle;

import java.util.HashMap;

public class CfgLang {
    private final static ConfigManager config = PLJRApi.getConfigManager();

    public static HashMap<Lang, String> lang = new HashMap<>();
    public static PLJRTitle teleportTitleTick;
    public static PLJRTitle teleportTitleFail;
    public static PLJRTitle teleportTitleTp;

    public static void load(){
        for (Lang lang : Lang.values()){
            CfgLang.lang.put(lang, config.getString("lang." + lang.toString()));
        }
        CfgLang.teleportTitleTick = config.getPLJRTitle("teleport-title.ticking");
        CfgLang.teleportTitleFail = config.getPLJRTitle("teleport-title.fail");
        CfgLang.teleportTitleTp = config.getPLJRTitle("teleport-title.teleport");
    }
}

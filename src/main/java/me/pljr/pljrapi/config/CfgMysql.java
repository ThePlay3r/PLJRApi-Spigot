package me.pljr.pljrapi.config;

import me.pljr.pljrapi.PLJRApi;
import me.pljr.pljrapi.managers.ConfigManager;

public class CfgMysql {
    private final static ConfigManager config = PLJRApi.getConfigManager();

    public static String host, port, database, username, password;

    public static void load(){
        CfgMysql.host = config.getString("mysql.host");
        CfgMysql.port = config.getString("mysql.port");
        CfgMysql.database = config.getString("mysql.database");
        CfgMysql.username = config.getString("mysql.username");
        CfgMysql.password = config.getString("mysql.password");
    }
}

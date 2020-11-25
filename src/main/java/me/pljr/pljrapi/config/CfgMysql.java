package me.pljr.pljrapi.config;

import me.pljr.pljrapi.managers.ConfigManager;

public class CfgMysql {
    public static String host, port, database, username, password;

    public static int maximumPoolSize;
    public static int maximumIdle;
    public static int maxLifetime;
    public static int connectionTimeout;
    public static String cachePrepStmts, prepStmtCacheSize, prepStmtCacheSqlLimit;

    public static void load(ConfigManager config){
        CfgMysql.host = config.getString("mysql.host");
        CfgMysql.port = config.getString("mysql.port");
        CfgMysql.database = config.getString("mysql.database");
        CfgMysql.username = config.getString("mysql.username");
        CfgMysql.password = config.getString("mysql.password");

        maximumPoolSize = config.getInt("mysql-settings.maximumPoolSize");
        maximumIdle = config.getInt("mysql-settings.maximumIdle");
        maxLifetime = config.getInt("mysql-settings.maxLifetime");
        connectionTimeout = config.getInt("mysql-settings.connectionTimeout");
        cachePrepStmts = config.getString("mysql-settings.cachePrepStmts");
        prepStmtCacheSize = config.getString("mysql-settings.prepStmtCacheSize");
        prepStmtCacheSqlLimit = config.getString("mysql-settings.prepStmtCacheSqlLimit");
    }
}

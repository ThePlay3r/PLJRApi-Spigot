package me.pljr.pljrapispigot.config;

import me.pljr.pljrapispigot.managers.ConfigManager;

public class CfgMysql {
    public static String HOST, PORT, DATABASE, USERNAME, PASSWORD;

    public static int MAXIMUM_POOL_SIZE;
    public static int MAXIMUM_IDLE;
    public static int MAX_LIFE_TIME;
    public static int CONNECTION_TIMEOUT;
    public static String CACHE_PREP_STMTS, PREP_STMT_CACHE_SIZE, PREP_STMT_CACHE_SQL_LIMIT;

    public static void load(ConfigManager config){
        CfgMysql.HOST = config.getString("mysql.host");
        CfgMysql.PORT = config.getString("mysql.port");
        CfgMysql.DATABASE = config.getString("mysql.database");
        CfgMysql.USERNAME = config.getString("mysql.username");
        CfgMysql.PASSWORD = config.getString("mysql.password");

        MAXIMUM_POOL_SIZE = config.getInt("mysql-settings.maximumPoolSize");
        MAXIMUM_IDLE = config.getInt("mysql-settings.maximumIdle");
        MAX_LIFE_TIME = config.getInt("mysql-settings.maxLifetime");
        CONNECTION_TIMEOUT = config.getInt("mysql-settings.connectionTimeout");
        CACHE_PREP_STMTS = config.getString("mysql-settings.cachePrepStmts");
        PREP_STMT_CACHE_SIZE = config.getString("mysql-settings.prepStmtCacheSize");
        PREP_STMT_CACHE_SQL_LIMIT = config.getString("mysql-settings.prepStmtCacheSqlLimit");
    }
}

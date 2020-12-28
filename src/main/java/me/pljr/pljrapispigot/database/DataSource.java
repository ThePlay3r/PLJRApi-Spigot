package me.pljr.pljrapispigot.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.pljr.pljrapispigot.PLJRApiSpigot;
import me.pljr.pljrapispigot.config.CfgMysql;
import me.pljr.pljrapispigot.managers.ConfigManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
    private final String host, port, database, username, password;

    private final HikariConfig config = new HikariConfig();
    private HikariDataSource ds;

    public DataSource(String host, String port, String database, String username, String password){
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public static DataSource getFromConfig(ConfigManager config){
        if (config.getBoolean("mysql.enabled")){
            DataSource dataSource = new DataSource(
                    config.getString("mysql.host"),
                    config.getString("mysql.port"),
                    config.getString("mysql.database"),
                    config.getString("mysql.username"),
                    config.getString("mysql.password")
            );
            dataSource.initPool();
            return dataSource;
        }
        return PLJRApiSpigot.getDataSource();
    }

    public void initPool() {
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + Integer.parseInt(port) + "/" + database + "?characterEncoding=UTF-8&autoReconnect=true&useSSL=false");
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", CfgMysql.CACHE_PREP_STMTS);
        config.addDataSourceProperty("prepStmtCacheSize", CfgMysql.PREP_STMT_CACHE_SIZE);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", CfgMysql.PREP_STMT_CACHE_SQL_LIMIT);
        config.setMaximumPoolSize(CfgMysql.MAXIMUM_POOL_SIZE);
        config.setMinimumIdle(CfgMysql.MAXIMUM_IDLE);
        config.setMaxLifetime(CfgMysql.MAX_LIFE_TIME);
        config.setConnectionTimeout(CfgMysql.CONNECTION_TIMEOUT);
        ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public void close(Connection conn, PreparedStatement ps, ResultSet res) {
        if (conn != null) try {
            conn.close();
        } catch (SQLException ignored) {
        }
        if (ps != null) try {
            ps.close();
        } catch (SQLException ignored) {
        }
        if (res != null) try {
            res.close();
        } catch (SQLException ignored) {
        }
    }
}

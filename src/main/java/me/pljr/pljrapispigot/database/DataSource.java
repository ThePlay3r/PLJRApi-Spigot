package me.pljr.pljrapispigot.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.pljr.pljrapispigot.managers.ConfigManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
    private final String host;
    private final String port;
    private final String database;
    private final String username;
    private final String password;
    private final int maximumPoolSize;
    private final int maximumIdle;
    private final int maxLifetime;
    private final int connectionTimeout;
    private final boolean cachePrepStmts;
    private final int prepStmtCacheSize;
    private final int prepStmtCacheSqlLimit;

    private final HikariConfig config = new HikariConfig();
    private HikariDataSource ds;

    public DataSource(ConfigManager config) {
        host = config.getString("mysql.host");
        port = config.getString("mysql.port");
        database = config.getString("mysql.database");
        username = config.getString("mysql.username");
        password = config.getString("mysql.password");
        maximumPoolSize = config.getInt("mysql-settings.maximumPoolSize");
        maximumIdle = config.getInt("mysql-settings.maximumIdle");
        maxLifetime = config.getInt("mysql-settings.maxLifetime");
        connectionTimeout = config.getInt("mysql-settings.connectionTimeout");
        cachePrepStmts = config.getBoolean("mysql-settings.cachePrepStmts");
        prepStmtCacheSize = config.getInt("mysql-settings.prepStmtCacheSize");
        prepStmtCacheSqlLimit = config.getInt("mysql-settings.prepStmtCacheSqlLimit");
    }

    public void initPool(String name) {
        if (this.ds.isRunning()) return;
        this.config.setJdbcUrl("jdbc:mysql://" + this.host + ":" + Integer.parseInt(this.port) + "/" + this.database + "?characterEncoding=UTF-8&autoReconnect=true&useSSL=false");
        this.config.setUsername(this.username);
        this.config.setPassword(this.password);
        this.config.addDataSourceProperty("cachePrepStmts", this.cachePrepStmts);
        this.config.addDataSourceProperty("prepStmtCacheSize", this.prepStmtCacheSize);
        this.config.addDataSourceProperty("prepStmtCacheSqlLimit", this.prepStmtCacheSqlLimit);
        this.config.setMaximumPoolSize(this.maximumPoolSize);
        this.config.setMinimumIdle(this.maximumIdle);
        this.config.setMaxLifetime(this.maxLifetime);
        this.config.setConnectionTimeout(this.connectionTimeout);
        this.config.setPoolName(name);
        this.ds = new HikariDataSource(this.config);
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

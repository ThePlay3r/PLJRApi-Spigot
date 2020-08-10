package me.pljr.pljrapi.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.pljr.pljrapi.PLJRApi;
import me.pljr.pljrapi.managers.ConfigManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
    private String host, port, database, username, password;

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
            return new DataSource(
                    config.getString("mysql.host"),
                    config.getString("mysql.port"),
                    config.getString("mysql.database"),
                    config.getString("mysql.username"),
                    config.getString("mysql.password")
            );
        }
        return PLJRApi.getDataSource();
    }

    public void initPool() {
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + Integer.parseInt(port) + "/" + database + "?characterEncoding=UTF-8&autoReconnect=true&useSSL=false");
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(16);
        config.setMinimumIdle(2);
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

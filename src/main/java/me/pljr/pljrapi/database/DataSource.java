package me.pljr.pljrapi.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.pljr.pljrapi.config.CfgMysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
    private String host, port, database, username, password;

    private final HikariConfig config = new HikariConfig();
    private HikariDataSource ds;

    public void load() {
        host = CfgMysql.host;
        port = CfgMysql.port;
        database = CfgMysql.database;
        username = CfgMysql.username;
        password = CfgMysql.password;
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

    public DataSource() {}

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

package me.pljr.pljrapispigot.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import me.pljr.pljrapispigot.config.ConfigManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

private const val CFG_PATH = "mysql"
private const val CFG_PATH_SETTINGS = "mysql-settings"

class DataSource(config: ConfigManager) {
    val hikariCfg = HikariConfig()
    var ds: HikariDataSource? = null

    val host = config.getString("$CFG_PATH.host")
    val port = config.getString("$CFG_PATH.port")
    val database = config.getString("$CFG_PATH.database")
    val username = config.getString("$CFG_PATH.username")
    val password = config.getString("$CFG_PATH.password")

    val maximumPoolSize = config.getInt("$CFG_PATH_SETTINGS.maximumPoolSize")
    val maximumIdle = config.getInt("$CFG_PATH_SETTINGS.maximumIdle")
    val maxLifeTime = config.getLong("$CFG_PATH_SETTINGS.maxLifeTime")
    val connectionTimeout = config.getLong("$CFG_PATH_SETTINGS.connectionTimeout")
    val cachePrepStmts = config.getBoolean("$CFG_PATH_SETTINGS.cachePrepStmts")
    val prepStmtCacheSize = config.getInt("$CFG_PATH_SETTINGS.prepStmtCacheSize")
    val prepStmtCacheSqlLimit = config.getInt("$CFG_PATH_SETTINGS.prepStmtCacheSqlLimit")

    fun initPool(name: String) {
        if (this.ds != null && this.ds!!.isRunning) return
        this.hikariCfg.jdbcUrl = "jdbc:mysql://$host:$port/$database?characterEncoding=UTF-8&autoReconnect=true&useSSL=false"
        this.hikariCfg.username = this.username
        this.hikariCfg.password = this.password
        this.hikariCfg.addDataSourceProperty("cachePrepStmts", this.cachePrepStmts)
        this.hikariCfg.addDataSourceProperty("prepStmtCacheSize", this.prepStmtCacheSize)
        this.hikariCfg.addDataSourceProperty("prepStmtCacheSqlLimit", this.prepStmtCacheSqlLimit)
        this.hikariCfg.maximumPoolSize = this.maximumPoolSize
        this.hikariCfg.minimumIdle = this.maximumIdle
        this.hikariCfg.maxLifetime = this.maxLifeTime
        this.hikariCfg.connectionTimeout = this.connectionTimeout
        this.hikariCfg.poolName = name
        this.ds = HikariDataSource(this.hikariCfg)
    }

    fun updateStatement(sql: String) {
        val statement = getConnection().prepareStatement(sql)
        statement.executeUpdate()
        close(statement)
    }

    fun getConnection(): Connection {
        return ds!!.connection
    }

    fun close(ps: PreparedStatement?) =  close(getConnection(), ps)

    fun close(conn: Connection?, ps: PreparedStatement?) = close(conn, ps, null)

    fun close(conn: Connection?, ps: PreparedStatement?, res: ResultSet?) {
        if (conn != null) try {
            conn.close()
        } catch (ignored: SQLException) {
        }
        if (ps != null) try {
            ps.close()
        } catch (ignored: SQLException) {
        }
        if (res != null) try {
            res.close()
        } catch (ignored: SQLException) {
        }
    }
}
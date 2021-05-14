package me.pljr.pljrapispigot.player

import me.pljr.pljrapispigot.database.DataSource
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

private const val TABLE_NAMES = "pljrapi_playernames"

class PlayerQuery(val dataSource: DataSource, val plugin: JavaPlugin) {

    val playerNames = HashMap<UUID, String>()

    init {
        dataSource.updateStatement(
            "CREATE TABLE IF NOT EXISTS $TABLE_NAMES (" +
                "uuid char(36) NOT NULL PRIMARY KEY," +
                "name varchar(16) NOT NULL);")
        loadPlayerNames()
    }

    private fun loadPlayerNames() {
        val statement = dataSource.getConnection().prepareStatement("SELECT * FROM $TABLE_NAMES")
        val results = statement.executeQuery()
        while (results.next()){
            playerNames[UUID.fromString(results.getString("uuid"))] = results.getString("name")
        }
        dataSource.close(statement)
    }

    fun savePlayerName(uuid: UUID, name: String) {
        dataSource.updateStatement("REPLACE INTO $TABLE_NAMES VALUES ('$uuid','$name')")
        playerNames[uuid] = name
    }
}
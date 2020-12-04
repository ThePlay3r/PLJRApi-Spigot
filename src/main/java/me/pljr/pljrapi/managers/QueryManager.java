package me.pljr.pljrapi.managers;

import me.pljr.pljrapi.database.DataSource;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class QueryManager {
    private final DataSource dataSource;
    private final JavaPlugin plugin;

    private final HashMap<UUID, String> playerNames;

    public QueryManager(DataSource dataSource, JavaPlugin plugin){
        this.dataSource = dataSource;
        this.plugin = plugin;

        this.playerNames = new HashMap<>();

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS pljrapi_playernames (" +
                            "uuid char(36) NOT NULL PRIMARY KEY," +
                            "name varchar(16) NOT NULL);"
            );
            statement.executeUpdate();
            dataSource.close(connection, statement, null);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void loadPlayerNames(){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::loadPlayerNamesSync);
    }

    public void loadPlayerNamesSync(){
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM pljrapi_playernames"
            );
            ResultSet results = statement.executeQuery();
            while (results.next()){
                playerNames.put(UUID.fromString(results.getString("uuid")), results.getString("name"));
            }
            dataSource.close(connection, statement, results);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void savePlayerName(UUID playerId, String playerName){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> savePlayerNameSync(playerId, playerName));
    }

    public void savePlayerNameSync(UUID playerId, String playerName){
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "REPLACE INTO pljrapi_playernames VALUES (?,?)"
            );
            statement.setString(1, playerId.toString());
            statement.setString(2, playerName);
            statement.executeUpdate();
            dataSource.close(connection, statement, null);
            this.playerNames.put(playerId, playerName);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getPlayerName(UUID uuid){
        return playerNames.get(uuid);
    }
}

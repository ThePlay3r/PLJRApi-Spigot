package me.pljr.pljrapispigot.database;

import me.pljr.pljrapispigot.objects.Level;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LevelQuery {
    private final DataSource dataSource;
    private final JavaPlugin plugin;
    private final String tableName;

    public LevelQuery(DataSource dataSource, JavaPlugin plugin, String tableName){
        this.dataSource = dataSource;
        this.plugin = plugin;
        this.tableName = tableName;

        try {
            Connection levelsConnection = dataSource.getConnection();
            PreparedStatement levelsStatement = levelsConnection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + tableName + "_levels (" +
                            "uuid char(36) NOT NULL PRIMARY KEY," +
                            "level int NOT NULL," +
                            "xp int NOT NULL," +
                            "name varchar(255) NOT NULL," +
                            "next_cost int NOT NULL);"
            );
            System.out.println("[PLJRApi] Created Levels Table");
            levelsStatement.executeUpdate();
            dataSource.close(levelsConnection, levelsStatement, null);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Level loadPlayerSync(UUID uuid){
        try {
            Connection connectionLevels = dataSource.getConnection();
            PreparedStatement statementLevels = connectionLevels.prepareStatement(
                    "SELECT * FROM  " + tableName + "_levels WHERE uuid=?"
            );
            statementLevels.setString(1, uuid.toString());
            ResultSet resultsLevels = statementLevels.executeQuery();
            Level level = null;
            if (resultsLevels.next()){
                level = new Level(
                        resultsLevels.getInt("level"),
                        resultsLevels.getInt("xp"),
                        resultsLevels.getString("name"),
                        resultsLevels.getInt("next_cost"));
            }
            dataSource.close(connectionLevels, statementLevels, resultsLevels);
            return level;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void savePlayer(UUID uuid, Level level){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, ()->{
            try {
                Connection connectionLevels = dataSource.getConnection();
                PreparedStatement statementLevels = connectionLevels.prepareStatement(
                        "REPLACE INTO " + tableName + "_levels VALUES (?,?,?,?,?)"
                );
                statementLevels.setString(1, uuid.toString());
                statementLevels.setInt(2, level.getLevel());
                statementLevels.setInt(3, level.getXp());
                statementLevels.setString(4, level.getNameRaw());
                statementLevels.setInt(5, level.getNextCost());
                statementLevels.executeUpdate();
                dataSource.close(connectionLevels, statementLevels, null);
            }catch (SQLException e){
                e.printStackTrace();
            }
        });
    }
}

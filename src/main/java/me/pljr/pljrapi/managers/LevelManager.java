package me.pljr.pljrapi.managers;

import me.pljr.pljrapi.database.LevelQuery;
import me.pljr.pljrapi.objects.Level;
import me.pljr.pljrapi.utils.NumberUtil;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.UUID;

public class LevelManager {
    private final LevelQuery query;

    private final HashMap<UUID, Level> players;
    private final HashMap<Integer, Level> levels;

    private final String othersName;
    private final int othersCost;

    private final int rewardMinute;
    private final int rewardCustom;
    private final int rewardWin;

    private final String progressSymbol;
    private final String progressUnlockedColor;
    private final String progressLockedColor;
    private final String progressFormat;

    public LevelManager(LevelQuery query, ConfigManager config){
        this.query = query;
        players = new HashMap<>();
        levels = new HashMap<>();
        ConfigurationSection csLevels = config.getConfigurationSection("levels.levels");
        if (csLevels != null){
            for (String level : csLevels.getKeys(false)){
                String name = config.getString("levels.levels." + level + ".name");
                int cost = config.getInt("levels.levels." + level + ".rankup-cost");
                if (NumberUtil.isInt(level)){
                    int levelNumber = Integer.parseInt(level);
                    levels.put(levelNumber, new Level(levelNumber, 0, name, cost));
                    continue;
                }
                String[] range = level.split("-");
                if (range.length == 2){
                    String rangeFrom = range[0];
                    String rangeTo = range[1];
                    if (NumberUtil.isInt(rangeFrom) && NumberUtil.isInt(rangeTo)){
                        int rangeFromInt = Integer.parseInt(rangeFrom);
                        int rangeToInt = Integer.parseInt(rangeTo);
                        for (int i = rangeFromInt; i<=rangeToInt; i++){
                            levels.put(i, new Level(i, 0, name, cost));
                        }
                    }
                }
            }
        }
        othersName = config.getString("levels.levels.others.name");
        othersCost = config.getInt("levels.levels.others.rankup-cost");

        rewardMinute = config.getInt("levels.xp.per-minute");
        rewardCustom = config.getInt("levels.xp.custom");
        rewardWin = config.getInt("levels.xp.game-win");

        progressSymbol = config.getString("levels.progress-bar.symbol");
        progressUnlockedColor = config.getString("levels.progress-bar.unlocked-color");
        progressLockedColor = config.getString("levels.progress-bar.locked-color");
        progressFormat = config.getString("levels.progress-bar.format");
    }

    public void loadPlayer(UUID uuid){
        Level level = query.loadPlayerSync(uuid);
        if (level == null){
            level = getLevel(1);
        }
        level.setManager(this);
        players.put(uuid, level);
    }

    public void setPlayer(UUID uuid, Level level){
        players.put(uuid, level);
    }

    public Level getPlayer(UUID uuid){
        if (players.get(uuid) == null){
            loadPlayer(uuid);
        }
        return players.get(uuid);
    }

    public void savePlayer(UUID uuid){
        Level levelPlayer = getPlayer(uuid);
        if (levelPlayer == null) return;
        query.savePlayer(uuid, levelPlayer);
    }

    public void addPlayerXp(UUID uuid, int amount){
        Level playerLevel = getPlayer(uuid);
        int xp = playerLevel.getXp();
        int nextCost = playerLevel.getNextCost();
        xp+=amount;
        if (xp >= nextCost){
            players.put(uuid, getLevel(playerLevel.getLevel()+1));
            return;
        }
        playerLevel.setXp(xp);
        players.put(uuid, playerLevel);
    }

    public Level getLevel(int level){
        if (levels.containsKey(level)){
            Level lvl = levels.get(level);
            lvl.setManager(this);
            return lvl;
        }
        Level lvl = new Level(level, 0, othersName, othersCost);
        lvl.setManager(this);
        return lvl;
    }

    @Deprecated
    public String getProgress(Level level){
        return level.getProgress();
    }

    public int getRewardMinute() {
        return rewardMinute;
    }
    public int getRewardCustom() {
        return rewardCustom;
    }
    public int getRewardWin() {
        return rewardWin;
    }

    public String getProgressSymbol() {
        return progressSymbol;
    }

    public String getProgressUnlockedColor() {
        return progressUnlockedColor;
    }

    public String getProgressLockedColor() {
        return progressLockedColor;
    }

    public String getProgressFormat() {
        return progressFormat;
    }
}

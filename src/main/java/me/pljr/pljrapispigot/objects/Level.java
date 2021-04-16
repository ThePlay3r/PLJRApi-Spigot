package me.pljr.pljrapispigot.objects;

import me.pljr.pljrapispigot.managers.LevelManager;
import me.pljr.pljrapispigot.utils.FormatUtil;

public class Level {
    private final int level;
    private int xp;
    private final String name;
    private final int nextCost;
    private LevelManager manager;
    private String progress;

    public Level(Level level){
        this.level = level.getLevel();
        this.xp = level.getXp();
        this.name = level.getName();
        this.nextCost = level.getNextCost();
        this.manager = level.manager;
        this.progress = level.getProgress();
    }

    public Level(int level, int xp, String name, int nextCost){
        this.level = level;
        this.xp = xp;
        this.name = name;
        this.nextCost = nextCost;
    }

    public void setManager(LevelManager manager) {
        this.manager = manager;
        updateProgressBar();
    }

    private void updateProgressBar(){
        String symbol = manager.getProgressSymbol();
        String lockedColor = manager.getProgressLockedColor();
        String unlockedColor = manager.getProgressUnlockedColor();
        String progress = FormatUtil.getProgressBar(xp, nextCost, symbol, lockedColor, unlockedColor);
        this.progress = manager.getProgressFormat().replace("{progress}", progress);
    }

    public int getLevel() {
        return level;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getXp() {
        return xp;
    }

    public String getName() {
        return name.replace("{number}", level+"");
    }

    public String getNameRaw(){
        return name;
    }

    public int getNextCost() {
        return nextCost;
    }

    public LevelManager getManager() {
        return manager;
    }

    public String getProgress() {
        return progress;
    }
}

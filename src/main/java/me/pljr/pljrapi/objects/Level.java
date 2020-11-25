package me.pljr.pljrapi.objects;

import me.pljr.pljrapi.managers.LevelManager;
import me.pljr.pljrapi.utils.FormatUtil;

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
        float onePercent = nextCost / 100;
        float percent = xp / onePercent;
        int unlocked = Math.round(percent / 10);

        String symbol = manager.getProgressSymbol();
        StringBuilder progress = new StringBuilder();
        for (int i = 1; i<=10;i++){
            progress.append(symbol);
        }
        progress.insert(unlocked, manager.getProgressLockedColor());
        progress.insert(0, manager.getProgressUnlockedColor());
        this.progress = manager.getProgressFormat().replace("{progress}", FormatUtil.colorString(progress.toString()));
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

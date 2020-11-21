package me.pljr.pljrapi.objects;

import me.pljr.pljrapi.managers.LevelManager;

public class Level {
    private final int level;
    private int xp;
    private final String name;
    private final int nextCost;

    public Level(Level level){
        this.level = level.getLevel();
        this.xp = level.getXp();
        this.name = level.getName();
        this.nextCost = level.getNextCost();
    }

    public Level(int level, int xp, String name, int nextCost){
        this.level = level;
        this.xp = xp;
        this.name = name;
        this.nextCost = nextCost;
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
}

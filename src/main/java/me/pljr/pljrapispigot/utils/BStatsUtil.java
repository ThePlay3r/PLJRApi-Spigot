package me.pljr.pljrapispigot.utils;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class BStatsUtil {

    public static void addMetrics(JavaPlugin plugin, int id){
        new Metrics(plugin, id);
    }
}

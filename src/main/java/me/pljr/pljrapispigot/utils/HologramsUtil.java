package me.pljr.pljrapispigot.utils;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.pljr.pljrapispigot.PLJRApiSpigot;
import me.pljr.pljrapispigot.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class HologramsUtil {
    private final static JavaPlugin PLUGIN = PLJRApiSpigot.get();
    private final static Settings SETTINGS = PLJRApiSpigot.get().getSettings();

    /**
     * Creates a {@link Hologram} for specified time using HolographicDisplays API.
     *
     * @param location {@link Location}, where the Hologram should be created
     * @param text Lines of Hologram
     * @param ticks For how long the Hologram should last
     */
    public static void create(Location location, List<String> text, int ticks){
        if (!SETTINGS.isHolograms()){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "PLJRApi: Tried to create a HolographicDisplays Hologram while disabled in config!");
            return;
        }
        Hologram hologram = HologramsAPI.createHologram(PLUGIN, location);
        for (String line : text){
            hologram.appendTextLine(line);
        }
        Bukkit.getScheduler().runTaskLaterAsynchronously(PLUGIN, () -> {
            if (hologram != null && !hologram.isDeleted()){
                hologram.delete();
            }
        }, ticks);
    }
}

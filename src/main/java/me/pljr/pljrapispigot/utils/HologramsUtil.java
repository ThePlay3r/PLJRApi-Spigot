package me.pljr.pljrapispigot.utils;

import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.line.TextLine;
import me.pljr.pljrapispigot.PLJRApiSpigot;
import me.pljr.pljrapispigot.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public final class HologramsUtil {
    private final static Settings SETTINGS = PLJRApiSpigot.get().getSettings();
    private final static HologramPlugin HOLOGRAM_PLUGIN = JavaPlugin.getPlugin(HologramPlugin.class);

    /**
     * Creates a {@link Hologram} for specified time using HolographicDisplays API.
     *
     * @param location {@link Location}, where the Hologram should be created
     * @param text Lines of Hologram
     * @param ticks For how long the Hologram should last
     */
    public static void create(Location location, List<String> text, int ticks){
        if (!SETTINGS.isHolograms()){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "PLJRApi: Tried to create a Holograms Hologram while disabled in config!");
            return;
        }
        HologramManager hologramManager = HOLOGRAM_PLUGIN.getHologramManager();
        Hologram hologram = new Hologram(UUID.randomUUID().toString(), location);
        for (String line : text){
            hologram.addLine(new TextLine(hologram, line));
        }
        hologramManager.addActiveHologram(hologram);
        Bukkit.getScheduler().runTaskLaterAsynchronously(PLJRApiSpigot.get(), () -> hologramManager.deleteHologram(hologram), ticks);
    }
}

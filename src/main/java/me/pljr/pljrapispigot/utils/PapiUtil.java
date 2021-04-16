package me.pljr.pljrapispigot.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.pljr.pljrapispigot.PLJRApiSpigot;
import me.pljr.pljrapispigot.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class PapiUtil {
    private final static Settings SETTINGS = PLJRApiSpigot.get().getSettings();

    /**
     * Parses {@link PlaceholderAPI} placeholders in String.
     *
     * @param player {@link Player} by whom the placeholders should be parsed
     * @param input String that should be parsed
     * @return Input with parsed strings (If enabled in configuration, unparsed input otherwise)
     */
    public static String setPlaceholders(Player player, String input){
        if (SETTINGS.isPlaceholders()){
            return PlaceholderAPI.setPlaceholders(player, input);
        }
        if (SETTINGS.isDisabledMessages()){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "PLJRApi: Tried to parse PAPI Placeholders, while disabled in config!");
        }
        return input;
    }
}

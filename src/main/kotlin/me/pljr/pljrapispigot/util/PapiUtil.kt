package me.pljr.pljrapispigot.util

import me.clip.placeholderapi.PlaceholderAPI
import me.pljr.pljrapispigot.config.configuration.Settings
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player

/**
 * Parses [PlaceholderAPI] placeholders in String.
 *
 * @param player [Player] by whom the placeholders should be parsed
 * @param input String that should be parsed
 * @return Input with parsed strings (If enabled in configuration, unparsed input otherwise)
 */
fun setPlaceholders(player: Player, input: String): String {
    if (Settings.PLACEHOLDERS) {
        return PlaceholderAPI.setPlaceholders(player, input)
    }
    if (Settings.DISABLED_MESSAGES) {
        Bukkit.getConsoleSender()
            .sendMessage(ChatColor.RED.toString() + "PLJRApi: Tried to parse PAPI Placeholders, while disabled in config!")
    }
    return input
}
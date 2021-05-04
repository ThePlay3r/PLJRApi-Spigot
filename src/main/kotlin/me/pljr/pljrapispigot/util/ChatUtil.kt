package me.pljr.pljrapispigot.util

import me.pljr.pljrapispigot.PLJRApiSpigot
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

private val BUKKIT_AUDIENCES = PLJRApiSpigot.bukkitAudiences

/**
 * Sends a message with parsed [MiniMessage] tags and PAPI Placeholders to [OfflinePlayer], if online.
 *
 * @param player [OfflinePlayer] that we will try to send the message to
 * @param message String that will be send to player, if online
 */
fun sendMessage(player: OfflinePlayer, message: String) {
    if (player.isOnline) {
        sendMessage(player.player!!, message)
    }
}

/**
 * Sends a message with parsed PAPI Placeholders to [OfflinePlayer], if online.
 *
 * @param player [OfflinePlayer] that we will try to send the message to
 * @param message String that will be send to player, if online
 */
fun sendMessageClean(player: OfflinePlayer, message: String) {
    if (player.isOnline) {
        sendMessageClean(player.player!!, message)
    }
}

/**
 * Sends a message with parsed [MiniMessage] tags and PAPI Placeholders to [Player].
 *
 * @param player [Player] that will receive the message
 * @param message String that will be send to the player
 */
fun sendMessage(player: Player, message: String) = BUKKIT_AUDIENCES.player(player).sendMessage(Component.text(setPlaceholders(player, message)))

/**
 * Sends a message with parsed PAPI Placeholders to [Player].
 *
 * @param player [Player] that will receive the message
 * @param message String that will be send to the player
 */
fun sendMessageClean(player: Player, message: String) = player.sendMessage(setPlaceholders(player, message))

/**
 * Sends a message with parsed [MiniMessage] tags and PAPI Placeholders to [CommandSender].
 *
 * @param target [CommandSender] that will receive the message
 * @param message String that will be send to the target
 */
fun sendMessage(target: CommandSender, message: String){
    if (target is Player){
        sendMessage(player = target, message)
    } else {
        BUKKIT_AUDIENCES.sender(target).sendMessage(Component.text(message))
    }
}

/**
 * Sends a message with parsed PAPI Placeholders to [CommandSender].
 *
 * @param target [CommandSender] that will receive the message
 * @param message String that will be send to the target
 */
fun sendMessageClean(target: CommandSender, message: String) {
    if (target is Player) {
        sendMessageClean(player = target, message)
    } else {
        target.sendMessage(message)
    }
}

/**
 * Broadcasts a message with parsed [MiniMessage] tags and PAPI Placeholders to either Bukkit or Bungee.
 *
 * @param message String that will be send to all Players and ConsoleSender
 * @param perm Permission that is required to receive the message
 */
fun broadcastMessage(message: String, perm: String) {
    sendMessage(Bukkit.getConsoleSender(), message)
    Bukkit.getOnlinePlayers().forEach {
        if (it.hasPermission(perm) || perm == "") {
            sendMessage(it, message)
        }
    }
}

/**
 * Broadcasts a list of messages with parsed [MiniMessage] tags and PAPI Placeholders to either Bukkit or Bungee.
 *
 * @param messages ArrayList that will be send to all Players and ConsoleSender
 * @param perm Permission that is required to receive the messages
 */
fun broadcastMessages(messages: List<String>, perm: String) = messages.forEach { broadcastMessage(it, perm) }

/**
 * Broadcasts a message with parsed PAPI Placeholders to either Bukkit or Bungee.
 *
 * @param message String that will be send to all Players and ConsoleSender
 * @param perm Permission that is required to receive the message
 */
fun broadcastMessageClean(message: String, perm: String) {
    sendMessageClean(Bukkit.getConsoleSender(), message)
    Bukkit.getOnlinePlayers().forEach {
        if (it.hasPermission(perm) || perm == "") {
            sendMessageClean(it, message)
        }
    }
}

/**
 * Broadcasts a list of messages with parsed PAPI Placeholders to either Bukkit or Bungee.
 *
 * @param messages ArrayList that will be send to all Players and ConsoleSender
 * @param perm Permission that is required to receive the messages
 */
fun broadcastMessagesClean(messages: List<String>, perm: String) = messages.forEach { broadcastMessageClean(it, perm) }

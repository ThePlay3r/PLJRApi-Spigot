package me.pljr.pljrapispigot.title

import me.pljr.pljrapispigot.PLJRApiSpigot
import org.bukkit.Bukkit
import org.bukkit.entity.Player

private val BUKKIT_AUDIENCES = PLJRApiSpigot.bukkitAudiences

/**
 * Will send a [PLJRTitle] to [Player].
 *
 * @param player [Player] that should receive the title
 * @param title [PLJRTitle] that should be send to player
 */
fun sendTitle(player: Player, title: PLJRTitle) = BUKKIT_AUDIENCES.player(player).showTitle(title.toAdventureTitle())

/**
 * Will broadcast a [PLJRTitle] to all online players.
 *
 * @param title [PLJRTitle] that should be broadcasts
 */
fun broadcastTitle(title: PLJRTitle) = Bukkit.getOnlinePlayers().forEach { sendTitle(it, title) }

/**
 * Clears a Title of [Player].
 *
 * @param player [Player] that should get the Title cleared
 */
fun clearTitle(player: Player) {
    BUKKIT_AUDIENCES.player(player).clearTitle()
}
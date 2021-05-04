package me.pljr.pljrapispigot.scoreboard

import me.pljr.pljrapispigot.util.setPlaceholders
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * Sends [PLJRScoreboard] to [Player]
 *
 * @param player [Player] that should receive the scoreboard
 * @param scoreboard [PLJRScoreboard] that should be sent to player
 */
fun sendScoreBoard(player: Player, scoreboard: PLJRScoreboard) {
    val builder = ScoreboardBuilder(scoreboard)
    val linesWithPAPI: MutableList<String> = ArrayList()
    builder.lines.forEach { linesWithPAPI.add(setPlaceholders(player, it)) }
    builder.withLines(linesWithPAPI)
    player.scoreboard = builder.create().toScoreboard()
}

/**
 * Broadcasts [PLJRScoreboard] to all players.
 *
 * @param scoreboard [PLJRScoreboard] that should be broadcasted
 */
fun broadcastScoreBoard(scoreboard: PLJRScoreboard) {
    Bukkit.getOnlinePlayers().forEach { sendScoreBoard(it, scoreboard) }
}
package me.pljr.pljrapispigot.scoreboard

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Scoreboard

class PLJRScoreboard(val title: String, val lines: MutableList<String>) {

    fun toScoreboard() : Scoreboard {
        val scoreBoard = Bukkit.getScoreboardManager().newScoreboard

        val objective = scoreBoard.registerNewObjective(title, "", title)
        objective.displaySlot = DisplaySlot.SIDEBAR

        var slot = 16
        lines.forEach {
            objective.getScore(it).score = slot
            slot--
        }

        return scoreBoard
    }

    fun send(player: Player) = sendScoreBoard(player, this)

    fun broadcast() = Bukkit.getOnlinePlayers().forEach { send(it) }
}
package me.pljr.pljrapispigot.title

import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import net.kyori.adventure.util.Ticks
import org.bukkit.entity.Player

class PLJRTitle(val title: Component, val subtitle: Component, val inTime: Long, val stayTime: Long, val outTime: Long) {

    fun send(player: Player) = sendTitle(player, this)

    fun broadcast() = broadcastTitle(this)

    fun toAdventureTitle(): Title {
        return Title.title(title, subtitle,
            Title.Times.of(Ticks.duration(inTime), Ticks.duration(stayTime), Ticks.duration(outTime)))
    }
}
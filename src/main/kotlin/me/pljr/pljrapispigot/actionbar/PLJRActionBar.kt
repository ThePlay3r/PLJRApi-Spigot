package me.pljr.pljrapispigot.actionbar

import org.bukkit.entity.Player

class PLJRActionBar(val message: String, val duration: Long) {

    fun send(player: Player) {
        sendActionBar(player, this)
    }

    fun broadcast() {
        broadcastActionBar(this)
    }
}
package me.pljr.pljrapispigot.player

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class PlayerListeners(val plugin: JavaPlugin, val query: PlayerQuery) : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin,
            Runnable { query.savePlayerName(event.player.uniqueId, event.player.name) })
    }
}
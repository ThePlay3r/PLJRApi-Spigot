package me.pljr.pljrapispigot.gui

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.java.JavaPlugin

class GUIListeners(val plugin: JavaPlugin, val manager: GUIManager) : Listener {

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        if (event.player is Player) {
            val player = event.player as Player
            if (manager.guis.containsKey(player.uniqueId)) {
                if (manager.guis[player.uniqueId]!!.onClose != null) {
                    Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                        manager.open(player, manager.guis[player.uniqueId]!!.onClose!!)
                    }, 1)
                } else {
                    manager.guis.remove(player.uniqueId)
                }
            }
        }
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent){
        if (event.whoClicked is Player) {
            val player = event.whoClicked as Player
            if (manager.guis.containsKey(player.uniqueId)) {
                event.isCancelled = true
                player.updateInventory()
                manager.guis[player.uniqueId]!!.items[event.rawSlot]?.run(event)
            }
        }
    }
}
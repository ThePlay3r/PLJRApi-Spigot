package me.pljr.pljrapispigot.gui

import me.pljr.pljrapispigot.gui.events.GUIOpenEvent
import me.pljr.pljrapispigot.player.isPlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class GUIManager {
    val guis = HashMap<UUID, GUI>()

    /**
     * Opens a [GUI] to [Player]
     *
     * @param player [Player] that will be opened with the gui.
     * @param gui [GUI] that will be opened to player.
     */
    fun open(player: Player, gui: GUI) {
        player.openInventory(gui.inventory)
        guis[player.uniqueId] = gui
        Bukkit.getPluginManager().callEvent(GUIOpenEvent(player, gui))
    }

    /**
     * Closes all currently opened guis.
     */
    fun closeAll() {
        guis.forEach { (playerId) ->
            run {
                if (isPlayer(playerId)) {
                    Bukkit.getPlayer(playerId)?.closeInventory()
                }
                guis.remove(playerId)
            }
        }
    }
}
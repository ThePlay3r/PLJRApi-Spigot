package me.pljr.pljrapispigot.gui.events

import me.pljr.pljrapispigot.gui.GUI
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class GUIOpenEvent(val player: Player, val gui: GUI) : Event() {
    companion object {
        private val HANDLERS = HandlerList()
        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }
}
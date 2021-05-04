package me.pljr.pljrapispigot.events

import me.pljr.pljrapispigot.PLJRApiSpigot
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PLJRApiSpigotStartupEvent(val plugin: PLJRApiSpigot) : Event() {
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
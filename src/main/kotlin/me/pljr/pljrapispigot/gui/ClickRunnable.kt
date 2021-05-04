package me.pljr.pljrapispigot.gui

import org.bukkit.event.inventory.InventoryClickEvent

interface ClickRunnable {
    fun run(clickEvent: InventoryClickEvent)
}
package me.pljr.pljrapispigot.gui

import me.pljr.pljrapispigot.PLJRApiSpigot
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class GUI(val inventory: Inventory, val items: HashMap<Int, ClickRunnable>, val onClose: GUI? = null) {

    fun open(player: Player) = PLJRApiSpigot.guiManager.open(player, this)
}
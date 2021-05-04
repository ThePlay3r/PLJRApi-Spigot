package me.pljr.pljrapispigot.actionbar

import com.cryptomorin.xseries.messages.ActionBar
import me.pljr.pljrapispigot.PLJRApiSpigot
import org.bukkit.entity.Player

/**
 * Will send a [PLJRActionBar] to [Player] using [ActionBar].
 *
 * @param player [Player] that should receive the actionBar
 * @param actionBar [PLJRActionBar] that should be send to player
 */
fun sendActionBar(player: Player, actionBar: PLJRActionBar) {
    ActionBar.sendActionBar(
        PLJRApiSpigot,
        player, actionBar.message, actionBar.duration
    )
}

/**
 * Will broadcast a [PLJRActionBar] to all online players using [ActionBar].
 *
 * @param actionBar [PLJRActionBar] that should be broadcasts
 */
fun broadcastActionBar(actionBar: PLJRActionBar) {
    ActionBar.sendPlayersActionBar(actionBar.message)
}

/**
 * Clears an ActionBar of [Player] using [ActionBar].
 *
 * @param player [Player] that should have the ActionBar cleared
 */
fun clearActionBar(player: Player) {
    ActionBar.clearActionBar(player)
}

/**
 * Clears an ActionBar of all online player using [ActionBar]
 */
fun clearAllActionBars() {
    ActionBar.clearPlayersActionBar()
}




package me.pljr.pljrapispigot.player

import me.pljr.pljrapispigot.PLJRApiSpigot
import me.pljr.pljrapispigot.config.configuration.Settings
import me.pljr.pljrapispigot.config.configuration.SoundType
import me.pljr.pljrapispigot.config.configuration.TitleType
import me.pljr.pljrapispigot.title.TitleBuilder
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

private val QUERY = PLJRApiSpigot.playerQuery

/**
 * Checks if input belongs to a username of [Player]
 *
 * @param input Username of [Player]
 * @return True if there is a [Player] with this username, false if otherwise
 */
fun isPlayer(input: String): Boolean {
    val player = Bukkit.getPlayer(input)
    return player != null && player.isOnline
}

/**
 * Checks if input belongs to a UUID of [Player]
 *
 * @param input UUID of [Player]
 * @return True if there is a [Player] with this UUID, false if otherwise
 */
fun isPlayer(input: UUID): Boolean {
    val player = Bukkit.getPlayer(input)
    return player != null && player.isOnline
}

/**
 * Will try to get a username of [OfflinePlayer]
 *
 * @param offlinePlayer [OfflinePlayer] that we will try to get the username of
 * @return Username of offlinePlayer, "?" otherwise
 */
fun getName(offlinePlayer: OfflinePlayer): String {
    return if (offlinePlayer.isOnline) {
        offlinePlayer.player!!.name
    } else getName(offlinePlayer.uniqueId)
}

/**
 * Will try to get a username of Player, from [UUID]
 *
 * @param uuid [UUID] of Player.
 * @return Username of player, "?" otherwise
 */
fun getName(uuid: UUID) = QUERY.playerNames.getOrElse(uuid) { "?" }

/**
 * Will teleport [Player] to another [Player] using [.teleport].
 *
 * @param player [Player] that will be teleported to target
 * @param target [Player] that whose location will be set as the destination
 */
fun teleport(player: Player, target: Player) = teleport(player, target.location)

/**
 * Will teleport [Player] to [Location] with countdown displayed by [PLJRTitle].
 *
 * @param player [Player] that should be teleported
 * @param location [Location] destination player should be teleported to
 */
fun teleport(player: Player, location: Location) {
    if (player.hasPermission("pljrapi.teleport.bypass")) {
        player.teleport(location)
        SoundType.TELEPORT_TP.get().play(player)
        TitleType.TELEPORT_TELEPORT.get().send(player)
        return
    }
    object : BukkitRunnable() {
        var countdown = Settings.TELEPORT_DELAY
        val pLoc = player.location
        val x = pLoc.x
        val z = pLoc.z
        override fun run() {
            val currentLoc = player.location
            val currentX = currentLoc.x
            val currentZ = currentLoc.z
            // Fail (Player moved)
            if (currentX != x || currentZ != z) {
                TitleBuilder(TitleType.TELEPORT_FAIL.get())
                    .replaceTitle("{time}", countdown.toString() + "")
                    .replaceSubtitle("{time}", countdown.toString() + "")
                    .create().send(player)
                SoundType.TELEPORT_FAIL.get().play(player)
                cancel()
                return
            }
            // Success (Teleporting Player)
            if (countdown <= 0) {
                TitleBuilder(TitleType.TELEPORT_TELEPORT.get())
                    .replaceTitle("{time}", countdown.toString() + "")
                    .replaceSubtitle("{time}", countdown.toString() + "")
                    .create().send(player)
                SoundType.TELEPORT_TP.get().play(player)
                Bukkit.getScheduler().runTask(PLJRApiSpigot.instance, Runnable { player.teleport(location) })
                cancel()
                return
            }
            // Ticking (Waiting to be teleported)
            TitleBuilder(TitleType.TELEPORT_TICKING.get())
                .replaceTitle("{time}", countdown.toString() + "")
                .replaceSubtitle("{time}", countdown.toString() + "")
                .create().send(player)
            SoundType.TELEPORT_TICK.get().play(player)
            countdown--
        }
    }.runTaskTimerAsynchronously(PLJRApiSpigot.instance, 0, 20)
}

/**
 * Will try to give an [ItemStack] to inventory of [Player], drops the item at target's location
 * if there is not enough space.
 *
 * @param target [Player] that should receive the itemStack
 * @param item [ItemStack] that should be given to target
 */
fun give(target: Player, item: ItemStack) {
    if (target.inventory.firstEmpty() == -1) {
        target.world.dropItem(target.location, item)
    } else {
        target.inventory.addItem(item)
    }
}
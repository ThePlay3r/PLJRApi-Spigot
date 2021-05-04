package me.pljr.pljrapispigot.util

import com.google.common.collect.Iterables
import com.google.common.io.ByteStreams
import me.pljr.pljrapispigot.PLJRApiSpigot
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * Sends a BungeeCord message to [Player].
 *
 * @param receiver The name of [Player] to receive the message
 * @param message String that will be send to the receiver
 */
fun sendBungeeMessage(receiver: String, message: String) {
    val out = ByteStreams.newDataOutput()
    out.writeUTF("message")
    out.writeUTF(receiver)
    out.writeUTF(message)
    val player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null) ?: return
    player.sendPluginMessage(PLJRApiSpigot, "pljrapi:chat", out.toByteArray())
}

fun connectToServer(player: Player, server: String) {
    val out = ByteStreams.newDataOutput()
    out.writeUTF("Connect")
    out.writeUTF(server)
    player.sendPluginMessage(PLJRApiSpigot, "BungeeCord", out.toByteArray())
}

/**
 * Broadcasts message across BungeeCord to all Players with specified permission.
 *
 * @param message String that will be send to all Players with specified permission
 * @param perm Permission tha will be required to see the message
 */
fun broadcastBungeeMessage(message: String, perm: String) {
    val out = ByteStreams.newDataOutput()
    out.writeUTF("broadcast")
    out.writeUTF(perm)
    out.writeUTF(message)
    val player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null) ?: return
    player.sendPluginMessage(PLJRApiSpigot, "pljrapi:chat", out.toByteArray())
}
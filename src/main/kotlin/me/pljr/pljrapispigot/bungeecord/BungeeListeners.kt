package me.pljr.pljrapispigot.bungeecord

import com.google.common.io.ByteStreams
import me.pljr.pljrapispigot.player.isPlayer
import me.pljr.pljrapispigot.util.broadcastMessage
import me.pljr.pljrapispigot.util.sendMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener

const val PLJRAPI_BUNGEE_CHANNEL = "pljrapi:chat"

class BungeeListeners : PluginMessageListener {

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        if (channel == PLJRAPI_BUNGEE_CHANNEL) {
            val input = ByteStreams.newDataInput(message)
            val subChannel = input.readUTF()
            when (subChannel) {
                "message" -> {
                    val playerName = input.readUTF()
                    val message = input.readUTF()
                    if (isPlayer(playerName)){
                        val player = Bukkit.getPlayer(playerName)!!
                        sendMessage(player, message)
                    }
                }
                "broadcast" -> {
                    val message = input.readUTF()
                    val perm = input.readUTF()
                    broadcastMessage(message, perm)
                }
            }
        }
    }
}
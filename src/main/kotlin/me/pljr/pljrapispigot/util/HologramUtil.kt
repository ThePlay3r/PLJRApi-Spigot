package me.pljr.pljrapispigot.util

import com.gmail.filoghost.holographicdisplays.api.Hologram
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI
import me.pljr.pljrapispigot.PLJRApiSpigot
import me.pljr.pljrapispigot.config.configuration.Settings
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location

/**
 * Creates a [Hologram] for specified time using HolographicDisplays API.
 *
 * @param location [Location], where the Hologram should be created
 * @param text Lines of Hologram
 * @param ticks For how long the Hologram should last
 */
fun create(location: Location?, text: List<String?>, ticks: Int) {
    if (!Settings.HOLOGRAMS) {
        Bukkit.getConsoleSender()
            .sendMessage(ChatColor.RED.toString() + "PLJRApi: Tried to create a HolographicDisplays Hologram while disabled in config!")
        return
    }
    val hologram = HologramsAPI.createHologram(PLJRApiSpigot, location)
    for (line in text) {
        hologram!!.appendTextLine(line)
    }
    Bukkit.getScheduler().runTaskLaterAsynchronously(PLJRApiSpigot,
        Runnable {
            if (hologram != null && !hologram.isDeleted) {
                hologram.delete()
            }
        }, ticks.toLong()
    )
}
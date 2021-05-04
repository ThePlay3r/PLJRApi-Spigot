package me.pljr.pljrapispigot.config.configuration

import me.pljr.pljrapispigot.config.ConfigManager
import me.pljr.pljrapispigot.title.PLJRTitle
import me.pljr.pljrapispigot.title.TitleBuilder
import net.kyori.adventure.text.Component
import org.bukkit.configuration.file.FileConfiguration
import java.util.*

enum class TitleType(val defaultValue: PLJRTitle) {
    TELEPORT_TICKING(TitleBuilder(
        Component.text("&b&lTeleportation"),
        Component.text("&7{time}&8s"),
        20, 40, 20).create()),
    TELEPORT_FAIL(TitleBuilder(
        Component.text("&b&lTeleportation"),
        Component.text("&cTeleportation canceled.."),
        20, 60, 20).create()),
    TELEPORT_TELEPORT(TitleBuilder(
        Component.text("&b&lTeleportation"),
        Component.text("&7Teleporting.."),
        20, 60, 20).create());
    
    companion object {
        private val titleType = EnumMap<TitleType, PLJRTitle>(TitleType::class.java)

        fun load(config: ConfigManager) {
            val fileConfig: FileConfiguration = config.config
            titleType.clear()
            values().forEach {
                if (!fileConfig.isSet(it.toString())){
                    fileConfig[it.toString()] = it.defaultValue
                } else {
                    titleType[it] = config.getPLJRTitle(titleType.toString())
                }
            }
            config.save()
        }
    }

    fun get(): PLJRTitle {
        return titleType.getOrDefault(this, this.defaultValue)
    }
}
package me.pljr.pljrapispigot.config.configuration

import me.pljr.pljrapispigot.config.ConfigManager
import me.pljr.pljrapispigot.util.colorString
import org.bukkit.configuration.file.FileConfiguration
import java.util.*

enum class Lang(val defaultValue: String) {
    NO_PERM("&c&l! &8» &fYou don't have enough permissions to do this!"),
    NO_NUMBER("&c&l! &8» &b{arg} &fis not a number!"),
    NO_MATERIAL("&c&l! &8» &b{material} &fis not a material!"),
    NO_BALANCE("&c&l! &8» &fYou don't have enough balance! &7(\${amount})"),
    OFFLINE("&c&l! &8» &e{name} &fis not on the server!"),
    TIME_FORMAT_DAYS("%d d, %02d h. %02d m. %02d s."),
    TIME_FORMAT_HOURS("%02d h. %02d m. %02d s."),
    TIME_FORMAT_MINUTES("00 h. %02d m. %02d s."),
    COMMAND_RESPONSE_PLAYER("&c&l! &8» &fYou can't use this command!"),
    COMMAND_RESPONSE_CONSOLE("&c&l! &8» &fThis command is for in-game use only!");

    companion object {
        private val lang = EnumMap<Lang, String>(Lang::class.java)

        fun load(config: ConfigManager) {
            val fileConfig: FileConfiguration = config.config
            lang.clear()
            values().forEach {
                if (!fileConfig.isSet(it.toString())){
                    fileConfig[it.toString()] = it.defaultValue
                } else {
                    lang[it] = config.getString(lang.toString())
                }
            }
            config.save()
        }
    }

    fun get(): String {
        return lang.getOrDefault(this, colorString(this.defaultValue))
    }
}
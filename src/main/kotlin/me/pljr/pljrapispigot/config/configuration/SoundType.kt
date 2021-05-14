package me.pljr.pljrapispigot.config.configuration

import com.cryptomorin.xseries.XSound
import me.pljr.pljrapispigot.config.ConfigManager
import me.pljr.pljrapispigot.sound.PLJRSound
import me.pljr.pljrapispigot.sound.SoundBuilder
import org.bukkit.configuration.file.FileConfiguration
import java.util.*

enum class SoundType(val defaultValue: PLJRSound) {
    COMMAND_FAIL(SoundBuilder(XSound.ENTITY_VILLAGER_NO, 10f, 1f).create()),
    TELEPORT_TICK(SoundBuilder(XSound.UI_BUTTON_CLICK, 10f, 1f).create()),
    TELEPORT_FAIL(SoundBuilder(XSound.ENTITY_VILLAGER_HURT, 10f, 1f).create()),
    TELEPORT_TP(SoundBuilder(XSound.ENTITY_CAT_PURR, 10f, 1f).create());

    companion object {
        private val soundType = EnumMap<SoundType, PLJRSound>(SoundType::class.java)

        fun load(config: ConfigManager) {
            val fileConfig: FileConfiguration = config.config
            soundType.clear()
            values().forEach {
                if (!fileConfig.isSet(it.toString())){
                    config.setPLJRSound(it.toString(), it.defaultValue)
                } else {
                    soundType[it] = config.getPLJRSound(it.toString())
                }
            }
            config.save()
        }
    }

    fun get(): PLJRSound {
        return soundType.getOrDefault(this, this.defaultValue)
    }
}
package me.pljr.pljrapispigot.config.configuration

import me.pljr.pljrapispigot.config.ConfigManager


object Settings {
    private const val PATH = "settings"

    var SOUNDS = true
    var TELEPORT_DELAY = 7
    var VAULT = false
    var HOLOGRAMS = false
    var PLACEHOLDERS = false
    var DISABLED_MESSAGES = true

    fun load(config: ConfigManager) {
        SOUNDS = config.getBoolean("$PATH.sounds", SOUNDS)
        TELEPORT_DELAY = config.getInt("$PATH.teleport-delay", TELEPORT_DELAY)
        VAULT = config.getBoolean("$PATH.vault", VAULT)
        HOLOGRAMS = config.getBoolean("$PATH.holograms", HOLOGRAMS)
        PLACEHOLDERS = config.getBoolean("$PATH.placeholders", PLACEHOLDERS)
        DISABLED_MESSAGES = config.getBoolean("$PATH.disabled-messages", DISABLED_MESSAGES)
    }
}

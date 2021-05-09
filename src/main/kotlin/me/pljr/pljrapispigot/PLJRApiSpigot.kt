package me.pljr.pljrapispigot

import me.pljr.pljrapispigot.bungeecord.BungeeListeners
import me.pljr.pljrapispigot.bungeecord.PLJRAPI_BUNGEE_CHANNEL
import me.pljr.pljrapispigot.config.ConfigManager
import me.pljr.pljrapispigot.config.configuration.Lang
import me.pljr.pljrapispigot.config.configuration.Settings
import me.pljr.pljrapispigot.config.configuration.SoundType
import me.pljr.pljrapispigot.config.configuration.TitleType
import me.pljr.pljrapispigot.database.DataSource
import me.pljr.pljrapispigot.events.PLJRApiSpigotStartupEvent
import me.pljr.pljrapispigot.gui.GUIListeners
import me.pljr.pljrapispigot.gui.GUIManager
import me.pljr.pljrapispigot.player.PlayerListeners
import me.pljr.pljrapispigot.player.PlayerQuery
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.milkbowl.vault.economy.Economy
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class PLJRApiSpigot : JavaPlugin() {
    companion object {
        private const val BSTATS_ID = 10442
        lateinit var instance: PLJRApiSpigot

        lateinit var dataSource: DataSource
        var vaultEcon: Economy? = null
        lateinit var bukkitAudiences: BukkitAudiences

        lateinit var playerQuery: PlayerQuery

        lateinit var guiManager: GUIManager

        lateinit var configManager: ConfigManager

        /**
         * Sends DataSource from provided configuration, if enabled.
         * PLJRApi's DataSource otherwise.
         *
         * @param config [DataSource] that will be checked for enabled MySQL.
         * @return [DataSource] from provided config, PLJRApi's otherwise.
         */
        fun getDataSource(config: ConfigManager): DataSource {
            return if (config.getBoolean("mysql.enabled")) {
                DataSource(config)
            } else dataSource
        }
    }

    override fun onEnable() {
        instance = this
        Metrics(this, BSTATS_ID)
        setupAdventure()
        setupConfig()
        setupDatabase()
        setupBungee()
        setupManagers()
        setupListeners()
        if (Settings.VAULT) {
            setupVault()
        }
        server.pluginManager.callEvent(PLJRApiSpigotStartupEvent(this))
    }

    private fun setupAdventure() {
        bukkitAudiences = BukkitAudiences.create(this)
    }

    private fun setupConfig(){
        saveDefaultConfig()
        configManager = ConfigManager(this, "config.yml")
        Settings.load(configManager)
        Lang.load(ConfigManager(this, "lang.yml"))
        SoundType.load(ConfigManager(this, "sounds.yml"))
        TitleType.load(ConfigManager(this, "titles.yml"))
    }

    private fun setupDatabase() {
        dataSource = DataSource(configManager)
        dataSource.initPool("PLJRApi-Spigot-Pool")
        playerQuery = PlayerQuery(dataSource, this)
    }

    private fun setupBungee() {
        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")

        server.messenger.registerIncomingPluginChannel(this, PLJRAPI_BUNGEE_CHANNEL, BungeeListeners())
        server.messenger.registerOutgoingPluginChannel(this, PLJRAPI_BUNGEE_CHANNEL)
    }

    private fun setupManagers() {
        guiManager = GUIManager()
    }

    private fun setupListeners() {
        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(GUIListeners(this, guiManager), this)
        pluginManager.registerEvents(PlayerListeners(this, playerQuery), this)
    }

    private fun setupVault() {
        if (!setupVaultEconomy()) {
            logger.severe("Plugin disabled due to no Vault dependecy found!")
            server.pluginManager.disablePlugin(this)
        }
    }

    private fun setupVaultEconomy() : Boolean {
        if (server.pluginManager.getPlugin("Vault") != null) {
            val rsp = server.servicesManager.getRegistration(Economy::class.java)
            if (rsp != null) {
                vaultEcon = rsp.provider
                return true
            }
        }
        return false
    }
}
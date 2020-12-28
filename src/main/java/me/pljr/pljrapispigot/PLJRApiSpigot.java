package me.pljr.pljrapispigot;

import me.pljr.pljrapispigot.config.CfgMysql;
import me.pljr.pljrapispigot.config.CfgSettings;
import me.pljr.pljrapispigot.config.Lang;
import me.pljr.pljrapispigot.config.SoundType;
import me.pljr.pljrapispigot.database.DataSource;
import me.pljr.pljrapispigot.events.PLJRApiStartupEvent;
import me.pljr.pljrapispigot.listeners.PlayerQuitListener;
import me.pljr.pljrapispigot.managers.ConfigManager;
import me.pljr.pljrapispigot.managers.GUIManager;
import me.pljr.pljrapispigot.managers.QueryManager;
import me.pljr.pljrapispigot.utils.BungeeUtil;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class PLJRApiSpigot extends JavaPlugin {
    private static PLJRApiSpigot instance;
    private static ConfigManager config;
    private static DataSource dataSource;
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy vaultEcon = null;
    private static BukkitAudiences bukkitAudiences;
    private static QueryManager queryManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        setupMiniMessage();
        setupConfig();
        setupDatabase();
        setupBungee();
        setupListeners();
        if (CfgSettings.VAULT) setupVault();
        getServer().getPluginManager().callEvent(new PLJRApiStartupEvent());
    }

    private void setupMiniMessage(){
        bukkitAudiences = BukkitAudiences.create(this);
    }

    private void setupVault(){
        if (!setupVaultEconomy()){
            log.severe(String.format(ChatColor.RED + "[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private boolean setupVaultEconomy(){
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        vaultEcon = rsp.getProvider();
        return vaultEcon != null;
    }

    private void setupDatabase(){
        dataSource = new DataSource(CfgMysql.HOST, CfgMysql.PORT, CfgMysql.DATABASE, CfgMysql.USERNAME, CfgMysql.PASSWORD);
        dataSource.initPool();
        queryManager = new QueryManager(dataSource, this);
    }

    private void setupBungee(){
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "pljrapi:chat", new BungeeUtil());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "pljrapi:chat");
    }

    private void setupConfig(){
        saveDefaultConfig();
        config = new ConfigManager(this, "config.yml");
        Lang.load(config);
        CfgSettings.load(config);
        SoundType.load(config);
        CfgMysql.load(config);
    }

    private void setupListeners(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new GUIManager(), this);
        pluginManager.registerEvents(new PlayerQuitListener(queryManager), this);
    }

    public static ConfigManager getConfigManager() {
        return config;
    }
    public static DataSource getDataSource(){
        return dataSource;
    }
    public static Economy getVaultEcon() {
        return vaultEcon;
    }
    public static PLJRApiSpigot getInstance() {
        return instance;
    }
    public static BukkitAudiences getBukkitAudiences() {
        return bukkitAudiences;
    }
    public static QueryManager getQueryManager() {
        return queryManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

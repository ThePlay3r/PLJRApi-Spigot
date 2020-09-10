package me.pljr.pljrapi;

import me.pljr.pljrapi.config.CfgLang;
import me.pljr.pljrapi.config.CfgMysql;
import me.pljr.pljrapi.config.CfgSettings;
import me.pljr.pljrapi.config.CfgSounds;
import me.pljr.pljrapi.database.DataSource;
import me.pljr.pljrapi.events.PLJRApiStartupEvent;
import me.pljr.pljrapi.managers.ConfigManager;
import me.pljr.pljrapi.utils.BungeeUtil;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class PLJRApi extends JavaPlugin {
    private static PLJRApi instance;
    private static ConfigManager config;
    private static DataSource dataSource;
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy vaultEcon = null;
    private static BukkitAudiences bukkitAudiences;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        setupMiniMessage();
        setupConfig();
        setupDatabase();
        setupBungee();
        setupVault();
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
        dataSource = new DataSource(CfgMysql.host, CfgMysql.port, CfgMysql.database, CfgMysql.username, CfgMysql.password);
        dataSource.initPool();
    }

    private void setupBungee(){
        getServer().getMessenger().registerIncomingPluginChannel(this, "pljrapi:chat", new BungeeUtil());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "pljrapi:chat");
    }

    private void setupConfig(){
        saveDefaultConfig();
        config = new ConfigManager(getConfig(), "§cPLJRApi:", "config.yml");
        CfgLang.load();
        CfgSettings.load(config);
        CfgSounds.load();
        CfgMysql.load();
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
    public static PLJRApi getInstance() {
        return instance;
    }
    public static BukkitAudiences getBukkitAudiences() {
        return bukkitAudiences;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

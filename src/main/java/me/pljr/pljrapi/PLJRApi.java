package me.pljr.pljrapi;

import com.cryptomorin.xseries.messages.Titles;
import me.pljr.pljrapi.config.CfgLang;
import me.pljr.pljrapi.config.CfgMysql;
import me.pljr.pljrapi.config.CfgSettings;
import me.pljr.pljrapi.config.CfgSounds;
import me.pljr.pljrapi.database.DataSource;
import me.pljr.pljrapi.managers.ConfigManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class PLJRApi extends JavaPlugin {
    private static PLJRApi instance;
    private static ConfigManager config;
    private static DataSource dataSource;
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy vaultEcon = null;
    private static Permission vaultPerms = null;
    private static Chat vaultChat = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        setupConfig();
        setupDatabase();
        setupBungee();
        setupVault();
    }

    private void setupVault(){
        if (!setupVaultEconomy() || !setupVaultChat() || !setupVaultPermissions()){
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
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

    private boolean setupVaultChat(){
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        vaultChat = rsp.getProvider();
        return vaultChat != null;
    }

    private boolean setupVaultPermissions(){
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        vaultPerms = rsp.getProvider();
        return vaultPerms != null;
    }
    private void setupDatabase(){
        dataSource = new DataSource();
        dataSource.load();
        dataSource.initPool();
    }

    private void setupBungee(){
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    private void setupConfig(){
        saveDefaultConfig();
        config = new ConfigManager(getConfig(), "§cPLJRApi:", "config.yml");
        CfgLang.load();
        CfgSettings.load();
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
    public static Permission getVaultPerms() {
        return vaultPerms;
    }
    public static Chat getVaultChat() {
        return vaultChat;
    }
    public static PLJRApi getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package me.pljr.pljrapispigot;

import lombok.Getter;
import me.pljr.pljrapispigot.config.Lang;
import me.pljr.pljrapispigot.config.Settings;
import me.pljr.pljrapispigot.config.SoundType;
import me.pljr.pljrapispigot.config.TitleType;
import me.pljr.pljrapispigot.database.DataSource;
import me.pljr.pljrapispigot.events.PLJRApiStartupEvent;
import me.pljr.pljrapispigot.listeners.BungeeListeners;
import me.pljr.pljrapispigot.listeners.PlayerQuitListener;
import me.pljr.pljrapispigot.managers.ConfigManager;
import me.pljr.pljrapispigot.managers.GUIManager;
import me.pljr.pljrapispigot.managers.QueryManager;
import me.pljr.pljrapispigot.utils.BStatsUtil;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class PLJRApiSpigot extends JavaPlugin {
    private static final int BSTATS_ID = 10442;

    private static PLJRApiSpigot instance;
    public static Logger log;

    private DataSource dataSource;
    @Getter private Economy vaultEcon = null;
    @Getter private BukkitAudiences bukkitAudiences;
    @Getter private QueryManager queryManager;

    @Getter private GUIManager guiManager;

    private ConfigManager configManager;
    @Getter private Settings settings;

    public static PLJRApiSpigot get(){
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        log = getLogger();
        BStatsUtil.addMetrics(this, BSTATS_ID);
        setupMiniMessage();
        setupConfig();
        setupDatabase();
        setupBungee();
        setupManagers();
        setupListeners();
        if (settings.isVault()) setupVault();
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
        dataSource = new DataSource(configManager);
        dataSource.initPool("PLJRAPI-Spigot-Pool");
        queryManager = new QueryManager(dataSource, this);
    }

    private void setupBungee(){
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "pljrapi:chat", new BungeeListeners());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "pljrapi:chat");
    }

    private void setupConfig(){
        saveDefaultConfig();
        configManager = new ConfigManager(this, "config.yml");
        settings = new Settings(configManager);
        Lang.load(new ConfigManager(this, "lang.yml"));
        SoundType.load(new ConfigManager(this, "sounds.yml"));
        TitleType.load(new ConfigManager(this, "titles.yml"));
    }

    private void setupManagers(){
        guiManager = new GUIManager(this);
    }

    private void setupListeners(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(guiManager, this);
        pluginManager.registerEvents(new PlayerQuitListener(queryManager), this);
    }

    /**
     * Sends DataSource from provided configuration, if enabled.
     * PLJRApi's DataSource otherwise.
     *
     * @param config {@link DataSource} that will be checked for enabled MySQL.
     * @return {@link DataSource} from provided config, PLJRApi's otherwise.
     */
    public DataSource getDataSource(ConfigManager config){
        if (config.getBoolean("mysql.enabled")){
            return new DataSource(config);
        }
        return dataSource;
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}

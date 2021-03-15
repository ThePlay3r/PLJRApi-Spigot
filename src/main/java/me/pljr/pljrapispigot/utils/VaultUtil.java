package me.pljr.pljrapispigot.utils;

import me.pljr.pljrapispigot.PLJRApiSpigot;
import me.pljr.pljrapispigot.config.Settings;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

/**
 * @see net.milkbowl.vault.economy.Economy
 */
public final class VaultUtil {
    private final static Settings SETTINGS = PLJRApiSpigot.get().getSettings();
    private final static Economy ECONOMY = PLJRApiSpigot.get().getVaultEcon();

    /**
     * Will get the current Economy balance of {@link OfflinePlayer}.
     *
     * @param target {@link OfflinePlayer} that we should get the balance of
     * @return Target's Economy balance
     */
    public static double getBalance(OfflinePlayer target){
        if (!SETTINGS.isVault()){
            if (SETTINGS.isDisabledMessages()) Bukkit.getConsoleSender().sendMessage("§cPLJRApi: Tried to use Vault, while disabled in config!");
            return 0;
        }
        return ECONOMY.getBalance(target);
    }

    /**
     * Will get the current Economy balance of target.
     *
     * @param target Target that we should get the balance of
     * @return Target's Economy balance
     */
    @Deprecated
    public static double getBalance(String target){
        if (!SETTINGS.isVault()){
            if (SETTINGS.isDisabledMessages()) Bukkit.getConsoleSender().sendMessage("§cPLJRApi: Tried to use Vault, while disabled in config!");
            return 0;
        }
        return ECONOMY.getBalance(target);
    }

    /**
     * Will check, if target has enough balance.
     *
     * @param target Target that we should get the balance of
     * @param amount Amount that will be check.
     * @return If target has at least the amount of balance.
     *
     * @see #getBalance(OfflinePlayer)
     */
    public static boolean hasBalance(OfflinePlayer target, double amount){
        if (!SETTINGS.isVault()){
            if (SETTINGS.isDisabledMessages()) Bukkit.getConsoleSender().sendMessage("§cPLJRApi: Tried to use Vault, while disabled in config!");
            return true;
        }
        return getBalance(target) >= amount;
    }

    /**
     * Will check, if target has enough balance.
     *
     * @param target Target that we should get the balance of
     * @param amount Amount that will be check.
     * @return If target has at least the amount of balance.
     *
     * @see #getBalance(String)
     */
    @Deprecated
    public static boolean hasBalance(String target, double amount){
        if (!SETTINGS.isVault()){
            if (SETTINGS.isDisabledMessages()) Bukkit.getConsoleSender().sendMessage("§cPLJRApi: Tried to use Vault, while disabled in config!");
            return true;
        }
        return getBalance(target) >= amount;
    }

    /**
     * Will deposit funds to Economy balance of {@link OfflinePlayer}.
     *
     * @param target {@link OfflinePlayer} that should get the amount deposited
     * @param amount Amount of money that should be deposited
     */
    public static void deposit(OfflinePlayer target, double amount){
        if (!SETTINGS.isVault()){
            if (SETTINGS.isDisabledMessages()) Bukkit.getConsoleSender().sendMessage("§cPLJRApi: Tried to use Vault, while disabled in config!");
            return;
        }
        ECONOMY.depositPlayer(target, amount);
    }

    /**
     * Will deposit funds to Economy balance of target.
     *
     * @param target Target that should get the amount deposited
     * @param amount Amount of money that should be deposited
     */
    @Deprecated
    public static void deposit(String target, double amount){
        if (!SETTINGS.isVault()){
            if (SETTINGS.isDisabledMessages()) Bukkit.getConsoleSender().sendMessage("§cPLJRApi: Tried to use Vault, while disabled in config!");
            return;
        }
        ECONOMY.depositPlayer(target, amount);
    }

    /**
     * Will withdraw funds from Economy balance of {@link OfflinePlayer}.
     *
     * @param target {@link OfflinePlayer} that should get the amount withdrawn
     * @param amount Amount of money that should be withdrawn
     */
    public static void withdraw(OfflinePlayer target, double amount){
        if (!SETTINGS.isVault()){
            if (SETTINGS.isDisabledMessages()) Bukkit.getConsoleSender().sendMessage("§cPLJRApi: Tried to use Vault, while disabled in config!");
            return;
        }
        ECONOMY.withdrawPlayer(target, amount);
    }

    /**
     * Will withdraw funds from Economy balance of target.
     *
     * @param target Target that should get the amount withdrawn
     * @param amount Amount of money that should be withdrawn
     */
    @Deprecated
    public static void withdraw(String target, double amount){
        if (!SETTINGS.isVault()){
            if (SETTINGS.isDisabledMessages()) Bukkit.getConsoleSender().sendMessage("§cPLJRApi: Tried to use Vault, while disabled in config!");
            return;
        }
        ECONOMY.withdrawPlayer(target, amount);
    }
}

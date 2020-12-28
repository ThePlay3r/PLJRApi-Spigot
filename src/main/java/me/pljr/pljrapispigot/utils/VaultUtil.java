package me.pljr.pljrapispigot.utils;

import me.pljr.pljrapispigot.PLJRApiSpigot;
import me.pljr.pljrapispigot.config.CfgSettings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

/**
 * @see net.milkbowl.vault.economy.Economy
 */
public class VaultUtil {

    /**
     * Will get the current Economy balance of {@link OfflinePlayer}.
     *
     * @param target {@link OfflinePlayer} that we should get the balance of
     * @return Target's Economy balance
     */
    public static double getBalance(OfflinePlayer target){
        if (!CfgSettings.VAULT){
            if (CfgSettings.DISABLED_MESSAGES) Bukkit.getConsoleSender().sendMessage("§cPLJRApi: Tried to use Vault, while disabled in config!");
            return 0;
        }
        return PLJRApiSpigot.getVaultEcon().getBalance(target);
    }

    /**
     * Will get the current Economy balance of target.
     *
     * @param target Target that we should get the balance of
     * @return Target's Economy balance
     */
    public static double getBalance(String target){
        if (!CfgSettings.VAULT){
            if (CfgSettings.DISABLED_MESSAGES) Bukkit.getConsoleSender().sendMessage("§cPLJRApi: Tried to use Vault, while disabled in config!");
            return 0;
        }
        return PLJRApiSpigot.getVaultEcon().getBalance(target);
    }

    /**
     * Will deposit funds to Economy balance of {@link OfflinePlayer}.
     *
     * @param target {@link OfflinePlayer} that should get the amount deposited
     * @param amount Amount of money that should be deposited
     */
    public static void deposit(OfflinePlayer target, double amount){
        if (!CfgSettings.VAULT){
            if (CfgSettings.DISABLED_MESSAGES) Bukkit.getConsoleSender().sendMessage("§cPLJRApi: Tried to use Vault, while disabled in config!");
            return;
        }
        PLJRApiSpigot.getVaultEcon().depositPlayer(target, amount);
    }

    /**
     * Will deposit funds to Economy balance of target.
     *
     * @param target Target that should get the amount deposited
     * @param amount Amount of money that should be deposited
     */
    public static void deposit(String target, double amount){
        if (!CfgSettings.VAULT){
            if (CfgSettings.DISABLED_MESSAGES) Bukkit.getConsoleSender().sendMessage("§cPLJRApi: Tried to use Vault, while disabled in config!");
            return;
        }
        PLJRApiSpigot.getVaultEcon().depositPlayer(target, amount);
    }

    /**
     * Will withdraw funds from Economy balance of {@link OfflinePlayer}.
     *
     * @param target {@link OfflinePlayer} that should get the amount withdrawn
     * @param amount Amount of money that should be withdrawn
     */
    public static void withdraw(OfflinePlayer target, double amount){
        if (!CfgSettings.VAULT){
            if (CfgSettings.DISABLED_MESSAGES) Bukkit.getConsoleSender().sendMessage("§cPLJRApi: Tried to use Vault, while disabled in config!");
            return;
        }
        PLJRApiSpigot.getVaultEcon().withdrawPlayer(target, amount);
    }

    /**
     * Will withdraw funds from Economy balance of target.
     *
     * @param target Target that should get the amount withdrawn
     * @param amount Amount of money that should be withdrawn
     */
    public static void withdraw(String target, double amount){
        if (!CfgSettings.VAULT){
            if (CfgSettings.DISABLED_MESSAGES) Bukkit.getConsoleSender().sendMessage("§cPLJRApi: Tried to use Vault, while disabled in config!");
            return;
        }
        PLJRApiSpigot.getVaultEcon().withdrawPlayer(target, amount);
    }
}

package me.pljr.pljrapi.utils;

import me.pljr.pljrapi.PLJRApi;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class VaultUtil {

    public static double getBalance(OfflinePlayer player){
        return PLJRApi.getVaultEcon().getBalance(player);
    }

    public static double getBalance(String player){
        return PLJRApi.getVaultEcon().getBalance(player);
    }

    public static void deposit(OfflinePlayer player, double amount){
        PLJRApi.getVaultEcon().depositPlayer(player, amount);
    }

    public static void deposit(String player, double amount){
        PLJRApi.getVaultEcon().depositPlayer(player, amount);
    }

    public static void withdraw(OfflinePlayer player, double amount){
        PLJRApi.getVaultEcon().withdrawPlayer(player, amount);
    }

    public static void withdraw(String player, double amount){
        PLJRApi.getVaultEcon().withdrawPlayer(player, amount);
    }

    public static void setPerm(Player player, String permission, boolean state){
        if (state){
            if (PLJRApi.getVaultPerms().has(player, permission)) return;
            PLJRApi.getVaultPerms().playerAdd(player, permission);
        }else{
            if (!PLJRApi.getVaultPerms().has(player, permission)) return;
            PLJRApi.getVaultPerms().playerRemove(player, permission);
        }
    }
}

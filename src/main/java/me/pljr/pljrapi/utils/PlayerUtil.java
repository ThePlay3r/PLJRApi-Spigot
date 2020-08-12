package me.pljr.pljrapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerUtil {

    public static boolean isPlayer(String playerName){
        Player player = Bukkit.getPlayer(playerName);
        return player != null && player.isOnline();
    }

    public static boolean isPlayer(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        return player != null && player.isOnline();
    }

    public static String getName(OfflinePlayer offlinePlayer){
        String name = offlinePlayer.getName();
        if (name == null){
            return  "?";
        }
        return name;
    }
}

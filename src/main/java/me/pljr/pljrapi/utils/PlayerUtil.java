package me.pljr.pljrapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUtil {

    public static boolean isPlayer(String playerName){
        Player player = Bukkit.getPlayer(playerName);
        return player != null && player.isOnline();
    }
}

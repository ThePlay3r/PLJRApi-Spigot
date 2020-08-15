package me.pljr.pljrapi.utils;

import me.pljr.pljrapi.config.CfgSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatUtil {

    public static void message(String receiver, String message, boolean bungee){
        if (bungee){
            BungeeUtil.message(receiver, message);
        }else{
            if (PlayerUtil.isPlayer(receiver)){
                Player player = Bukkit.getPlayer(receiver);
                player.sendMessage(message);
            }
        }
    }

    public static void broadcast(String message, boolean bungee){
        if (bungee){
            BungeeUtil.broadcastMessage(message);
        }else{
            Bukkit.broadcastMessage(message);
        }
    }

    public static void broadcast(List<String> messages, boolean bungee){
        if (bungee){
            messages.forEach(BungeeUtil::broadcastMessage);
        }else{
            messages.forEach(Bukkit::broadcastMessage);
        }
    }
}

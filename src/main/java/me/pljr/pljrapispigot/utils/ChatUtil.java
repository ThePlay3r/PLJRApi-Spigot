package me.pljr.pljrapispigot.utils;

import me.pljr.pljrapispigot.PLJRApiSpigot;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class ChatUtil {
    private final static BukkitAudiences BUKKIT_AUDIENCES = PLJRApiSpigot.get().getBukkitAudiences();
    /**
     * Sends a message with parsed {@link MiniMessage} tags and PAPI Placeholders to {@link OfflinePlayer}, if online.
     *
     * @param player {@link OfflinePlayer} that we will try to send the message to
     * @param message String that will be send to player, if online
     *
     * @see #sendMessage(Player, String)
     */
    public static void sendMessage(OfflinePlayer player, String message){
        if (!player.isOnline()) return;
        sendMessage(player.getPlayer(), message);
    }

    /**
     * Sends a message with parsed PAPI Placeholders to {@link OfflinePlayer}, if online.
     *
     * @param player {@link OfflinePlayer} that we will try to send the message to
     * @param message String that will be send to player, if online
     *
     * @see #sendMessageClean(Player, String)
     */
    public static void sendMessageClean(OfflinePlayer player, String message){
        if (!player.isOnline()) return;
        sendMessageClean(player.getPlayer(), message);
    }

    /**
     * Sends a message with parsed {@link MiniMessage} tags and PAPI Placeholders to {@link Player}.
     *
     * @param player {@link Player} that will receive the message
     * @param message String that will be send to the player
     */
    public static void sendMessage(Player player, String message){
        BUKKIT_AUDIENCES.player(player).sendMessage(MiniMessage.get().parse(PapiUtil.setPlaceholders(player, message)));
    }

    /**
     * Sends a message with parsed PAPI Placeholders to {@link Player}.
     *
     * @param player {@link Player} that will receive the message
     * @param message String that will be send to the player
     */
    public static void sendMessageClean(Player player, String message){
        player.sendMessage(PapiUtil.setPlaceholders(player, message));
    }

    /**
     * Sends a message with parsed {@link MiniMessage} tags and PAPI Placeholders to {@link CommandSender}.
     *
     * @param target {@link CommandSender} that will receive the message
     * @param message String that will be send to the target
     *
     * @see #sendMessage(Player, String)
     */
    public static void sendMessage(CommandSender target, String message){
        if (target instanceof Player){
            sendMessage((Player) target, message);
            return;
        }
        BUKKIT_AUDIENCES.sender(target).sendMessage(MiniMessage.get().parse(message));
    }

    /**
     * Sends a message with parsed PAPI Placeholders to {@link CommandSender}.
     *
     * @param target {@link CommandSender} that will receive the message
     * @param message String that will be send to the target
     *
     * @see #sendMessageClean(Player, String)
     */
    public static void sendMessageClean(CommandSender target, String message){
        if (target instanceof Player){
            sendMessageClean((Player) target, message);
            return;
        }
        target.sendMessage(message);
    }

    /**
     * Broadcasts a message with parsed {@link MiniMessage} tags and PAPI Placeholders to either Bukkit or Bungee.
     *
     * @param message String that will be send to all Players and ConsoleSender
     * @param perm Permission that is required to receive the message
     * @param bungee Determines if message should be send to Bungee or not
     *
     * @see #sendMessage(Player, String)
     * @see #sendMessage(CommandSender, String)
     */
    public static void broadcast(String message, String perm, boolean bungee){
        if (bungee){
            BungeeUtil.broadcastMessage(message, perm);
        }else{
            for (Player player : Bukkit.getOnlinePlayers()){
                if (player.hasPermission(perm) || perm.equals("")){
                    sendMessage(player, message);
                }
            }
        }
        sendMessage(Bukkit.getConsoleSender(), message);
    }

    /**
     * Broadcasts a list of messages with parsed {@link MiniMessage} tags and PAPI Placeholders to either Bukkit or Bungee.
     *
     * @param messages ArrayList that will be send to all Players and ConsoleSender
     * @param perm Permission that is required to receive the messages
     * @param bungee Determines if messages should be send to Bungee or not
     *
     * @see #broadcast(String, String, boolean)
     */
    public static void broadcast(List<String> messages, String perm, boolean bungee){
        for (String line : messages){
            broadcast(line, perm, bungee);
        }
    }

    /**
     * Broadcasts a message with parsed PAPI Placeholders to either Bukkit or Bungee.
     *
     * @param message String that will be send to all Players and ConsoleSender
     * @param perm Permission that is required to receive the message
     * @param bungee Determines if message should be send to Bungee or not
     *
     * @see #sendMessage(Player, String)
     * @see #sendMessageClean(CommandSender, String)
     */
    public static void broadcastClean(String message, String perm, boolean bungee){
        if (bungee){
            BungeeUtil.broadcastMessage(message, perm);
        }else{
            for (Player player : Bukkit.getOnlinePlayers()){
                if (player.hasPermission(perm) || perm.equals("")){
                    sendMessageClean(player, message);
                }
            }
        }
        sendMessageClean(Bukkit.getConsoleSender(), message);
    }

    /**
     * Broadcasts a list of messages with parsed PAPI Placeholders to either Bukkit or Bungee.
     *
     * @param messages ArrayList that will be send to all Players and ConsoleSender
     * @param perm Permission that is required to receive the messages
     * @param bungee Determines if messages should be send to Bungee or not
     *
     * @see #broadcastClean(List, String, boolean)
     */
    public static void broadcastClean(List<String> messages, String perm, boolean bungee){
        for (String line : messages){
            broadcastClean(line, perm, bungee);
        }
    }
}

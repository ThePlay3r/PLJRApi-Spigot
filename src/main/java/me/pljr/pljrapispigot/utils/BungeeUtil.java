package me.pljr.pljrapispigot.utils;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.pljr.pljrapispigot.PLJRApiSpigot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class BungeeUtil {
    private final static PLJRApiSpigot PLJRAPI_SPIGOT = PLJRApiSpigot.get();
    /**
     * Sends a BungeeCord message to {@link Player}.
     *
     * @param receiver The name of {@link Player} to receive the message
     * @param message String that will be send to the receiver
     */
    public static void message(String receiver, String message){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("message");
        out.writeUTF(receiver);
        out.writeUTF(message);

        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        if (player == null) return;
        player.sendPluginMessage(PLJRAPI_SPIGOT, "pljrapi:chat", out.toByteArray());
    }

    public static void send(Player player, String server){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);

        player.sendPluginMessage(PLJRAPI_SPIGOT, "BungeeCord", out.toByteArray());
    }

    /**
     * Broadcasts message across BungeeCord to all Players with specified permission.
     *
     * @param message String that will be send to all Players with specified permission
     * @param perm Permission tha will be required to see the message
     */
    public static void broadcastMessage(String message, String perm){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("broadcast");
        out.writeUTF(perm);
        out.writeUTF(message);

        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        if (player == null) return;
        player.sendPluginMessage(PLJRAPI_SPIGOT, "pljrapi:chat", out.toByteArray());
    }
}

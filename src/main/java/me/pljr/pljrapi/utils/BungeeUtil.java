package me.pljr.pljrapi.utils;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.pljr.pljrapi.PLJRApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BungeeUtil {
    private final static PLJRApi instance = PLJRApi.getInstance();

    public static void message(String receiver, String message){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Message");
        out.writeUTF(receiver);
        out.writeUTF(message);

        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        if (player == null) return;
        player.sendPluginMessage(instance, "BungeeCord", out.toByteArray());
    }

    public static void broadcastMessage(String message){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Message");
        out.writeUTF("ALL");
        out.writeUTF(message);

        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        if (player == null) return;
        player.sendPluginMessage(instance, "BungeeCord", out.toByteArray());
    }
}

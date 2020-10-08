package me.pljr.pljrapi.utils;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.pljr.pljrapi.PLJRApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeeUtil implements PluginMessageListener {
    private final static PLJRApi instance = PLJRApi.getInstance();
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
        player.sendPluginMessage(instance, "pljrapi:chat", out.toByteArray());
    }

    public static void send(Player player, String server){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);

        player.sendPluginMessage(instance, "BungeeCord", out.toByteArray());
    }

    private static void receiveMessage(String receiver, String message){
        if (PlayerUtil.isPlayer(receiver)){
            Player player = Bukkit.getPlayer(receiver);
            ChatUtil.sendMessage(player, message);
        }
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
        player.sendPluginMessage(instance, "pljrapi:chat", out.toByteArray());
    }

    private static void receiveBroadcast(String message, String perm){
        ChatUtil.broadcast(message, perm, false);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (channel.equals("pljrapi:chat")){
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            String subChannel = in.readUTF();
            if (subChannel.equals("message")){
                receiveMessage(in.readUTF(), in.readUTF());
                return;
            }
            if (subChannel.equals("broadcast")){
                receiveMessage(in.readUTF(), in.readUTF());
            }
        }
    }
}

package me.pljr.pljrapispigot.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import me.pljr.pljrapispigot.utils.ChatUtil;
import me.pljr.pljrapispigot.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public class BungeeListeners implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, @NotNull Player player, byte[] message) {
        if (channel.equals("pljrapi:chat")){
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            String subChannel = in.readUTF();
            if (subChannel.equals("message")){
                receiveMessage(in.readUTF(), in.readUTF());
                return;
            }
            if (subChannel.equals("broadcast")){
                receiveBroadcast(in.readUTF(), in.readUTF());
            }
        }
    }

    private void receiveMessage(String receiver, String message){
        if (PlayerUtil.isPlayer(receiver)){
            Player player = Bukkit.getPlayer(receiver);
            ChatUtil.sendMessage(player, message);
        }
    }

    private void receiveBroadcast(String message, String perm){
        ChatUtil.broadcast(message, perm, false);
    }
}

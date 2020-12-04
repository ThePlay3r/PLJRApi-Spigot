package me.pljr.pljrapi.listeners;

import me.pljr.pljrapi.managers.QueryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {
    private final QueryManager queryManager;

    public PlayerJoinListener(QueryManager queryManager){
        this.queryManager = queryManager;
    }

    @EventHandler
    public void onQuit(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String playerName = player.getName();
        UUID playerId = player.getUniqueId();
        queryManager.savePlayerName(playerId, playerName);
    }
}

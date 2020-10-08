package me.pljr.pljrapi.managers;

import me.pljr.pljrapi.objects.PLJRScoreboard;
import me.pljr.pljrapi.utils.PapiUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardManager {
    /**
     * Sends {@link PLJRScoreboard} to {@link Player}
     *
     * @param player {@link Player} that should receive the scoreboard
     * @param scoreboard {@link PLJRScoreboard} that should be sent to player
     */
    public static void send(Player player, PLJRScoreboard scoreboard){
        List<String> linesWithPAPI = new ArrayList<>();
        for (String line : scoreboard.getLines()){
            linesWithPAPI.add(PapiUtil.setPlaceholders(player, line));
        }
        scoreboard.setLines(linesWithPAPI);
        player.setScoreboard(scoreboard.parseScoreboard());
    }

    /**
     * Broadcasts {@link PLJRScoreboard} to all players.
     *
     * @param scoreboard {@link PLJRScoreboard} that should be broadcasted
     *
     * @see #send(Player, PLJRScoreboard)
     */
    public static void broadcast(PLJRScoreboard scoreboard){
        for (Player player : Bukkit.getOnlinePlayers()){
            send(player, scoreboard);
        }
    }
}

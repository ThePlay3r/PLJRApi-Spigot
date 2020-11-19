package me.pljr.pljrapi.managers;

import me.pljr.pljrapi.builders.ScoreboardBuilder;
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
        ScoreboardBuilder builder = new ScoreboardBuilder(scoreboard);
        List<String> linesWithPAPI = new ArrayList<>();
        for (String line : builder.getLines()){
            linesWithPAPI.add(PapiUtil.setPlaceholders(player, line));
        }
        builder.withLines(linesWithPAPI);
        player.setScoreboard(builder.create().parseScoreboard());
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

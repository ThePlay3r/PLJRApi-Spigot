package me.pljr.pljrapispigot.objects;

import me.pljr.pljrapispigot.utils.ScoreboardUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

public class PLJRScoreboard {
    private final String title;
    private final List<String> lines;

    public PLJRScoreboard(PLJRScoreboard scoreboard){
        this.title = scoreboard.getTitle();
        this.lines = scoreboard.getLines();
    }

    public PLJRScoreboard(String title, List<String> lines){
        this.title = title;
        this.lines = lines;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getLines() {
        return lines;
    }

    public Scoreboard parseScoreboard(){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective(title, "");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(title);

        int slot = 16;
        for (String line : lines){
            Score score = objective.getScore(line);
            score.setScore(slot);
            slot--;
        }

        return scoreboard;
    }

    public void send(Player player){
        ScoreboardUtil.send(player, this);
    }

    public void broadcast(){
        ScoreboardUtil.broadcast(this);
    }
}

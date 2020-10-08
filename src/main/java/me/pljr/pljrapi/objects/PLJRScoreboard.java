package me.pljr.pljrapi.objects;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class PLJRScoreboard {
    private String title;
    private List<String> lines;

    public PLJRScoreboard(PLJRScoreboard scoreboard){
        this.title = scoreboard.getTitle();
        this.lines = scoreboard.getLines();
    }

    public PLJRScoreboard(String title){
        this.title = title;
        this.lines = new ArrayList<>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public List<String> getLines() {
        return lines;
    }

    public void replaceLines(String target, String input){
        List<String> replacedLines = new ArrayList<>();
        for (String line : lines){
            replacedLines.add(line.replace(target, input));
        }
        setLines(replacedLines);
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
}

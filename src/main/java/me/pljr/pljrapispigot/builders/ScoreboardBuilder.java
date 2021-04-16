package me.pljr.pljrapispigot.builders;

import me.pljr.pljrapispigot.objects.PLJRScoreboard;
import me.pljr.pljrapispigot.utils.FormatUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScoreboardBuilder {
    private String title;
    private List<String> lines;

    public String getTitle() {
        return title;
    }

    public List<String> getLines() {
        return lines;
    }

    /**
     * Creates a default, empty ScoreboardBuilder.
     */
    public ScoreboardBuilder(){
        this("", new ArrayList<>());
    }

    /**
     * Creates an ScoreboardBuilder with selected title.
     *
     * @param title {@link String} that will represent the title.
     */
    public ScoreboardBuilder(String title){
        this(title, new ArrayList<>());
    }

    /**
     * Creates an ScoreboardBuilder with selected title and lines.
     *
     * @param title {@link String} that will represent the title.
     * @param lines {@link String} that will represent the lines.
     */
    public ScoreboardBuilder(String title, String... lines){
        this(title, Arrays.asList(lines));
    }

    /**
     * Creates an ScoreboardBuilder with selected title and lines.
     *
     * @param title {@link String} that will represent the title.
     * @param lines {@link ArrayList} that will represent the lines.
     */
    public ScoreboardBuilder(String title, List<String> lines){
        this.title = title;
        this.lines = lines;
    }

    /**
     * Creates and ScoreboardBuilder from {@link PLJRScoreboard}.
     *
     * @param scoreboard {@link PLJRScoreboard} that will be copied.
     */
    public ScoreboardBuilder(PLJRScoreboard scoreboard){
        this.title = scoreboard.getTitle();
        this.lines = scoreboard.getLines();
    }

    /**
     * Changes the current title.
     *
     * @param title {@link String} that will represent the new title.
     * @return {@link ScoreboardBuilder} with changed title.
     */
    public ScoreboardBuilder withTitle(String title){
        this.title = title;
        return this;
    }

    /**
     * Changes the current lines.
     *
     * @param lines {@link ArrayList} that will represent the new title.
     * @return {@link ScoreboardBuilder} with changed lines.
     */
    public ScoreboardBuilder withLines(List<String> lines){
        this.lines = lines;
        return this;
    }

    /**
     * Changes the current lines.
     *
     * @param lines {@link String} that will represent the new title.
     * @return {@link ScoreboardBuilder} with changed lines.
     */
    public ScoreboardBuilder withLines(String... lines){
        this.lines = Arrays.asList(lines);
        return this;
    }

    /**
     * Replaces a String with another String in the lore.
     *
     * @param target {@link String} that should be replaced.
     * @param input {@link String} that the target should be replaced with.
     * @return {@link ScoreboardBuilder} with changed lines.
     */
    public ScoreboardBuilder replaceLines(String target, String input){
        List<String> replacedLines = new ArrayList<>();
        for (String line : lines){
            replacedLines.add(line.replace(target, input));
        }
        this.lines = replacedLines;
        return this;
    }

    /**
     * Creates PLJRScoreboard from selected values.
     *
     * @return {@link PLJRScoreboard} consisting of all previously selected values.
     */
    public PLJRScoreboard create(){
        List<String> coloredLines = new ArrayList<>();
        lines.forEach(line -> coloredLines.add(FormatUtil.colorString(line)));
        return new PLJRScoreboard(FormatUtil.colorString(title), coloredLines);
    }
}

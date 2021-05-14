package me.pljr.pljrapispigot.scoreboard

import me.pljr.pljrapispigot.util.colorString

class ScoreboardBuilder(var title: String, var lines: MutableList<String>) {

    constructor() : this("", ArrayList())

    constructor(title: String) : this(title, ArrayList())

    constructor(title: String, vararg lines: String) : this(title, lines.toMutableList())

    constructor(scoreboard: PLJRScoreboard) : this(scoreboard.title, scoreboard.lines)

    /**
     * Changes the current title.
     *
     * @param title [String] that will represent the new title.
     * @return [ScoreboardBuilder] with changed title.
     */
    fun withTitle(title: String): ScoreboardBuilder {
        this.title = title
        return this
    }

    /**
     * Changes the current lines.
     *
     * @param lines [ArrayList] that will represent the new title.
     * @return [ScoreboardBuilder] with changed lines.
     */
    fun withLines(lines: MutableList<String>): ScoreboardBuilder {
        this.lines = lines
        return this
    }

    /**
     * Changes the current lines.
     *
     * @param lines [String] that will represent the new title.
     * @return [ScoreboardBuilder] with changed lines.
     */
    fun withLines(vararg lines: String): ScoreboardBuilder {
        this.lines = lines.toMutableList()
        return this
    }

    /**
     * Replaces a String with another String in the lore.
     *
     * @param target [String] that should be replaced.
     * @param input [String] that the target should be replaced with.
     * @return [ScoreboardBuilder] with changed lines.
     */
    fun replaceLines(target: String, input: String): ScoreboardBuilder {
        val replacedLines: MutableList<String> = ArrayList()
        lines.forEach { replacedLines.add(it.replace(target, input)) }
        lines = replacedLines
        return this
    }

    /**
     * Creates PLJRScoreboard from selected values.
     *
     * @return [PLJRScoreboard] consisting of all previously selected values.
     */
    fun create(): PLJRScoreboard {
        val coloredLines: MutableList<String> = java.util.ArrayList()
        lines.forEach { coloredLines.add(colorString(it)) }
        return PLJRScoreboard(colorString(title), coloredLines)
    }
}
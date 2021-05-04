package me.pljr.pljrapispigot.title

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextReplacementConfig


class TitleBuilder(var title: Component, var subtitle: Component, var inTime: Long, var stayTime: Long, var outTime: Long) {

    /**
     * Creates a new default, empty TitleBuilder.
     */
    constructor() : this(Component.text(""), Component.text(""), 20, 40, 20)

    /**
     * Creates an TitleBuilder with custom title.
     *
     * @param title [Component] that will represent the title.
     */
    constructor(title: Component) : this(title, Component.text(""), 20, 40, 20)

    @Deprecated("Use Adventure API's Component instead of String.",
        ReplaceWith("TitleBuilder(Component.text(title))", "net.kyori.adventure.text.Component"))
    constructor(title: String) : this(Component.text(title))

    /**
     * Creates an TitleBuilder with custom title and subtitle.
     *
     * @param title [Component] that will represent the tile
     * @param subtitle [Component] that will represent the subtitle.
     */
    constructor(title: Component, subtitle: Component) : this(title, subtitle, 20, 40, 20)

    @Deprecated("Use Adventure API's Component instead of String.",
        ReplaceWith("TitleBuilder(Component.text(title), Component.text(subtitle))", "net.kyori.adventure.text.Component"))
    constructor(title: String, subtitle: String) : this(Component.text(title), Component.text(subtitle))

    /**
     * Creates an TitleBuilder from [PLJRTitle].
     *
     * @param title [PLJRTitle] that will be copied.
     */
    constructor(title: PLJRTitle) : this(title.title, title.subtitle, title.inTime, title.stayTime, title.outTime)

    /**
     * Changes current title.
     *
     * @param title [Component] that will represent the new title.
     * @return [TitleBuilder] with changed title.
     */
    fun withTitle(title: Component): TitleBuilder {
        this.title = title
        return this
    }

    @Deprecated("Use Adventure API's Component instead of String.",
        ReplaceWith("withTitle(Component.text(title))", "net.kyori.adventure.text.Component"))
    fun withTitle(title: String) = withTitle(Component.text(title))

    /**
     * Replaces a String with another String in the title.
     *
     * @param target [String] that should be replaced.
     * @param replacement [String] that the target should be replaced with.
     * @return [TitleBuilder] with changed lore.
     */
    fun replaceTitle(target: String, replacement: String): TitleBuilder {
        title = title.replaceText(TextReplacementConfig.builder().matchLiteral(target).replacement(replacement).build())
        return this
    }

    /**
     * Changes current subtitle.
     *
     * @param subtitle [Component] that will represent the new subtitle.
     * @return [TitleBuilder] with changed subtitle.
     */
    fun withSubtitle(subtitle: Component): TitleBuilder {
        this.subtitle = subtitle
        return this
    }

    @Deprecated("Use Adventure API's Component instead of String.",
        ReplaceWith("withSubtitle(Component.text(subtitle))", "net.kyori.adventure.text.Component"))
    fun withSubtitle(subtitle: String) = withSubtitle(Component.text(subtitle))

    /**
     * Replaces a String with another String in the subtitle.
     *
     * @param target [String] that should be replaced.
     * @param replacement [String] that the target should be replaced with.
     * @return [TitleBuilder] with changed lore.
     */
    fun replaceSubtitle(target: String, replacement: String): TitleBuilder {
        subtitle = subtitle.replaceText(TextReplacementConfig.builder().matchLiteral(target).replacement(replacement).build())
        return this
    }

    /**
     * Changes the times of the title.
     *
     * @param inTime int that will represent the time title will take to show up.
     * @param stayTime int that will represent the time title will be shown after showing up.
     * @param outTime int that will represent how long the title will take to fully disappear.
     * @return [TitleBuilder] with changed times.
     */
    fun withTimes(inTime: Long, stayTime: Long, outTime: Long): TitleBuilder {
        this.inTime = inTime
        this.stayTime = stayTime
        this.outTime = outTime
        return this
    }

    /**
     * Creates a new [PLJRTitle] from previously defined values.
     *
     * @return [PLJRTitle] with previously defined values.
     */
    fun create(): PLJRTitle {
        return PLJRTitle(title, subtitle, inTime, stayTime, outTime)
    }
}
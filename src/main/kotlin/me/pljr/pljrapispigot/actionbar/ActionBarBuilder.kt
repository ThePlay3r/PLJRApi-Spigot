package me.pljr.pljrapispigot.actionbar

import me.pljr.pljrapispigot.util.colorString

class ActionBarBuilder(var message: String, var duration: Long) {

    /**
     * Creates a default, empty ActionBarBuilder.
     */
    constructor() : this("", 20)

    /**
     * Creates an ActionBarBuilder, with selected message.
     *
     * @param message [String] that will represent the message.
     */
    constructor(message: String) : this(message, 20)

    /**
     * Changes the current message.
     *
     * @param message [String] that will represent the new message.
     * @return [ActionBarBuilder] with changed message.
     */
    fun withMessage(message: String): ActionBarBuilder {
        this.message = message
        return this
    }

    /**
     * Replaces a String with another String in the message.
     *
     * @param target [String] that should be replaced.
     * @param replacement [String] that the target should be replaced with.
     * @return [ActionBarBuilder] with changed lore.
     */
    fun replaceMessage(target: String, replacement: String): ActionBarBuilder {
        message = message.replace(target, replacement)
        return this
    }

    /**
     * Changes the current duration.
     *
     * @param duration long that will represent the new duration.
     * @return [ActionBarBuilder] with changed duration.
     */
    fun withDuration(duration: Long): ActionBarBuilder {
        this.duration = duration
        return this
    }

    /**
     * Creates [PLJRActionBar] from selected values.
     *
     * @return [PLJRActionBar] consisting of all previously selected values.
     */
    fun create(): PLJRActionBar {
        return PLJRActionBar(colorString(message), duration)
    }
}
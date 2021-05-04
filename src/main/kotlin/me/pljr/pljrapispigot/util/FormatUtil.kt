package me.pljr.pljrapispigot.util

import me.pljr.pljrapispigot.config.configuration.Lang
import org.bukkit.ChatColor
import java.util.*
import kotlin.math.roundToInt

/**
 * Applies default [ChatColor] codes to String and returns it.
 *
 * @param string String that should get default ChatColor applied
 * @return Colored string
 */
fun colorString(string: String): String {
    return ChatColor.translateAlternateColorCodes('&', string)
}

/**
 * Formats a time in seconds to better looking format, set in configuration.
 *
 * @param sec Time that should be formatted
 * @return Formatted time in String
 */
fun formatTime(sec: Long): String {
    val seconds = sec % 60
    var minutes = sec / 60
    if (minutes >= 60) {
        val hours = minutes / 60
        minutes %= 60
        if (hours >= 24) {
            val days = hours / 24
            return String.format(Lang.TIME_FORMAT_DAYS.get(), days, hours % 24, minutes, seconds)
        }
        return String.format(Lang.TIME_FORMAT_HOURS.get(), hours, minutes, seconds)
    }
    return String.format(Lang.TIME_FORMAT_MINUTES.get(), minutes, seconds)
}

/**
 * Randomly scrambles String.
 *
 * @param inputString String that should be scrambled
 * @return Scrambled inputString
 */
fun scrambleString(inputString: String): String {
    val a = inputString.toCharArray()
    for (i in a.indices) {
        val j = Random().nextInt(a.size)
        val temp = a[i]
        a[i] = a[j]
        a[j] = temp
    }
    return String(a)
}

/**
 * Returns a [String] as a progress bar.
 *
 * @param current Current amount of progress.
 * @param max Maximum amount of progress.
 * @param symbol Symbol, that will represent the bar.
 * @param lockedColor Color of unfinished progress.
 * @param unlockedColor Color of finished progress.
 * @return [String] that will represent the progress bar.
 */
fun getProgressBar(current: Float, max: Float, symbol: String, lockedColor: String, unlockedColor: String): String {
    val onePercent = max / 100
    val percent = current / onePercent
    val unlocked = (percent / 10).roundToInt()
    val progress = StringBuilder()
    for (i in 1..10) {
        progress.append(symbol)
    }
    progress.insert(unlocked, lockedColor)
    progress.insert(0, unlockedColor)
    return colorString(progress.toString())
}
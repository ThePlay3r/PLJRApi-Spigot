package me.pljr.pljrapispigot.utils;

import me.pljr.pljrapispigot.config.Lang;
import org.bukkit.ChatColor;

import java.util.Random;

public class FormatUtil {
    /**
     * Applies default {@link ChatColor} codes to String and returns it.
     *
     * @param string String that should get default ChatColor applied
     * @return Colored string
     */
    public static String colorString(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Formats a time in seconds to better looking format, set in configuration.
     *
     * @param sec Time that should be formatted
     * @return Formatted time in String
     */
    public static String formatTime(long sec) {
        long seconds = sec % 60;
        long minutes = sec / 60;
        if (minutes >= 60) {
            long hours = minutes / 60;
            minutes %= 60;
            if (hours >= 24) {
                long days = hours / 24;
                return String.format(Lang.TIME_FORMAT_DAYS.get(), days, hours % 24, minutes, seconds);
            }
            return String.format(Lang.TIME_FORMAT_HOURS.get(), hours, minutes, seconds);
        }
        return String.format(Lang.TIME_FORMAT_MINUTES.get(), minutes, seconds);
    }

    /**
     * Randomly scrambles String.
     *
     * @param inputString String that should be scrambled
     * @return Scrambled inputString
     */
    public static String scramble(String inputString) {
        char[] a = inputString.toCharArray();

        for(int i=0 ; i<a.length ; i++) {
            int j = new Random().nextInt(a.length);
            char temp = a[i]; a[i] = a[j];  a[j] = temp;
        }

        return new String(a);
    }

    /**
     * Returns a {@link String} as a progress bar.
     *
     * @param current Current amount of progress.
     * @param max Maximum amount of progress.
     * @param symbol Symbol, that will represent the bar.
     * @param lockedColor Color of unfinished progress.
     * @param unlockedColor Color of finished progress.
     * @return {@link String} that will represent the progress bar.
     */
    public static String getProgressBar(float current, float max, String symbol, String lockedColor, String unlockedColor){
        float onePercent = max / 100;
        float percent = current / onePercent;
        int unlocked = Math.round(percent / 10);

        StringBuilder progress = new StringBuilder();
        for (int i = 1; i<=10;i++){
            progress.append(symbol);
        }
        progress.insert(unlocked, lockedColor);
        progress.insert(0, unlockedColor);
        return colorString(progress.toString());
    }
}

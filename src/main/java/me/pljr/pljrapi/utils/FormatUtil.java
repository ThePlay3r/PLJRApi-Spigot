package me.pljr.pljrapi.utils;

import org.bukkit.ChatColor;

import java.util.Random;

public class FormatUtil {
    public static String colorString(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String formatTime(long sec) {
        long seconds = sec % 60;
        long minutes = sec / 60;
        if (minutes >= 60) {
            long hours = minutes / 60;
            minutes %= 60;
            if (hours >= 24) {
                long days = hours / 24;
                return String.format("§b%d d, §b%02d h. §b%02d m. §b%02d s.", days, hours % 24, minutes, seconds);
            }
            return String.format("§b%02d h. §b%02d m. §b%02d s.", hours, minutes, seconds);
        }
        return String.format("§b00 h. §b%02d m. §b%02d s.", minutes, seconds);
    }

    public static String scramble(String inputString) {
        char[] a = inputString.toCharArray();

        for(int i=0 ; i<a.length ; i++) {
            int j = new Random().nextInt(a.length);
            char temp = a[i]; a[i] = a[j];  a[j] = temp;
        }

        return new String(a);
    }
}

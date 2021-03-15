package me.pljr.pljrapispigot.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public final class MiniMessageUtil {
    /**
     * Parses a string into an {@link MiniMessage} {@link Component}.
     *
     * @param input the input string
     * @return the output component
     */
    public static Component parse(String input){
        return MiniMessage.get().parse(input);
    }

    /**
     * Removes all {@link MiniMessage} tokens in the input message.
     *
     * @param input the input message, with tokens
     * @return the output, without tokens
     */
    public static String strip(String input){
        return MiniMessage.get().stripTokens(input);
    }
}

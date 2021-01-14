package me.pljr.pljrapispigot.exceptions;

import org.bukkit.entity.Player;

public class NoHeldItemException extends Exception {
    private final Player source;

    public NoHeldItemException(Player source){
        this.source = source;
    }

    public Player getSource() {
        return source;
    }
}

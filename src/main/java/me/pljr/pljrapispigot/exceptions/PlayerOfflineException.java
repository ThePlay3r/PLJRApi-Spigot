package me.pljr.pljrapispigot.exceptions;

import java.util.UUID;

public class PlayerOfflineException extends Exception {
    private final UUID source;

    public PlayerOfflineException(UUID source){
        this.source = source;
    }

    public UUID getSource() {
        return source;
    }
}

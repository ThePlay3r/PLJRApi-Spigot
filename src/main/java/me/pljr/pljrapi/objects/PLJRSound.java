package me.pljr.pljrapi.objects;

import org.bukkit.Sound;

public class PLJRSound {
    private final Sound type;
    private final int volume;
    private final int pitch;

    public PLJRSound(Sound type, int volume, int pitch){
        this.type = type;
        this.volume = volume;
        this.pitch = pitch;
    }

    public Sound getType() {
        return type;
    }

    public int getVolume() {
        return volume;
    }

    public int getPitch() {
        return pitch;
    }
}

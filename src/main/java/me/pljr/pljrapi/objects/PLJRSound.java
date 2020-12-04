package me.pljr.pljrapi.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PLJRSound {
    private final Sound type;
    private final float volume;
    private final float pitch;

    public PLJRSound(Sound type, float volume, float pitch){
        this.type = type;
        this.volume = volume;
        this.pitch = pitch;
    }

    public Sound getType() {
        return type;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

    public void play(Player player){
        Location playerLoc = player.getLocation();
        player.playSound(playerLoc, type, volume, pitch);
    }

    public void broadcast(){
        for (Player player : Bukkit.getOnlinePlayers()){
            play(player);
        }
    }
}

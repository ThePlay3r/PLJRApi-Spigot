package me.pljr.pljrapi.utils;

import org.bukkit.Location;
import xyz.xenondevs.particle.ParticleEffect;

public class ParticleUtil {

    public static void spawn(String particle, Location location, int amount, float offsetX, float offsetY, float offsetZ){
        ParticleEffect.valueOf(particle).display(location, offsetX, offsetY, offsetZ, 1, amount, null);
    }
}

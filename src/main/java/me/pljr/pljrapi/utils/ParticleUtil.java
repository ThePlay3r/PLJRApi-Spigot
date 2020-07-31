package me.pljr.pljrapi.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import xyz.xenondevs.particle.ParticleEffect;

public class ParticleUtil {

    public static void spawn(Particle particle, Location location, int amount, float offsetX, float offsetY, float offsetZ){
        ParticleEffect.valueOf(particle.toString()).display(location, offsetX, offsetY, offsetZ, 1, amount, null);
    }
}

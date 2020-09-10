package me.pljr.pljrapi.utils;

import org.bukkit.Location;
import xyz.xenondevs.particle.ParticleEffect;

public class ParticleUtil {
    /**
     * Spawns/Displays a {@link ParticleEffect}
     *
     * @param particle Name of the particle
     * @param location {@link Location} of the spawned particle
     * @param amount Amount of particles that should be spawned
     * @param offsetX X axis offset
     * @param offsetY Y axis offset
     * @param offsetZ Z axis offset
     */
    public static void spawn(String particle, Location location, int amount, float offsetX, float offsetY, float offsetZ){
        ParticleEffect.valueOf(particle).display(location, offsetX, offsetY, offsetZ, 1, amount, null);
    }
}

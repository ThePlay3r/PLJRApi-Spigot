package me.pljr.pljrapispigot.utils;

import me.pljr.pljrapispigot.PLJRApiSpigot;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.entity.Player;

public final class BossBarUtil {
    private static final BukkitAudiences BUKKIT_AUDIENCES = PLJRApiSpigot.get().getBukkitAudiences();

    /**
     * Shows {@link BossBar} to {@link Player}
     *
     * @param player {@link Player} that should receive the bossBar
     * @param bossBar {@link BossBar} that should be shown to player
     */
    public static void show(Player player, BossBar bossBar){
        BUKKIT_AUDIENCES.player(player).showBossBar(bossBar);
    }
}

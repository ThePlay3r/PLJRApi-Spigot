package me.pljr.pljrapi.utils;

import me.pljr.pljrapi.PLJRApi;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.entity.Player;

public class BossBarUtil {

    /**
     * Shows {@link BossBar} to {@link Player}
     *
     * @param player {@link Player} that should receive the bossBar
     * @param bossBar {@link BossBar} that should be shown to player
     */
    public static void show(Player player, BossBar bossBar){
        PLJRApi.getBukkitAudiences().player(player).showBossBar(bossBar);
    }
}

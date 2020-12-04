package me.pljr.pljrapi.utils;

import me.pljr.pljrapi.PLJRApi;
import me.pljr.pljrapi.builders.TitleBuilder;
import me.pljr.pljrapi.config.CfgLang;
import me.pljr.pljrapi.config.CfgSettings;
import me.pljr.pljrapi.config.CfgSounds;
import me.pljr.pljrapi.enums.Sounds;
import me.pljr.pljrapi.managers.QueryManager;
import me.pljr.pljrapi.managers.TitleManager;
import me.pljr.pljrapi.objects.PLJRTitle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PlayerUtil {
    /**
     * Checks if input belongs to a username of {@link Player}
     *
     * @param input Username of {@link Player}
     * @return True if there is a {@link Player} with this username, false if otherwise
     */
    public static boolean isPlayer(String input){
        Player player = Bukkit.getPlayer(input);
        return player != null && player.isOnline();
    }

    /**
     * Checks if input belongs to a UUID of {@link Player}
     *
     * @param input UUID of {@link Player}
     * @return True if there is a {@link Player} with this UUID, false if otherwise
     */
    public static boolean isPlayer(UUID input){
        Player player = Bukkit.getPlayer(input);
        return player != null && player.isOnline();
    }

    /**
     * Will try to get a username of {@link OfflinePlayer}
     *
     * @param offlinePlayer {@link OfflinePlayer} that we will try to get the username of
     * @return Username of offlinePlayer, "?" otherwise
     */
    public static String getName(OfflinePlayer offlinePlayer){
        QueryManager queryManager = PLJRApi.getQueryManager();

        UUID playerId = offlinePlayer.getUniqueId();

        if (offlinePlayer.isOnline()){
            queryManager.savePlayerName(playerId, offlinePlayer.getName());
            return offlinePlayer.getName();
        }

        String name = queryManager.getPlayerName(playerId);
        if (name == null){
            return  "?";
        }
        return name;
    }

    /**
     * Will teleport {@link Player} to another {@link Player} using {@link #teleport(Player, Location, boolean)}.
     *
     * @param player {@link Player} that will be teleported to target
     * @param target {@link Player} that whose location will be set as the destination
     * @param delay Determines if the teleportation should be immediate or delayed
     *
     * @see #teleport(Player, Location, boolean)
     */
    public static void teleport(Player player, Player target, boolean delay){
        teleport(player, target.getLocation(), delay);
    }

    /**
     * Will teleport {@link Player} to {@link Location} with countdown displayed by {@link PLJRTitle},
     * using {@link TitleManager}.
     *
     * @param player {@link Player} that should be teleported
     * @param location {@link Location} destination player should be teleported to
     * @param delay Determines if the teleportation should be immediate or delayed
     *
     * @see PLJRTitle
     * @see TitleManager
     */
    public static void teleport(Player player, Location location, boolean delay){
        if (player.hasPermission("pljrapi.teleport.bypass")){
            player.teleport(location);
            player.playSound(player.getLocation(), CfgSounds.sounds.get(Sounds.TELEPORT_TP), 1, 1);
            TitleManager.send(player, CfgLang.teleportTitleTp);
            return;
        }
        final int countdown;
        if (delay){
            countdown = CfgSettings.teleportDelay;
        }else{
            TitleManager.send(player, CfgLang.teleportTitleTp);
            player.teleport(location);
            player.playSound(player.getLocation(), CfgSounds.sounds.get(Sounds.TELEPORT_TP), 1, 1);
            return;
        }
        new BukkitRunnable() {
            int finalCountdown = countdown;
            final Location pLoc = player.getLocation();
            final double x = pLoc.getX();
            final double z = pLoc.getZ();
            @Override
            public void run() {
                Location currentLoc = player.getLocation();
                double currentX = currentLoc.getX();
                double currentZ = currentLoc.getZ();
                // Fail (Player moved)
                if (currentX != x || currentZ != z){
                    TitleManager.send(player, new TitleBuilder(CfgLang.teleportTitleFail)
                            .replaceTitle("%time", finalCountdown+"")
                            .replaceSubtitle("%time", finalCountdown+"")
                            .create());
                    player.playSound(player.getLocation(), CfgSounds.sounds.get(Sounds.TELEPORT_FAIL), 1, 1);
                    cancel();
                    return;
                }
                // Success (Teleporting Player)
                if (finalCountdown <= 0){
                    TitleManager.send(player, new TitleBuilder(CfgLang.teleportTitleTp)
                            .replaceTitle("%time", finalCountdown+"")
                            .replaceSubtitle("%time", finalCountdown+"")
                            .create());
                    player.playSound(player.getLocation(), CfgSounds.sounds.get(Sounds.TELEPORT_TP), 1, 1);
                    Bukkit.getScheduler().runTask(PLJRApi.getInstance(), ()-> player.teleport(location));
                    cancel();
                    return;
                }
                // Ticking (Waiting to be teleported)
                TitleManager.send(player, new TitleBuilder(CfgLang.teleportTitleTick)
                        .replaceTitle("%time", finalCountdown+"")
                        .replaceSubtitle("%time", finalCountdown+"")
                        .create());
                player.playSound(player.getLocation(), CfgSounds.sounds.get(Sounds.TELEPORT_TICK), 1, 1);
                finalCountdown--;
            }
        }.runTaskTimerAsynchronously(PLJRApi.getInstance(), 0, 20);
    }

    /**
     * Will try to give an {@link ItemStack} to inventory of {@link Player}, drops the item at target's location
     * if there is no enough space.
     *
     * @param target {@link Player} that should receive the itemStack
     * @param item {@link ItemStack} that should be given to target
     */
    public static void give(Player target, ItemStack item){
        if (target.getInventory().firstEmpty() == -1){
            target.getWorld().dropItem(target.getLocation(), item);
        }else{
            target.getInventory().addItem(item);
        }
    }
}

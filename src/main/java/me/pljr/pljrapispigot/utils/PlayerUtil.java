package me.pljr.pljrapispigot.utils;

import me.pljr.pljrapispigot.PLJRApiSpigot;
import me.pljr.pljrapispigot.builders.TitleBuilder;
import me.pljr.pljrapispigot.config.Settings;
import me.pljr.pljrapispigot.config.SoundType;
import me.pljr.pljrapispigot.config.TitleType;
import me.pljr.pljrapispigot.managers.QueryManager;
import me.pljr.pljrapispigot.objects.PLJRTitle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public final class PlayerUtil {
    private static final Settings SETTINGS = PLJRApiSpigot.get().getSettings();
    private static final QueryManager QUERYMANAGER = PLJRApiSpigot.get().getQueryManager();

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
     *
     * @see #getName(UUID)
     */
    public static String getName(OfflinePlayer offlinePlayer){
        if (offlinePlayer.isOnline()){
            return offlinePlayer.getName();
        }
        return getName(offlinePlayer.getUniqueId());
    }

    /**
     * Will try to get a username of Player, from {@link UUID}
     *
     * @param uuid {@link UUID} of Player.
     * @return Username of player, "?" otherwise
     */
    public static String getName(UUID uuid){
        String name = QUERYMANAGER.getPlayerName(uuid);
        if (name == null){
            return "?";
        }
        return name;
    }

    /**
     * Will teleport {@link Player} to another {@link Player} using {@link #teleport(Player, Location)}.
     *
     * @param player {@link Player} that will be teleported to target
     * @param target {@link Player} that whose location will be set as the destination
     */
    public static void teleport(Player player, Player target){
        teleport(player, target.getLocation());
    }

    /**
     * Will teleport {@link Player} to {@link Location} with countdown displayed by {@link PLJRTitle}.
     *
     * @param player {@link Player} that should be teleported
     * @param location {@link Location} destination player should be teleported to
     *
     * @see PLJRTitle
     */
    public static void teleport(Player player, Location location){
        if (player.hasPermission("pljrapi.teleport.bypass")){
            player.teleport(location);
            SoundType.TELEPORT_TP.get().play(player);
            TitleType.TELEPORT_TELEPORT.get().send(player);
            return;
        }
        final int countdown = SETTINGS.getTeleportDelay();
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
                    new TitleBuilder(TitleType.TELEPORT_FAIL.get())
                            .replaceTitle("{time}", finalCountdown+"")
                            .replaceSubtitle("{time}", finalCountdown+"")
                            .create().send(player);
                    SoundType.TELEPORT_FAIL.get().play(player);
                    cancel();
                    return;
                }
                // Success (Teleporting Player)
                if (finalCountdown <= 0){
                    new TitleBuilder(TitleType.TELEPORT_TELEPORT.get())
                            .replaceTitle("{time}", finalCountdown+"")
                            .replaceSubtitle("{time}", finalCountdown+"")
                            .create().send(player);
                    SoundType.TELEPORT_TP.get().play(player);
                    Bukkit.getScheduler().runTask(PLJRApiSpigot.get(), ()-> player.teleport(location));
                    cancel();
                    return;
                }
                // Ticking (Waiting to be teleported)
                new TitleBuilder(TitleType.TELEPORT_TICKING.get())
                        .replaceTitle("{time}", finalCountdown+"")
                        .replaceSubtitle("{time}", finalCountdown+"")
                        .create().send(player);
                SoundType.TELEPORT_TICK.get().play(player);
                finalCountdown--;
            }
        }.runTaskTimerAsynchronously(PLJRApiSpigot.get(), 0, 20);
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

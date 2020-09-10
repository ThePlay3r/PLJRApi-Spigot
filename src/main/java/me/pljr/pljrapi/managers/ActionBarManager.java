package me.pljr.pljrapi.managers;

import com.cryptomorin.xseries.messages.ActionBar;
import me.pljr.pljrapi.PLJRApi;
import me.pljr.pljrapi.objects.PLJRActionBar;
import org.bukkit.entity.Player;

public class ActionBarManager {
    private static final PLJRApi pljrApi = PLJRApi.getInstance();

    /**
     * Will send a {@link PLJRActionBar} to {@link Player} using {@link ActionBar}.
     *
     * @param player {@link Player} that should receive the actionBar
     * @param actionBar {@link PLJRActionBar} that should be send to player
     */
    public static void send(Player player, PLJRActionBar actionBar){
        ActionBar.sendActionBar(pljrApi, player, actionBar.getMessage(), actionBar.getDuration());
    }

    /**
     * Will broadcast a {@link PLJRActionBar} to all online players using {@link ActionBar}.
     *
     * @param actionBar {@link PLJRActionBar} that should be broadcasts
     */
    public static void broadcast(PLJRActionBar actionBar){
        ActionBar.sendPlayersActionBar(actionBar.getMessage());
    }

    /**
     * Clears an ActionBar of {@link Player} using {@link ActionBar}.
     *
     * @param player {@link Player} that should have the ActionBar cleared
     */
    public static void clear(Player player){
        ActionBar.clearActionBar(player);
    }

    /**
     * Clears an ActionBar of all online player using {@link ActionBar}
     */
    public static void clearAll(){
        ActionBar.clearPlayersActionBar();
    }
}

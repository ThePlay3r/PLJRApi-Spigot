package me.pljr.pljrapi.managers;

import com.cryptomorin.xseries.messages.ActionBar;
import me.pljr.pljrapi.PLJRApi;
import me.pljr.pljrapi.objects.PLJRActionBar;
import org.bukkit.entity.Player;

public class ActionBarManager {
    private static final PLJRApi pljrApi = PLJRApi.getInstance();

    public static void send(Player player, PLJRActionBar actionBar){
        ActionBar.sendActionBar(pljrApi, player, actionBar.getMessage(), actionBar.getDuration());
    }

    public static void broadcast(PLJRActionBar actionBar){
        ActionBar.sendPlayersActionBar(actionBar.getMessage());
    }

    public static void clear(Player player){
        ActionBar.clearActionBar(player);
    }

    public static void clearAll(){
        ActionBar.clearPlayersActionBar();
    }
}

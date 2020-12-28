package me.pljr.pljrapispigot.managers;

import com.cryptomorin.xseries.messages.Titles;
import me.pljr.pljrapispigot.objects.PLJRTitle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TitleManager {
    /**
     * Will send a {@link PLJRTitle} to {@link Player} using {@link Titles}.
     *
     * @param player {@link Player} that should receive the title
     * @param title {@link PLJRTitle} that should be send to player
     */
    public static void send(Player player, PLJRTitle title){
        Titles.sendTitle(player, title.getIn(), title.getStay(), title.getOut(), title.getTitle(), title.getSubtitle());
    }

    /**
     * Will broadcast a {@link PLJRTitle} to all online players.
     *
     * @param title {@link PLJRTitle} that should be broadcasts
     *
     * @see #send(Player, PLJRTitle)
     */
    public static void broadcast(PLJRTitle title){
        for (Player player : Bukkit.getOnlinePlayers()){
            send(player, title);
        }
    }

    /**
     * Clears a Title of {@link Player} using {@link Titles}.
     *
     * @param player {@link Player} that should get the Title cleared
     */
    public static void clear(Player player){
        Titles.clearTitle(player);
    }
}

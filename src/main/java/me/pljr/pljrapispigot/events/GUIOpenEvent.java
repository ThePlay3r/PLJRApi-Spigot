package me.pljr.pljrapispigot.events;

import me.pljr.pljrapispigot.objects.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GUIOpenEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    private final Player player;
    private final GUI gui;

    public GUIOpenEvent(Player player, GUI gui){
        this.player = player;
        this.gui = gui;
    }

    public Player getPlayer() {
        return player;
    }

    public GUI getGui() {
        return gui;
    }
}

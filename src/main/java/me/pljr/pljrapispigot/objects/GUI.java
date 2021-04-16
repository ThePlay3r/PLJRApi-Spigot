package me.pljr.pljrapispigot.objects;

import me.pljr.pljrapispigot.events.GUIOpenEvent;
import me.pljr.pljrapispigot.managers.GUIManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;

public class GUI {
    private final PluginManager pluginManager;
    private final Inventory inventory;
    private final HashMap<Integer, GUIManager.ClickRunnable> items;
    private final GUI onClose;

    public Inventory getInventory() {
        return inventory;
    }

    public HashMap<Integer, GUIManager.ClickRunnable> getItems() {
        return items;
    }

    public GUI getOnClose() {
        return onClose;
    }

    public GUI(Inventory inventory, HashMap<Integer, GUIManager.ClickRunnable> items, GUI onClose){
        this.pluginManager = Bukkit.getPluginManager();
        this.inventory = inventory;
        this.items = items;
        this.onClose = onClose;
    }

    public GUI(Inventory inventory, HashMap<Integer, GUIManager.ClickRunnable> items){
        this.pluginManager = Bukkit.getPluginManager();
        this.inventory = inventory;
        this.items = items;
        this.onClose = null;
    }

    public GUI(GUI gui){
        this(gui.getInventory(), gui.getItems(), gui.getOnClose());
    }

    public void open(Player player){
        pluginManager.callEvent(new GUIOpenEvent(player, this));
    }
}

package me.pljr.pljrapispigot.managers;

import me.pljr.pljrapispigot.events.GUIOpenEvent;
import me.pljr.pljrapispigot.objects.GUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUIManager implements Listener {
    private final JavaPlugin plugin;
    private final HashMap<UUID, GUI> guis;

    public GUIManager(JavaPlugin plugin){
        this.plugin = plugin;
        guis = new HashMap<>();
    }

    /**
     * Opens a {@link GUI} to {@link Player}
     *
     * @param player {@link Player} that will be opened with the gui.
     * @param gui {@link GUI} that will be opened to player.
     */
    public void open(Player player, GUI gui){
        UUID playerId = player.getUniqueId();
        Inventory inventory = gui.getInventory();

        player.openInventory(inventory);
        guis.put(playerId, gui);
    }

    /**
     * Closes all currently opened guis.
     */
    public void closeAll(){
        for (Map.Entry<UUID, GUI> entry : guis.entrySet()){
            UUID playerId = entry.getKey();
            Player player = Bukkit.getPlayer(playerId);
            if (player != null && player.isOnline()){
                player.closeInventory();
            }
            guis.remove(playerId);
        }
    }

    @EventHandler
    private void onOpen(GUIOpenEvent event){
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        GUI gui = event.getGui();
        Inventory inventory = gui.getInventory();

        player.openInventory(inventory);
        guis.put(playerId, gui);
    }

    @EventHandler
    private void onClose(InventoryCloseEvent event){
        if (!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();
        UUID playerId = player.getUniqueId();
        if (!guis.containsKey(playerId)) return;
        if (guis.get(playerId).getOnClose() != null){
            Bukkit.getScheduler().runTaskLater(plugin, () -> open(player, guis.get(playerId).getOnClose()), 1);
            return;
        }
        guis.remove(playerId);
    }

    @EventHandler
    private void onClick(InventoryClickEvent event){
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        UUID playerId = player.getUniqueId();

        if (!guis.containsKey(playerId)) return;
        event.setCancelled(true);
        player.updateInventory();

        int slot = event.getRawSlot();
        GUI gui = guis.get(playerId);
        ClickRunnable runnable = gui.getItems().get(slot);

        if (runnable == null) return;
        runnable.run(event);
    }

    public interface ClickRunnable {
        void run(InventoryClickEvent event);
    }
}

package me.pljr.pljrapispigot.builders;

import me.pljr.pljrapispigot.managers.GUIManager;
import me.pljr.pljrapispigot.objects.GUI;
import me.pljr.pljrapispigot.objects.GUIItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GUIBuilder {
    private String title;
    private int rows;
    private HashMap<Integer, GUIItem> items;
    private GUI onClose;

    public String getTitle() {
        return title;
    }

    public int getRows() {
        return rows;
    }

    public HashMap<Integer, GUIItem> getItems() {
        return items;
    }

    public GUI getOnClose() {
        return onClose;
    }

    /**
     * Creates a default GUIBuilder with no title and 1 row.
     */
    public GUIBuilder(){
        this.title = "";
        this.rows = 1;
        this.items = new HashMap<>();
        this.onClose = null;
    }

    /**
     * Creates a new GUIBuilder  with a title.
     *
     * @param title {@link String} that will be used as the title.
     */
    public GUIBuilder(String title){
        this.title = title;
        this.rows = 1;
        this.items = new HashMap<>();
        this.onClose = null;
    }

    /**
     * Creates a new GUIBuilder  with title and number of rows.
     *
     * @param title {@link String} that will be used as the title.
     * @param rows int, that will be used as the number of rows. (Max 6)
     */
    public GUIBuilder(String title, int rows){
        this.title = title;
        this.rows = rows;
        this.items = new HashMap<>();
        this.onClose = null;
    }

    /**
     * Changes the title of the gui.
     *
     * @param title {@link String} that will be used as the new title.
     * @return {@link GUIBuilder} with the changed title.
     */
    public GUIBuilder withTitle(String title){
        this.title = title;
        return this;
    }

    /**
     * Changes the amount of rows the gui will have.
     *
     * @param rows int that will represent the new amount of rows. (Max 6)
     * @return {@link GUIBuilder} with the changed rows.
     */
    public GUIBuilder withRows(int rows){
        this.rows = rows;
        return this;
    }

    /**
     * Sets an item in a selected slot of the gui.
     *
     * @param slot int that will represent the slot in the gui.
     * @param item {@link GUIItem} that will be placed in the slot.
     * @return {@link GUIBuilder} with the changed items.
     */
    public GUIBuilder setItem(int slot, GUIItem item){
        this.items.put(slot, item);
        return this;
    }

    /**
     * Sets an item in a selected slot of the gui.
     *
     * @param slot int that will represent the slot in the gui.
     * @param item {@link ItemStack} that will be placed in the slot.
     * @return {@link GUIBuilder} with the changed items.
     */
    public GUIBuilder setItem(int slot, ItemStack item){
        this.items.put(slot, new GUIItem(item));
        return this;
    }

    /**
     * Sets the gui, that will be opened after closing current.
     *
     * @param gui {@link GUI} that will be opened on close.
     * @return {@link GUIBuilder} with the changed on-close gui.
     */
    public GUIBuilder openOnClose(GUI gui){
        this.onClose = gui;
        return this;
    }

    /**
     * Removes all items from the gui.
     *
     * @return {@link GUIBuilder} with cleared items.
     */
    public GUIBuilder clearItems(){
        this.items = new HashMap<>();
        return this;
    }

    /**
     * Creates a new {@link GUI} with selected attributes.
     *
     * @return New {@link GUI} with selected attributes.
     */
    public GUI create(){
        Inventory inventory = Bukkit.createInventory(null, this.rows*9, this.title);
        HashMap<Integer, GUIManager.ClickRunnable> items = new HashMap<>();
        for (Map.Entry<Integer, GUIItem> entry : this.items.entrySet()){
            int slot = entry.getKey();
            GUIItem item = entry.getValue();
            inventory.setItem(slot, item.getItemStack());
            if (item.getRunnable() != null){
                items.put(slot, item.getRunnable());
            }
        }
        return new GUI(inventory, items);
    }
}

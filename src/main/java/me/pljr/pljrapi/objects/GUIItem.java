package me.pljr.pljrapi.objects;

import me.pljr.pljrapi.managers.GUIManager;
import org.bukkit.inventory.ItemStack;

public class GUIItem {
    private final ItemStack itemStack;
    private GUIManager.ClickRunnable clickRunnable;

    public GUIItem(ItemStack itemStack){
        this.itemStack = itemStack;
        this.clickRunnable = null;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setRunnable(GUIManager.ClickRunnable clickRunnable){
        this.clickRunnable = clickRunnable;
    }

    public GUIManager.ClickRunnable getRunnable() {
        return clickRunnable;
    }

    public GUIItem(ItemStack itemStack, GUIManager.ClickRunnable clickRunnable){
        this.itemStack = itemStack;
        this.clickRunnable = clickRunnable;
    }
}

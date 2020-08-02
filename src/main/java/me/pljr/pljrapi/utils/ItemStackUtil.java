package me.pljr.pljrapi.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemStackUtil {

    public static ItemStack replaceLore(ItemStack item, String target, String replacement){
        ItemStack itemStack = new ItemStack(item);
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> replaceLore = new ArrayList<>();
        for (String line : itemMeta.getLore()){
            replaceLore.add(line.replace(target, replacement));
        }
        itemMeta.setLore(replaceLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

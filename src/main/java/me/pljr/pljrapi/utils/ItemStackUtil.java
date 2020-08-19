package me.pljr.pljrapi.utils;

import com.cryptomorin.xseries.SkullUtils;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemStackUtil {

    public static ItemStack replaceLore(ItemStack item, String target, String replacement){
        ItemStack itemStack = new ItemStack(item);
        if (!itemStack.getItemMeta().hasLore()) return itemStack;
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> replaceLore = new ArrayList<>();
        for (String line : itemMeta.getLore()){
            replaceLore.add(line.replace(target, replacement));
        }
        itemMeta.setLore(replaceLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createHead(String owner, String name, int amount, String... lore){
        ItemStack head = new ItemStack(XMaterial.PLAYER_HEAD.parseItem());
        head.setAmount(amount);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta = SkullUtils.applySkin(headMeta, owner);
        headMeta.setDisplayName(name);
        headMeta.setLore(Arrays.asList(lore));
        head.setItemMeta(headMeta);

        return head;
    }

    public static ItemStack createHead(String owner, String name, int amount, List<String> lore){
        ItemStack head = new ItemStack(XMaterial.PLAYER_HEAD.parseItem());
        head.setAmount(amount);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta = SkullUtils.applySkin(headMeta, owner);
        headMeta.setDisplayName(name);
        headMeta.setLore(lore);
        head.setItemMeta(headMeta);

        return head;
    }

    public static ItemStack createItem(Material material, String name, String... lore){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(Material material, String name, List<String> lore){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}

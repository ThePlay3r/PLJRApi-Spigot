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
    /**
     * Replaces a String with another String in a lore of {@link ItemStack}.
     *
     * @param item {@link ItemStack} that should have the lore changed.
     * @param target String that should be replaced.
     * @param replacement String that the target should be replaced with.
     * @return ItemStack with replaced lore.
     */
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

    /**
     * Creates a Minecraft Head {@link ItemStack} with custom display name, amount and lore.
     *
     * @param owner Owner of the head. (Username, UUID, URL, Base64)
     * @param name Display name of ItemStack.
     * @param amount Display name of ItemStack.
     * @param lore Lore of the ItemStack
     * @return Head ItemStack with specified attributes.
     */
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

    /**
     * Creates a Minecraft Head {@link ItemStack} with custom display name, amount and lore.
     *
     * @param owner Owner of the head. (Username, UUID, URL, Base64)
     * @param name Display name of ItemStack.
     * @param amount Display name of ItemStack.
     * @param lore Lore of the ItemStack
     * @return Head ItemStack with specified attributes.
     */
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

    /**
     * Creates a custom {@link ItemStack} with display name and lore.
     *
     * @param material Material of ItemStack.
     * @param name Display name of ItemStack.
     * @param lore Lore of ItemStack.
     * @return ItemStack with specified attributes.
     */
    public static ItemStack createItem(Material material, String name, String... lore){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Creates a custom {@link ItemStack} with display name and lore.
     *
     * @param material Material of ItemStack
     * @param name Display name of ItemStack
     * @param lore Lore of ItemStack
     * @return ItemStack with specified attributes
     */
    public static ItemStack createItem(Material material, String name, List<String> lore){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Creates a custom copy of {@link ItemStack} with display name and lore.
     *
     * @param itemStack ItemStack that will be copied
     * @param name Display name of ItemStack
     * @param lore Lore of ItemStack
     * @return ItemStack with specified attributes
     */
    public static ItemStack createItem(ItemStack itemStack, String name, List<String> lore){
        ItemStack item = new ItemStack(itemStack);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Creates a custom copy of {@link ItemStack} with display name and lore.
     *
     * @param itemStack ItemStack that will be copied
     * @param name Display name of ItemStack
     * @param lore Lore of ItemStack
     * @return ItemStack with specified attributes
     */
    public static ItemStack createItem(ItemStack itemStack, String name, String... lore){
        ItemStack item = new ItemStack(itemStack);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
}

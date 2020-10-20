package me.pljr.pljrapi.builders;

import com.cryptomorin.xseries.XMaterial;
import me.pljr.pljrapi.utils.FormatUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
    private final ItemStack itemStack;
    private String name;
    private int amount;
    private List<String> lore;

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public List<String> getLore() {
        return lore;
    }

    public ItemBuilder(){
        this.itemStack = new ItemStack(Material.STONE);
        this.name = "";
        this.amount = 1;
        this.lore = new ArrayList<>();
    }

    public ItemBuilder(ItemStack itemStack){
        this.itemStack = itemStack;
        this.amount = itemStack.getAmount();
        ItemMeta itemMeta = itemStack.getItemMeta();
        String name = "";
        List<String> lore = new ArrayList<>();
        if (itemMeta != null){
            if (itemMeta.getDisplayName() != null){
                name = itemMeta.getDisplayName();
            }
            if (itemMeta.getLore() != null){
                lore = itemMeta.getLore();
            }
        }
        this.name = name;
        this.lore = lore;
    }

    public ItemBuilder(Material material){
        this(new ItemStack(material));
    }

    public ItemBuilder(XMaterial xMaterial){
        this(xMaterial.parseItem());
    }

    public ItemBuilder withName(String name){
        this.name = name;
        return this;
    }

    public ItemBuilder withLore(List<String> lore){
        this.lore = lore;
        return this;
    }

    public ItemBuilder withLore(String... lore){
        this.lore = Arrays.asList(lore);
        return this;
    }

    public ItemBuilder replaceLore(String target, String replacement){
        List<String> lore = new ArrayList<>();
        for (String line : this.lore){
            lore.add(line.replace(target, replacement));
        }
        this.lore = lore;
        return this;
    }

    public ItemBuilder withAmount(int amount){
        this.amount = amount;
        return this;
    }

    public ItemStack create(){
        ItemStack itemStack = new ItemStack(this.itemStack);
        itemStack.setAmount(this.amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!name.equals("")){
            itemMeta.setDisplayName(FormatUtil.colorString(name));
        }
        if (!lore.isEmpty()){
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

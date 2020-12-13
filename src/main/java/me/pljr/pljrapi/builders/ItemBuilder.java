package me.pljr.pljrapi.builders;

import com.cryptomorin.xseries.SkullUtils;
import com.cryptomorin.xseries.XMaterial;
import me.pljr.pljrapi.utils.FormatUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
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

    /**
     * Creates a default ItemBuilder from {@link Material#STONE}.
     */
    public ItemBuilder(){
        this.itemStack = new ItemStack(Material.STONE);
        this.name = "";
        this.amount = 1;
        this.lore = new ArrayList<>();
    }

    /**
     * Creates an ItemBuilder from selected itemStack.
     *
     * @param itemStack {@link ItemStack} from what the values will be set to.
     */
    public ItemBuilder(ItemStack itemStack){
        this.itemStack = new ItemStack(itemStack);
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

    /**
     * Creates an ItemBuilder from selected material.
     *
     * @param material {@link Material} Which will be to create and {@link ItemStack} in {@link ItemBuilder#ItemBuilder(ItemStack)}.
     */
    public ItemBuilder(Material material){
        this(new ItemStack(material));
    }

    /**
     * Creates an ItemBuilder from selected material.
     *
     * @param xMaterial {@link XMaterial} Which will be to create and {@link ItemStack} in {@link ItemBuilder#ItemBuilder(ItemStack)}.
     */
    public ItemBuilder(XMaterial xMaterial){
        this(xMaterial.parseItem());
    }

    /**
     * Changes the current name.
     *
     * @param name {@link String} that will represent the new name.
     * @return {@link ItemBuilder} with changed name.
     */
    public ItemBuilder withName(String name){
        this.name = name;
        return this;
    }

    /**
     * Replaces a String with another String in the name.
     *
     * @param target {@link String} that should be replaced.
     * @param replacement {@link String} that the target should be replaced with.
     * @return {@link ItemBuilder} with changed lore.
     */
    public ItemBuilder replaceName(String target, String replacement){
        this.name = name.replace(target, replacement);
        return this;
    }

    /**
     * Changes the current lore.
     *
     * @param lore {@link List<String>} that will represent the new lore.
     * @return {@link ItemBuilder} with changed lore.
     */
    public ItemBuilder withLore(List<String> lore){
        this.lore = lore;
        return this;
    }

    /**
     * Changes the current lore.
     *
     * @param lore {@link String} that will represent the new lore.
     * @return {@link ItemBuilder} with changed lore.
     */
    public ItemBuilder withLore(String... lore){
        this.lore = Arrays.asList(lore);
        return this;
    }

    /**
     * Replaces a String with another String in the lore.
     *
     * @param target {@link String} that should be replaced.
     * @param replacement {@link String} that the target should be replaced with.
     * @return {@link ItemBuilder} with changed lore.
     */
    public ItemBuilder replaceLore(String target, String replacement){
        List<String> lore = new ArrayList<>();
        for (String line : this.lore){
            lore.add(line.replace(target, replacement));
        }
        this.lore = lore;
        return this;
    }

    /**
     * Changes the current amount.
     *
     * @param amount Int, that will represent the new amount.
     * @return {@link ItemBuilder} with changed amount.
     */
    public ItemBuilder withAmount(int amount){
        this.amount = amount;
        return this;
    }

    /**
     * Changes the current owner, has no effect if the ItemStack isn't skull.
     *
     * @param owner {@link String} that will represent the new owner.
     * @return {@link ItemBuilder} with changed owner.
     */
    public ItemBuilder withOwner(String owner){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta = SkullUtils.applySkin(itemMeta, owner);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Makes the current item glowing, cannot be undone.
     * Gives item Durability I {@link Enchantment} and hides all enchantments.
     *
     * @return {@link ItemBuilder} with glowing effect.
     */
    public ItemBuilder setGlowing(){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Creates ItemStack from selected values.
     *
     * @return {@link ItemStack} consisting of all previously selected values.
     */
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

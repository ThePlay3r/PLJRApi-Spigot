package me.pljr.pljrapispigot.item

import com.cryptomorin.xseries.SkullUtils
import com.cryptomorin.xseries.XMaterial
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextReplacementConfig
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class ItemBuilder(itemStack: ItemStack) {
    val itemStack = ItemStack(itemStack)
    var name = itemStack.itemMeta.displayName() ?: Component.text("")
    var amount = itemStack.amount
    var lore = itemStack.itemMeta.lore() ?: ArrayList()

    constructor() : this(ItemStack(Material.STONE))

    constructor(material: Material) : this(ItemStack(material))

    constructor(xMaterial: XMaterial) : this(xMaterial.parseItem()!!)

    /**
     * Changes the current name.
     *
     * @param name [Component] that will represent the new name.
     * @return [ItemBuilder] with changed name.
     */
    fun withName(name: Component): ItemBuilder {
        this.name = name
        return this
    }
    @Deprecated("Use Adventure API",
        ReplaceWith("withName(Component.text(name))", "net.kyori.adventure.text.Component"))
    fun withName(name: String) = withName(Component.text(name))

    /**
     * Replaces a String with another String in the name.
     *
     * @param target [String] that should be replaced.
     * @param replacement [String] that the target should be replaced with.
     * @return [ItemBuilder] with changed lore.
     */
    fun replaceName(target: String, replacement: String): ItemBuilder {
        name = name.replaceText(TextReplacementConfig.builder().matchLiteral(target).replacement(replacement).build())
        return this
    }

    /**
     * Changes the current lore.
     *
     * @param lore [MutableList] that will represent the new lore.
     * @return [ItemBuilder] with changed lore.
     */
    fun withLore(lore: MutableList<Component>): ItemBuilder {
        this.lore = lore
        return this
    }
    @JvmName("withStringLore")
    @Deprecated("Use Adventure API's Component instead of String.")
    fun withLore(lore: MutableList<String>): ItemBuilder {
        var componentLore: MutableList<Component> = ArrayList()
        lore.forEach { componentLore.add(Component.text(it)) }
        this.lore = componentLore
        return this
    }

    /**
     * Changes the current lore.
     *
     * @param lore [Component] that will represent the new lore.
     * @return [ItemBuilder] with changed lore.
     */
    fun withLore(vararg lore: Component): ItemBuilder {
        this.lore = mutableListOf(*lore)
        return this
    }
    @Deprecated("User Adventure API's Component instead of String.",
        ReplaceWith("withLore(Component.text(*lore))", "net.kyori.adventure.text.Component"))
    fun withLore(vararg lore: String) = withLore(mutableListOf(*lore))

    /**
     * Replaces a String with another String in the lore.
     *
     * @param target [String] that should be replaced.
     * @param replacement [String] that the target should be replaced with.
     * @return [ItemBuilder] with changed lore.
     */
    fun replaceLore(target: String, replacement: String): ItemBuilder {
        val lore: MutableList<Component> = ArrayList()
            this.lore.forEach { lore.add(it.replaceText(TextReplacementConfig.builder().matchLiteral(target).replacement(replacement).build())) }
        this.lore = lore
        return this
    }

    /**
     * Changes the current amount.
     *
     * @param amount Int, that will represent the new amount.
     * @return [ItemBuilder] with changed amount.
     */
    fun withAmount(amount: Int): ItemBuilder {
        this.amount = amount
        return this
    }

    /**
     * Changes the current owner, has no effect if the ItemStack isn't skull.
     *
     * @param owner [String] that will represent the new owner.
     * @return [ItemBuilder] with changed owner.
     */
    fun withOwner(owner: String): ItemBuilder {
        if (itemStack.type != XMaterial.PLAYER_HEAD.parseMaterial()) {
            itemStack.type = XMaterial.PLAYER_HEAD.parseMaterial()!!
        }
        var itemMeta = itemStack.itemMeta
        itemMeta = SkullUtils.applySkin(itemMeta, owner)
        itemStack.itemMeta = itemMeta
        return this
    }

    /**
     * Makes the current item glowing, cannot be undone.
     * Gives item Durability I [Enchantment] and hides all enchantments.
     *
     * @return [ItemBuilder] with glowing effect.
     */
    fun setGlowing(): ItemBuilder {
        val itemMeta = itemStack.itemMeta
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true)
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        itemStack.itemMeta = itemMeta
        return this
    }

    /**
     * Adds an [Enchantment] to an item.
     *
     * @param enchant [Enchantment] that should be added.
     * @param level Level of the enchantment.
     * @return [ItemBuilder] with added enchantment.
     */
    fun withEnchant(enchant: Enchantment, level: Int): ItemBuilder {
        val itemMeta = itemStack.itemMeta
        itemMeta.addEnchant(enchant, level, true)
        itemStack.itemMeta = itemMeta
        return this
    }

    /**
     * Creates ItemStack from selected values.
     *
     * @return [ItemStack] consisting of all previously selected values.
     */
    fun create() : ItemStack {
        val itemStack = ItemStack(this.itemStack)
        itemStack.amount = amount
        val itemMeta = itemStack.itemMeta
        itemMeta.displayName(this.name)
        if (lore.isNotEmpty()) itemMeta.lore(this.lore)
        itemStack.itemMeta = itemMeta
        return itemStack
    }
}
package me.pljr.pljrapispigot.config

import com.cryptomorin.xseries.SkullUtils
import com.cryptomorin.xseries.XEnchantment
import com.cryptomorin.xseries.XMaterial
import com.cryptomorin.xseries.XSound
import me.pljr.pljrapispigot.actionbar.ActionBarBuilder
import me.pljr.pljrapispigot.actionbar.PLJRActionBar
import me.pljr.pljrapispigot.item.ItemBuilder
import me.pljr.pljrapispigot.sound.PLJRSound
import me.pljr.pljrapispigot.sound.SoundBuilder
import me.pljr.pljrapispigot.title.PLJRTitle
import me.pljr.pljrapispigot.title.TitleBuilder
import me.pljr.pljrapispigot.util.colorString
import me.pljr.pljrapispigot.util.isInt
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.*
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException


class ConfigManager(private val plugin: JavaPlugin, private val fileName: String) {
    val file: File
    var config: FileConfiguration

    init {
        val dataFolder = plugin.dataFolder
        if (!dataFolder.exists()) dataFolder.mkdir()
        this.file = File(dataFolder, fileName)
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file)
    }

    fun save() {
        config.save(file)
    }

    fun reload() {
        save()
        config = YamlConfiguration.loadConfiguration(file)
    }

    private fun pathNotFound(path: String) {
        plugin.logger.warning("Path $path was not found in $fileName.")
    }

    private fun isNot(type: String, name: String, path: String) {
        plugin.logger.warning("$name is not a $type in file $fileName on $path.")
    }

    /**
     * Tries to get a colored String from [FileConfiguration].
     *
     * @param path Path to the String
     * @return Colored String if one was found, default value if otherwise.
     */
    fun getString(path: String, def: String = "${ChatColor.RED}$path"): String {
        return if (config.isSet(path)) {
            colorString(config.getString(path, def)!!)
        } else {
            pathNotFound(path)
            def
        }
    }

    /**
     * Tries to get a [Sound] from [FileConfiguration] using [XSound].
     *
     * @param path Path to the [Sound]
     * @return Custom [Sound] if one was found, default value otherwise
     */
    fun getSound(path: String, def: Sound = Sound.ENTITY_VILLAGER_NO): Sound {
        if (config.isSet(path)) {
            val soundName = getString(path)
            val xSoundOptional = XSound.matchXSound(soundName)
            if (xSoundOptional.isPresent) {
                return xSoundOptional.get().parseSound()!!
            }
            isNot("Sound", soundName, path)
            return def
        }
        pathNotFound(path)
        return def
    }

    /**
     * Tries to get an Integer from [FileConfiguration].
     *
     * @param path Path to the Integer
     * @return Custom Integer if one was found, default value otherwise.
     */
    fun getInt(path: String, def: Int = -1): Int {
        if (config.isSet(path)) {
            if (config.isInt(path)) {
                return config.getInt(path, def)
            }
            isNot("Integer", getString(path), path)
            return def
        }
        pathNotFound(path)
        return def
    }

    /**
     * Tries to get an Double from [FileConfiguration].
     *
     * @param path Path to the Double
     * @return Custom Double if one was found, default value otherwise.
     */
    fun getDouble(path: String, def: Double = -1.0): Double {
        if (config.isSet(path)) {
            if (config.isDouble(path)) {
                return config.getDouble(path, def)
            }
            isNot("Double", getString(path), path)
            return def
        }
        pathNotFound(path)
        return def
    }

    /**
     * Tries to get an Long from [FileConfiguration].
     *
     * @param path Path to the Double
     * @return Custom Long if one was found, default value otherwise.
     */
    fun getLong(path: String, def: Int = -1) = getInt(path, def).toLong()

    /**
     * Gets an Float from [FileConfiguration].
     *
     * @param path Path to the Float.
     * @return Custom Float.
     */
    fun getFloat(path: String, def: Double = -1.0) = getDouble(path, def).toFloat()

    /**
     * Tries to get an Boolean from [FileConfiguration].
     *
     * @param path Path to the boolean
     * @return Custom Boolean if one was found, default value otherwise
     */
    fun getBoolean(path: String, def: Boolean = false): Boolean {
        if (config.isSet(path)) {
            if (config.isBoolean(path)) {
                return config.getBoolean(path, def)
            }
            isNot("Boolean", getString(path), path)
            return def
        }
        pathNotFound(path)
        return def
    }

    /**
     * Tries to get a Colored String ArrayList from [FileConfiguration].
     *
     * @param path Path to the StringList
     * @return Colored StringList if one was found, empty ArrayList otherwise
     */
    fun getStringList(path: String): List<String> {
        if (config.isSet(path)) {
            if (config.isList(path)) {
                val stringList = config.getStringList(path)
                val coloredStringList: MutableList<String> = ArrayList()
                for (string in stringList) {
                    coloredStringList.add(colorString(string))
                }
                return coloredStringList
            }
            isNot("StringList", "Value", path)
            return ArrayList()
        }
        pathNotFound(path)
        return ArrayList()
    }

    /**
     * Tries to get an [EntityType] from [FileConfiguration].
     *
     * @param path Path to the [EntityType]
     * @return Custom [EntityType] if one was found, PIG otherwise
     */
    fun getEntityType(path: String): EntityType {
        if (config.isSet(path)) {
            val entityName = getString(path)
            for (entity in EntityType.values()) {
                if (entity.toString().equals(entityName, ignoreCase = true)) {
                    return entity
                }
            }
            isNot("EntityType", entityName, path)
            return EntityType.PIG
        }
        pathNotFound(path)
        return EntityType.PIG
    }

    /**
     * Tries to gen an [XMaterial] from [FileConfiguration].
     *
     * @param path Path to the [XMaterial]
     * @return Custom [XMaterial] if one was found, STONE otherwise
     */
    fun getXMaterial(path: String): XMaterial {
        if (config.isSet(path)) {
            val materialName = getString(path)
            val xMaterialOptional = XMaterial.matchXMaterial(materialName)
            if (xMaterialOptional.isPresent && xMaterialOptional.get().parseMaterial() != null) {
                return xMaterialOptional.get()
            }
            isNot("Material", materialName, path)
            return XMaterial.STONE
        }
        pathNotFound(path)
        return XMaterial.STONE
    }

    /**
     * Tries to gen an [Material] from [FileConfiguration].
     *
     * @param path Path to the [Material]
     * @return Custom [Material] if one was found, STONE otherwise.
     */
    fun getMaterial(path: String): Material {
        if (config.isSet(path)) {
            val materialName = getString(path)
            val xMaterialOptional = XMaterial.matchXMaterial(materialName)
            if (xMaterialOptional.isPresent && xMaterialOptional.get().parseMaterial() != null) {
                return xMaterialOptional.get().parseItem()!!.type
            }
            isNot("Material", materialName, path)
            return Material.STONE
        }
        pathNotFound(path)
        return Material.STONE
    }

    /**
     * Tries to get an [ItemStack] from [FileConfiguration] using better-looking format.
     *
     * @param path Path to the [ItemStack]
     * @return Custom [ItemStack] if one was found, ItemStack(Material.STONE) otherwise
     */
    fun getSimpleItemStack(path: String): ItemStack {
        if (config.isSet(path)) {
            if (config.isSet("$path.head-owner")) {
                return getHead(path)
            }
            val itemStack = ItemStack(getMaterial("$path.type"))
            val itemMeta = itemStack.itemMeta
            if (config.isSet("$path.name")) {
                itemMeta.displayName(Component.text(getString("$path.name")))
            }
            if (config.isSet("$path.lore")) {
                val lore: MutableList<Component> = ArrayList()
                getStringList("$path.lore").forEach { lore.add(Component.text(it))  }
                itemMeta.lore(lore)
            }
            itemStack.setItemMeta(itemMeta)
            if (config.isSet("$path.enchantments")) {
                itemStack.addEnchantments(getEnchantments("$path.enchantments"))
            }
            return itemStack
        }
        pathNotFound(path)
        return ItemStack(Material.STONE)
    }

    /**
     * Sets an [ItemStack] into a [FileConfiguration] in SimpleItemStack format.
     *
     * @param path Path to where the itemstack will be saved.
     * @param itemStack [ItemStack] that should be saved.
     */
    fun setSimpleItemStack(path: String, itemStack: ItemStack) {
        val type = itemStack.type
        if (type == XMaterial.PLAYER_HEAD.parseMaterial()) config["$path.head-owner"] = SkullUtils.getSkinValue(itemStack.itemMeta)
        config["$path.type"] = type.toString()
        config["$path.amount"] = itemStack.amount
        if (itemStack.hasItemMeta()){
            val itemMeta = itemStack.itemMeta
            if (itemMeta.hasDisplayName()) config["$path.name"] = MiniMessage.get().serialize(itemMeta.displayName()!!)
            if (itemMeta.hasLore()) {
                val lore : MutableList<String> = ArrayList()
                itemMeta.lore()!!.forEach { line ->
                    lore.add(MiniMessage.get().serialize(line))
                }
                config["$path.lore"] = lore
            }
        }
        if (itemStack.enchantments.isNotEmpty()) setEnchantments("$path.enchantments", itemStack.enchantments)
    }

    /**
     * Tries to get HashMap of [Enchantment] from [FileConfiguration] using [XEnchantment].
     *
     * @param path Path to the list of [Enchantment]
     * @return Custom HashMap if [Enchantment] was found, empty one otherwise
     */
    fun getEnchantments(path: String): HashMap<Enchantment, Int> {
        val enchs = HashMap<Enchantment, Int>()
        val enchantments = getStringList(path)
        for (enchantment in enchantments) {
            val enchNameAndLevel = enchantment.split(":").toTypedArray()
            if (enchNameAndLevel.size == 2) {
                val xEnchantmentOptional = XEnchantment.matchXEnchantment(enchNameAndLevel[0])
                if (xEnchantmentOptional.isPresent) {
                    if (isInt(enchNameAndLevel[1])) {
                        enchs[xEnchantmentOptional.get().parseEnchantment()!!] = enchNameAndLevel[1].toInt()
                    } else {
                        isNot("Integer", enchNameAndLevel[1], path)
                    }
                } else {
                    isNot("Enchantment", enchNameAndLevel[0], path)
                }
            } else {
                isNot("Valid Enchantment Format", enchNameAndLevel.contentToString(), path)
            }
        }
        return enchs
    }

    /**
     * Saves an array of [Enchantment] to [FileConfiguration].
     *
     * @param path Path to where the array will be saved.
     * @param enchants Enchantments that should be saved.
     */
    fun setEnchantments(path: String, enchants: Map<Enchantment, Int>) {
        val enchs: MutableList<String> = ArrayList()
        for ((key, value) in enchants) {
            enchs.add("${key.key}:$value")
        }
        config[path] = enchs
    }

    /**
     * Tries to get an [ItemStack] from [FileConfiguration].
     *
     * @param path Path to the [ItemStack]
     * @return Custom [ItemStack] if one was found, ItemStack(Material.STONE) otherwise
     */
    fun getItemStack(path: String): ItemStack {
        if (config.isSet(path)) {
            val itemStack = config.getItemStack(path)
            if (itemStack == null) {
                isNot("ItemStack", "?", path)
                return ItemStack(Material.STONE)
            }
            return itemStack
        }
        pathNotFound(path)
        return ItemStack(Material.STONE)
    }

    /**
     * Tries to create an Head [ItemStack] from [FileConfiguration] using [ItemBuilder].
     *
     * @param path Path to the Head
     * @return Custom Head [ItemStack] if one was found, ItemStack(Material.STONE) otherwise
     */
    fun getHead(path: String): ItemStack {
        if (config.isSet(path)) {
            val owner = getString("$path.head-owner")
            var name = Component.text("Head")
            var amount = 1
            val lore: MutableList<Component> = ArrayList()
            if (config.isSet("$path.amount")) {
                amount = getInt("$path.amount")
            }
            if (config.isSet("$path.name")) {
                name = Component.text(getString("$path.name"))
            }
            if (config.isSet("$path.lore")) {
                getStringList("$path.lore").forEach { lore.add(Component.text(it)) }
            }
            val head: ItemStack = ItemBuilder(XMaterial.PLAYER_HEAD)
                .withName(name)
                .withAmount(amount)
                .withLore(lore)
                .withOwner(owner)
                .create()
            if (config.isSet("$path.enchantments")) {
                head.addEnchantments(getEnchantments("$path.enchantments"))
            }
            return head
        }
        pathNotFound(path)
        return ItemStack(Material.STONE)
    }

    /**
     * Tries to get an DamageCause from [FileConfiguration].
     *
     * @param path Path to the DamageCause
     * @return Custom DamageCause if one was found, VOID otherwise
     */
    fun getDamageCause(path: String): EntityDamageEvent.DamageCause {
        if (config.isSet(path)) {
            val causeName = getString(path)
            for (cause in EntityDamageEvent.DamageCause.values()) {
                if (cause.toString().equals(causeName, true)) {
                    return cause
                }
            }
            isNot("DamageCause", causeName, path)
            return EntityDamageEvent.DamageCause.VOID
        }
        pathNotFound(path)
        return EntityDamageEvent.DamageCause.VOID
    }

    /**
     * Tries to get an [PLJRTitle] from [FileConfiguration].
     *
     * @param path Path to the [PLJRTitle]
     * @return Custom [PLJRTitle] if one was found.
     */
    fun getPLJRTitle(path: String): PLJRTitle {
        if (config.isSet(path)) {
            val title = Component.text(getString("$path.title"))
            val subtitle = Component.text(getString("$path.subtitle"))
            val inTime = getLong("$path.in")
            val stayTime = getLong("$path.stay")
            val outTime = getLong("$path.out")
            return PLJRTitle(title, subtitle, inTime, stayTime, outTime)
        }
        pathNotFound(path)
        return TitleBuilder(Component.text("Title"), Component.text("Was not found!")).create()
    }

    /**
     * Sets [PLJRTitle] to [PLJRActionBar].
     *
     * @param path Path to where the [PLJRTitle] should be set.
     * @param title [PLJRTitle] that should be set.
     */
    fun setPLJRTitle(path: String, title: PLJRTitle) {
        config["$path.title"] = MiniMessage.get().serialize(title.title)
        config["$path.subtitle"] = MiniMessage.get().serialize(title.subtitle)
        config["$path.in"] = title.inTime
        config["$path.stay"] = title.stayTime
        config["$path.out"] = title.outTime
    }

    /**
     * Tries to get an [PLJRSound] from [FileConfiguration].
     *
     * @param path Path to the [PLJRSound]
     * @return Custom [PLJRSound] if one was found.
     */
    fun getPLJRSound(path: String): PLJRSound {
        if (config.isSet(path)) {
            val type = getSound("$path.type")
            val volume = getFloat("$path.volume")
            val pitch = getFloat("$path.pitch")
            return PLJRSound(type, volume, pitch)
        }
        pathNotFound(path)
        return SoundBuilder(XSound.ENTITY_BAT_DEATH).create()
    }

    /**
     * Sets [PLJRSound] to [FileConfiguration].
     *
     * @param path Path to where the [PLJRSound] should be set.
     * @param sound [PLJRSound] that should be set.
     */
    fun setPLJRSound(path: String, sound: PLJRSound) {
        config["$path.type"] = sound.type.toString()
        config["$path.volume"] = sound.volume
        config["$path.pitch"] = sound.pitch
    }

    /**
     * Tries to get an [ConfigurationSection] from [FileConfiguration].
     *
     * @param path Path to the [ConfigurationSection]
     * @return Custom [ConfigurationSection] if one was found, null otherwise
     *
     * @see .pathNotFound
     */
    fun getConfigurationSection(path: String): ConfigurationSection? {
        if (config.isSet(path)) {
            return config.getConfigurationSection(path)
        }
        pathNotFound(path)
        return null
    }

    /**
     * Tries to get an [PLJRActionBar] from [FileConfiguration].
     *
     * @param path Path to the [PLJRActionBar]
     * @return Custom [PLJRActionBar] if one was found.
     */
    fun getPLJRActionBar(path: String): PLJRActionBar {
        if (config.isSet(path)) {
            val message = getString("$path.message")
            val duration = getLong("$path.duration")
            return PLJRActionBar(message, duration)
        }
        pathNotFound(path)
        return ActionBarBuilder(path).create()
    }

    /**
     * Sets [PLJRActionBar] to [FileConfiguration].
     *
     * @param path Path to where the [PLJRActionBar] should be set.
     * @param actionBar [PLJRActionBar] that should be set.
     */
    fun setPLJRActionBar(path: String, actionBar: PLJRActionBar) {
        config["$path.message"] = actionBar.message
        config["$path.duration"] = actionBar.duration
    }

    /**
     * Tries to get an [World] from [FileConfiguration].
     *
     * @param path Path to the [World]
     * @return [World] if one was found, random from loaded worlds otherwise.
     *
     * @see .getString
     * @see .isNotWorld
     */
    fun getWorld(path: String): World {
        val worldName = getString(path)
        if (Bukkit.getWorld(worldName) == null) {
            isNot("World", worldName, path)
            return Bukkit.getWorlds()[0]
        }
        return Bukkit.getWorld(worldName)!!
    }

    /**
     * Gets an [Location] from [FileConfiguration].
     *
     * @param path Path to the [Location]
     * @return Custom [Location] from the settings.
     *
     * @see .getWorld
     * @see .getInt
     * @see .getFloat
     */
    fun getLocation(path: String): Location {
        val world: World = getWorld("$path.world")
        val x = getDouble("$path.x")
        val y = getDouble("$path.y")
        val z = getDouble("$path.z")
        val yaw = getFloat("$path.yaw")
        val pitch = getFloat("$path.pitch")
        return Location(world, x, y, z, yaw, pitch)
    }
}
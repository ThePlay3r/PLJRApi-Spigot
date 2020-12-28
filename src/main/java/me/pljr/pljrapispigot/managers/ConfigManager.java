package me.pljr.pljrapispigot.managers;

import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import me.pljr.pljrapispigot.builders.ItemBuilder;
import me.pljr.pljrapispigot.builders.ScoreboardBuilder;
import me.pljr.pljrapispigot.objects.PLJRActionBar;
import me.pljr.pljrapispigot.objects.PLJRScoreboard;
import me.pljr.pljrapispigot.objects.PLJRSound;
import me.pljr.pljrapispigot.objects.PLJRTitle;
import me.pljr.pljrapispigot.utils.FormatUtil;
import me.pljr.pljrapispigot.utils.NumberUtil;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ConfigManager {
    private final File file;
    private FileConfiguration config;
    private final String prefix;
    private final String fileName;
    private final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    private void pathNotFound(String path){
        console.sendMessage(prefix + " Path " + path + " was not found in " + fileName + " !");
    }

    private void isNotSound(String sound, String path){
        console.sendMessage(prefix + " " + sound + " is not a sound on " + path +" in " + fileName + " !");
    }

    private void isNotInt(String integer, String path){
        console.sendMessage(prefix + " " + integer + " is not an int on " + path + " in " + fileName + " !");
    }

    private void isNotDouble(String integer, String path){
        console.sendMessage(prefix + " " + integer + " is not an double on " + path + " in " + fileName + " !");
    }

    private void isNotBoolean(String bool, String path){
        console.sendMessage(prefix + " " + bool + " is not a boolean on " + path +" in " + fileName + " !");
    }

    private void isNotStringlist(String path){
        console.sendMessage(prefix + " Couldn't find a String List on " + path +" in " + fileName + " !");
    }

    private void isNotEntityType(String entity, String path){
        console.sendMessage(prefix + " " + entity + "is not an entity on " + path + " in " + fileName + " !");
    }

    private void isNotMaterial(String material, String path){
        console.sendMessage(prefix + " " + material + " is not a material on " + path + " in " + fileName + " !");
    }

    private void isNotDamageCause(String cause, String path){
        console.sendMessage(prefix + " " + cause + "is not a damage cause on " + path + " in " + fileName + " !");
    }

    private void isNotItemStack(String path){
        console.sendMessage(prefix + " Could not deserialize ItemStack at " + path + " in " + fileName + " !");
    }

    private void isNotValidEnchantmentFormat(String format, String path){
        console.sendMessage(prefix + format + " is not a valid enchantment format at " + path + " in " + fileName + " !");
    }

    private void isNotEnchantment(String enchantment, String path){
        console.sendMessage(prefix + enchantment + " is not a valid enchantment at " + path + " in " + fileName + " !");
    }

    private void isNotWorld(String worldName, String path){
        console.sendMessage(prefix + worldName + " is not a valid world at " + path + " in " + fileName + " !");
    }

    /**
     * Creates a new ConfigManager.
     *
     * @param plugin {@link JavaPlugin} who's data folder and name will be used.
     * @param fileName {@link String} name of the file.
     */
    public ConfigManager(JavaPlugin plugin, String fileName){
        String pluginName = plugin.getDescription().getName();
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) dataFolder.mkdir();
        File file = new File(dataFolder, fileName);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
        this.prefix = "§c" + pluginName + ":";
        this.fileName = fileName;
    }

    public void save(){
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        save();
        config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Tries to get a colored String from {@link FileConfiguration}.
     *
     * @param path Path to the String
     * @return Colored String if one was found, path if otherwise.
     *
     * @see FormatUtil
     * @see #pathNotFound(String)
     */
    public String getString(String path){
        if (config.isSet(path)){
            return FormatUtil.colorString(config.getString(path));
        }else{
            pathNotFound(path);
            return ChatColor.RED + path;
        }
    }

    /**
     * Tries to get a {@link Sound} from {@link FileConfiguration} using {@link XSound}.
     *
     * @param path Path to the {@link Sound}
     * @return Custom {@link Sound} if one was found, ENTITY_VILLAGER_NO sound otherwise
     *
     * @see #pathNotFound(String)
     * @see #isNotSound(String, String)
     */
    public Sound getSound(String path){
        if (config.isSet(path)){
            String soundName = getString(path);
            if (soundName == null || soundName.isEmpty()){
                isNotSound(soundName, path);
                return null;
            }
            Optional<XSound> xSoundOptional = XSound.matchXSound(soundName);
            if (xSoundOptional.isPresent()){
                return xSoundOptional.get().parseSound();
            }

            isNotSound(soundName, path);
            return XSound.ENTITY_VILLAGER_NO.parseSound();
        }

        pathNotFound(path);
        return XSound.ENTITY_VILLAGER_NO.parseSound();
    }

    /**
     * Tries to get an Integer from {@link FileConfiguration}.
     *
     * @param path Path to the Integer
     * @return Custom Integer if one was found, 1 otherwise.
     */
    public int getInt(String path){
        if (config.isSet(path)){
            if (config.isInt(path)){
                return config.getInt(path);
            }

            isNotInt(config.getString(path), path);
            return 1;
        }

        pathNotFound(path);
        return 1;
    }

    /**
     * Tries to get an Double from {@link FileConfiguration},
     *
     * @param path Path to the Double
     * @return Custom Double if one was found, 1 otherwise.
     */
    public double getDouble(String path){
        if (config.isSet(path)){
            if (NumberUtil.isDouble(getString(path))){
                return config.getDouble(path);
            }

            isNotDouble(config.getString(path), path);
            return 1;
        }

        pathNotFound(path);
        return 1;
    }

    /**
     * Gets an Float from {@link FileConfiguration},
     *
     * @param path Path to the Float.
     * @return Custom Float.
     * 
     * @see #getDouble(String) 
     */
    public float getFloat(String path){
        return (float) getDouble(path);
    }

    /**
     * Tries to get an Boolean from {@link FileConfiguration}.
     *
     * @param path Path to the boolean
     * @return Custom Boolean if one was found, false otherwise
     *
     * @see #pathNotFound(String)
     * @see #isNotBoolean(String, String)
     */
    public boolean getBoolean(String path){
        if (config.isSet(path)){
            if (config.isBoolean(path)){
                return config.getBoolean(path);
            }

            isNotBoolean(config.getString(path), path);
            return false;
        }

        pathNotFound(path);
        return false;
    }

    /**
     * Tries to get a Colored String ArrayList from {@link FileConfiguration}.
     *
     * @param path Path to the StringList
     * @return Colored StringList if one was found, empty ArrayList otherwise
     *
     * @see #pathNotFound(String)
     * @see #isNotStringlist(String)
     */
    public List<String> getStringList(String path){
        if (config.isSet(path)){
            if (config.isList(path)){
                List<String> stringList = config.getStringList(path);
                List<String> coloredStringList = new ArrayList<>();

                for (String string : stringList){
                    coloredStringList.add(FormatUtil.colorString(string));
                }

                return coloredStringList;
            }

            isNotStringlist(path);
            return new ArrayList<>();
        }

        pathNotFound(path);
        return new ArrayList<>();
    }

    /**
     * Tries to get an {@link EntityType} from {@link FileConfiguration}.
     *
     * @param path Path to the {@link EntityType}
     * @return Custom {@link EntityType} if one was found, PIG otherwise
     *
     * @see #pathNotFound(String)
     * @see #isNotEntityType(String, String)
     */
    public EntityType getEntityType(String path){
        if (config.isSet(path)){
            String entityName = config.getString(path);

            for (EntityType entity : EntityType.values()){
                if (entity.toString().equalsIgnoreCase(entityName)){
                    return entity;
                }
            }

            isNotEntityType(entityName, path);
            return EntityType.PIG;
        }

        pathNotFound(path);
        return EntityType.PIG;
    }

    /**
     * Tries to gen an {@link XMaterial} from {@link FileConfiguration}.
     *
     * @param path Path to the {@link XMaterial}
     * @return Custom {@link XMaterial} if one was found, STONE otherwise
     *
     * @see #pathNotFound(String)
     * @see #isNotMaterial(String, String)
     */
    public XMaterial getXMaterial(String path){
        if (config.isSet(path)){
            String materialName = config.getString(path);
            if (materialName == null || materialName.isEmpty()){
                pathNotFound(path);
                return XMaterial.STONE;
            }


            Optional<XMaterial> xMaterialOptional = XMaterial.matchXMaterial(materialName);
            if (xMaterialOptional.isPresent() && xMaterialOptional.get().parseMaterial() != null){
                return xMaterialOptional.get();
            }

            isNotMaterial(materialName, path);
            return XMaterial.STONE;
        }

        pathNotFound(path);
        return XMaterial.STONE;
    }

    /**
     * Tries to gen an {@link Material} from {@link FileConfiguration}.
     *
     * @param path Path to the {@link Material}
     * @return Custom {@link Material} if one was found, STONE otherwise.
     *
     * @see #pathNotFound(String)
     * @see #isNotMaterial(String, String)
     */
    public Material getMaterial(String path){
        if (config.isSet(path)){
            String materialName = getString(path);
            if (materialName == null || materialName.isEmpty()){
                pathNotFound(path);
                return Material.STONE;
            }


            Optional<XMaterial> xMaterialOptional = XMaterial.matchXMaterial(materialName);
            if (xMaterialOptional.isPresent() && xMaterialOptional.get().parseMaterial() != null){
                return xMaterialOptional.get().parseItem().getType();
            }

            isNotMaterial(materialName, path);
            return Material.STONE;
        }

        pathNotFound(path);
        return Material.STONE;
    }

    /**
     * Tries to get an {@link ItemStack} from {@link FileConfiguration} using better-looking format.
     *
     * @param path Path to the {@link ItemStack}
     * @return Custom {@link ItemStack} if one was found, ItemStack(Material.STONE) otherwise
     *
     * @see #pathNotFound(String)
     * @see #getHead(String)
     * @see #getXMaterial(String)
     * @see #getInt(String)
     * @see #getString(String)
     * @see #getStringList(String)
     * @see #getEnchantments(String)
     */
    public ItemStack getSimpleItemStack(String path){
        if (config.isSet(path)){
            if (config.isSet(path+".head-owner")){
                return getHead(path);
            }

            XMaterial type = getXMaterial(path+".type");
            ItemStack itemStack = new ItemStack(type.parseItem());
            if (config.isSet(path+".amount")){
                itemStack.setAmount(getInt(path+".amount"));
            }

            ItemMeta itemMeta = itemStack.getItemMeta();
            if (config.isSet(path+".name")){
                itemMeta.setDisplayName(getString(path+".name"));
            }
            if (config.isSet(path+".lore")){
                itemMeta.setLore(getStringList(path+".lore"));
            }
            itemStack.setItemMeta(itemMeta);

            if (config.isSet(path+".enchantments")){
                HashMap<Enchantment, Integer> enchs = getEnchantments(path+".enchantments");
                itemStack.addEnchantments(enchs);
            }

            return itemStack;
        }

        pathNotFound(path);
        return new ItemStack(Material.STONE);
    }

    /**
     * Tries to get HashMap of {@link Enchantment} from {@link FileConfiguration} using {@link XEnchantment}.
     *
     * @param path Path to the list of {@link Enchantment}
     * @return Custom HashMap if {@link Enchantment} was found, empty one otherwise
     *
     * @see NumberUtil
     * @see #isNotInt(String, String)
     * @see #isNotEnchantment(String, String)
     * @see #isNotValidEnchantmentFormat(String, String)
     */
    public HashMap<Enchantment, Integer> getEnchantments(String path){
        HashMap<Enchantment, Integer> enchs = new HashMap<>();
        List<String> enchantments = getStringList(path);
        for (String enchantment : enchantments){
            String[] enchNameAndLevel = enchantment.split(":");
            if (enchNameAndLevel.length == 2){
                Optional<XEnchantment> xEnchantmentOptional = XEnchantment.matchXEnchantment(enchNameAndLevel[0]);
                if (xEnchantmentOptional.isPresent()){
                    if (NumberUtil.isInt(enchNameAndLevel[1])){
                        enchs.put(xEnchantmentOptional.get().parseEnchantment(), Integer.parseInt(enchNameAndLevel[1]));
                    }else{
                        isNotInt(enchNameAndLevel[1], path);
                    }
                }else{
                    isNotEnchantment(enchNameAndLevel[0], path);
                }
            }else{
                isNotValidEnchantmentFormat(Arrays.toString(enchNameAndLevel), path);
            }
        }
        return enchs;
    }

    /**
     * Tries to get an {@link ItemStack} from {@link FileConfiguration}.
     *
     * @param path Path to the {@link ItemStack}
     * @return Custom {@link ItemStack} if one was found, ItemStack(Material.STONE) otherwise
     *
     * @see #pathNotFound(String)
     * @see #isNotItemStack(String)
     */
    public ItemStack getItemStack(String path){
        if (config.isSet(path)){
            ItemStack itemStack = config.getItemStack(path);
            if (itemStack == null){
                isNotItemStack(path);
                return new ItemStack(Material.STONE);
            }
            return itemStack;
        }

        pathNotFound(path);
        return new ItemStack(Material.STONE);
    }

    /**
     * Tries to create an Head {@link ItemStack} from {@link FileConfiguration} using {@link me.pljr.pljrapispigot.builders.ItemBuilder}.
     *
     * @param path Path to the Head
     * @return Custom Head {@link ItemStack} if one was found, ItemStack(Material.STONE) otherwise
     *
     * @see #pathNotFound(String)
     */
    public ItemStack getHead(String path){
        if (config.isSet(path)){
            String owner = getString(path+".head-owner");
            String name = "Head";
            int amount = 1;
            List<String> lore = new ArrayList<>();

            if (config.isSet(path+".amount")){
               amount = getInt(path+".amount");
            }
            if (config.isSet(path+".name")){
                name = getString(path+".name");
            }
            if (config.isSet(path+".lore")){
                lore = getStringList(path+".lore");
            }

            return new ItemBuilder(XMaterial.PLAYER_HEAD)
                    .withName(name)
                    .withAmount(amount)
                    .withLore(lore)
                    .withOwner(owner)
                    .create();
        }

        pathNotFound(path);
        return new ItemStack(Material.STONE);
    }

    /**
     * Tries to get an DamageCause from {@link FileConfiguration}.
     *
     * @param path Path to the DamageCause
     * @return Custom DamageCause if one was found, VOID otherwise
     *
     * @see #pathNotFound(String)
     * @see #isNotDamageCause(String, String)
     */
    public EntityDamageEvent.DamageCause getDamageCause(String path){
        if (config.isSet(path)){
            String causeName = config.getString(path);

            for (EntityDamageEvent.DamageCause cause : EntityDamageEvent.DamageCause.values()){
                if (cause.toString().equalsIgnoreCase(causeName)){
                    return cause;
                }
            }

            isNotDamageCause(causeName, path);
            return EntityDamageEvent.DamageCause.VOID;
        }

        pathNotFound(path);
        return EntityDamageEvent.DamageCause.VOID;
    }

    /**
     * Tries to get an {@link PLJRTitle} from {@link FileConfiguration}.
     *
     * @param path Path to the {@link PLJRTitle}
     * @return Custom {@link PLJRTitle} if one was found, PLJRTitle("§cTitle", "§cWas not found!", 20, 40, 20) otherwise
     *
     * @see #pathNotFound(String)
     */
    public PLJRTitle getPLJRTitle(String path){
        if (config.isSet(path)){
            String title = getString(path+".title");
            String subtitle = getString(path+".subtitle");
            int in = getInt(path+".in");
            int stay = getInt(path+".stay");
            int out = getInt(path+".out");
            return new PLJRTitle(title, subtitle, in, stay, out);
        }

        pathNotFound(path);
        return new PLJRTitle("§cTitle", "§cWas not found!", 20, 40, 20);
    }

    /**
     * Tries to get an {@link PLJRSound} from {@link FileConfiguration}.
     *
     * @param path Path to the {@link PLJRSound}
     * @return Custom {@link PLJRSound} if one was found, PLJRSound(XSound.ENTITY_BAT_DEATH.parseSound(), 1, 1) otherwise
     *
     * @see #pathNotFound(String)
     */
    public PLJRSound getPLJRSound(String path){
        if (config.isSet(path)){
            Sound type = getSound(path+".type");
            int volume = getInt(path+".volume");
            int pitch = getInt(path+".pitch");
            return new PLJRSound(type, volume, pitch);
        }

        pathNotFound(path);
        return new PLJRSound(XSound.ENTITY_BAT_DEATH.parseSound(), 1, 1);
    }

    /**
     * Tries to get an {@link ConfigurationSection} from {@link FileConfiguration}.
     *
     * @param path Path to the {@link ConfigurationSection}
     * @return Custom {@link ConfigurationSection} if one was found, null otherwise
     *
     * @see #pathNotFound(String)
     */
    public ConfigurationSection getConfigurationSection(String path){
        if (config.isSet(path)){
            return config.getConfigurationSection(path);
        }

        pathNotFound(path);
        return null;
    }

    Boolean test;

    /**
     * Tries to get an {@link PLJRActionBar} from {@link FileConfiguration}.
     *
     * @param path Path to the {@link PLJRActionBar}
     * @return Custom {@link PLJRActionBar} if one was found, PLJRActionBar("§c"+path, 20) otherwise
     *
     * @see #pathNotFound(String)
     */
    public PLJRActionBar getPLJRActionBar(String path){
        if (config.isSet(path)){
            String message = getString(path+".message");
            int duration = getInt(path+".duration");
            return new PLJRActionBar(message, duration);
        }

        pathNotFound(path);
        return new PLJRActionBar("§c"+path, 20);
    }

    /**
     * Tries to get an {@link PLJRScoreboard} from {@link FileConfiguration}.
     * 
     * @param path Path to the {@link PLJRScoreboard}
     * @return Custom {@link PLJRScoreboard} if one was found, PLJRScoreboard("") otherwise
     * 
     * @see #getStringList(String) 
     */
    public PLJRScoreboard getPLJRScoreboard(String path){
        List<String> lines = getStringList(path);
        if (lines.isEmpty()){
            return new ScoreboardBuilder().create();
        }
        ScoreboardBuilder builder = new ScoreboardBuilder(lines.get(0));
        lines.remove(0);
        for (String line : lines){
            builder.getLines().add(line);
        }
        return builder.create();
    }

    /**
     * Tries to get an {@link World} from {@link FileConfiguration}.
     * 
     * @param path Path to the {@link World}
     * @return {@link World} if one was found, random from loaded worlds otherwise.
     * 
     * @see #getString(String) 
     * @see #isNotWorld(String, String) 
     */
    public World getWorld(String path){
        String worldName = getString(path);
        if (Bukkit.getWorld(worldName) == null){
            isNotWorld(worldName, path);
            return Bukkit.getWorlds().get(0);
        }
        return Bukkit.getWorld(worldName);
    }

    /**
     * Gets an {@link Location} from {@link FileConfiguration}.
     * 
     * @param path Path to the {@link Location}
     * @return Custom {@link Location} from the settings.
     * 
     * @see #getWorld(String)
     * @see #getInt(String)
     * @see #getFloat(String) 
     */
    public Location getLocation(String path){
        World world = getWorld(path+".world");
        double x = getDouble(path+".x");
        double y = getDouble(path+".y");
        double z = getDouble(path+".z");
        float yaw = getFloat(path+".yaw");
        float pitch = getFloat(path+".pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }
}


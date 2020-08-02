package me.pljr.pljrapi.managers;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import me.pljr.pljrapi.objects.PLJRActionBar;
import me.pljr.pljrapi.objects.PLJRSound;
import me.pljr.pljrapi.objects.PLJRTitle;
import me.pljr.pljrapi.utils.FormatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConfigManager {
    private final FileConfiguration config;
    private final String prefix;
    private final String fileName;
    private final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    private void pathNotFound(String path){
        console.sendMessage(prefix + " Path " + path + " was not found in " + fileName + " !");
    }

    private void isNotSound(String sound, String path){
        console.sendMessage(prefix + " " + sound + " is not a sound on " + path +" in " + fileName + " !");
    }

    private void isNotInt(String integer, String path){
        console.sendMessage(prefix + " " + integer + " is not an int on " + path + " in " + fileName + " !");
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

    public ConfigManager(FileConfiguration config, String prefix, String fileName){
        this.config = config;
        this.prefix = prefix;
        this.fileName = fileName;
    }

    public String getString(String path){
        if (config.isSet(path)){
            return FormatUtil.colorString(config.getString(path));
        }else{
            pathNotFound(path);
            return ChatColor.RED + path;
        }
    }

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

    public Material getMaterial(String path){
        if (config.isSet(path)){
            String materialName = config.getString(path);
            if (materialName == null || materialName.isEmpty()){
                pathNotFound(path);
                return Material.STONE;
            }

            Optional<XMaterial> xMaterialOptional = XMaterial.matchXMaterial(materialName);
            if (xMaterialOptional.isPresent() && xMaterialOptional.get().parseMaterial() != null){
                return xMaterialOptional.get().parseMaterial();
            }

            isNotMaterial(materialName, path);
            return Material.STONE;
        }

        pathNotFound(path);
        return Material.STONE;
    }

    public ItemStack getSimpleItemStack(String path){
        if (config.isSet(path)){

            Material type = getMaterial(path+".type");
            String name = getString(path+".name");
            int amount = getInt(path+".amount");
            List<String> lore = getStringList(path+".lore");

            ItemStack itemStack = new ItemStack(type, amount);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(name);
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);

            return itemStack;
        }

        pathNotFound(path);
        return new ItemStack(Material.STONE);
    }

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

    public ConfigurationSection getConfigurationSection(String path){
        if (config.isSet(path)){
            return config.getConfigurationSection(path);
        }

        pathNotFound(path);
        return null;
    }

    public PLJRActionBar getPLJRActionBar(String path){
        if (config.isSet(path)){
            String message = getString(path+".message");
            int duration = getInt(path+".duration");
            return new PLJRActionBar(message, duration);
        }

        pathNotFound(path);
        return new PLJRActionBar("§c"+path, 20);
    }
}


package me.pljr.pljrapi.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.pljr.pljrapi.config.CfgLang;
import me.pljr.pljrapi.config.CfgSettings;
import me.pljr.pljrapi.config.CfgSounds;
import me.pljr.pljrapi.enums.Lang;
import me.pljr.pljrapi.enums.Sounds;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class CommandUtil {

    public void fail(Player player){
        Location playerLoc = player.getLocation();
        if (CfgSettings.sounds) player.playSound(playerLoc, CfgSounds.sounds.get(Sounds.COMMAND_FAIL), 10, 1);
    }

    public boolean checkPerm(CommandSender sender, String perm){
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (player.hasPermission(perm)) return true;
        player.sendMessage(CfgLang.lang.get(Lang.NO_PERM));
        fail(player);
        return false;
    }

    public boolean checkPlayer(CommandSender sender, String requestName){
        if (!PlayerUtil.isPlayer(requestName)){
            sender.sendMessage(CfgLang.lang.get(Lang.OFFLINE).replace("%name", requestName));
            if (sender instanceof Player){
                fail((Player) sender);
            }
            return false;
        }
        return true;
    }

    public boolean checkInt(CommandSender sender, String target){
        if (NumberUtil.isInt(target)){
            return true;
        }
        sender.sendMessage(CfgLang.lang.get(Lang.NO_NUMBER));
        return false;
    }

    public void sendHelp(CommandSender sender, List<String> help){
        if (sender instanceof Player){
            Player player = (Player) sender;
            for (String line : help){
                player.sendMessage(PlaceholderAPI.setPlaceholders(player, line));
            }
            return;
        }
        help.forEach(sender::sendMessage);
    }
}

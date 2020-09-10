package me.pljr.pljrapi.utils;

import me.pljr.pljrapi.config.CfgLang;
import me.pljr.pljrapi.config.CfgSettings;
import me.pljr.pljrapi.config.CfgSounds;
import me.pljr.pljrapi.enums.Lang;
import me.pljr.pljrapi.enums.Sounds;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class CommandUtil {
    /**
     * Plays a sound of command failure to {@link Player}, if sounds are enabled in configuration.
     *
     * @param player {@link Player} that failed the command
     */
    public void fail(Player player){
        Location playerLoc = player.getLocation();
        if (CfgSettings.sounds) player.playSound(playerLoc, CfgSounds.sounds.get(Sounds.COMMAND_FAIL), 10, 1);
    }

    /**
     * Checks if {@link CommandSender} has a specified permission, sends NO_PERM message and fails command.
     *
     * @param sender {@link CommandSender} that will be checked for the permission
     * @param perm Permission that will be checked
     * @return True if sender isn't a Player or is a Player and does have the permission, False if sender is Player and
     * does not have the required permission
     *
     * @see #fail(Player)
     * @see #sendMessage(Player, String)
     */
    public boolean checkPerm(CommandSender sender, String perm){
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (player.hasPermission(perm)) return true;
        sendMessage(player, CfgLang.lang.get(Lang.NO_PERM));
        fail(player);
        return false;
    }

    /**
     * Checks if requested player name belongs to a {@link Player} using {@link PlayerUtil}.
     *
     * @param sender {@link CommandSender} that requested the check
     * @param requestName Name of the requested Player
     * @return True if there is a player with requested name, False if there is not one
     *
     * @see PlayerUtil
     * @see #fail(Player)
     * @see #sendMessage(CommandSender, String)
     */
    public boolean checkPlayer(CommandSender sender, String requestName){
        if (!PlayerUtil.isPlayer(requestName)){
            sendMessage(sender, CfgLang.lang.get(Lang.OFFLINE).replace("%name", requestName));
            if (sender instanceof Player){
                fail((Player) sender);
            }
            return false;
        }
        return true;
    }

    /**
     * Checks if requested string is an Integer using {@link NumberUtil}.
     *
     * @param sender {@link CommandSender} that requested the check
     * @param target String that will be checked for Integer
     * @return True if target is in fact an Integer, False if otherwise
     *
     * @see NumberUtil
     * @see #fail(Player)
     * @see #sendMessage(CommandSender, String)
     */
    public boolean checkInt(CommandSender sender, String target){
        if (NumberUtil.isInt(target)){
            return true;
        }
        sendMessage(sender, CfgLang.lang.get(Lang.NO_NUMBER).replace("%arg", target));
        if (sender instanceof Player){
            fail((Player) sender);
        }
        return false;
    }

    /**
     * Chechks if requested string is a Material using {@link MaterialUtil}.
     *
     * @param sender {@link CommandSender} that requested the check
     * @param target String that will be checked for Material
     * @return True if target is in fact a Material, False if otherwise
     *
     * @see MaterialUtil
     * @see #fail(Player)
     * @see #sendMessage(CommandSender, String)
     */
    public boolean checkMaterial(CommandSender sender, String target){
        if (MaterialUtil.isMaterial(target)){
            return true;
        }
        sendMessage(sender, CfgLang.lang.get(Lang.NO_MATERIAL).replace("%material", target));
        if (sender instanceof Player){
            fail((Player) sender);
        }
        return false;
    }

    /**
     * Sends a List of messages with parsed MiniMessage tags and PAPI Placeholders to {@link CommandSender}.
     *
     * @param sender {@link CommandSender} that should receive the messages
     * @param help List of messages that should be send
     *
     * @see #sendMessage(Player, String)
     * @see #sendMessage(CommandSender, String)
     */
    public void sendHelp(CommandSender sender, List<String> help){
        if (sender instanceof Player){
            Player player = (Player) sender;
            for (String line : help){
                sendMessage(player, line);
            }
            return;
        }
        for (String line : help){
            sendMessage(sender, line);
        }
    }

    /**
     * Sends a List of messages with parsed PAPI Placeholders to {@link CommandSender}.
     *
     * @param sender {@link CommandSender} that should receive the messages
     * @param help List of messages that should be send
     *
     * @see #sendMessage(Player, String)
     * @see #sendMessage(CommandSender, String)
     */
    public void sendHelpClean(CommandSender sender, List<String> help){
        if (sender instanceof Player){
            Player player = (Player) sender;
            for (String line : help){
                sendMessageClean(player, line);
            }
            return;
        }
        for (String line : help){
            sendMessageClean(sender, line);
        }
    }

    /**
     * Sends a message with parsed MiniMessage tags and PAPI Placeholders to {@link OfflinePlayer}, if online.
     *
     * @param player {@link OfflinePlayer} that should receive the message
     * @param message String that should be send to player
     *
     * @see #sendMessage(Player, String)
     */
    public void sendMessage(OfflinePlayer player, String message){
        if (!player.isOnline()) return;
        sendMessage((Player)player, message);
    }

    /**
     * Sends a message with parsed PAPI Placeholders to {@link OfflinePlayer}, if online.
     *
     * @param player {@link OfflinePlayer} that should receive the message
     * @param message String that should be send to player
     *
     * @see #sendMessage(Player, String)
     */
    public void sendMessageClean(OfflinePlayer player, String message){
        if (!player.isOnline()) return;
        sendMessageClean((Player)player, message);
    }

    /**
     * Sends a message with parsed MiniMessage tags and PAPI Placeholders to {@link Player} using {@link ChatUtil}.
     *
     * @param player {@link Player} that should receive the message
     * @param message String that will be send to player
     *
     * @see ChatUtil
     */
    public void sendMessage(Player player, String message){
        ChatUtil.sendMessage(player, message);
    }

    /**
     * Sends a message with parsed PAPI Placeholders to {@link Player} using {@link ChatUtil}.
     *
     * @param player {@link Player} that should receive the message
     * @param message String that will be send to player
     *
     * @see ChatUtil
     */
    public void sendMessageClean(Player player, String message){
        ChatUtil.sendMessageClean(player, message);
    }

    /**
     * Sends a message with parsed MiniMessage tags and PAPI Placeholders to {@link CommandSender} using {@link ChatUtil}.
     *
     * @param sender {@link CommandSender} that should receive the message
     * @param message String that will be send to sender
     *
     * @see ChatUtil
     */
    public void sendMessage(CommandSender sender, String message){
        ChatUtil.sendMessage(sender, message);
    }

    /**
     * Sends a message with parsed PAPI Placeholders to {@link CommandSender} using {@link ChatUtil}.
     *
     * @param sender {@link CommandSender} that should receive the message
     * @param message String that will be send to sender
     *
     * @see ChatUtil
     */
    public void sendMessageClean(CommandSender sender, String message){
        ChatUtil.sendMessageClean(sender, message);
    }
}

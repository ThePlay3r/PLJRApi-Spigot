package me.pljr.pljrapispigot.command

import me.pljr.pljrapispigot.config.configuration.Lang
import me.pljr.pljrapispigot.util.sendMessage
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

abstract class SubCommand(val command: String, val permission: String) {
    val subCommands: MutableList<SubCommand> = ArrayList()

    fun onTabComplete(args: List<String>) : List<String> {
        if (args.isEmpty()) {
            val list: MutableList<String> = ArrayList()
            subCommands.forEach { list.add(it.command) }
            return list
        } else {
            subCommands.forEach {
                if (it.equals(args[0])) {
                    return it.onTabComplete(args.drop(1))
                }
            }
        }
        return emptyList()
    }

    fun onCommand(sender: CommandSender, args: List<String>) : Boolean {
        if (checkPerm(sender, permission)){
            if (args.isNotEmpty()) {
                subCommands.forEach {
                    if (it.equals(args[0])) {
                        return it.onCommand(sender, args.drop(1))
                    }
                }
            } else if (sender is Player) {
                onPlayerCommand(sender, args)
                return true
            } else if (sender is ConsoleCommandSender) {
                onConsoleCommand(sender, args)
                return true
            } else {
                sender.sendMessage("Unknows Sender")
                return false
            }
        }
        return false
    }

    open fun onPlayerCommand(player: Player, args: List<String>) = sendMessage(player, Lang.COMMAND_RESPONSE_PLAYER.get())

    open fun onConsoleCommand(sender: ConsoleCommandSender, args: List<String>) = sendMessage(sender, Lang.COMMAND_RESPONSE_CONSOLE.get())

    /**
     * Checks if [CommandSender] has a specified permission, sends NO_PERM message and fails command.
     *
     * @param sender [CommandSender] that will be checked for the permission
     * @param perm Permission that will be checked
     * @return True if sender isn't a Player or is a Player and does have the permission, False if sender is Player and
     * does not have the required permission
     */
    fun checkPerm(sender: CommandSender, perm: String): Boolean {
        if (sender !is Player) return true
        if (sender.hasPermission(perm)) return true
        sendMessage(sender, Lang.NO_PERM.get())
        return false
    }
}
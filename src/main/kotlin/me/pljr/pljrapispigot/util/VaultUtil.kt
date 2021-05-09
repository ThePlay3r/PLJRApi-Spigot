package me.pljr.pljrapispigot.util

import me.pljr.pljrapispigot.PLJRApiSpigot
import me.pljr.pljrapispigot.config.configuration.Settings
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

private val ECONOMY = PLJRApiSpigot.vaultEcon!!

private fun checkEnabled() : Boolean {
    if (Settings.VAULT) {
        return true
    } else if (Settings.DISABLED_MESSAGES) {
        PLJRApiSpigot.instance.logger.warning("Tried to use Vault while disabled in config!")
    }
    return false
}

/**
 * Will get the current Economy balance of [OfflinePlayer].
 *
 * @param target [OfflinePlayer] that we should get the balance of
 * @return Target's Economy balance
 */
fun getBalance(target: OfflinePlayer): Double {
    return if (checkEnabled()) {
        ECONOMY.getBalance(target)
    } else {
        -1.0
    }
}

/**
 * Will get the current Economy balance of target.
 *
 * @param target Target that we should get the balance of
 * @return Target's Economy balance
 */
@Deprecated("Use OfflinePlayer instead of display name.")
fun getBalance(target: String): Double {
    return if (checkEnabled()) {
        ECONOMY.getBalance(target)
    } else {
        -1.0
    }
}

/**
 * Will check, if target has enough balance.
 *
 * @param target Target that we should get the balance of
 * @param amount Amount that will be check.
 * @return If target has at least the amount of balance.
 */
fun hasBalance(target: OfflinePlayer, amount: Double): Boolean {
    return if (checkEnabled()) {
        getBalance(target) >= amount
    } else {
        true
    }
}

/**
 * Will check, if target has enough balance.
 *
 * @param target Target that we should get the balance of
 * @param amount Amount that will be check.
 * @return If target has at least the amount of balance.
 *
 * @see .getBalance
 */
@Deprecated("Use OfflinePlayer instead of display name.")
fun hasBalance(target: String, amount: Double): Boolean {
    return if (checkEnabled()) {
        getBalance(target) >= amount
    } else {
        true
    }
}

/**
 * Will deposit funds to Economy balance of [OfflinePlayer].
 *
 * @param target [OfflinePlayer] that should get the amount deposited
 * @param amount Amount of money that should be deposited
 */
fun deposit(target: OfflinePlayer, amount: Double) {
    if (checkEnabled()) {
        ECONOMY.depositPlayer(target, amount)
    }
}

/**
 * Will deposit funds to Economy balance of target.
 *
 * @param target Target that should get the amount deposited
 * @param amount Amount of money that should be deposited
 */
@Deprecated("Use OfflinePlayer instead of display name.")
fun deposit(target: String, amount: Double) {
    if (checkEnabled()) {
        ECONOMY.depositPlayer(target, amount)
    }
}

/**
 * Will withdraw funds from Economy balance of [OfflinePlayer].
 *
 * @param target [OfflinePlayer] that should get the amount withdrawn
 * @param amount Amount of money that should be withdrawn
 */
fun withdraw(target: OfflinePlayer, amount: Double) {
    if (checkEnabled()) {
        ECONOMY.withdrawPlayer(target, amount)
    }
}

/**
 * Will withdraw funds from Economy balance of target.
 *
 * @param target Target that should get the amount withdrawn
 * @param amount Amount of money that should be withdrawn
 */
@Deprecated("Use OfflinePlayer instead of display name.")
fun withdraw(target: String, amount: Double) {
    if (checkEnabled()) {
        ECONOMY.withdrawPlayer(target, amount)
    }
}
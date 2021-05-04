package me.pljr.pljrapispigot.util

import org.bukkit.Material

/**
 * Checks if String is a [Material].
 *
 * @param value String that should be checked for Material
 * @return True if value is Material, false if otherwise
 */
fun isMaterial(value: String): Boolean {
    for (material in Material.values()) {
        if (material.toString().equals(value, true)) {
            return true
        }
    }
    return false
}
package me.pljr.pljrapi.utils;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;

public class MaterialUtil {
    /**
     * Checks if String is a {@link Material}.
     *
     * @param value String that should be checked for Material
     * @return True if value is Material, false if otherwise
     */
    public static boolean isMaterial(String value){
        for (Material material : Material.values()){
            if (material.toString().equalsIgnoreCase(value)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if String is a {@link XMaterial}.
     *
     * @param value String that should be checked for XMaterial
     * @return True if value is XMaterial, false if otherwise
     */
    public static boolean isXMaterial(String value){
        return XMaterial.matchXMaterial(value).isPresent();
    }
}

package me.pljr.pljrapi.utils;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;

public class MaterialUtil {

    public static boolean isMaterial(String value){
        for (Material material : Material.values()){
            if (material.toString().equalsIgnoreCase(value)){
                return true;
            }
        }
        return false;
    }

    public static boolean isXMaterial(String value){
        return XMaterial.matchXMaterial(value).isPresent();
    }
}

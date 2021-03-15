package me.pljr.pljrapispigot.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public final class SpigotUtil {
    /**
     * Tries to check, if resource with defined resource ID is up-to-date
     *
     * @param resourceId ID of the resource
     * @param version Current version of the resource
     * @return True if the versions are equal, false otherwise
     */
    public static boolean upToDate(int resourceId, String version){
        try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
            return version.equalsIgnoreCase(scanner.next());
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }
}

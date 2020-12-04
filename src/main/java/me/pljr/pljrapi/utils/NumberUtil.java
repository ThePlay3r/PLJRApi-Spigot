package me.pljr.pljrapi.utils;

public class NumberUtil {
    /**
     * Checks if String is an Integer.
     *
     * @param number String that should be checked
     * @return True if number is an Integer, false if otherwise
     */
    public static boolean isInt(String number){
        try {
            Integer.parseInt(number);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if String is an Double.
     *
     * @param number String that should be checked
     * @return True if number is an Double, false if otherwise
     */
    public static boolean isDouble(String number){
        try {
            Double.parseDouble(number);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if String is an Float.
     *
     * @param number String that should be checked
     * @return True if number is an Float, false if otherwise
     */
    public static boolean isFloat(String number){
        try {
            Float.parseFloat(number);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}

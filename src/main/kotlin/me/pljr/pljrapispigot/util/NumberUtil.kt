package me.pljr.pljrapispigot.util

/**
 * Checks if String is an Integer.
 *
 * @param number String that should be checked
 * @return True if number is an Integer, false if otherwise
 */
fun isInt(number: String): Boolean {
    return try {
        number.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

/**
 * Checks if String is an Double.
 *
 * @param number String that should be checked
 * @return True if number is an Double, false if otherwise
 */
fun isDouble(number: String): Boolean {
    return try {
        number.toDouble()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

/**
 * Checks if String is an Float.
 *
 * @param number String that should be checked
 * @return True if number is an Float, false if otherwise
 */
fun isFloat(number: String): Boolean {
    return try {
        number.toFloat()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

/**
 * Checks if String is an Long.
 *
 * @param number String that should be checked
 * @return True if number is an Long, false if otherwise
 */
fun isLong(number: String): Boolean {
    return try {
        number.toLong()
        true
    }catch (e: java.lang.NumberFormatException) {
        false
    }
}
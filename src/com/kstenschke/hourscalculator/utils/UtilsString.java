package com.kstenschke.hourscalculator.utils;

public class UtilsString {

    /**
     * @param   str
     * @return  String  Cleaned time string, containing only 0-9 and :
     */
    public static String sanitizeTimeString(String str) {
        str = str.trim();
        str = str.replaceAll("[^0-9|^:]", "");

        if( str.equals("") ) {
            str = "0:00";
        }

        return str;
    }
}

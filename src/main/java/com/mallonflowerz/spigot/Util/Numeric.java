package com.mallonflowerz.spigot.Util;

public class Numeric {
    
    public static final boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

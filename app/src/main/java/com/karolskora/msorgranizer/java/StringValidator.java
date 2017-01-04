package com.karolskora.msorgranizer.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by apple on 04.01.2017.
 */

public class StringValidator {

    public static boolean isEmail(String string) {
        Pattern p =Pattern.compile("[a-zA-Z0-9_.]*@[a-zA-Z]*.[a-zA-Z]*");
        Matcher m = p.matcher(string);
        boolean bm = m.matches();
        return bm;
    }

    public static boolean isNumber(String numberString) {
        try {
            new java.math.BigDecimal(numberString);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isNumber(String[] numberStrings) {

        for(String string: numberStrings) {
            try {
                new java.math.BigDecimal(string);
            } catch(NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    public static boolean containsOnlyLetters(String string) {
        return string.matches("[a-zA-Z]+");
    }
    public static boolean containsOnlyLetters(String[] strings) {
        for (String singleString:strings) {
            if(!singleString.matches("[a-zA-Z]+")) {
                return false;
            }
        }
        return true;
    }
}

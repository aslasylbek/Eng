package com.uibenglish.aslan.mvpmindorkssample.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aslan on 17.05.2018.
 */

public class CommonUtils {
    public static final String REGEX = "[0-9]+";
    public static boolean isBarcodeValid(String barcode){
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(REGEX);
        matcher = pattern.matcher(barcode);
        return matcher.matches();

    }
}

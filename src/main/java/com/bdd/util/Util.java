package com.bdd.util;

import com.bdd.environmet.ManageEnvironment;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;

import java.util.regex.Pattern;

public class Util {
    public static boolean isNumeric(String strNum){
        if (strNum == null){
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (Exception e){
            return false;
        }
        return true;
    }
    public static boolean matchCI (String pattern, String text){
        return Pattern.compile(
                pattern,
                Pattern.CASE_INSENSITIVE
        ).matcher(text).find();
    }

    public static String getEnvironmentProperty(String property){
        String res = "";
        try{
            res = EnvironmentSpecificConfiguration.from(ManageEnvironment.getEnvironment()).getProperty(property);
        } catch (Exception ignored) {
        }
        return res;
    }
}

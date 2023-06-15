package com.bdd.environmet;

import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;

public class ManageEnvironment {
    private static final EnvironmentVariables environmentVariables = SystemEnvironmentVariables.currentEnvironmentVariables();
    public static void setProperty(String key, Object value){
        environmentVariables.setProperty(key, value.toString());
    }
    public static String getProperty(String key){
        return environmentVariables.getProperty(key);
    }
    public static EnvironmentVariables getEnvironment(){
        return environmentVariables;
    }
}

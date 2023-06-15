package com.bdd.util;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;

public class UtilApi {

    public static void saveVariableOnSession(String key, Object value) {
        Serenity.setSessionVariable(key).to(value);
    }

    public static <T> T getVariableOnSession(String key) {
        return Serenity.sessionVariableCalled(key);
    }

}

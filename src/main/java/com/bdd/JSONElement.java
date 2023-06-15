package com.bdd;
import com.google.common.base.Preconditions;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONElement {

    private static final Pattern pattern = Pattern.compile("(?<=\\[)([^]]+)(?=])");

    private final Object base;

    public JSONElement(Object base) {
        Preconditions.checkNotNull(base, "base");
        Preconditions.checkState(base instanceof JSONObject || base instanceof JSONArray, "base must be a JSONObject or JSONArray instead of " + base.getClass().getSimpleName());
        this.base = base;
    }

    public Object put(Object key, Object value) {
        String keyAsString = String.valueOf(key);

        if (base instanceof JSONObject) {
            if (keyAsString.contains("[")) {
                String formatKey = keyAsString.contains("[") ? keyAsString.substring(0, keyAsString.indexOf("[")) : keyAsString;
                JSONArray array = ((JSONObject) base).has(formatKey) ? ((JSONObject) base).getJSONArray(formatKey) : new JSONArray();

                int index = getIndex(keyAsString);
                array.put(index, value);
                ((JSONObject) base).put(formatKey, array);
                return ((JSONArray) ((JSONObject) base).get(formatKey)).get(index);
            }

            ((JSONObject) base).put(keyAsString, value);
            return ((JSONObject) base).get(keyAsString);
        }

        int index = getIndex(keyAsString);
        ((JSONArray) base).put(index, value);
        return ((JSONArray) base).get(index);
    }

    public boolean has(Object key) {
        String keyAsString = String.valueOf(key);

        if (base instanceof JSONObject) {
            JSONObject object = (JSONObject) base;
            String formatKey = formatKey(keyAsString);
            if (keyAsString.contains("["))
                return object.has(formatKey) && !object.getJSONArray(formatKey).isNull(getIndex(keyAsString));

            return object.has(formatKey);
        }

        return !((JSONArray) base).isNull(getIndex(keyAsString));
    }

    public Object get(Object key) {
        String keyAsString = String.valueOf(key);
        if (base instanceof JSONObject) {
            JSONObject object = (JSONObject) base;
            String formatKey = formatKey(keyAsString);
            if (keyAsString.contains("["))
                return object.getJSONArray(formatKey).get(getIndex(keyAsString));

            return object.get(formatKey);
        }

        return ((JSONArray) base).get(getIndex(keyAsString));
    }

    public Object newInstance() {
        return base instanceof JSONObject ? new JSONObject() : new JSONArray();
    }

    private int getIndex(String key) {
        Matcher matcher = pattern.matcher(key);
        Preconditions.checkState(matcher.find(), String.format("Matcher couldn't find an index number in \"%s\"", key));
        return Integer.parseInt(matcher.group());
    }

    private String formatKey(String key) {
        return key.contains("[") ? key.substring(0, key.indexOf("[")) : key;
    }
}
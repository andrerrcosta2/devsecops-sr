package com.nobblecrafts.challenge.devsecopssr.util.ser;

import com.google.gson.*;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

public class JsonUtils {

    private JsonUtils() {}

    public static String extractFromJson(String json, String... patterns) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
            JsonObject resultObject = new JsonObject();

            for (String pattern : patterns) {
                Object extractedData = JsonPath.read(document, pattern);
                resultObject.add(pattern, gson.toJsonTree(extractedData));
            }

            return gson.toJson(resultObject);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while extracting data from JSON");
        }
    }

    public static String prettifyJsonString(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(json);
        return gson.toJson(je);
    }
}

package services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.LinkedList;

public class JSONHandler {
    public static JsonArray getJsonData(String data){
        JsonArray jsonObject = new JsonParser()
                .parse(data)
                .getAsJsonArray();
        return jsonObject;
    }

    public static String getValuesFromJsonArray(JsonArray array, String valueName){
        LinkedList<Object> values = new LinkedList<>();
        for (JsonElement jsonElement : array) {
            jsonElement.getAsJsonObject().get(valueName);
        }
        return values.toString();
    }
}

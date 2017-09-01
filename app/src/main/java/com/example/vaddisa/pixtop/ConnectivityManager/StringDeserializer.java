package com.example.vaddisa.pixtop.ConnectivityManager;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.apache.commons.lang3.StringEscapeUtils;

import java.lang.reflect.Type;

/**
 * Created by vaddisa on 8/20/2017.
 */
public class StringDeserializer implements JsonDeserializer<String> {
    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String data = StringEscapeUtils.unescapeJson(json.getAsString());
        return data;
    }
}

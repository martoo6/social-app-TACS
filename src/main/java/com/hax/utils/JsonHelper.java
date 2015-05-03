package com.hax.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hax.models.DespegarError;

import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by martin on 5/3/15.
 */
public class JsonHelper {
    static ObjectMapper mapper = new ObjectMapper();

    public static <T> T readValue(Response response, Class<T> var2){
        return readValue(response, var2, "Service Unavailable.");
    }

    public static <T> T readValue(Response response, Class<T> var2, String customMessage){
        try {
            return mapper.readValue(response.readEntity(String.class), var2);
        } catch (IOException e) {
            throw new RuntimeException(customMessage);
        }
    }

    public static <T> T readValue(String var1, Class<T> var2){
        return readValue(var1, var2, "Service Unavailable.");
    }

    public static <T> T readValue(String var1, Class<T> var2, String customMessage){
        try {
            return mapper.readValue(var1, var2);
        } catch (IOException e) {
            throw new RuntimeException(customMessage);
        }
    }
}

package com.hax.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FlightModel {
    
    public static JSONArray flights() throws JSONException
    {
        return new JSONArray().put(
                new JSONObject().put("id", 1)
                    .put("destination", "Acapulco")
                    .put("origin", "Buenos Aires")
                    .put("airline", "Awesome Express")
                    .put("flight-duration", 10)
                    .put("flight-date", "5/5/2015")
                    .put("flight-num", 666)
                    .put("price", 250.50)
                );
    } 
   
}

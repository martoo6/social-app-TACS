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
                    .put("flight-duration", "1h 20m")
                    .put("departure-time", "20-04-2015 18:30")
                    .put("flight-num", "P659B2")
                    .put("price", 250.50)
                );
    } 
   
}

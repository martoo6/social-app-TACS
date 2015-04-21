package com.hax.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserModel {
    
    public static JSONArray friends() throws JSONException
    {
        return new JSONArray().put(
                new JSONObject().put("id", 1)
                    .put("name", "Tyler")
                    .put("lastname", "Durden")
                    .put("age", 31)
                );
    } 
   
}

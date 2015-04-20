package com.hax.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
import org.json.JSONObject;

@Path("login")
public class LoginController
{

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String index()
    {
        return "form para ingresar al sistema";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String asd(String postData) throws JSONException
    {
        /*
        *recieves postData to log user in
        *use postData like:
        *
        *new JSONObject(postData)
        *
        */
        return new JSONObject().put("success", true).toString();
    }
}
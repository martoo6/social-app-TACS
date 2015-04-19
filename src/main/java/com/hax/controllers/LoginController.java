package com.hax.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
   @Produces(MediaType.APPLICATION_JSON)
   public String asd(@QueryParam("mail") String mail, @QueryParam("password") String password) throws JSONException
   {
      return new JSONObject().put("success", true).toString();
   }
}
package com.hax.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Path("flights")
public class TravelsController {
    
    private JSONObject flights;

    public TravelsController() throws JSONException {
        this.flights = new JSONObject().put("id", 1)
                        .put("destination", "Acapulco")
                        .put("airline", "Awesome Express")
                        .put("flight-duration", 10)
                        .put("flight-date", "5/5/2015")
                        .put("flight-nro", 666)
                        .put("price", 250.50);
    }
    
    @Path("list") 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String index(@QueryParam("from") String from, @QueryParam("to") String to, @QueryParam("from_date") String fromDate, @QueryParam("to_date") String toDate) throws JSONException
    {
        //ask despegar api for travels and filters them
        return new JSONObject().put("flights", this.flights).toString();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String saveTravel(String flight) throws JSONException
    {
        /*
        *recive and save in the system one of the flight's json
        *to access the flight json just do:
        *
        *new JSONObject(flight)
        */
        return new JSONObject().put("success", true).toString();
    }
    
    @Path("flights/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String myTravels(@PathParam("userId") int userId) throws JSONException
    {
        //retrieves user flights
        return new JSONObject().put("flights", this.flights).toString();
    }
}
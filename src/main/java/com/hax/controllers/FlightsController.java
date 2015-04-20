package com.hax.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
public class FlightsController {
    
    private final JSONArray flights;

    public FlightsController() throws JSONException {
        this.flights = new JSONArray().put(
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

    /**
     * 
     * Asks despegar api for travels and filters them
     * 
     * @param from
     * @param to
     * @param fromDate
     * @param toDate
     * @return Flights list filtered by paramaters
     * @throws JSONException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String showFlights(@QueryParam("from") String from, @QueryParam("to") String to,
            @QueryParam("from_date") String fromDate, @QueryParam("to_date") String toDate) throws JSONException
    {
        return flights.toString();
    }

    /**
     * 
     * Recive and save in the system one of the flight's json of {userId}
     *
     * @param flight
     * @param userId
     * @return operation status
     * @throws JSONException
     */
    @Path("{userId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String saveFlight(String flight, @PathParam("userId") int userId) throws JSONException
    {
        return new JSONObject().put("success", true).toString();
    }

    /**
     *
     * @param userId
     * @return flights from user
     * @throws JSONException
     */
    @Path("{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String flights(@PathParam("userId") int userId) throws JSONException
    {
        //retrieves {userId} flights
        return flights.toString();
    }

    /**
     *
     * @param userId
     * @return friends from user
     * @throws JSONException
     */
    @Path("friends/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String friendsFlights(@PathParam("userId") int userId) throws JSONException
    {
        JSONObject aFriend = new JSONObject()
                                .put("id", "1")
                                .put("name", "Carlos")
                                .put("apellido", "Ledesma")
                                .put("flights", this.flights);
                
        //retrieves {userId} friends with their flights
        return new JSONArray().put(aFriend).toString();
    }

    /**
     *
     * @param userId
     * @return flights recommended by friends to {userId}
     * @throws JSONException
     */
    @Path("friends/recommended/{userId}") 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String showRecommended(@PathParam("userId") int userId) throws JSONException
    {
        return new JSONArray().put(new JSONObject()
                                .put("id", 5)
                                .put("friend-id", 6)
                                .put("flight", this.flights.get(0))
                            ).toString();
    }

    /**
     * 
     * Accepts recommendation from friend(in postData)
     * 
     * @param postData
     * @param userId
     * @param recommId
     * @return operation status
     * @throws JSONException
     */
    @Path("friends/recommended/{userId}/{recommID}") 
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String acceptRecommendation(String postData, @PathParam("userId") int userId,
            @PathParam("recommId") int recommId) throws JSONException
    {
        return new JSONObject().put("success", true).toString();
    }

    /**
     * 
     * Deny recommendation from friend(in postData)
     * 
     * @param postData
     * @param userId
     * @param recommId
     * @return operation status
     * @throws JSONException
     */
    @Path("friends/recommended/{userId}/{recommID}") 
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String denyRecommendation(String postData, @PathParam("userId") int userId,
            @PathParam("recommId") int recommId) throws JSONException
    {
        return new JSONObject().put("success", true).toString();
    }

    /**
     *
     * {userId} recommends a flight to {friendId}
     * additional data to perform recomendation in postData
     * 
     * @param postData
     * @param userId
     * @param friendId
     * @return operation status
     * @throws JSONException
     */
    @Path("friends/recommend/{userId}/{friendId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String recommendToFriend(String postData, @PathParam("userId") int userId,
            @PathParam("friendId") int friendId) throws JSONException
    {
        return new JSONObject().put("success", true).toString();
    }
    
            
    /**
     * 
     * Allows {userId} to share flight in facebook
     *
     * @param flight
     * @param userId
     * @return operation status
     * @throws JSONException
     */
    @Path("share/{userId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String shareFlight(String flight, @PathParam("userId") int userId) throws JSONException
    {
        return new JSONObject().put("success", true).toString();
    }
}

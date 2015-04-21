package com.hax.controllers;

import com.hax.models.FlightModel;
import com.hax.models.UserModel;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by martin on 4/20/15.
 */

@Path("users")
public class UsersController {

    /**
     *
     * @param userId
     * @return Todos los vuelos de un usuario
     * @throws JSONException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userID}/flights/")
    public String getFlights(@PathParam("userId") int userId) throws JSONException
    {
        return FlightModel.flights().toString();
    }

    /**
     *
     * @param userId
     * @return Todos los amigos de un usuario
     * @throws JSONException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userID}/friends/")
    public String getFriends(@PathParam("userId") int userId) throws JSONException
    {
        return UserModel.friends().toString();
    }

    /**
     *
     * @param userId
     * @return  Todas las recomendacions de vuelo de un usauario
     * @throws JSONException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userID}/recommendations/flights")
    public String getFlightsRecommendations(@PathParam("userId") int userId) throws JSONException
    {
        return FlightModel.flights().toString();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    //TODO: el Redirect del login con facebook tendria q llegar aca si no existe el usuario para crearlo
    public String createUser() throws JSONException
    {
        return new JSONObject().put("success", true).toString();
    }
}

package com.hax.controllers;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.utils.FutureHelper;
import com.hax.models.Recommendation;
import com.hax.models.User;
import com.hax.services.UsersServiceInterface;
import org.json.JSONException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import static com.hax.async.utils.FutureHelper.addControllerCallback;

/**
 * Created by martin on 4/20/15.
 */

@Path("users")
public class UsersController {

    @Inject
    UsersServiceInterface usersService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userID}")
    public void getUser(@PathParam("userId") int userId, @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        FutureHelper.addControllerCallback(usersService.getUser(userId) ,asyncResponse);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getAllUser(@Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        FutureHelper.addControllerCallback(usersService.getAll() ,asyncResponse);
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
    public void getFriends(@PathParam("userId") int userId, @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        FutureHelper.addControllerCallback(usersService.getFriends(userId) ,asyncResponse);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userID}/flights/")
    public void getFlights(@PathParam("userId") int userId, @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        FutureHelper.addControllerCallback(usersService.getFlights(userId) ,asyncResponse);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userID}/recommendations/")
    public void getRecommendations(@PathParam("userId") int userId, @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        FutureHelper.addControllerCallback(usersService.getRecommendations(userId) ,asyncResponse);
    }

    //TODO: mas bien aca tiraria la vista/webpage si usaramos un template system
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    //TODO: el Redirect del login con facebook tendria q llegar aca si no existe el usuario para crearlo
//    public void createUser() throws JSONException
//    {}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createUser(final User user, @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        FutureHelper.addControllerCallback(usersService.createUser(user) ,asyncResponse);
    }

    /**
     *
     * @param flightID
     * @param userId
     * @return Estado de la operacion
     * @throws JSONException
     */
    @Path("{userID}/recommendations/{flightID}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void recommendFlight(@PathParam("flightId") int flightID ,
                                @PathParam("userId") int userId,
                                @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        //TODO: El cero esta harcodeado y tiene que ser el id del usuario loggeado actualmente.
        ListenableFuture<Recommendation> f= usersService.recommendFlight(flightID, 0, userId);
        addControllerCallback(f, asyncResponse);
    }
}

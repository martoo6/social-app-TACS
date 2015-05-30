package com.hax.controllers;

import com.hax.services.UsersServiceInterface;
import org.json.JSONException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import static com.hax.utils.ControllerHelper.addControllerCallback;
import static com.hax.utils.ControllerHelper.fail;

/**
 * Created by martin on 4/20/15.
 */

@Path("friends")
public class FriendsController {

    @Inject
    UsersServiceInterface usersService;


    /**
     *
     * @return Todos los amigos de un usuario
     * @throws JSONException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getFriends(@Context HttpHeaders hh, @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        String optToken = hh.getHeaderString("token");
        if(optToken==null) {
            fail("Missing token", asyncResponse);
        } else {
            Integer token = Integer.parseInt(optToken);
            addControllerCallback(usersService.getFriends(token), asyncResponse);
        }
    }

    /**
     *
     * @return Todos los amigos de un usuario
     * @throws JSONException
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{friendId}")
    public void addFriend(@PathParam("friendId") Integer friendId,@Context HttpHeaders hh, @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        String optToken = hh.getHeaderString("token");
        if(optToken==null) {
            fail("Missing token", asyncResponse);
        } else {
            Integer token = Integer.parseInt(optToken);
            addControllerCallback(usersService.addFriend(token, friendId), asyncResponse);
        }
    }

    /**
     *
     * @return Todos los amigos de un usuario
     * @throws JSONException
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{friendId}")
    public void removeFriend(@PathParam("friendId") Integer friendId,@Context HttpHeaders hh, @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        String optToken = hh.getHeaderString("token");
        if(optToken==null) {
            fail("Missing token", asyncResponse);
        } else {
            Integer token = Integer.parseInt(optToken);
            addControllerCallback(usersService.removeFriend(token, friendId), asyncResponse);
        }
    }
}

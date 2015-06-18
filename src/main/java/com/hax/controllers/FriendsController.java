package com.hax.controllers;

import com.hax.services.UsersServiceInterface;
import org.json.JSONException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response getFriends(@Context HttpHeaders hh) throws JSONException
    {
        String token = hh.getHeaderString("token");
        if(token==null) {
            return fail("Missing token");
        } else {
            return addControllerCallback(usersService.getFriends(token));
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
    public Response addFriend(@PathParam("friendId") String friendId, @Context HttpHeaders hh) throws JSONException
    {
        String token = hh.getHeaderString("token");
        if(token==null) {
            return fail("Missing token");
        } else {
            return addControllerCallback(usersService.addFriend(token, friendId));
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
    public Response removeFriend(@PathParam("friendId") String friendId,@Context HttpHeaders hh) throws JSONException
    {
        String token = hh.getHeaderString("token");
        if(token==null) {
            return fail("Missing token");
        } else {
            return addControllerCallback(usersService.removeFriend(token, friendId));
        }
    }
}

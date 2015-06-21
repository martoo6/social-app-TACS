package com.hax.controllers;

import com.hax.models.User;
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

@Path("users")
public class UsersController {

    @Inject
    UsersServiceInterface usersService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userId}")
    public Response getUser(@PathParam("userId") String userId) throws JSONException
    {
        return addControllerCallback(usersService.getUser(userId));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("me/friends")
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
     * @return Lista de vuelos que cumplen el criterio de busqueda
     * @throws JSONException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("me/trips")
    public Response getFilteredTrip(@Context HttpHeaders hh) throws JSONException
    {
        String token = hh.getHeaderString("token");
        if(token==null) {
            return fail("Missing token");
        } else {
            return addControllerCallback(usersService.getTrips(token));
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUser(@Context HttpServletResponse asyncResponse) throws JSONException
    {
        return addControllerCallback(usersService.getAll());
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
    @Path("{token}")
    public Response createUser(@PathParam("token") String token) throws JSONException
    {
        return addControllerCallback(usersService.createUser(token));
    }
}

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.hax.utils.ControllerHelper.addControllerCallback;

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
    public Response getUser(@PathParam("token") Long userId) throws JSONException
    {
        return addControllerCallback(usersService.getUser(userId));
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
    public Response createUser(final User user) throws JSONException
    {
        return addControllerCallback(usersService.createUser(user));
    }
}

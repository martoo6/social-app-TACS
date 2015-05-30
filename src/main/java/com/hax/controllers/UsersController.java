package com.hax.controllers;

import com.hax.models.User;
import com.hax.services.UsersServiceInterface;
import org.json.JSONException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

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
    public void getUser(@PathParam("userId") int userId, @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        addControllerCallback(usersService.getUser(userId), asyncResponse);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getAllUser(@Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        addControllerCallback(usersService.getAll(), asyncResponse);
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
        addControllerCallback(usersService.createUser(user), asyncResponse);
    }
}

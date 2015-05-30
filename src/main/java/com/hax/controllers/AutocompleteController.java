package com.hax.controllers;

import com.hax.services.AutocompleteServiceInterface;
import org.json.JSONException;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import static com.hax.utils.ControllerHelper.addControllerCallback;

@Singleton
@Service
@Path("autocomplete/airports")
public class AutocompleteController {
    @Inject
    AutocompleteServiceInterface autocompleteService;

    @Path("/{autocomplete}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void publishFlight(@PathParam("autocomplete") String autocomplete ,
                                @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        addControllerCallback(autocompleteService.getAirports(autocomplete), asyncResponse);
    }

}

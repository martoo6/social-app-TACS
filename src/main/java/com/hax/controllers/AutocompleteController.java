package com.hax.controllers;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Flight;
import com.hax.services.AutocompleteServiceInterface;
import com.hax.services.FlightsServiceInterface;
import com.hax.services.UsersServiceInterface;
import org.json.JSONException;
import org.json.JSONObject;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.hax.async.utils.FutureHelper.addControllerCallback;

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

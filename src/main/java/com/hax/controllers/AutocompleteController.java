package com.hax.controllers;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.executors.Default;
import com.hax.services.AutocompleteServiceInterface;
import org.json.JSONException;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

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
    public Response publishFlight(@PathParam("autocomplete") String autocomplete ,
                                @Context final HttpServletResponse asyncResponse) throws JSONException, ExecutionException, InterruptedException {
        return addControllerCallback(autocompleteService.getAirports(autocomplete));
    }
}

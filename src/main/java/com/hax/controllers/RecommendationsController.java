package com.hax.controllers;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Recommendation;
import com.hax.models.RecommendationPOST;
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

@Path("recommendations")
public class RecommendationsController {

    @Inject
    UsersServiceInterface usersService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getRecommendations(@Context HttpHeaders hh, @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        String optToken = hh.getHeaderString("token");
        if(optToken==null) {
            fail("Missing token", asyncResponse);
        } else {
            Integer token = Integer.parseInt(optToken);
            addControllerCallback(usersService.getRecommendations(token), asyncResponse);
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void recommendFlight(final RecommendationPOST recJson,
                                @Context HttpHeaders hh,
                                @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        String optToken = hh.getHeaderString("token");
        if(optToken==null) {
            fail("Missing token", asyncResponse);
        } else {
            Integer fromToken = Integer.parseInt(optToken);
            ListenableFuture<Recommendation> f = usersService.recommendFlight(recJson.getFlightId(), fromToken, recJson.getToUserId());
            addControllerCallback(f, asyncResponse);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{recommendationId}")
    public void acceptRecommendation(@PathParam("recommendationId") Integer recommendationId,
                                     @Context HttpHeaders hh,
                                     @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        String optToken = hh.getHeaderString("token");
        if(optToken==null) {
            fail("Missing token", asyncResponse);
        } else {
            Integer token = Integer.parseInt(optToken);
            addControllerCallback(usersService.acceptRecommendation(recommendationId, token), asyncResponse);
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{recommendationId}")
    public void rejectRecommendation(@PathParam("recommendationId") Integer recommendationId,
                                     @Context HttpHeaders hh,
                                     @Suspended final AsyncResponse asyncResponse) throws JSONException {
        String optToken = hh.getHeaderString("token");
        if(optToken==null) {
            fail("Missing token", asyncResponse);
        } else {
            Integer token = Integer.parseInt(optToken);
            addControllerCallback(usersService.rejectRecommendation(recommendationId, token), asyncResponse);
        }
    }
}

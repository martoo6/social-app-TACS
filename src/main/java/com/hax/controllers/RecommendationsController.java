package com.hax.controllers;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.utils.FutureHelper;
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

import static com.hax.async.utils.FutureHelper.addControllerCallback;

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
        Integer userId = Integer.parseInt(hh.getHeaderString("userId"));
        FutureHelper.addControllerCallback(usersService.getRecommendations(userId), asyncResponse);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void recommendFlight(final RecommendationPOST recJson,
                                @Context HttpHeaders hh,
                                @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        Integer fromUserId = Integer.parseInt(hh.getHeaderString("userId"));
        ListenableFuture<Recommendation> f= usersService.recommendFlight(recJson.getFlightId(), fromUserId, recJson.getToUserId());
        addControllerCallback(f, asyncResponse);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{recommendationId}")
    public void acceptRecommendation(@PathParam("recommendationId") Integer recommendationId,
                                     @Context HttpHeaders hh,
                                     @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        Integer userId = Integer.parseInt(hh.getHeaderString("userId"));
        addControllerCallback(usersService.acceptRecommendation(recommendationId, userId), asyncResponse);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{recommendationId}")
    public void rejectRecommendation(@PathParam("recommendationId") Integer recommendationId,
                                     @Context HttpHeaders hh,
                                     @Suspended final AsyncResponse asyncResponse) throws JSONException {
        Integer userId = Integer.parseInt(hh.getHeaderString("userId"));
        addControllerCallback(usersService.rejectRecommendation(recommendationId, userId), asyncResponse);
    }
}

package com.hax.controllers;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Recommendation;
import com.hax.models.RecommendationPOST;
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

@Path("recommendations")
public class RecommendationsController {

    @Inject
    UsersServiceInterface usersService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecommendations(@Context HttpHeaders hh) throws JSONException
    {
        String optToken = hh.getHeaderString("token");
        if(optToken==null) {
            return fail("Missing token");
        } else {
            Integer token = Integer.parseInt(optToken);
            return addControllerCallback(usersService.getRecommendations(token));
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response recommendFlight(final RecommendationPOST recJson,
                                @Context HttpHeaders hh) throws JSONException
    {
        String optToken = hh.getHeaderString("token");
        if(optToken==null) {
            return fail("Missing token");
        } else {
            Integer fromToken = Integer.parseInt(optToken);
            ListenableFuture<Recommendation> f = usersService.recommendFlight(recJson.getFlightId(), fromToken, recJson.getToUserId());
            return addControllerCallback(f);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{recommendationId}")
    public Response acceptRecommendation(@PathParam("recommendationId") Integer recommendationId,
                                     @Context HttpHeaders hh,
                                     @Context HttpServletResponse asyncResponse) throws JSONException
    {
        String optToken = hh.getHeaderString("token");
        if(optToken==null) {
            return fail("Missing token");
        } else {
            Integer token = Integer.parseInt(optToken);
            return addControllerCallback(usersService.acceptRecommendation(recommendationId, token));
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{recommendationId}")
    public Response rejectRecommendation(@PathParam("recommendationId") Integer recommendationId,
                                     @Context HttpHeaders hh,
                                     @Context HttpServletResponse asyncResponse) throws JSONException {
        String optToken = hh.getHeaderString("token");
        if(optToken==null) {
            return fail("Missing token");
        } else {
            Integer token = Integer.parseInt(optToken);
            return addControllerCallback(usersService.rejectRecommendation(recommendationId, token));
        }
    }
}

package com.hax.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
import org.json.JSONObject;

@Path("recommendations")
public class RecommendationsController {
    
    /**
     * 
     * Accepts, deny or leave recommendation pending
     * postData should include said status
     *
     * @param postData
     * @param recId
     * @return operation status
     * @throws JSONException
     */
    @Path("{recommendationId}") 
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String affectRecommendation(String postData,
            @PathParam("recommendationId") int recId) throws JSONException
    {
        return new JSONObject().put("success", true).toString();
    }

    /**
     * 
     * Notifies that a friend accepted the recommendation
     * 
     * @param postData
     * @param recId
     * @return operation status
     * @throws JSONException
     */
    @Path("{recommendationId}/notify/accepted") 
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String notifyaccepted(String postData,
            @PathParam("recommendationId") int recId) throws JSONException
    {
        return new JSONObject().put("success", true).toString();
    }

    /**
     * 
     * Notifies that a made a recommendation was made
     * 
     * @param postData
     * @param recId
     * @return operation status
     * @throws JSONException
     */
    @Path("{recommendationId}/notify/recommended") 
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String notifyRecommended(String postData,
            @PathParam("recommendationId") int recId) throws JSONException
    {
        return new JSONObject().put("success", true).toString();
    }
    
}

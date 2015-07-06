package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Trip;
import com.hax.models.Recommendation;
import com.hax.models.User;

import java.util.List;

/**
 * Created by martin on 4/27/15.
 */

public interface UsersServiceInterface {
    List<User> getAll();
    User getUser(String token);
    User createUser(String token);
    List<User> getFriends(String token);
    List<Trip> getTrips(String token);
    User update(User user);
    List<Recommendation> getRecommendations(String id);
    Recommendation recommendFlight(Long flightId,String fromUserId, String toUserId);
    Recommendation acceptRecommendation(Long recommendationId,String userId);
    Recommendation rejectRecommendation(Long recommendationId,String userId);
}

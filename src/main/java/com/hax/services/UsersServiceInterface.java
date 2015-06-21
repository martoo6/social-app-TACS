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
    ListenableFuture<List<User>> getAll();
    ListenableFuture<User> getUser(String token);
    ListenableFuture<User> createUser(String token);
    ListenableFuture<List<User>> getFriends(String token);
    ListenableFuture<List<Trip>> getTrips(String token);
    ListenableFuture<User> update(User user);
    ListenableFuture<List<Recommendation>> getRecommendations(String id);
    ListenableFuture<Recommendation> recommendFlight(Long flightId,String fromUserId, String toUserId);
    ListenableFuture<Recommendation> acceptRecommendation(Long recommendationId,String userId);
    ListenableFuture<Recommendation> rejectRecommendation(Long recommendationId,String userId);
    ListenableFuture<User> addFriend(String userId, String friendId);
    ListenableFuture<User> removeFriend(String userId, String friendId);
}

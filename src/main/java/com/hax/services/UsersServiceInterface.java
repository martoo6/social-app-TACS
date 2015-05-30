package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Flight;
import com.hax.models.Recommendation;
import com.hax.models.User;

import java.util.List;

/**
 * Created by martin on 4/27/15.
 */

public interface UsersServiceInterface {
    ListenableFuture<List<User>> getAll();
    ListenableFuture<User> getUser(Integer id);
    ListenableFuture<User> createUser(User user);
    ListenableFuture<List<User>> getFriends(Integer id);
    ListenableFuture<List<Flight>> getFlights(Integer id);
    ListenableFuture<User> update(User user);
    ListenableFuture<List<Recommendation>> getRecommendations(Integer id);
    ListenableFuture<Recommendation> recommendFlight(Integer flightId,Integer fromUserId, Integer toUserId);
    ListenableFuture<Recommendation> acceptRecommendation(Integer recommendationId,Integer userId);
    ListenableFuture<Recommendation> rejectRecommendation(Integer recommendationId,Integer userId);
    ListenableFuture<User> addFriend(Integer userId, Integer friendId);
    ListenableFuture<User> removeFriend(Integer userId, Integer friendId);
}

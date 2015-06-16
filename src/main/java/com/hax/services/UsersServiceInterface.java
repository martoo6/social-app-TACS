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
    ListenableFuture<User> getUser(Long id);
    ListenableFuture<User> createUser(User user);
    ListenableFuture<List<User>> getFriends(Long id);
    ListenableFuture<List<Trip>> getFlights(Long id);
    ListenableFuture<User> update(User user);
    ListenableFuture<List<Recommendation>> getRecommendations(Long id);
    ListenableFuture<Recommendation> recommendFlight(Long flightId,Long fromUserId, Long toUserId);
    ListenableFuture<Recommendation> acceptRecommendation(Long recommendationId,Long userId);
    ListenableFuture<Recommendation> rejectRecommendation(Long recommendationId,Long userId);
    ListenableFuture<User> addFriend(Long userId, Long friendId);
    ListenableFuture<User> removeFriend(Long userId, Long friendId);
}

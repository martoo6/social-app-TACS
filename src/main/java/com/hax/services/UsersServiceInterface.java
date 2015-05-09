package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Flight;
import com.hax.models.Recommendation;
import com.hax.models.User;

import java.util.ArrayList;

/**
 * Created by martin on 4/27/15.
 */

public interface UsersServiceInterface {
    ListenableFuture<ArrayList<User>> getAll();
    ListenableFuture<User> getUser(Integer id);
    ListenableFuture<User> createUser(User user);
    ListenableFuture<ArrayList<User>> getFriends(Integer id);
    ListenableFuture<ArrayList<Flight>> getFlights(Integer id);
    ListenableFuture<User> update(User user);
    ListenableFuture<ArrayList<Recommendation>> getRecommendations(Integer id);
    ListenableFuture<Recommendation> recommendFlight(Integer flightId,Integer fromUserId, Integer toUserId);
}

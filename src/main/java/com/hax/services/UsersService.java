package com.hax.services;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.FlightsRepositoryInterface;
import com.hax.connectors.UsersRepositoryInterface;
import com.hax.models.Flight;
import com.hax.models.Recommendation;
import com.hax.models.User;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Created by martin on 4/26/15.
 */
public class UsersService implements UsersServiceInterface {
    @Inject
    public UsersRepositoryInterface userRepository;

    public ListenableFuture<ArrayList<User>> getAll() {
        return userRepository.getAll();
    }

    public ListenableFuture<User> getUser(Integer id) {
        return userRepository.get(id);
    }

    public ListenableFuture<User> createUser(User user) {
        return userRepository.insert(user);
    }

    public ListenableFuture<ArrayList<User>> getFriends(Integer id) {
        return Futures.transform(userRepository.get(id), new Function<User, ArrayList<User>>() {
            public ArrayList<User> apply(User user) {
                return user.getFriends();
            }
        });
    }

    public ListenableFuture<ArrayList<Flight>> getFlights(Integer id) {
        return Futures.transform(userRepository.get(id), new Function<User, ArrayList<Flight>>() {
            public ArrayList<Flight> apply(User user) {
                return user.getFlights();
            }
        });
    }

    public ListenableFuture<ArrayList<Recommendation>> getRecommendations(Integer id) {
        return Futures.transform(userRepository.get(id), new Function<User, ArrayList<Recommendation>>() {
            public ArrayList<Recommendation> apply(User user) {
                return user.getRecommendations();
            }
        });
    }

    public ListenableFuture<User> update(User user) {
        return userRepository.update(user);
    }
}

package com.hax.services;

import com.google.common.base.Function;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.utils.FutureHelper;
import com.hax.async.utils.Tuple3;
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
    public UsersRepositoryInterface usersRepository;
    @Inject
    public FlightsRepositoryInterface flightsRepository;

    public ListenableFuture<ArrayList<User>> getAll() {
        return usersRepository.getAll();
    }

    public ListenableFuture<User> getUser(Integer id) {
        return usersRepository.get(id);
    }

    public ListenableFuture<User> createUser(User user) {
        return usersRepository.insert(user);
    }

    public ListenableFuture<ArrayList<User>> getFriends(Integer id) {
        return Futures.transform(usersRepository.get(id), new Function<User, ArrayList<User>>() {
            public ArrayList<User> apply(User user) {
                return user.getFriends();
            }
        });
    }

    public ListenableFuture<ArrayList<Flight>> getFlights(Integer id) {
        return Futures.transform(usersRepository.get(id), new Function<User, ArrayList<Flight>>() {
            public ArrayList<Flight> apply(User user) {
                return user.getFlights();
            }
        });
    }

    public ListenableFuture<ArrayList<Recommendation>> getRecommendations(Integer id) {
        return Futures.transform(usersRepository.get(id), new Function<User, ArrayList<Recommendation>>() {
            public ArrayList<Recommendation> apply(User user) {
                return user.getRecommendations();
            }
        });
    }

    public ListenableFuture<User> update(User user) {
        return usersRepository.update(user);
    }

    /**
     *
     * @param flightId
     * @param fromUserId
     * @param toUserId
     * @return
     */
    public ListenableFuture<Recommendation> recommendFlight(Integer flightId,Integer fromUserId, Integer toUserId){

        /**
         Hacemos 3 llamadas asincronicas a los repositorios y esperamos el resultado de las 3.
         El Future resultante es utilizado para insertar la recomendacion.
         Al no ir a los repositorios de manera secuencial se logra la maxima velocidad para obtener los resultados.
         */

        final ListenableFuture<Flight> flightF = flightsRepository.get(flightId);
        final ListenableFuture<User> fromUserF = usersRepository.get(fromUserId);
        final ListenableFuture<User> toUserF = usersRepository.get(toUserId);


        ListenableFuture<Tuple3<Flight,User,User>> compFuture = FutureHelper.compose(flightF, fromUserF, toUserF);

        return Futures.transform(compFuture, new AsyncFunction<Tuple3<Flight, User, User>, Recommendation>() {
            public ListenableFuture<Recommendation> apply(Tuple3<Flight, User, User> t) throws Exception {
                User toUser = t.getR3();
                final Recommendation recom = new Recommendation(t.getR1(), t.getR2());
                toUser.getRecommendations().add(recom);
                return Futures.transform(usersRepository.update(toUser), new Function<User, Recommendation>() {
                    public Recommendation apply(User user) {
                        return recom;
                    }
                });
            }
        });
    }
}

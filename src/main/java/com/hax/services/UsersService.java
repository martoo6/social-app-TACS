package com.hax.services;

import com.google.common.base.Function;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.utils.FutureHelper;
import com.hax.async.utils.Tuple2;
import com.hax.async.utils.Tuple3;
import com.hax.connectors.FlightsRepositoryInterface;
import com.hax.connectors.UsersRepositoryInterface;
import com.hax.models.Flight;
import com.hax.models.Recommendation;
import com.hax.models.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 4/26/15.
 */
public class UsersService implements UsersServiceInterface {
    @Inject
    public UsersRepositoryInterface usersRepository;
    @Inject
    public FlightsRepositoryInterface flightsRepository;

    public ListenableFuture<List<User>> getAll() {
        return usersRepository.getAll();
    }

    public ListenableFuture<User> getUser(Integer id) {
        return usersRepository.get(id);
    }

    public ListenableFuture<User> createUser(User user) {
        return usersRepository.insert(user);
    }

    public ListenableFuture<List<User>> getFriends(Integer id) {
        return Futures.transform(usersRepository.get(id), new Function<User, List<User>>() {
            public List<User> apply(User user) {
                return user.getFriends();
            }
        });
    }

    public ListenableFuture<List<Flight>> getFlights(Integer id) {
        return Futures.transform(usersRepository.get(id), new Function<User, List<Flight>>() {
            public List<Flight> apply(User user) {
                return user.getFlights();
            }
        });
    }

    public ListenableFuture<List<Recommendation>> getRecommendations(Integer id) {
        return Futures.transform(usersRepository.get(id), new Function<User, List<Recommendation>>() {
            public List<Recommendation> apply(User user) {
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

    public ListenableFuture<Recommendation> acceptRecommendation(Integer recommendationId,Integer userId) {
        return setRecommendationState(recommendationId, "Accepted", userId);
    }

    public ListenableFuture<Recommendation> rejectRecommendation(Integer recommendationId,Integer userId) {
        return setRecommendationState(recommendationId, "Rejected", userId);
    }

    private ListenableFuture<Recommendation> setRecommendationState(final Integer recommendationId, final String state,Integer userId){
        return Futures.transform(usersRepository.get(userId), new Function<User, Recommendation>() {
                    public Recommendation apply(User user) {

                        List<Recommendation> lst = user.getRecommendations();
                        Recommendation r = null;
                        for (Recommendation tmpR : lst) if (tmpR.getId() == recommendationId) r=tmpR;
                        if (r == null) throw new RuntimeException("Recommendation not found");
                        r.setState(state);
                        usersRepository.update(user);
                        return r;
                    }
                }
        );
    }

    public ListenableFuture<User> addFriend(Integer userId, Integer friendId) {
        ListenableFuture<User> userFuture = usersRepository.get(userId);
        ListenableFuture<User> friendFuture = usersRepository.get(friendId);

        ListenableFuture<Tuple2<User, User>> fTuple = FutureHelper.compose(userFuture, friendFuture);

        return Futures.transform(fTuple, new Function<Tuple2<User,User>, User>() {
            public User apply(Tuple2<User, User> tuple) {
                User user = tuple.getR1();
                User friend = tuple.getR2();
                user.getFriends().add(friend);
                usersRepository.update(user);
                return user;
            }
        });
    }

    public ListenableFuture<User> removeFriend(Integer userId, Integer friendId) {
        ListenableFuture<User> userFuture = usersRepository.get(userId);
        ListenableFuture<User> friendFuture = usersRepository.get(friendId);

        ListenableFuture<Tuple2<User, User>> fTuple = FutureHelper.compose(userFuture, friendFuture);

        return Futures.transform(fTuple, new Function<Tuple2<User,User>, User>() {
            public User apply(Tuple2<User, User> tuple) {
                User user = tuple.getR1();
                User friend = tuple.getR2();
                user.getFriends().remove(friend);
                usersRepository.update(user);
                return user;
            }
        });
    }
}

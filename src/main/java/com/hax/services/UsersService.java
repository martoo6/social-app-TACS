package com.hax.services;

import com.google.common.base.Function;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureFallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.utils.FutureHelper;
import com.hax.async.utils.Tuple2;
import com.hax.async.utils.Tuple3;
import com.hax.connectors.*;
import com.hax.models.*;
import com.hax.models.fb.FbFriend;
import com.hax.models.fb.FbFriends;
import com.hax.models.fb.FbVerify;

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
    public TripsRepositoryInterface tripsRespository;
    @Inject
    public FacebookConnectorInterface facebookConnector;
    @Inject
    public AirportsConnectorInterface airportsConnector;
    @Inject
    public RecommendationsRepositoryInterface recommendationsRepository;

    public ListenableFuture<List<User>> getAll() {
        return usersRepository.getAll();
    }

    public ListenableFuture<User> getUser(String id) {
        return usersRepository.get(id);
    }

    public ListenableFuture<User> createUser(final String token) {
        return Futures.transform(facebookConnector.verifyAccessToken(token), new AsyncFunction<FbVerify, User>() {
            @Override
            public ListenableFuture<User> apply(final FbVerify fbVerify) throws Exception {
                return Futures.withFallback(usersRepository.get(fbVerify.getId()), new FutureFallback<User>() {
                    @Override
                    public ListenableFuture<User> create(Throwable throwable) throws Exception {

                        return Futures.transform(facebookConnector.getUserFriends(token), new AsyncFunction<FbFriends, User>() {
                            @Override
                            public ListenableFuture<User> apply(FbFriends fbFriends) throws Exception {
                                ArrayList<ListenableFuture<User>> lst = new ArrayList<ListenableFuture<User>>();
                                for (FbFriend fbFriend : fbFriends.getData()) {
                                    lst.add(usersRepository.get(fbFriend.getId()));
                                }
                                return Futures.transform(Futures.successfulAsList(lst), new AsyncFunction<List<User>, User>() {
                                    @Override
                                    public ListenableFuture<User> apply(List<User> friends) throws Exception {
                                        User newUser = new User(fbVerify);
                                        for (User friend : friends) {
                                            newUser.getFriends().add(friend.getId());
                                            friend.getFriends().add(newUser.getId());
                                            usersRepository.update(friend);
                                        }
                                        return usersRepository.insert(newUser);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    public ListenableFuture<List<User>> getFriends(final String token) {


        ListenableFuture<User> userF = Futures.transform(facebookConnector.verifyAccessToken(token), new AsyncFunction<FbVerify, User>() {
            @Override
            public ListenableFuture<User> apply(FbVerify fbVerify) throws Exception {
                return usersRepository.get(fbVerify.getId());
            }
        });

        return Futures.transform(userF, new AsyncFunction<User, List<User>>() {
            public ListenableFuture<List<User>> apply(User user) {
                ArrayList<ListenableFuture<User>> lst = new ArrayList<ListenableFuture<User>>();
                for(String id:user.getFriends()){
                    lst.add(usersRepository.get(id));
                }
                return Futures.allAsList(lst);
            }
        });
    }

    public ListenableFuture<List<Trip>> getTrips(final String token) {
        ListenableFuture<User> userF = Futures.transform(facebookConnector.verifyAccessToken(token), new AsyncFunction<FbVerify, User>() {
            @Override
            public ListenableFuture<User> apply(FbVerify fbVerify) throws Exception {
                return usersRepository.get(fbVerify.getId());
            }
        });

        return Futures.transform(userF, new AsyncFunction<User, List<Trip>>() {
            public ListenableFuture<List<Trip>> apply(User user) {
                ArrayList<ListenableFuture<Trip>> lst = new ArrayList<ListenableFuture<Trip>>();
                for(Long id:user.getTrips()){
                    lst.add(tripsRespository.get(id));
                }
                return Futures.allAsList(lst);
            }
        });
    }

    public ListenableFuture<List<Recommendation>> getRecommendations(final String token) {
        ListenableFuture<User> userF = Futures.transform(facebookConnector.verifyAccessToken(token), new AsyncFunction<FbVerify, User>() {
            @Override
            public ListenableFuture<User> apply(FbVerify fbVerify) throws Exception {
                return usersRepository.get(fbVerify.getId());
            }
        });

        return Futures.transform(userF, new AsyncFunction<User, List<Recommendation>>() {
            public ListenableFuture<List<Recommendation>> apply(User user) {

                ArrayList<ListenableFuture<Recommendation>> lst = new ArrayList<ListenableFuture<Recommendation>>();
                for(Long id:user.getRecommendations()){
                    lst.add(recommendationsRepository.get(id));
                }
                return Futures.allAsList(lst);
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
    public ListenableFuture<Recommendation> recommendFlight(Long flightId, final String token, String toUserId){

        /**
         Hacemos 3 llamadas asincronicas a los repositorios y esperamos el resultado de las 3.
         El Future resultante es utilizado para insertar la recomendacion.
         Al no ir a los repositorios de manera secuencial se logra la maxima velocidad para obtener los resultados.
         */

        final ListenableFuture<User> fromUserF =  Futures.transform(facebookConnector.verifyAccessToken(token), new AsyncFunction<FbVerify, User>() {
            @Override
            public ListenableFuture<User> apply(FbVerify fbVerify) throws Exception {
                return usersRepository.get(fbVerify.getId());
            }
        });

        final ListenableFuture<Trip> flightF = tripsRespository.get(flightId);
        final ListenableFuture<User> toUserF = usersRepository.get(toUserId);


        ListenableFuture<Tuple3<Trip,User,User>> compFuture = FutureHelper.compose(flightF, fromUserF, toUserF);

        return Futures.transform(compFuture, new AsyncFunction<Tuple3<Trip, User, User>, Recommendation>() {
            public ListenableFuture<Recommendation> apply(final Tuple3<Trip, User, User> t) throws Exception {

                final Recommendation tmpRecom = new Recommendation(t.getR1().getId(), t.getR2().getId(), t.getR3().getId());

                return Futures.transform(recommendationsRepository.insert(tmpRecom), new AsyncFunction<Recommendation, Recommendation>() {
                    @Override
                    public ListenableFuture<Recommendation> apply(final Recommendation recommendation) throws Exception {
                        User toUser = t.getR3();
                        toUser.getRecommendations().add(recommendation.getId());
                        return Futures.transform(usersRepository.update(toUser), new AsyncFunction<User, Recommendation>() {
                            public ListenableFuture<Recommendation> apply(User user) {
                                return Futures.transform(airportsConnector.getAirportAsync(t.getR1().getDestiny()), new Function<AirportResponse, Recommendation>() {
                                    @Override
                                    public Recommendation apply(AirportResponse airportResponse) {
                                        facebookConnector.publishNotification(token, tmpRecom.getToUserId() ,t.getR2().getUsername() + " te ha recomendado un viaje a " + airportResponse.getCity());
                                        return recommendation;
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }


    public ListenableFuture<Recommendation> acceptRecommendation(final Long recommendationId, final String token) {

        return Futures.transform(facebookConnector.verifyAccessToken(token), new AsyncFunction<FbVerify, Recommendation>() {
            @Override
            public ListenableFuture<Recommendation> apply(FbVerify fbVerify) throws Exception {
                ListenableFuture<User> userF = usersRepository.get(fbVerify.getId());
                ListenableFuture<Recommendation> recomF = recommendationsRepository.get(recommendationId);

                return Futures.transform(FutureHelper.compose(userF, recomF), new AsyncFunction<Tuple2<User, Recommendation>, Recommendation>() {
                    @Override
                    public ListenableFuture<Recommendation> apply(Tuple2<User, Recommendation> t) throws Exception {
                        final Recommendation recom = t.getR2();
                        recom.setState(RecommendationState.ACCEPTED);

                        Futures.transform(tripsRespository.get(recom.getTrip()), new AsyncFunction<Trip, String>() {
                            @Override
                            public ListenableFuture<String> apply(Trip trip) throws Exception {
                                return Futures.transform(airportsConnector.getAirportAsync(trip.getOrigin()), new AsyncFunction<AirportResponse, String>() {
                                    @Override
                                    public ListenableFuture<String> apply(AirportResponse airportResponse) throws Exception {
                                        return facebookConnector.publishNotification(token, recom.getFromUserId(), "Han aceptado tu recomendacion a " + airportResponse.getCity());
                                    }
                                });
                            }
                        });

                        return recommendationsRepository.update(recom);
                    }
                });
            }
        });
    }

    public ListenableFuture<Recommendation> rejectRecommendation(final Long recommendationId, final String token) {

        return Futures.transform(facebookConnector.verifyAccessToken(token), new AsyncFunction<FbVerify, Recommendation>() {
            @Override
            public ListenableFuture<Recommendation> apply(FbVerify fbVerify) throws Exception {
                ListenableFuture<User> userF = usersRepository.get(fbVerify.getId());
                ListenableFuture<Recommendation> recomF = recommendationsRepository.get(recommendationId);

                return Futures.transform(FutureHelper.compose(userF, recomF), new AsyncFunction<Tuple2<User, Recommendation>, Recommendation>() {
                    @Override
                    public ListenableFuture<Recommendation> apply(Tuple2<User, Recommendation> t) throws Exception {
                        final Recommendation recom = t.getR2();
                        recom.setState(RecommendationState.REJECTED);
                        return recommendationsRepository.update(recom);
                    }
                });
            }
        });
    }

    public ListenableFuture<User> addFriend(String userId, String friendId) {
        ListenableFuture<User> userFuture = usersRepository.get(userId);
        ListenableFuture<User> friendFuture = usersRepository.get(friendId);

        ListenableFuture<Tuple2<User, User>> fTuple = FutureHelper.compose(userFuture, friendFuture);

        return Futures.transform(fTuple, new Function<Tuple2<User,User>, User>() {
            public User apply(Tuple2<User, User> tuple) {
                User user = tuple.getR1();
                User friend = tuple.getR2();
                user.getFriends().add(friend.getId());
                friend.getFriends().add(user.getId());
                usersRepository.update(friend);
                usersRepository.update(user);
                return user;
            }
        });
    }

    public ListenableFuture<User> removeFriend(String userId, String friendId) {
        ListenableFuture<User> userFuture = usersRepository.get(userId);
        ListenableFuture<User> friendFuture = usersRepository.get(friendId);

        ListenableFuture<Tuple2<User, User>> fTuple = FutureHelper.compose(userFuture, friendFuture);

        return Futures.transform(fTuple, new Function<Tuple2<User,User>, User>() {
            public User apply(Tuple2<User, User> tuple) {
                User user = tuple.getR1();
                User friend = tuple.getR2();
                user.getFriends().remove(friend.getId());
                friend.getFriends().remove(user.getId());
                usersRepository.update(friend);
                usersRepository.update(user);
                return user;
            }
        });
    }
}

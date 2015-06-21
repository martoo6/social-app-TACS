package com.hax.services;

import com.google.common.base.Function;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureFallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.utils.FutureHelper;
import com.hax.async.utils.Tuple2;
import com.hax.async.utils.Tuple3;
import com.hax.connectors.FacebookConnectorInterface;
import com.hax.connectors.TripsRepositoryInterface;
import com.hax.connectors.UsersRepositoryInterface;
import com.hax.models.Trip;
import com.hax.models.Recommendation;
import com.hax.models.RecommendationState;
import com.hax.models.User;
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
                                for(FbFriend fbFriend: fbFriends.getData()){
                                    lst.add(usersRepository.get(fbFriend.getId()));
                                }
                                return Futures.transform(Futures.successfulAsList(lst), new AsyncFunction<List<User>, User>() {
                                    @Override
                                    public ListenableFuture<User> apply(List<User> friends) throws Exception {
                                        User newUser = new User(fbVerify);
                                        for(User friend :friends)  {
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

        return Futures.transform(userF, new Function<User, List<Recommendation>>() {
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
    public ListenableFuture<Recommendation> recommendFlight(Long flightId,String fromUserId, String toUserId){

        /**
         Hacemos 3 llamadas asincronicas a los repositorios y esperamos el resultado de las 3.
         El Future resultante es utilizado para insertar la recomendacion.
         Al no ir a los repositorios de manera secuencial se logra la maxima velocidad para obtener los resultados.
         */

        final ListenableFuture<Trip> flightF = tripsRespository.get(flightId);
        final ListenableFuture<User> fromUserF = usersRepository.get(fromUserId);
        final ListenableFuture<User> toUserF = usersRepository.get(toUserId);


        ListenableFuture<Tuple3<Trip,User,User>> compFuture = FutureHelper.compose(flightF, fromUserF, toUserF);

        return Futures.transform(compFuture, new AsyncFunction<Tuple3<Trip, User, User>, Recommendation>() {
            public ListenableFuture<Recommendation> apply(Tuple3<Trip, User, User> t) throws Exception {
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

    public ListenableFuture<Recommendation> acceptRecommendation(Long recommendationId,String userId) {
        return setRecommendationState(recommendationId, RecommendationState.ACCEPTED, userId);
    }

    public ListenableFuture<Recommendation> rejectRecommendation(Long recommendationId,String userId) {
        return setRecommendationState(recommendationId, RecommendationState.REJECTED, userId);
    }

    private ListenableFuture<Recommendation> setRecommendationState(final Long recommendationId, final RecommendationState state, String userId){
        return Futures.transform(usersRepository.get(userId), new Function<User, Recommendation>() {
                    public Recommendation apply(User user) {

                        List<Recommendation> lst = user.getRecommendations();
                        Recommendation r = null;
                        for (Recommendation tmpR : lst) if (tmpR.getId() == recommendationId) r=tmpR;
                        if (r == null) throw new RuntimeException("Missing Recommendation");
                        r.setState(state);
                        usersRepository.update(user);
                        return r;
                    }
                }
        );
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

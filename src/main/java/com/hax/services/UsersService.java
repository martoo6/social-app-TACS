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

    public List<User> getAll() {
        return usersRepository.getAll();
    }

    public User getUser(String id) {
        return usersRepository.get(id);
    }

    public User createUser(final String token) {
        FbVerify fbVerify = facebookConnector.verifyAccessToken(token);
        User user = usersRepository.get(fbVerify.getId());

        if(user==null){
            user = new User(fbVerify);
            setFriends(user, token);
            return usersRepository.insert(user);
        }
        setFriends(user, token);
        return usersRepository.update(user);
    }


    private void setFriends(User user,String token){
        FbFriends fbFriends = facebookConnector.getUserFriends(token);
        //Facebook trae todos los amigos de un usuario que tambien tengan isntalda la app, porq lo que tienen que existir en la app
        for (FbFriend fbFriend : fbFriends.getData()) {
            User friend = usersRepository.get(fbFriend.getId());
            if(friend != null && !user.getFriends().contains(fbFriend.getId())){
                user.getFriends().add(friend.getFacebookId());
                friend.getFriends().add(user.getFacebookId());
                usersRepository.update(friend);
            }
        }
    }

    public List<User> getFriends(final String token) {

        FbVerify fbVerify = facebookConnector.verifyAccessToken(token);
        User user = usersRepository.get(fbVerify.getId());
        if(user!=null) {
            ArrayList<User> lst = new ArrayList<User>();
            for (String id : user.getFriends()) {
                lst.add(usersRepository.get(id));
            }
            return lst;
        }
        return null;
    }

    public List<Trip> getTrips(final String token) {
        FbVerify fbVerify = facebookConnector.verifyAccessToken(token);
        User user = usersRepository.get(fbVerify.getId());

        if(user!=null) {
            ArrayList<Trip> lst = new ArrayList<Trip>();
            for(Long id:user.getTrips()){
                lst.add(tripsRespository.get(id));
            }
            return lst;
        }
        return null;
    }

    public List<Recommendation> getRecommendations(final String token) {
        FbVerify fbVerify = facebookConnector.verifyAccessToken(token);
        User user = usersRepository.get(fbVerify.getId());

        if(user!=null) {
            ArrayList<Recommendation> lst = new ArrayList<Recommendation>();
            for(Long id:user.getRecommendations()){
                lst.add(recommendationsRepository.get(id));
            }
            return lst;
        }
        return null;
    }

    public User update(User user) {
        return usersRepository.update(user);
    }

    /**
     *
     * @param flightId
     * @param toUserId
     * @return
     */
    public Recommendation recommendFlight(Long flightId, final String token, String toUserId){

        FbVerify fbVerify =facebookConnector.verifyAccessToken(token);
        User fromUser = usersRepository.get(fbVerify.getId());
        Trip flight = tripsRespository.get(flightId);
        User toUser = usersRepository.get(toUserId);
        Recommendation tmpRecom = new Recommendation(flight.getId(), fromUser.getFacebookId(), toUser.getFacebookId());
        Recommendation recommendation = recommendationsRepository.insert(tmpRecom);
        toUser.getRecommendations().add(recommendation.getId());
        usersRepository.update(toUser);
        AirportResponse airportResponse = airportsConnector.getAirportAsync(flight.getDestiny());
        facebookConnector.publishNotification(token, tmpRecom.getToUserId(), fromUser.getUsername() + " te ha recomendado un viaje a " + airportResponse.getCity());

        return recommendation;
    }


    public Recommendation acceptRecommendation(final Long recommendationId, final String token) {
        FbVerify fbVerify = facebookConnector.verifyAccessToken(token);
        User user = usersRepository.get(fbVerify.getId()); //Si no lo encuentra no sigue la ejecucion
        if(user!=null){
            Recommendation recom = recommendationsRepository.get(recommendationId);
            recom.setState(RecommendationState.ACCEPTED);
            Trip trip = tripsRespository.get(recom.getTrip());
            AirportResponse airportResponse = airportsConnector.getAirportAsync(trip.getOrigin());
            facebookConnector.publishNotification(token, recom.getFromUserId(), "Han aceptado tu recomendacion a " + airportResponse.getCity());
            return recommendationsRepository.update(recom);
        }
        return null;
    }

    public Recommendation rejectRecommendation(final Long recommendationId, final String token) {
        FbVerify fbVerify = facebookConnector.verifyAccessToken(token);
        User user = usersRepository.get(fbVerify.getId()); //Si no lo encuentra no sigue la ejecucion
        if(user!=null){
            Recommendation recom = recommendationsRepository.get(recommendationId);
            recom.setState(RecommendationState.REJECTED);
            return recommendationsRepository.update(recom);
        }
        return null;
    }
}

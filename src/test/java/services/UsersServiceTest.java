package services;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.*;
import com.hax.models.*;
import com.hax.models.fb.FbFriend;
import com.hax.models.fb.FbFriends;
import com.hax.models.fb.FbVerify;
import com.hax.services.UsersService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by martin on 5/2/15.
 */
public class UsersServiceTest extends GenericTest {

    @Test
    public void getAllUsers() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User user = new User();

        when(ur.getAll()).thenReturn((Arrays.asList(user)));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        List<User> lf = us.getAll();

        try {
            List<User> ul = lf;
            assertTrue(ul.contains(user));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void getUser() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User user = new User();

        when(ur.get("1")).thenReturn((user));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        User lf = us.getUser("1");

        try {
            User u = lf;
            assertTrue(u==user);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void getUserMissing() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        when(ur.get("1")).thenReturn(null);


        UsersService us = new UsersService();
        us.usersRepository = ur;

        assertNull(us.getUser("1"));
    }

    @Test
    public void createUser() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        FacebookConnectorInterface fC = mock(FacebookConnectorInterface.class);


        User friend = new User();
        friend.setFacebookId("1234");

        User user = new User();
        user.setFacebookId("1");
        user.setUsername("pepito");
        user.getFriends().add(friend.getFacebookId());

        FbVerify fb = new FbVerify();
        fb.setId("1");
        fb.setName("pepito");
        fb.setGender("male");

        FbFriend fbFriend1 = new FbFriend();
        FbFriend fbFriend2 = new FbFriend();
        fbFriend1.setId("1234");
        fbFriend1.setName("Amigo1");
        fbFriend2.setId("4321");
        fbFriend2.setName("Amigo2");

        ArrayList<FbFriend> friendsLst = new ArrayList<FbFriend>();
        //friendsLst.addAll(Arrays.asList(fbFriend1, fbFriend2));
        friendsLst.addAll(Arrays.asList(fbFriend1));

        FbFriends fbFriends = new FbFriends();
        fbFriends.setData(friendsLst);

        when(ur.insert(any(User.class))).thenReturn((user));
        when(ur.get("1")).thenReturn(null);
        when(ur.get("1234")).thenReturn((friend));
        //when(ur.get("4321")).thenReturn(Futures.<User>immediateFailedFuture(new RuntimeException("No existe el usuario")));
        when(fC.verifyAccessToken(any(String.class))).thenReturn((fb));
        when(fC.getUserFriends(any(String.class))).thenReturn((fbFriends));

        UsersService us = new UsersService();
        us.usersRepository = ur;
        us.facebookConnector = fC;

        User lf = us.createUser("token-de-prueba");

        try {
            User u = lf;
            assertTrue(u.getFacebookId().equals(fb.getId()));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void getFriends() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
        FbVerify fbVerify = new FbVerify();
        fbVerify.setId("1");

        User user = new User();
        List<String> friends = user.getFriends();
        User friend = new User();
        friend.setFacebookId("1234");
        friends.add(friend.getFacebookId());

        when(ur.get("1")).thenReturn((user));
        when(fb.verifyAccessToken(anyString())).thenReturn(fbVerify);
        when(ur.get("1234")).thenReturn((friend));

        UsersService us = new UsersService();
        us.usersRepository = ur;
        us.facebookConnector =  fb;

        List<User> lf = us.getFriends("1");

        try {
            List<User> ul = lf;
            assertTrue(ul.contains(friend));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void getFriendsUserMissing() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
        FbVerify fbVerify = new FbVerify();
        fbVerify.setId("2");


        when(ur.get(anyString())).thenReturn(null);
        when(fb.verifyAccessToken(anyString())).thenReturn(fbVerify);

        UsersService us = new UsersService();
        us.usersRepository = ur;
        us.facebookConnector = fb;

        assertNull(us.getFriends("1"));
    }

    @Test
    public void getFlights() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
        TripsRepositoryInterface tr = mock(TripsRepositoryInterface.class);

        FbVerify fbVerify = new FbVerify();
        fbVerify.setId("1");

        User user = new User();
        user.setFacebookId("1");
        List<Long> trips = user.getTrips();
        Trip trip = new Trip();
        trip.setId(1234L);
        trips.add(trip.getId());

        when(ur.get(anyString())).thenReturn((user));
        when(fb.verifyAccessToken(anyString())).thenReturn(fbVerify);
        when(tr.get(anyLong())).thenReturn((trip));

        UsersService us = new UsersService();
        us.usersRepository = ur;
        us.facebookConnector = fb;
        us.tripsRespository = tr;

        List<Trip> lf = us.getTrips("1");

        List<Trip> ul = lf;
        assertTrue(ul.contains(trip));
    }

    @Test
    public void getFlightsMissingUser() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
        FbVerify fbVerify = new FbVerify();
        fbVerify.setId("1");


        User user = new User();
        List<Long> trips = user.getTrips();
        Trip trip = new Trip();
        trip.setId(1234L);
        trips.add(trip.getId());

        when(ur.get(anyString())).thenReturn(null);
        when(fb.verifyAccessToken(anyString())).thenReturn(fbVerify);

        UsersService us = new UsersService();
        us.usersRepository = ur;
        us.facebookConnector = fb;

        assertNull(us.getTrips("1"));
    }

    @Test
    public void getRecommendations() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
        RecommendationsRepositoryInterface rr = mock(RecommendationsRepositoryInterface.class);

        FbVerify fbVerify = new FbVerify();
        fbVerify.setId("1234");

        User user = new User();
        user.setFacebookId("1234");
        List<Long> recommendations = user.getRecommendations();
        recommendations.add(120L);

        Recommendation r = new Recommendation(120L, "1234", "4567");

        when(ur.get("1234")).thenReturn((user));
        when(fb.verifyAccessToken("abcd")).thenReturn(fbVerify);
        when(rr.get(120L)).thenReturn((r));

        UsersService us = new UsersService();
        us.usersRepository = ur;
        us.facebookConnector = fb;
        us.recommendationsRepository = rr;

        List<Recommendation> lf = us.getRecommendations("abcd");

        List<Recommendation> lstRec = lf;
        assertTrue(lstRec.contains(r));
    }

    @Test
    public void getRecommendationsMissingUser() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
        FbVerify fbVerify = new FbVerify();
        fbVerify.setId("1");

        when(ur.get(anyString())).thenReturn(null);
        when(fb.verifyAccessToken(anyString())).thenReturn(fbVerify);

        UsersService us = new UsersService();
        us.usersRepository = ur;
        us.facebookConnector = fb;

        assertNull(us.getRecommendations("1"));
    }

    @Test
    public void update() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);

        User user = new User();

        when(ur.update(any(User.class))).thenReturn((user));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        User lf = us.update(user);

        try {
            User u = lf;
            assertTrue(u == user);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void recommendFlight() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        TripsRepositoryInterface fr = mock(TripsRepositoryInterface.class);
        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
        RecommendationsRepositoryInterface rr = mock(RecommendationsRepositoryInterface.class);
        AirportsConnectorInterface airportsConnector = mock(AirportsConnectorInterface.class);

        User user = new User();
        user.setFacebookId("1234");

        User friend = new User();
        friend.setFacebookId("2345");

        Trip trip = new Trip();
        trip.setId(0L);
        trip.setDestiny("BUE");

        FbVerify fbVerify = new FbVerify();
        fbVerify.setId("1234");

        Recommendation rec= new Recommendation(trip.getId(), user.getFacebookId(), friend.getFacebookId());
        rec.setId(10L);

        AirportResponse ar = new AirportResponse();
        ar.setCity("Buenos Aires");

        when(ur.get("1234")).thenReturn((user));
        when(ur.get("2345")).thenReturn((friend));
        when(ur.update(friend)).thenReturn((friend));
        when(fr.get(0L)).thenReturn((trip));
        when(fb.verifyAccessToken("abc")).thenReturn(fbVerify);
        when(rr.insert(any(Recommendation.class))).thenReturn((rec));
        when(airportsConnector.getAirportAsync("BUE")).thenReturn((ar));

        UsersService us = new UsersService();
        us.usersRepository = ur;
        us.tripsRespository = fr;
        us.facebookConnector = fb;
        us.recommendationsRepository = rr;
        us.airportsConnector = airportsConnector;

        Recommendation lf = us.recommendFlight(0L, "abc", "2345");


        Recommendation r = lf;
        assertTrue(r.getTrip()== trip.getId());
        assertTrue(r.getFromUserId() == user.getFacebookId());
        assertTrue(r.getState()== RecommendationState.PENDING);

    }

    @Test
    public void recommendFlightMissingFlight() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        TripsRepositoryInterface fr = mock(TripsRepositoryInterface.class);
        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);


        User user = new User();
        user.setFacebookId("1234");

        User friend = new User();
        friend.setFacebookId("2");

        FbVerify fbVerify = new FbVerify();
        fbVerify.setId("1234");

        when(ur.get("1")).thenReturn((user));
        when(ur.get("2")).thenReturn((friend));
        when(ur.update(friend)).thenReturn((friend));
        when(fr.get(0L)).thenReturn(null);
        when(fb.verifyAccessToken(anyString())).thenReturn(fbVerify);


        UsersService us = new UsersService();
        us.usersRepository = ur;
        us.tripsRespository = fr;
        us.facebookConnector = fb;

        assertNull(us.recommendFlight(0L, "1", "2"));
    }

    @Test
    public void acceptRecommendation() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
        RecommendationsRepositoryInterface rr = mock(RecommendationsRepositoryInterface.class);
        TripsRepositoryInterface tripsRepository = mock(TripsRepositoryInterface.class);

        User user = new User();
        user.setFacebookId("1234");
        user.getRecommendations().add(333L);

        FbVerify fbVerify = new FbVerify();
        fbVerify.setId("1234");

        Recommendation rec = new Recommendation(333L,"1234","4567");

        Trip trip = new Trip();
        trip.setDestiny("BUE");
        trip.setDestinyDescription("Buenos Aires");

        when(ur.get("1234")).thenReturn((user));
        when(ur.update(user)).thenReturn((user));
        when(fb.verifyAccessToken(anyString())).thenReturn(fbVerify);
        when(fb.publishNotification(anyString(), anyString())).thenReturn((""));
        when(rr.get(anyLong())).thenReturn((rec));
        when(tripsRepository.get(anyLong())).thenReturn((trip));
        when(rr.update(any(Recommendation.class))).thenReturn((rec));


        UsersService us = new UsersService();
        us.usersRepository = ur;
        us.facebookConnector = fb;
        us.recommendationsRepository = rr;
        us.tripsRespository = tripsRepository;

        Recommendation lf = us.acceptRecommendation(4L, "1");

        assertTrue(lf.getState()==RecommendationState.ACCEPTED);
    }

    @Test
    public void acceptRecommendationMissingRecommendation() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
        RecommendationsRepositoryInterface rr = mock(RecommendationsRepositoryInterface.class);

        User user = new User();
        user.setFacebookId("1234");

        FbVerify fbVerify = new FbVerify();
        fbVerify.setId("1234");

        when(ur.get("1234")).thenReturn((user));
        when(ur.update(user)).thenReturn((user));
        when(fb.verifyAccessToken(anyString())).thenReturn(fbVerify);
        when(fb.publishNotification(anyString(), anyString())).thenReturn((""));
        when(rr.get(anyLong())).thenReturn(null);


        UsersService us = new UsersService();
        us.usersRepository = ur;
        us.facebookConnector = fb;
        us.recommendationsRepository = rr;

        assertNull(us.acceptRecommendation(4L, "1"));
    }

    @Test
    public void rejectRecommendation() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
        RecommendationsRepositoryInterface rr = mock(RecommendationsRepositoryInterface.class);
        TripsRepositoryInterface tripsRepository = mock(TripsRepositoryInterface.class);

        User user = new User();
        user.setFacebookId("1234");
        user.getRecommendations().add(333L);

        FbVerify fbVerify = new FbVerify();
        fbVerify.setId("1234");

        Recommendation rec = new Recommendation(333L,"1234","4567");

        AirportResponse ar = new AirportResponse();
        ar.setCity("Buenos Aires");

        Trip trip = new Trip();
        trip.setDestiny("BUE");

        when(ur.get("1234")).thenReturn((user));
        when(ur.update(user)).thenReturn((user));
        when(fb.verifyAccessToken(anyString())).thenReturn(fbVerify);
        when(fb.publishNotification(anyString(), anyString())).thenReturn((""));
        when(rr.get(anyLong())).thenReturn((rec));
        when(tripsRepository.get(anyLong())).thenReturn((trip));
        when(rr.update(any(Recommendation.class))).thenReturn((rec));


        UsersService us = new UsersService();
        us.usersRepository = ur;
        us.facebookConnector = fb;
        us.recommendationsRepository = rr;
        us.tripsRespository = tripsRepository;

        Recommendation r = us.rejectRecommendation(4L, "1");
        assertTrue(r.getState()==RecommendationState.REJECTED);

    }

    @Test
    public void rejectRecommendationMissingRecommendation() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
        RecommendationsRepositoryInterface rr = mock(RecommendationsRepositoryInterface.class);

        User user = new User();
        user.setFacebookId("1234");

        FbVerify fbVerify = new FbVerify();
        fbVerify.setId("1234");

        when(ur.get("1234")).thenReturn((user));
        when(ur.update(user)).thenReturn((user));
        when(fb.verifyAccessToken(anyString())).thenReturn(fbVerify);
        when(fb.publishNotification(anyString(), anyString())).thenReturn((""));
        when(rr.get(anyLong())).thenReturn(null);


        UsersService us = new UsersService();
        us.usersRepository = ur;
        us.facebookConnector = fb;
        us.recommendationsRepository = rr;

        assertNull(us.rejectRecommendation(4L, "1"));
    }

    @Override
    protected AbstractBinder setBinder() {
        return null;
    }

}

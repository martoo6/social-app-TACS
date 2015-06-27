//package services;
//
//import com.google.common.util.concurrent.Futures;
//import com.google.common.util.concurrent.ListenableFuture;
//import com.hax.connectors.*;
//import com.hax.models.*;
//import com.hax.models.fb.FbFriend;
//import com.hax.models.fb.FbFriends;
//import com.hax.models.fb.FbVerify;
//import com.hax.services.UsersService;
//import org.glassfish.hk2.utilities.binding.AbstractBinder;
//import org.junit.Test;
//import utils.GenericTest;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyLong;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
///**
// * Created by martin on 5/2/15.
// */
//public class UsersServiceTest extends GenericTest {
//
//    @Test
//    public void getAllUsers() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//
//        User user = new User();
//
//        when(ur.getAll()).thenReturn(Futures.immediateFuture(Arrays.asList(user)));
//
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//
//        List<User> lf = us.getAll();
//
//        try {
//            List<User> ul = lf.get();
//            assertTrue(ul.contains(user));
//        } catch (Exception e) {
//            assertTrue(false);
//        }
//    }
//
//    @Test
//    public void getUser() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//
//        User user = new User();
//
//        when(ur.get("1")).thenReturn(Futures.immediateFuture(user));
//
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//
//        User lf = us.getUser("1");
//
//        try {
//            User u = lf.get();
//            assertTrue(u==user);
//        } catch (Exception e) {
//            assertTrue(false);
//        }
//    }
//
//    @Test
//    public void getUserMissing() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//
//        when(ur.get("1")).thenReturn(Futures.<User>immediateFailedFuture(new RuntimeException("Missing User")));
//
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//
//        User lf = us.getUser("1");
//
//        try {
//            lf.get();
//            assertTrue(false);
//        } catch (Exception e) {
//            assertTrue(e.getMessage().contains("Missing User"));
//        }
//    }
//
//    @Test
//    public void createUser() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fC = mock(FacebookConnectorInterface.class);
//
//
//        User friend = new User();
//        friend.setFacebookId("1234");
//
//        User user = new User();
//        user.setFacebookId("1");
//        user.setUsername("pepito");
//        user.getFriends().add(friend.getFacebookId());
//
//        FbVerify fb = new FbVerify();
//        fb.setId("1");
//        fb.setName("pepito");
//        fb.setGender("male");
//
//        FbFriend fbFriend1 = new FbFriend();
//        FbFriend fbFriend2 = new FbFriend();
//        fbFriend1.setId("1234");
//        fbFriend1.setName("Amigo1");
//        fbFriend2.setId("4321");
//        fbFriend2.setName("Amigo2");
//
//        ArrayList<FbFriend> friendsLst = new ArrayList<FbFriend>();
//        //friendsLst.addAll(Arrays.asList(fbFriend1, fbFriend2));
//        friendsLst.addAll(Arrays.asList(fbFriend1));
//
//        FbFriends fbFriends = new FbFriends();
//        fbFriends.setData(friendsLst);
//
//        when(ur.insert(any(User.class))).thenReturn(Futures.immediateFuture(user));
//        when(ur.get("1")).thenReturn(Futures.<User>immediateFailedFuture(new RuntimeException("No existe el usuario")));
//        when(ur.get("1234")).thenReturn(Futures.immediateFuture(friend));
//        //when(ur.get("4321")).thenReturn(Futures.<User>immediateFailedFuture(new RuntimeException("No existe el usuario")));
//        when(fC.verifyAccessToken(any(String.class))).thenReturn(Futures.immediateFuture(fb));
//        when(fC.getUserFriends(any(String.class))).thenReturn(Futures.immediateFuture(fbFriends));
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//        us.facebookConnector = fC;
//
//        User lf = us.createUser("token-de-prueba");
//
//        try {
//            User u = lf.get();
//            assertTrue(u.getFacebookId().equals(fb.getId()));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            assertTrue(false);
//        }
//    }
//
//    @Test
//    public void getFriends() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//        FbVerify fbVerify = new FbVerify();
//        fbVerify.setId("1");
//
//        User user = new User();
//        List<String> friends = user.getFriends();
//        User friend = new User();
//        friend.setFacebookId("1234");
//        friends.add(friend.getFacebookId());
//
//        when(ur.get("1")).thenReturn(Futures.immediateFuture(user));
//        when(fb.verifyAccessToken(anyString())).thenReturn(Futures.immediateFuture(fbVerify));
//        when(ur.get("1234")).thenReturn(Futures.immediateFuture(friend));
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//        us.facebookConnector =  fb;
//
//        List<User> lf = us.getFriends("1");
//
//        try {
//            List<User> ul = lf.get();
//            assertTrue(ul.contains(friend));
//        } catch (Exception e) {
//            assertTrue(false);
//        }
//    }
//
//    @Test
//    public void getFriendsUserMissing() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//        FbVerify fbVerify = new FbVerify();
//        fbVerify.setId("2");
//
//
//        when(ur.get(anyString())).thenReturn(Futures.<User>immediateFailedFuture(new RuntimeException("Missing User")));
//        when(fb.verifyAccessToken(anyString())).thenReturn(Futures.immediateFuture(fbVerify));
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//        us.facebookConnector = fb;
//
//        List<User> lf = us.getFriends("1");
//
//        try {
//            lf.get();
//            assertTrue(false);
//        } catch (Exception e) {
//            assertTrue(e.getMessage().contains("Missing User"));
//        }
//    }
//
//    @Test
//    public void getFlights() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//        TripsRepositoryInterface tr = mock(TripsRepositoryInterface.class);
//
//        FbVerify fbVerify = new FbVerify();
//        fbVerify.setId("1");
//
//        User user = new User();
//        user.setFacebookId("1");
//        List<Long> trips = user.getTrips();
//        Trip trip = new Trip();
//        trip.setId(1234L);
//        trips.add(trip.getId());
//
//        when(ur.get(anyString())).thenReturn(Futures.immediateFuture(user));
//        when(fb.verifyAccessToken(anyString())).thenReturn(Futures.immediateFuture(fbVerify));
//        when(tr.get(anyLong())).thenReturn(Futures.immediateFuture(trip));
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//        us.facebookConnector = fb;
//        us.tripsRespository = tr;
//
//        List<Trip> lf = us.getTrips("1");
//
//        try {
//            List<Trip> ul = lf.get();
//            assertTrue(ul.contains(trip));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            assertTrue(false);
//        }
//    }
//
//    @Test
//    public void getFlightsMissingUser() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//        FbVerify fbVerify = new FbVerify();
//        fbVerify.setId("1");
//
//
//        User user = new User();
//        List<Long> trips = user.getTrips();
//        Trip trip = new Trip();
//        trip.setId(1234L);
//        trips.add(trip.getId());
//
//        when(ur.get(anyString())).thenReturn(Futures.<User>immediateFailedFuture(new RuntimeException("Missing User")));
//        when(fb.verifyAccessToken(anyString())).thenReturn(Futures.immediateFuture(fbVerify));
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//        us.facebookConnector = fb;
//
//        List<Trip> lf = us.getTrips("1");
//
//        try {
//            lf.get();
//            assertTrue(false);
//        } catch (Exception e) {
//            assertTrue(e.getMessage().contains("Missing User"));
//        }
//    }
//
//    @Test
//    public void getRecommendations() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//        RecommendationsRepositoryInterface rr = mock(RecommendationsRepositoryInterface.class);
//
//        FbVerify fbVerify = new FbVerify();
//        fbVerify.setId("1234");
//
//        User user = new User();
//        user.setFacebookId("1234");
//        List<Long> recommendations = user.getRecommendations();
//        recommendations.add(120L);
//
//        Recommendation r = new Recommendation(120L, "1234", "4567");
//
//        when(ur.get("1234")).thenReturn(Futures.immediateFuture(user));
//        when(fb.verifyAccessToken("abcd")).thenReturn(Futures.immediateFuture(fbVerify));
//        when(rr.get(120L)).thenReturn(Futures.immediateFuture(r));
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//        us.facebookConnector = fb;
//        us.recommendationsRepository = rr;
//
//        ListenableFuture<List<Recommendation>> lf = us.getRecommendations("abcd");
//
//        try {
//            List<Recommendation> lstRec = lf.get();
//            assertTrue(lstRec.contains(r));
//        } catch (Exception e) {
//            assertTrue(false);
//        }
//    }
//
//    @Test
//    public void getRecommendationsMissingUser() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//        FbVerify fbVerify = new FbVerify();
//        fbVerify.setId("1");
//
//        when(ur.get(anyString())).thenReturn(Futures.<User>immediateFailedFuture(new RuntimeException("Missing User")));
//        when(fb.verifyAccessToken(anyString())).thenReturn(Futures.immediateFuture(fbVerify));
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//        us.facebookConnector = fb;
//
//        ListenableFuture<List<Recommendation>> lf = us.getRecommendations("1");
//
//        try {
//            lf.get();
//            assertTrue(false);
//        } catch (Exception e) {
//            assertTrue(e.getMessage().contains("Missing User"));
//        }
//    }
//
//    @Test
//    public void update() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//
//        User user = new User();
//
//        when(ur.update(any(User.class))).thenReturn(Futures.immediateFuture(user));
//
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//
//        User lf = us.update(user);
//
//        try {
//            User u = lf.get();
//            assertTrue(u == user);
//        } catch (Exception e) {
//            assertTrue(false);
//        }
//    }
//
//
////No se usa mas
////    @Test
////    public void addFriend() {
////        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
////        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
////
////        User user = new User();
////        user.setId("1");
////
////        User friend = new User();
////        friend.setId("2");
////
////        when(ur.get("1")).thenReturn(Futures.immediateFuture(user));
////        when(ur.get("2")).thenReturn(Futures.immediateFuture(friend));
////
////
////        UsersService us = new UsersService();
////        us.usersRepository = ur;
////
////        User lf = us.addFriend("1", "2");
////
////        try {
////            User u = lf.get();
////            assertTrue(u.getFriends().contains(friend.getId()));
////            assertTrue(friend.getFriends().contains(u.getId()));
////        } catch (Exception e) {
////            assertTrue(false);
////        }
////    }
//
//    @Test
//    public void addFriendMissingUser() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//
//        User friend = new User();
//        friend.setFacebookId("2");
//
//        when(ur.get("1")).thenReturn(Futures.<User>immediateFailedFuture(new RuntimeException("Missing User")));
//
//        when(ur.get("2")).thenReturn(Futures.immediateFuture(friend));
//
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//
//        User lf = us.addFriend("1", "2");
//
//        try {
//            lf.get();
//            assertTrue(false);
//        } catch (Exception e) {
//            assertTrue(e.getMessage().contains("Missing User"));
//        }
//    }
//
//    @Test
//    public void addFriendMissingFriend() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//
//        User user = new User();
//        user.setFacebookId("1");
//
//        when(ur.get("1")).thenReturn(Futures.immediateFuture(user));
//
//        when(ur.get("2")).thenReturn(Futures.<User>immediateFailedFuture(new RuntimeException("Missing Friend")));
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//
//        User lf = us.addFriend("1", "2");
//
//        try {
//            lf.get();
//            assertTrue(false);
//        } catch (Exception e) {
//            assertTrue(e.getMessage().contains("Missing Friend"));
//        }
//    }
//
//    @Test
//    public void removeFriend() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//
//        User friend = new User();
//        friend.setFacebookId("2");
//
//        User user = new User();
//        user.setFacebookId("1");
//        user.getFriends().add(friend.getFacebookId());
//
//
//        when(ur.get("1")).thenReturn(Futures.immediateFuture(user));
//        when(ur.get("2")).thenReturn(Futures.immediateFuture(friend));
//
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//
//        User lf = us.removeFriend("1", "2");
//
//        try {
//            User u = lf.get();
//            assertFalse(u.getFriends().contains(friend.getFacebookId()));
//            assertFalse(friend.getFriends().contains(u.getFacebookId()));
//        } catch (Exception e) {
//            assertTrue(false);
//        }
//    }
//
//    @Test
//    public void removeFriendMissingUser() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//
//        User friend = new User();
//        friend.setFacebookId("2");
//
//        when(ur.get("1")).thenReturn(Futures.<User>immediateFailedFuture(new RuntimeException("Missing User")));
//
//        when(ur.get("2")).thenReturn(Futures.immediateFuture(friend));
//
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//
//        User lf = us.removeFriend("1", "2");
//
//        try {
//            lf.get();
//            assertTrue(false);
//        } catch (Exception e) {
//            assertTrue(e.getMessage().contains("Missing User"));
//        }
//    }
//
//    @Test
//    public void removeFriendMissingFriend() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//
//        User user = new User();
//        user.setFacebookId("1");
//
//        when(ur.get("1")).thenReturn(Futures.immediateFuture(user));
//
//        when(ur.get("2")).thenReturn(Futures.<User>immediateFailedFuture(new RuntimeException("Missing Friend")));
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//
//        User lf = us.removeFriend("1", "2");
//
//        try {
//            lf.get();
//            assertTrue(false);
//        } catch (Exception e) {
//            assertTrue(e.getMessage().contains("Missing Friend"));
//        }
//    }
//
//    @Test
//    public void recommendFlight() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        TripsRepositoryInterface fr = mock(TripsRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//        RecommendationsRepositoryInterface rr = mock(RecommendationsRepositoryInterface.class);
//        AirportsConnectorInterface airportsConnector = mock(AirportsConnectorInterface.class);
//
//        User user = new User();
//        user.setFacebookId("1234");
//
//        User friend = new User();
//        friend.setFacebookId("2345");
//
//        Trip trip = new Trip();
//        trip.setId(0L);
//        trip.setDestiny("BUE");
//
//        FbVerify fbVerify = new FbVerify();
//        fbVerify.setId("1234");
//
//        Recommendation rec= new Recommendation(trip.getId(), user.getFacebookId(), friend.getFacebookId());
//        rec.setId(10L);
//
//        AirportResponse ar = new AirportResponse();
//        ar.setCity("Buenos Aires");
//
//        when(ur.get("1234")).thenReturn(Futures.immediateFuture(user));
//        when(ur.get("2345")).thenReturn(Futures.immediateFuture(friend));
//        when(ur.update(friend)).thenReturn(Futures.immediateFuture(friend));
//        when(fr.get(0L)).thenReturn(Futures.immediateFuture(trip));
//        when(fb.verifyAccessToken("abc")).thenReturn(Futures.immediateFuture(fbVerify));
//        when(rr.insert(any(Recommendation.class))).thenReturn(Futures.immediateFuture(rec));
//        when(airportsConnector.getAirportAsync("BUE")).thenReturn(Futures.immediateFuture(ar));
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//        us.tripsRespository = fr;
//        us.facebookConnector = fb;
//        us.recommendationsRepository = rr;
//        us.airportsConnector = airportsConnector;
//
//        ListenableFuture<Recommendation> lf = us.recommendFlight(0L, "abc", "2345");
//
//        try {
//            Recommendation r = lf.get();
//            assertTrue(r.getTrip()== trip.getId());
//            assertTrue(r.getFromUserId() == user.getFacebookId());
//            assertTrue(r.getState()== RecommendationState.PENDING);
//        } catch (Exception e) {
//            assertTrue(false);
//        }
//    }
//
//    @Test
//    public void recommendFlightMissingFlight() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        TripsRepositoryInterface fr = mock(TripsRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//
//
//        User user = new User();
//        user.setFacebookId("1234");
//
//        User friend = new User();
//        friend.setFacebookId("2");
//
//        FbVerify fbVerify = new FbVerify();
//        fbVerify.setId("1234");
//
//        when(ur.get("1")).thenReturn(Futures.immediateFuture(user));
//        when(ur.get("2")).thenReturn(Futures.immediateFuture(friend));
//        when(ur.update(friend)).thenReturn(Futures.immediateFuture(friend));
//        when(fr.get(0L)).thenReturn(Futures.<Trip>immediateFailedFuture(new RuntimeException("Missing Trip")));
//        when(fb.verifyAccessToken(anyString())).thenReturn(Futures.immediateFuture(fbVerify));
//
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//        us.tripsRespository = fr;
//        us.facebookConnector = fb;
//
//        ListenableFuture<Recommendation> lf = us.recommendFlight(0L, "1", "2");
//
//        try {
//            lf.get();
//            assertTrue(false);
//        } catch (Exception e) {
//            assertTrue(e.getMessage().contains("Missing Trip"));
//        }
//    }
//
//    @Test
//    public void acceptRecommendation() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//        RecommendationsRepositoryInterface rr = mock(RecommendationsRepositoryInterface.class);
//        AirportsConnectorInterface airportsConnector = mock(AirportsConnectorInterface.class);
//        TripsRepositoryInterface tripsRepository = mock(TripsRepositoryInterface.class);
//
//        User user = new User();
//        user.setFacebookId("1234");
//        user.getRecommendations().add(333L);
//
//        FbVerify fbVerify = new FbVerify();
//        fbVerify.setId("1234");
//
//        Recommendation rec = new Recommendation(333L,"1234","4567");
//
//        AirportResponse ar = new AirportResponse();
//        ar.setCity("Buenos Aires");
//
//        Trip trip = new Trip();
//        trip.setDestiny("BUE");
//
//        when(ur.get("1234")).thenReturn(Futures.immediateFuture(user));
//        when(ur.update(user)).thenReturn(Futures.immediateFuture(user));
//        when(fb.verifyAccessToken(anyString())).thenReturn(Futures.immediateFuture(fbVerify));
//        when(fb.publishNotification(anyString(), anyString())).thenReturn(Futures.immediateFuture(""));
//        when(rr.get(anyLong())).thenReturn(Futures.immediateFuture(rec));
//        when(airportsConnector.getAirportAsync("BUE")).thenReturn(Futures.immediateFuture(ar));
//        when(tripsRepository.get(anyLong())).thenReturn(Futures.immediateFuture(trip));
//        when(rr.update(any(Recommendation.class))).thenReturn(Futures.immediateFuture(rec));
//
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//        us.facebookConnector = fb;
//        us.airportsConnector = airportsConnector;
//        us.recommendationsRepository = rr;
//        us.tripsRespository = tripsRepository;
//
//        ListenableFuture<Recommendation> lf = us.acceptRecommendation(4L, "1");
//
//        try {
//            Recommendation r = lf.get();
//            assertTrue(r.getState()==RecommendationState.ACCEPTED);
//        } catch (Exception e) {
//            assertTrue(false);
//        }
//    }
//
//    @Test
//    public void acceptRecommendationMissingRecommendation() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//        RecommendationsRepositoryInterface rr = mock(RecommendationsRepositoryInterface.class);
//
//        User user = new User();
//        user.setFacebookId("1234");
//
//        FbVerify fbVerify = new FbVerify();
//        fbVerify.setId("1234");
//
//        when(ur.get("1234")).thenReturn(Futures.immediateFuture(user));
//        when(ur.update(user)).thenReturn(Futures.immediateFuture(user));
//        when(fb.verifyAccessToken(anyString())).thenReturn(Futures.immediateFuture(fbVerify));
//        when(fb.publishNotification(anyString(), anyString())).thenReturn(Futures.immediateFuture(""));
//        when(rr.get(anyLong())).thenReturn(Futures.<Recommendation>immediateFailedFuture(new RuntimeException("Missing Recommendation")));
//
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//        us.facebookConnector = fb;
//        us.recommendationsRepository = rr;
//
//        ListenableFuture<Recommendation> lf = us.acceptRecommendation(4L, "1");
//
//        try {
//            lf.get();
//            assertTrue(false);
//        } catch (Exception e) {
//            assertTrue(e.getMessage().contains("Missing Recommendation"));
//        }
//    }
//
//    @Test
//    public void rejectRecommendation() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//        RecommendationsRepositoryInterface rr = mock(RecommendationsRepositoryInterface.class);
//        TripsRepositoryInterface tripsRepository = mock(TripsRepositoryInterface.class);
//
//        User user = new User();
//        user.setFacebookId("1234");
//        user.getRecommendations().add(333L);
//
//        FbVerify fbVerify = new FbVerify();
//        fbVerify.setId("1234");
//
//        Recommendation rec = new Recommendation(333L,"1234","4567");
//
//        AirportResponse ar = new AirportResponse();
//        ar.setCity("Buenos Aires");
//
//        Trip trip = new Trip();
//        trip.setDestiny("BUE");
//
//        when(ur.get("1234")).thenReturn(Futures.immediateFuture(user));
//        when(ur.update(user)).thenReturn(Futures.immediateFuture(user));
//        when(fb.verifyAccessToken(anyString())).thenReturn(Futures.immediateFuture(fbVerify));
//        when(fb.publishNotification(anyString(), anyString())).thenReturn(Futures.immediateFuture(""));
//        when(rr.get(anyLong())).thenReturn(Futures.immediateFuture(rec));
//        when(tripsRepository.get(anyLong())).thenReturn(Futures.immediateFuture(trip));
//        when(rr.update(any(Recommendation.class))).thenReturn(Futures.immediateFuture(rec));
//
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//        us.facebookConnector = fb;
//        us.recommendationsRepository = rr;
//        us.tripsRespository = tripsRepository;
//
//        ListenableFuture<Recommendation> lf = us.rejectRecommendation(4L, "1");
//
//        try {
//            Recommendation r = lf.get();
//            assertTrue(r.getState()==RecommendationState.REJECTED);
//        } catch (Exception e) {
//            assertTrue(false);
//        }
//    }
//
//    @Test
//    public void rejectRecommendationMissingRecommendation() {
//        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
//        FacebookConnectorInterface fb = mock(FacebookConnectorInterface.class);
//        RecommendationsRepositoryInterface rr = mock(RecommendationsRepositoryInterface.class);
//
//        User user = new User();
//        user.setFacebookId("1234");
//
//        FbVerify fbVerify = new FbVerify();
//        fbVerify.setId("1234");
//
//        when(ur.get("1234")).thenReturn(Futures.immediateFuture(user));
//        when(ur.update(user)).thenReturn(Futures.immediateFuture(user));
//        when(fb.verifyAccessToken(anyString())).thenReturn(Futures.immediateFuture(fbVerify));
//        when(fb.publishNotification(anyString(), anyString())).thenReturn(Futures.immediateFuture(""));
//        when(rr.get(anyLong())).thenReturn(Futures.<Recommendation>immediateFailedFuture(new RuntimeException("Missing Recommendation")));
//
//
//        UsersService us = new UsersService();
//        us.usersRepository = ur;
//        us.facebookConnector = fb;
//        us.recommendationsRepository = rr;
//
//        ListenableFuture<Recommendation> lf = us.rejectRecommendation(4L, "1");
//
//        try {
//            lf.get();
//            assertTrue(false);
//        } catch (Exception e) {
//            assertTrue(e.getMessage().contains("Missing Recommendation"));
//        }
//    }
//
//    @Override
//    protected AbstractBinder setBinder() {
//        return null;
//    }
//
//}

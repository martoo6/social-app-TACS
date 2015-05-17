package services;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.FlightsRepositoryInterface;
import com.hax.connectors.UsersRepositoryInterface;
import com.hax.models.Flight;
import com.hax.models.Recommendation;
import com.hax.models.RecommendationState;
import com.hax.models.User;
import com.hax.services.UsersService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import static com.hax.async.executors.Default.ex;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
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

        when(ur.getAll()).thenReturn(Futures.immediateFuture(Arrays.asList(user)));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<List<User>> lf = us.getAll();

        try {
            List<User> ul = lf.get();
            assertTrue(ul.contains(user));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void getUser() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User user = new User();

        when(ur.get(1)).thenReturn(Futures.immediateFuture(user));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<User> lf = us.getUser(1);

        try {
            User u = lf.get();
            assertTrue(u==user);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void getUserMissing() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        when(ur.get(1)).thenReturn(ex.submit(new Callable() {
            public Object call() throws Exception {
                throw new RuntimeException("Missing User");
            }
        }));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<User> lf = us.getUser(1);

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Missing User"));
        }
    }

    @Test
    public void createUser() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User user = new User();

        when(ur.insert(any(User.class))).thenReturn(Futures.immediateFuture(user));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<User> lf = us.createUser(user);

        try {
            User u = lf.get();
            assertTrue(u==user);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void getFriends() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User user = new User();
        List<User> friends = user.getFriends();
        User friend = new User();
        friends.add(friend);

        when(ur.get(1)).thenReturn(Futures.immediateFuture(user));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<List<User>> lf = us.getFriends(1);

        try {
            List<User> ul = lf.get();
            assertTrue(ul.contains(friend));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void getFriendsUserMissing() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        when(ur.get(1)).thenReturn(ex.submit(new Callable() {
            public Object call() throws Exception {
                throw new RuntimeException("Missing User");
            }
        }));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<List<User>> lf = us.getFriends(1);

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Missing User"));
        }
    }

    @Test
    public void getFlights() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User user = new User();
        List<Flight> flights = user.getFlights();
        Flight flight = new Flight();
        flights.add(flight);

        when(ur.get(1)).thenReturn(Futures.immediateFuture(user));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<List<Flight>> lf = us.getFlights(1);

        try {
            List<Flight> ul = lf.get();
            assertTrue(ul.contains(flight));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void getFlightsMissingUser() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User user = new User();
        List<Flight> flights = user.getFlights();
        Flight flight = new Flight();
        flights.add(flight);

        when(ur.get(1)).thenReturn(ex.submit(new Callable() {
            public Object call() throws Exception {
                throw new RuntimeException("Missing User");
            }
        }));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<List<Flight>> lf = us.getFlights(1);

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Missing User"));
        }
    }

    @Test
    public void getRecommendations() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User user = new User();
        List<Recommendation> recommendations = user.getRecommendations();
        Recommendation recommendation = new Recommendation(null,null);
        recommendations.add(recommendation);

        when(ur.get(1)).thenReturn(Futures.immediateFuture(user));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<List<Recommendation>> lf = us.getRecommendations(1);

        try {
            List<Recommendation> lstRec = lf.get();
            assertTrue(lstRec.contains(recommendation));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void getRecommendationsMissingUser() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        when(ur.get(1)).thenReturn(ex.submit(new Callable() {
            public Object call() throws Exception {
                throw new RuntimeException("Missing User");
            }
        }));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<List<Recommendation>> lf = us.getRecommendations(1);

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Missing User"));
        }
    }

    @Test
    public void update() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User user = new User();

        when(ur.update(any(User.class))).thenReturn(Futures.immediateFuture(user));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<User> lf = us.update(user);

        try {
            User u = lf.get();
            assertTrue(u == user);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void addFriend() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User user = new User();
        user.setId(1);

        User friend = new User();
        friend.setId(2);

        when(ur.get(1)).thenReturn(Futures.immediateFuture(user));
        when(ur.get(2)).thenReturn(Futures.immediateFuture(friend));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<User> lf = us.addFriend(1, 2);

        try {
            User u = lf.get();
            assertTrue(u.getFriends().contains(friend));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void addFriendMissingUser() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User friend = new User();
        friend.setId(2);

        when(ur.get(1)).thenReturn(ex.submit(new Callable() {
            public Object call() throws Exception {
                throw new RuntimeException("Missing User");
            }
        }));

        when(ur.get(2)).thenReturn(Futures.immediateFuture(friend));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<User> lf = us.addFriend(1, 2);

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Missing User"));
        }
    }

    @Test
    public void addFriendMissingFriend() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User user = new User();
        user.setId(1);

        when(ur.get(1)).thenReturn(Futures.immediateFuture(user));

        when(ur.get(2)).thenReturn(ex.submit(new Callable() {
            public Object call() throws Exception {
                throw new RuntimeException("Missing Friend");
            }
        }));

        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<User> lf = us.addFriend(1, 2);

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Missing Friend"));
        }
    }

    @Test
    public void removeFriend() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User friend = new User();
        friend.setId(2);

        User user = new User();
        user.getFriends().add(friend);
        user.setId(1);

        when(ur.get(1)).thenReturn(Futures.immediateFuture(user));
        when(ur.get(2)).thenReturn(Futures.immediateFuture(friend));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<User> lf = us.removeFriend(1, 2);

        try {
            User u = lf.get();
            assertFalse(u.getFriends().contains(friend));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void removeFriendMissingUser() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User friend = new User();
        friend.setId(2);

        when(ur.get(1)).thenReturn(ex.submit(new Callable() {
            public Object call() throws Exception {
                throw new RuntimeException("Missing User");
            }
        }));

        when(ur.get(2)).thenReturn(Futures.immediateFuture(friend));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<User> lf = us.removeFriend(1, 2);

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Missing User"));
        }
    }

    @Test
    public void removeFriendMissingFriend() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User user = new User();
        user.setId(1);

        when(ur.get(1)).thenReturn(Futures.immediateFuture(user));

        when(ur.get(2)).thenReturn(ex.submit(new Callable() {
            public Object call() throws Exception {
                throw new RuntimeException("Missing Friend");
            }
        }));

        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<User> lf = us.removeFriend(1, 2);

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Missing Friend"));
        }
    }

    @Test
    public void recommendFlight() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        FlightsRepositoryInterface fr = mock(FlightsRepositoryInterface.class);


        User user = new User();
        user.setId(1);

        User friend = new User();
        friend.setId(2);

        Flight flight = new Flight();

        when(ur.get(1)).thenReturn(Futures.immediateFuture(user));
        when(ur.get(2)).thenReturn(Futures.immediateFuture(friend));
        when(ur.update(friend)).thenReturn(Futures.immediateFuture(friend));
        when(fr.get(0)).thenReturn(Futures.immediateFuture(flight));


        UsersService us = new UsersService();
        us.usersRepository = ur;
        us.flightsRepository = fr;

        ListenableFuture<Recommendation> lf = us.recommendFlight(0, 1, 2);

        try {
            Recommendation r = lf.get();
            assertTrue(r.getFlight()==flight);
            assertTrue(r.getFromUser() == user);
            assertTrue(r.getState()== RecommendationState.PENDING);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void recommendFlightMissingFlight() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        FlightsRepositoryInterface fr = mock(FlightsRepositoryInterface.class);


        User user = new User();
        user.setId(1);

        User friend = new User();
        friend.setId(2);

        when(ur.get(1)).thenReturn(Futures.immediateFuture(user));
        when(ur.get(2)).thenReturn(Futures.immediateFuture(friend));
        when(ur.update(friend)).thenReturn(Futures.immediateFuture(friend));
        when(fr.get(0)).thenReturn(ex.submit(new Callable() {
            public Object call() throws Exception {
                throw new RuntimeException("Missing Flight");
            }
        }));


        UsersService us = new UsersService();
        us.usersRepository = ur;
        us.flightsRepository = fr;

        ListenableFuture<Recommendation> lf = us.recommendFlight(0, 1, 2);

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Missing Flight"));
        }
    }

    @Test
    public void acceptRecommendation() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);


        Recommendation recommendation = new Recommendation(null,null);
        recommendation.setId(4);

        User user = new User();
        user.setId(1);
        user.getRecommendations().add(recommendation);

        when(ur.get(1)).thenReturn(Futures.immediateFuture(user));
        when(ur.update(user)).thenReturn(Futures.immediateFuture(user));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<Recommendation> lf = us.acceptRecommendation(4, 1);

        try {
            Recommendation r = lf.get();
            assertTrue(r.getState()==RecommendationState.ACCEPTED);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void acceptRecommendationMissingRecommendation() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User user = new User();
        user.setId(1);

        when(ur.get(1)).thenReturn(Futures.immediateFuture(user));
        when(ur.update(user)).thenReturn(Futures.immediateFuture(user));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<Recommendation> lf = us.acceptRecommendation(4, 1);

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Missing Recommendation"));
        }
    }

    @Test
    public void rejectRecommendation() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);


        Recommendation recommendation = new Recommendation(null,null);
        recommendation.setId(4);

        User user = new User();
        user.setId(1);
        user.getRecommendations().add(recommendation);

        when(ur.get(1)).thenReturn(Futures.immediateFuture(user));
        when(ur.update(user)).thenReturn(Futures.immediateFuture(user));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<Recommendation> lf = us.rejectRecommendation(4, 1);

        try {
            Recommendation r = lf.get();
            assertTrue(r.getState()==RecommendationState.REJECTED);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void rejectRecommendationMissingRecommendation() {
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User user = new User();
        user.setId(1);

        when(ur.get(1)).thenReturn(Futures.immediateFuture(user));
        when(ur.update(user)).thenReturn(Futures.immediateFuture(user));


        UsersService us = new UsersService();
        us.usersRepository = ur;

        ListenableFuture<Recommendation> lf = us.rejectRecommendation(4, 1);

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Missing Recommendation"));
        }
    }

    @Override
    protected AbstractBinder setBinder() {
        return null;
    }

}

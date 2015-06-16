package com.hax.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 5/5/15.
 */
public class User {
    private String username;
    private String password;
    private String email;
    private Long id;
    private List<User> friends = new ArrayList<User>();
    private List<Trip> trips = new ArrayList<Trip>();
    private List<Recommendation> recommendations = new ArrayList<Recommendation>();
    private String longLivedToken;


    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public String getLongLivedToken() {
        return longLivedToken;
    }

    public void setLongLivedToken(String longLivedToken) {
        this.longLivedToken = longLivedToken;
    }
}

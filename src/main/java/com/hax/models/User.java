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
    private Integer id;
    private List<User> friends = new ArrayList<User>();
    private List<Flight> flights = new ArrayList<Flight>();
    private List<Recommendation> recommendations = new ArrayList<Recommendation>();


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

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }
}

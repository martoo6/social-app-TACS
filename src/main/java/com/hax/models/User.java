package com.hax.models;

import com.hax.models.fb.FbVerify;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 5/5/15.
 */
public class User {
    private String username;
    private String email;
    private String id;
    private List<String> friends = new ArrayList<String>();
    private List<Long> trips = new ArrayList<Long>();
    private List<Long> recommendations = new ArrayList<Long>();
    private String longLivedToken;
    private String gender;

    public User() {
    }

    public User(FbVerify fbVerify) {
        username = fbVerify.getName();
        id = fbVerify.getId();
        gender = fbVerify.getGender();
    }

    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<Long> getTrips() {
        return trips;
    }

    public void setTrips(List<Long> trips) {
        this.trips = trips;
    }

    public List<Long> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Long> recommendations) {
        this.recommendations = recommendations;
    }

    public String getLongLivedToken() {
        return longLivedToken;
    }

    public void setLongLivedToken(String longLivedToken) {
        this.longLivedToken = longLivedToken;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

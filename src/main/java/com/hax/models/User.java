package com.hax.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.hax.models.fb.FbVerify;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 5/5/15.
 */
@Entity
public class User {
    public String username;
    public String email;
    @Id public String facebookId;
    public List<String> friends = new ArrayList<String>();
    public List<Long> trips = new ArrayList<Long>();
    public List<Long> recommendations = new ArrayList<Long>();
    public String longLivedToken;
    public String gender;

    public User() {
    }

    public User(FbVerify fbVerify) {
        username = fbVerify.getName();
        facebookId = fbVerify.getId();
        gender = fbVerify.getGender();
        email = fbVerify.getEmail();
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

    public String getFacebookId() { return facebookId; }

    public void setFacebookId(String facebookId) { this.facebookId = facebookId; }

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

package com.hax.models;

/**
 * Created by martin on 5/5/15.
 */
public class Recommendation {
    private User fromUser;
    private Trip trip;
    private RecommendationState state;
    private Integer id;

    public Recommendation(Trip trip, User fromUser) {
        this.fromUser = fromUser;
        this.trip = trip;
        this.state = RecommendationState.PENDING;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User toUser) {
        this.fromUser = toUser;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public RecommendationState getState() {
        return state;
    }

    public void setState(RecommendationState state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

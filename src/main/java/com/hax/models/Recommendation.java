package com.hax.models;

/**
 * Created by martin on 5/5/15.
 */
public class Recommendation {
    private User fromUser;
    private Flight flight;
    private RecommendationState state;
    private Integer id;

    public Recommendation(Flight flight, User fromUser) {
        this.fromUser = fromUser;
        this.flight = flight;
        this.state = RecommendationState.PENDING;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User toUser) {
        this.fromUser = toUser;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
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

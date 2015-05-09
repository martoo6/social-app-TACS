package com.hax.models;

/**
 * Created by martin on 5/5/15.
 */
public class Recommendation {
    private User fromUser;
    private Flight flight;
    private String state;
    private Integer id;

    public Recommendation(Flight flight, User fromUser) {
        this.fromUser = fromUser;
        this.flight = flight;
        this.state = "PENDIENTE";
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

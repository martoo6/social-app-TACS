package com.hax.models;

/**
 * Created by martin on 5/5/15.
 */

public class Flight {
    private Ticket wayTicket;
    private Ticket returnTicket;
    private Double totalPrice;
    private Integer id;

    //Dummy Contructor
    public Flight() {
    }

    public Flight(Ticket wayTicket, Ticket returnTicket, Double totalPrice) {
        this.wayTicket = wayTicket;
        this.returnTicket = returnTicket;
        this.totalPrice = totalPrice;
    }

    public Ticket getWayTicket() {
        return wayTicket;
    }

    public void setWayTicket(Ticket wayTicket) {
        this.wayTicket = wayTicket;
    }

    public Ticket getReturnTicket() {
        return returnTicket;
    }

    public void setReturnTicket(Ticket returnTicket) {
        this.returnTicket = returnTicket;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }
}


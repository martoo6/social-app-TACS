package com.hax.models;

public class Ticket{
    private String origin;
    private String destiny;
    private String company;
    private String flightNum;
    private String departureTime; //TODO: Pasar a dateTime (posiblemente JODA)
    private String duration;//TODO: Pasar a dateTime (posiblemente JODA)
    private Double price;

    //Dummy Contructor para poder funcionar con la Inyeccion de Dependencias
    public Ticket(){}

    public Ticket(String origin, String destiny, String company, String flightNum, String departureTime, String duration, Double price) {
        this.origin = origin;
        this.destiny = destiny;
        this.company = company;
        this.flightNum = flightNum;
        this.departureTime = departureTime;
        this.duration = duration;
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

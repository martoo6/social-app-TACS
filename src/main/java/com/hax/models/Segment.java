package com.hax.models;

public class Segment {
    private String origin;
    private String destiny;
    private String airline;
    private String flightNum;
    private String departureTime; //TODO: Pasar a dateTime (posiblemente JODA)
    private String duration;//TODO: Pasar a dateTime (posiblemente JODA)

    //Dummy Contructor para poder funcionar con la Inyeccion de Dependencias
    public Segment(){}

    public Segment(String origin, String destiny, String airline, String flightNum, String departureTime, String duration) {
        this.origin = origin;
        this.destiny = destiny;
        this.airline = airline;
        this.flightNum = flightNum;
        this.departureTime = departureTime;
        this.duration = duration;
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

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
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
}

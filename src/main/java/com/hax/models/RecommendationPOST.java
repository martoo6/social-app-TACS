package com.hax.models;

/**
 * Created by martin on 5/5/15.
 */
public class RecommendationPOST {
    private Integer toUserId;
    private Integer flightId;
    private String state;

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }
}
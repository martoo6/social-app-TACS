package com.hax.models;

/**
 * Created by martin on 5/5/15.
 */
public class RecommendationPOST {
    private String toUserId;
    private Long flightId;
    private String state;

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }
}

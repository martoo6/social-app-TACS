package com.hax.models.fb;

/**
 * Created by martin on 6/24/15.
 */
public class FbFeed {
    private String message;

    public FbFeed(String message) {
        this.message = message;
    }

    public FbFeed() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

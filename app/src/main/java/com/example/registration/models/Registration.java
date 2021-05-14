package com.example.registration.models;

public class Registration {
    private String expiry_date;
    private Boolean expired;

    public Registration(Boolean expired, String expiry_date) {
        this.expired = expired;
        this.expiry_date = expiry_date;
    }

    public Boolean getExpired() {
        return expired;
    }

    public String getExpiryDate() {
        return expiry_date;
    }
}

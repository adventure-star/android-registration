package com.example.registration.models;

public class Insurer {

    private int code;
    private String name;

    public Insurer(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }
    public String getName() {
        return this.name;
    }
}

package com.example.registration.models;

public class Vehicle {
    private String type;
    private String make;
    private String model;
    private String colour;
    private String vin;
    private int tare_weight;
    private int gross_mass;

    public Vehicle(String type, String make, String model, String colour, String vin, int tare_weight, int gross_mass) {
        this.type = type;
        this.make = make;
        this.model = model;
        this.colour = colour;
        this.vin = vin;
        this.tare_weight = tare_weight;
        this.gross_mass = gross_mass;
    }

    public String getType() {
        return this.type;
    }
    public String getMake() {
        return this.make;
    }

    public String getModel() {
        return this.model;
    }
    public String getColour() {
        return this.colour;
    }
    public String getVin() {
        return this.vin;
    }
    public int getTareWeight() {
        return this.tare_weight;
    }
    public int getGrossMass() {
        return this.gross_mass;
    }

}

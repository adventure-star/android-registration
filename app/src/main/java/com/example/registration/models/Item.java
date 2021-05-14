package com.example.registration.models;

public class Item {
    private String plate_number;
    private Registration registration;
    private Vehicle vehicle;
    private Insurer insurer;


    public Item(String plate_number, Registration registration, Vehicle vehicle, Insurer insurer){

        this.plate_number = plate_number;
        this.registration = registration;
        this.vehicle = vehicle;
        this.insurer = insurer;

    }

    public String getPlateNumber() {
        return this.plate_number;
    }
    public Registration getRegistration() {
        return this.registration;
    }
    public Vehicle getVehicle() {
        return this.vehicle;
    }
    public Insurer getInsurer() {
        return this.insurer;
    }



}

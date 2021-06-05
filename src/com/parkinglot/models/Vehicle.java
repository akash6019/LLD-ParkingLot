package com.parkinglot.models;

public class Vehicle {

    private final VehicleType type;
    private final String registrationNumber;
    private final String Colour;

    public Vehicle(VehicleType type, String registrationNumber, String Colour) {
        this.type = type;
        this.registrationNumber = registrationNumber;
        this.Colour = Colour;
    }

    public VehicleType getVehicleType() {
        return this.type;
    }

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    public String getColour() {
        return this.Colour;
    }
}

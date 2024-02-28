package com.parking.entites;

import com.parking.util.VehicleType;

public class Vehicle {
    private String licensePlate;
    private VehicleType size;

    public Vehicle(String licensePlate, VehicleType size) {
        this.licensePlate = licensePlate;
        this.size = size;
    }

    public VehicleType getSize() {
        return size;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}

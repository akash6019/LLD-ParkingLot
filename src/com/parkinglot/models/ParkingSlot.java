package com.parkinglot.models;

public class ParkingSlot {

    private int slotNumber;
    private boolean isAvailable;
    private VehicleType vehicleType;

    public ParkingSlot(int slotNumber,VehicleType vehicleType, boolean availability) {
        this.slotNumber = slotNumber;
        this.vehicleType = vehicleType;
        this.isAvailable = availability;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public boolean checkAvailability() {
        return isAvailable;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void updateType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void updateAvailability(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}

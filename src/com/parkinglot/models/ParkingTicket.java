package com.parkinglot.models;

public class ParkingTicket {

    int floorNumber;
    int slotNumber;

    public ParkingTicket(final int floorNumber, final int slotNumber) {
        this.floorNumber = floorNumber;
        this.slotNumber = slotNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public String getTicketId() {
        return floorNumber +"_"+ slotNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)  return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingTicket ticket = (ParkingTicket) o;
        return this.floorNumber == ticket.floorNumber && this.slotNumber == ticket.slotNumber;
    }

    @Override
    public int hashCode() {
        String hash = Integer.toString(floorNumber) + Integer.toString(slotNumber);
        return hash.hashCode();
    }
}

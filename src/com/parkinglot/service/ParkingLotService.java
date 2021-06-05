package com.parkinglot.service;

import com.parkinglot.constants.MessageConstants;
import com.parkinglot.models.ParkingLot;
import com.parkinglot.models.ParkingTicket;
import com.parkinglot.models.Vehicle;
import com.parkinglot.models.VehicleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ParkingLotService {

    ParkingLot parkingLot;

    public String createParkingLot(String parkingLotId, int numberOfFloor, int numberOfSlotsOnEachFloor) {
        if(this.parkingLot!=null) throw new RuntimeException();
        this.parkingLot = new ParkingLot(parkingLotId,numberOfFloor,numberOfSlotsOnEachFloor);
        return String.format(MessageConstants.CREATE_MESSAGE, numberOfFloor,numberOfSlotsOnEachFloor);
    }

    public String parkVehicle(Vehicle vehicle) {
        validateParkingLotExists();
        Optional<ParkingTicket> ticket = parkingLot.occupySlot(vehicle);
        if(ticket.isPresent())
            return String.format(MessageConstants.PARK_MESSAGE,parkingLot.getParkingLotId(),ticket.get().getTicketId());
        else
            return MessageConstants.PARKING_LOT_FULL;
    }

    public String unParkVehicle(ParkingTicket ticket) {
        validateParkingLotExists();
        Optional<Vehicle> vehicle = parkingLot.deOccupySlot(ticket);
        if(vehicle.isPresent())
            return String.format(MessageConstants.UNPARK_MESSAGE,vehicle.get().getRegistrationNumber(),vehicle.get().getColour());
        else
            return MessageConstants.INVALID_TICKET;
    }

    public String getFreeSlots(VehicleType vehicleType) {

        validateParkingLotExists();

        HashMap<Integer, List<Integer>> freeSlots = parkingLot.positionsOfAvailableSlots(vehicleType);
        StringBuffer response = new StringBuffer();

        for(Map.Entry<Integer,List<Integer>> entry : freeSlots.entrySet()) {
            response.append(String.format(MessageConstants.FREE_SLOTS,vehicleType.toString(),entry.getKey(),entry.getValue().toString()));
            response.append("\n");
        }

        return response.toString();
    }

    public String getOccupiedSlots(VehicleType vehicleType) {

        validateParkingLotExists();

        HashMap<Integer, List<Integer>> occupiedSlots = parkingLot.positionsOfOccupiedSlots(vehicleType);
        StringBuffer response = new StringBuffer();

        for(Map.Entry<Integer,List<Integer>> entry : occupiedSlots.entrySet()) {
            response.append(String.format(MessageConstants.OCCUPIED_SLOTS,vehicleType.toString(),entry.getKey(),entry.getValue().toString()));
            response.append("\n");
        }

        return response.toString();
    }

    public String getFreeSlotsCount(VehicleType vehicleType) {

        validateParkingLotExists();

        HashMap<Integer,Integer> freeSlots = parkingLot.getCountOfAvailablePosition(vehicleType);
        StringBuffer response = new StringBuffer();

        for(Map.Entry<Integer,Integer> entry : freeSlots.entrySet())
            response.append(String.format(MessageConstants.FREE_SLOTS_COUNT,vehicleType.toString(),entry.getKey(),entry.getValue()));

        return response.toString();
    }

    public String getOccupiedSlotsCount(VehicleType vehicleType) {

        validateParkingLotExists();

        HashMap<Integer,Integer> freeSlots = parkingLot.getCountOfOccupiedPosition(vehicleType);
        StringBuffer response = new StringBuffer();

        for(Map.Entry<Integer,Integer> entry : freeSlots.entrySet())
            response.append(String.format(MessageConstants.OCCUPIED_SLOTS_COUNT,vehicleType.toString(),entry.getKey(),entry.getValue()));

        return response.toString();
    }

    public List<String> getRegistrationNumberForVehicleColor(String colour) {
        validateParkingLotExists();
        return parkingLot.getRegistrationNumberFromVehicleColour(colour).stream().collect(Collectors.toList());
    }

    public String getParkingPositionForRegistrationNumber(String registrationNumber) {
        validateParkingLotExists();
        Optional<ParkingTicket> parkingTicket = parkingLot.getParkingTicketFromRegistrationNumber(registrationNumber);
        return parkingTicket.isEmpty() ? MessageConstants.NOT_FOUND : parkingTicket.get().getFloorNumber() + "|" + parkingTicket.get().getSlotNumber();
    }

    public List<ParkingTicket> getParkingPositionForVehicleColor(String colour) {
        validateParkingLotExists();
        return parkingLot.getTicketsFromVehicleColour(colour).stream().collect(Collectors.toList());
    }

    private void validateParkingLotExists() {
        if (parkingLot == null) {
            throw new RuntimeException("Parking lot does not exists to park.");
        }
    }
}

package com.parkinglot.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ParkingLot {

    private String parkingLotId;
    private int parkingFloorIndex;
    private int parkingFloorsCount;

    private TreeMap<Integer,ParkingFloor> parkingFloors;
    private HashMap<ParkingTicket,Vehicle> ticketMappingToVehicle;
    private HashMap<String,ParkingTicket> registrationNumberMappingToTicket;
    private HashMap<String,HashMap<String,ParkingTicket>> colourMapping;

    public ParkingLot(String parkingLotId, int numberOfFloors, int numberOfSlots) {
        this.parkingLotId = parkingLotId;
        parkingFloors = new TreeMap<>();
        ticketMappingToVehicle = new HashMap<>();
        registrationNumberMappingToTicket = new HashMap<>();
        colourMapping = new HashMap<>();;
        createParkingFloor(numberOfFloors, numberOfSlots);
    }

    public void createParkingFloor(int nFloors, int numberOfSlots) {
        while (nFloors>0) {
            parkingFloorIndex++;
            parkingFloorsCount++;
            ParkingFloor xd = new ParkingFloor(parkingFloorIndex, numberOfSlots);
            parkingFloors.put(parkingFloorIndex,xd);
            nFloors--;
        }
    }

    public Optional<ParkingTicket> occupySlot(Vehicle vehicle) {
        return getNearestParkingFloor(vehicle.getVehicleType()).map(floor -> {
            int parkedSlotNumber = floor.availParkingSlot(vehicle.getVehicleType());
            return Optional.of(createTicket(floor.getFloorNumber(),parkedSlotNumber,vehicle));
        }).orElse(Optional.empty());
    }

    public Optional<Vehicle> deOccupySlot(ParkingTicket ticket) {

        int floorNumber = ticket.getFloorNumber(), slotNumber = ticket.getSlotNumber();

        if(!validateParkingFloor(floorNumber))
            return Optional.empty();

        ParkingFloor parkingFloor = parkingFloors.get(floorNumber);

        if(parkingFloor.freeParkingSlot(slotNumber)) {
            Vehicle vehicle = ticketMappingToVehicle.get(ticket);
            ticketMappingToVehicle.remove(ticket);
            registrationNumberMappingToTicket.remove(vehicle.getRegistrationNumber());
            colourMapping.get(vehicle.getColour()).remove(vehicle.getRegistrationNumber());
            return Optional.of(vehicle);
        }

        return Optional.empty();
    }

    public HashMap<Integer,Integer> getCountOfAvailablePosition(VehicleType vehicleType) {

        HashMap<Integer,Integer> freeSlots = new HashMap<>();

        for(ParkingFloor floor : parkingFloors.values()) {
            ArrayList<ParkingSlot> parkingSlots = floor.getAvailableParkingSlotsList(vehicleType);
            freeSlots.put(floor.getFloorNumber(),freeSlots.getOrDefault(floor.getFloorNumber(),0) + parkingSlots.size());
        }

        return freeSlots;
    }

    public HashMap<Integer, List<Integer>> positionsOfAvailableSlots(VehicleType vehicleType) {

        HashMap<Integer, List<Integer>> freeSlots = new HashMap<>();

        for(ParkingFloor floor : parkingFloors.values()) {
            ArrayList<ParkingSlot> parkingSlots = floor.getAvailableParkingSlotsList(vehicleType);
            List<Integer> slotsPos = parkingSlots.stream().map(parkingSlot -> parkingSlot.getSlotNumber()).collect(Collectors.toList());
            freeSlots.put(floor.getFloorNumber(),slotsPos);
        }

        return freeSlots;
    }

    public HashMap<Integer,Integer> getCountOfOccupiedPosition(VehicleType vehicleType) {

        HashMap<Integer,Integer> occupiedSlots = new HashMap<>();

        for(ParkingFloor floor: parkingFloors.values()) {
            ArrayList<ParkingSlot> parkingSlots = floor.getOccupiedParkingSlotsList(vehicleType);
            occupiedSlots.put(floor.getFloorNumber(),occupiedSlots.getOrDefault(floor.getFloorNumber(),0) + parkingSlots.size());
        }

        return occupiedSlots;
    }

    public HashMap<Integer, List<Integer>> positionsOfOccupiedSlots(VehicleType vehicleType) {

        HashMap<Integer, List<Integer>> occupiedSlots = new HashMap<>();

        for(ParkingFloor floor: parkingFloors.values()) {
            ArrayList<ParkingSlot> parkingSlots = floor.getOccupiedParkingSlotsList(vehicleType);
            List<Integer> slotsPos = parkingSlots.stream().map(parkingSlot -> parkingSlot.getSlotNumber()).collect(Collectors.toList());
            occupiedSlots.put(floor.getFloorNumber(),slotsPos);
        }

        return occupiedSlots;
    }

    public List<String> getRegistrationNumberFromVehicleColour(String colour) {
        if(colourMapping.containsKey(colour)) {
            HashMap<String,ParkingTicket> ticketHashMap = colourMapping.get(colour);
            return ticketHashMap.keySet().stream().collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<ParkingTicket> getTicketsFromVehicleColour(String colour) {
        if(colourMapping.containsKey(colour)) {
            HashMap<String,ParkingTicket> ticketHashMap = colourMapping.get(colour);
            return ticketHashMap.values().stream().collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private ParkingTicket createTicket(int floorNumber, int slotNumber, Vehicle vehicle) {
        final ParkingTicket ticket = new ParkingTicket(floorNumber, slotNumber);
        ticketMappingToVehicle.put(ticket,vehicle);
        registrationNumberMappingToTicket.put(vehicle.getRegistrationNumber(),ticket);
        colourMapping.put(vehicle.getColour(),registrationNumberMappingToTicket);
        return ticket;
    }

    private Optional<ParkingFloor> getNearestParkingFloor(VehicleType vehicleType) {
        for(ParkingFloor floor: parkingFloors.values()) {
            TreeMap<Integer,ParkingSlot> parkingSlots = floor.getAvailableParkingSlotsForVehicleType().get(vehicleType);
            if(!parkingSlots.isEmpty())  return Optional.of(floor);
        }
        return Optional.empty();
    }

    public String getParkingLotId() {
        return parkingLotId;
    }


    public Optional<ParkingTicket> getParkingTicketFromRegistrationNumber(String registrationNumber) {
        return Optional.ofNullable(registrationNumberMappingToTicket.get(registrationNumber));
    }

    public TreeMap<Integer, ParkingFloor> getParkingFloors() {
        return parkingFloors;
    }
    public boolean validateParkingFloor(int parkingFloorNumber) {
        return parkingFloors.containsKey(parkingFloorNumber);
    }
}

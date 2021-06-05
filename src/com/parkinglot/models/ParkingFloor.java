package com.parkinglot.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class ParkingFloor {

    private int floorNumber;
    private int parkingSlotIndex;
    private int parkingSlotsCount;

    private HashMap<VehicleType,TreeMap<Integer,ParkingSlot>> availableParkingSlotsForVehicleType, occupiedParkingSlotsForVehicleType;
    private TreeMap<Integer,ParkingSlot> parkingSlots;

    public ParkingFloor(int floorNumber, int numberOfSlots) {
        this.floorNumber = floorNumber;
        availableParkingSlotsForVehicleType = init();
        occupiedParkingSlotsForVehicleType = init();
        parkingSlots = new TreeMap<>();
        createParkingSlots(1, VehicleType.TRUCK);
        createParkingSlots(Math.min(numberOfSlots-1,2), VehicleType.BIKE);
        createParkingSlots(numberOfSlots - 3, VehicleType.CAR);
    }

    public int availParkingSlot(VehicleType vehicleType) {

        TreeMap<Integer,ParkingSlot> availableSlots = availableParkingSlotsForVehicleType.get(vehicleType);
        TreeMap<Integer,ParkingSlot> occupiedSlots = occupiedParkingSlotsForVehicleType.get(vehicleType);

        if(availableSlots.isEmpty()) {
            return -1;
        } else {
            Map.Entry<Integer, ParkingSlot> entry = availableSlots.firstEntry();
            int slotNumber = entry.getKey();
            ParkingSlot slot = entry.getValue();
            slot.updateAvailability(false);
            availableSlots.remove(slotNumber);
            occupiedSlots.put(slotNumber, slot);
            return slotNumber;
        }
    }

    public boolean freeParkingSlot(int parkingSlotNumber) {

        Optional<ParkingSlot> parkingSlot = Optional.ofNullable(parkingSlots.get(parkingSlotNumber));

        if(parkingSlot.isPresent()) {
            TreeMap<Integer, ParkingSlot> occupiedSlots = occupiedParkingSlotsForVehicleType.get(parkingSlot.get().getVehicleType());
            if(occupiedSlots.containsKey(parkingSlotNumber)) {
                parkingSlot.get().updateAvailability(true);
                occupiedSlots.remove(parkingSlotNumber);
                availableParkingSlotsForVehicleType.get(parkingSlot.get().getVehicleType()).put(parkingSlotNumber,parkingSlot.get());
                return true;
            }
            return false;
        }
        return false;
    }

    public ArrayList<ParkingSlot> getAvailableParkingSlotsList(VehicleType vehicleType) {
        return getParkingSlots(vehicleType, availableParkingSlotsForVehicleType);
    }

    public ArrayList<ParkingSlot> getOccupiedParkingSlotsList(VehicleType vehicleType) {
        return getParkingSlots(vehicleType, occupiedParkingSlotsForVehicleType);
    }


    public int getFloorNumber() {
        return floorNumber;
    }

    public int getParkingSlotsCount() {
        return parkingSlotsCount;
    }

    public HashMap<VehicleType, TreeMap<Integer, ParkingSlot>> getAvailableParkingSlotsForVehicleType() {
        return availableParkingSlotsForVehicleType;
    }

    public HashMap<VehicleType, TreeMap<Integer, ParkingSlot>> getOccupiedParkingSlotsForVehicleType() {
        return occupiedParkingSlotsForVehicleType;
    }

    private void createParkingSlots(int nSlots, VehicleType vehicleType) {
        TreeMap<Integer,ParkingSlot> parkingSlotsForVehicleType = availableParkingSlotsForVehicleType.get(vehicleType);
        while (nSlots>0) {
            parkingSlotIndex++;
            ParkingSlot parkingSlot = new ParkingSlot(parkingSlotIndex, vehicleType,true);
            parkingSlotsForVehicleType.put(parkingSlotIndex, parkingSlot);
            parkingSlots.put(parkingSlotIndex,parkingSlot);
            parkingSlotsCount++;
            nSlots--;
        }
    }

    private ArrayList<ParkingSlot> getParkingSlots(VehicleType vehicleType, HashMap<VehicleType, TreeMap<Integer, ParkingSlot>> parkingSlotsPerVehicle) {
        TreeMap<Integer,ParkingSlot> slots = parkingSlotsPerVehicle.get(vehicleType);

        ArrayList<ParkingSlot> parkingSlots =  new ArrayList<>();
        parkingSlots.addAll(slots.values());

        return parkingSlots;
    }

    private HashMap<VehicleType,TreeMap<Integer,ParkingSlot>> init() {
        return new HashMap() {
            {
                put(VehicleType.TRUCK, new TreeMap<Integer,ParkingSlot>());
                put(VehicleType.BIKE, new TreeMap<Integer,ParkingSlot>());
                put(VehicleType.CAR, new TreeMap<Integer,ParkingSlot>());
            }
        };
    }
}
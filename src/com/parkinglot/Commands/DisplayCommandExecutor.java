package com.parkinglot.Commands;

import com.parkinglot.Writer;
import com.parkinglot.models.Command;
import com.parkinglot.models.VehicleType;
import com.parkinglot.service.ParkingLotService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class DisplayCommandExecutor extends CommandExecutor{

    public static final String COMMAND_NAME = "display";
    HashSet<String> Query = new HashSet<>(Arrays.asList("free_slots","occupied_slots","free_count","occupied_count"));
    HashSet<String> vehicleCategory = new HashSet<>(Arrays.asList("TRUCK","BIKE","CAR"));

    DisplayCommandExecutor(final ParkingLotService parkingLotService, final Writer writer) {
        super(parkingLotService,writer);
    }

    @Override
    public void execute(Command command) {
        String query = command.getParams().get(0);
        VehicleType vehicle = VehicleType.valueOf(command.getParams().get(1));
        String response = "";
        switch (query) {
            case "free_slots" :
                response = parkingLotService.getFreeSlots(vehicle);
                break;
            case "occupied_slots" :
                response = parkingLotService.getOccupiedSlots(vehicle);
                break;
            case "free_count" :
                response = parkingLotService.getFreeSlotsCount(vehicle);
                break;
            case "occupied_count" :
                response = parkingLotService.getOccupiedSlotsCount(vehicle);
                break;
        }
        writer.print(response);
    }

    @Override
    public boolean validate(Command command) {

        List<String> params = command.getParams();

        if(params.size()<2)
            throw new RuntimeException();

        return Query.contains(command.getParams().get(0)) && vehicleCategory.contains(command.getParams().get(1));
    }
}

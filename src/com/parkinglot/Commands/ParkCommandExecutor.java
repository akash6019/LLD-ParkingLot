package com.parkinglot.Commands;

import com.parkinglot.Writer;
import com.parkinglot.models.Command;
import com.parkinglot.models.Vehicle;
import com.parkinglot.models.VehicleType;
import com.parkinglot.service.ParkingLotService;

import java.util.Arrays;
import java.util.HashSet;

public class ParkCommandExecutor extends CommandExecutor {

    public static final String COMMAND_NAME = "park_vehicle";
    HashSet<String> vehicleCategory = new HashSet<>(Arrays.asList("TRUCK","BIKE","CAR"));

    public ParkCommandExecutor(ParkingLotService parkingLotService, Writer writer) {
        super(parkingLotService, writer);
    }

    @Override
    public void execute(Command command) {

        String vehicleType = command.getParams().get(0);
        String registrationNumber = command.getParams().get(1);
        String vehicleColour = command.getParams().get(2);

        String respone = parkingLotService.parkVehicle(new Vehicle(VehicleType.valueOf(vehicleType),registrationNumber,vehicleColour));
        writer.print(respone);
    }

    @Override
    public boolean validate(Command command) {
        if(command.getParams().size() != 3 && !vehicleCategory.contains(command.getParams().get(0)))
            return false;
        return true;
    }
}

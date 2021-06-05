package com.parkinglot.Commands;

import com.parkinglot.Writer;
import com.parkinglot.models.Command;
import com.parkinglot.models.ParkingTicket;
import com.parkinglot.service.ParkingLotService;
import com.parkinglot.validator.IntegerValidator;

public class UnParkCommandExecutor extends CommandExecutor {

    public static final String COMMAND_NAME = "unpark_vehicle";

    public UnParkCommandExecutor(ParkingLotService parkingLotService, Writer writer) {
        super(parkingLotService, writer);
    }

    @Override
    public void execute(Command command) {
        String ticket = command.getParams().get(0);
        int floorNumber = Integer.parseInt(ticket.split("_")[1]);
        int slotNumber = Integer.parseInt(ticket.split("_")[2]);
        String response = parkingLotService.unParkVehicle(new ParkingTicket(floorNumber,slotNumber));
        writer.print(response);
    }

    @Override
    public boolean validate(Command command) {
        if(command.getParams().size() != 1)
            return false;
        String query [] = command.getParams().get(0).split("_");
        return IntegerValidator.isInteger(query[1]) && IntegerValidator.isInteger(query[2]);
    }
}

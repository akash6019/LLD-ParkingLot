package com.parkinglot.Commands;

import com.parkinglot.Writer;
import com.parkinglot.models.Command;
import com.parkinglot.service.ParkingLotService;
import com.parkinglot.validator.IntegerValidator;

import java.util.List;
import java.util.stream.Collectors;

public class CreateCommandExecutor extends CommandExecutor{

    public final static String COMMAND_NAME = "create_parking_lot";

    CreateCommandExecutor(final ParkingLotService parkingLotService, final Writer writer) {
        super(parkingLotService,writer);
    }

    @Override
    public boolean validate(Command command) {

        List<String> params = command.getParams().stream().skip(1).collect(Collectors.toList());

        if(params.size()<2)
            throw new RuntimeException();

        int cnt = params.stream().mapToInt(param -> IntegerValidator.isInteger(param) ? 1 : 0).sum();
        return cnt == 2;
    }

    @Override
    public void execute(Command command) {
        String parkingLotId = command.getParams().get(0);
        int numberOfFloors = Integer.parseInt(command.getParams().get(1));
        int numberOfSlots = Integer.parseInt(command.getParams().get(2));
        String response = parkingLotService.createParkingLot(parkingLotId,numberOfFloors,numberOfSlots);
        writer.print(response);
    }
}

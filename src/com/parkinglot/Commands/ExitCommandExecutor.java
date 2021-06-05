package com.parkinglot.Commands;

import com.parkinglot.Writer;
import com.parkinglot.models.Command;
import com.parkinglot.service.ParkingLotService;

public class ExitCommandExecutor extends CommandExecutor{

    public static final String COMMAND_NAME = "exit";

    public ExitCommandExecutor(ParkingLotService parkingLotService, Writer writer) {
        super(parkingLotService, writer);
    }

    @Override
    public void execute(Command command) {
        writer.thankYou();
        System.exit(0);
    }

    @Override
    public boolean validate(Command command) {
        return command.getCommandName().equals(COMMAND_NAME) && command.getParams().size() == 0;
    }
}

package com.parkinglot.Commands;

import com.parkinglot.Writer;
import com.parkinglot.models.Command;
import com.parkinglot.service.ParkingLotService;

public abstract class CommandExecutor {

    protected ParkingLotService parkingLotService;
    protected Writer writer;

    public CommandExecutor(final ParkingLotService parkingLotService, final Writer writer) {
        this.parkingLotService = parkingLotService;
        this.writer = writer;
    }

    public abstract void execute(Command command);

    public abstract boolean validate(Command command);
}
